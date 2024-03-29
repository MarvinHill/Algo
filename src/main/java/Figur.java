import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Marvin Hill (211926)
 * @author Janina Dörlinger (211923)
 */

class Figur {
  public final static ArrayList<Figur> figures = new ArrayList<>();
  private final int[][] points;

  Figur(int[][] points) {
    this.points = points;
    figures.add(this);
  }

  public int[] computeBounds() {
    int sizeX = 0;
    int sizeY = 0;

    for (int[] p : points) {
      if (p[0] > sizeX) {
        sizeX = p[0];
      }
      if (p[1] > sizeY) {
        sizeY = p[1];
      }
    }
    return new int[]{sizeX, sizeY};
  }

  public Integer[] computeValues(int x, int y, int n) {
    ArrayList<Integer> erg = new ArrayList<>();
    for (int i = 0; i < (n) * 5; i++) {
      erg.add(0);
    }
    n--;
    for (int[] point : points) {
      erg.set((point[1] + y)*(n+1)+(point[0] + x),1);
    }
    return erg.toArray(Integer[]::new);
  }

  public static void generateFigures() {
    // U`s
    new Figur(new int[][] {{0, 0}, {1, 0}, {0, 1}, {0, 2}, {1, 2}});
    new Figur(new int[][] {{0, 0}, {1, 0}, {1, 1}, {0, 2}, {1, 2}});
    new Figur(new int[][] {{0, 0}, {1, 0}, {2, 0}, {0, 1}, {2, 1}});
    new Figur(new int[][] {{0, 0}, {2, 0}, {0, 1}, {1, 1}, {2, 1}});
    // Z`s
    new Figur(new int[][] {{2, 0}, {0, 1}, {1, 1}, {2, 1}, {0, 2}});
    new Figur(new int[][] {{0, 0}, {0, 1}, {1, 1}, {2, 1}, {2, 2}});
    new Figur(new int[][] {{0, 0}, {1, 0}, {1, 1}, {1, 2}, {2, 2}});
    new Figur(new int[][] {{1, 0}, {2, 0}, {1, 1}, {0, 2}, {1, 2}});
    //N`s
    new Figur(new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}, {1, 3}});
    new Figur(new int[][] {{2, 0}, {3, 0}, {0, 1}, {1, 1}, {2, 1}});
    new Figur(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {1, 3}});
    new Figur(new int[][] {{1, 0}, {2, 0}, {3, 0}, {0, 1}, {1, 1}});
    new Figur(new int[][] {{1, 0}, {0, 1}, {1, 1}, {0, 2}, {0, 3}});
    new Figur(new int[][] {{0, 0}, {1, 0}, {2, 0}, {2, 1}, {3, 1}});
    new Figur(new int[][] {{1, 0}, {1, 1}, {0, 2}, {1, 2}, {0, 3}});
    new Figur(new int[][] {{0, 0}, {1, 0}, {1, 1}, {2, 1}, {3, 1}});
  }

  @Override
  public String toString() {
    return "Figur{" +
        "points=" + Arrays.toString(points) +
        '}';
  }
}