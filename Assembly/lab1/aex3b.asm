# aex3b.asm — addi, addi, xori, addi, add
# compute 1234 - (567 + 89) without sub
# 1234 → x6
addi x6, x0, 1234
# (567 + 89) → x7
addi x7, x0, 567
addi x7, x7, 89
# negate x7 (two's complement) → - (567+89)
xori x7, x7, -1
addi x7, x7, 1
# x5 = 1234 + (-(567+89)) = 578
add  x5, x6, x7
