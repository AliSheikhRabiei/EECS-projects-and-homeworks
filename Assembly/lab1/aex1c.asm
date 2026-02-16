# aex1c.asm — addi, sub
# compute (1024-512) into x6
addi x6, x0, 1024
addi x7, x0, 512
sub  x6, x6, x7
# compute (256-128) into x7
addi x7, x0, 256
addi x8, x0, 128
sub  x7, x7, x8
# x5 = (1024-512) - (256-128) = 384
sub  x5, x6, x7
