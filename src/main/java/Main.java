import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

  public static void main(String[] args) {
    double startTime = System.nanoTime();
    possiblePoints(100);
    double endTime = System.nanoTime();
    double duration = (endTime - startTime);
    System.out.println(duration / 1000000.0d / 1000.0d + " Sekunden");
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
      //System.out.println(point);
      gesamtAnzahlPfade = gesamtAnzahlPfade.add(getPathAmount(point));
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


  public static BigInteger getPathAmount(Point p) {

    BigDecimal temp;
    BigDecimal temp2;
    BigDecimal erg;
    BigInteger out;

    BigDecimal pi =  new BigDecimal(p.i);
    BigDecimal pj = new BigDecimal(p.j);

    temp = pi.add(BigDecimal.ONE).subtract(pj).divide(pi.add(BigDecimal.ONE),4096,RoundingMode.HALF_UP);
    temp2 = new BigDecimal(binom1(p.i + p.j, p.i));
    //temp = BigDecimal.valueOf(p.i + 1 - p.j).divide(BigDecimal.valueOf(p.i + 1),4096,RoundingMode.HALF_UP);


    erg = temp.multiply(temp2).setScale(4096,RoundingMode.HALF_EVEN);
    out = erg.toBigInteger();

    //System.out.println("Pfadanzahl:" + erg.setScale(1, RoundingMode.HALF_EVEN) + " Prozent: " + temp.setScale(5, RoundingMode.HALF_EVEN));

    return out;
  }

  //
  public static BigInteger binom1(int n, int k)
      throws IllegalArgumentException {

    if (n < 0 || k < 0) {
      throw new IllegalArgumentException(
          "Cannot calculate combination"
              + " for negative numbers.");
    }
    if (n == 0) {
      return BigInteger.ZERO;
    }

    BigInteger r = BigInteger.ONE;
    int nMinusK = n - k;
    for (int i = 1; i <= k; i++) {
      r = r.multiply(BigInteger.valueOf(nMinusK + i)).divide(
          BigInteger.valueOf(i));
    }
    return r;
  }

  public static BigInteger binom2(int N, int K) {
    BigInteger ret = BigInteger.ONE;
    for (int k = 0; k < K; k++) {
      ret = ret.multiply(BigInteger.valueOf(N-k))
          .divide(BigInteger.valueOf(k+1));
    }
    return ret;
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
