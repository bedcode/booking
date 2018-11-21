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

	/**
	 * It prints all the bookings of a resource.
	 * 
	 * @param a an instance of Agenda
	 * @return a string
	 */
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

	/**
	 * It prints all the bookings of all the users.
	 * 
	 * @param a an instance of Agenda
	 * @param users list of Users
	 * @return a string
	 */
	public String printBookingsUsers(Agenda a, List<User> users) {
		String s = "";
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		for (User u : users) {
			s += a.printAllBookings(u);
		}
		return s;
	}

	/**
	 * It adds a resource.
	 * 
	 * @param r an instance of Resource
	 * @param a an instance of Agenda
	 * @return list of Booking
	 */
	public List<Booking> addResource(Resource r, Agenda a) {
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		if (agenda.containsKey(r)) {
			return null;
		} else {
			return a.addResource(r);
		}
	}

	/**
	 * It returns a string with all the bookings of a resource.
	 * 
	 * @param resource an instance of Resource
	 * @param a an instance of Agenda
	 * @return a string
	 */
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
	
	/**
	 * It updates a resource
	 * 
	 * @param r an instance of Resource
	 * @param constraint new constraint
	 * @param a an instance of Agenda
	 * @return true if the resource is updated, otherwise false
	 */
	public boolean updateResource(Resource r, int constraint, Agenda a) {
		Map<Resource, List<Booking>> agenda = a.getAgenda();
		if (agenda.containsKey(r)) {
			r.setConstraint(constraint);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * It deletes a resource.
	 * 
	 * @param r an instance of Resource
	 * @param a an instance of Agenda
	 * @return true if the resource is deleted, otherwise false
	 */
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
