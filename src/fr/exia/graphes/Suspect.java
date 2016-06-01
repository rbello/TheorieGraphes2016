package fr.exia.graphes;

import java.awt.geom.Point2D;

public class Suspect {
	
	private String name;
	private Point2D loc;
	private int index;
	
	private static int INDEX = 0;
	
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
	
	@Override
	public String toString() {
		return this.getName();
	}

}
