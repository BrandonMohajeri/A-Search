A* Search
==========
This program provides a solution to a navigation problem using an implementation of the A* algorithm. 

## Synopsis
A robot represented as a point moves in a regular two-dimensional NxN grid (e.g., N =
100). Each point of the grid is either "free" or "forbidden" (obstacle). From any position (i,j)
in the grid the robot can reach each of the 4 adjacent positions (i,j-1), (i,j+1), (i-1,j), (i+1,j),
if it is not forbidden. A navigation problem consists of finding a path in the grid (sequence
of adjacent free points) that connects a given initial position to a given goal position. Each
move has a cost of 1. 

Four Evaluation Functions:
  1. f(N) = Euclidean distance from N to the goal (Strategy 1) 
    * (i.e. the length of a straight line between two points, E((i,j),(i',j')) = sqrt[(i-i')^2+(j-j')^2])
  2. f(N) = Manhattan distance to the goal (Strategy 2) 
    * (i.e. the length of the shortest path obtainable by traversing only in the cardinal directions, ignoring any obstacles, M((i,j),(i',j')) = |i-i'| + |j-j'|) 
  3. f(N) = g(N) + h(N), where: 
    * g(N) is the cost of the path found so far from the initial node to N 
    * h(N) is: - the Euclidean distance from N to the goal (Strategy 3)
    * The Manhattan distance to the goal (Strategy 4) 


For each problem-function pair, the program will output the generated path, its cost, and the
number of nodes in the search tree when the solution.

### Input Files
The input files will contain information about the map that the robot will traverse. The first line will
have a number N that is the width and the height of the map (all maps are NxN, i.e. same height and
width). No map will have N greater than 80. The following N lines will detail the map. Each line
will have N characters, representing the N spaces in that row. The first line will be row N and the
first character in each row will be in column 0. The characters in the rows will be as follows:
<dd>. empty space (traversable)<dd><dd>*i initial space (traversable, robot starts here)<dd><dd>g goal space (traversable, robot’s goal here)<dd><dd>+ obstacle (not traversable) <dd>

A sample input file might be as follows:
5	
<dd>...g.<dd><dd>.++..<dd><dd>.i+..<dd>	<dd>..+..<dd><dd>+...+<dd>	
