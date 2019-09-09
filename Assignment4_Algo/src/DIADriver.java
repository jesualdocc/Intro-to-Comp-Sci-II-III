import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DIADriver {

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<Aircraft> incomingFlights = new ArrayList<Aircraft>();
		MinPriorityQueue airportQueue = new MinPriorityQueue();
		Scanner keyboard = new Scanner(System.in);
		
		//User input
		System.out.println("Enter name of File to Read: (eg.arrivals_01_00010.txt )");
		String fileName = keyboard.next();
		
		
		System.out.println("Enter name of Output File (Including .txt extension)");
		String fileOutName = keyboard.next();
		
		PrintWriter writeFile = new PrintWriter( new FileOutputStream(fileOutName)); //Output file .txt
		
		keyboard.close();
		//

		Scanner in = new Scanner(new FileInputStream(fileName));
		int numRunways = in.nextInt();
		
		//Read data from text file
		while (in.hasNext()) {
			String id = in.next();
			int arrivalTime = in.nextInt();
			int priority = in.nextInt();
			incomingFlights.add(new Aircraft(id, arrivalTime, priority)); //Stores all planes from file in a temporary array
		}

		in.close();
		

		int t = 0; //Time
		int numPlanes = incomingFlights.size();
		int planesLanded = 0;
		
		System.out.println();
		String s = "Number of Runways: " + numRunways;
		System.out.println(s);
		String caption = "AircraftID	ArrivalTime		LandingPriority		TimeLanded";
		writeFile.println(s);
		writeFile.println(caption);
		writeFile.println();
		System.out.println(caption);
		System.out.println();
	
		while (planesLanded != numPlanes) {

			int runWaysAvailable = numRunways; //at Each Time step

			for (int i = 0; i < incomingFlights.size(); i++) {

				if (t == incomingFlights.get(i).getArrivalTime()) {

					//Adds planes to the queue (holding pattern) at time of arrival
					airportQueue.MinHeapInsert(incomingFlights.remove(i));
					i = i - 1;
					
				}
			}

			if (airportQueue.isEmpty()) {
				// Do nothing since no aircraft is in a holding pattern
			} else {

				do {

					//Lands plane at set arrival time or later depending on priority and runway availability
					if (t >= airportQueue.Heap_Min().getArrivalTime()) {

						Aircraft a = airportQueue.HeapExtractMin(); //Landing

						runWaysAvailable--; //Keeps track of how many runways have been used at time t
						planesLanded++;
						
						String outputText = a.getFlightID() + "		" + a.getArrivalTime() + "		" + a.getLandingPriority() + "			" + t;
						
						writeFile.println(outputText);
						
						System.out.println(outputText);
					} else {
						break;
					}

				} while (!airportQueue.isEmpty() && runWaysAvailable > 0);
			}

			t++;

		}
		
		
		writeFile.close();
		
	}
}
