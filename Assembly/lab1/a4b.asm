# a4b.asm — lui, addi, add
# build 4098 in x6 via 1/2 split
lui  x6, 1
addi x6, x6, 2
# load 3 into x7
addi x7, x0, 3
# x5 = x6 + x7 = 4101
add  x5, x6, x7
