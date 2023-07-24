//---------------------------------------------------------------------------
// BinarySearchTree.java          by Dale/Joyce/Weems               Chapter 7
//
// Defines all constructs for a reference-based BST.
// Supports three traversal orders Preorder, Postorder & Inorder ("natural")
//---------------------------------------------------------------------------

package Week10;
import java.util.Random;
import java.util.*;   // Iterator, Comparator

//import ch04.queues.*;
//import ch02.stacks.*;

import Week9.support.*;


public class BinarySearchTree<T> implements BSTInterface<T>
{
  protected BSTNode<T> root;      // reference to the root of this BST
  protected Comparator<T> comp;   // used for all comparisons

  protected boolean found;   // used by remove

  public BinarySearchTree() 
  // Precondition: T implements Comparable
  // Creates an empty BST object - uses the natural order of elements.
  {
    root = null;
    comp = new Comparator<T>()
    {
       public int compare(T element1, T element2)
       {
         return ((Comparable)element1).compareTo(element2);
       }
    };
  }

  public BinarySearchTree(Comparator<T> comp) 
  // Creates an empty BST object - uses Comparator comp for order
  // of elements.
  {
    root = null;
    this.comp = comp;
  }

  public boolean isFull()
  // Returns false; this link-based BST is never full.
  {
    return false;
  }

  public boolean isEmpty()
  // Returns true if this BST is empty; otherwise, returns false.
  {
    return (root == null);
  }

  public T min()
  // If this BST is empty, returns null;
  // otherwise returns the smallest element of the tree.
  {
    if (isEmpty())
       return null;
    else
    {
       BSTNode<T> node = root;
       while (node.getLeft() != null)
         node = node.getLeft();
       return node.getInfo();
    }
  }

  public T max()
  // If this BST is empty, returns null;
  // otherwise returns the largest element of the tree.
  {
    if (isEmpty())
       return null;
    else
    {
       BSTNode<T> node = root;
       while (node.getRight() != null)
         node = node.getRight();
       return node.getInfo();
    }
  }

  private int recSize(BSTNode<T> node)
  // Returns the number of elements in subtree rooted at node.
  {
    if (node == null)    
      return 0;
    else
      return 1 + recSize(node.getLeft()) + recSize(node.getRight());
  }

  public int size()
  // Returns the number of elements in this BST.
  {
    return recSize(root);
  }

  public int size2()
  // Returns the number of elements in this BST.
  {
    int count = 0;
    if (root != null)
    {
      LinkedListBoundedStack<BSTNode<T>> nodeStack = new LinkedListBoundedStack<BSTNode<T>>();
      BSTNode<T> currNode;
      nodeStack.push(root);
      while (!nodeStack.isEmpty())
      {
        currNode = nodeStack.top();
        nodeStack.pop();
        count++;
        if (currNode.getLeft() != null)
          nodeStack.push(currNode.getLeft());
        if (currNode.getRight() != null)
          nodeStack.push(currNode.getRight());
      }
    }
    return count;
  }

  private boolean recContains(T target, BSTNode<T> node)
  // Returns true if the subtree rooted at node contains info i such that 
  // comp.compare(target, i) == 0; otherwise, returns false.
 {
    if (node == null)
      return false;       // target is not found
    else if (comp.compare(target, node.getInfo()) < 0)
      return recContains(target, node.getLeft());   // Search left subtree
    else if (comp.compare(target, node.getInfo()) > 0)
      return recContains(target, node.getRight());  // Search right subtree
    else
      return true;        // target is found
  }

  public boolean contains (T target)
  // Returns true if this BST contains a node with info i such that 
  // comp.compare(target, i) == 0; otherwise, returns false.
  {
    return recContains(target, root);
  }

  
  private T recGet(T target, BSTNode<T> node)
  // Returns info i from the subtree rooted at node such that 
  // comp.compare(target, i) == 0; if no such info exists, returns null.
  {
    if (node == null)
      return null;             // target is not found
    else if (comp.compare(target, node.getInfo()) < 0)
      return recGet(target, node.getLeft());         // get from left subtree
    else
    if (comp.compare(target, node.getInfo()) > 0)
      return recGet(target, node.getRight());        // get from right subtree
    else
      return node.getInfo();  // target is found
  }

  public T get(T target)
  // Returns info i from node of this BST where comp.compare(target, i) == 0;
  // if no such node exists, returns null.
  {
    return recGet(target, root);
  }

  private BSTNode<T> recAdd(T element, BSTNode<T> node)
  // Adds element to tree rooted at node; tree retains its BST property.
  {
    if (node == null)
      // Addition place found
      node = new BSTNode<T>(element);
    else if (comp.compare(element, node.getInfo()) <= 0)
      node.setLeft(recAdd(element, node.getLeft()));    // Add in left subtree
    else
      node.setRight(recAdd(element, node.getRight()));   // Add in right subtree
    return node;
  }

  public boolean add (T element)
  // Adds element to this BST. The tree retains its BST property.
  {
    root = recAdd(element, root);
    return true;
  }

/*
  public boolean add (T element)
  // Adds element to this BST. The tree retains its BST property.
  {
    BSTNode<T> newNode = new BSTNode<T>(element);
    BSTNode<T> prev = null, curr = null;
    
    if (root == null)
      root = newNode;
    else
    {
      curr = root;
      while (curr != null)
      {
        if (comp.compare(element, curr.getInfo()) <= 0)
        {
          prev = curr;
          curr = curr.getLeft();
        }
        else
        {
          prev = curr;
          curr = curr.getRight();
        }
      }
      if (comp.compare(element, prev.getInfo()) <= 0)
        prev.setLeft(newNode);
      else
        prev.setLeft(newNode);
    }
    return true;
  }
*/

  private T getPredecessor(BSTNode<T> subtree)
  // Returns the information held in the rightmost node of subtree
  {
    BSTNode<T> temp = subtree;
    while (temp.getRight() != null)
      temp = temp.getRight();
    return temp.getInfo();
  }

  private BSTNode<T> removeNode(BSTNode<T> node)
  // Removes the information at node from the tree. 
  {
    T data;
    if (node.getLeft() == null)
      return node.getRight();
    else if (node.getRight() == null) 
      return node.getLeft();
    else
    {
      data = getPredecessor(node.getLeft());
      node.setInfo(data);
      node.setLeft(recRemove(data, node.getLeft()));  
      return node;
    }
  }

  private BSTNode<T> recRemove(T target, BSTNode<T> node)
  // Removes element with info i from tree rooted at node such that
  // comp.compare(target, i) == 0 and returns true; 
  // if no such node exists, returns false. 
  {
    if (node == null)
      found = false;
    else if (comp.compare(target, node.getInfo()) < 0)
      node.setLeft(recRemove(target, node.getLeft()));
    else if (comp.compare(target, node.getInfo()) > 0)
      node.setRight(recRemove(target, node.getRight()));
    else  
    {
      node = removeNode(node);
      found = true;
    }
    return node;
  }

  public boolean remove (T target)
  // Removes a node with info i from tree such that comp.compare(target,i) == 0
  // and returns true; if no such node exists, returns false.
  {
    root = recRemove(target, root);
    return found;
  }

  public Iterator<T> getIterator(BSTInterface.Traversal orderType)
  // Creates and returns an Iterator providing a traversal of a "snapshot" 
  // of the current tree in the order indicated by the argument.
  // Supports Preorder, Postorder, and Inorder traversal.
  {
    final LinkedQueue<T> infoQueue = new LinkedQueue<T>();
    if (orderType == BSTInterface.Traversal.Preorder)
      preOrder(root, infoQueue);
    else
    if (orderType == BSTInterface.Traversal.Inorder)
      inOrder(root, infoQueue);
    else
    if (orderType == BSTInterface.Traversal.Postorder)
      postOrder(root, infoQueue);

    return new Iterator<T>()
    {
      public boolean hasNext()
      // Returns true if the iteration has more elements; otherwise returns false.
      {
        return !infoQueue.isEmpty();
      }
      
      public T next()
      // Returns the next element in the iteration.
      // Throws NoSuchElementException - if the iteration has no more elements
      { 
        if (!hasNext())
          throw new IndexOutOfBoundsException("illegal invocation of next " + 
                                     " in BinarySearchTree iterator.\n");
        return infoQueue.dequeue();
      }

      public void remove()
      // Throws UnsupportedOperationException.
      // Not supported. Removal from snapshot iteration is meaningless.
      {
        throw new UnsupportedOperationException("Unsupported remove attempted on " 
                                              + "BinarySearchTree iterator.\n");
      }
    };
  }

  private void preOrder(BSTNode<T> node, LinkedQueue<T> q)
  // Enqueues the elements from the subtree rooted at node into q in preOrder.
  {
    if (node != null)
    {
      q.enqueue(node.getInfo());
      preOrder(node.getLeft(), q);
      preOrder(node.getRight(), q);
    }
  }

  private void inOrder(BSTNode<T> node, LinkedQueue<T> q)
  // Enqueues the elements from the subtree rooted at node into q in inOrder.  
  {
    if (node != null)
    {
      inOrder(node.getLeft(), q);
      q.enqueue(node.getInfo());
      inOrder(node.getRight(), q);
    }
  }

  private void postOrder(BSTNode<T> node, LinkedQueue<T> q)
  // Enqueues the elements from the subtree rooted at node into q in postOrder.  
  {
    if (node != null)
    {
      postOrder(node.getLeft(), q);
      postOrder(node.getRight(), q);
      q.enqueue(node.getInfo());
    }
  }
  
  public Iterator<T> iterator()
  // InOrder is the default, "natural" order.
  {
    return getIterator(BSTInterface.Traversal.Inorder);
  }
  int heightIterative()
  {
	  BSTNode<T> currNode = root;
	  LinkedListBoundedStack<BSTNode<T>> nodes = new LinkedListBoundedStack<BSTNode<T>>();
	  LinkedListBoundedStack<Integer> level = new LinkedListBoundedStack<Integer>();
	  
	  nodes.push(currNode);
	  level.push(0);
	  
	  int i = 0;
	  int maxHeight = 0;
	  while(!nodes.isEmpty()) {
		  
		  if(currNode.getLeft() != null) {
			  nodes.push(currNode.getLeft());
			  level.push(i+1);
		  }
		  if(currNode.getRight() != null) {
			  nodes.push(currNode.getRight());
			  level.push(i+1);
		  }
		  
		  currNode=nodes.poptop();
		  i=level.poptop();
		  if(i > maxHeight) {
			  maxHeight = i;
		  }
	  }
	  return ++maxHeight;
  }
  

  int heightRec()
  {
	  return heightRecHelper(root);
  }
  
  int heightRecHelper(BSTNode node)
  {
	  if (node == null)
	  return 0;
	  int lDepth = heightRecHelper(node.getLeft());
	  int rDepth = heightRecHelper(node.getRight());
    
	  if (lDepth > rDepth) {
		  return (lDepth + 1);
	  }
	  else {
		  return (rDepth + 1);
	  }
  }
  double fratio() {

	return Math.ceil( ( (Math.log10(size())/Math.log10(2)) +1)) / heightRec();
  }
  // The way this method works - we first find the optimal height
  // (Minimum height) of the
  // tree by using Log base 2(number of nodes). I had to use to change of 
  // base formula, since java doesn't let us use a Log base 2 formula. We
  // used the Math.ceil function to round the result of the the optimal
  // height, since we will not always have a perfect tree(where each leaf
  // has right and left leafs. then we devide it by the maximum height.

  


	public static void main(String[] args) {
		BinarySearchTree test = new BinarySearchTree<Integer>();
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		test.add(1);
		
		System.out.println("Recursive: " + test.heightRec());
		System.out.println("Iterative: " + test.heightIterative());
		System.out.println("fratio: " + test.fratio());
		
		LinkedList<BinarySearchTree<Integer>> treeList = new LinkedList<>();
		for(int i = 0; i<10; i++) {
			treeList.add(new BinarySearchTree<Integer>());//generating 10 trees
		}
		
		for(BinarySearchTree<Integer> t : treeList) {//enhanced for loop generating 1,000 nodes for each tree, each node contains numbers between 1 and 3,000 by using a math.random() fucnction which generates a random number between 0 to 1, then we multiply it by 2,999 and add 1. we always round it up using (int)
			for(int i = 0; i < 1000; i++) {
				t.add((int)(2999*Math.random())+1);
			}
		}
		
		int i = 0;
		for(BinarySearchTree<Integer> t : treeList) {//using enhanced for loop for printing out the results
			System.out.println("Tree #" + i + ": ");// fot trees 1-10
			System.out.println("\tHeight: " + t.heightRec());//presenting the height
			System.out.println("\tOptimal Height: " + t.fratio()*t.heightRec());//optimal will be basically doing the reverse action of the fratio function. we are multiplying the max height by the fration and get the minumum height which gives the minimum height, which is basically the most balanced way to arrange a tree and therefore is the most balanced tree
			System.out.println("\tfratio: " + t.fratio());//presenting fratio
			i++;
		}
		
		System.out.println("\nBiased trees:\n");
		
		treeList = new LinkedList<>();
		for(i = 0; i<10; i++) {
			treeList.add(new BinarySearchTree<Integer>());//creating a linked list of 10 binary trees
		}
		
		double k = 0.0;
		for(BinarySearchTree<Integer> t : treeList) {
			for (i = 0; i < 1000; i++) {
				if (Math.random() < (1.0-k)) {
					t.add((int) (2999 * Math.random()) + 1);
				}else{
					t.add(42);
				}
			} 
			k = k+0.1;
		}
		//k starts at 0 while math.random is smaller than 1.0-k which starts being 100% and is decreasing by 10% with each iteration of the loop, we will add a random number between 1 and 3,000. otherwise we will add 42. since there are 10 trees, and the k is increased by 10% with each iteration, it averages out so there is a k% chance for adding 42
		
		i = 0;
		for(BinarySearchTree<Integer> t : treeList) {
			System.out.println("Tree #" + i + ": ");
			System.out.println("\tHeight: " + t.heightRec());
			System.out.println("\tOptimal Height: " + t.fratio()*t.heightRec());
			System.out.println("\tfratio: " + t.fratio());
			i++;
		}
		
		System.out.println("averages: ");
		
		
		LinkedList<LinkedList<BinarySearchTree<Integer>>> dtreeList = new LinkedList<>();
		for(i = 0; i<10; i++) {
			dtreeList.add(new LinkedList<BinarySearchTree<Integer>>());
		}
		for(LinkedList<BinarySearchTree<Integer>> l : dtreeList) {
			for(i = 0; i<10; i++) {
				l.add(new BinarySearchTree<Integer>());
			}
		}
	
		k = 0.0;
		for(LinkedList<BinarySearchTree<Integer>> l : dtreeList) {
			
			for (BinarySearchTree<Integer> t : l) {
				for (i = 0; i < 1000; i++) {
					if (Math.random() < (1.0 - k)) {
						t.add((int) (2999 * Math.random()) + 1);
					} else {
						t.add(42);
					}
				} 
			}
			k = k+0.1;
		}
		
		k=0.0;
		System.out.println(dtreeList.size());
		for(LinkedList<BinarySearchTree<Integer>> l : dtreeList) {
			int heightSum = 0;
			for (BinarySearchTree<Integer> t : l) {
				heightSum += t.heightRec();
			}
			
			double fratioSum = 0;
			for (BinarySearchTree<Integer> t : l) {
				fratioSum += t.fratio();
			}
			
			System.out.println("k: " + k);
			System.out.println("\tAvg. Height: " + heightSum/10);
			System.out.println("\tAvg. fratio: " + fratioSum/10);
			
			k+=0.1;
		}

	}//similarly to the first part of the problem we create a linked list with 10 trees and calculate the chances to add 42 with different percentage of k's, only now we create and additional linked list of 10 trees and use a double for loop, to make it 100 trees. then we calculate the average height and fration for different values of k
}

