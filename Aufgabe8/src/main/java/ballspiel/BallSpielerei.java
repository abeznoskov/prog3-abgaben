package ballspiel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Startet ein kleines Ballspiel als Übung für Threads
 *
 */
public class BallSpielerei extends Application {
	private BallOberflaeche view;
	private Farbtopf[] farben = {new Farbtopf(Color.BLUE), new Farbtopf(Color.YELLOW), new Farbtopf(Color.RED)};

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hüpfende Bälle");
		view = new BallOberflaeche(this);
		Scene scene = new Scene(view, 500, 400, false);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {alleBeenden();});
		primaryStage.show();

		// Uhrzeit-Thread starten
		Thread uhrzeitThread = new Thread(() -> {
			while (true) {
				String aktuelleZeit = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
				Platform.runLater(() -> view.setUhrzeit(aktuelleZeit)); // UI-Update im JavaFX-Thread
				try {
					Thread.sleep(1000); // Aktualisierung jede Sekunde
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		});
		uhrzeitThread.setDaemon(true);
		uhrzeitThread.start();
	}


	private List<Thread> ballThreads = new ArrayList<>();

	/**
	 * erzeugt einen neuen Ball und macht ihn in der Oberfläche sichtbar
	 */
	public void neuerBall() {
		Random r = new Random();
		int dauer = r.nextInt(500) + 1000; // Zufallszahl zwischen 1000 und 1500
		int farbe = r.nextInt(3);
		int dx = r.nextInt(5) + 1;
		int dy = r.nextInt(5) + 1;
		Ball b = new Ball(view.getVerfuegbareBreite(), view.getVerfuegbareHoehe(), dx, dy, farben[farbe]);
		view.ballEintragen(b);

		Thread t = new Thread(() -> b.huepfen(dauer));
		t.setDaemon(true); // Damit Threads automatisch beendet werden
		t.start();
		ballThreads.add(t);
	}


	/**
	 * farben
	 * @return farben
	 */
	public Farbtopf[] getFarben() {
		return farben;
	}

	public void auffuellen(Farbtopf topf)
	{
		Random r = new Random();
		int menge = r.nextInt(5000) + 1000;
		topf.fuellstandErhoehen(menge);
	}

	/**
	 * beendet das Hüpfen aller Bälle
	 */
	public void alleBeenden() {
		Platform.runLater(() -> view.getSpielflaeche().getChildren().clear()); // Alle Bälle entfernen
		for (Thread t : ballThreads) {
			t.interrupt(); // Threads unterbrechen
		}
		ballThreads.clear(); // Liste leeren
	}


}
