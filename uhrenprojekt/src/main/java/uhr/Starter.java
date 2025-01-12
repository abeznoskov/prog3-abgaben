package uhr;

import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Startet eine Uhrenoberfläche
 * @author Doro
 *
 */
public class Starter extends Application {
	@FXML private Button btnAnalog;
	@FXML private Button btnDigital;
	@FXML private Button btnEntfernen;
	private Stage primaryStage;

	private List<DigitalUhr> dUhren = new LinkedList<>();
	private List<KreisUhr> kUhren = new LinkedList<>();

	private Zeit model;

	/**
     * Startet die Anwendung
     * @param primaryStage die Oberflaeche der Anwendung
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("starter.fxml"));
		loader.setController(this);
		Parent lc = loader.load();
		Scene scene = new Scene(lc, 300, 100);
		primaryStage.setTitle("Viele Uhren");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Initialisiere das Modell, bevor die UI vollständig initialisiert wird
		if (model == null) {
			model = new Zeit();
		}
	}

	@FXML private void initialize() {
		btnAnalog.setOnAction(e -> neueKreisUhr());
		btnDigital.setOnAction(e -> neueDigitalUhr());
		btnEntfernen.setOnAction(e -> alleEntfernen());
		primaryStage.setOnCloseRequest(e -> starterSchliessen());
	}

	/**
	 * wird beim Klick auf Analog-Button aufgerufen und angemeldet
	 */
	private void neueDigitalUhr() {
		DigitalUhr digitalUhr = new DigitalUhr(model);
		model.anmelden(digitalUhr);
		dUhren.add(digitalUhr);
	}
	/**
	 * wird beim Klick auf Analog-Button aufgerufen und angemeldet
	 */
	private void neueKreisUhr() {
		KreisUhr kreisUhr = new KreisUhr(model);
		model.anmelden(kreisUhr);
		kUhren.add(kreisUhr);
	}

	/**
     * Entfernt alle Uhren von der Oberflaeche
     */
	private void alleEntfernen() {
		for(KreisUhr k : kUhren) k.dispose();
		for(DigitalUhr d : dUhren) d.beenden();
		kUhren.clear();
		dUhren.clear();
	}

	/**
	 * Controller schließen
	 */
	private void starterSchliessen() {
		alleEntfernen();
	}

	/**
     * Uhr wird angehalten
     */
	public void anhalten() {
		if (model != null) {
			model.anhaltenUhr();
		}
	}

	/**
	 * Uhr wird fortgesetzt
	 */
	public void fortsetzen() {
		if (model != null) {
			model.fortsetzenUhr();
		}
	}
}