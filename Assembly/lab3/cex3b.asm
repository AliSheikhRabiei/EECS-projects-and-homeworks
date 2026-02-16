# tail-recursive Fibonacci

qN: DC "n: \0"
STACK: EQU 0x100000

# init stack, read n, set a=0, b=1
lui sp, STACK>>12
addi x6, x0, qN
ecall x0, x6, 4
ecall a0, x0, 5
addi a1, x0, 0
addi a2, x0, 1

# call fib(n,a,b) -> a0
jal x1, fib
ecall x0, a0, 0
ebreak x0, x0, 0

# fib(a0=n, a1=a, a2=b) -> a0
fib:
beq  a0, x0, fib_ret_a
addi x5, x0, 1
beq  a0, x5, fib_ret_b
addi sp, sp, -8
sd   x1, 0(sp)
add  a3, a1, a2
addi a0, a0, -1
addi a1, a2, 0
addi a2, a3, 0
jal  x1, fib
ld   x1, 0(sp)
addi sp, sp, 8
jalr x0, 0(x1)

fib_ret_a:
addi a0, a1, 0
jalr x0, 0(x1)

fib_ret_b:
addi a0, a2, 0
jalr x0, 0(x1)
