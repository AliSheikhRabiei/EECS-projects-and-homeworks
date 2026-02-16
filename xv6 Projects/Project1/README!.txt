Overview
I implemented a priority scheduler with round robin if equal priority processes, plus starvation prevention via aging. Two new system calls expose process priorities to user space:
int setpriority(int pid, int prio) — set a process’s base priority
int getpriority(int pid) — read the current effective priority
The kernel tracks three new per-process fields: priority (effective), base_priority (requested), and wait_ticks (aging counter). The scheduler always runs the highest-priority runnable process; when multiple processes share that top priority, it round-robins them.

Implementation
Kernel data and defaults
Added to struct proc (kernel/proc.h):
int priority; int base_priority; int wait_ticks;
Constants in kernel (e.g., proc.h):
PRIO_MIN=0, PRIO_MAX=10, PRIO_DEFAULT=5, AGING_TICKS=10 (you can tune AGING_TICKS).

Initialization:
allocproc() and userinit() set priority = base_priority = PRIO_DEFAULT and wait_ticks = 0.
fork() copies parent’s priority and base_priority to the child and resets the child’s wait_ticks.

System calls
sys_setpriority(pid, prio)
Fetches args with argint.
Validates/clamps prio to [PRIO_MIN..PRIO_MAX].
Finds the target proc under p->lock, sets base_priority = prio, and also sets priority = prio (so changes take effect immediately).
Returns 0 on success or -1 if PID not found/invalid.
sys_getpriority(pid)
Finds the target proc and returns its current effective priority (or -1 if not found).
Scheduler (kernel/proc.c: scheduler())

Pass 1 — Aging + discover highest priority present:
For every RUNNABLE process: increment wait_ticks.
If wait_ticks >= AGING_TICKS and priority < PRIO_MAX, bump priority++ and reset wait_ticks.
Track bestprio = max priority seen among runnable procs.
Pass 2 — Selection with round-robin among equals:
Starting from a rotating index rr_start, pick the first RUNNABLE process whose priority == bestprio.
Run it: set RUNNING, clear its wait_ticks, context switch.
On return, restore fairness: p->priority = p->base_priority (so temporary boosts don’t stick forever) and advance rr_start so the next tie starts after this process.

Design decisions
keep scaning the scheduler so we never pick a low priority while a higher one exists.
Aging uses a small counter so tracking is easy(more info in challenges!)
Restoring priority to base_priority after each run ensures “real” changes come from setpriority, while aging only helps tasks that were actually waiting.
Round robin pointer rr_start guarantees fairness among equal-priority tasks.
Syscalls return small integers and avoid panics on bad input; all process table touches are under p->lock.

Testing approach
implimented multiple user programs to test.
start by running burn to create two infinit CPU tasks.
Use prio set <pid> 10 for one and prio set <pid> 0 for the other.
Run testcheck <pidA> <pidB> <samples> <interval> (e.g., testcheck 4 6 100 1) — the higher-priority PID should show the overwhelming majority of RUNNING samples.
If Set both burn processes to the same priority. testcheck reports similar RUNNING percentages across both PIDs.(RR)
Aging behavior
Start two burns. Leave both at low base priority or set one low and one high.
Run agecheck <pid> <samples> <interval> to watch the low process’s priority climb over time (in steps of +1 each AGING_TICKS of waiting) until it reaches the cap; after it runs, it resets to its base_priority.
priocheck <pid> <samples> can also show the effective priority evolving over ticks.

Race sampler
race <ticks> <prioA> <prioB> launches two short-lived CPU loops(parent and child) at chosen priorities and prints their iteration counts; higher priority should consistently do (slightly) more work.

Notes on timing
Sampling tools (meter, testcheck) look at states every N ticks; very large sample counts can take a while. Prefer moderate settings (e.g., 100 samples, interval 1) for the demo.

Usage instructions
Build and boot
From repo root: make clean then make qemu.
For better results use make qemu CPUS=1. using 1 cpu makes it easier to see the tests.

Helpful commands inside this xv6
ps — show processes.
burn — CPU burning workload.
prio get <pid> / prio set <pid> <prio> — read/set priorities.
testcheck <pidA> <pidB> <samples> <interval> — who runs more often.
agecheck <pid> <samples> <interval> — watch priority boosts from aging.
priocheck <pid> <samples> — sample effective priority over time.
race <ticks> <prioA> <prioB> — quick head-to-head run.

Challenges and how they were solved
All the classic typos and forgetting to put the user programs in Makefills and the .... 
Finding a test, used to spam ps to see the pid status but then make the "check" programs to do that instead and report. Scheduler fairness among equals, A “pick first matching” starved later entries; introducing rr_start provided stable round-robin.
Aging that never shows up: If the running task’s priority isn’t reset, it stays permanently boosted. by resetting priority to base_priority after each run makes aging “temporary” and observable.
Long sample runs took too long and short samples make unreliable outputs. Finding a balance was tricky.
Using more than 1 CPU also made it tricky, i got easier tests and results by using CPUS=1


FILES MODIFIED:
Project 1:
kernel/syscall.h — added SYS_getprocs number.
kernel/syscall.c — declared extern sys_getprocs and added it to the syscall table.
kernel/sysproc.c — implemented sys_getprocs (copies an array of procinfo to user space).
user/usys.S — added stubs for getprocs.
user/user.h — declared int getprocs(struct procinfo*, int);.
Makefile — added ps to UPROGS.

Project 2:
kernel/proc.h — added PRIO_MIN/PRIO_MAX/PRIO_DEFAULT, and per-proc fields priority, base_priority, wait_ticks.
kernel/proc.c
allocproc() / userinit() / fork() — initialize and propagate priority fields.
scheduler() — replaced with priority + aging + RR-tie logic.
kernel/syscall.h — added SYS_setpriority and SYS_getpriority.
kernel/syscall.c — declared extern sys_setpriority/sys_getpriority and added to table.
kernel/sysproc.c — implemented sys_setpriority / sys_getpriority.
user/usys.S — added stubs for setpriority / getpriority.
user/user.h — declared int setpriority(int pid,int prio); int getpriority(int pid);
Makefile — added prio, burn, meter, race, priocheck, testcheck, agecheck to UPROGS.

FILES ADDED:
Project 1:
procinfo.h — shared struct definition for struct procinfo (top-level so both kernel and user can #include it).
user/ps.c — user program that calls getprocs and prints a ps-style table.
Project 2:
user/prio.c — CLI to getpriority <pid> and setpriority <pid> <prio>.
user/burn.c — tight CPU hog used to generate runnable load.
user/meter.c — periodic sampler that reports each PID’s RUNNING/RUNNABLE ticks.
user/race.c — runs two CPU hogs; shows how priority skews runtime.
user/priocheck.c — prints a PID’s priority over time (to visualize aging/reset).
user/testcheck.c — quick sampler for two PIDs (sanity check of skew).
user/agecheck.c — demonstrates priority climb via aging.
user/sleeper.c, user/waster.c user/meter keeping cpu busy
