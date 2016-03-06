import java.io.*;
import java.util.*;
import java.lang.Math;

public class fileIO{

	// Generate random map and output
	public void generateMap(int width){
			Random rand = new Random();
			char[][] map = new char[10000][10000];
			double density = 25;
			for(int i = 0; i <= width; i++){
				for (int j = 0; j <= width; j ++){				
					if(rand.nextInt(101) <= density){ map[i][j] = '+'; } 
					else {map[i][j] = '.';}
				}
			}

			// Assign random points for initial and goal
			map[rand.nextInt(width+1)][rand.nextInt(width+1)] = 'i';
			map[rand.nextInt(width+1)][rand.nextInt(width+1)] = 'g';
				
			// Write map to file and print to user
		    try(Writer writer = new BufferedWriter(new OutputStreamWriter(
		    	new FileOutputStream("Generated_Map.txt"), "utf-8"))){
		    	writer.write(Integer.toString(width));
				writer.write("\n");	

				System.out.print("\n#############################################################\n");
				System.out.print("                           Generated Map                       \n");
				System.out.println("#############################################################\n");

		    	for(int i = 0; i <= width; i++){
					for (int j = 0; j <= width; j ++){	
						System.out.print(map[i][j]);
						writer.write(map[i][j]);
					}
					System.out.println("");
					writer.write("\n");
				}
				System.out.println();
		    }
		    catch(Exception e){
		    	System.out.println("ERROR: File failed to be created");
		    }
	}

	// Read map from file
	public char[][] readMap(String fileName){

		char[][] map = new char[10000][10000];
		int lineNumber = -1;
		int width = 0;
		File here = new File(fileName);

		try(BufferedReader br = new BufferedReader(new FileReader(here))){

			String line = null;
			while((line = br.readLine()) != null){
				int index = 0;
				if(lineNumber != -1){
					for(char c : line.toCharArray()){
						int[] blocked = new int[2];
						map[index][lineNumber] = c;

						if(c == '+'){
							blocked[0] = index;
							blocked[1] = lineNumber;
						}
						index++;
					}
				}
				else{
					width = Integer.parseInt(line);
				}
				lineNumber++;
			}

			return map;
		}
		catch(Exception e){
			System.out.println("Error: File not found.");
			return null;
		}
	}

	// Get map width
	public int getWidth(char[][] map){
		int count = -1;
		for(char c : map[0]){
			if(c != '\0'){
				count++;
			}
		}
		return count;
	}

	// Get map's blocked points 
	public LinkedList<int[]> getBlockedPoints(char[][] map, int width){
		LinkedList<int[]> blockedPoints = new LinkedList<int[]>();

		for(int i = 0; i <= width; i++){
			for(int j = 0; j <= width; j++){
				if(map[i][j] == '+'){
					int[] point = new int[2];
					point[0] = i;
					point[1] = j;
					blockedPoints.add(point);
				}
			}
		}
		return blockedPoints;
	}
}
