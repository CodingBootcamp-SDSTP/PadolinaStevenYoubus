import java.time.LocalDate;

public class User
{
	final int ID;
	final String FIRSTNAME;
	final String LASTNAME;
	final String SEX;
	final LocalDate BIRTHDATE;

	public User(int id, String firstname, String lastname, String sex, LocalDate birthdate) {
		ID = id;
		FIRSTNAME = firstname;
		LASTNAME = lastname;
		SEX = sex;
		BIRTHDATE = birthdate;
	}
}
