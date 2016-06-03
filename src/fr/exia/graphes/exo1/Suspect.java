package fr.exia.graphes.exo1;

import fr.exia.graphes.GraphUtils.Vertex2d;

public class Suspect extends Vertex2d {
	
	/**
	 * Niveau de suspicion, croissant quand des incertitudes apparaissent.
	 */
	private int suspicion;
	
	public Suspect(String name, double x, double y) {
		super(name, x, y);
	}
	
	public int getSuspicionLevel() {
		return this.suspicion;
	}
	
	public void addSuspicion() {
		this.suspicion += 1;
	}

}
