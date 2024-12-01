package ballspiel;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * ein hüpfender Ball für eine JavaFx-Oberfläche
 *
 */
public class Ball extends Circle{
	private static final int RADIUS = 8;
	private static final int BENOETIGTE_MENGE = 2;
	private final double breite;
	private final double hoehe;
	private int dx = 2;
	private int dy = 2;
	private double x;
	private double y;
	private Farbtopf topf;
	
	/**
	 * erstellt einen Ball und lässt ihn loshüpfen
	 * @param breite Breite des zur Verfügung stehenden Platzes
	 * @param hoehe Höhe des zur Verfügung stehenden Platzes
	 * @param dx Sprungweite des Balles in x-Richtung (zwischen 1 und 5)
	 * @param dy Sprungweite des Balles in y-Richtung (zwischen 1 und 5)
	 * @param topf Farbtopf, mit dessen Farbe dieser Ball gezeichnet wird
	 */
	public Ball(double breite, double hoehe, int dx, int dy, Farbtopf topf) {
		this.breite = breite;
		this.hoehe = hoehe;
		this.setFill(topf.getFarbe());
		this.setVisible(true);
		this.setRadius(RADIUS);
		x = RADIUS;
		y = RADIUS;
		zeichnen(true);
		this.topf = topf;
		this.dx = Math.max(Math.min(dx, 5), 1);
		this.dy = Math.max(Math.min(dy, 5), 1);
	}

	/**
	 * setzt den Ball an die angegebene Position
	 * @param grau true, wenn der Ball in grau gezeichnet werden soll, 
	 *             false, wenn er in der richtigen Farbe gezeichnet werden soll
	 */
	private void zeichnen(boolean grau) {
		Platform.runLater(() ->
		{
			if(grau)
				this.setFill(Color.GREY);
			else
				this.setFill(topf.getFarbe());	
			this.setLayoutX(x);
			this.setLayoutY(y);
		});
	}
	
	/**
	 * macht den Ball unsichtbar
	 */
	private void unsichtbarMachen()
	{
		Platform.runLater(() ->
		{
			this.setVisible(false);
		});
	}

	/**
	 * bewegt den Ball einen Schritt weiter
	 */
	private void einSchrittWeiter() {
		topf.fuellstandVerringern(BENOETIGTE_MENGE);
		x += dx;
		y += dy;
		if (x - RADIUS <= 0) {
			x = RADIUS;
			dx = -dx;
		}
		if (x + RADIUS >= breite) {
			x = breite - RADIUS;
			dx = -dx;
		}
		if (y - RADIUS <= 0) {
			y = RADIUS;
			dy = -dy;
		}
		if (y + RADIUS >= hoehe) {
			y = hoehe - RADIUS;
			dy = -dy;
		}
		zeichnen(false);
	}

	/**
	 * bewegt den Ball dauer viele Schritte weiter, ohne die UI zu blockieren.
	 * @param dauer Anzahl der Schritte
	 */
	public void huepfen(int dauer) {
		Runnable task = () -> {
			for (int i = 0; i < dauer; i++) {
				synchronized (topf) {
					while (topf.getFuellstand() < BENOETIGTE_MENGE) {
						try {
							topf.wait(); // Wartet auf Benachrichtigung
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return; // Thread sauber beenden
						}
					}
					topf.fuellstandVerringern(BENOETIGTE_MENGE);
				}
				Platform.runLater(this::einSchrittWeiter); // Ball bewegen im UI-Thread
				try {
					Thread.sleep(5); // Pause für flüssige Animation
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
			Platform.runLater(this::unsichtbarMachen); // Ball entfernen
		};

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}




}
