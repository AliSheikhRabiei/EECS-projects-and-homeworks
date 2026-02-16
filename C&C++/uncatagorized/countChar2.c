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
    char c;
    int count = 0;

    printf("Enter characters (Ctrl+D to end):\n");
    while ((c = getchar()) != EOF) {
        count++;
    }

    printf("You entered %d characters.\n", count);
    return 0;
}
