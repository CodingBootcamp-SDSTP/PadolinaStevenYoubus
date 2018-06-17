public class Route
{
	final int ID;
	String name;
	String origin;
	String destination;
	int distance;
	int duration;
	String rate;

	public Route(int id, String name, String origin, String destination, int distance, int duration, String rate) {
		ID = id;
		this.name = name;
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
		this.duration = duration;
		this.rate = rate;
	}

	public String getName() {
		return(name);
	}

	public String getOrigin() {
		return(origin);
	}

	public String getDestination() {
		return(destination);
	}

	public int getDistance() {
		return(distance);
	}

	public int getDuration() {
		return(duration);
	}

	public String getRate() {
		return(rate);
	}
}
