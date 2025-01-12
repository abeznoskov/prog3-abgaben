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

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = 
				new FXMLLoader(getClass().getResource("starter.fxml"));
		loader.setController(this);
		Parent lc = loader.load();
	    Scene scene = new Scene(lc, 300, 100);
        primaryStage.setTitle("Viele Uhren");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	@FXML private void initialize()
	{
		btnAnalog.setOnAction(e -> neueKreisUhr());
		btnDigital.setOnAction(e -> neueDigitalUhr());
		btnEntfernen.setOnAction(e -> alleEntfernen());
		primaryStage.setOnCloseRequest(e -> starterSchliessen());
	}
	
	/**
	 * wird beim Klick auf Digital-Button aufgerufen
	 */
	private void neueDigitalUhr()
	{
		dUhren.add(new DigitalUhr());
	}
	
	/**
	 * wird beim Klick auf Analog-Button aufgerufen
	 */
	private void neueKreisUhr()
	{
		kUhren.add(new KreisUhr());
	}
	
	private void alleEntfernen()
	{
		for(KreisUhr k : kUhren) k.dispose();
		for(DigitalUhr d : dUhren) d.beenden();
		kUhren.clear();
		dUhren.clear();
	}
	
	private void starterSchliessen()
	{
	}
}
