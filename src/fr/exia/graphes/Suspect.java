package fr.exia.graphes;

import java.awt.geom.Point2D;

public class Suspect {
	
	private static int INDEX = 0;
	
	/**
	 * Numéro d'index du sommet dans le graphe.
	 */
	private int index;
	
	/**
	 * Nom du suspect.
	 */
	private String name;
	
	/**
	 * Emplacement graphique dans la fenêtre.
	 */
	private Point2D loc;
	
	/**
	 * Niveau de suspicion, croissant quand des incertitudes apparaissent.
	 */
	private int suspicion;
	
	public Suspect(String name, double x, double y) {
		this.index = INDEX++;
		this.name = name;
		this.loc = new Point2D.Double(x * 7, y * 7);
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Point2D getLocation() {
		return this.loc;
	}
	
	public int getSuspicionLevel() {
		return this.suspicion;
	}
	
	public void addSuspicion() {
		this.suspicion += 1;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
