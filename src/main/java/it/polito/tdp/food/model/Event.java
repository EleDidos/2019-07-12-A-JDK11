package it.polito.tdp.food.model;

public class Event implements Comparable <Event>{
	
	public enum EventType{
		INIZIO,//nuovo piatto
		FINE//stazione libera
	}
	
	private double minutes; //di preparazione --> tempo finale
	private EventType type;
	private Food inPreparazione; //cibo in preparazione
	
	public Food getInPreparazione() {
		return inPreparazione;
	}

	public void setInPreparazione(Food inPreparazione) {
		this.inPreparazione = inPreparazione;
	}

	@Override
	public int compareTo(Event other) {
		return (int)(this.minutes-other.minutes);
	}

	public double getMinutes() {
		return minutes;
	}

	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Event(double minutes, EventType type, Food inPreparazione) {
		super();
		this.minutes = minutes;
		this.type = type;
		this.inPreparazione=inPreparazione;
	}
	
	

}
