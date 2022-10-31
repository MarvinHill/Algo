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

public class PermParity {
  static final Scanner scanner = new Scanner(System.in); //für die Eingabe von n
  static long count = 0;

  public static void main(String[] args) throws IOException {
    // start application main loop
    while (true) {
      // get user input
      System.out.print("Please enter n: ");
      int n = scanner.nextInt();
      System.out.println("new n: " + n);
      computeAllParity(n);
    }
  }

  private static void computeAllParity(int n) throws IOException {

    count = 0;

    ArrayList<Integer> list = new ArrayList<Integer>();

    for (int i = 1; i <= n; i++) {
      list.add(i);
    }
    long startTime = System.nanoTime();
    for (Integer i : list) {
      ArrayList<Integer> startList = new ArrayList<>();
      ArrayList<Integer> rest = (ArrayList<Integer>) list.clone();
      rest.remove(rest.indexOf(i));
      startList.add(i);

      computeParity(startList, rest, n);
    }
    long endTime = System.nanoTime();
    long duration = (endTime - startTime);
    System.out.println("Count of Solutions: " + count);
    System.out.println("Time: " + duration + " ns");
  }

  private static void computeParity(ArrayList<Integer> list, ArrayList<Integer> rest, int n)
      throws IOException {
    //Wenn Rest leer, da keine Zahlen mehr und Ergebnis lang genug
    if (rest.size() == 0 && list.size() <= n) {
      //dann wird das Ergebnis ausgegeben
      System.out.println(list);
      count++;
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
        computeParity(newList, newRest, n);
      }
    }
  }
}
