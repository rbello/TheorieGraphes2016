package fr.exia.graphes.exo3;

public class Chemin {

	private Ville from;
	private Ville to;
	private double distance;
	
	public Chemin(Ville from, Ville to, double duration) {
		this.from = from;
		this.to = to;
		this.distance = duration;
	}
	
	public Ville getFrom() {
		return from;
	}
	
	public Ville getTo() {
		return to;
	}
	
	public double getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return "" + distance;
	}

	public Ville getOther(Ville v) {
		if (v == from && v != to) return to;
		if (v == to && v != from) return to;
		return null;
	}

}
