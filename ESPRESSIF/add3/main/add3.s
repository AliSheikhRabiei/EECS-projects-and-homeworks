# extern int add3 ( int arg1 , int arg2 , int arg3 )
#
# ARGUMENTS:
# a0 The value of the first argument
# a1 The value of the second argument
# a2 The value of the third argument
#
# RETURNS:
# a0 ( int ) Total sum

 .global add3
 .text
 add3:
  add a0 , a0 , a1 # a0 = a0 + a1
  add a0 , a0 , a2 # a0 = a0 + a2
  ret # return value in a0

