/***************************************
* 25s - Lab4 *
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

import java.util.Scanner;

public class Hello {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in); // or use BufferedReader or Console

        System.out.print("Please enter your name: ");
        String name = scan.next();

        System.out.print("Please enter an integer number: ");
        int a = scan.nextInt();

        int b = a * 3;
        int c = a * 4;

        System.out.println("Hi " + name + ", you entered " + a + ".");
        System.out.printf("Triple and quadruple of %d are %d and %d, respectively.\n", a, b, c);

        scan.close();
    }
}
