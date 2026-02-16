# a4e.asm — lui, addi, add  (WRONG due to sign-ext of 0x802)
# try to build 6146 into x6
lui  x6, 1
addi x6, x6, 0x802    # treated as -2046 → x6 = 4096 - 2046 = 2050
# load 3 and add
addi x7, x0, 3
add  x5, x6, x7       # gives 2053 (wrong)
