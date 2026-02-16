# aex4a.asm — lui, addi, addi
# store 8000 in x6, then x5 = 8000 - 10
# adjust LUI by +1 because low12 (0xF40) is negative when sign-extended
lui  x6, 2
addi x6, x6, 0xF40
addi x5, x6, -10
