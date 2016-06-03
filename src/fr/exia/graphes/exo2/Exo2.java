package fr.exia.graphes.exo2;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import fr.exia.graphes.GraphUtils;

public class Exo2 {

	public static void execute() {
		
		// Cr�ation d'un graph orient�.
		DirectedGraph<Tache, Lien> G = new DirectedSparseGraph<Tache, Lien>();
		
		// On fabrique les sommets
		Tache s1 = new Tache("Gros �uvre", 15, 10, 25); G.addVertex(s1);
		Tache s2 = new Tache("Charpente", 4, 20, 10); G.addVertex(s2);
		Tache s3 = new Tache("Electricit�-Plomberie", 6, 20, 40); G.addVertex(s3);
		Tache s4 = new Tache("Couverture", 4, 40, 10); G.addVertex(s4);
		Tache s5 = new Tache("Isolation des combles", 6, 50, 20); G.addVertex(s5);
		Tache s6 = new Tache("Menuiseries int�rieures", 8, 50, 30); G.addVertex(s6);
		Tache s7 = new Tache("Menuiseries ext�rieures", 3, 50, 40); G.addVertex(s7);
		Tache s8 = new Tache("Pl�tres", 3, 80, 30); G.addVertex(s8);
		Tache s9 = new Tache("Peinture", 2, 95, 30); G.addVertex(s9);
		Tache s10 = new Tache("Peinture", 4, 80, 40); G.addVertex(s10);
		Tache s11 = new Tache("Finitions", 2, 110, 30); G.addVertex(s11);
		Tache s12 = new Tache("Fin", 0, 125, 30); G.addVertex(s12);
		
		// On fabrique les ar�tes
		ajouterArete(G, s1, s2, 15);
		ajouterArete(G, s1, s3, 15);
		ajouterArete(G, s2, s4, 4);
		ajouterArete(G, s3, s5, 6);
		ajouterArete(G, s3, s6, 6);
		ajouterArete(G, s3, s7, 6);
		ajouterArete(G, s4, s5, 4);
		ajouterArete(G, s4, s6, 4);
		ajouterArete(G, s4, s7, 4);
		ajouterArete(G, s5, s11, 6);
		ajouterArete(G, s6, s8, 8);
		ajouterArete(G, s7, s10, 3);
		ajouterArete(G, s8, s9, 3);
		ajouterArete(G, s9, s11, 2);
		ajouterArete(G, s10, s11, 4);
		ajouterArete(G, s11, s12, 2);
		
		// On somme les dur�es
		int sum = 0;
		for (Tache s : (Collection<Tache>)G.getVertices()) {
			sum += s.getDuration();
		}
		System.out.println("Dur�e totale : " + sum + " jours de travaux\n");
		
		// On recherche le chemin le plus court (sans prendre en compte les dur�es)
		DijkstraShortestPath<Tache, Lien> p = new DijkstraShortestPath<Tache, Lien>(G, false);
		List<Lien> ls = p.getPath(s1, s12);
		System.out.println("Chemin le plus court en distance : " + ls.size() + " �tapes (sans poids)");
		for (Lien l : ls) System.out.println("\t- " + l);
		
		// Ce transformer permet de r�cup�rer la distance d'une ar�te
		Transformer<Lien, Double> wt = new Transformer<Lien, Double>() {
            public Double transform(Lien link) {
                return link.getDuration();
            }
        };
        p = new DijkstraShortestPath<Tache, Lien>(G, wt, false);
        ls = p.getPath(s1, s12);
		System.out.println("\nChemin le plus court : " + ls.size() + " �tapes (avec poids)");
		int w = 0;
		for (Lien l : ls) {
			System.out.println("\t- " + l);
			w += l.getDuration();
		}
		System.out.println("Soit un temps minimal de " + w + " jours");
		
		// Pour le reste du calcule on va avoir besoin de la liste ordonn�e des sommets
		List<Tache> lt = new ArrayList<Tache>((Collection<Tache>)G.getVertices());
		Collections.sort(lt, new Comparator<Tache>() {
	        public int compare(Tache t1, Tache t2) {
	            return Integer.compare(t1.getIndex(), t2.getIndex());
	        }
	    });
		
		// On d�termine les dates au plus t�t
		System.out.println("\nPoint\tDateAuPlusTot");
		for (Tache s : lt) {
			Number v = p.getDistance(s1, s);
			System.out.println(String.format(" %d\t%.2f", s.getIndex(), v));
		}
		
		// On affiche le graphe
		GraphUtils.displayGraph("Exo 2", G, new Dimension(1000, 400), false, null);
	}

	private static void ajouterArete(DirectedGraph<Tache, Lien> G, Tache s1, Tache s2, int duration) {
		G.addEdge(new Lien(s1, s2, duration), s1, s2, EdgeType.DIRECTED);
	}

}
