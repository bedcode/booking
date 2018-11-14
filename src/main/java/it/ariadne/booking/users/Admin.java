package it.ariadne.booking.users;

import java.util.List;
import java.util.Map;

import it.ariadne.booking.Agenda;
import it.ariadne.booking.Booking;
import it.ariadne.booking.Resource;

public class Admin extends AbstractUser {

	public Admin(String name, String surname, String email, String password) {
		super(name, surname, email, password);
	}

	public String printBookingsResource(Agenda a) {
		String s = "";
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		for (Map.Entry<Resource, List<Booking>> map : agenda.entrySet()) {
			List<Booking> list = map.getValue();
			s += "\nLe prenotazioni per la risorsa " + map.getKey().getClass().getSimpleName() + " sono:\n";
			for (Booking b: list) {
				s += b.toString() + "\n";
			}
		}
		
		
		return s;
	}
	
	public String printBookingUsers(User u) {
		String s = "";
		return s;
	}
}
