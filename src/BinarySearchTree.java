import java.util.ArrayList;

/**
 * The BinarySearchTree class implements a binary search tree which
 * is populated with data provided by the Project4 class. Insert, find,
 * and delete are supported as well as methods to print the tree
 * inorder, preorder, and postorder.
 * 
 * @author Miranda Weathers N01482572
 * @version 11/18/2021
 */

public class BinarySearchTree {
	
	Node root;
	
	/**
	 * BST constructor; creates new Binary Search Tree.
	 */
	public BinarySearchTree() {
		root = null;
	}//BST constructor
	
	/**
	 * Insert into BST; takes state name and death rate.
	 * @param name
	 * @param DR
	 */
	public void insert(String name, double DR) {
		Node n = new Node();
		n.stateName = name;
		n.deathRate = DR;
		
		if(root==null) { //if empty BST
			root = n;
		} else { //if root already exists
			Node current = root;
			Node parent;
			while(true) {
				parent = current;
				if(name.compareToIgnoreCase(current.stateName) < 0) { //if new node name < current name
					current = current.leftChild;
					if(current==null) {
						parent.leftChild = n;
						return;
					}//null reached
				}//insert left
				else { //if new node name > current node name
					current = current.rightChild;
					if(current==null) {
						parent.rightChild = n;
						return;
					}//null reached
				}//insert right
			}//while
		}//insert (not root)
	}//insert()
	
	/**
	 * Find a state in the BST by name and return its death rate.
	 * If empty tree, returns 0. If not found, returns -1.
	 * 
	 * @param key
	 * @return DR
	 */
	public double find(String key) {
		if(root == null) {
			System.out.println("Empty tree.");
			return 0;
		}//if empty tree
		
		Node current = root;
		String path = current.stateName;
		
		while(key.compareToIgnoreCase(current.stateName) != 0) {
			if(key.compareToIgnoreCase(current.stateName) < 0) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
			if(current == null) {
				System.out.println("\n" + key + " is not found.");
				return -1;
			}
			else
				path += " --> " + current.stateName;
		}//search loop
		
		System.out.printf("\n"+current.stateName+" is found with a death rate of %.2f\n", current.deathRate);
		System.out.println("Path to "+current.stateName+" is " + path);
		return current.deathRate;
	}//find()
	
	/**
	 * Delete state from BST given user-input name, if found in tree.
	 * @param key
	 */
	public void delete(String key) {
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		while(key.compareToIgnoreCase(current.stateName) != 0) {
			parent = current;
			if(key.compareToIgnoreCase(current.stateName) < 0) {
				isLeftChild = true;
				current = current.leftChild;
			} else {
				isLeftChild = false;
				current = current.rightChild;
			}//left or right
			if(current == null) {
				System.out.println("\n" + key + " was not found.");
				return;
			}//not found
		}//find while
		
		//if no children
		if(current.leftChild==null && current.rightChild==null) {
			if(current == root)
				root = null;
			else if(isLeftChild)
				parent.leftChild = null;
			else
				parent.rightChild = null;
		}//if no children
		
		//if no right child
		else if(current.rightChild==null) {
			if(current == root)
				root = current.leftChild;
			else if(isLeftChild)
				parent.leftChild = current.leftChild;
			else
				parent.rightChild = current.leftChild;
		}//if no right child
		
		//if no left child
		else if(current.leftChild==null) {
			if(current == root) 
				root = current.rightChild;
			else if(isLeftChild)
				parent.leftChild = current.rightChild;
			else
				parent.rightChild = current.rightChild;
		}//if no left child
		
		//if two children
		else {
			Node successor = getSuccessor(current);
			if(current == root)
				root = successor;
			else if(isLeftChild)
				parent.leftChild = successor;
			else
				parent.rightChild = successor;
			
			successor.leftChild = current.leftChild;
		}//if two children
		
		System.out.println("\n" + key + " is deleted from binary search tree.");
	}//delete()
	
	/**
	 * Get successor for state to-be-deleted to keep tree functional.
	 * @param delNode
	 * @return successor
	 */
	public Node getSuccessor(Node delNode) {
		Node successorParent = delNode; 
		Node successor = delNode;
		Node current = delNode.rightChild; 
		
		while(current != null) {
			successorParent = successor; 
			successor = current;
			current = current.leftChild; 
		}//go right until no more left children
		
		if(successor != delNode.rightChild) {
			successorParent.leftChild = successor.rightChild; 
			successor.rightChild = delNode.rightChild;
		}//if successor not right child, rearrange
		
		return successor;
	}//getSuccessor()
	
	/**
	 * Prints the BST in-order by state name recursively.
	 * @param root
	 */
	public void printInorder(Node root) {
		Node current = root;
		if(current != null) {
			printInorder(current.leftChild);
			current.printNode();
			printInorder(current.rightChild);
		}
	}//printInorder()
	
	/**
	 * Prints the BST pre-order by state name recursively.
	 * @param root
	 */
	public void printPreorder(Node root) {
		Node current = root;
		if(current != null) {
			current.printNode();
			printPreorder(current.leftChild);
			printPreorder(current.rightChild);
		}
	}//printPreorder()
	
	/**
	 * Prints the BST post-order by state name recursively.
	 * @param root
	 */
	public void printPostorder(Node root) {
		Node current = root;
		if(current != null) {
			printPostorder(current.leftChild);
			printPostorder(current.rightChild);
			current.printNode();
		}
	}//printPostorder()
	
	/**
	 * Prints bottom c states (highest DR) in descending order, 
	 * where c is an integer [1,50] given by user.
	 * @param c
	 */
	public void printBottomStates(int c) {
		//System.out.println("\nWhoops! Couldn't print the bottom " + c + " states.");
		
		ArrayList<Node> a = new ArrayList<>();
		bstToArray(root,a);
		
		for(int i = 0; i < a.size(); i++)
			System.out.println(a.get(i).stateName + ": " + a.get(i).deathRate);
		
		Node[] states = new Node[50];
		int i,j;
		
		for(i = 0; i < 50; i++)
			states[i] = a.get(i);
		
		///////// new
		Node temp = null;
		for(i=0; i<c; i++) {
			for(j=0; j<a.size(); j++) {
				temp = a.get(j);
				while(a.get(j+1) != null) {
					if(a.get(j+1).deathRate > temp.deathRate)
						temp = a.get(j+1);
				}
			}
			states[i] = temp;
			System.out.println("i=" + i + "; " + states[i].stateName + ": " + states[i].deathRate);
		}
		
		/*
		 * start at btm[0]
		 * btm[0] = a(0)
		 * look at a(1); is it greater than btm[0]?
		 * 	if so, set btm[1] = btm[0]; btm[0] = a(1). 
		 * look at a(2); is it greater than btm[1]?
		 * 	if so, set btm[2] = btm[1]; btm[1] = a(2);
		 * 
		 */
		
		
		
//		Node[] btm = new Node[c];
//		for(i=0; i<c; i++) {
//			for(j=0; j<a.size(); j++)
//				if(i==0) { //if at beginning of array of c
//					btm[i] = a.get(0);
//				} else { //not at beginning of array of c
//					
//			}	
//		}
		
		///////// end new
		
//		states[0] = a.get(0);
//		
//		for(i = 0; i < c; i++) {
//			for(j=0; j < a.size(); j++)
//				if(states[i].deathRate > a.get(j).deathRate) {
//					
//					break;
//				}
//			for(int k = c-1; k > j; k--)
//				states[k] = states[k-1];
//			states[i] = a.get(j);
//		}
//		for(i = 0; i < c; i++)
//			System.out.println(states[i].stateName + ": " + states[i].deathRate);
	}//printBottomStates()
	
	/**
	 * Prints top c states (lowest DR) in ascending order, 
	 * where c is an integer [1,50] given by user.
	 * @param c
	 */
	public void printTopStates(int c) {
		System.out.println("\nWhoops! Couldn't print the top " + c + " states.");
	}//printTopStates()
	
	public ArrayList<Node> bstToArray(Node n, ArrayList<Node> a) {
		if(n == null)
			return a;
		bstToArray(n.leftChild,a);
		a.add(n);
		bstToArray(n.rightChild,a);
		return a;
	}//bstToArray()
	
}//BST class
