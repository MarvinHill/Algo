import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

public class Main {

  String hoch = "/";
  String runter = "\\";
  String bleib = "-";
  String point = "#";

  public static void main(String[] args) {
    // Tes
    // visualizePath(test);
    visualizeAllPoints(possiblePoints(5000));

    //Test binominal Antwort = 56
    //System.out.println(binom(8,3));
  }

  public static Point[] possiblePoints(int n) {
    ArrayList<Point> points = new ArrayList<>();
    Point[] out;
    BigInteger gesamtAnzahlPfade = BigInteger.ZERO;

    for (int i = 0; i <= n / 2; i++) {
      int j = n - 2*i;
      if(j<=i) points.add(new Point(i,j));
    }

    out = points.toArray(new Point[0]);

    for (Point point : out) {
      System.out.println(point);
      gesamtAnzahlPfade = gesamtAnzahlPfade.add(printPathAmount(point));

    }

    // Copied Code
    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

    symbols.setGroupingSeparator('.');
    formatter.setDecimalFormatSymbols(symbols);
    // End Copied Code

    System.out.println("Gesamtpfadanzahl: "+formatter.format(gesamtAnzahlPfade));
    return out;
  }

  public static void visualizeAllPoints(Point[] points) {
    int xMax = 0;
    int yMax = 0;
    int[][] koordsys;

    for (Point point : points) {
      if (point.i > yMax) yMax = point.i;
      if (point.j > xMax) xMax = point.j;
    }
    koordsys = new int[xMax + 1][yMax + 1];
    //System.out.println(xMax + " " + yMax);

    for (Point point : points) {
      koordsys[point.j][point.i] = 1;
    }

    visualize(koordsys, "All Points:");
  }

  public static void visualizePointPath(Point[] points) {
    int xMax = 0;
    int yMax = 0;
    int[][] koordsys;

    for (Point point : points) {
      if (point.i > yMax) yMax = point.i;
      if (point.j > xMax) xMax = point.j;
    }
    koordsys = new int[xMax + 1][yMax + 1];
    System.out.println(xMax + " " + yMax);

    for (Point point : points) {
      koordsys[point.j][point.i] = 1;
    }

    visualize(koordsys, "Path");
  }

  public static BigInteger printPathAmount(Point p) {
    BigDecimal temp = BigDecimal.valueOf(p.i + 1 - p.j).divide(BigDecimal.valueOf(p.i + 1),4096,RoundingMode.HALF_UP);
    BigDecimal temp2 = new BigDecimal(binom(p.i + p.j, p.i));
    BigDecimal erg = temp.multiply(temp2).setScale(4096,RoundingMode.HALF_EVEN);
    BigInteger out = erg.toBigInteger();

    //double temp = ((double) p.i + 1 - (double) p.j) / ((double) p.i + 1);
    //double temp2 = binom(p.i + p.j, p.i);
    //long erg = (long) (temp * temp2);
    System.out.println("Pfadanzahl:" + erg + " Gesamtanzahl: " + temp);

    return out;
  }

  public static BigInteger binom(int n, int k) {
    BigInteger[][] array = new BigInteger[n + 1][k + 1];
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= Math.min(i, k); j++) {
        if (j == 0 || j == i)
          array[i][j] = BigInteger.ONE;
        else
          array[i][j] = array[i - 1][j - 1].add(array[i - 1][j]);
      }
    }
    return array[n][k];
  }

  public static void visualize(int[][] path, String titel) {

    System.out.println("-------------\n " + titel);
    for (int i = path.length - 1; i >= 0; i--) {
      for (int j = 0; j < path[0].length; j++) {
        if (path[i][j] == 1) {
          System.out.print(" * ");
        } else if (i == j) {
          System.out.print(" # ");
        } else if (path[i][j] == 0) {
          System.out.print(" - ");
        }
      }
      System.out.println("");
    }
    System.out.println("-------------");
  }

  public static void computePaths(Point point) {
    int x = 0;
    int y = 0;
    int i = point.i;
    int j = point.j;

    Stack<Point> points = new Stack<>();
    ArrayList<Point> pfad = new ArrayList<>();

    do {
      if (x == y || y == j) {
        x++;
        pfad.add(new Point(x, y));
      } else if (x == i) {
        y++;
        pfad.add(new Point(x, y));
      } else {
        points.add(new Point(x, y));
        x++;
        pfad.add(new Point(x, y));
      }

      if (x == i && y == j) {
        visualizePointPath(pfad.toArray(new Point[0]));
        if (!points.isEmpty()) {
          Point tempPoint = points.pop();
          x = tempPoint.i;
          y = tempPoint.j;
          // Rest fehltb
        }
      }
    } while (!points.isEmpty());
  }

  public static class Point {
    int i, j;

    Point(int i, int j) {
      this.i = i;
      this.j = j;
    }

    @Override
    public String toString() {
      return "(i:" + Integer.toString(i) + "/j:" + Integer.toString(j) + ")";
    }
  }
}
