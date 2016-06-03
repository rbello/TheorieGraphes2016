package fr.exia.graphes.exo2;

public class Lien {

	private Tache from;
	private Tache to;

	public Lien(Tache from, Tache to) {
		this.from = from;
		this.to = to;
	}

	public Tache getFrom() {
		return from;
	}

	public Tache getTo() {
		return to;
	}
	
}
