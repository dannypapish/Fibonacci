//----------------------------------------------------------------------
// LinkedStack.java         by Dale/Joyce/Weems                Chapter 2
//
// Implements StackInterface using a linked list to hold the elements.
//-----------------------------------------------------------------------

package FINAL_PROJECT;

import support.LLNode;
import ch02.stacks.StackInterface;
import ch02.stacks.StackOverflowException;
import ch02.stacks.StackUnderflowException;

public class LinkedStack<T> implements StackInterface<T>
{
  protected LLNode<T> top;   // reference to the top of this stack
  protected LLNode<T> temp;  // temporary reference list to traverse without affecting stack
  

  public LinkedStack()
  {
    top = null;
  }

  public void push(T element)
  // Places element at the top of this stack.
  { 
    LLNode<T> newNode = new LLNode<T>(element);
    newNode.setLink(top);
    top = newNode;
  }     

  public void pop()
  // Throws StackUnderflowException if this stack is empty,
  // otherwise removes top element from this stack.
  {                  
    if (isEmpty())
      throw new StackUnderflowException("Pop attempted on an empty stack.");
    else
      top = top.getLink();
  }

  public T top()
  // Throws StackUnderflowException if this stack is empty,
  // otherwise returns top element of this stack.
  {                 
    if (isEmpty())
      throw new StackUnderflowException("Top attempted on an empty stack.");
    else
      return top.getInfo();
  }

  public boolean isEmpty()
  // Returns true if this stack is empty, otherwise returns false.
  {              
    return (top == null); 
  }

  public boolean isFull()
  // Returns false - a linked stack is never full
  {              
      return false;
  }

  public String toString()
  // Returns the current state of the stack as a string
  // Returns "The stack is empty" if empty 
  {
      String returnString = "";     // String to represent the current state of the stack

      try
      {      
         temp = top;
         returnString = "" + temp.getInfo();
         int size = 1;                          // Integer to keep track of the size of the stack for toString
       
         
         while (temp.getLink() != null) 
         {
            temp = temp.getLink();    
            returnString = returnString + " " + temp.getInfo();
            size++;
         }
            returnString ="The current stack size is: " + size + "\n" + returnString;
         
         return returnString;
      }
      catch (NullPointerException e)
      {
         System.out.println("The stack is empty");
         return returnString;
      }
  }
  
  public int size()  
  // Method to determine size of the stack
  // Returns 0 for no stack
  {
      temp = top;
      int count = 0;
      
      while (temp != null)
      {
      temp = temp.getLink();
      count++;
      }
      return count;
  }
  
  public void popSome(int count) 
  // Throws StackUnderflowException if the count is higher than the size of stack 
  // otherwise pops the provided amount of times 
  {
         
         try
         {
            for (int i = count; i > 0; i--)
            {
               top = top.getLink();
            }
         }
         catch (NullPointerException e)
         {
            throw new StackUnderflowException("Top attempted on an empty stack");
         }

  }
  
  public boolean swapStart()
  // Returns false if there are less 2 elements on the stack
  // otherwise swaps the top two elements and returns true 
  {
      boolean i = false;
      
      try
      {      
         LLNode<T> swap1 = new LLNode<T>(top.getInfo());
         top = top.getLink();
         
         LLNode<T> swap2 = new LLNode<T>(top.getInfo());
         top = top.getLink();
         
         swap1.setLink(top);
         top = swap1;   
         
         swap2.setLink(top);
         top = swap2;   
         
         i = true;
         
         
         return i;
      }
      catch (NullPointerException e)
      {
         return i;
      }
  }
  
  public T popTop()
  // Throws StackUnderflowException if this stack is empty
  // otherwise returns the value of the top index and removes it
  {
      
      T returnVar;
      try
      {
         returnVar = top.getInfo();
         top = top.getLink();   
         
         return returnVar;
      }
      catch (NullPointerException e)
      {
         throw new StackUnderflowException("Pop attempted on an empty stack");
      }
  }
}

