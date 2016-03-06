import java.io.*;
import java.util.*;

public class Search{
	fileIO fileIO = new fileIO();
	LinkedList<Node> openList = new LinkedList<Node>();		//list of queued nodes
	LinkedList<Node> closedList = new LinkedList<Node>();   //list of visited nodes


	public void conductSearch(char[][] map, int width, int searchStrategy){
		LinkedList<int[]> blockedPoints;	// 	List of blocked points
		Node startNode = new Node();		//	Initial Node
		Node goalNode = new Node();			//	Final Node
		
		int expandedNodes = 0;				//	Num of expanded nodes
		goalNode = initializeGoal(map, width);
		startNode = initializeStart(map, width, searchStrategy, goalNode);
		blockedPoints = fileIO.getBlockedPoints(map, width);

		openList.add(startNode);			// Add initial node to list 
		while(openList.size() > 0){
			Node currentNode = new Node();
			currentNode = getLowest(openList);	//	Grab lowest f(n)
			System.out.println("Expanding Node     |  #  " + expandedNodes + "   |     :" + currentNode.toString());
			expandedNodes++;
			
			//	If goal reached, construct path
			if(currentNode.isGoal == true){
				constructPath(currentNode, width, map);
				break;
			}
			
			// Move current node from open list to closed list. 
	    	openList.remove(currentNode); closedList.add(currentNode);

	    	// Retrieve and iterate through valid neighbors
	    	LinkedList<Node> neighborList = new LinkedList<>();
	    	neighborList = getNeighbors(currentNode, goalNode, width, blockedPoints, searchStrategy);
	    	
	    	for(Node neighbor : neighborList){
	    		if(doesContain(closedList,neighbor) == true){continue;}		// If visiited - continue
	    		if(doesContain(openList, neighbor) == false ){			    // If not visited - add neighbor 
	    			openList.add(neighbor);
	    			neighbor.parent=currentNode;
	    		}
	    		else if(currentNode.depth <= neighbor.depth){continue;}	    // Else if current g(n) - continue 
	    	}
		}
		
		System.out.println("\tExpanded Nodes : " + expandedNodes + "\n");
	}

	// Visually constructs path from initial to goal
    public void constructPath(Node node, int width, char[][] map){
		LinkedList<Node> path = new LinkedList();
		LinkedList<int[]> pathList = new LinkedList();
		fileIO myFile = new fileIO();
		int[] points = new int[2];
		int numSteps = 0;
		System.out.print("\n#############################################################\n");
		System.out.print("                        Constructed Map                     \n");
		System.out.println("#############################################################\n");

		while(node.parent != null){
			numSteps++;
			path.addFirst(node);
			node = node.parent;
			points[0] = node.x;
			points[1] = node.y;
			//pathList.add(points);			if this runs without this, delete
			if(map[points[0]][points[1]] != 'i'){
				map[points[0]][points[1]] = 'O';
			}
		}

		for(int i = 0; i <= width; i++){
			for(int j = 0; j <= width; j++){
				System.out.print(map[j][i]);
			}
			System.out.println();
		}
		System.out.print("\n-----------------------------------");
		System.out.print("\n\t     Statistics\n");
		System.out.print("-----------------------------------");
		System.out.println("\n\tNumber of Steps: " + numSteps);
	}

	// Does list contain node
    public boolean doesContain(LinkedList<Node> myList, Node searchNode){
		for(Node myNode : myList){
			if(myNode.x == searchNode.x && myNode.y == searchNode.y){
				return true;
			}
		}
		return false;
	}

	// Find all nonblocked neighbors of a node
	public LinkedList<Node> getNeighbors(Node currentNode, Node goalNode, int width, LinkedList<int[]> blockedList, int searchStrategy){
		LinkedList<Node> neighbors = new LinkedList<>();
		LinkedList<int[]> nList = new LinkedList<int[]>();
		LinkedList<int[]> finalList = new LinkedList<int[]>();

		//Left neighbor
		if(currentNode.x-1 >= 0){
			int[] p = new int[2];
			p[0] = currentNode.x-1;
			p[1] = currentNode.y;
			nList.add(p);
			finalList.add(p);
		}

		//Bottom nieghbor
		if(currentNode.y-1 >= 0){
			int[] p = new int[2];
			p[0] = currentNode.x;
			p[1] = currentNode.y-1;
			nList.add(p);
						finalList.add(p);
		}

		//Right neighbor
		if(currentNode.x+1 <= width-1){
			int[] p = new int[2];
			p[0] = currentNode.x+1;
			p[1] = currentNode.y;
			nList.add(p);
			finalList.add(p);
		}

		//Top neighbor.
		if(currentNode.y+1 <= width-1){
			int[] p = new int[2];
			p[0] = currentNode.x;
			p[1] = currentNode.y+1;
			nList.add(p);
			finalList.add(p);
		}

		// Check to see if blocked
		for(int[] xx : nList){
			for(int[] jj : blockedList){
				if(xx[0] == jj[0] && xx[1] == jj[1]){
					int[] temp = new int[2];
					temp[0] = xx[0];
					temp[1] = xx[1];
					finalList.remove(xx);
				}
			}
		}

		//Set costs and add node to neighbor list. 
		for(int[] pp : finalList){
			Node newN = new Node();
			newN.x = pp[0];
			newN.y = pp[1];
            if(searchStrategy == 1){newN.hCost = newN.setEuclidian(goalNode);}
		    if(searchStrategy == 2){newN.hCost = newN.setManhattan(goalNode);}
		    newN.depth = newN.setDepth(currentNode);
		    neighbors.add(newN);
		}


		// If neighbor is goal, make goal value true
		for(Node curr : neighbors){
			if((curr.x == goalNode.x) && (curr.y == goalNode.y)){curr.isGoal = true;}
		}
		return neighbors;
	}

	// Grab lowest scoring f(n) from list
	public Node getLowest(LinkedList<Node> myList){
		Node lowestNode = new Node();
		double lowest = 10000;
		for(Node myNode : myList){
			if(myNode.getCost() < lowest){
				lowestNode = myNode;
				lowest = lowestNode.getCost();
			}
		}
		return lowestNode;
	}

	// Initialize goal Node
	public Node initializeGoal(char[][] map, int width){
		int[] goalPoint = new int[2];
		Node goalNode = new Node();

		for(int i = 0; i <= width; i++){
			for(int j = 0; j <= width; j++){
				// System.out.println(map[i][j]);
				if(map[i][j] == 'g'){goalPoint[0]=i;goalPoint[1]=j;break;}
			}
		}
		
		goalNode.x = goalPoint[0];
		goalNode.y = goalPoint[1];
		goalNode.isGoal = true;
		goalNode.depth = 0;
		goalNode.parent = null;
		return goalNode;
	}

	// Initialize start Node
	public Node initializeStart(char[][] map, int width, int searchStrategy, Node goalNode){
		int[] initialPoint = new int[2];
		Node initialNode = new Node();

		for(int i = 0; i <= width; i++){
			for(int j = 0; j <= width; j++){
				if(map[i][j] == 'i'){initialPoint[0]=i;initialPoint[1]=j;break;}
			}
		}
		
		initialNode.x = initialPoint[0];
		initialNode.y = initialPoint[1];
		initialNode.isGoal = false;
		initialNode.depth = 0;
		initialNode.parent = null;

		if(searchStrategy == 1){
			initialNode.hCost = initialNode.setEuclidian(goalNode);
		}
		if(searchStrategy == 2){
			initialNode.hCost = initialNode.setManhattan(goalNode);
		}
		System.out.println(initialNode.hCost);
		return initialNode;
	}
}