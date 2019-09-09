import java.util.HashMap;
import java.util.Map;

//Construction of the Graph
public class WeightedGraph {

	public Map<String, Vertex> gMap;

	public WeightedGraph() {
		gMap = new HashMap<String, Vertex>();

	}

	public Map<String, Vertex> getgMap() {
		return gMap;
	}

	public void addEdge(String s, String d, double w) {

		if (!gMap.containsKey(s)) {
			System.out.println("Error! No Vertex Named " + s + " Exists in the Graph");
		} 

		else {

			Vertex destinationVertex = gMap.get(d);

			Edge e = new Edge(destinationVertex, w);
			Vertex sourceVertex = gMap.get(s);
			
			sourceVertex.AdjacencyList.add(e);
			
		}

	}

	public void addVertex(String name) {

		if (gMap.containsKey(name)) {

			System.out.println("Error! No Vertices Can Have The Same ID(Name)");

		} else {
			gMap.put(name, new Vertex(name));
		}

	}

}
