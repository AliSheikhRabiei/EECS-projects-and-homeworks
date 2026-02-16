# sum 1..(n-1) and verify with (n*(n-1))/2

p: DC "sum(1..n-1) Enter n:\0"
lab1:DC "sum(1.."
lab2:DC ")="
eq: DC "\n\0"

# read n
addi x6, x0, p
ecall x0, x6, 4
ecall x5, x0, 5

# sum loop: s
add  x7, x0, x0
add  x8, x0, x0
sum_loop:
addi x8, x8, 1
add  x7, x7, x8
blt  x8, x5, sum_loop

sub  x7, x7, x5

# print n and the value
addi x6, x0, lab1
ecall x1, x6, 4
add  x9, x5, x0
addi x9, x9, -1
ecall x1, x9, 0
addi x6, x0, lab2
ecall x1, x6, 4
ecall x0, x7, 0

# newline
addi x6, x0, eq
ecall x0, x6, 4

# verify: (n*(n-1))/2
add  x10, x5, x0
addi x11, x5, -1
mul  x12, x10, x11
addi x13, x0, 2
div  x12, x12, x13

# print n and value
addi x6, x0, "(n*(n-1))/2=\0"
ecall x1, x6, 4
ecall x0, x12, 0
ebreak x0, x0, 0
