/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/
#include <stdio.h>
#include <stdlib.h>  // for atoi
#include <string.h>

#define SIZE 14

int isQuit (char arr[]);
int my_atoi (char c[]);

int main(){
  int a,b;
  char arr [SIZE];

  printf("Enter a word of positive number or 'quit': " );
  scanf("%s", arr);
  while(isQuit(arr))
  {
    printf("%s\n", arr);

    a = atoi(arr);
    printf("atoi:    %d (%#o, %#X)\t%d\t%d\n", a,a,a, a*2, a*a);

    b = my_atoi(arr);
    printf("my_atoi: %d (%#o, %#X)\t%d\t%d\n", b,b,b, b*2, b*b);

    printf("Enter a word of positive number or 'quit': " );
    scanf("%s", arr);

  }

  return 0;

}

/* converts an array of (digit) characters into a decimal value*/

/* Recommended book K&R scans from left to right.
 Here you should scan from RIGHT to LEFT. This is a little more complicated
 but more straightforward approach (IMHO) */

int my_atoi (char c[])
{
    int len = strlen(c);
    int result = 0;
    int place = 1;
    for (int i = len - 1; i >= 0; i--) {
        result += (c[i] - '0') * place;
        place *= 10;
    }
    return result;

}

int isQuit (char arr[])
{
 int i;
 if (arr[0]=='q' && arr[1]=='u' && arr[2]=='i' && arr[3]=='t')
    return 0;
 else
    return 1;
}
