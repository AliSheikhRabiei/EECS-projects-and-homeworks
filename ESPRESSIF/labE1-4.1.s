.text
.global _start
.extern g_x5
.extern g_x6
.extern g_x7

_start:
# x5 = 0x12345878 using only LUI/ADDI/SLLI/ADD ---
lui x5, 0x12345
addi  x5, x5, 2047
addi  x5, x5, 121

# x6 = 0x11223344 using LUI+ADDI ---
lui x6, 0x11223
addi  x6, x6, 0x344

# x7 = x5 + x6 ---
add x7, x5, x6

# Store to globals so C can print actual register values
la  t0, g_x5
sw  x5, 0(t0)
la  t1, g_x6
sw  x6, 0(t1)
la  t2, g_x7
sw  x7, 0(t2)

ret
