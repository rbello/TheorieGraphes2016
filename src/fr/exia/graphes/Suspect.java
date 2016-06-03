package fr.exia.graphes;

public class Suspect extends Sommet2d {
	
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
	 * Niveau de suspicion, croissant quand des incertitudes apparaissent.
	 */
	private int suspicion;
	
	public Suspect(String name, double x, double y) {
		super(x, y);
		this.index = INDEX++;
		this.name = name;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String getName() {
		return this.name;
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
