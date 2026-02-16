# a2d.asm — srai, addi
# load -88 into x6
addi x6, x0, -88
# arithmetic right shift by 3 (÷8 with sign)
srai x5, x6, 3
