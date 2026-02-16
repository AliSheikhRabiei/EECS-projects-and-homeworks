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
    int i, times;
    float a, b;
    greet(2031);
    greet(1012);
    printf("Enter the number of interactions: ");
    scanf("%d", &times);
    for (i = 0; i < times; i++) {
        printf("\nEnter two float numbers separated by ##: ");
        scanf("%f##%f", &a, &b);
        printf("%.3f + %.3f = %f (%.2f)\n", a, b, a + b, a + b);
    }
    return 0;
}
