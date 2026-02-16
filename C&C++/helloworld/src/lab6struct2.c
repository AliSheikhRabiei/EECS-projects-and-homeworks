// your information goes here
/***************************************
* 25s - Lab06 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>
#include <stdlib.h>

struct ints
{
   int data1;
   int data2;
};


void processStruc (struct ints);
//...
struct ints getSumDiff(int a, int b);
void printStruc (struct ints x);

int main()
{
  struct ints a = {100,4};
  printf("struct a before process: %d %d\n", a.data1, a.data2);
  processStruc(a);  // pass by value
  // display a's members again, no change
  printf("struct a after  process: %d %d\n\n", a.data1, a.data2);

  // functions that return a struct, thus encapsulating multiple values
  /**********************************  */
  int num1, num2;
  printf("Enter two integers: ");
  if (scanf("%d %d", &num1, &num2) != 2) return 0;
  while ( !(num1 == -1 && num2 == -1) ){
   struct ints r = getSumDiff(num1, num2);
   printStruc(r);
   printf("Enter two integers: ");
   if (scanf("%d %d", &num1, &num2) != 2) break;
 }
 /********************************** */
  return 0;
}

/* call/pass by value  */
void processStruc(struct ints x){
  x.data1 ++;
  x.data2 +=100;
}

struct ints getSumDiff(int a, int b){
//...
 struct ints t;
 t.data1 = a + b;
 t.data2 = a - b;
 return t;
}

void printStruc (struct ints x){
//...
 printf("sum is: %d, diff is %d\n", x.data1, x.data2);
}




