package it.polito.tdp.food.model;

public class Stazione {
	
	private Integer ID;
	private boolean free;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	public Stazione(Integer iD) {
		super();
		ID = iD;
		this.free = true;
	}
	

}
