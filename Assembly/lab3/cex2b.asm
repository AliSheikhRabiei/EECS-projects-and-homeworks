# use subch/repch to cover delete/insert cases (i–iv)

# repch(a2=addr, a3=delete_count, a4=addr_of_replacement)

repch:
sd x1, 0(sp)
sd s0, -8(sp)
sd s1, -16(sp)
addi sp, sp, -24
addi s0, a2, 0
addi s1, a4, 0
jal x1, delch
addi a2, s0, 0
addi a3, a4, 0
jal x1, insch
addi sp, sp, 24
ld x1, 0(sp)
ld s0, -8(sp)
ld s1, -16(sp)
jalr x0, 0(x1)

# leaf helpers from C2 (needed by repch)

delch1:
lb x5, 0(a2)
d1_loop:
beq x5, x0, d1_end
lb x5, 1(a2)
sb x5, 0(a2)
addi a2, a2, 1
jal x0, d1_loop
d1_end:
jalr x0, 0(x1)

delch:
sd x1, 0(sp)
sd s0, -8(sp)
sd s1, -16(sp)
addi sp, sp, -24
addi s0, a2, 0
addi s1, a3, 0
bge x0, s1, d_end
d_loop:
jal x1, delch1
addi a2, s0, 0
addi s1, s1, -1
bne s1, x0, d_loop
d_end:
addi sp, sp, 24
ld x1, 0(sp)
ld s0, -8(sp)
ld s1, -16(sp)
jalr x0, 0(x1)

insch1:
lb x5, 0(a2)
sb a3, 0(a2)
addi a3, x5, 0
addi a2, a2, 1
bne a3, x0, insch1
sb a3, 0(a2)
jalr x0, 0(x1)

insch:
sd x1, 0(sp)
sd s0, -8(sp)
sd s1, -16(sp)
addi sp, sp, -24
addi s0, a2, 0
addi s1, a3, 0
i_loop:
lb a3, 0(s1)
beq a3, x0, i_end
jal x1, insch1
addi s0, s0, 1
addi a2, s0, 0
addi s1, s1, 1
beq x0, x0, i_loop
i_end:
addi sp, sp, 24
ld x1, 0(sp)
ld s0, -8(sp)
ld s1, -16(sp)
jalr x0, 0(x1)

# test data + four calls using repch only

txt:   DC "abcdefghi\0"
empty: DC "\0"
oneX:  DC "X\0"
many:  DC "NEW\0"
STACK: EQU 0x100000

lui sp, STACK>>12

# i) delete 1 char: remove 'c' (index 2)
addi a2, x0, txt+2
addi a3, x0, 1
addi a4, x0, empty
jal x1, repch

# ii) delete >1: remove "def" starting at index 3
addi a2, x0, txt+3
addi a3, x0, 3
addi a4, x0, empty
jal x1, repch

# iii) insert one char 'X' at index 1
addi a2, x0, txt+1
addi a3, x0, 0
addi a4, x0, oneX
jal x1, repch

# iv) insert "NEW" at index 5
addi a2, x0, txt+5
addi a3, x0, 0
addi a4, x0, many
jal x1, repch

addi x6, x0, txt
ecall x0, x6, 4
ebreak x0, x0, 0
