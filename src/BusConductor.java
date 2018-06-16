import java.time.LocalDate;

public class BusConductor extends BusCrew
{
	public BusConductor(int id, String lastname, String firstname, String sex, LocalDate birthdate) {
		super(id, lastname, firstname, sex, birthdate);
	}
}
