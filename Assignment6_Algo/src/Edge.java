
public class Edge {
	private Vertex destination;
	private double weight;

	public Edge(Vertex d, double w) {
		destination = d;
		weight = w;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public double getWeight() {
		return weight;
	}


}
