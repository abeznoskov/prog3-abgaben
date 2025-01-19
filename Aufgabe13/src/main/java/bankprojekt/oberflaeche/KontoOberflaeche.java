package bankprojekt.oberflaeche;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import bankprojekt.geld.Waehrung;

public class KontoOberflaeche extends BorderPane {

	private Text nummer;
	private Text stand;
	private CheckBox gesperrt;
	private TextArea adresse;
	private Text meldung;
	private TextField betrag;
	private ChoiceBox<Waehrung> waehrung;
	private Button einzahlen;
	private Button abheben;

	public KontoOberflaeche() {
		Text ueberschrift = new Text("Ein Konto verändern");
		ueberschrift.setFont(new Font("Sans Serif", 25));
		BorderPane.setAlignment(ueberschrift, Pos.CENTER);
		this.setTop(ueberschrift);

		GridPane anzeige = new GridPane();
		anzeige.setPadding(new Insets(20));
		anzeige.setVgap(10);
		anzeige.setAlignment(Pos.CENTER);

		Text txtNummer = new Text("Kontonummer:");
		txtNummer.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtNummer, 0, 0);
		nummer = new Text();
		nummer.setFont(new Font("Sans Serif", 15));
		GridPane.setHalignment(nummer, HPos.RIGHT);
		anzeige.add(nummer, 1, 0);

		Text txtStand = new Text("Kontostand:");
		txtStand.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtStand, 0, 1);
		stand = new Text();
		stand.setFont(new Font("Sans Serif", 15));
		GridPane.setHalignment(stand, HPos.RIGHT);
		anzeige.add(stand, 1, 1);

		Text txtGesperrt = new Text("Gesperrt: ");
		txtGesperrt.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtGesperrt, 0, 2);
		gesperrt = new CheckBox();
		GridPane.setHalignment(gesperrt, HPos.RIGHT);
		anzeige.add(gesperrt, 1, 2);

		Text txtAdresse = new Text("Adresse: ");
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
		anzeige.add(meldung, 0, 4, 2, 1);

		this.setCenter(anzeige);

		HBox aktionen = new HBox();
		aktionen.setSpacing(10);
		aktionen.setAlignment(Pos.CENTER);

		betrag = new TextField("100.00");
		waehrung = new ChoiceBox<>();
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

	// Getter-Methoden

	public Text getNummer() {
		return nummer;
	}

	public Text getStand() {
		return stand;
	}

	public CheckBox getGesperrt() {
		return gesperrt;
	}

	public TextArea getAdresse() {
		return adresse;
	}

	public Text getMeldung() {
		return meldung;
	}

	public TextField getBetrag() {
		return betrag;
	}

	public ChoiceBox<Waehrung> getWaehrung() {
		return waehrung;
	}

	public Button getEinzahlenButton() {
		return einzahlen;
	}

	public Button getAbhebenButton() {
		return abheben;
	}
}