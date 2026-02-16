.section .data
number: .word 0xA5A5A5A5     # Example number
result: .word 0

.section .text
.global _start

_start:
# Load input and init parity accumulator
la  t0, number
lw  t1, 0(t0)
li  t2, 0

# Parity loop: while (t1 != 0) { t2 ^= (t1 & 1); t1 >>= 1; }
1:  andi  t3, t1, 1
xor t2, t2, t3
srli  t1, t1, 1
bnez  t1, 1b

# Store result and return it in a0
la  t0, result
sw  t2, 0(t0)
mv  a0, t2

ret
