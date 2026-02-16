package console_apps;

import java.util.Scanner;

import model.Product;

public class ProductApp {

    public static void main(String[] args) {
    	
        Scanner input = new Scanner(System.in);
        //i just copied everything on the video here
        
        Product p = new Product();
        System.out.println(p); 

        Product p2 = new Product("iPad Pro 12.9", 1709.00);
        System.out.println(p2);

        //idk why i typed these if they are going to stay comment like this
        // System.out.println("Enter a model:");
        // String model = input.nextline();
        // System.out.println("Enter the original price:");
        // double op = input.nextDouble();
        // Product p3 = new Product(model, op);
        // System.out.println(p3);

        input.close();
    }
}
