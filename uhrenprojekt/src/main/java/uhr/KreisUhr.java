package uhr;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * stellt eine analoge Uhr dar
 *
 * @author Doro
 *
 */
public class KreisUhr extends JFrame implements Beobachter {
	private static final long serialVersionUID = 1L;
	private static final String TITEL = "Kreisuhr";
	private static final String INFO = "Tasten: A(usschalten), E(inschalten)";
	private static final int BREITE = 400;
	private static final int HOEHE = 300;
	private static final int ZENTRUM_X = BREITE / 2;
	private static final int ZENTRUM_Y = HOEHE / 2;
	private static final int RADIUS = 100;
	private static final int DURCHMESSER = 2 * RADIUS;
	private static final int POS_INFO_X = 20;
	private static final int POS_INFO_Y = 40;
	private static final int POS_UHRZEIT = 60;
	private static final int[] ZEIGERLAENGE = new int[] { 5 * RADIUS / 10, 6 * RADIUS / 10, 7 * RADIUS / 10 };
	private static final Color[] ZEIGERFARBE = new Color[] { Color.red, Color.green, Color.blue };
	private static final Color[] GRAUE_FARBEN = new Color[] { Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY };
	private static final Color HINTERGRUND_FARBE = Color.white;
	private static final Color KREIS_FARBE = Color.black;
	private static final int[][] END_X = new int[3][60];
	private static final int[][] END_Y = new int[3][60];
	private static final double ZWEI_PI = 2 * Math.PI;
	private static final double[] KONST = new double[] { ZWEI_PI / 12, ZWEI_PI / 60, ZWEI_PI / 60 };

	private Zeit uhrzeit;
	private boolean uhrAn;
	private Color[] farben = ZEIGERFARBE;

	/**
	 * Erstellt die analoge Uhr und bringt sie auf den Bildschirm
	 */
	public KreisUhr(Zeit zeitModell) {
		this.uhrzeit = zeitModell; // Nutze die übergebene Zeit-Instanz
		zeitModell.anmelden(this); // Melde diese View als Beobachter an
		uhrAn = true;

		// Erstellen der Oberflächenelemente:
		setTitle(TITEL);
		setSize(BREITE, HOEHE);
		setVisible(true);

		// Einrichten des KeyListeners, d.h., die Uhr reagiert auf Tastendruck

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (Character.toUpperCase(e.getKeyChar())) {
					case 'E' -> {
						uhrAn = true;
						farben = ZEIGERFARBE;
						zeitModell.fortsetzenUhr(); // Setzt das Zeitmodell fort
					}
					case 'A' -> {
						uhrAn = false;
						farben = GRAUE_FARBEN;
						zeitModell.anhaltenUhr(); // Haelt das Zeitmodell an
					}
				}
				repaint();
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Bei Fenster schließen aufräumen
			}
		});
	}



	/**
	 * Analoge Uhr zeichnen
	 */
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, BREITE, HOEHE);
		g.setColor(HINTERGRUND_FARBE);
		g.fillRect(0, 0, BREITE, HOEHE);
		g.setColor(KREIS_FARBE);
		g.drawOval((BREITE - DURCHMESSER) / 2, (HOEHE - DURCHMESSER) / 2, DURCHMESSER, DURCHMESSER);
		if (uhrzeit != null) {
			final int[] zeit = new int[] { uhrzeit.getStunde(), uhrzeit.getMinute(), uhrzeit.getSekunde() };
			g.drawString(INFO, POS_INFO_X, POS_INFO_Y);
			g.drawString(String.format("%02d:%02d:%02d", zeit[0], zeit[1], zeit[2]), POS_INFO_X, POS_UHRZEIT);
			for (int i = 0; i < END_X.length; i++) {
				final int z = zeit[i];
				if (END_X[i][z] == 0) {
					final double grad = z * KONST[i];
					END_X[i][z] = (int) (ZENTRUM_X + ZEIGERLAENGE[i] * Math.sin(grad));
					END_Y[i][z] = (int) (ZENTRUM_Y - ZEIGERLAENGE[i] * Math.cos(grad));
				}
				g.setColor(farben[i]);
				g.drawLine(ZENTRUM_X, ZENTRUM_Y, END_X[i][zeit[i]], END_Y[i][zeit[i]]);
			}
		}
	}

	/**
	 * Wird aufgerufen, wenn sich die Uhrzeit verändert
	 * @param zeit aktuelle Zeit
	 */
	@Override
	public void aktualisieren(Zeit zeit) {
		this.uhrzeit = zeit;
		repaint();
	}
}