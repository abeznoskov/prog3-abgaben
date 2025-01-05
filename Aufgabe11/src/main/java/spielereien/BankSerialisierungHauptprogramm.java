package spielereien;

import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verwaltung.Bank;
import java.io.*;
/*
public class BankSerialisierungHauptprogramm {
    public static void main(String[] args) {
        // Erstellen einer Bank
        Bank bank = new Bank(12345678L);

        // Testweise ein Konto hinzufügen
        bank.kontoErstellen(new Kunde());

        // Speichern der Bank
        try (FileOutputStream fos = new FileOutputStream("bank.txt")) {
            bank.speichern(fos);
            System.out.println("Bank wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Laden der Bank
        try (FileInputStream fis = new FileInputStream("bank.txt")) {
            Bank geladeneBank = Bank.einlesen(fis);
            System.out.println("Bank wurde erfolgreich geladen.");
            System.out.println("Geladene Bankleitzahl: " + geladeneBank.getBankleitzahl());
            System.out.println("Alle Konten: " + geladeneBank.getAlleKonten());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/