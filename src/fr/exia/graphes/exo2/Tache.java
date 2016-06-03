package fr.exia.graphes.exo2;

import fr.exia.graphes.GraphUtils.Vertex2d;

public class Tache extends Vertex2d {

	/**
	 * La durée de la tâche en jours
	 */
	private double duration;

	public Tache(String name, double duration, double x, double y) {
		super(name, x, y);
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return getName() + " (" + duration + ")";
	}
	
}
