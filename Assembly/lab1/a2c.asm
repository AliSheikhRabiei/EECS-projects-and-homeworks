# a2c.asm — srli, addi
# load -88 into x6
addi x6, x0, -88
# shift right by 3 bits (logical ÷8) — WRONG for negatives
srli x5, x6, 3
