package it.ariadne.booking;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import it.ariadne.booking.resource.Car;

public class AskBookingTest {

	@Test
	public void test() {
		List<Booking> bookings = new ArrayList<>();
		Resource car = new Car(bookings);
		DateTime start1 = new DateTime(2018, 11, 9, 14, 30);
		DateTime end1 = new DateTime(2018, 11, 9, 16, 30);
		boolean success = car.addBooking(start1, end1, "1");
		assertEquals("La risorsa è prenotata", true, success);
		
		DateTime start2 = new DateTime(2018, 11, 9, 15, 30);
		DateTime end2 = new DateTime(2018, 11, 9, 17, 30);
		success = car.addBooking(start2, end2, "2");
		assertEquals("La risorsa è prenotata", false, success);
		
		boolean available = car.getAvailability(start2, end2);
		assertEquals("La risorsa è disponibile?", false, available);
		DateTime s = new DateTime(2018, 1, 29, 15, 30);
		DateTime e = new DateTime(2018, 1, 29, 17, 30);
		available = car.getAvailability(s, e);
		assertEquals("La risorsa è disponibile?", true, available);
	}
	
	@Test
	public void deleteBooking() {
		List<Booking> bookings = new ArrayList<>();
		Resource car = new Car(bookings);
		DateTime start = new DateTime(2018, 11, 9, 14, 30);
		DateTime end = new DateTime(2018, 11, 9, 16, 30);
		car.addBooking(start, end, "1");
		boolean success = car.deleteBooking("1");
		assertEquals("La prenotazione è stata cancellata", true, success);
		success = car.deleteBooking("1");
		assertEquals("La prenotazione è stata cancellata", false, success);
	}

}
