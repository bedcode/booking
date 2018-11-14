package it.ariadne.booking.users;

public abstract class AbstractUser {

	private String name;
	private String surname;
	private String email;
	private String password;

	public AbstractUser(String name, String surname, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

}
