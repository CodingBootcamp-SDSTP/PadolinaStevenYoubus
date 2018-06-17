import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class YoubusDatabase
{
	public static YoubusDatabase _instance = null;

	public static YoubusDatabase instance() {
		if(_instance == null) {
			_instance = new YoubusDatabase();
		}
		return(_instance);
	}

	private Connection conn;

	public YoubusDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/dbyoubus?user=rufe05&" +
				"password=abc123&serverTimezone=UTC&useSSL=false");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteFromDB(String table, int id) {
		Boolean flag = false;
		String a = (id == -1) ? "" : "where conductorid = " + id;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM ? ?;");
			stmt.setString(1, table);
			stmt.setString(2, a);
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addCrew(BusCrew crew) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			if(crew instanceof BusDriver) {
				BusDriver d = (BusDriver)crew;
				stmt = conn.prepareStatement("INSERT INTO tbdrivers ( firstname, lastname, sex, birthdate, dlicense ) VALUES ( ?, ?, ?, ?, ? );");
				stmt.setString(5, d.getDLicense());
			}
			else {
				stmt = conn.prepareStatement("INSERT INTO tbconductors ( firstname, lastname, sex, birthdate ) VALUES ( ?, ?, ?, ? );");
			}
			stmt.setString(1, crew.getFirstname());
			stmt.setString(2, crew.getLastname());
			stmt.setString(3, crew.getSex());
			stmt.setString(4, crew.getBirthdate().toString());
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addTrip(BusTrip trip) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbtrips ( routeid, busid, tripdate, departure, availableseats ) VALUES ( ?, ?, ?, ?, (SELECT capacity from tbbuses where busid=?));");
			stmt.setString(1, trip.ROUTE);
			stmt.setString(2, trip.BUS);
			stmt.setString(3, trip.DATE.toString());
			stmt.setString(4, trip.DEPARTURE_TIME.toString());
			stmt.setString(5, trip.BUS);
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addRoute(Route route) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbroutes ( routename, origin, destination, distance, duration, rate ) VALUES ( ?, ?, ?, ?, ?, ? );");
			stmt.setString(1, route.getName());
			stmt.setString(2, route.getOrigin());
			stmt.setString(3, route.getDestination());
			stmt.setInt(4, route.getDistance());
			stmt.setInt(5, route.getDuration());
			stmt.setString(6, route.getRate());
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addLocation(Location location) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tblocations ( locationname, city ) VALUES ( ?, ? );");
			stmt.setString(1, location.getName());
			stmt.setString(2, location.getCity());
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addBus(Bus bus) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbbuses ( plateno, driverid, conductorid, capacity, busname ) VALUES ( ?, ?, ?, ?, ? );");
			stmt.setString(1, bus.getPlateNo());
			stmt.setString(2, bus.getDriver());
			stmt.setString(3, bus.getConductor());
			stmt.setInt(4, bus.getCapacity());
			stmt.setString(5, bus.getName());
			stmt.executeUpdate();
			flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public void printAllCrew() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM tbdrivers;");
			System.out.format("%-5s  %-20s  %-20s  %-10s  %-6s  %-10s  %-15s\n", "ID", "FIRSTNAME", "LASTNAME", "BIRTHDATE", "SEX", "TYPE", "DRIVER'S LICENSE");
			while(rs.next()) {
				System.out.format("%-5s  %-20s  %-20s  %-10s  %-6s  %-10s  %-15s\n", rs.getString("driverid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birthdate"), rs.getString("sex"), "driver", rs.getString("dlicense"));
			}
			rs = stmt.executeQuery("SELECT * FROM tbconductors;");
			while(rs.next()) {
				System.out.format("%-5s  %-20s  %-20s  %-10s  %-6s  %-10s  %-15s\n", rs.getString("conductorid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birthdate"), rs.getString("sex"), "conductor", "n/a");
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
	}

	public ArrayList<BusTrip> getAllTrips() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<BusTrip> trips = new ArrayList<BusTrip>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM viewtrips;");
			while(rs.next()) {
				trips.add(new BusTrip.Builder().id(rs.getInt("tripid")).bus(rs.getString("busname")).driver(rs.getString("driver")).conductor(rs.getString("conductor")).origin(rs.getString("origin")).destination(rs.getString("destination")).date(LocalDate.parse(rs.getString("tripdate"))).departureTime(LocalTime.parse(rs.getString("departure"))).duration(rs.getInt("duration")).rate(rs.getInt("rate")).availableSeats(rs.getInt("availableseats")).build());
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(trips);
	}

	public ArrayList<Bus> getAllBuses() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Bus> buses = new ArrayList<Bus>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM viewbuses;");
			while(rs.next()) {
				buses.add(new Bus(rs.getInt("busid"), rs.getString("busname"), rs.getString("plateno"), rs.getString("driver"), rs.getString("conductor"), rs.getInt("capacity")));
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(buses);
	}

	public ArrayList<Route> getAllRoutes() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM viewroutes;");
			while(rs.next()) {
				routes.add(new Route(rs.getInt("routeid"), rs.getString("routename"), rs.getString("origin"), rs.getString("destination"), rs.getInt("distance"), rs.getInt("duration"), rs.getString("rate")));
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(routes);
	}

	public ArrayList<Location> getAllLocations() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Location> locations = new ArrayList<Location>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM tblocations;");
			while(rs.next()) {
				locations.add(new Location(rs.getInt("locationid"), rs.getString("locationname"), rs.getString("city")));
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(locations);
	}

	public ArrayList<BusCrew> getAllCrews(int flag) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<BusCrew> crews = new ArrayList<BusCrew>();
		try {
			stmt = conn.createStatement();
			if(flag == 0 || flag == 2) {
				rs = stmt.executeQuery("SELECT * FROM tbdrivers;");
				while(rs.next()) {
					crews.add(new BusDriver(rs.getInt("driverid"), rs.getString("lastname"), rs.getString("firstname"), rs.getString("sex"), LocalDate.parse(rs.getString("birthdate")), rs.getString("dlicense")));
				}
			}
			if(flag == 1 || flag == 2) {
				rs = stmt.executeQuery("SELECT * FROM tbconductors;");
				while(rs.next()) {
					crews.add(new BusConductor(rs.getInt("conductorid"), rs.getString("lastname"), rs.getString("firstname"), rs.getString("sex"), LocalDate.parse(rs.getString("birthdate"))));
				}
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(crews);
	}
}
