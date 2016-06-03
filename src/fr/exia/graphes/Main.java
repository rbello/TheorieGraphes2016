package fr.exia.graphes;

import fr.exia.graphes.exo1.Exo1;
import fr.exia.graphes.exo2.Exo2;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("---- EXERCICE 1\n");
		Exo1.execute();
		
		GraphUtils.resetVerticesIndex(1);
		
		System.out.println("\n---- EXERCICE 2\n");
		Exo2.execute();
		
	}

}