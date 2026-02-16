# a4f.asm — lui(+1), addi, add  (correct 6146 + 3)
# adjust upper by +1 to counter sign-extension of low 12 bits
lui  x6, 2
addi x6, x6, 0x802
# load 3 and add
addi x7, x0, 3
add  x5, x6, x7
