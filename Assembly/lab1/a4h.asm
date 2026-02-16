# a4h.asm — expressions with +1 fix
# build 6146 directly and add 3
lui  x6, (6146 >> 12) + 1
addi x6, x6, 6146 & 0xfff
addi x5, x6, 3
