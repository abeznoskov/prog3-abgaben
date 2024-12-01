package ballspiel;

import java.util.Random;
import javafx.application.Application;
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

	}
	
	/**
	 * erzeugt einen neuen Ball und macht ihn in der Oberfläche sichtbar
	 */
	public void neuerBall()
	{
		Random r = new Random();
		int dauer = r.nextInt(500) + 1000; // Zufallszahl zwischen 1000 und 1500
		int farbe = r.nextInt(3);
		int dx = r.nextInt(5) + 1;
		int dy = r.nextInt(5) + 1;
		Ball b = new Ball(view.getVerfuegbareBreite(), view.getVerfuegbareHoehe(), dx, dy, farben[farbe]);
		b.huepfen(dauer);
		view.ballEintragen(b);
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
	public void alleBeenden()
	{
		
	}

}
