//your information here
/***************************************
* 25s - Lab06 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>
#include <string.h>

#define SIZE 56
// more headers, declarations, as needed
void sortArr (char * arr);   /* bubble sort, pointer style, no [] */
void sortArr2 (char * arr);  /* selection sort, pointer style, no [] */

int main()
{
   char arr[SIZE]; char arrB[SIZE];
   fgets(arr,SIZE,stdin);
   while ( strcmp(arr,"quit\n") != 0 )
   {
//       ... // manually remove (replace) the trailing \n in the first place

       char *p = arr;
       while (*p && *p != '\n') p++;
       if (*p == '\n') *p = '\0';

       strcpy(arrB, arr);

       sortArr(arr);
       printf("%s\n", arr);

       sortArr2(arrB);
       printf("%s\n\n", arrB);

       //....
       if (!fgets(arr,SIZE,stdin)) break;
    }
   return 0;
}

// two sorting function definitions
void sortArr (char * arr){
    // bubble sort
    int n = 0;
    char *t = arr;
    while (*t++) n++;
    if (n < 2) return;

    for (int i = 0; i < n-1; i++){
        for (int j = n-1; j > i; j--){
            char *a = arr + j;
            char *b = arr + j - 1;
            if (*a < *b){
                char tmp = *a;
                *a = *b;
                *b = tmp;
            }
        }
    }
}


void sortArr2 (char * arr){
    // selection sort
    int n = 0;
    char *t = arr;
    while (*t++) n++;
    if (n < 2) return;

    for (int i = 0; i < n-1; i++){
        int smallest = i;
        for (int j = i+1; j < n; j++){
            if (*(arr + j) < *(arr + smallest))
                smallest = j;
        }
        if (smallest != i){
            char *pi = arr + i;
            char *ps = arr + smallest;
            char tmp = *pi;
            *pi = *ps;
            *ps = tmp;
        }
    }
}

