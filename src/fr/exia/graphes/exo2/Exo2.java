package fr.exia.graphes.exo2;

import java.awt.Dimension;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import fr.exia.graphes.GraphUtils;

public class Exo2 {

	public static void execute() {
		
		// Création d'un graph orienté.
		DirectedGraph<Tache, Lien> G = new DirectedSparseGraph<Tache, Lien>();
		
		// On fabrique les sommets
		Tache s1 = new Tache("Gros œuvre", 15, 10, 25); G.addVertex(s1);
		Tache s2 = new Tache("Charpente", 4, 20, 10); G.addVertex(s2);
		Tache s3 = new Tache("Electricité-Plomberie", 6, 20, 40); G.addVertex(s3);
		Tache s4 = new Tache("Couverture", 4, 30, 10); G.addVertex(s4);
		Tache s5 = new Tache("Isolation des combles", 6, 40, 20); G.addVertex(s5);
		Tache s6 = new Tache("Menuiseries intérieures", 8, 40, 30); G.addVertex(s6);
		Tache s7 = new Tache("Menuiseries extérieures", 3, 40, 40); G.addVertex(s7);
		Tache s8 = new Tache("Plâtres", 3, 50, 30); G.addVertex(s8);
		Tache s9 = new Tache("Peinture", 2, 60, 30); G.addVertex(s9);
		Tache s10 = new Tache("Peinture", 4, 50, 40); G.addVertex(s10);
		Tache s11 = new Tache("Finitions", 2, 70, 30); G.addVertex(s11);
		Tache s12 = new Tache("Fin", 0, 80, 30); G.addVertex(s12);
		
		// On fabrique les arêtes
		ajouterArete(G, s1, s2);
		ajouterArete(G, s1, s3);
		ajouterArete(G, s2, s4);
		ajouterArete(G, s3, s5);
		ajouterArete(G, s3, s6);
		ajouterArete(G, s3, s7);
		ajouterArete(G, s4, s5);
		ajouterArete(G, s4, s6);
		ajouterArete(G, s4, s7);
		ajouterArete(G, s5, s11);
		ajouterArete(G, s6, s8);
		ajouterArete(G, s7, s10);
		ajouterArete(G, s8, s9);
		ajouterArete(G, s9, s11);
		ajouterArete(G, s10, s11);
		ajouterArete(G, s11, s12);
		
		// On affiche le graphe
		GraphUtils.displayGraph("Exo 2", G, new Dimension(660, 400), false, null);
	}

	private static void ajouterArete(DirectedGraph<Tache, Lien> G, Tache s1, Tache s2) {
		G.addEdge(new Lien(s1, s2), s1, s2, EdgeType.DIRECTED);
	}

}
