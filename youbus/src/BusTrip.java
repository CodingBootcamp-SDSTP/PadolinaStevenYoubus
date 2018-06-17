import java.time.LocalDate;
import java.time.LocalTime;

public class BusTrip
{
	public static class Builder {
		private int id;
		private String route;
		private String origin;
		private String destination;
		private String bus;
		private String driver;
		private String conductor;
		private LocalDate date;
		private LocalTime departureTime;
		private int duration;
		private int rate;
		private int availableSeats;

		public Builder id(int id) {
			this.id = id;
			return(this);
		}

		public Builder route(String route) {
			this.route = route;
			return(this);
		}

		public Builder origin(String origin) {
			this.origin = origin;
			return(this);
		}

		public Builder destination(String destination) {
			this.destination = destination;
			return(this);
		}

		public Builder bus(String bus) {
			this.bus = bus;
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

		public Builder date(LocalDate date) {
			this.date = date;
			return(this);
		}

		public Builder departureTime(LocalTime departureTime) {
			this.departureTime = departureTime;
			return(this);
		}

		public Builder duration(int duration) {
			this.duration = duration;
			return(this);
		}

		public Builder rate(int rate) {
			this.rate = rate;
			return(this);
		}

		public Builder availableSeats(int availableSeats) {
			this.availableSeats = availableSeats;
			return(this);
		}

		public int getId() {
			return(id);
		}

		public String getRoute() {
			return(route);
		}

		public String getOrigin() {
			return(origin);
		}

		public String getDestination() {
			return(destination);
		}

		public String getBus() {
			return(bus);
		}

		public String getDriver() {
			return(driver);
		}

		public String getConductor() {
			return(conductor);
		}

		public LocalDate getDate() {
			return(date);
		}

		public LocalTime getDepartureTime() {
			return(departureTime);
		}

		public int getDuration() {
			return(duration);
		}

		public int getRate() {
			return(rate);
		}

		public int getAvailableSeats() {
			return(availableSeats);
		}

		public BusTrip build() {
			return(new BusTrip(this));
		}
	}

	final int ID;
	final String ROUTE;
	final String ORIGIN;
	final String DESTINATION;
	final String BUS;
	final String DRIVER;
	final String CONDUCTOR;
	final LocalDate DATE;
	final LocalTime DEPARTURE_TIME;
	final int DURATION;
	final int RATE;
	final int AVAILABLE_SEATS;

	public BusTrip(Builder builder) {
		ID = builder.getId();
		ROUTE = builder.getRoute();
		ORIGIN = builder.getOrigin();
		DESTINATION = builder.getDestination();
		BUS = builder.getBus();
		DRIVER = builder.getDriver();
		CONDUCTOR = builder.getConductor();
		DATE = builder.getDate();
		DEPARTURE_TIME = builder.getDepartureTime();
		DURATION = builder.getDuration();
		RATE = builder.getRate();
		AVAILABLE_SEATS = builder.getAvailableSeats();
	}
}
