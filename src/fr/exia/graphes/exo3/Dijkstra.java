package fr.exia.graphes.exo3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedGraph;
import fr.exia.graphes.Player;

public class Dijkstra {

	public static Trajet getShortestPath(UndirectedGraph<Ville, Chemin> g, Ville from, Ville to) {
		from.color = Color.RED;
		to.color = Color.RED;
		Player.pause();
		// On fabrique un premier trajet, qui part de la ville initiale
		List<Trajet> trajets = new ArrayList<Trajet>();
		trajets.add(new Trajet(from));
		// On boucle jusqu'à avoir trouvé
		while (true) {
			// On cherche le trajet ayant la plus courte distance
			Trajet min = null;
			for (Trajet t : trajets) {
				// Si on a trouvé un chemin
				if (t.hasReachedTarget()) {
					to.color = Color.BLACK;
					t.setColor(Color.RED);
					Player.pause();
					return t;
				}
				// On détermine si c'est le plus court chemin
				if (min == null || t.getDistance() < min.getDistance()) {
					min = t;
				}
			}
			// On explore ce trajet
			dijkstra(g, trajets, min, to);
		}
	}

	private static void dijkstra(UndirectedGraph<Ville, Chemin> g, List<Trajet> trajets, Trajet t, Ville to) {
		Ville l = t.getLast();
		l.color = Color.GREEN;
		Player.pause();
		trajets.remove(t);
		int i = 0;
		// On parcours tous les chemins qui partent de la dernière ville
		for (Chemin c : g.getIncidentEdges(l)) {
			//On recupère la ville au bout du chemin
			Ville v = c.getOther(l);
			// On ne revient pas en arrière
			if (v.visited()) continue;
			i++;
			v.color = Color.ORANGE;
			// On fabrique un nouveau trajet
			Trajet tt = new Trajet(t);
			// Et on le rajoute dans la liste
			trajets.add(tt);
			// On ajoute cette ville sur le trajet
			tt.add(v, c);
			// On a trouvé la ville de destination !
			if (v == to) {
				tt.setTargetReached();
			}
		}
		if (i > 0) Player.pause();
	}
	
}
