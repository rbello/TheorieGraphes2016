package fr.exia.graphes.exo3;

import fr.exia.graphes.GraphUtils.Vertex2d;

public class Ville extends Vertex2d {

	private boolean visited;

	public Ville(String name, double x, double y) {
		super(name, x, y);
	}
	
	@Override
	public String toString() {
		return this.getName() + "[" + getIndex() + "]";
	}

	public boolean visited() {
		boolean r = visited;
		visited = true;
		return r;
	}

	public void unvisited() {
		visited = false;
	}
	
}
