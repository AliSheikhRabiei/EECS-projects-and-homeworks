# a2e.asm — slli, srli, addi  (extract bits [7:4] from x6 into x5[3:0])
# load 0x0123 into x6
addi x6, x0, 0x123
# move target nibble to the MSB, clearing lower bits on next step
slli x7, x6, 56
# bring it down to the LSB; upper bits zero-filled by srli
srli x5, x7, 60
