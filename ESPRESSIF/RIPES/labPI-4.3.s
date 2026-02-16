.data
#i have changed this code so many times. i didnt keepup with all the comments and tried my best to do it as clean as possiable.
#so many hazards(14 if im not mistaken) needed to be fixed, but it worked at the end.
a:  .word 12, 11, 13, 14, 19, 21, 61
b:  .word 1, 2, 3, 5, 9, 10, 5
N:  .word 28

.text

#load base addresses & N once
auipc x3, 0x10000          # base to reach N / a / b
auipc x4, 0x10000          # base for a
auipc x5, 0x10000          # base for b

addi  x4, x4, -4           # x4 -> &a[0]
addi  x5, x5, 20           # x5 -> &b[0]

lw    x6, 56(x3)

addi  x11, x0, 0

#Main loop

loop_head:
# Compute addresses for this iteration, independent of loads
add   x12, x5, x11
add   x13, x4, x11

# Load b[i] and a[i]
lw    x14, 0(x12)
lw    x15, 0(x13)

# Two independent instructions to separate both loads
addi  x3, x3, 0            # dummy, no effect
addi  x3, x3, 0            # dummy, no effect

# Now safe to use loaded values
add   x10, x15, x14
andi  x10, x10, 1

beq   x10, x0, even

#Odd case branch

odd:
add   x23, x14, x14        # x23 = 2 * b[i]
sw    x23, 0(x13)          # a[i] = 2 * b[i]

addi  x24, x0, 5           # x24 = 5
sw    x24, 0(x12)          # b[i] = 5

j     loop_tail            # skip even-part

#Even case branch

even:
addi  x23, x0, 1           # x23 = 1
sw    x23, 0(x13)          # a[i] = 1

mul   x24, x14, x14        # x24 = b[i]^2
sw    x24, 0(x12)          # b[i] = b[i]^2

#Loop update & exit

loop_tail:
addi  x11, x11, 4          # i += 4

addi  x3, x3, 0
addi  x3, x3, 0

bne   x6, x11, loop_head   # if i != N, loop again
