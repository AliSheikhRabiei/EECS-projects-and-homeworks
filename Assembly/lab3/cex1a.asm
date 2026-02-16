# binary digits with stack
q: DC "Unsigned int: \0"
nl: DC "\n\0"
STACK: EQU 0x100000

# initialize sp to stack top
lui sp, STACK>>12

# ask & read the number
addi x6, x0, q
ecall x0, x6, 4
ecall x5, x0, 5

# special case n == 0:
beq  x5, x0, print_zero

# while (n > 0): push (n & 1), n >>= 1
bin_push_loop:
andi x6, x5, 1
sd x6, 0(sp)
addi sp, sp, -8
srli x5, x5, 1
bne x5, x0, bin_push_loop

# pop & print all bits
bin_pop_loop:
addi sp, sp, 8
ld x6, 0(sp)
ecall x1, x6, 0

# stop when we've popped last sp back at STACK?
lui x7, STACK>>12
bne sp, x7, bin_pop_loop

# print newline and exit
addi x6, x0, nl
ecall x0, x6, 4
ebreak x0, x0, 0

print_zero:
addi x6, x0, 0
ecall x1, x6, 0
addi x6, x0, nl
ecall x0, x6, 4
ebreak x0, x0, 0
