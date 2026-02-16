// your information goes here
/***************************************
* 25s - Lab06 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/


/* Reads in a list of strings from the keyboard, then re-organize them */
 /* and then displays them on the screen. */

 #include <stdlib.h>
 #include <stdio.h>
 #include <string.h>

 #define MAX_LINES 34
 #define MAX_COLS  54

void exchange2D(char p[][MAX_COLS], int n);
void print2D(char p[][MAX_COLS], int n);

//if then why no int, if void then why return?!
 int main()
 {
     char inputs[MAX_LINES][MAX_COLS];
     int n = 0;

     printf("sizeof inputs: %d\n\n", sizeof inputs);

     /* Read in the lines from the stdin (keyboard) now, using function fgets() */
//     ....

     printf("Enter string: ");

     while (n < MAX_LINES && fgets(inputs[n], MAX_COLS, stdin))
     {
         size_t len = strlen(inputs[n]);
         if (len > 0 && inputs[n][len-1] == '\n')
             inputs[n][len-1] = '\0';

         if (strcmp(inputs[n], "xxx") == 0)
             break;

         n++;
         if (n < MAX_LINES)
             printf("Enter string: ");
     }

     printf("\n");

     // displays the array by calling sub-function print2D(...)
     print2D(inputs, n);
     // swaps the first and second row here
     if (n >= 2) {
         char temp[MAX_COLS];
         strcpy(temp, inputs[0]);
         strcpy(inputs[0], inputs[1]);
         strcpy(inputs[1], temp);
     }
     // calls sub-function exchange2D() to swap some other rows
     if (n > 2){
         exchange2D(inputs, n);
     }

     printf("\n== after swapping ==\n");

     // displays the (exchanged array) by calling sub-function print2D()

     print2D(inputs, n);

     //    scanf("\n enter to exit\n");

     return 0;
 }


 // Exchange of rows. need to involve data movement
 //why in the sample files you give us the names are worng! i was wondering for half and hour looking for problem in other parts of the code
 void exchange2D(char p[][MAX_COLS], int n)
 {
     char temp[MAX_COLS];
     for (int i = 2; i + 1 < n; i += 2) {
         strcpy(temp,   p[i]);
         strcpy(p[i],   p[i+1]);
         strcpy(p[i+1], temp);
     }
 }

 // output the 2D array, row by row
 //this drove me crazy!
 void print2D(char p[][MAX_COLS], int n)
 {
     for (int i = 0; i < n; i++){
         printf("[%d]: %s\n", i, p[i]);
     }
 }

