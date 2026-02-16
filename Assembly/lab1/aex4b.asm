# aex4b.asm — lui, addi, addi
# store -8000 in x6, then x5 = -8000 + 10
# pick U = -2 so: (-2 << 12) + 192 = -8192 + 192 = -8000
lui  x6, -2
addi x6, x6, 192
addi x5, x6, 10
