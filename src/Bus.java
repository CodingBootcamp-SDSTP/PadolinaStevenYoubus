public class Bus
{
	final int ID;
	private String name;
	private String plateNo;
	private int driverID;
	private int conductorID;
	private int capacity;

	public Bus(int id, String name, String plateNo, int driverID, int conductorID, int capacity) {
		ID = id;
		this.name = name;
		this.plateNo = plateNo;
		this.driverID = driverID;
		this.conductorID = conductorID;
		this.capacity = capacity;
	}

	public String getName() {
		return(name);
	}

	public String getPlateNo() {
		return(plateNo);
	}

	public int getDriverID() {
		return(driverID);
	}
	
	public int getConductorID() {
		return(conductorID);
	}

	public int getCapacity() {
		return(capacity);
	}
}
