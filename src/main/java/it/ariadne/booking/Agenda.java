package it.ariadne.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import it.ariadne.booking.users.User;

public class Agenda {

	private Map<Resource, List<Booking>> agenda = new HashMap<>();

	public List<Booking> addResource(Resource r) {
		List<Booking> l = new ArrayList<>();
		agenda.put(r, l);
		return l;
	}

	/**
	 * It checks availability of a resource in a given period.
	 * 
	 * @param start start of the DateTime to check
	 * @param end end of the DateTime to check
	 * @param r Resource to check
	 * @return true is Resource is available, otherwise false
	 */
	public boolean getAvailability(DateTime start, DateTime end, Resource r) {
		Interval interval = new Interval(start, end);
		List<Booking> bookings = this.agenda.get(r);
		for (Booking b : bookings) {
			if (b.getInterval().overlaps(interval)) {
				return false;
			}
		}
		return true;
	}

	public boolean addBooking(DateTime start, DateTime end, Resource r, String id, User user) {
		if (this.getAvailability(start, end, r)) {
			Interval interval = new Interval(start, end);
			Booking booking = new Booking(id, interval, user);
			List<Booking> bookings = this.agenda.get(r);
			bookings.add(booking);
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteBooking(Resource r, String id) {
		List<Booking> bookings = this.agenda.get(r);
		for (Booking b : bookings) {
			if (b.getId().equals(id)) {
				bookings.remove(b);
				return true;
			}
		}
		return false;
	}

	/**
	 * It searches the first date when a Resource is available.
	 * 
	 * @param r Resource to check
	 * @param period Period to check
	 * @return DateTime when Resource is available, null if no bookings are available
	 */
	public DateTime searchAvailability(Resource r, Period period) {
		boolean condition = false;
		DateTime start = new DateTime();
		DateTime end = start.plus(period);
		while (condition == false) {
			condition = this.getAvailability(start, end, r);
			start = start.plusHours(1);
			end = end.plusHours(1);
		}
		if (condition) {
			return start.minusHours(1);
		}
		return null;
	}

	/**
	 * It searches the first date when a Resource is available in a given period.
	 * 
	 * @param start DateTime start of the interval in which checking the availability of the Resource
	 * @param end DateTime end of the interval in which checking the availability of the Resource
	 * @param r Resource to check
	 * @param period Period of the desired booking
	 * @return DateTime when Resource is available, null if no bookings are available
	 */
	public DateTime searchAvailability(DateTime start, DateTime end, Resource r, Period period) {
		boolean condition = false;
		while (condition == false && start.plus(period).isBefore(end)) {
			condition = this.getAvailability(start, end, r);
			start = start.plusHours(1);
			end = end.plusHours(1);
		}
		if (condition) {
			return start.minusHours(1);
		}
		return null;
	}

	/**
	 * It searches the first date when a Resource with a particular constraint is available.
	 * 
	 * @param start of the DateTime to check
	 * @param end end of the DateTime to check
	 * @param resourceName name of Resource type
	 * @param constraint constraint of the Resource
	 * @return DateTime when Resource is available, null if no bookings are available
	 */
	public DateTime searchAvailabilityConstraint(DateTime start, DateTime end, String resourceName, int constraint) {
		Period p = new Period(start, end);
		DateTime dt;
		for (Map.Entry<Resource, List<Booking>> map : this.agenda.entrySet()) {
			Resource r = map.getKey();
			if (r.getClass().getSimpleName().equals(resourceName) && r.getConstraint() >= constraint) {
				dt = this.searchAvailability(r, p);
				if (dt != null) {
					return dt;				}
			}
		}
		return null;
	}
	
	public String printAllBookings(User user) {
		String s = "Le prenotazioni di " + user.getName() + " " + user.getSurname() + " sono:\n";
		for (Map.Entry<Resource, List<Booking>> map : this.agenda.entrySet()) {
			List<Booking> list = map.getValue();
			for (Booking b: list) {
				if (b.getUser().equals(user)) {
					s += b.toStringWithoutUser() + "\n";
				}
			}
		}
		return s;
	}
	
	public String printFutureBookings(User user) {
		String s = "Le prenotazioni di " + user.getName() + " " + user.getSurname() + " sono:\n";
		for (Map.Entry<Resource, List<Booking>> map : this.agenda.entrySet()) {
			List<Booking> list = map.getValue();
			for (Booking b: list) {
				if (b.getUser().equals(user) && b.getInterval().isAfterNow()) {
					s += b.toStringWithoutUser() + "\n";
				}
			}
		}
		return s;
	}

	public Map<Resource, List<Booking>> getAgenda() {
		return agenda;
	}
}
