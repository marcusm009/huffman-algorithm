public class Node
{
  public int frequency;
  public int character;
  public Node left;
  public Node right;
  public Node parent;

  public Node(int character, int frequency)
  {
    this.character = character;
    this.frequency = frequency;
  }

  public String toString()
  {
    String temp = "char: " + (char)character + " freq: " + frequency;
    return temp;
  }
}
