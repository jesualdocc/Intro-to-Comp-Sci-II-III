
import java.util.LinkedList;



public class Vertex{
	public LinkedList<Edge> AdjacencyList;
	private double distance;
	private Vertex Predecessor;
	private int temp;
	private String id ;
	

	public Vertex(String name) {
		AdjacencyList = new LinkedList<Edge>();
		id = name;
	}


	public String getId() {
		return id;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public Vertex getPredecessor() {
		return Predecessor;
	}


	public void setPredecessor(Vertex predecessor) {
		Predecessor = predecessor;
	}


	public int getTemp() {
		return temp;
	}


	public void setTemp(int temp) {
		this.temp = temp;
	}


	public LinkedList<Edge> getAdjacencyList() {
		return AdjacencyList;
	}


	public void setAdjacencyList(LinkedList<Edge> adjacencyList) {
		AdjacencyList = adjacencyList;
	}

	
	
}
