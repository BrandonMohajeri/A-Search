import java.util.*;
import java.io.*;

public class main{
	public static void main(String[] args){

		fileIO fileIO = new fileIO();
		Search search = new Search();
		Scanner reader = new Scanner(System.in);  
		System.out.println("Select Option:");
		System.out.print("\t1) Import Map\n\t2) Create Map\n\t3) Exit\nEnter: ");
		int userInput = reader.nextInt(); 
		
		switch(userInput){
			case 1:
		  	     System.out.print("Enter File Name: ");
		  	     String fileName = reader.next();
		  	     int width = fileIO.getWidth(fileIO.readMap(fileName));
				 System.out.print("Select Search Strategy:\n\t1) Euclidian\n\t2) Manhattan\nEnter: ");
				 int searchStrategy = reader.nextInt();

				search.conductSearch(fileIO.readMap(fileName), width, searchStrategy);


				break;
			case 2:
				System.out.print("Enter Dimensions (N x N): ");
				int width2 = reader.nextInt();
				fileIO.generateMap(width2);
				System.out.println("File 'Generated_Map.txt' has been created.");
				System.out.print("Select Search Strategy:\n\t1) Euclidian\n\t2) Manhattan\nEnter: ");
				int searchStrategy2 = reader.nextInt();

				search.conductSearch(fileIO.readMap("Generated_Map.txt"), width2, searchStrategy2);
				break;
			case 3:
				break;
			default:
		}

		
	}





}