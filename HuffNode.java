public interface HuffNode
{
  //Declaring methods
  public boolean isLeaf();
  public int getFrequency();
  public HuffNode getLeft();
  public HuffNode getRight();
  public String getCode();
  public void setCode(String code);
}

class HuffLeafNode implements HuffNode
{
  private int frequency;
  private int asciiVal;
  private String code;

  public HuffLeafNode(int asciiVal, int frequency)
  {
    this.asciiVal = asciiVal;
    this.frequency = frequency;
    this.code = "";
  }

  public String toString()
  {
    String temp = (char)asciiVal + " " + frequency + "\n";
    return temp;
  }

  public boolean isLeaf()
  {
    return true;
  }

  public int getFrequency()
  {
    return frequency;
  }

  public int getAscii()
  {
    return asciiVal;
  }

  public char getCharacter()
  {
    return (char)asciiVal;
  }

  public HuffNode getLeft()
  {
    return null;
  }

  public HuffNode getRight()
  {
    return null;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }
}

class HuffInternalNode implements HuffNode
{
  private int frequency;
  private HuffNode left;
  private HuffNode right;
  private String code;

  public HuffInternalNode(int frequency, HuffNode left, HuffNode right)
  {
    this.frequency = frequency;
    this.left = left;
    this.right = right;
    this.code = "";
  }

  public String toString()
  {
    return "" + frequency;
  }

  public boolean isLeaf()
  {
    return false;
  }

  public int getFrequency()
  {
    return frequency;
  }

  public HuffNode getLeft()
  {
    left.setCode(this.getCode() + "0");
    return left;
  }

  public HuffNode getRight()
  {
    right.setCode(this.getCode() + "1");
    return right;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }
}
