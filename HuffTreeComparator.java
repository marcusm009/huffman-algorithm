import java.util.Comparator;

public class HuffTreeComparator implements Comparator<HuffTree>
{
  @Override
  public int compare(HuffTree x, HuffTree y)
  {
    if(x.root.frequency < y.root.frequency)
    {
      return 1;
    }
    if(x.root.frequency > y.root.frequency)
    {
      return -1;
    }
    return 0;
  }
}
