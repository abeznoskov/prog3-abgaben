package spielereien;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Formatierung {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie eine ganze Zahl ein: ");
        int intNumber = scanner.nextInt();

        System.out.print("Geben Sie eine Zahl mit Nachkommastellen ein: ");
        double doubleNumber = scanner.nextDouble();

        try (PrintWriter writer = new PrintWriter(new FileWriter("formatted_output.txt"))) {
            // 1. Ganze Zahl ohne weitere Formatierung
            writer.println(intNumber);

            // 2. Ganze Zahl mit 12 Stellen, vorne mit Nullen aufgefüllt
            writer.printf("%012d%n", intNumber);

            // 3. Ganze Zahl mit Vorzeichen und Tausendertrennzeichen
            writer.printf("%+,d%n", intNumber);

            // 4. Ganze Zahl hexadezimal mit Großbuchstaben
            writer.printf("%X%n", intNumber);

            // 5. Zahl mit Nachkommastellen in Standardformatierung
            writer.println(doubleNumber);

            // 6. Zahl mit Vorzeichen und 4 Nachkommastellen
            writer.printf("%+.4f%n", doubleNumber);

            // 7. Zahl in wissenschaftlicher Darstellung
            writer.printf("%e%n", doubleNumber);

            // 8. Zahl mit 2 Nachkommastellen und Punkt als Dezimaltrennzeichen (US-Format)
            writer.printf(Locale.US, "%.2f%n", doubleNumber);

            // 9. Aktuelles Datum: Tag (ohne führende Null), ausgeschriebener Monat, Jahr, abgekürzter Wochentag
            LocalDate today = LocalDate.now();
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("d MMMM uuuu (EEE)", Locale.GERMAN);
            writer.println(today.format(germanFormatter));

            // 10. Aktuelles Datum: zweistelliger Tag/Monat/Jahr, ausgeschriebener Wochentag auf Französisch
            DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu EEEE", Locale.FRENCH);
            writer.println(today.format(frenchFormatter));

            // 11. Aktuelle Uhrzeit im englischen Format (ohne führende Nullen, am/pm)
            LocalTime now = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            writer.println(now.format(timeFormatter));

            System.out.println("Die Datei 'formatted_output.txt' wurde erfolgreich erstellt.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
        }

        scanner.close();
    }
}
