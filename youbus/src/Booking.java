public class Booking
{
	final int ID;
	private String tripID;
	private String busID;
	private String seatID;
	private String user;

	public Booking(int id, String tripID, String busID, String seatID, String user) {
		ID = id;
		this.tripID = tripID;
		this.busID = busID;
		this.seatID = seatID;
		this.user = user;
	}

	public String getUser() {
		return(user);
	}

	public String getTripID() {
		return(tripID);
	}

	public String getBusID() {
		return(busID);
	}

	public String getSeatID() {
		return(seatID);
	}
}
