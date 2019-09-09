//Jesualdo Cristovao
//Assignment 5
//11-4-18

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DirectedGraph {

	private LinkedList<Integer> AdjacencyList[];
	private int V; // Number of Vertices
	private String scc;

	public DirectedGraph(int V) {

		this.V = V;
		AdjacencyList = new LinkedList[V];
		scc = "";

		for (int i = 0; i < V; ++i) {
			AdjacencyList[i] = new LinkedList<Integer>(); // Array of Linked Lists
		}
	}

	// Add an edge to a vertex
	public void addEdge(int v, int w) {
		AdjacencyList[v].add(w);
	}

	public void DFS(int v, boolean visited[]) {
		//Recursively checks all the vertices adjacent to v
		visited[v] = true;
		scc += v + " ";
		System.out.print(v + " ");

		for (Iterator<Integer> j = AdjacencyList[v].iterator(); j.hasNext();) {

			int i = j.next();
			if (!visited[i]) {
				DFS(i, visited);
			}
		}
	}

	private void transposeGraph() {
		//Reverses all edges of the graph

		DirectedGraph gT = new DirectedGraph(V); // To create a temporary adjacency list before updating main one

		for (int i = 0; i < V; i++) {

			for (Iterator<Integer> j = AdjacencyList[i].listIterator(); j.hasNext();) {
				gT.AdjacencyList[j.next()].add(i);
			}
		}

		AdjacencyList = gT.AdjacencyList;
	}

	public void stronglyConnectedGraphs() {

		//A stack is used to keep track of the vertices being processed 
		Stack<Integer> s = new Stack<Integer>(); // LIFO
		boolean visited[] = new boolean[V];

		for (int i = 0; i < V; i++) {
			visited[i] = false; //No vertex has been visited yet (DFS)
		}

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				DFSHelper(i, s, visited); //Marks the vertex that have been visited
			}
		}

		transposeGraph(); //Reverses all edges of the graph

		for (int i = 0; i < V; i++) {
			visited[i] = false;
		}

		while (!s.isEmpty()) {
			int v = s.pop();

			if (!visited[v]) {
				DFS(v, visited);
				scc += "\n";
				System.out.println();
			}
		}

	}

	private void DFSHelper(int v, Stack<Integer> s, boolean visited[]) {
		visited[v] = true;

		for (Iterator<Integer> j = AdjacencyList[v].iterator(); j.hasNext();) {

			int i = j.next();
			if (!visited[i]) {
				DFSHelper(i, s, visited);
			}
		}
		s.push(v);
	}

	//Returns all SSCs to be writen on text file
	public String getSCCs() {
		stronglyConnectedGraphs();
		return scc;
	}

}
