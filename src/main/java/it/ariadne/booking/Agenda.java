package it.ariadne.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class Agenda {

	private Map<Resource, List<Booking>> agenda = new HashMap<>();
	
	public boolean getAvailability(DateTime start, DateTime end, Resource r) {
		Interval interval = new Interval(start, end);
		List<Booking> bookings = this.agenda.get(r);
		for (Booking b: bookings) {
			if (b.getInterval().overlaps(interval)) {
				return false;
			}
		}
		return true;
	}

}
