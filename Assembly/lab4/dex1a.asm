# factorial of a small positive integer

q: DC "n! Enter n:\0"
eq: DC "\n\0"

# read n
addi x6, x0, q
ecall x0, x6, 4
ecall x5, x0, 5

# handle 0! = 1
beq  x5, x0, fact_zero

# res = 1, i = n
addi x7, x0, 1
add  x8, x5, x0

fact_loop:
mul  x7, x7, x8
addi x8, x8, -1
bne  x8, x0, fact_loop
ecall x0, x7, 0
addi x6, x0, eq
ecall x0, x6, 4
ebreak x0, x0, 0

fact_zero:
addi x7, x0, 1
ecall x0, x7, 0
addi x6, x0, eq
ecall x0, x6, 4
ebreak x0, x0, 0
