import static java.lang.Thread.sleep;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PermParity {
  static final Scanner scanner = new Scanner(System.in);
  static final HashMap<Integer, LinkedList<Integer[]>> speicher = new HashMap<>();

  public static void main(String[] args) {
    // start application main loop
    while (true) {
      // get user input
      System.out.print("Please enter n: ");
      int n = scanner.nextInt();
      System.out.println("new n: " + n);

      measureTime(n);
    }
  }

  private static void measureTime(int n) {
    ArrayList<Long> times = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      long startTime = System.nanoTime();
      computeAllParity(i);
      long endTime = System.nanoTime();
      long duration = (endTime - startTime);
      long durationInMs = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
      times.add(durationInMs);
    }

    double maxTime = 0;
    for (double time : times) {
      if (time > maxTime) maxTime = time;
    }
    double x = 100 / maxTime;

    for (double time : times) {
      int amount = (int) (time * x);
      if (amount == 0) System.out.print("*");
      for (int i = 0; i < amount; i++) {
        System.out.print("*");
      }
      System.out.println(time + "ms");
    }
  }

  private static void computeAllParity(int n) {

    ArrayList<Integer> list = new ArrayList<Integer>();

    for (int i = 1; i <= n; i++) {
      list.add(i);
    }
    for (Integer i : list) {
      ArrayList<Integer> startList = new ArrayList<>();
      ArrayList<Integer> rest = (ArrayList<Integer>) list.clone();
      rest.remove(rest.indexOf(i));
      startList.add(i);

      computeParity(startList, rest, n);
    }
  }

  private static void computeParity(ArrayList<Integer> list, ArrayList<Integer> rest, int n) {
    if (rest.size() == 0 && list.size() <= n) {
      System.out.println(list);
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
