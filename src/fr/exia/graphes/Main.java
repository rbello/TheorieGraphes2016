package fr.exia.graphes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("---- EXERCICE 1\n");
		
		// Création d'un graph non orienté.
		UndirectedGraph<Suspect, Rencontre> G = new UndirectedSparseGraph<Suspect, Rencontre>();
		
		// On fabrique les sommets
		Suspect a = new Suspect("Ann", 30, 10); G.addVertex(a); 
		Suspect b = new Suspect("Betty", 50, 25); G.addVertex(b);
		Suspect c = new Suspect("Cynthia", 50, 50); G.addVertex(c);
		Suspect d = new Suspect("Diana", 30, 65); G.addVertex(d);
		Suspect e = new Suspect("Emily", 10, 50); G.addVertex(e);
		Suspect f = new Suspect("Felicia", 10, 25); G.addVertex(f);
		Suspect g = new Suspect("Georgia", 80, 10); G.addVertex(g);
		Suspect h = new Suspect("Helen", 80, 50); G.addVertex(h);
		
		// On fabrique les arêtes
		ajouterArete(G, a, b);
		ajouterArete(G, a, c);
		ajouterArete(G, a, e);
		ajouterArete(G, a, f);
		ajouterArete(G, a, g);
		ajouterArete(G, b, c);
		ajouterArete(G, b, h);
		ajouterArete(G, c, d);
		ajouterArete(G, c, e);
		ajouterArete(G, c, h);
		ajouterArete(G, d, e);
		ajouterArete(G, e, f);
		ajouterArete(G, g, h);
		
		// On affiche la matrice d'adjacence
		System.out.println("Matrice d'adjacence :\n");
		GraphUtils.afficherMatriceAdjacence(GraphUtils.calculerMatriceAdjacence(G));
		
		// Détection des anomalies
		System.out.println("\nAnomalies :\n");
		detecterAnomalies(G);
		
		// On affiche le graph
		displayColoredGraph("Graphe", G, new Dimension(680, 550));
		
		// On détermine la coupable
		Suspect max = null;
		for (Suspect s : (Collection<Suspect>)G.getVertices()) {
			if (max == null || max.getSuspicionLevel() < s.getSuspicionLevel()) max = s;
		}
		System.out.println(String.format("\n%s est probalement coupable, car elle cumule un niveau de suspicion de %d",
				max, max.getSuspicionLevel()));
		
		//System.out.println("\n---- EXERCICE 2\n");
		
		
		
	}
	
	public static void displayColoredGraph(String title, UndirectedGraph<Suspect, Rencontre> graph, Dimension dimension) {
		// On calcule la valeur max de suspicion
		int i = 0;
		for (Suspect s : (Collection<Suspect>)graph.getVertices()) i = Math.max(i, s.getSuspicionLevel());
		final double max = i;
		// Afficher les sommets avec des couleurs
		Transformer<Suspect, Paint> ts = new Transformer<Suspect, Paint>() {
			public Paint transform(Suspect p) {
				// Gradiant de vert à rouge
				double ratio = ((Suspect)p).getSuspicionLevel() / max;
				return new Color((int)(255d * ratio), (int)(255 * (1d - ratio)), 0);
			}
		};
		// On appelle la librairie pour afficher le graph
		GraphUtils.displayGraph(title, graph, dimension, false, ts);
	}

	private static void detecterAnomalies(UndirectedGraph<Suspect, Rencontre> g) {
		List<Rencontre> rs = new ArrayList<Rencontre>();
		// Pour chaque suspect
		for (Suspect s1 : new ArrayList<Suspect>((Collection<Suspect>)g.getVertices())) {
			// On récupère les voisins de ce sommet
			List<Suspect> n1 = new ArrayList<Suspect>(g.getNeighbors(s1));
			// Liste des voisins de niveaux 2
			List<Suspect> ss = new ArrayList<Suspect>();
			// Je parcours tous ses voisins
			for (Suspect s2 : n1) {
				// On recupère les sommets voisins de ce sommet
				for (Suspect s3 : g.getNeighbors(s2)) {
					if (s3 == s1) continue;
					if (n1.contains(s3)) continue;
					// Ce voisin de voisin a déjà été rencontré !
					if (ss.contains(s3)) {
						Rencontre r = new Rencontre(s1, s3);
						// On ne fait plus ce test : le fait de conserver les redondances permet
						// d'augmenter la suspicion
						//if (rs.contains(r)) continue;
						rs.add(r);
						System.out.println(String.format(" - %s et %s ne se sont pas rencontreés, alors que %s les a vu tt les deux", s1, s3, s2));
						s1.addSuspicion();
						s3.addSuspicion();
					}
					ss.add(s3);
				}
			}
		}
	}

	private static void ajouterArete(Graph<Suspect, Rencontre> G, Suspect a, Suspect b) {
		G.addEdge(new Rencontre(a, b), a, b, EdgeType.UNDIRECTED);
	}

}
