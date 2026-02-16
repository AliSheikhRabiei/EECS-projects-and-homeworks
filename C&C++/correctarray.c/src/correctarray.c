/***************************************
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <assert.h>
#include <sys/types.h>
#include <sys/stat.h>
//#include <sys/wait.h>


int a[30]; // the global array
unsigned long faulty_array(int n){
    int i;
    unsigned long sum = 0;
    //checking n just in case
    int limit;
    if (n < 0) {
        limit = 0;
    } else {
        limit = n;
    }

    int max   = (int)(sizeof a / sizeof a[0]);

    if (limit > max) limit = max;

    for (i = 0; i < limit; i++) {
        sum = sum + a[i];
    }
    return sum;
}

int main(void) {
    printf("Hello world\n");
    long int s;
    s = faulty_array(100000);
    printf("Faulty array sum:%ld\n", s);
//    scanf("press anything to exit");
    return 0;
}



