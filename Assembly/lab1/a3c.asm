# a3c.asm — xori, addi
# load 0x123 into x6
addi x6, x0, 0x123
# bitwise NOT of x6 into x5
xori x5, x6, -1
