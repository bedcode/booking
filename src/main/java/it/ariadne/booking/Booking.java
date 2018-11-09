package it.ariadne.booking;

import org.joda.time.Interval;

public class Booking {

	private String id = "";
	private Interval interval;
	
	public Booking(String id, Interval interval) {
		this.id = id;
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public String getId() {
		return id;
	}

}
