import java.time.LocalDate;

public class BusDriver extends BusCrew
{
	private String dLicense;

	public BusDriver(int id, String lastname, String firstname, String sex, LocalDate birthdate, String dLicense) {
		super(id, lastname, firstname, sex, birthdate);
		this.dLicense = dLicense;
	}

	public String getDLicense() {
		return(dLicense);
	}
}
