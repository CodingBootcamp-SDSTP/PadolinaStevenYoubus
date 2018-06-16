import java.time.LocalDate;

public class BusCrew
{
	final int ID;
	private String lastname;
	private String firstname;
	private String sex;
	private LocalDate birthdate;
	
	public BusCrew(int id, String lastname, String firstname, String sex, LocalDate birthdate) {
		ID = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.sex = sex;
		this.birthdate = birthdate;
	}

	public String getLastname() {
		return(lastname);
	}

	public String getFirstname() {
		return(firstname);
	}

	public LocalDate getBirthdate() {
		return(birthdate);
	}

	public String getSex() {
		return(sex);
	}
}
