# a4i.asm — build 64-bit constant 0x1234567811223344
# define the constant
c:   EQU 0x1234567811223344
# load low 32 bits into x6 ([31:12] via LUI, [11:0] via ADDI)
lui  x6, (c & 0xffffffff) >> 12
addi x6, x6, c & 0xfff
# load high 32 bits into x7 ([63:44] via LUI; [43:32] via ADDI)
lui  x7, c >> 44
addi x7, x7, (c & 0xfff00000000) >> 32
# shift high part into position and combine
slli x7, x7, 32
or   x5, x6, x7
