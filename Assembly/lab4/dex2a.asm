# dot product of two FP vectors (store result after code)

# data
v1: DF 1.21, 5.85, -7.3, 23.1, -5.55
v2: DF 3.14, -2.1, 44.2, 11.0, -7.77
N:  DD 5
dst: DM 1

# pointers & count
addi x10, x0, v1
addi x11, x0, v2
ld   x12, N(x0)

# sum = 0.0
fmv.d.x f5, x0

# loop
dpa_loop:
beq  x12, x0, dpa_done
fld  f1, 0(x10)
fld  f2, 0(x11)
fmul.d f3, f1, f2
fadd.d f5, f5, f3
addi x10, x10, 8
addi x11, x11, 8
addi x12, x12, -1
jal  x0, dpa_loop

dpa_done:
fsd  f5, dst(x0)
# optional print
ecall x0, f5, 1
ebreak x0, x0, 0
