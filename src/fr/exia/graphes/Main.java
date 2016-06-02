package fr.exia.graphes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Main {
	
	public static void main(String[] args) {
		
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
		afficherMatriceAdjacence(calculerMatriceAdjacence(G));
		
		// Détection des anomalies
		System.out.println("\nAnomalies :\n");
		detecterAnomalies(G);
		
		// On affiche le graph
		displayGraph("Original", G, new Dimension(750, 550));
		
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
						// On ne fait plus ce test : cela augmente la suspicion
						//if (rs.contains(r)) continue;
						rs.add(r);
						System.out.println(" - " + s1 + " et " + s3 + " ne se sont pas rencontreés, alors que " + s2 + " les a vu tt les deux");
						s1.addSuspicion();
						s3.addSuspicion();
					}
					ss.add(s3);
				}
			}
		}
	}

	protected static UndirectedGraph<Suspect, Rencontre> clone(UndirectedGraph<Suspect, Rencontre> g) {
		UndirectedGraph<Suspect, Rencontre> G = new UndirectedSparseGraph<Suspect, Rencontre>();
		for (Suspect s : (Collection<Suspect>)g.getVertices()) G.addVertex(s);
		for (Rencontre r : (Collection<Rencontre>)g.getEdges()) G.addEdge(r, r.getLeft(), r.getRight(), EdgeType.UNDIRECTED);
		return G;
	}

	private static void afficherMatriceAdjacence(int[][] m) {
		System.out.print("   ");
		for (int i = 0; i < m.length; ++i)
			System.out.print((char)(65 + i) + " ");
		System.out.println();
		for (int i = 0; i < m.length; ++i) {
			System.out.print((char)(65 + i));
			System.out.print("|");
			for (int j = 0; j < m.length; ++j) {
				System.out.print(" " + m[i][j]);
			}
			System.out.println(" |");
		}
	}

	private static void ajouterArete(Graph<Suspect, Rencontre> G, Suspect a, Suspect b) {
		G.addEdge(new Rencontre(a, b), a, b, EdgeType.UNDIRECTED);
	}

	public static int[][] calculerMatriceAdjacence(UndirectedGraph<?, ?> g) {
		// Nombre de sommets
		int length = g.getVertexCount();
		// Matrice en sortie
		int[][] m = new int[length][length];
		// On recupère les arêtes
		for (Rencontre e : (Collection<Rencontre>)g.getEdges()) {
			int i = e.getLeft().getIndex();
			int j = e.getRight().getIndex();
			m[i][j] = 1;
			m[j][i] = 1;
		}
		return m;
	}
	
	public static void displayGraph(final String title, final Graph g, final Dimension dim) {
		
		// On calcule la valeur max de suspicion
		int i = 0;
		for (Suspect s : (Collection<Suspect>)g.getVertices()) i = Math.max(i, s.getSuspicionLevel());
		final double max = i;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Placement automatique
				Layout l = new ISOMLayout(g);
				// Placement manuel
				l = new StaticLayout(g, new Transformer<Suspect, Point2D>() {
					public Point2D transform(Suspect s) {
						return s.getLocation();
					}
				});
				JFrame jf = new JFrame();
				VisualizationViewer vv = new VisualizationViewer(l, dim);
				// Afficher les sommets avec des couleurs
				vv.getRenderContext().setVertexFillPaintTransformer(new Transformer() {
					public Paint transform(Object p) {
						// Gradiant de vert à rouge
						double ratio = ((Suspect)p).getSuspicionLevel() / max;
						return new Color((int)(255d * ratio), (int)(255 * (1d - ratio)), 0);
					}
				});
				// Afficher les arrêtes droites
				vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Integer,String>());
				// Afficher les labels des sommets
				vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
				jf.getContentPane().add(vv);
				jf.pack();
				jf.setTitle(title);
				jf.setVisible(true);
			}
		});
	}
	
}
