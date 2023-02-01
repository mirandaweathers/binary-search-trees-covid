
public class Node {
	String stateName;
	double deathRate;
	Node leftChild = null;
	Node rightChild = null;
	
	public void printNode() {
		System.out.printf("%-16s%10.2f\n", stateName, deathRate);
	}//printNode()
	
}//Node class
