public class Location
{
	final int ID;
	private String name;
	private String city;

	public Location(int id, String name, String city) {
		ID = id;
		this.name = name;
		this.city = city;
	}

	public String getName() {
		return(name);
	}

	public String getCity() {
		return(city);
	}
}
