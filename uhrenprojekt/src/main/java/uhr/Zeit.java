package uhr;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* eine Uhr mit Sekundenzählung
*/
public class Zeit 
{
	private Future<?> laufen;
	private ScheduledExecutorService service;
	private int stunde, minute, sekunde;     

	/**
	 * erstellt die Uhr
	 */
    public Zeit() {
		//Thread starten, um die Uhrzeit laufen zu lassen:
    	service = Executors.newSingleThreadScheduledExecutor();
		laufen = service.scheduleAtFixedRate(() -> laufen(), 0, 1, TimeUnit.SECONDS);
    }

    /**
     * liefert die aktuelle Stunde
     * @return Stunde
     */
    public int getStunde() { return stunde; }

    /**
     * liefert die aktuelle Minute
     * @return Minute
     */
    public int getMinute() { return minute; }

    /**
     * liefert die aktuelle Sekunde
     * @return Sekunde
     */
    public int getSekunde() { return sekunde; }  
	
	private void laufen()
	{
		LocalTime jetzt = LocalTime.now();
		stunde = jetzt.getHour();
		minute = jetzt.getMinute();
		sekunde = jetzt.getSecond();
		benachrichtigen();
	}
	
	/**
	 * stoppt die laufende Uhr
	 */
	public void uhrStoppen()
	{
		laufen.cancel(false);
		service.shutdown();
	}

	/**
	 * enthaelt die Beschreibung der Zeit
	 */
	private List<Beobachter> anzeigeListe = new LinkedList<>();

	/**
	 * benachrichtigt alle angemeldeten Beobachter
	 */
	protected void benachrichtigen(){
		anzeigeListe.forEach(b->b.aktualisieren(this));
	}

	/**
	 * meldet b am Subjekt an
	 * @param b der neue Beobachter
	 */
	public void anmelden(Beobachter b){
		if(b != null)
			anzeigeListe.add(b);
	}

	/**
	 * meldet b am Subjekt ab
	 * @param b der zu entfernende Beobachter
	 */
	public void abmelden(Beobachter b){
		if(b != null)
			anzeigeListe.remove(b);
	}

	private void startZeit() {
		if (service != null && !service.isShutdown()) {
			return;
		}
		service = Executors.newSingleThreadScheduledExecutor();
		laufen = service.scheduleAtFixedRate(() -> laufen(), 0, 1, TimeUnit.SECONDS);
	}

	/**
	 * Uhr anhalten
	 */
	public void anhaltenUhr() {
		if (laufen != null) {
			laufen.cancel(false);
		}
		if (service != null) {
			service.shutdown();
		}
	}

	/**
	 * Uhr fortsetzen
	 */
	public void fortsetzenUhr() {
		if (service == null || service.isShutdown()) {
			startZeit();
		}
	}
}
