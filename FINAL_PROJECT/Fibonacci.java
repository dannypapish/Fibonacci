package FINAL_PROJECT;

import java.util.Scanner;
import java.util.ArrayList;

import ch07.trees.BinarySearchTree;
import support.*;

public class Fibonacci{

      ArrayList<Integer> seq = new ArrayList<Integer>(); //arraylist initialization
      BinarySearchTree<Integer> fibTree;
         
      private int n;                // n hold the current n term
      private int nOne;             // nOne holds n-1 term
      private int nTwo;             // nTwo holds n-2 term        
      int swap;                     // Global variable for transforming terms     
      int i = 0;
      
      public Fibonacci() {
      
         nOne = 1;                  // First n-1 natural Fibonacci term is always 1
         nTwo = 1;                  // First n-2 natural Fibonacci term is always 1
         seq.add(0, nTwo);          // Adds n-2 term to first array index
         seq.add(1, nOne);          // Adds n-1 term to second array index
         
         IFibGen(nOne, nTwo);
      }
      
      public Fibonacci(int firstIn, int secondIn) {
      // Accepts 2 inputs and generates fibonnaci sequence from those points on
      
         nOne = secondIn;           // n-1 term equals second input
         nTwo = firstIn;            // n-2 term equals first input
         seq.add(nTwo);             // Adds n-2 term to first array index
         seq.add(nOne);             // Adds n-1 term to second array index
         RecFibGen(nOne, nTwo); 
      }
      
      
      public ArrayList IFibGen(int n1, int n2) {           
      // Method responsible for generating fibonnaci sequences to 15 terms
   
         for(int i = 0; i < 44; i++ ) {
            
            n = n1 + n2;            
            seq.add(i+2, n);
            
            swap = n1;
            n2 = swap;
            swap = n;
            n1 = swap;
         
         }
         return seq;
      }
      
      public ArrayList RecFibGen (int n1, int n2) {
      // Method to recursively generate Fibonacci sequence from given terms

      n = n1 + n2;
      swap = n1;
      n2 = swap;
      seq.add(n);
      
      while(i < 12)
      {
      
         i++;
         RecFibGen(n, n2);
                  
      }
      return seq;
      }
      
      public String printFwrd(){
      // Method that prints the sequence ArrayList lowest integer to highest
         
         String retString = "";
         
         retString = seq.toString();
         
         return retString;
      }
      
      public String printBack(){
      // Method that prints sequence ArrayList highest integer to lowest
      
         LinkedStack pStack = new LinkedStack<Integer>();  //stack initialization
         String retString = "";
         
         for (int value : seq){
            pStack.push(value);
         }
         
         int size = pStack.size();
         
         for (int i = 0; i < size; i++){
            retString += pStack.popTop() + ", ";
         }

         retString = retString.substring(0, retString.length() - 2);
         retString = "[" + retString + "]";
         
         return retString;
      }
      
      private void treeGen(){
      // generates
      
         fibTree = new BinarySearchTree<Integer>();
         recTreeGen(0, seq.size() - 1);
         
      }
      
      private void recTreeGen(int low, int high){

         if (low > high) 
            return;
            
                    
         int mid = (low + high)/2;
         fibTree.add(seq.get(mid));
         

         
         recTreeGen(low, mid - 1);
         recTreeGen(mid + 1, high);
      }
      public int search (int target) {
      
         if (fibTree == null)
            treeGen();
         int index;
            
         if (fibTree.contains(target)) {
            index = seq.indexOf(target);
            return index;   
         }
            return index = -1;
      }
      

      
      
   
   public static void main(String[] args){
   
      
      System.out.println("Welcome to Fibonacci\n ");
      //System.out.println("Please enter a number 0 to 100");
      Scanner keyboard = new Scanner(System.in);
      
      //int firstIn = keyboard.nextInt();
      //int secondIn = keyboard.nextInt();
      
      Fibonacci test = new Fibonacci();
      
      System.out.println(test.printFwrd());
      System.out.println(test.printBack());
      System.out.println(test.search(1));
      
      
      
      
      
      
      
   
   }

}