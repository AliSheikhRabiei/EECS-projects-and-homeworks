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
    float a, b;
    greet(2031);
    greet(1012);
    printf("Enter two float numbers separated by ##: ");
    scanf("%f##%f", &a, &b);
    printf("%f + %f = %f\n", a, b, a + b);
    return 0;
}
