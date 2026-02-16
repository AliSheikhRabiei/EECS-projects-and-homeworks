.section .data
x:      .word  2          # Input x (change to test)
y:      .word  3          # Input y (change to test)
result: .word  0          # f(x,y) stored here

.section .text
.global _start

# helper to make sure a0 <- |a0|
.abs32:
srai   t4, a0, 31
xor    a0, a0, t4
sub    a0, a0, t4
ret

# helper for square(a0) = a0*a0
square:
mv  t5, ra
jal   ra, .abs32
mv  t0, a0
mv     t1, x0
beq    t0, x0, .sq_done

.sq_loop:
add    t1, t1, a0
addi   t0, t0, -1
bne    t0, x0, .sq_loop

.sq_done:
    mv     a0, t1
    mv     ra, t5
    ret

#SAVE RA from main
_start:
mv     t6, ra

# x^2
la     t2, x
lw     a0, 0(t2)
jal    ra, square
mv     t3, a0

# y^2
la     t2, y
lw     a0, 0(t2)
jal    ra, square

add    a0, a0, t3      # a0 = x^2 + y^2
la     t2, result
sw     a0, 0(t2)

# RESTORE RA for return to main
mv     ra, t6
ret
