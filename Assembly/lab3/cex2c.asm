# lench (string length) and appch (append src to dst)

# lench(a2=ptr) -> a0=len
lench:
addi a0, x0, 0
L0:
lb x5, 0(a2)
beq x5, x0, L1
addi a2, a2, 1
addi a0, a0, 1
jal x0, L0
L1:
jalr x0, 0(x1)

# appch(a2=dst, a3=src) ; dst must have enough space
appch:
addi sp, sp, -8
sd x1, 0(sp)
jal x1, lench
add a2, a2, a0
A0:
lb x5, 0(a3)
sb x5, 0(a2)
addi a2, a2, 1
addi a3, a3, 1
bne x5, x0, A0
ld x1, 0(sp)
addi sp, sp, 8
jalr x0, 0(x1)

# demo
dst: DC "hello \0"
src: DC "world!\0"
STACK: EQU 0x100000
lui sp, STACK>>12
addi a2, x0, dst
addi a3, x0, src
jal x1, appch
addi x6, x0, dst
ecall x0, x6, 4
ebreak x0, x0, 0
