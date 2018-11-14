package it.ariadne.booking;

import org.joda.time.Interval;

import it.ariadne.booking.users.User;

public class Booking {

	private String id = "";
	private Interval interval;
	private User user;

	public Booking(String id, Interval interval, User user) {
		this.id = id;
		this.interval = interval;
		this.user = user;
	}

	public Interval getInterval() {
		return interval;
	}

	public String getId() {
		return id;
	}

}
