package ballspiel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Startet ein kleines Ballspiel als Übung für Threads
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
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

		// Jede Minute Uhrzeit updaten z.B. mit sleep(). Endlosschleife für eine laufende Uhr
		Thread uhrzeitThread = new Thread(() -> {
			while (true) {
				// aktuelle Uhrzeit als String erzeugen
				String aktuelleZeit = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
				view.setUhrzeit(aktuelleZeit);
				try {
					Thread.sleep(60000); // Damit es nur jede 60 Sekunden updated
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		});
		uhrzeitThread.start();
	}


	// Liste fuer die verschiedenen Threads der Baelle
	private final List<Thread> ballThreads = new ArrayList<>();

	/**
	 * erzeugt einen neuen Ball und macht ihn in der Oberfläche sichtbar
	 */
	public void neuerBall() {
		Random r = new Random();
		int dauer = r.nextInt(500) + 1000;
		int farbe = r.nextInt(3);
		int dx = r.nextInt(5) + 1;
		int dy = r.nextInt(5) + 1;
		Ball b = new Ball(view.getVerfuegbareBreite(), view.getVerfuegbareHoehe(), dx, dy, farben[farbe]);
		view.ballEintragen(b);

		// erzeugter Ball huepft nebenlauufig, damit die anderen Buttons ueberhaupt reagieren koennen
		// bzw. damit man mehrere Baelle parallel huepfen lassen kann
		Thread t = new Thread(() -> b.huepfen(dauer));
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
		// loescht alle Baelle auf der Oberflaesche/Pane
		view.getSpielflaeche().getChildren().clear();

		// beendet alle laufenden Threads aus ballThreads
		for (Thread t : ballThreads) {
			t.interrupt();
		}

		// setzt alle Threads in ballThreads auf null
		ballThreads.clear();
	}


}
