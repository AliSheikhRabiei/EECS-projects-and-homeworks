# a4d.asm — EQU (compile-time split of 6146)
# b20 = upper 20 bits of 6146 (6146 >> 12)
b20: EQU 6146 >> 12
# b12 = low 12 bits of 6146 (6146 & 0xfff)
b12: EQU 6146 & 0xfff
