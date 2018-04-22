public class HuffTree
{
  public Node root;

  public HuffTree()
  {
    root = new Node(0,0);
  }

  public HuffTree(int character, int frequency)
  {
    root = new Node(character, frequency);
  }

  public String toString()
  {
    return root.toString();
  }
}
