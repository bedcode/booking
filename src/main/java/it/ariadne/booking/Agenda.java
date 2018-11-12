package it.ariadne.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

public class Agenda {

	private Map<Resource, List<Booking>> agenda = new HashMap<>();

	public void addResource(Resource r) {
		List<Booking> l = new ArrayList<>();
		this.agenda.put(r, l);
	}

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

	public boolean addBooking(DateTime start, DateTime end, Resource r, String id) {
		if (this.getAvailability(start, end, r)) {
			Interval interval = new Interval(start, end);
			Booking booking = new Booking(id, interval);
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

	public DateTime searchAvailabilityConstraint(DateTime start, DateTime end, String resourceName, int constraint) {
		Period p = new Period(start, end);
		DateTime dt;
		for (Map.Entry<Resource, List<Booking>> map : this.agenda.entrySet()) {
			Resource r = map.getKey();
			if (r.getClass().getSimpleName().equals(resourceName) && r.getConstraint() >= constraint) {
				dt = this.searchAvailability(r, p);
				if (dt != null) {
					return dt;
				}
			}
		}
		return null;
	}
}
