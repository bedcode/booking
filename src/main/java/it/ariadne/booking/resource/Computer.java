package it.ariadne.booking.resource;

import it.ariadne.booking.Resource;

public class Computer implements Resource{

	private int ram;
	
	public Computer(int ram) {
		this.ram = ram;
	}

	@Override
	public int getConstraint() {
		return this.ram;
	}

	@Override
	public void setConstraint(int constraint) {
		this.ram = constraint;		
	}

}
