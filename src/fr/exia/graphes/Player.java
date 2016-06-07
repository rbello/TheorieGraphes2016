package fr.exia.graphes;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Player {

	private static boolean lock = false;
	public static JFrame frame = null;

	public static void next() {
		lock = false;
	}

	public static void pause() {
		lock = true;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.repaint();
			}
		});
		while (lock) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
