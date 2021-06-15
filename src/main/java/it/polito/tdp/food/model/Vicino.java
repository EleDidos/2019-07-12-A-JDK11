package it.polito.tdp.food.model;

public class Vicino implements Comparable<Vicino>{
	
	private Food vicino;
	private double peso;
	
	public Vicino(Food vicino, double peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}
	public Food getVicino() {
		return vicino;
	}
	public void setVicino(Food vicino) {
		this.vicino = vicino;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	//decrescente
	@Override
	public int compareTo(Vicino other) {
		return (int)(other.peso-this.peso);
	}
	
	public String toString() {
		return vicino + " - " +peso;
	}
	

}
