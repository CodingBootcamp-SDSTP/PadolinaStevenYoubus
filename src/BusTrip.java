import java.time.LocalDate;
import java.time.LocalTime;

public class BusTrip
{
	final int ID;
	private int route;
	private int bus;
	private LocalDate date;
	private LocalTime departureTime;

	public BusTrip(int id, int route, int bus, LocalDate date, LocalTime departureTime) {
		ID = id;
		this.route = route;
		this.bus = bus;
		this.date = date;
		this.departureTime = departureTime;
	}

	public int getRoute() {
		return(route);
	}

	public int getBus() {
		return(bus);
	}

	public LocalDate getDate() {
		return(date);
	}

	public LocalTime getDepartureTime() {
		return(departureTime);
	}
}
