import java.util.*;
import java.lang.Math;

public class Node<T> implements Comparable<T>{
	Node<T> parent;
	int x,y;
	boolean isGoal;
	double hCost, depth;

	public int compareTo(T other){
		double thisValue = this.getCost();
		double otherValue = ((Node)other).getCost();
		double v = thisValue - otherValue;
		return (v>0)?1:(v<0)?-1:0;
	}

	// Return f(n)
	public double getCost(){
		return this.depth + this.hCost;
	}

	// Return manhattan cost
	public double setManhattan(T goal){
		int x = Math.abs(this.x - ((Node)goal).x);
		int y = Math.abs(this.y - ((Node)goal).y);
		int z = x+y;
		return z;
	}

	// Return euclidian cost
	public double setEuclidian(T goal){
		double x = Math.abs(this.x - ((Node)goal).x);
		double y = Math.abs(this.y - ((Node)goal).y);
		double dist = Math.sqrt(x*x + y*y);
		return dist;
	}

	// Node toString
	public String toString(){
		return this.x + ", " + 
		       this.y + ", " +
		       // this.isObstical + ", " +
		       this.isGoal + ", " +
		       this.depth + ", " +
			   Double.toString(this.hCost);//+ " || " + this.parent;

	}

	// Set Node depth
	public double setDepth(Node myParent){
		return myParent.depth + 1; 
	}
}

