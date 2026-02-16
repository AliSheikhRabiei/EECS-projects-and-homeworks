# aex2c.asm — addi, slli
# x5 = 0x0000123400000000
# build 0x1234, then shift left 32
addi x5, x0, 0x12
slli x5, x5, 8
addi x5, x5, 0x34
slli x5, x5, 32
