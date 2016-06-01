package fr.exia.graphes;

import java.awt.geom.Point2D;

public class Suspect {
	
	private String name;
	private Point2D loc;
	
	public Suspect(String name, double x, double y) {
		this.name = name;
		this.loc = new Point2D.Double(x * 7, y * 7);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Point2D getLocation() {
		return this.loc;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
