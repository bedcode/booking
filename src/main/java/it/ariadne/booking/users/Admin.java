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
			for (Booking b : list) {
				s += b.toString() + "\n";
			}
		}
		return s;
	}

	public String printBookingsUsers(Agenda a, List<User> users) {
		String s = "";
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		for (User u : users) {
			s += a.printAllBookings(u);
		}
		return s;
	}

	public List<Booking> addResource(Resource r, Agenda a) {
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		if (agenda.containsKey(r)) {
			return null;
		} else {
			return a.addResource(r);
		}
	}

	public String readResource(Resource resource, Agenda a) {
		String s = "";
		s += "Risorsa " + resource.getClass().getSimpleName() + " limite: " + resource.getConstraint();
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		for (Map.Entry<Resource, List<Booking>> map : agenda.entrySet()) {
			Resource r = map.getKey();
			if (r.getClass().getSimpleName().equals(resource.getClass().getSimpleName())) {
				List<Booking> list = map.getValue();
				s += "\nLe prenotazioni per la risorsa " + map.getKey().getClass().getSimpleName() + " sono:\n";
				for (Booking b : list) {
					s += b.toString() + "\n";
				}
			}
		}
		return s;
	}
	
	public List<Booking> updateResource(Resource rOld, Resource rNew, Agenda a) {
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		if (agenda.containsKey(rOld)) {
			List<Booking> l = agenda.remove(rOld);
			agenda.put(rNew, l);
			return l;
		} else {
			return null;
		}
	}
	
	public boolean deleteResource(Resource r, Agenda a) {
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		if (agenda.containsKey(r)) {
			agenda.remove(r);
			return true;
		} else {
			return false;
		}
	}
 }
