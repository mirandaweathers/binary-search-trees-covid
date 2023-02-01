import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * COP 3530: Project 4 - Binary Search Trees
 * <p>
 * This project implements a binary search tree (BST) with data
 * from the user-linked CSV file containing information about US
 * states and COVID-19 statistics. Each state's name and calculated
 * COVID-19 death rate is added to the BST in alphabetical order.
 * <p>
 * The user is presented a menu with options to print the current
 * BST (inorder, preorder, or postorder), delete a state by given name,
 * find a state by name and print its death rate and the path to the
 * state in the tree, print the top or bottom states in regard to
 * death rate, and exit the program.
 * <p>
 * User input is validated throughout the program.
 * 
 * @author Miranda Weathers N01482572
 * @version 11/18/2021
 */

public class Project4 {

	/**
	 * Read in file, insert data into BST, provide menu to user,
	 * call method to perform user's selection.
	 * @param args
	 */
	public static void main(String[] args) {
		//read in states from csv
		System.out.print("Enter the file name: ");
		Scanner scan = new Scanner(System.in);
		String fileName = scan.nextLine();
		
		//check if file exists
		Scanner readFile = null;
		try {
			readFile = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("\nFile not found.");
			System.exit(1);
		}//try-catch
		
		BinarySearchTree bst = new BinarySearchTree();
		
		readFile.nextLine(); //skip labels in CSV
		readFile.useDelimiter(",|\n"); //set delimiters
		while(readFile.hasNext()) { //while file has more data,
			String name = readFile.next(); //save name
			readFile.next(); //skip capitol
			readFile.next(); //skip region
			readFile.next(); //skip seats
			double population = readFile.nextInt(); //save population
			readFile.next(); //skip cases
			double deaths = readFile.nextInt(); //save deaths
			readFile.next(); //skip mhi
			readFile.next(); //skip vcr
			double dr = (deaths/population) * 100000; //calculate DR
			bst.insert(name, dr); //insert info into BST
		}//create BST from file
		
		//present menu
		boolean flag = true;
		int choice = 0; //menu choice
		int c = 0; //number of states for options 6-7
		String search = null; //for option 5
		
		while(flag==true) {
			System.out.println("\n1) Print tree inorder");
			System.out.println("2) Print tree preorder");
			System.out.println("3) Print tree postorder");
			System.out.println("4) Delete a state for a given name");
			System.out.println("5) Search and print a state and its path for a given name");
			System.out.println("6) Print bottom states regarding DR");
			System.out.println("7) Print top states regarding DR");
			System.out.println("8) Exit");
			System.out.print("\nEnter the number of your choice: ");
			
			//check if input matches type int
			if(scan.hasNextInt()) {
				choice = scan.nextInt();
				scan.nextLine();
			} else {
				System.out.println("\nPlease enter an integer between 1-8.");
				scan.nextLine();
				continue;
			}//end if else
			
			switch(choice) {
			case 1: //Inorder
				tableLabels();
				bst.printInorder(bst.root);
				break;
			case 2: //Preorder
				tableLabels();
				bst.printPreorder(bst.root);
				break;
			case 3: //Postorder
				tableLabels();
				bst.printPostorder(bst.root);
				break;
			case 4: //Delete
				System.out.print("Enter state to delete: ");
				search = scan.nextLine();
				search = search.replaceAll("\\n", "");
				bst.delete(search);
				break;
			case 5: //Find and print state and path
				System.out.print("Enter state to find: ");
				search = scan.nextLine();
				search = search.replaceAll("\\n", "");
				bst.find(search);
				break;
			case 6: //Bottom states
				System.out.print("How many states: ");
				if(scan.hasNextInt()) {
					c = scan.nextInt();
					scan.nextLine();
					if(c<1 || c>50) { //out of range
						System.out.println("\nPlease enter an integer between 1-50.");
						continue;
					} else { //in range
						bst.printBottomStates(c);
					}//if else
				} else {
					System.out.println("\nPlease enter an integer between 1-50.");
					scan.nextLine();
					continue;
				}//end if else
				break;
			case 7: //Top states
				System.out.print("How many states: ");
				if(scan.hasNextInt()) {
					c = scan.nextInt();
					scan.nextLine();
					if(c<1 || c>50) { //out of range
						System.out.println("\nPlease enter an integer between 1-50.");
						continue;
					} else { //in range
						bst.printTopStates(c);
					}//if else
				} else {
					System.out.println("\nPlease enter an integer between 1-50.");
					scan.nextLine();
					continue;
				}//end if else
				break;
			case 8: //Exit
				System.out.println("\nGoodbye!");
				flag = false;
				break;
			default:
				System.out.println("\nPlease select a valid option.");
				break;
			}//switch
			
		}//menu loop
		
		scan.close();
		
	}//main()
	
	/**
	 * Prints the labels and horizontal divider for name/DR table.
	 */
	public static void tableLabels() {
		System.out.println("\nName            Death Rate");
		System.out.println("--------------------------");
	}

}
