/*
 ============================================================================
 Name        : lab5B.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int c;

    while ((c = getchar()) != EOF) {

        if (c >= 'a' && c <= 'z') {
            putchar(c - ('a' - 'A'));

        } else if (c >= '0' && c <= '9') {
            if (c < '5')
                putchar('-');
            else if (c > '5')
                putchar('+');
            else
                putchar(c);

        } else {
            putchar(c);
        }
    }

    return 0;
}
