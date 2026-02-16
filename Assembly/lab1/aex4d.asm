# aex4d.asm — addi, lui, slli, add
# x5 = 0x1234587811223344 using only addi/lui/slli/add (no or)
# build low32 = 0x11223344 → x6
lui  x6, 0x11223
addi x6, x6, 0x344
# build high32 = 0x12345878 → x7  (add +1 to LUI because low12=0x878 is negative)
lui  x7, 0x12346
addi x7, x7, 0x878
# shift high into [63:32] and add low
slli x7, x7, 32
add  x5, x7, x6
