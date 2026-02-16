/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

int main(void) {
    int c;
    while((c=getchar()) != EOF){
    	if(c>='a'&&c<='z'){
    		putchar(c+('A'-'a'));
    	}
    	else if(c>='0'&&c<='4'){
    		putchar('-');
    	}
    	else if(c>='6'&&c<='9'){
    		putchar('+');
    	}
    	else{
    		putchar(c);
    	}
    }

    return 0;
}
