package it.ariadne.booking;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import it.ariadne.booking.resource.Car;
import it.ariadne.booking.users.AbstractUser;
import it.ariadne.booking.users.Admin;
import it.ariadne.booking.users.User;

public class AskBookingTest {

	@Test
	public void test() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime start1 = new DateTime(2018, 11, 9, 14, 30);
		DateTime end1 = new DateTime(2018, 11, 9, 16, 30);
		boolean success = agenda.addBooking(start1, end1, car, "1", null);
		assertEquals("La risorsa è prenotata", true, success);

		DateTime start2 = new DateTime(2018, 11, 9, 15, 30);
		DateTime end2 = new DateTime(2018, 11, 9, 17, 30);
		success = agenda.addBooking(start2, end2, car, "2", null);
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
		agenda.addBooking(start, end, car, "1", null);
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
		agenda.addBooking(now, end, car, "1", null);
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
		agenda.addBooking(startBook, endBook, car, "2", null);
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
		agenda.addBooking(now, end, car, "1", null);
		availability = agenda.searchAvailabilityConstraint(now, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", now.getHourOfDay() + 3, availability.getHourOfDay());

		resourceName = "Aula";
		availability = agenda.searchAvailabilityConstraint(start, end, resourceName, constraint);
		assertEquals("La risorsa è disponibile", null, availability);
	}

	@Test
	public void users() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime start = new DateTime(2018, 11, 12, 14, 00);
		DateTime end = new DateTime(2018, 11, 12, 17, 00);
		User user = new User("Mario", "Rossi", "mario.rossi@gmail.com", "1234");

		boolean success = user.getAvailability(agenda, start, end, car);
		assertEquals("La risorsa è disponibile", true, success);

		success = user.addBooking(agenda, start, end, car, "1");
		assertEquals("La risorsa è prenotata", true, success);

		success = user.deleteBooking(agenda, car, "1");
		assertEquals("La risorsa è cancellata", true, success);

		DateTime now = new DateTime();
		end = now.plusHours(3);
		Period period = new Period(now, end);
		DateTime availability = user.searchAvailability(agenda, car, period);
		assertEquals("La prima data disponibile è", now.getHourOfDay(), availability.getHourOfDay());

		end = now.plusHours(1);
		period = new Period(now, end);
		start = now;
		end = now.plusHours(2);
		availability = user.searchAvailability(agenda, now, end, car, period);
		assertEquals("La prima data disponibile è", now.getHourOfDay(), availability.getHourOfDay());

		String resourceName = "Car";
		int constraint = 4;
		availability = user.searchAvailabilityConstraint(agenda, start, end, resourceName, constraint);
		assertEquals("La prima data disponibile è", start.getHourOfDay(), availability.getHourOfDay());
	}

	@Test
	public void print() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		agenda.addResource(car);
		DateTime start = new DateTime(2020, 11, 12, 14, 00);
		DateTime end = new DateTime(2020, 11, 12, 17, 00);
		List<User> users = new ArrayList<>();
		User user = new User("Mario", "Rossi", "mario.rossi@gmail.com", "1234");
		users.add(user);
		boolean success = user.addBooking(agenda, start, end, car, "1");
		assertEquals("La risorsa è prenotata", true, success);
		user.addBooking(agenda, start, end, car, "1");
		assertEquals("Stampa prenotazioni utente",
				"Le prenotazioni di Mario Rossi sono:\n"
						+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00\n",
				agenda.printFutureBookings(user));

		start = new DateTime(2018, 11, 12, 14, 00);
		end = new DateTime(2018, 11, 12, 17, 00);
		user.addBooking(agenda, start, end, car, "2");
		assertEquals("Stampa prenotazioni future utente",
				"Le prenotazioni di Mario Rossi sono:\n"
						+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00\n"
						+ "Prenotazione: 2 2018-11-12T14:00:00.000+01:00/2018-11-12T17:00:00.000+01:00\n",
				agenda.printAllBookings(user));

		Admin admin = new Admin("John", "Doe", "john.doe@gmail.com", "1234");
		assertEquals("Stampa prenotazioni per risorsa", "\nLe prenotazioni per la risorsa Car sono:\n"
				+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00 eseguita da Mario Rossi\n"
				+ "Prenotazione: 2 2018-11-12T14:00:00.000+01:00/2018-11-12T17:00:00.000+01:00 eseguita da Mario Rossi\n",
				admin.printBookingsResource(agenda));

		assertEquals("Stampa prenotazioni per utente",
				"Le prenotazioni di Mario Rossi sono:\n"
						+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00\n"
						+ "Prenotazione: 2 2018-11-12T14:00:00.000+01:00/2018-11-12T17:00:00.000+01:00\n",
				admin.printBookingsUsers(agenda, users));
	}

	@Test
	public void crud() {
		Agenda agenda = new Agenda();
		Resource car = new Car(4);
		Admin admin = new Admin("John", "Doe", "john.doe@gmail.com", "1234");
		List<Booking> l = admin.addResource(car, agenda);
		assertEquals("Risorsa aggiunta", true, l.isEmpty());

		l = admin.addResource(car, agenda);
		assertEquals("Risorsa non aggiunta", null, l);

		DateTime start = new DateTime(2020, 11, 12, 14, 00);
		DateTime end = new DateTime(2020, 11, 12, 17, 00);
		List<User> users = new ArrayList<>();
		User user = new User("Mario", "Rossi", "mario.rossi@gmail.com", "1234");
		user.addBooking(agenda, start, end, car, "1");
		User user2 = new User("Maria", "Rossi", "maria.rossi@gmail.com", "1234");
		user2.addBooking(agenda, start.plusDays(2), end.plusDays(2), car, "2");
		assertEquals("Lettura risorsa", "Risorsa Car limite: 4" + "\nLe prenotazioni per la risorsa Car sono:\n"
				+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00 eseguita da Mario Rossi\n"
				+ "Prenotazione: 2 2020-11-14T14:00:00.000+01:00/2020-11-14T17:00:00.000+01:00 eseguita da Maria Rossi\n",
				admin.readResource(car, agenda));

		Resource car2 = new Car(5);
		l = admin.updateResource(car, car2, agenda);
		assertEquals("Modifica risorsa", "Risorsa Car limite: 5" + "\nLe prenotazioni per la risorsa Car sono:\n"
				+ "Prenotazione: 1 2020-11-12T14:00:00.000+01:00/2020-11-12T17:00:00.000+01:00 eseguita da Mario Rossi\n"
				+ "Prenotazione: 2 2020-11-14T14:00:00.000+01:00/2020-11-14T17:00:00.000+01:00 eseguita da Maria Rossi\n",
				admin.readResource(car2, agenda));
		
		l = admin.updateResource(car, car2, agenda);
		assertEquals("Modifica risorsa", null, l);
		
		boolean success = admin.deleteResource(car2, agenda);
		assertEquals("Elimina risorsa", true, success);
		success = admin.deleteResource(car, agenda);
		assertEquals("Elimina risorsa", false, success);
	}
}
