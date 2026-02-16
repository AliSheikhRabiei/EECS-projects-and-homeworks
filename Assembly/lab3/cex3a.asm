# recursive gcd

qA: DC "a: \0"
qB: DC "b: \0"
STACK: EQU 0x100000

# init stack, read a,b
lui sp, STACK>>12
addi x6, x0, qA
ecall x0, x6, 4
ecall a0, x0, 5
addi x6, x0, qB
ecall x0, x6, 4
ecall a1, x0, 5

# call gcd(a0, a1) -> a0
jal x1, gcd
ecall x0, a0, 0
ebreak x0, x0, 0

# gcd(a0, a1) -> a0
gcd:
beq a1, x0, gcd_ret
rem x5, a0, a1
addi sp, sp, -8
sd x1, 0(sp)
addi a0, a1, 0
addi a1, x5, 0
jal x1, gcd
ld x1, 0(sp)
addi sp, sp, 8
gcd_ret:
jalr x0, 0(x1)
