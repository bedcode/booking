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
		Agenda agenda = new Agenda();
		Resource car = new Car();
		agenda.addResource(car);
		List<Booking> bookings = new ArrayList<>();
		DateTime start1 = new DateTime(2018, 11, 9, 14, 30);
		DateTime end1 = new DateTime(2018, 11, 9, 16, 30);
		boolean success = agenda.addBooking(start1, end1, car, "1");
		assertEquals("La risorsa è prenotata", true, success);

		DateTime start2 = new DateTime(2018, 11, 9, 15, 30);
		DateTime end2 = new DateTime(2018, 11, 9, 17, 30);
		success = agenda.addBooking(start2, end2, car, "2");
		assertEquals("La risorsa è prenotata", false, success);

		boolean available = agenda.getAvailability(start2, end2, car);
		assertEquals("La risorsa è disponibile?", false, available);
		DateTime s = new DateTime(2018, 1, 29, 15, 30);
		DateTime e = new DateTime(2018, 1, 29, 17, 30);
		available = agenda.getAvailability(s, e, car);
		assertEquals("La risorsa è disponibile?", true, available);
	}

	@Test
	public void deleteBooking() {
		Agenda agenda = new Agenda();
		Resource car = new Car();
		agenda.addResource(car);
		DateTime start = new DateTime(2018, 11, 9, 14, 30);
		DateTime end = new DateTime(2018, 11, 9, 16, 30);
		agenda.addBooking(start, end, car, "1");
		boolean success = agenda.deleteBooking(car, "1");
		assertEquals("La prenotazione è stata cancellata", true, success);
		success = agenda.deleteBooking(car, "1");
		assertEquals("La prenotazione è stata cancellata", false, success);
	}

}
