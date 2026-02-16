# approximate e by sum_{k=0..n} 1.0/k!

# prompts/const
ask: DC "n:\0"

# read n
addi x6, x0, ask
ecall x0, x6, 4
ecall x5, x0, 5

# sum=1.0, term=1.0
addi x7, x0, 1
fmv.d.x f1, x0
fcvt.d.l f1, x7
fadd.d  f2, f1, x0
fadd.d  f3, f1, x0

# for k=1..n: term/=k; sum+=term
ebeqz:
beq  x5, x0, e_done
e_loop:
# convert k -> double in f4
fcvt.d.l f4, x7
fdiv.d   f3, f3, f4
fadd.d   f2, f2, f3    
addi     x7, x7, 1
blt      x7, x5, e_loop
beq      x7, x5, e_loop

e_done:
# print sum
ecall x0, f2, 1
ebreak x0, x0, 0
