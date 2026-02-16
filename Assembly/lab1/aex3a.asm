# aex3a.asm — addi, xori, addi
# convert -5 to +5 (two's complement: NOT then +1)
# load -5 into x6
addi x6, x0, -5
# bitwise NOT into x5
xori x5, x6, -1
# add 1 → +5
addi x5, x5, 1
