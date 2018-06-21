public class Bus
{
	public static class Builder {
		private int id;
		private String name;
		private String plateNo;
		private String driver;
		private String conductor;
		private String busType;
		private boolean aircon;
		private boolean restroom;
		private int capacity;

		public Builder id(int id) {
			this.id = id;
			return(this);
		}

		public Builder name(String name) {
			this.name = name;
			return(this);
		}

		public Builder plateNo(String plateNo) {
			this.plateNo = plateNo;
			return(this);
		}

		public Builder driver(String driver) {
			this.driver = driver;
			return(this);
		}

		public Builder conductor(String conductor) {
			this.conductor = conductor;
			return(this);
		}

		public Builder busType(String busType) {
			this.busType = busType;
			return(this);
		}

		public Builder aircon(boolean aircon) {
			this.aircon = aircon;
			return(this);
		}

		public Builder restroom(boolean restroom) {
			this.restroom = restroom;
			return(this);
		}

		public Builder capacity(int capacity) {
			this.capacity = capacity;
			return(this);
		}

		public int getId() {
			return(id);
		}

		public boolean getAircon() {
			return(aircon);
		}

		public boolean getRestroom() {
			return(restroom);
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

		public String getBusType() {
			return(busType);
		}

		public int getCapacity() {
			return(capacity);
		}

		public Bus build() {
			return(new Bus(this));
		}
	}

	final int ID;
	final String NAME;
	final String PLATE_NO;
	final String DRIVER;
	final String CONDUCTOR;
	final String BUS_TYPE;
	final boolean AIRCON;
	final boolean RESTROOM;
	private String layout[][];
	final int CAPACITY;

	public Bus(Builder builder) {
		ID = builder.getId();
		NAME = builder.getName();
		PLATE_NO = builder.getPlateNo();
		DRIVER = builder.getDriver();
		CONDUCTOR = builder.getConductor();
		BUS_TYPE = builder.getBusType();
		AIRCON = builder.getAircon();
		RESTROOM = builder.getRestroom();
		CAPACITY = builder.getCapacity();
		if("1".equals(BUS_TYPE)) {
			layout = new String[][] {
				{"A1", "A2", "A3", null, null, "A6", "A7", "A8", "A9"},
				{null, null, null, null, null, null, null, null, "D9"},
				{"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9"},
				{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9"}
			};
		}
		else if("2".equals(BUS_TYPE)) {
			layout = new String[][] {
				{"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11"},
				{"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11"},
				{null, null, null, null, null, null, null, null, null, null, "E11"},
				{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11"},
				{"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11"}
			};
		}
		else if("3".equals(BUS_TYPE)) {
			layout = new String[][] {
				{"A1", "A2", "A3", "A4", "A5", "A6", "A7", null, "A9", "A10", "A11", "A12", "A13", "A14"},
				{"B1", "B2", "B3", "B4", "B5", "B6", "B7", null, "B9", "B10", "B11", "B12", "B13", "B14"},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, "A14"},
				{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14"},
				{"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14"}
			};
		}
	}

	public String[][] getLayout() {
		return(layout);
	}
}
