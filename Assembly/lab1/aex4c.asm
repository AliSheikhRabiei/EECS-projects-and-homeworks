# aex4c.asm — lui, addi, sub
# 23456 into x6 (needs +1 adjust because low12=0xBA0 is negative)
lui  x6, 6
addi x6, x6, 0xBA0
# 12345 into x7 (low12=0x039 positive)
lui  x7, 3
addi x7, x7, 57
# x5 = 23456 - 12345 = 11111
sub  x5, x6, x7
