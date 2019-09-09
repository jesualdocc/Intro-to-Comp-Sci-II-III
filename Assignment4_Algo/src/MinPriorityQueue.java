import java.util.ArrayList;

public class MinPriorityQueue {

	private ArrayList<Aircraft> A;

	public MinPriorityQueue() {

		A = new ArrayList<Aircraft>();

	}

	public Aircraft Heap_Min() {
		
		if (isEmpty()) {
			throw new IllegalStateException("Heap Underflow");
		}
		
		return A.get(0);
	}

	public int getHeapSize() {
		return A.size();
	}

	//Extracts head of the queue
	public Aircraft HeapExtractMin() {
		Aircraft min = null;

		if (isEmpty()) {
			throw new IllegalStateException("Heap Underflow");
		}

		if (A.size() == 1) {
			return A.remove(0);
		}

		min = A.get(0);

		A.set(0, A.remove(A.size() - 1));

		//Makes sure queue properties are maintained
		MinHeapify(0);

		return min;

	}

	//Queue ordered in ascending order by Landing Priority
	public void HeapDecreaseKey(int i, Aircraft key) {

		if (key.getLandingPriority() > A.get(i).getLandingPriority()) {
			System.err.println("New Key > Current Key");
		}

		A.set(i, key);

		//Assures that no child will have a parent with a lower priority
		while (i > 0 && A.get(i / 2).getLandingPriority() > A.get(i).getLandingPriority()) {

			Aircraft temp = A.get(i);
			A.set(i, A.get(i / 2));
			A.set(i / 2, temp);
			i = i / 2;
		}

	}
	
	//Adds an element to the queue
	public void MinHeapInsert(Aircraft key) {
		Aircraft a = new Aircraft("TempFlight#", 99, Integer.MAX_VALUE);
		A.add(a);
		HeapDecreaseKey(A.size() - 1, key);

	}
	

	//Maintains properties
	public void MinHeapify(int i) {
		int least = 0;
		
		int l = (2 * i) + 1; ////left Child
		int r = (2 * i) + 2; // Right Child

		//Identify whether parent of left child > left child  
		if ((l <= A.size() - 1) && (A.get(l).getLandingPriority() < A.get(i).getLandingPriority())) {
			least = l;
		} else {
			least = i;
		}

		//Identify whether parent of right child > right child  
		if ((r <= A.size() - 1) && (A.get(r).getLandingPriority() < A.get(least).getLandingPriority())) {
			least = r;
		}

		//If the parent is > than any of its children, Swap them until tree if fixed
		if (least != i) {

			// Swap A[i] with A[least]
			Aircraft temp = A.get(i);
			A.set(i, A.get(least));
			A.set(least, temp);

			MinHeapify(least);
		}
	}
	
	// For debbuging
	public void printQueue() {
		System.out.println("Index      FlightID    Arrival Time     Priority");
		for (int i = 0; i < A.size(); i++) {
			System.out.print(i + "      " + A.get(i).getFlightID() + "    "+ A.get(i).getArrivalTime() + "          " + A.get(i).getLandingPriority());
			System.out.println();
		}

	}

	public boolean isEmpty() {
		return (A.size() == 0);

	}
	
}
