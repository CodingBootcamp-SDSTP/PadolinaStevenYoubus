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
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT tripstatus from tbtrips where busid=? and tripstatus='pending'");
			stmt.setString(1, trip.BUS);
			rs = stmt.executeQuery();
			if(rs.next()) {
				flag = false;
			}
			else {
				stmt = conn.prepareStatement("INSERT INTO tbtrips ( routeid, busid, tripdate, departure, availableseats, tripname ) VALUES ( ?, ?, ?, ?, (SELECT capacity from viewbuses where busid=?), ?);");
				stmt.setString(1, trip.ROUTE);
				stmt.setString(2, trip.BUS);
				stmt.setString(3, trip.DATE.toString());
				stmt.setString(4, trip.DEPARTURE_TIME.toString());
				stmt.setString(5, trip.BUS);
				stmt.setString(6, trip.NAME);
				stmt.executeUpdate();
				flag = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
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
			stmt = conn.prepareStatement("INSERT INTO tbbuses ( plateno, driverid, conductorid, bustype, busname ) VALUES ( ?, ?, ?, ?, ? );");
			stmt.setString(1, bus.PLATE_NO);
			stmt.setString(2, bus.DRIVER);
			stmt.setString(3, bus.CONDUCTOR);
			stmt.setString(4, bus.BUS_TYPE);
			stmt.setString(5, bus.NAME);
			stmt.executeUpdate();
			stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int i = rs.getInt("LAST_INSERT_ID()");
				flag = addSeats(bus.BUS_TYPE, i, bus.getLayout());
				if(flag == false) {
					stmt = conn.prepareStatement("DELETE FROM tbbuses where busid=?;");
					stmt.setInt(1, i);
					stmt.executeUpdate();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public String addUser(User user) {
		String flag = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbusers ( firstname, lastname, sex, birthdate ) VALUES ( ?, ?, ?, ? );");
			stmt.setString(1, user.FIRSTNAME);
			stmt.setString(2, user.LASTNAME);
			stmt.setString(3, user.SEX);
			stmt.setString(4, user.BIRTHDATE.toString());
			stmt.executeUpdate();
			stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
			rs = stmt.executeQuery();
			if(rs.next()) {
				flag = rs.getString("LAST_INSERT_ID()");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean addUserAccount(UserAccount account) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbuseraccounts ( userid, email, username, password, type ) VALUES ( ?, ?, ?, ?, ? );");
			stmt.setString(1, account.getUserID());
			stmt.setString(2, account.getEmail());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword());
			stmt.setString(5, account.getType());
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

	public boolean addSeats(String type, int id, String[][] layout) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT capacity FROM tbbustypes where bustypeid=?;");
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			if(rs.next()) {
				int capacity = rs.getInt("capacity");
				stmt = conn.prepareStatement("INSERT into tbbusseats ( busid, seatno ) VALUES ( ?, ? );");
				for(int i = 0; i < layout.length; i++) {
					for(int j = 0; j < layout[i].length; j++) {
						stmt.setInt(1, id);
						stmt.setString(2, layout[i][j]);
						stmt.executeUpdate();
					}
				}
				flag = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
		}
		return(flag);
	}

	public boolean bookTrip(Booking booking) {
		Boolean flag = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT seatid from tbbookings where tripid=? and seatid=?;");
			stmt.setString(1, booking.getTripID());
			stmt.setString(2, booking.getSeatID());
			rs = stmt.executeQuery();
			if(rs.next()) {
				flag = false;
			}
			else {
				stmt = conn.prepareStatement("INSERT INTO tbbookings ( tripid, busid, seatid, userid ) VALUES ( ?, ?, ?, ? );");
				stmt.setString(1, booking.getTripID());
				stmt.setString(2, booking.getBusID());
				stmt.setString(3, booking.getSeatID());
				stmt.setString(4, booking.getUser());
				stmt.executeUpdate();
				stmt = conn.prepareStatement("UPDATE tbbusseats SET occupied=true WHERE seatid=?;");
				stmt.setString(1, booking.getSeatID());
				stmt.executeUpdate();
				stmt = conn.prepareStatement("UPDATE tbtrips SET availableseats=availableseats-1 WHERE tripid=?;");
				stmt.setString(1, booking.getTripID());
				stmt.executeUpdate();
				flag = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {};
			try { if(rs != null) rs.close(); } catch (Exception e) {};
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

	public ArrayList<BusSeat> getSeats(String flag, String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<BusSeat> seats = new ArrayList<BusSeat>();
		try {
			if("busid".equals(flag)) {
				stmt = conn.prepareStatement("SELECT * FROM tbbusseats where busid=?;");
			}
			else {
				stmt = conn.prepareStatement("SELECT * FROM tbbusseats where seatid=?;");
			}
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				seats.add(new BusSeat(rs.getInt("seatid"), rs.getString("busid"), rs.getString("seatno"), rs.getBoolean("occupied")));
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
		return(seats);
	}

	public ArrayList<BusTrip> getAllTrips() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<BusTrip> trips = new ArrayList<BusTrip>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM viewtrips;");
			while(rs.next()) {
				trips.add(new BusTrip.Builder().id(rs.getInt("tripid")).name(rs.getString("tripname")).bus(rs.getString("busname")).busID(rs.getInt("busid")).driver(rs.getString("driver")).conductor(rs.getString("conductor")).origin(rs.getString("origin")).destination(rs.getString("destination")).date(LocalDate.parse(rs.getString("tripdate"))).departureTime(LocalTime.parse(rs.getString("departure"))).duration(rs.getInt("duration")).rate(rs.getInt("rate")).capacity(rs.getInt("capacity")).availableSeats(rs.getInt("availableseats")).build());
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
				buses.add(new Bus.Builder().id(rs.getInt("busid")).name(rs.getString("busname")).plateNo(rs.getString("plateno")).driver(rs.getString("driver")).conductor(rs.getString("conductor")).busType(rs.getString("bustype")).aircon(rs.getBoolean("aircon")).restroom(rs.getBoolean("restroom")).capacity( rs.getInt("capacity")).build());
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

	public ArrayList<BusType> getAllBuseTypes() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<BusType> busTypes = new ArrayList<BusType>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM tbbustypes;");
			while(rs.next()) {
				busTypes.add(new BusType(rs.getInt("bustypeid"), rs.getString("bustypename"), rs.getBoolean("aircon"), rs.getBoolean("restroom"), rs.getInt("capacity")));
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
		return(busTypes);
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

	public UserAccount getAccount(String username) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		UserAccount account = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM tbuseraccounts where username=?;");
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			while(rs.next()) {
				account = new UserAccount(rs.getInt("accountid"), rs.getString("userid"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
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
		return(account);
	}

	public User getUser(String userID) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM tbusers where userid=?;");
			stmt.setString(1, userID);
			rs = stmt.executeQuery();
			while(rs.next()) {
				user = new User(rs.getInt("userid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("sex"), LocalDate.parse(rs.getString("birthdate")));
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
		return(user);
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

	public boolean createObject(String fileLine) throws Exception {
		String[] details = fileLine.split("~");
		int len = details.length - 1;
		boolean success = false;
		String d = details[len];
		switch(d) {
			case "route":
				success = addRoute(new Route(1, details[0], details[1], details[2], Integer.parseInt(details[3]), Integer.parseInt(details[4]), details[5]));
				break;
			case "bus":
				success = addBus(new Bus.Builder().name(details[0]).plateNo(details[1]).driver(details[2]).conductor(details[3]).busType(details[4]).build());
				break;
			case "trip":
				success = addTrip(new BusTrip.Builder().name(details[4]).route(details[0]).bus(details[1]).date(LocalDate.parse(details[2])).departureTime(LocalTime.parse(details[3])).build());
				break;
			case "Driver":
				success = addCrew(new BusDriver(1, details[0], details[1], details[2], LocalDate.parse(details[3]), details[4]));
				break;
			case "Conductor":
				success = addCrew(new BusConductor(1, details[0], details[1], details[2], LocalDate.parse(details[3])));
				break;
			case "location":
				success = addLocation(new Location(1, details[0], details[1]));
				break;
			case "booking":
				success = bookTrip(new Booking(1, details[0], details[1], details[2], details[3]));
				break;
			case "user":
				String userID = addUser(new User(1, details[0], details[1], details[2], LocalDate.parse(details[3])));
				if(userID != null) {
					success = addUserAccount(new UserAccount(1, userID, details[4], details[5], details[6], details[7]));
				}
				break;
		}
		return(success);
	}
}
