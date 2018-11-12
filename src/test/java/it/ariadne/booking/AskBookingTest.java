package it.ariadne.booking;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import it.ariadne.booking.resource.Car;

public class AskBookingTest {

	@Test
	public void test() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
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
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime start = new DateTime(2018, 11, 9, 14, 30);
		DateTime end = new DateTime(2018, 11, 9, 16, 30);
		agenda.addBooking(start, end, car, "1");
		boolean success = agenda.deleteBooking(car, "1");
		assertEquals("La prenotazione è stata cancellata", true, success);
		success = agenda.deleteBooking(car, "1");
		assertEquals("La prenotazione è stata cancellata", false, success);
	}

	@Test
	public void searchAvailability() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime now = new DateTime();
		DateTime end = now.plusHours(3);
		Period period = new Period(now, end);
		agenda.addBooking(now, end, car, "1");
		DateTime availability = agenda.searchAvailability(car, period);
		assertEquals("La prima data disponibile è", now.getHourOfDay() + 3, availability.getHourOfDay());
		DateTime start = new DateTime();
		start = now.plusHours(3);
		end = now.plusHours(6);
		period = new Period(start, end);
		availability = agenda.searchAvailability(car, period);
		assertEquals("La prima data disponibile è", start.getHourOfDay(), availability.getHourOfDay());

		start = new DateTime(2018, 12, 12, 9, 00);
		end = new DateTime(2018, 12, 13, 12, 00);
		DateTime startBook = new DateTime(2018, 12, 12, 9, 00);
		DateTime endBook = new DateTime(2018, 12, 12, 12, 00);
		availability = agenda.searchAvailability(start, end, car, period);
		assertEquals("La prima data disponibile è", start.getHourOfDay(), availability.getHourOfDay());
		agenda.addBooking(startBook, endBook, car, "2");
		start = new DateTime(2018, 12, 12, 9, 00);
		end = new DateTime(2018, 12, 12, 11, 00);
		availability = agenda.searchAvailability(start, end, car, period);
		assertEquals("La prima data disponibile è", null, availability);
	}

	@Test
	public void searchAvailabilityConstraint() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime start = new DateTime(2018, 11, 12, 14, 00);
		DateTime end = new DateTime(2018, 11, 12, 17, 00);
		String resourceName = "Car";
		int constraint = 5;
		DateTime availability = agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", null, availability);
		constraint = 4;
		DateTime now = new DateTime();
		availability = agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", now.getHourOfDay(), availability.getHourOfDay());
		
		end = now.plusHours(3);
		agenda.addBooking(now, end, car, "1");
		availability = agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", now.getHourOfDay() + 3, availability.getHourOfDay());
		
		resourceName = "Aula";
		availability = agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", null, availability);
	}
}
