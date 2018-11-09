package it.ariadne.booking;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public interface Resource {
	
    static boolean getAvailability(DateTime start, DateTime end, List<Booking> bookings) {
		Interval interval = new Interval(start, end);
		for (Booking b: bookings) {
			if (b.getInterval().overlaps(interval)) {
				return false;
			}
		}
		return true;
	}
    
    boolean addBooking(DateTime start, DateTime end, String id);
	boolean getAvailability(DateTime start, DateTime end);
	boolean deleteBooking(String id);
}
