import java.io.*;
import java.util.*;

public class Project3
{
  public static String FILE = "illiad.txt";

  public static void main(String[] args) throws FileNotFoundException
  {
    HuffmanAlgorithm ha = new HuffmanAlgorithm();

    try
    {
      File file = new File(FILE);
      ha.buildTree(file);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
