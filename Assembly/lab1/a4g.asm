# a4g.asm — EQU labels with adjusted LUI
# define split parts (resolved at compile time)
b20: EQU 6146 >> 12
b12: EQU 6146 & 0xfff
# build 6146 into x6 using b20+1 and b12
lui  x6, b20 + 1
addi x6, x6, b12
# x5 = x6 + 3
addi x7, x0, 3
add  x5, x6, x7
