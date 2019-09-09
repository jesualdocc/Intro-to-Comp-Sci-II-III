import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		double distance1 = v1.getDistance();
		double distance2 = v2.getDistance();
		
		if (distance1 > distance2) {
			return 1;
		} else if (distance1 < distance2) {
			return -1;
		}

		else {
			return 0;
		}
	}

}
