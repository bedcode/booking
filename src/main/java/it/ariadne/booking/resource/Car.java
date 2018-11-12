package it.ariadne.booking.resource;

import it.ariadne.booking.Resource;

public class Car implements Resource {

	@Override
	public boolean getLimit() {
		return false;
	}

}
