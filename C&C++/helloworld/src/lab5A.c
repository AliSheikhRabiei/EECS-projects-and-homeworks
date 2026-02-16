/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

int isDigit(char c);
int isLetter(char c);
int isOperator(char c);

int main(void){
	int c1;
	char c2;
	while(1){
		printf("Enter an integer and a character separated by blank: ");
		scanf("%d %c",&c1, &c2);
		if(c1==-10000)
			break;

		if(isDigit(c2)==1){
			int digit_value=c2-'0';
			int sum=c1+digit_value;
			printf("Character '%c' represents a digit. Sum of %d and %c is %d \n",c2,c1,c2, sum);
		}
		else if(isLetter(c2)==1){
			printf("Character '%c' represents a Letter \n",c2);
		}
		else if(isOperator(c2)){
			printf("Character '%c' represents an Operator \n",c2);
		}
		else{
			printf("Character '%c' represents a Others \n",c2);
		}
	}
	return 0;
}

int isDigit(char c){
	if(c>='0'&&c<='9'){
		return 1;
	}
	return 0;
}
int isLetter(char c){
	if((c>='a'&&c<='z')||((c>='A'&&c<='Z'))){
		return 1;
	}
	return 0;
}
int isOperator(char c){
	if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%'){
		return 1;
	}
	return 0;
}

