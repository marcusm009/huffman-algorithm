// Marcus Mills
// COP3530
// Description: A class that uses a Huffman Tree to encode and decode files.
import java.io.*;
import java.util.*;

public class HuffmanEncoder implements HuffmanCoding
{
  //take a file as input and create a table with characters and frequencies
  //print the characters and frequencies
  public String getFrequencies(File inputFile) throws FileNotFoundException
  {
    //Creates a hash table to store frequencies of characters based on ASCII
    HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
    //Uses StringBuilder to quickly append new strings
    StringBuilder output = new StringBuilder();

    try
    {
      Scanner scan = new Scanner(inputFile);
      //Changes delimiter to "" to read in characters
      scan.useDelimiter("");

      while(scan.hasNext())
      {
        //Initializes asciiVal and declares frequency
        int asciiVal = (int)scan.next().charAt(0);
        int frequency;
        //If the asciiVal isn't in the table, it means the frequency is zero
        if(table.get(asciiVal) == null)
        {
          frequency = 0;
        }
        //Otherwise, grab the frequency from the table
        else
        {
          frequency = table.get(asciiVal);
        }
        //Increment frequency and put the new value in the tbale
        table.put(asciiVal, ++frequency);
      }
      scan.close();
    }
    catch(FileNotFoundException ex)
    {
      System.out.println("error1");
    }

    //Loops through all ASCII characters
    for(int i = 0; i < 128; i++)
    {
      //If the key doesn't map to anything or it maps to the newline character
      // it skips the iteration
      if(table.get(i) == null || (char)i == '\n')
      {
        continue;
      }
      //Appends character and frequency to the output string
      output.append((char)i + " " + table.get(i) + "\n");
    }
    return output.toString();
  }

  //take a file as input and create a Huffman Tree
  public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception
  {
    //Initializes the HashMap and PriorityQueue
    HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
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
    //Initializes the Stack, HashMap, StringBuilder, and curNode to root
    Stack<HuffNode> stack = new Stack<HuffNode>();
    HashMap<Integer, String> table = new HashMap<Integer, String>();
    StringBuilder output = new StringBuilder();
    HuffNode curNode = huffTree.getRoot();

    //Traverses the Huffman Tree and creates a HashMap to reference each
    // character

    //Iterates down the left subtree to get initial stack
    while(curNode != null)
    {
      stack.push(curNode);
      curNode = curNode.getLeft();
    }

    //While the stack is greater than zero, it pops the last element off the
    // stack, adds it to the hash table, then traverses down its right subtree
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

    try
    {
      Scanner scan = new Scanner(inputFile);
      //Changes delimiter to "" to read in characters
      scan.useDelimiter("");

      while(scan.hasNext())
      {
        //Gets the unique binary code associated with the current character
        String temp = table.get((int)scan.next().charAt(0));
        //Checks for null
        if(temp == null)
        {
          continue;
        }
        //Appends to output
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
    //Initializes StringBuilder and curNode to root
    StringBuilder output = new StringBuilder();
    HuffNode curNode = huffTree.getRoot();

    //For each character in the String
    for(char e: code.toCharArray())
    {
      //If it's 0, traverse left
      if(e == '0')
      {
        curNode = curNode.getLeft();
      }
      //If it's 1, traverse right
      else if(e == '1')
      {
        curNode = curNode.getRight();
      }
      //Otherwise, it's an error
      else
      {
        return "error4";
      }

      //If the node is a leaf, it appends the character to the output and
      // resets curNode to root
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
    StringBuilder output = new StringBuilder();

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
      output.append((char)i + " " + table.get(i) + "\n");
    }
    return output.toString();
  }
}
