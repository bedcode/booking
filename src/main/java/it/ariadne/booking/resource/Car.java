package it.ariadne.booking.resource;

import it.ariadne.booking.Resource;

public class Car implements Resource {

	private int seats;

	public Car(int seats) {
		this.seats = seats;
	}

	@Override
	public int getConstraint() {
		return this.seats;
	}

	@Override
	public void setConstraint(int constraint) {
		this.seats = constraint;		
	}
}
