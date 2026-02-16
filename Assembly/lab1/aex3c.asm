# aex3c.asm — addi, srli, slli, or
# rotate right by 4 bits: (x >> 4) | (x << 60)
# load 0x000...0123 into x6
addi x6, x0, 0x123
# low part after right shift
srli x7, x6, 4
# high nibble moved to top
slli x8, x6, 60
# combine → x5 = 0x3000000000000012
or   x5, x7, x8
