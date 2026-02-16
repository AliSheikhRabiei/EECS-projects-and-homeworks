# a4c.asm — lui, addi
# build 4098 in x6 via 1/2 split
lui  x6, 1
addi x6, x6, 2
# x5 = x6 + 3 = 4101
addi x5, x6, 3
