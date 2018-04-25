import java.io.*;
import java.util.*;

public class Project3
{
  public static String FILE = "test.txt";

  public static void main(String[] args) throws FileNotFoundException
  {
    Huffman huff = new Huffman();
    HuffTree ht;
    String code;

    try
    {
      File file = new File(FILE);
      ht = huff.buildTree(file);
      //System.out.println(huff.getFrequencies(file));
      //System.out.println(huff.traverseHuffmanTree(ht));
      code = huff.encodeFile(file, ht);
      System.out.println(huff.decodeFile(code, ht));
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
