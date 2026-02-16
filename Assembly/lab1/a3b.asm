# a3b.asm — or, andi, addi
# load 0x123 into x6
addi x6, x0, 0x123
# keep only the middle hex digit in x6
andi x6, x6, 0x0f0
# load 0x456 into x7
addi x7, x0, 0x456
# keep only the 1st and 3rd hex digits in x7
andi x7, x7, 0xf0f
# combine selected digits into x5
or   x5, x6, x7
