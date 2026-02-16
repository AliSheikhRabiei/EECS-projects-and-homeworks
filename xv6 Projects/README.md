Also check the video demonstration inside each project for more information
Project1:
I implemented a priority scheduler with round robin if equal priority processes, plus starvation prevention via aging. Two new system calls expose process priorities to user space:
int setpriority(int pid, int prio) — set a process’s base priority
int getpriority(int pid) — read the current effective priority
The kernel tracks three new per-process fields: priority (effective), base_priority (requested), and wait_ticks (aging counter). The scheduler always runs the highest-priority runnable process; when multiple processes share that top priority, it round-robins them.
Project2:
I implemented a system call named getprocs that copies information about currently existing processes from the kernel into a user array, then returns the number of entries written, a user program named ps calls this system call and prints a simple process table.
The kernel function sys_getprocs reads the kernel process table, takes a small snapshot of live processes (pid, parent pid, state, memory size, name), and copies these snapshots into a user buffer. The copy is done with copyout so it is safe with respect to address spaces. The function returns the count of processes copied. If the parameters are invalid, it returns -1.
Then ps program allocates a buffer for an array of process snapshots, calls getprocs with that buffer and a capacity, checks the return value, converts the numeric state to a human readable word (UNUSED, SLEEP, RUNNABLE, RUNNING, ZOMBIE), and prints a table with columns: PID, PPID, STATE, SZ, NAME.
Project3
This project adds a small memory allocator to xv6 and exposes it to user programs through three syscalls:
getmemstats(): packed stats
stu_alloc(int sz): allocate a student block
stu_free(void *p): free a previously allocated block
