package it.ariadne.booking.users;

import org.joda.time.DateTime;
import org.joda.time.Period;

import it.ariadne.booking.Agenda;
import it.ariadne.booking.Resource;

public class User extends AbstractUser {

	public User(String name, String surname, String email, String password) {
		super(name, surname, email, password);
	}

	public boolean getAvailability(Agenda agenda, DateTime start, DateTime end, Resource r) {
		return agenda.getAvailability(end, end, r);
	}

	public boolean addBooking(Agenda agenda, DateTime start, DateTime end, Resource r, String id) {
		return agenda.addBooking(start, end, r, id, this);
	}

	public boolean deleteBooking(Agenda agenda, Resource r, String id) {
		return agenda.deleteBooking(r, id);
	}

	public DateTime searchAvailability(Agenda agenda, Resource r, Period period) {
		return agenda.searchAvailability(r, period);
	}

	public DateTime searchAvailability(Agenda agenda, DateTime start, DateTime end, Resource r, Period period) {
		return agenda.searchAvailability(start, end, r, period);
	}

	public DateTime searchAvailabilityConstraint(Agenda agenda, DateTime start, DateTime end, String resourceName,
			int constraint) {
		return agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
	}
}
