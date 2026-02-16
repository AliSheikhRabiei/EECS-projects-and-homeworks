/***************************************
* 25s - Lab4 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

void greet(int i) {
    printf("Hello %d!\n", i);
}

int main() {
    int x;
    printf("Please enter an integer number: ");
    scanf("%d", &x);
    printf("Hi, you entered %d. Triple and quadruple of %d are %d and %d, respectively.\n", x, x, x*3, x*4);
    return 0;
}
