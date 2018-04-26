import java.lang.Comparable;

public class HuffTree implements Comparable
{
  //Private member variable
  private HuffNode root;

  //If two ints are passed in, then it uses leaf node constructor
  public HuffTree(int asciiVal, int frequency)
  {
    root = new HuffLeafNode(asciiVal, frequency);
  }

  //If two HuffTrees are passed in, then it uses internal node constructor
  public HuffTree(HuffTree left, HuffTree right)
  {
    root = new HuffInternalNode(left.getWeight()+right.getWeight(), left.getRoot(), right.getRoot());
  }

  //Getters
  public HuffNode getRoot()
  {
    return root;
  }

  public int getWeight()
  {
    return root.getFrequency();
  }

  //Overrides compareTo method to be used in priority queue
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
