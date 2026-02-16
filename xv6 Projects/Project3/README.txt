Project3
Student Allocator for xv6-riscv
Ali Sheikh Rabiei
Student ID=218475095

This project adds a small memory allocator to xv6 and exposes it to user programs through three syscalls:
getmemstats(): packed stats 
stu_alloc(int sz): allocate a student block
stu_free(void *p): free a previously allocated block

Strategy=0: first-fit over a fixed-size block list.
Blocks are taken from a singly-linked free list; the first free block with sufficient capacity is returned.

Current implementation is fixed-block first-fit; multi-block or coalescing is intentionally omitted.
All user-facing behavior (ID marker, strategy, counts) is observable via getmemstats(), they are based on my student ID and formula provided, and can be verified by tests1 and basic test.

Three user programs demonstrate correctness and behavior:
test_basic: basic allocate and free and ID(magic numbers) verification
test_strategy: fragmentation and strategy behavior
test_stress: testing repeated bursts, edge cases, timing


How to use? simple just run these 3 after make qemu
test_basic
test_strategy
test_stress

Files I changed and added:
Kernel:
kernel/kalloc.c: added student allocator state, student_init(), student_malloc(), student_free(), student_stats().
kernel/sysproc.c: added syscall handlers sys_getmemstats, sys_stu_alloc, sys_stu_free.
kernel/syscall.h: added syscall numbers.
kernel/syscall.c: registered the 3 syscalls in the dispatch table.
kernel/defs.h: prototypes for student allocator helpers.

User:
user/user.h: prototypes for the 3 syscalls.
user/usys.S: syscall stubs for the 3 syscalls.

Tests(new):
user/test_basic.c
user/test_strategy.c
user/test_stress.c
user/test1

Makefile:
Added _test_basic, _test_strategy, _test_stress, test1 to UPROGS.