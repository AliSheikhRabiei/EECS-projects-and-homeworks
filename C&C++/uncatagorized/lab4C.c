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

float sum(float a, float b) {
    return a + b;
}
int main() {
    greet(2031);
    greet(1012);
    printf("%f + %f = %f\n", 2.2, 3.3, sum(2.2, 3.3));
    return 0;
}
