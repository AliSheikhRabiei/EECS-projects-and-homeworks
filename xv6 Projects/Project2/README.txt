Implementation:
I implemented a system call named getprocs that copies information about currently existing processes from the kernel into a user array, then returns the number of entries written, a user program named ps calls this system call and prints a simple process table.
The kernel function sys_getprocs reads the kernel process table, takes a small snapshot of live processes (pid, parent pid, state, memory size, name), and copies these snapshots into a user buffer. The copy is done with copyout so it is safe with respect to address spaces. The function returns the count of processes copied. If the parameters are invalid, it returns -1.
Then ps program allocates a buffer for an array of process snapshots, calls getprocs with that buffer and a capacity, checks the return value, converts the numeric state to a human readable word (UNUSED, SLEEP, RUNNABLE, RUNNING, ZOMBIE), and prints a table with columns: PID, PPID, STATE, SZ, NAME.
ps calls the C stub for getprocs. The stub performs a system call instruction. The kernel trap handler enters the generic dispatcher in syscall.c. The dispatcher reads the system call number, looks it up in the table, and calls sys_getprocs. The return value is placed in the user register a0. Control returns to ps, which then prints the result.
I also used sleeper and waster for testing system. The sleeper program suspends execution for a caller-specified number of timer ticks and then exits. It is used to create a short-lived background process that blocks inside the kernel, so its state can be observed with the ps program. The waster program is a long-lived process that performs simple computation and periodically yields the CPU. It is used to produce a background task that remains visible in ps for an extended time. 

Design decisions:
I put a copy of procinfo.h for use and kernel, both identical, to have easier access to them and avoid mixing kernel and user codes and access.
In system call, getprocs, each process entry is read while holding that process in spinlock. This prevents torn or inconsistent reads. It also rejects a null user pointer and any negative max value by returning minus one. A max value of zero is treated as a valid request that returns zero entries. Any other invalid user address is caught by copyout failing, which also leads to returning minus one.
The ps program allocates its array on the heap with malloc instead of using a large stack array. xv6 user stacks are small, so this avoids page faults from stack overflow. Also xv6 printf is minimal. The program uses only the supported formats for integers and strings, and performs a simple mapping from numeric state to a short word for readability.

Testing approach:
Successful boot is always a good sign!
Run ps after boot. Expect to see at least init, sh, and ps with sensible PIDs and PPIDs.
I used sleeper and waster to make long lasting background process that gives me chance to test ps in action. You can change the pause time in these 2 function to see the difference it make.
ptest.c is a program that was made to test the different cases, especially the edge cases for max_procs.
max_procs equals zero: expect return value zero, no entries written.
max_procs equals one: expect return value one, exactly one entry printed.
Null user pointer: expect return value minus one, no kernel panic.
Starting few background jobs, like “sleep 50”, and run ps again. Expect more rows and states such as SLEEP and RUNNABLE to appear with new numbers.
Intentionally invalid or unmapped user buffer: expect copyout to fail and return minus one.

Usage instructions:
Build and boot
From the xv6 project root, run: make clean, then make qemu.
xv6 will boot inside QEMU and present a shell prompt.
Run the program
At the xv6 shell prompt, run: ps.
if you need a background process to check the ps you can use: 
waster & 
ps
sleeper & 
ps
Since both sleeper and waster are infinite loops & let us call something after them but you cannot enter 2 calls in same line so you call ps in next line, and this should give you a long lasting process to see in the ps table. 

files changed:
Makefile
kernel/syscall.h
kernel/syscall.c
kernel/sysproc.c
user/usys.S
user/user.h

files added:
kernel/procinfo.h
user/procinfo.h
ps.c
ptest.c
waster.c
sleeper.c
getcourseno.c

Challenges encountered and how they were solved:
RISC-V xv6 expects syscall handlers with the signature “uint64 sys_name(void)”. Using any other return type causes type mismatches in the dispatcher. Switching to the correct signature resolved build errors. Basically any typo was a pain to find and solve. Also lots of small mistakes like forgetting to add #include "procinfo.h" on top or user.c and many more were encountered and solved. 
In xv6 tree, argaddr and argint are void-returning helpers that only fetch arguments. Early attempts to compare their return values caused compile errors. The solution was to use them to fetch values, then validate the fetched values and rely on copyout to detect bad pointers.
xv6 printf is simple and doesnot support many things. Printing is limited to supported formats and simple textual mapping of state values.
Testing the ps in action was a challenge. I had to comeup with sleeper and waster to keep the CPU busy to check the ps. 

And i didn't reinstalled the xv64 soo the getcourseno from assignment 3 is still here and working.

