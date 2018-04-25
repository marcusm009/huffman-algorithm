import java.io.*;
import java.util.*;

public class Huffman implements HuffmanCoding
{
  //take a file as input and create a table with characters and frequencies
  //print the characters and frequencies
  public String getFrequencies(File inputFile) throws FileNotFoundException
  {
    //Creates a hash table to store frequencies of characters based on ASCII
    Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
    //Queue<Character> order = new LinkedList<Character>();
    String output = "";

    try
    {
      Scanner scan = new Scanner(inputFile);
      //Changes delimiter to "" to read in characters
      scan.useDelimiter("");

      //Initializes frequency of each char to zero or increments it
      while(scan.hasNext())
      {
        int asciiVal = (int)scan.next().charAt(0);
        int frequency;
        if(table.get(asciiVal) == null)
        {
          //order.add(character);
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
      System.out.println("error1");
    }

    for(int i = 0; i < 128; i++)
    {
      if(table.get(i) == null || (char)i == '\n')
      {
        continue;
      }
      output += (char)i + " " + table.get(i) + "\n";
    }

    return output;
  }

  //take a file as input and create a Huffman Tree
  public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception
  {
    //Initializes the hash table, the huff tree comparator, and the priorityQ
    Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
    //Comparator<HuffTree> comparator = new HuffTreeComparator();
    PriorityQueue<HuffTree> htQueue = new PriorityQueue<HuffTree>();

    //Same hashing function as used in getFrequencies
    try
    {
      Scanner scan = new Scanner(inputFile);
      scan.useDelimiter("");

      while(scan.hasNext())
      {
        int asciiVal = (int)scan.next().charAt(0);
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

      //Loops through the hash table, creates single node huffTrees for non-null
      // ASCII characters, and adds them to the priority queue
      for(int i = 0; i < 128; i++)
      {
        if(table.get(i) == null || (char)i == '\n')
        {
          continue;
        }
        htQueue.add(new HuffTree(i,table.get(i)));
      }

      //While the queue has a size larger than 1, pop the first two trees and
      // create a new tree that combines the two
      while(htQueue.size() > 1)
      {
        HuffTree temp1 = htQueue.poll();
        HuffTree temp2 = htQueue.poll();
        htQueue.add(new HuffTree(temp1,temp2));
      }

    }
    catch(FileNotFoundException ex)
    {
      System.out.println("error2");
    }
    return htQueue.poll();

  }

  //take a file and a HuffTree and encode the file.
  //output a string of 1's and 0's representing the file
  public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException
  {
    //TRAVERSE TREE
    Stack<HuffNode> stack = new Stack<HuffNode>();
    HashMap<Integer, String> table = new HashMap<Integer, String>();
    HuffNode curNode = huffTree.getRoot();

    //Iterates down the left subtree to get initial stack
    while(curNode != null)
    {
      stack.push(curNode);
      curNode = curNode.getLeft();
    }

    //While the stack is greater than zero, it pops the last element off the
    //stack, adds it to the sorted list, then traverses down its right subtree
    while(stack.size() > 0)
    {
      curNode = stack.pop();
      if(curNode.isLeaf())
      {
        table.put(((HuffLeafNode)curNode).getAscii(),curNode.getCode());
      }
      if(curNode.getRight() != null)
      {
        curNode = curNode.getRight();

        while(curNode != null)
        {
          stack.push(curNode);
          curNode = curNode.getLeft();
        }
      }
    }

    //SCANNING FILE
    StringBuilder output = new StringBuilder();
    int count = 0;

    try
    {
      Scanner scan = new Scanner(inputFile);
      //Changes delimiter to "" to read in characters
      scan.useDelimiter("");

      //Initializes frequency of each char to zero or increments it
      while(scan.hasNext())
      {
        System.out.println(++count);
        String temp = table.get((int)scan.next().charAt(0));
        if(temp == null)
        {
          continue;
        }
        output.append(temp);
      }
      scan.close();
    }
    catch(FileNotFoundException ex)
    {
      System.out.println("error3");
    }

    return output.toString();
  }

  //take a String and HuffTree and output the decoded words
  public String decodeFile(String code, HuffTree huffTree) throws Exception
  {
    StringBuilder output = new StringBuilder();
    HuffNode curNode = huffTree.getRoot();

    for(char e: code.toCharArray())
    {
      if(e == '0')
      {
        curNode = curNode.getLeft();
      }
      else if(e == '1')
      {
        curNode = curNode.getRight();
      }
      else
      {
        return "error4";
      }

      if(curNode.isLeaf())
      {
        output.append(((HuffLeafNode)curNode).getCharacter());
        curNode = huffTree.getRoot();
      }
    }

    return output.toString();
  }

  //print the characters and their codes
  public String traverseHuffmanTree(HuffTree huffTree) throws Exception
  {
    Stack<HuffNode> stack = new Stack<HuffNode>();
    Hashtable<Integer, String> table = new Hashtable<Integer, String>();
    HuffNode curNode = huffTree.getRoot();
    String inOrder = "";
    String code = "";

    //Iterates down the left subtree to get initial stack
    while(curNode != null)
    {
      stack.push(curNode);
      curNode = curNode.getLeft();
    }

    //While the stack is greater than zero, it pops the last element off the
    //stack, adds it to the sorted list, then traverses down its right subtree
    while(stack.size() > 0)
    {
      curNode = stack.pop();
      if(curNode.isLeaf())
      {
        table.put(((HuffLeafNode)curNode).getAscii(),curNode.getCode());
      }
      if(curNode.getRight() != null)
      {
        curNode = curNode.getRight();

        while(curNode != null)
        {
          stack.push(curNode);
          curNode = curNode.getLeft();
        }
      }
    }

    //Loops through the hash table, creates single node huffTrees for non-null
    // ASCII characters, and adds them to the priority queue
    for(int i = 0; i < 128; i++)
    {
      if(table.get(i) == null || (char)i == '\n')
      {
        continue;
      }
      inOrder += (char)i + " " + table.get(i) + "\n";
    }

    return inOrder;
  }
}
