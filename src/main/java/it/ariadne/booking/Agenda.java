package it.ariadne.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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
}
