import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * @author Marvin Hill (211926)
 * @author Janina Dörlinger (211923)
 */
public class PermParity {
  static final Scanner scanner = new Scanner(System.in); // für die Eingabe von n
  static long count = 0;

  public static void main(String[] args) throws IOException {
    // Hauptschleife des Programms.
    while (true) {
      // Abfrage einer User-Eingabe.
      System.out.print("Please enter n: ");
      int n = scanner.nextInt();
      System.out.println("new n: " + n);
      // Berechnung aller Permutationen von n.
      computeAllParity(n);
    }
  }

  // Berechnet alle möglichen Permutationen von einem gegebenen n und die Ergebnisse werden, sobald
  // sie gefunden werden in aufsteigender Reihenfolge ausgeben.
  protected static void computeAllParity(int n) throws IOException {

    count = 0;

    ArrayList<Integer> list = new ArrayList<Integer>();

    for (int i = 1; i <= n; i++) {
      list.add(i);
    }
    // Start Zeit der Berechnung.
    long startTime = System.nanoTime();
    for (Integer i : list) {
      ArrayList<Integer> startList = new ArrayList<>();
      ArrayList<Integer> rest = (ArrayList<Integer>) list.clone();
      rest.remove(rest.indexOf(i));
      startList.add(i);

      computeParity(startList, rest, n);
    }
    // End Zeit der Berechnung.
    long endTime = System.nanoTime();
    // Berechnung der Länge der Berechnung.
    long duration = (endTime - startTime);
    // Ausgabe der Werte.
    System.out.println("Count of Solutions: " + count);
    System.out.println("Time: " + duration + " ns");
  }

  // Berechnet alle Permutationen startend mit einem gegebenen n.
  private static void computeParity(ArrayList<Integer> list, ArrayList<Integer> rest, int n)
      throws IOException {
    // Wenn Rest leer, da keine Zahlen mehr und Ergebnis lang genug
    if (rest.size() == 0 && list.size() <= n) {
      // dann wird das Ergebnis ausgegeben
      System.out.println(list);
      // Erhöhen des Counters für die gefundenen Lösungen.
      count++;
      // Wenn ein Ergebnis gefunden wurde kann hier abgebrochen werden.
      return;
    }
    int zahl = list.get(list.size() - 1);

    // ungerade Zahlen
    for (int i = 1; i < zahl; i += 2) {
      if (rest.contains(i)) {
        ArrayList<Integer> newRest = (ArrayList<Integer>) rest.clone();
        newRest.remove(rest.indexOf(i));
        ArrayList<Integer> newList = (ArrayList<Integer>) list.clone();
        newList.add(i);
        // Rekursiver Aufruf der Funktion
        computeParity(newList, newRest, n);
      }
    }
    // geradeZahlen
    int value;
    if ((zahl % 2) == 0) {
      value = zahl;
    } else {
      value = zahl + 1;
    }
    for (; value <= n; value += 2) {
      if (rest.contains(value)) {
        ArrayList<Integer> newRest = (ArrayList<Integer>) rest.clone();
        newRest.remove(rest.indexOf(value));
        ArrayList<Integer> newList = (ArrayList<Integer>) list.clone();
        newList.add(value);
        // Rekursiver Aufruf der Funktion
        computeParity(newList, newRest, n);
      }
    }
  }
}
