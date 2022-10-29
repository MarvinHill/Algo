import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Marvin Hill (211926)
 * @author Janina Dörlinger (211923)
 */
public class Zpaths {

  public static void main(String[] args) {
    double startTime = System.nanoTime();
    findPathAmount(10000000);
    double endTime = System.nanoTime();
    double duration = (endTime - startTime);
    System.out.println(duration / 1000000.0d / 1000.0d + " Sekunden");
  }

  /**
   * Berechnet alle möglichen Pfade zu allen i, j welche für ein gegebenes n möglich sind
   *
   * @param n
   */
  public static void findPathAmount(int n) {
    /* Array welches mit Punkten befüllt wird welche für das gegebene n gefunden wurden */
    ArrayList<Point> points = new ArrayList<>();
    /*
     * Variable welche die gesamte Anzahl aller Pfade für n beinhaltet also die Summe aller Pfade
     * der einzelnen Punkte welche für n gefunden wurden
     */
    BigInteger gesamtAnzahlPfade = BigInteger.ZERO;

    // Es werden alle möglichen Punkte für n gefunden und der points ArrayList hinzugefügt
    for (int i = 0; i <= n / 2; i++) {
      int j = n - 2 * i;
      if (j <= i) points.add(new Point(i, j));
    }

    // Für jeden Punkt im point ArrayList wird die Anzahl der Pfade bestimmt
    for (Zpaths.Point point : points.toArray(new Zpaths.Point[0])) {
      gesamtAnzahlPfade = gesamtAnzahlPfade.add(getPathAmount(point));
    }

    // Das Ergebnis wird für die leichtere Lesbarkeit formatiert
    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    symbols.setGroupingSeparator('.');
    formatter.setDecimalFormatSymbols(symbols);

    // Das Ergebnis wird ausgegeben
    System.out.println("Gesamtpfadanzahl n=" + n +" : " + formatter.format(gesamtAnzahlPfade));
  }

  /**
   * Methode welche die Anzahl der Pfade ermittelt von (0,0) zu (i,j) eines Punktes
   * @param p Punkt Objekt zu welchen die Pfade berechnet werden sollen
   * @return Returns a BigInteger Number of Paths that lead to the point
   */
  public static BigInteger getPathAmount(Zpaths.Point p) {
    // Erstes Teilergebnis
    BigDecimal temp;
    // Zweites Teilergebnis
    BigDecimal temp2;
    // Ergebnis welches ausgegeben wird
    BigDecimal erg;

    // i des Zielpunkts
    BigDecimal pi = new BigDecimal(p.i);
    // j des Zielpunkts
    BigDecimal pj = new BigDecimal(p.j);

    temp =
        pi.add(BigDecimal.ONE)
            .subtract(pj)
            .divide(pi.add(BigDecimal.ONE), 4096, RoundingMode.HALF_UP);
    temp2 = new BigDecimal(binom(p.i + p.j, p.i));

    erg = temp.multiply(temp2).setScale(4096, RoundingMode.HALF_EVEN);

    return erg.toBigInteger();
  }


  /**
   * Berechnung des Binomialkoeffizienten
   */
  public static BigInteger binom(int n, int k) throws IllegalArgumentException {

    if (n < 0 || k < 0) {
      throw new IllegalArgumentException("Cannot calculate combination" + " for negative numbers.");
    }
    if (n == 0) {
      return BigInteger.ZERO;
    }

    BigInteger r = BigInteger.ONE;
    int nMinusK = n - k;
    for (int i = 1; i <= k; i++) {
      r = r.multiply(BigInteger.valueOf(nMinusK + i)).divide(BigInteger.valueOf(i));
    }
    return r;
  }

  /**
   * Punkt klasse welche über eine i und j Koordinate verfügt
   */
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
