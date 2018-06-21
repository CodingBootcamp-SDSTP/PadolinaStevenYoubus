public class BusSeat
{
	final int ID;
	private String busID;
	private String seatNo;
	private boolean occupied;

	public BusSeat(int id, String busID, String seatNo, boolean occupied) {
		ID = id;
		this.busID = busID;
		this.seatNo = seatNo;
		this.occupied = occupied;
	}

	public String getBusID() {
		return(busID);
	}

	public String getSeatNo() {
		return(seatNo);
	}

	public boolean occupied() {
		return(occupied);
	}
}
