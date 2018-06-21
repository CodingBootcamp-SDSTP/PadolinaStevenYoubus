public class BusType
{
	private int id;
	private String name;
	private boolean aircon;
	private boolean restroom;
	private int capacity;

	public BusType(int id, String name, boolean aircon, boolean restroom, int capacity) {
		this.id = id;
		this.name = name;
		this.aircon = aircon;
		this.restroom = restroom;
		this.capacity = capacity;
	}

	private int getId() {
		return(id);
	}

	private String getName() {
		return(name);
	}

	private boolean getAircon() {
		return(aircon);
	}

	private boolean getRestRoom() {
		return(restroom);
	}

	private int getCapacity() {
		return(capacity);
	}
}
