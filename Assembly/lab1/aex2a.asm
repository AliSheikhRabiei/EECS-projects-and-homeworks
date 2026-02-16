# aex2a.asm — srli, slli, sub, slli
# goal: x5 = (888/8 - 123*4) * 2
# 888/8 → x6
addi x6, x0, 888
srli x6, x6, 3
# 123*4 → x7
addi x7, x0, 123
slli x7, x7, 2
# (888/8 - 123*4) → x5
sub  x5, x6, x7
# … then *2
slli x5, x5, 1
