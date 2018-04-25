import java.lang.Comparable;

public class HuffTree implements Comparable
{
  private HuffNode root;

  public HuffTree(int asciiVal, int frequency)
  {
    root = new HuffLeafNode(asciiVal, frequency);
  }

  public HuffTree(HuffTree left, HuffTree right)
  {
    root = new HuffInternalNode(left.getWeight()+right.getWeight(), left.getRoot(), right.getRoot());
  }

  public HuffNode getRoot()
  {
    return root;
  }

  public int getWeight()
  {
    return root.getFrequency();
  }

  public String toString()
  {
    return root.toString();
  }

  @Override
  public int compareTo(Object obj)
  {
    HuffTree ht = (HuffTree) obj;
    if(root.getFrequency() > ht.getRoot().getFrequency())
    {
      return 1;
    }
    if(root.getFrequency() < ht.getRoot().getFrequency())
    {
      return -1;
    }
    return 0;
  }

}
