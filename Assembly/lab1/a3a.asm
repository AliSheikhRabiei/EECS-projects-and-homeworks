# a3a.asm — andi, srli
# load 0x123 into x6
addi x6, x0, 0x123
# mask bits [7:4] into x7
andi x7, x6, 0x0f0
# move masked nibble down to [3:0] in x5
srli x5, x7, 4
