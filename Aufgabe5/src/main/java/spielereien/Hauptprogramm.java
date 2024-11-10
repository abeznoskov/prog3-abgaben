package spielereien;

import mathematik.Intervall;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Main-Klasse zum testen von mathematik.Intervall
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class Hauptprogramm {
    /**
     * Ad-Hoc Tests für die Klasse mathematik.Intervall
     */
    public static void main(String[] args) {

        //ein Intervall von Strings anlegen und alle drei Methoden mindestens einmal dafür auf rufen.
        String s1 = "Anna";
        String s2 = "Bob";
        String s3 = "Clara";
        String s4 = "Dominik";
        String s5 = "Ellen";
        String s6 = "Frank";
        String s7 = "Gert";
        String s8 = "Hans";

        // isLeer()
        Intervall<String> stringIntervall1 = new Intervall<>(s1, s8);
        System.out.println("stringIntervall1: " + stringIntervall1);
        System.out.println("Leeres Intervall?: " + stringIntervall1.isLeer());

        System.out.println("----------------------------------------------------------------");

        // enthaelt()
        Intervall<String> stringIntervall2 = new Intervall<>(s3, s7);
        System.out.println("stringIntervall2: " + stringIntervall2);
        System.out.println("Liegt Bob im Intervall?: " + stringIntervall2.enthaelt(s2));
        System.out.println("Liegt Ellen im Intervall?: " + stringIntervall2.enthaelt(s5));
        System.out.println("Liegt Gert im Intervall?: " + stringIntervall2.enthaelt(s7));
        System.out.println("Liegt Hans im Intervall?: " + stringIntervall2.enthaelt(s8));

        System.out.println("----------------------------------------------------------------");

        // schnitt()
        Intervall<String> stringIntervall3 = new Intervall<>(s4, s8);
        System.out.println("stringIntervall3: " + stringIntervall3);
        System.out.println("Schnittmenge von Intervall2 und Intervall3: " + stringIntervall2.schnitt(stringIntervall3));

        System.out.println("----------------------------------------------------------------");

        /* Part1: ein Intervall mit dem Typparameter java.util.Date anlegen und den Schnitt mit einem zweiten Intervall von
         * java.util.Date bilden
         * Part2: dann noch den Schnitt mit einem Intervall von java.sql.Time.
         */

        //Part1 dateIntervall1 geschnitten mit dateIntervall2:
        Date date1 = new Date(100, 0, 20);
        Date date2 = new Date(103, 4, 13);
        Date date3 = new Date(105, 11, 11);
        Date date4 = new Date(107, 8, 17);

        Intervall<Date> dateIntervall1 = new Intervall<>(date1, date3);
        Intervall<Date> dateIntervall2 = new Intervall<>(date2, date4);

        System.out.println("dateIntervall1: " + dateIntervall1);
        System.out.println("dateIntervall2: " + dateIntervall2);
        System.out.println("Schnittmenge von dateIntervall1 und dateIntervall2: " + dateIntervall2.schnitt(dateIntervall1));

        System.out.println("----------------------------------------------------------------");

        // Part2: dateIntervall1 geschnitten mit timeIntervall
        Time time1 = new Time(4364347618030L);
        Time time2 = new Time(956428762223452L);
        Time time3 = new Time(System.currentTimeMillis());

        Intervall<Date> timeIntervall = new Intervall<>(time1, time3);
        Intervall<Date> dateTimeIntervall = timeIntervall.schnitt(dateIntervall1);

        System.out.println("timeIntervall: " + timeIntervall);
        System.out.println("Schnittintervall aus dateIntervall1 und timeIntervall: " + dateTimeIntervall);

        System.out.println("----------------------------------------------------------------");

        Timestamp timestamp1 = new Timestamp(7274358192000L);
        System.out.println("Timestamp1: " + timestamp1);
        System.out.println("Ist Timestamp1 im dateIntervall1 enthalten?: " + dateIntervall1.enthaelt(timestamp1));
        System.out.println("Ist Timestamp1 im timeIntervall enthalten? " + timeIntervall.enthaelt(timestamp1));


        // Befehle die nicht funktionieren sollen:

        //Intervall<Object> falsch; //hier kommt der Compilerfehler
        Intervall<String> richtig = new Intervall<>("A", "B");
        //richtig.enthaelt(3.5); //hier kommt der Compilerfehler
        Intervall<Double> zahlen = new Intervall<>(1.2, 3.4);
        Intervall<String> texte = new Intervall<>("a", "b");
        //zahlen.schnitt(texte); //hier kommt der Compilerfehler

    }
}
