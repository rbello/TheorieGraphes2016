package fr.exia.graphes.exo3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Trajet {
	
	private List<Ville> path = new ArrayList<Ville>();
	private Ville last = null;
	private int distance = 0;
	private boolean reached = false;
	
	public Trajet(Ville from) {
		path.add(from);
		last = from;
		distance = 0;
	}

	public Trajet(Trajet t) {
		path = new ArrayList<Ville>(t.getPath()); // copie
		last = t.getLast();
		distance = t.getDistance();
	}

	private List<Ville> getPath() {
		return path;
	}

	public Ville getLast() {
		return last;
	}

	public int getDistance() {
		return distance;
	}

	public boolean hasReachedTarget() {
		return reached;
	}

	public boolean contains(Ville v) {
		return path.contains(v);
	}

	public void add(Ville v, Chemin c) {
		path.add(v);
		last = v;
		distance += c.getDistance();
	}

	public void setTargetReached() {
		reached = true;
	}
	
	public int length() {
		return path.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Ville v : path) {
			if (!first) sb.append(" > ");
			first = false;
			sb.append(v.getName());
		}
		sb.append(" (" + distance + ")");
		return sb.toString();
	}

	public void setColor(Color color) {
		for (Ville v : path)
			v.color = color;
	}

}
