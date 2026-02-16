# square root by bisection: solve x*x = a to |x^2 - a| < eps

# prompts/consts
qa: DC "a:\0"
eps: DF 1e-12

# read a
addi x6, x0, qa
ecall x0, x6, 4
ecall fA, x0, 6

# if a < 1: [lo=a, hi=1]; else: [lo=1, hi=a]
fcvt.d.l f1, x0
addi x7, x0, 1
fcvt.d.l f2, x7
flt.d x1, fA, f2
bne  x1, x0, set_lo_a
# else
fadd.d fLO, f2, x0
fadd.d fHI, fA, x0
jal   x0, have_bounds
set_lo_a:
fadd.d fLO, fA, x0
fadd.d fHI, f2, x0
have_bounds:

# eps and 2.0
fld   fE, eps(x0)
addi  x8, x0, 2
fcvt.d.l fTWO, x8

# loop: while |mid^2 - a| > eps
bisect_loop:
fadd.d  fM, fLO, fHI
fdiv.d  fM, fM, fTWO

fmul.d  fS, fM, fM
# diff = |s - a|
fsub.d  fD, fS, fA
flt.d   x1, fD, f1
beq     x1, x0, abs_ok
fsub.d  fD, f1, fD
abs_ok:
# stop if diff <= eps
fle.d   x1, fD, fE
bne     x1, x0, sqrt_done

# if s < a -> lo=mid else hi=mid
flt.d   x1, fS, fA
bne     x1, x0, set_lo_mid
fadd.d  fHI, fM, x0
jal     x0, bisect_loop
set_lo_mid:
fadd.d  fLO, fM, x0
jal     x0, bisect_loop

sqrt_done:
# print result mid
ecall x0, fM, 1
ebreak x0, x0, 0
