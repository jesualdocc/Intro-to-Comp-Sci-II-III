import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Assignment6Driver {

	public static void main(String[] args) throws FileNotFoundException {

		WeightedGraph graph1 = new WeightedGraph();//
		WeightedGraph graph2 = new WeightedGraph();//

		Scanner keyboard = new Scanner(System.in);

		// User input
		System.out.println("Enter name of File to Read: (Including .txt extension )");
		String inputFile = keyboard.next();

		keyboard.close();

		String fileOutBellman = inputFile + "b.out";
		String fileOutDijkstra = inputFile + "d.out";

		PrintWriter writeFile1 = new PrintWriter(new FileOutputStream(fileOutBellman)); // Output file .txt
		PrintWriter writeFile2 = new PrintWriter(new FileOutputStream(fileOutDijkstra)); // Output file .txt

		Scanner in = new Scanner(new FileInputStream(inputFile));

		// Create graph from input file
		while (in.hasNextLine()) {
			String input = in.nextLine();
			String[] splitted = input.split("\\s+");

			String source = splitted[0];
			graph1.addVertex(source);
			graph2.addVertex(source);
			for (int i = 1; i < splitted.length; i = i + 2) {
				String destination = splitted[i];
				double weight = Double.parseDouble(splitted[i + 1]);
				graph1.addEdge(source, destination, weight);
				graph2.addEdge(source, destination, weight);
				// Graphs 1 and 2 are exactly the same, two were created for measuring times
				// only
				// So that both algorithm start execution with the exact same graph
			}

		}

		in.close();
		in = new Scanner(new FileInputStream(inputFile));
		// Reset so that it can go back and use the very string value as the starting
		// point
		String s = in.next(); // Select starting point for algorithms
		in.close();

		System.out.println(s);

		int numIterations = 100000;

		CpuTimer timer = new CpuTimer();
		for (int i = 0; i < numIterations; i++) {
			Dijkstra(graph1, s);
		}

		float DijkstraAvg = (float) timer.getElapsedCpuTime() / numIterations;

		CpuTimer timer2 = new CpuTimer();
		for (int i = 0; i < numIterations; i++) {
			BellmanFord(graph2, s);
		}

		float BellmanFordAvg = (float) timer2.getElapsedCpuTime() / numIterations;

		int numVertices1 = graph1.getgMap().size();
		int numEdges1 = 0;

		int numVertices2 = graph2.getgMap().size();
		int numEdges2 = 0;

		for (Vertex v : graph1.getgMap().values()) {
			numEdges1 += numEdges1 + v.getAdjacencyList().size();
			System.out.println(numEdges1);
		}

		for (Vertex v : graph2.getgMap().values()) {
			numEdges2 += numEdges2 + v.getAdjacencyList().size();
		}

		System.out.println(numVertices1 + "," + numEdges1 + "," + "\"D\"," + DijkstraAvg + "," + inputFile);
		System.out.println(numVertices2 + "," + numEdges2 + "," + "\"B\"," + BellmanFordAvg + "," + inputFile);

		// Write File for Both Algorithms

		// Dijkstra
		for (Vertex v : graph1.getgMap().values()) {
			String vertexName = v.getId();
			float vertexDistance = (float) v.getDistance();
			writeFile1.println(vertexName + " " + vertexDistance);
		}

		// Bellman-Ford
		for (Vertex v : graph2.getgMap().values()) {
			String vertexName = v.getId();
			float vertexDistance = (float) v.getDistance();
			writeFile2.println(vertexName + " " + vertexDistance);
		}

		writeFile1.close();
		writeFile2.close();
	}

	public static void Dijkstra(WeightedGraph g, String s) throws NullPointerException{
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(1, new VertexComparator());
		Map<String, Vertex> map = g.gMap;

		if (!map.containsKey(s)) {
			throw new NoSuchElementException("No Vertex Named " + s + " Found");
		}

		// Initialize Single Source
		for (Vertex v : map.values()) {
			v.setDistance(Double.MAX_VALUE);
			v.setPredecessor(null);
		}

		q.add(map.get(s));

		Vertex source = map.get(s);
		source.setDistance(0);

		while (!q.isEmpty()) {

			Vertex u = q.remove();

			for (int i = 0; i < u.AdjacencyList.size(); i++) {

				double edgeWeight = u.AdjacencyList.get(i).getWeight();

				Vertex neighbor = u.AdjacencyList.get(i).getDestination();

				double neighborDistance = neighbor.getDistance();

				double newDistanceUtoV = u.getDistance() + edgeWeight;

				if (newDistanceUtoV < neighborDistance) {

					if (q.contains(neighbor)) {
						q.remove(neighbor);
						neighbor.setDistance(newDistanceUtoV);
						neighbor.setPredecessor(u);
						q.add(neighbor);
					} else {
						neighbor.setDistance(newDistanceUtoV);
						neighbor.setPredecessor(u);
						q.add(neighbor);
					}

				}
				//g.gMap.put(neighbor.getId(), neighbor);
			}

		}

	}

	public static void BellmanFord(WeightedGraph g, String s) {
		Map<String, Vertex> map = g.getgMap();

		if (!map.containsKey(s)) {
			throw new NoSuchElementException("No Vertex Named " + s + " Found");
		}

		// Initialize Single Source
		for (Vertex v : map.values()) {
			v.setDistance(Double.MAX_VALUE);
			v.setPredecessor(null);
		}
		Vertex source = map.get(s);
		source.setDistance(0);

		for (Vertex v : map.values()) {

			if (v.getId() == s) {
				continue;
			}

			// Relax Edges
			LinkedList<Edge> Edge = v.getAdjacencyList();
			int numEdges = Edge.size();
			for (int i = 0; i < numEdges; ++i) {
				double weight = Edge.get(i).getWeight();

				if (Edge.get(i).getDestination() == null) {
					continue;
				}

				Vertex d = Edge.get(i).getDestination();

				if (v.getDistance() != Double.MAX_VALUE && (v.getDistance() + weight) < d.getDistance()) {
					d.setDistance(v.getDistance() + weight);
				}
			}

		}
		for (Vertex v : map.values()) {
			// Check for Negative Cycles
			LinkedList<Edge> Edge = v.getAdjacencyList();
			int numEdges = Edge.size();
			for (int i = 0; i < numEdges; ++i) {
				double weight = Edge.get(i).getWeight();

				if (Edge.get(i).getDestination() == null) {
					continue;
				}

				Vertex d = Edge.get(i).getDestination();

				if (v.getDistance() != Double.MAX_VALUE && (v.getDistance() + weight) < d.getDistance()) {
					d.setDistance(v.getDistance() + weight);
					System.out.println("Graph Contains Negative Weight Cycle(s)");
				}
			}
		}

	}

}
