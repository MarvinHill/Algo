import java.util.ArrayList;
import java.util.Arrays;

public class DLXPentominoNUZ {
  public static void main(String[] args) {
    int n = 2 ;
    int k = 0;
    Figur.generateFigures();
    ArrayList<Figur> figures = Figur.figures;
    ArrayList<DLXNode> headers = new ArrayList<>();

    DLXNode current = DLXNode.h;
    for (int i = 0; i < 5 * n; i++) {
      current.R = new DLXNode();
      current.R.L = current;
      current = current.R;
      headers.add(current);
    }

    int count = 1;
    for (Figur figur : figures){
      System.out.println("Figure: " + count++ + " Teilmengen:");
      int[] bounds = figur.computeBounds();
      for (int y = 0; y < 5 - bounds[1]; y++) {
        for (int x = 0; x < n - bounds[0]; x++) {
          //System.out.println(Arrays.toString(figur.computeBounds()));
          Integer[] values = figur.computeValues(x,y,n);
          // Generate Row in Matrix

          ArrayList<DLXNode> nodes = new ArrayList<>();
          for (int i = 0; i < 5*n; i++) {
            if (values[i] == 1){
              DLXNode node = new DLXNode();
              nodes.add(node);
              DLXNode header = headers.get(i);
              node.D = header.D;
              header.D = node;
              node.D.U = node;
              node.C = header;
            }
          }

          DLXNode currentNode;
          DLXNode nextNode = null;
          for (int i = 0; i < nodes.size()-2; i++) {
            currentNode = nodes.get(i);
            nextNode = nodes.get(i+1);
            currentNode.R = nextNode;
            nextNode.L = currentNode;
          }
          nextNode.R = nodes.get(0);
          nodes.get(0).L = nextNode;


          System.out.println(Arrays.toString(values));
//          int c = 1;
//              for (int p : values) {
//                System.out.print((p == 1 ? "x" : "-") + " ");
//                if (c % n == 0) { System.out.println("");}
//                c++;
//              }
//              System.out.println("");
        }
      }
    }


    // start perfect cover alogrithm
    System.out.println("\nerg: " + DLXNode.cnt + " for n=" + n);
  }
}
