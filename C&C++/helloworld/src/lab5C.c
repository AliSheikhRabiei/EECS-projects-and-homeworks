/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

int main(void) {
    int counts[10] = {0};
    int others = 0;
    int intput;

    while ((intput = getchar()) != EOF) {
        if (intput >= '0' && intput <= '9') {
            counts[intput - '0']++;
        } else {
            others++;
        }
    }
    for (int i = 0; i < 10; i++) {
        printf("%d: %d\n", i, counts[i]);
    }
    printf("X: %d\n", others);
    return 0;
}
