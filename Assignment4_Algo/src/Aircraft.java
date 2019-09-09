
public class Aircraft {

	//Characteristics of each aircraft
	private String flightID;
	private int arrivalTime;
	private int landingPriority;

	public Aircraft(String id, int time, int priority) {
			flightID = id;
			arrivalTime = time;
			landingPriority = priority;

	}

	public String getFlightID() {
		return flightID;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getLandingPriority() {
		return landingPriority;
	}

}
