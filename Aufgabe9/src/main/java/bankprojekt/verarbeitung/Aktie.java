package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Eine Aktie, die ständig ihren Kurs verändert
 * @author Doro
 *
 */
public class Aktie {
	
	private static Map<String, Aktie> alleAktien = new HashMap<>();
	private String wkn;
	private Geldbetrag kurs;

	private final Random random = new Random();
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

	/**
	 * gibt die Aktie mit der gewünschten Wertpapierkennnummer zurück
	 * @param wkn Wertpapierkennnummer
	 * @return Aktie mit der angegebenen Wertpapierkennnummer oder null, wenn es diese WKN
	 * 			nicht gibt.
	 */
	public static Aktie getAktie(String wkn)
	{
		return alleAktien.get(wkn);
	}
	
	/**
	 * erstellt eine neue Aktie mit den angegebenen Werten
	 * @param wkn Wertpapierkennnummer
	 * @param k aktueller Kurs
	 * @throws IllegalArgumentException wenn einer der Parameter null bzw. negativ ist
	 * 		                            oder es eine Aktie mit dieser WKN bereits gibt
	 */
	public Aktie(String wkn, Geldbetrag k) {
		if(wkn == null || k == null || k.isNegativ() || alleAktien.containsKey(wkn))
			throw new IllegalArgumentException();	
		this.wkn = wkn;
		this.kurs = k;
		alleAktien.put(wkn, this);

		// Zufällige Zeitspanne zwischen 1 und 5 Sekunden
		int zeit = random.nextInt(5) + 1;

		// Kursänderungen planen
		scheduler.scheduleAtFixedRate(this::aendereKurs, zeit, zeit, TimeUnit.SECONDS);
	}

	/**
	 * Wertpapierkennnummer
	 * @return WKN der Aktie
	 */
	public String getWkn() {
		return wkn;
	}

	/**
	 * aktueller Kurs
	 * @return Kurs der Aktie
	 */
	public Geldbetrag getKurs() {
		return kurs;
	}

	private synchronized void aendereKurs() {
		double prozent = ((random.nextDouble() * 6) - 3);
		prozent = Math.round(prozent * 1000.0) / 1000.0;

		Geldbetrag temp1 = new Geldbetrag(kurs.getBetrag() * (prozent / 100.0));

		double temp2 = kurs.plus(temp1).getBetrag();
		temp2 = Math.round(temp2 * 1000.0) / 1000.0;

		kurs = new Geldbetrag(temp2);

		System.out.println("Kurs: " + kurs.getBetrag() + " --> geändert: " + prozent + "%");
	}
}
