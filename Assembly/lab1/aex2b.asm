# aex2b.asm — addi, slli
# x5 = 0xffffffff00000000
# start from -1 then shift left 32
addi x5, x0, -1
slli x5, x5, 32
