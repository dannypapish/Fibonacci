package FINAL_PROJECT;

import java.util.Scanner;


public class FibonacciDriver {


   public static void main (String[] args) {      

      Fibonacci test = null;
      System.out.println("Welcome to Fibonacci\n");

      Scanner keyboard = new Scanner(System.in);
      
      int testType;
      boolean stop = false;
      
      System.out.println("Please choose the constructor to be used \n" + 
                         "\t[1] Fibonacci()\n" +
                         "\t[2] Fibonacci(n1, n2) where n1 < n2 \n");      
      
      testType = keyboard.nextInt();
      
      switch (testType) {
         case 1: 
            test = new Fibonacci();            
            break;
         
         case 2:         
            System.out.println("Please enter two positive integers where n1 > n2");
            
            System.out.println("\tPlease input the first integer n1");
            int firstIn = keyboard.nextInt();
            
            System.out.println("\tPlease input the second integer n2");
            int secondIn = keyboard.nextInt();
            
            test = new Fibonacci(firstIn, secondIn);
            break;   
      }
      do{
      
         System.out.println("Please enter an integer to choose which method to test.\n" +
                            "\t[1] printFwrd()\n"+
                            "\t[2] printBack()\n"+
                            "\t[3] search(target)\n"+
                            "\t[4] STOP\n");         
                   
         testType = keyboard.nextInt();
         
         switch (testType) {       
            
            case 1:
               System.out.println(test.printFwrd() + "\n");
               break;
               
            case 2:
               System.out.println(test.printBack() + "\n");
               break;
               
            case 3:
               System.out.println("Please enter an integer to be searched for in Fibonacci's set");
               int target = keyboard.nextInt();
               System.out.println(test.search(target));
               break;
               
            case 4:
               stop = true;
               break;
            }
      }while (stop == false);
   }
}