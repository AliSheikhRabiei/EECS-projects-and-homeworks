/***************************************
* 25s - Lab5 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <stdio.h>

#define AlphaV 100
#define SIZE 32

void printBinary(int n);

int main() {

  int r, g,b;
  unsigned int rgb_pack;
  int r_unpack, g_unpack,b_unpack;
  int alpha = AlphaV;

  printf("enter R value (0~255): ");
  scanf("%d",&r);
  printf("enter G value (0~255): ");
  scanf("%d",&g);
  printf("enter B value (0~255): ");
  scanf("%d",&b);

  while(! (r<0 || g<0 || b <0) )
  {
     printf("A: %d\t", alpha);  printBinary(alpha); printf("\n");
     printf("R: %d\t", r);  printBinary(r); printf("\n");
     printf("G: %d\t", g);  printBinary(g); printf("\n");
     printf("B: %d\t", b);  printBinary(b); printf("\n");

    /* now do packing for Alpha,Red,Green,Blue, */

     rgb_pack = ( (unsigned)alpha << 24 )
              | ( (unsigned)r     << 16 )
              | ( (unsigned)g     <<  8 )
              | ( (unsigned)b        );


     //printf("\nPacked: value %d\t", rgb_pack); printBinary(rgb_pack);printf("\n");
     printf("\nPacked:\t"); printBinary(rgb_pack);printf(" (%d)\n", rgb_pack);

     printf("\nUnpacking  ......\n");
     r=g=b=-1;

    /* now do unpacking */

     r_unpack = (rgb_pack >> 16) & 0xFF;
     g_unpack = (rgb_pack >>  8) & 0xFF;
     b_unpack =  rgb_pack         & 0xFF;




     printf("R: "); printBinary(r_unpack); printf(" (%d, %#o, %#X)\n", r_unpack, r_unpack, r_unpack);
     printf("G: "); printBinary(g_unpack); printf(" (%d, %#o, %#X)\n", g_unpack, g_unpack, g_unpack);
     printf("B: "); printBinary(b_unpack); printf(" (%d, %#o, %#X)\n", b_unpack, b_unpack, b_unpack);
     printf("------------------------------------\n");

     printf("enter R value (0~255): ");
     scanf("%d",&r);
     printf("enter G value (0~255): ");
     scanf("%d",&g);
     printf("enter B value (0~255): ");
     scanf("%d",&b);

  }

}

//added here just for testing since it was hard to add both files again and again to remote lab and my laptop
//void printBinary(int n ){
//        printf("binary: ");
//        int cou = SIZE-1;
//        int k[SIZE]={0};
//
//        while (n && cou >=0) {
//             if (n & 1) // != 0
//                  k[cou]=1; //printf("1");
//             else
//                  k[cou]=0; //printf("0");
//
//             n >>= 1;
//             cou--;
//        }
//        int i=0;
//        for(; i< SIZE;i++)
//        {
//            printf("%d", k[i]);
//            if ((i+1)%8==0)
//            {
//                printf(" ");
//            }
//        }
//
//}
