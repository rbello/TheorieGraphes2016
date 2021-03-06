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
		final DirectedGraph<Tache, LienFinADebut> G = new DirectedSparseGraph<Tache, LienFinADebut>();

		// On fabrique les sommets
		Tache s1 = new Tache("Gros �uvre", 15, 10, 25);
		G.addVertex(s1);
		Tache s2 = new Tache("Charpente", 4, 20, 10);
		G.addVertex(s2);
		Tache s3 = new Tache("Electricit�-Plomberie", 6, 20, 40);
		G.addVertex(s3);
		Tache s4 = new Tache("Couverture", 4, 40, 10);
		G.addVertex(s4);
		Tache s5 = new Tache("Isolation des combles", 6, 50, 20);
		G.addVertex(s5);
		Tache s6 = new Tache("Menuiseries int�rieures", 8, 50, 30);
		G.addVertex(s6);
		Tache s7 = new Tache("Menuiseries ext�rieures", 3, 50, 40);
		G.addVertex(s7);
		Tache s8 = new Tache("Pl�tres", 3, 80, 30);
		G.addVertex(s8);
		Tache s9 = new Tache("Peinture", 2, 95, 30);
		G.addVertex(s9);
		Tache s10 = new Tache("Peinture", 4, 80, 40);
		G.addVertex(s10);
		Tache s11 = new Tache("Finitions", 2, 110, 30);
		G.addVertex(s11);
		Tache s12 = new Tache("Fin", 0, 125, 30);
		G.addVertex(s12);

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
		for (Tache s : (Collection<Tache>) G.getVertices()) {
			sum += s.getDuration();
		}
		System.out.println("Dur�e totale : " + sum + " jours/hommes\n");

		// Ce transformer permet de r�cup�rer la distance d'une ar�te
		Transformer<LienFinADebut, Double> wt = new Transformer<LienFinADebut, Double>() {
			public Double transform(LienFinADebut link) {
				return link.getDuration();
			}
		};

		// On fabrique un calculateur Dijkstra
		DijkstraShortestPath<Tache, LienFinADebut> p = new DijkstraShortestPath<Tache, LienFinADebut>(G, wt, false);

		// On d�termine le chemin au plus court
		List<LienFinADebut> ls = p.getPath(s1, s12);
		System.out.println("Chemin le plus court : " + ls.size() + " �tapes (avec poids)");
		int w = 0;
		for (LienFinADebut l : ls) {
			System.out.println("\t- " + l);
			w += l.getDuration();
		}
		System.out.println(String.format("Soit un temps minimal de %d jours, mais ce qui repr�sente %.2f%% du travail.", w, (double)w / sum * 100d));
		System.out.println("Conclusion : le chemin le plus court ne sert � rien dans cet exercice !");

		// Pour le reste du calcul on va avoir besoin de la liste ordonn�e des
		// sommets
		List<Tache> lt = new ArrayList<Tache>((Collection<Tache>) G.getVertices());
		Collections.sort(lt, new Comparator<Tache>() {
			public int compare(Tache t1, Tache t2) {
				return Integer.compare(t1.getIndex(), t2.getIndex());
			}
		});

		// On r�alise les calculs sur le graph
		System.out.println("\nOn calcule les interd�pendances (dates de fin/d�but plus t�t/tard et marges)...");
		computeTasksInterdependencies(G, s1);

		// On d�termine les dates au plus t�t
		System.out.println("\nTache\tDebut+Tot\tFin+Tot\tDebut+Tard\tFin+Tard");
		for (Tache s : lt) {
			System.out.println(String.format(" %d\t%.2f\t\t%.2f\t%.2f\t\t%.2f", s.getIndex(), s.getEarlyStart(),
					s.getEarlyFinish(), s.getLateStart(), s.getLateFinish()));
		}

		System.out.println("Les travaux durant au plus " + s12.getLateFinish()
				+ " jours, ces derniers seront termin�s\npour l�arriv�e de b�b� (� condition que ce dernier ne pointe\npas son nez pr�matur�ment)");

		// On fabrique l'arbre couvrant minimum
		// Graph<Tache, Lien> g = new PrimMinimumSpanningTree<Tache, Lien>(new
		// Factory<Graph<Tache, Lien>>() {
		// public Graph<Tache, Lien> create() {
		// return new DirectedSparseGraph<Tache, Lien>();
		// }
		// }).transform(G);
		// GraphUtils.displayGraph("Exo 2 - Arbre couvrant de poids minimal", g,
		// new Dimension(1000, 400), false, null);

		// On affiche les graphes
		GraphUtils.displayGraph("Exo 2 - Graphe", G, new Dimension(1000, 400), false, null, false);

	}

	/**
	 * Effectuer les calculs d'interd�pendances entre les t�ches d'un graphe repr�sentant un
	 * r�seau PERT. Ce calcul permet d�terminer les dates de d�but et de fin au plus t�t et
	 * au plus tard, ainsi que les marges d�gag�es.
	 * 
	 * @param g Le graphe complet.
	 * @param t La t�che en cours � calculer.
	 */
	public static void computeTasksInterdependencies(DirectedGraph<Tache, LienFinADebut> g, Tache t) {
		// Calcul des dur�es
		forwardPassCalculation(g, t, 0);
		// Calcul des marges
		backwardPassCalculation(g, t, 0);
	}
	
	/**
	 * Calcul des dur�es.
	 * 
	 * @param g Le graphe complet.
	 * @param t La t�che en cours � calculer.
	 * @param start La date actuelle, c-�-d. la fin de la t�che pr�c�dente.
	 */
	private static void forwardPassCalculation(DirectedGraph<Tache, LienFinADebut> g, Tache t, double start) {
		double end = start + t.getDuration();
		List<Tache> vs = new ArrayList<Tache>(g.getSuccessors(t));
		// System.out.println(String.format("Je suis sur %s, debut %.2f fin
		// %.2f. Il y a %d successeurs", t, start, end, vs.size()));
		t.setDates(start, end);
		for (Tache v : vs) {
			forwardPassCalculation(g, v, end);
		}
	}

	/**
	 * Calcul des marges.
	 * 
	 * @param g Le graphe complet.
	 * @param t La t�che en cours � calculer.
	 * @param start La date actuelle, c-�-d. la fin de la t�che pr�c�dente.
	 */
	private static void backwardPassCalculation(DirectedGraph<Tache, LienFinADebut> g, Tache t, int start) {
		t.setFloats(t.getLateFinish() - t.getEarlyFinish() - t.getDuration());
	}

	/**
	 * Fonction utilitaire pour ajouter une ar�te dans le graphe.
	 * 
	 * @param g Le graphe complet.
	 * @param s1 La t�che N.
	 * @param s2 La t�che N+1.
	 * @param duration La dur�e des travaux entre les deux t�ches.
	 */
	private static void ajouterArete(DirectedGraph<Tache, LienFinADebut> g, Tache s1, Tache s2, int duration) {
		g.addEdge(new LienFinADebut(s1, s2, duration), s1, s2, EdgeType.DIRECTED);
	}

}
