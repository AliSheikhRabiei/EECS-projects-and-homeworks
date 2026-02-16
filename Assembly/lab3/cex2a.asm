# no stack delch/insch that call delch1/insch1

# delch1: delete 1 char at a2
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

# insch1: insert 1 char at a2 (char in a3)
insch1:
lb x5, 0(a2)
sb a3, 0(a2)
addi a3, x5, 0
addi a2, a2, 1
bne a3, x0, insch1
sb a3, 0(a2)
jalr x0, 0(x1)

# delch_ns(a2=addr, a3=count) — no stack; uses temps t0..t3
delch_ns:
addi x28, x1, 0
addi x6,  a2, 0
addi x7,  a3, 0
d_ns_loop:
beq  x7, x0, d_ns_end
addi a2, x6, 0
jal  x1, delch1
addi x7, x7, -1
jal  x0, d_ns_loop
d_ns_end:
jalr x0, 0(x28)

# insch_ns(a2=addr, a3=addr_of_string) — no stack; uses temps t0..t3
insch_ns:
addi x28, x1, 0
addi x6,  a2, 0
addi x7,  a3, 0      
i_ns_loop:
lb   a3, 0(x7)
beq  a3, x0, i_ns_end
addi a2, x6, 0
jal  x1, insch1
addi x6, x6, 1
addi x7, x7, 1
jal  x0, i_ns_loop
i_ns_end:
jalr x0, 0(x28)
