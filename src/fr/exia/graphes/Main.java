package fr.exia.graphes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Main {
	
	private static Map<Object, Color> COLORS = new HashMap<Object, Color>();
	private static JFrame FRAME = null;

	public static void main(String[] args) {
		
		// On fabrique le graph non-orienté
		Graph<Suspect, Rencontre> G = new UndirectedSparseGraph<Suspect, Rencontre>();
		
		// On fabrique les sommets
		Suspect a = new Suspect("Ann", 30, 10); G.addVertex(a); 
		Suspect b = new Suspect("Betty", 50, 25); G.addVertex(b);
		Suspect c = new Suspect("Cynthia", 50, 50); G.addVertex(c);
		Suspect d = new Suspect("Diana", 30, 65); G.addVertex(d);
		Suspect e = new Suspect("Emily", 10, 50); G.addVertex(e);
		Suspect f = new Suspect("Felicia", 10, 25); G.addVertex(f);
		Suspect g = new Suspect("Georgia", 80, 10); G.addVertex(g);
		Suspect h = new Suspect("Helen", 80, 50); G.addVertex(h);
		
		// On inclus les arrêtes
		G.addEdge(new Rencontre(), a, b, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), a, c, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), a, e, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), a, f, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), a, g, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), b, c, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), b, h, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), c, d, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), c, e, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), c, h, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), d, e, EdgeType.UNDIRECTED);
		//G.addEdge(new Rencontre(), d, h, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), e, f, EdgeType.UNDIRECTED);
		G.addEdge(new Rencontre(), g, h, EdgeType.UNDIRECTED);
		
		// On affiche le graph
		afficherGraph(G	, new Dimension(750, 550));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void afficherGraph(Graph g, Dimension dim) {
		Layout l = new ISOMLayout(g); // Placement automatique
		// Placement manuel
		l = new StaticLayout(g, new Transformer<Suspect, Point2D>() {
			public Point2D transform(Suspect arg0) {
				return arg0.getLocation();
			}
		});
		JFrame jf = new JFrame();
		VisualizationViewer vv = new VisualizationViewer(l, dim);
		// Afficher les sommets avec des couleurs
		vv.getRenderContext().setVertexFillPaintTransformer(new Transformer() {
			public Paint transform(Object p) {
				if (COLORS.containsKey(p)) {
					return COLORS.get(p);
				}
				return Color.RED;
			}
		});
		// Afficher les arrêtes droites
		vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Integer,String>());
		// Afficher les labels des sommets
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		jf.getContentPane().add(vv);
		jf.pack();
		FRAME = jf;
		jf.setVisible(true);
	}
	
	public static void setNodeColor(Object node, Color color) {
		COLORS.put(node, color);
		if (FRAME != null) FRAME.repaint();
	}

}
