//Jesualdo Cristovao
//Assignment 5
//11-4-18

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Assignment5Driver {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner keyboard = new Scanner(System.in);

		// User input
		System.out.println("Enter name of File to Read: (Including .txt extension )");
		String fileName = keyboard.next();

		System.out.println("Enter name of Output File (Including .txt extension)");
		String fileOutName = keyboard.next();

		PrintWriter writeFile = new PrintWriter(new FileOutputStream(fileOutName)); // Output file .txt

		keyboard.close();

		Scanner in = new Scanner(new FileInputStream(fileName));
		int numVertices = in.nextInt();

		DirectedGraph graph = new DirectedGraph(numVertices);

		//Create graph from input file
		while (in.hasNext()) {
			int source = in.nextInt();
			int destination = in.nextInt();
			graph.addEdge(source, destination);
		}

		in.close();

		//To write to text file
		String s = graph.getSCCs();
		writeFile.println(s);
		writeFile.println();
		System.out.println(s);

		writeFile.close();

	}

}
