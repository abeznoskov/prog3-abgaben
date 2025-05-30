package bankprojekt.oberflaeche;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import bankprojekt.geld.Waehrung;
import javafx.beans.binding.Bindings;

/**
 * Eine Oberfläche für ein einzelnes Konto. Man kann einzahlen
 * und abheben und sperren und die Adresse des Kontoinhabers 
 * ändern
 * @author Doro
 *
 */
public class KontoOberflaeche extends BorderPane {
	private Text ueberschrift;
	private GridPane anzeige;
	private Text txtNummer;
	/**
	 * Anzeige der Kontonummer
	 */
	private Text nummer;
	private Text txtStand;
	/**
	 * Anzeige des Kontostandes
	 */
	private Text stand;
	private Text txtGesperrt;
	/**
	 * Anzeige und Änderung des Gesperrt-Zustandes
	 */
	private CheckBox gesperrt;
	private Text txtAdresse;
	/**
	 * Anzeige und Änderung der Adresse des Kontoinhabers
	 */
	private TextArea adresse;
	/**
	 * Anzeige von Meldungen über Kontoaktionen
	 */
	private Text meldung;
	private HBox aktionen;
	/**
	 * Auswahl des Betrags für eine Kontoaktion
	 */
	private TextField betrag;
	/**
	 * Auswahl für die Währung des Betrages für eine Kontoaktion
	 */
	private ChoiceBox<Waehrung> waehrung;
	/**
	 * löst eine Einzahlung aus
	 */
	private Button einzahlen;
	/**
	 * löst eine Abhebung aus
	 */
	private Button abheben;

	/**
	 * erstellt die Oberfläche
	 */
	public KontoOberflaeche()
	{
		ueberschrift = new Text("Ein Konto verändern");
		ueberschrift.setFont(new Font("Sans Serif", 25));
		BorderPane.setAlignment(ueberschrift, Pos.CENTER);
		this.setTop(ueberschrift);

		anzeige = new GridPane();
		anzeige.setPadding(new Insets(20));
		anzeige.setVgap(10);
		anzeige.setAlignment(Pos.CENTER);

		txtNummer = new Text("Kontonummer:");
		txtNummer.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtNummer, 0, 0);
		nummer = new Text();
		nummer.setFont(new Font("Sans Serif", 15));
		GridPane.setHalignment(nummer, HPos.RIGHT);
		anzeige.add(nummer, 1, 0);

		txtStand = new Text("Kontostand:");
		txtStand.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtStand, 0, 1);
		stand = new Text();
		stand.setFont(new Font("Sans Serif", 15));
		GridPane.setHalignment(stand, HPos.RIGHT);
		anzeige.add(stand, 1, 1);

		txtGesperrt = new Text("Gesperrt: ");
		txtGesperrt.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtGesperrt, 0, 2);
		gesperrt = new CheckBox();
		GridPane.setHalignment(gesperrt, HPos.RIGHT);
		anzeige.add(gesperrt, 1, 2);

		txtAdresse = new Text("Adresse: ");
		txtAdresse.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtAdresse, 0, 3);
		adresse = new TextArea();
		adresse.setPrefColumnCount(25);
		adresse.setPrefRowCount(2);
		GridPane.setHalignment(adresse, HPos.RIGHT);
		anzeige.add(adresse, 1, 3);

		meldung = new Text("Willkommen lieber Benutzer");
		meldung.setFont(new Font("Sans Serif", 15));
		meldung.setFill(Color.RED);
		anzeige.add(meldung,  0, 4, 2, 1);

		this.setCenter(anzeige);

		aktionen = new HBox();
		aktionen.setSpacing(10);
		aktionen.setAlignment(Pos.CENTER);

		betrag = new TextField("100.00");
		waehrung = new ChoiceBox();
		waehrung.setItems(FXCollections.observableArrayList(Waehrung.values()));
		waehrung.getSelectionModel().select(0);
		aktionen.getChildren().add(betrag);
		aktionen.getChildren().add(waehrung);
		einzahlen = new Button("Einzahlen");
		aktionen.getChildren().add(einzahlen);
		abheben = new Button("Abheben");
		aktionen.getChildren().add(abheben);

		this.setBottom(aktionen);
	}

	/** Fuer Aufgabe 3 a)
     *  Gibt den Kontostand zurueck
     */
	public Text getNummer() {
		return nummer;
	}

	/** Fuer Aufgabe 3 c)
	 *  Gibt meldung zurueck
	 */
	public Text getMeldung() {
		return meldung;
	}
	/**
	 * Gibt den betrag zurueck
	 */
	public TextField getBetrag() {
		return betrag;
	}
	/**
	 * Gibt die Art der Waehrung zurueck, aus der ChoiceBox
	 */
	public ChoiceBox<Waehrung> getWaehrung() {
		return waehrung;
	}
	/**
	 * gibt den Button fuer das Einzahlen zurueck
	 */
	public Button getEinzahlenButton() {
		return einzahlen;
	}
	/**
	 * gibt den Button fuer das Abheben zurueck
	 */
	public Button getAbhebenButton() {
		return abheben;
	}

	/** Fuer Aufgabe 3 e)
	 *  Gibt die Adresse des Kunden zurueck
	 */
	public TextArea getAdresse() {
		return adresse;
	}


	/** Aufgabe 3 b)
	 * Methode bindet die ReadOnlyKontostand an dem anzuzeigenden stand
	 * und faebrt diese anhand des Kontostandes gruen/rot
	 * @param readOnlyKontostand der Kontostand als ReadOnly
	 */
	public void bindToModel(ReadOnlyDoubleProperty readOnlyKontostand) {
		stand.textProperty().bind(Bindings.format("%.2f", readOnlyKontostand));

		stand.fillProperty().bind(Bindings.createObjectBinding(() -> {
			double currentValue = readOnlyKontostand.get();
			return currentValue < 0 ? Color.RED : Color.GREEN;
		}, readOnlyKontostand));
	}

	/** Aufgabe 3 d)
	 *  Methode, um die gesperrtProperty des Kontos mit der CheckBox zu verbinden
	 * 	bindet die Checkbox bidirectional an die gesperrtProperty des Kontos
	 *  @param gesperrtProperty gesperrt Status als ReadOnly
	 */
	public void bindGesperrtToModel(BooleanProperty gesperrtProperty) {
		gesperrt.selectedProperty().bindBidirectional(gesperrtProperty);
	}

	/** Aufgabe 3 e)
	 * Bindet die TextArea bidirectional an die Adresse des Kunden
	 * @param adresseProperty adresse Property des Kunden
	 */
	public void bindAdresseToModel(StringProperty adresseProperty) {
		adresse.textProperty().bindBidirectional(adresseProperty);
	}
}
