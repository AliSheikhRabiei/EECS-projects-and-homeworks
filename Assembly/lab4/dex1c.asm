# list all divisors of a positive integer

hdr: DC "Find all divisors.\n\0"
ask: DC "Enter i:\0"
lab: DC "Divisors:\n\0"
nl:  DC "\n\0"

# intro + read i
addi x6, x0, hdr
ecall x0, x6, 4
addi x6, x0, ask
ecall x0, x6, 4
ecall x5, x0, 5       # i

# title
addi x6, x0, lab
ecall x0, x6, 4

# d = 1..i
addi x7, x0, 1
div_loop:
rem  x8, x5, x7
bne  x8, x0, next_d
ecall x0, x7, 0       # print divisor
addi x6, x0, nl
ecall x0, x6, 4
next_d:
addi x7, x7, 1
ble  x7, x5, div_loop
ebreak x0, x0, 0
