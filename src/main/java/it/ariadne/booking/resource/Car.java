package it.ariadne.booking.resource;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import it.ariadne.booking.Booking;
import it.ariadne.booking.Resource;

public class Car implements Resource {
	
	private List<Booking> bookings = new ArrayList<>();
	
	public Car(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	@Override
	public boolean addBooking(DateTime start, DateTime end, String id) {
		if (this.getAvailability(start, end)) {
			Interval interval = new Interval(start, end);
			Booking booking = new Booking(id, interval);
			booking.setInterval(interval);
			this.bookings.add(booking);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getAvailability(DateTime start, DateTime end) {
		return Resource.getAvailability(start, end, this.bookings);
	}

	@Override
	public boolean deleteBooking(String id) {
		for (Booking b: bookings) {
			if (b.getId().equals(id)) {
				bookings.remove(b);
				return true;
			}
		}
		return false;
	}
}
