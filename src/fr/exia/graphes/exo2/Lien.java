package fr.exia.graphes.exo2;

public class Lien {

	private Tache from;
	private Tache to;
	private double duration;
	
	public Lien(Tache from, Tache to, double duration) {
		this.from = from;
		this.to = to;
		this.duration = duration;
	}
	
	public Tache getFrom() {
		return from;
	}
	
	public Tache getTo() {
		return to;
	}
	
	public double getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return from + " -> " + to;
	}

}
