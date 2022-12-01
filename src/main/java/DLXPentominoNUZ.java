import java.util.ArrayList;
import java.util.Arrays;

public class DLXPentominoNUZ {
  private final static ArrayList<DLXNode> headers = new ArrayList<>();
  private final static ArrayList<Integer[]> valueList = new ArrayList<>();
  public static boolean debug = false;

  public static void main(String[] args) {
    int n = 20 ;
    Figur.generateFigures();
    ArrayList<Figur> figures = Figur.figures;


    // Werte berechnen
    int count = 1;
    for (Figur figur : figures){
      if(DLXPentominoNUZ.debug)System.out.println("Figure: " + count++ + " Teilmengen:");
      int[] bounds = figur.computeBounds();
      for (int y = 0; y < 5 - bounds[1]; y++) {
        for (int x = 0; x < n - bounds[0]; x++) {
          //System.out.println(Arrays.toString(figur.computeBounds()));
          Integer[] values = figur.computeValues(x,y,n);
          if(DLXPentominoNUZ.debug)System.out.println(Arrays.toString(values));
          valueList.add(values);
        }
      }
    }

    //Matrix generieren
    generateMatrix(n);
    int k = n;
    DLXNode.search(k);

    // start perfect cover alogrithm
    System.out.println("\nerg: " + DLXNode.cnt + " for n=" + n);
  }
  public static void generateMatrix(int n){

    DLXNode current = DLXNode.h;
    for (int i = 0; i < 5 * n; i++) {
      DLXNode newNode = new DLXNode();
      newNode.R = current.R;
      newNode.R.L = newNode;
      current.R = newNode;
      newNode.L = current;
      current = newNode;
      current.name = "h" + current.name;
      headers.add(current);
    }


    //Reihe
    ArrayList<DLXNode> row = new ArrayList<>();
    //letzte Elemente spalten
    ArrayList<DLXNode> collums = (ArrayList<DLXNode>) headers.clone();

    for (Integer[] values: valueList) {

      for (int i = 0; i < values.length; i++) {

        if (values[i] == 1){
          DLXNode node = new DLXNode();
          node.name = node.name + "r" + (i+1);
          DLXNode previouseNode = collums.get(i);

          node.D = previouseNode.D;
          previouseNode.D = node;
          node.D.U = node;
          node.U = previouseNode;

          collums.set(i, node);
          node.C = headers.get(i);
          row.add(node);
        }


      }

      DLXNode currentNode = row.get(0);
      for (int j = 0; j < row.size(); j++) {
        DLXNode newNode = row.get(j);
        newNode.R = currentNode.R;
        newNode.R.L = newNode;
        currentNode.R = newNode;
        newNode.L = currentNode ;
        currentNode  = newNode;
      }
      currentNode.R = row.get(0);
      row.get(0).L = currentNode;
      row.clear();

    }
    if(DLXPentominoNUZ.debug) System.out.println(DLXNode.getAllNodes());

  }
}



