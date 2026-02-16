#  evaluate a postfix expression using a stack


# prompt and buffer
q: DC "Postfix: \0"
buf: DM 8

# stack base (down-growing per Lab C)
STACK: EQU 0x100000

# init stack pointer
lui sp, STACK>>12

# read expression
addi x6, x0, q
ecall x0, x6, 4
addi x5, x0, buf
ecall x5, x0, 9

# scan pointer
addi  x10, x0, buf

scan:
lb x9, 0(x10)
beq x9, x0, done
addi x10, x10, 1

# if 0 <= x9 <= 9, push
addi x7, x0, '0'
blt x9, x7, maybe_op
addi x7, x0, '9'+1
bge x9, x7, maybe_op

addi x6, x9, -'0'
sd x6, 0(sp)
addi sp, sp, -8
beq  x0, x0, scan

# pop op2, then op1
maybe_op:
addi sp, sp, 8
ld x7, 0(sp)
addi sp, sp, 8
ld x6, 0(sp)

# dispatch on operator in x9
addi x8, x0, '+'
beq x9, x8, do_add
addi x8, x0, '-'
beq x9, x8, do_sub
addi x8, x0, '*'
beq x9, x8, do_mul
addi x8, x0, '/'
beq x9, x8, do_div
beq x0, x0, scan

do_add:
add x5, x6, x7
beq x0, x0, push_res
do_sub:
sub x5, x6, x7
beq x0, x0, push_res
do_mul:
mul x5, x6, x7
beq x0, x0, push_res
do_div:
div x5, x6, x7

push_res:
sd x5, 0(sp)
addi sp, sp, -8
beq x0, x0, scan

done:
addi sp, sp, 8
ld x5, 0(sp)
ecall x0, x5, 0         
ebreak x0, x0, 0
