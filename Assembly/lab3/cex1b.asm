# palindrome check using stack

# messages
ask: DC "String:\0"
yes: DC "palindrome\n\0"
no:  DC "not palindrome\n\0"

# stack base
STACK: EQU 0x100000

# input buffer
buf: DM 8

# init stack pointer
lui sp, STACK>>12

# prompt & read string into buf
addi x6, x0, ask
ecall x0, x6, 4
addi x5, x0, buf
ecall x5, x0, 9

# push all characters until NUL
addi x10, x0, buf
push_loop:
lb   x6, 0(x10)
beq  x6, x0, begin_cmp
sb   x6, 0(sp)
addi sp, sp, -1
addi x10, x10, 1
beq  x0, x0, push_loop

# compare forward vs reverse (pop)
addi x10, x0, buf
begin_cmp:
lb x6, 0(x10)
beq x6, x0, pal_yes
addi sp, sp, 1
lb x7, 0(sp)
bne x6, x7, pal_no
addi x10, x10, 1
beq x0, x0, begin_cmp

pal_yes:
addi x6, x0, yes
ecall x0, x6, 4
ebreak x0, x0, 0

pal_no:
addi x6, x0, no
ecall x0, x6, 4
ebreak x0, x0, 0
