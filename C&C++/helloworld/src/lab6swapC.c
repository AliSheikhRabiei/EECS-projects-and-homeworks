//your information here
/***************************************
* 25s - Lab06 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

void swapIncres(int *, int *, int *);
void swap(int **, int **);

int main( ) {
  int a, b, c;

  /* Read in three integers */
  scanf("%d %d %d", &a, &b, &c);
  while(a != -1) {
     printf("Original inputs:   a:%-4d  b:%-4d  c:%-4d\n", a, b, c);
     swapIncres(&a, &b, &c);
     printf("Rearranged inputs: a:%-4d  b:%-4d  c:%-4d\n\n", a, b, c);

     /* to read again */
     scanf("%d %d %d", &a, &b, &c);
   }

}

//swaps, and also increments variables a,b,c
void swapIncres(int *x, int *y, int *z){
   swap(&x, &z);
   *x += 100;
   *y *= 2;
}

void swap(int **p, int **q){
   int *t = *p;
   *p = *q;
   *q = t;
}
