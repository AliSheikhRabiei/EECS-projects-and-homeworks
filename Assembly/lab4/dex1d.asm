# store divisors after code; print "prime" or "not prime"

ask: DC "Enter i:\0"
prime: DC "prime\n\0"
nprim: DC "not prime\n\0"
nl: DC "\n\0"

# read i
addi x6, x0, ask
ecall x0, x6, 4
ecall x5, x0, 5

# storage for divisors
dst: DM 128

# count=0, offset=0
add  x9, x0, x0
add  x1, x0, x0
addi x7, x0, 1

div_loop:
rem  x8, x5, x7
bne  x8, x0, next_d
sd   x7, dst(x1)
addi x1, x1, 8
addi x9, x9, 1
next_d:
addi x7, x7, 1
ble  x7, x5, div_loop

# decide: prime iff count == 2 
addi x6, x0, 2
bne  x9, x6, notp
addi x6, x0, prime
ecall x0, x6, 4
ebreak x0, x0, 0

notp:
addi x6, x0, nprim
ecall x0, x6, 4
ebreak x0, x0, 0
