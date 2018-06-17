public class Bus
{
	final int ID;
	private String name;
	private String plateNo;
	private String driver;
	private String conductor;
	private int capacity;

	public Bus(int id, String name, String plateNo, String driver, String conductor, int capacity) {
		ID = id;
		this.name = name;
		this.plateNo = plateNo;
		this.driver = driver;
		this.conductor = conductor;
		this.capacity = capacity;
	}

	public String getName() {
		return(name);
	}

	public String getPlateNo() {
		return(plateNo);
	}

	public String getDriver() {
		return(driver);
	}
	
	public String getConductor() {
		return(conductor);
	}

	public int getCapacity() {
		return(capacity);
	}
}
