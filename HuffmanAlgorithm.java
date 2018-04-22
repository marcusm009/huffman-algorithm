import java.io.*;
import java.util.*;

public class HuffmanAlgorithm implements HuffmanCoding
{

  //take a file as input and create a table with characters and frequencies
  //print the characters and frequencies
  public String getFrequencies(File inputFile) throws FileNotFoundException
  {
    Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();

    try
    {
      Scanner scan = new Scanner(inputFile);
      scan.useDelimiter("");

      while(scan.hasNext())
      {
        int asciiVal = (int) scan.next().charAt(0);
        int frequency;

        if(table.get(asciiVal) == null)
        {
          frequency = 0;
        }
        else
        {
          frequency = table.get(asciiVal);
        }

        table.put(asciiVal, ++frequency);
      }
      scan.close();
    }
    catch(FileNotFoundException ex)
    {
      System.out.println("error");
    }

    System.out.println(table.toString());

    return table.toString();
  }

  //take a file as input and create a Huffman Tree
  public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception
  {
    Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
    Comparator<HuffTree> comparator = new HuffTreeComparator();
    PriorityQueue<HuffTree> htQueue = new PriorityQueue<HuffTree>(128, comparator);

    try
    {
      Scanner scan = new Scanner(inputFile);
      scan.useDelimiter("");

      while(scan.hasNext())
      {
        int asciiVal = (int) scan.next().charAt(0);
        int frequency;

        if(table.get(asciiVal) == null)
        {
          frequency = 0;
        }
        else
        {
          frequency = table.get(asciiVal);
        }

        table.put(asciiVal, ++frequency);
      }
      scan.close();

      for(int i = 0; i < 128; i++)
      {
        if(table.get(i) == null)
        {
          continue;
        }
        htQueue.add(new HuffTree(i,table.get(i)));
      }

      while(htQueue.size() != 0)
      {
        System.out.println(htQueue.poll());
      }

/*
      while(!htQueue.isEmpty())
      {
         char curChar = buildQueue.pop();
         HuffTree curTree = new HuffTree(curChar, table.get(curChar));
         htQueue.add(curTree);
      }
      */
    }
    catch(FileNotFoundException ex)
    {
      System.out.println("error");
    }
    return new HuffTree();

  }

  //take a file and a HuffTree and encode the file.
  //output a string of 1's and 0's representing the file
  public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException
  {
    return "";
  }

  //take a String and HuffTree and output the decoded words
  public String decodeFile(String code, HuffTree huffTree) throws Exception
  {
    return "";
  }

  //print the characters and their codes
  public String traverseHuffmanTree(HuffTree huffTree) throws Exception
  {
    return "";
  }
}
