package spielereien;

import mathematik.Intervall;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Pepe Duks
 * Main-Klasse zum testen von mathematik.Intervall
 */
public class Hauptprogramm {
    /**
     * Ad-Hoc Tests für die Klasse mathematik.Intervall.
     * @param args nicht benutzt
     */
    public static void main(String[] args) {
        /*
        Integer i1 = 4;
        Integer i2 = 8;
        Intervall<Integer> integerIntervall = new Intervall<>(i1, i2);

        System.out.println(integerIntervall.enthaelt(3));
        System.out.println(integerIntervall.enthaelt(4));
        System.out.println(integerIntervall.enthaelt(7));
        System.out.println(integerIntervall.enthaelt(8));
        System.out.println(integerIntervall.enthaelt(9));

         */

        String s1 = "Apfel";
        String s2 = "Birne";
        String s3 = "Clementine";
        String s4 = "Dattel";
        String s5 = "Erdbeere";
        String s6 = "Feige";

        Intervall<String> stringIntervall = new Intervall<>(s2, s4);

        System.out.println("- - - - - - - - - - - - ");
        System.out.println("String- Vergleiche:");
        System.out.println("--------------");
        System.out.println("Intervall: " + stringIntervall);
        System.out.println("Apfel liegt im Intervall: " + stringIntervall.enthaelt(s1));
        System.out.println("Clementine liegt im Intervall: " + stringIntervall.enthaelt(s3));
        System.out.println("Dattel liegt im Intervall: " + stringIntervall.enthaelt(s4));

        Intervall<String> stringIntervall2 = new Intervall<>(s3, s5);

        System.out.println("--------------");
        System.out.println("Intervall2: " + stringIntervall2);
        System.out.println("Schnittmenge: " + stringIntervall2.schnitt(stringIntervall));

        Intervall<String> stringIntervall3 = new Intervall<>(s5, s3);
        System.out.println("--------------");
        System.out.println("Schnittmenge3: " + stringIntervall3);
        System.out.println("Leer?: " + stringIntervall3.isLeer());

        System.out.println("- - - - - - - - - - - - ");
        System.out.println("Date-Vergleiche");
        System.out.println("--------------");
        Date date1 = new Date(108, 10, 12);
        Date date2 = new Date(109, 1, 30);
        Date date3 = new Date(110, 04, 22);
        Date date4 = new Date(111, 06, 21);

        Intervall<Date> dateIntervall1 = new Intervall<>(date1, date3);
        Intervall<Date> dateIntervall2 = new Intervall<>(date2, date4);

        System.out.println("Intervall1: " + dateIntervall1);
        System.out.println("Intervall2: " + dateIntervall2);
        System.out.println("Schnittmenge: " + dateIntervall2.schnitt(dateIntervall1));

        Time time1 = new Time(1264339392000L);
        Time time2 = new Time(System.currentTimeMillis());
        Time time3 = new Time(12642876123412L);

        Intervall<Date> timeIntervall = new Intervall<>(time1, time3);
        Intervall<Date> dateTimeIntervall = timeIntervall.schnitt(dateIntervall1);

        System.out.println("--------------");
        System.out.println("SQL-Zeit");
        System.out.println("Zeitintervall: " + timeIntervall);
        System.out.println("Schnittintervall: " + dateTimeIntervall);

        Timestamp timestamp1 = new Timestamp(1274358192000L);

        System.out.println("--------------");
        System.out.println("Timestamp");
        System.out.println("Timestamp1: " + timestamp1);
        System.out.println("Timestamp1 ist im DateIntervall enthalten: " + dateIntervall1.enthaelt(timestamp1));
        System.out.println("Timestamp1 ist im TimeIntervall enthalten: " + timeIntervall.enthaelt(timestamp1));

        /*
        Intervall<Object> falsch; //hier kommt der Compilerfehler
        Intervall<String> richtig = new Intervall<>("A", "B");
        richtig.enthaelt(3.5); //hier kommt der Compilerfehler
        Intervall<Double> zahlen = new Intervall<>(1.2, 3.4);
        Intervall<String> texte = new Intervall<>("a", "b");
        zahlen.schnitt(texte); //hier kommt der Compilerfehler
         */
    }
}
