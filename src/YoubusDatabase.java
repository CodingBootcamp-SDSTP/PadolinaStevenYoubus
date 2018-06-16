import java.sql.*;

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
			stmt = conn.prepareStatement("INSERT INTO tbtrips ( routeid, busid, tripdate, departure ) VALUES ( ?, ?, ?, ? );");
			stmt.setInt(1, trip.getRoute());
			stmt.setInt(2, trip.getBus());
			stmt.setString(3, trip.getDate().toString());
			stmt.setString(4, trip.getDepartureTime().toString());
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
			stmt.setInt(2, route.getOrigin());
			stmt.setInt(3, route.getDestination());
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
			stmt.setInt(2, bus.getDriverID());
			stmt.setInt(3, bus.getConductorID());
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
}
