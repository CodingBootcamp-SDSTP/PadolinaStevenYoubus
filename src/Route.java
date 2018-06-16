public class Route
{
	final int ID;
	String name;
	int origin;
	int destination;
	int distance;
	int duration;
	String rate;

	public Route(int id, String name, int origin, int destination, int distance, int duration, String rate) {
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

	public int getOrigin() {
		return(origin);
	}

	public int getDestination() {
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
