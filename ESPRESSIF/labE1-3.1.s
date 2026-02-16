.text
.global add_numbers

add_numbers:
# a0<=2030, a1=2021
addi  a0, x0, 2030
addi  a1, x0, 2021

# a2 = a0 + a1
add   a2, a0, a1

# return value in a0
mv  a0, a2
ret
