package it.ariadne.booking.resource;

import it.ariadne.booking.Resource;

public class Car implements Resource {
	
	private int limit = 0;

	public Car(int limit) {
		this.limit = limit;
	}

	@Override
	public int getLimit() {
		return this.limit;
	}

}
