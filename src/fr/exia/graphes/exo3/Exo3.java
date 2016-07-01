package fr.exia.graphes.exo3;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import fr.exia.graphes.GraphUtils;
import fr.exia.graphes.GraphUtils.ColorTransformer;
import fr.exia.graphes.Placeholder;
import fr.exia.graphes.Player;

public class Exo3 {

	public static void execute() {

		// Création d'un graph non orienté.
		final UndirectedGraph<Ville, Chemin> G = new UndirectedSparseGraph<Ville, Chemin>();

		// On fabrique les sommets
		final Ville s1 = new Ville("Donaldville", 10, 25);
		G.addVertex(s1);
		Ville s2 = new Ville("Gotham City", 20, 10);
		G.addVertex(s2);
		Ville s3 = new Ville("Capital City", 20, 40);
		G.addVertex(s3);
		Ville s4 = new Ville("Raccoon City", 40, 10);
		G.addVertex(s4);
		Ville s5 = new Ville("Trifouilly-les-Oies", 50, 20);
		G.addVertex(s5);
		Ville s6 = new Ville("San Andreas", 50, 30);
		G.addVertex(s6);
		Ville s7 = new Ville("Springfield", 50, 40);
		G.addVertex(s7);
		Ville s8 = new Ville("Toulouse", 80, 30);
		G.addVertex(s8);
		Ville s9 = new Ville("Tataouine", 95, 30);
		G.addVertex(s9);
		Ville s10 = new Ville("Tocquebourg", 80, 40);
		G.addVertex(s10);
		Ville s11 = new Ville("Hill Valley", 110, 30);
		G.addVertex(s11);
		final Ville s12 = new Ville("Godric's Hollow", 125, 30);
		G.addVertex(s12);

		// On fabrique les arêtes
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

		// Ce transformer permet de récupérer la distance d'une arête
		Transformer<Chemin, Double> wt = new Transformer<Chemin, Double>() {
			public Double transform(Chemin link) {
				return link.getDistance();
			}
		};

		// On fabrique un calculateur Dijkstra
		DijkstraShortestPath<Ville, Chemin> p = new DijkstraShortestPath<Ville, Chemin>(G, wt, false);

		// On détermine le chemin au plus court
		List<Chemin> ls = p.getPath(s1, s12);
		System.out.println("AVEC L'ALGO DE JUNG");
		System.out.println("Chemin le plus court de " + s1 + " à " + s12 + " : " + (ls.size() + 1) + " étapes");
		int w = 0;
		for (Chemin l : ls) {
			System.out.println("\t- " + l.getFrom() + " -> " + l.getTo() + " (" + l + ")");
			w += l.getDistance();
		}
		System.out.println(" Soit un total de distance: " + w);

		// On affiche les graphes
		Placeholder<JFrame> f = GraphUtils.displayGraph("Exo 3 - Graphe", G, new Dimension(1000, 450), true,
				new ColorTransformer<Ville>(), true);
		f.join();
		final JFrame x = f.getObject();
		Player.frame = x;
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JMenuBar mb = new JMenuBar();
				final JMenu m = new JMenu("Start");
				m.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// Lancement
						if (m.getText().equals("Start")) {
							m.setText("Next");
							new Thread(new Runnable() {
								public void run() {
									Trajet chemin = Dijkstra.getShortestPath(G, s1, s12);
									System.out.println("\nAVEC ALGO PAS A PAS");
									System.out.println("Chemin le plus court de " + s1 + " à " + s12 + " : "
											+ chemin.length() + " étapes");
									System.out.println(chemin);
									System.out.println(" Soit un total de distance: " + chemin.getDistance());
									for (Ville v : G.getVertices()) {
										v.color = null;
										v.unvisited();
									}
									SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											m.setText("Start");
											x.repaint();
										}
									});
								}
							}).start();
						} else {
							Player.next();
						}
					}
				});
				mb.add(m);
				x.setJMenuBar(mb);
				x.setVisible(false);
				x.setVisible(true);
			}
		});
	}

	private static void ajouterArete(UndirectedGraph<Ville, Chemin> G, Ville s1, Ville s2, int duration) {
		G.addEdge(new Chemin(s1, s2, duration), s1, s2, EdgeType.UNDIRECTED);
	}

}
