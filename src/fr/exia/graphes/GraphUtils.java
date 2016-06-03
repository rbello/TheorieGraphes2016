package fr.exia.graphes;

import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GraphUtils {

	public static <T extends Sommet2d> void displayGraph(final String title, final Graph<T, ?> graph,
			final Dimension dim, final boolean autoLocation, final Transformer<T, Paint> ts) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Placement automatique des points
				Layout layout = new ISOMLayout(graph);
				// Placement manuel
				if (!autoLocation) {
					layout = new StaticLayout(graph, new Transformer<T, Point2D>() {
						public Point2D transform(T s) {
							return s.getLocation();
						}
					});
				}
				JFrame jf = new JFrame();
				VisualizationViewer vv = new VisualizationViewer(layout, dim);
				// Modifie le transformer des sommets
				if (ts != null) vv.getRenderContext().setVertexFillPaintTransformer(ts);
				// Afficher les arrêtes droites
				vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Integer,String>());
				// Afficher les labels des sommets
				vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
				jf.getContentPane().add(vv);
				jf.pack();
				jf.setTitle(title);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setVisible(true);
			}
		});
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
	
	public static void afficherMatriceAdjacence(int[][] m) {
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
	
}
