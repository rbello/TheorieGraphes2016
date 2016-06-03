package fr.exia.graphes;

import java.awt.geom.Point2D;

public abstract class Sommet2d {

	private Point2D loc;
	
	public Sommet2d(double x, double y) {
		this.loc = new Point2D.Double(x * 7, y * 7);
	}

	public Point2D getLocation() {
		return this.loc;
	}
	
}
