//program for my_strcat.c
//your information here
/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/


#include<stdio.h>
//include string library header files
#include<string.h>

//more declarations as needed
void my_strcat(char des[], char src[]);

#define SIZES 35

int main(){
   char a[SIZES];
   char b[SIZES];
   char c[SIZES];
   char d[SIZES];

   scanf("%s",a);
   scanf("%s",b);
   while (!(strcmp(a,"xxx")==0 && strcmp(b,"xxx")==0)){

      strcpy(c,a);
      strcpy(d,b);

      strcat(a,b);
      my_strcat(c,d);

      printf("strcat:   %s\n", a);
      printf("mystrcat: %s\n\n", c);

      scanf("%s",a);
      scanf("%s",b);
   }
}

//your version of strcat implementation here
void my_strcat(char des[], char src[]){
    int i = 0;
    int j = 0;
    while (des[i] != '\0')
    	i++;
    while (1){
    	des[i]=src[j];
    	if(src[j]=='\0'){
    		break;
    	}
    	i++;
    	j++;
    }

}
