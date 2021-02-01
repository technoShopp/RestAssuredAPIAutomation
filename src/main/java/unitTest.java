import java.util.HashMap;

public class unitTest {
	public static void main(String[] a)
	{
	HashMap<Integer, String> m = new HashMap<Integer, String>();
	/*m.put(1,"Malini");
	m.put(2,"Nalini");
	m.put(3,"ABC");
	m.put(4,"wer");*/
	for(int i=0;i<4;i++)
	{
		m.put(i+1,"ABC"+i);
	}
	System.out.println(m.size());
	
for(int i=0;i<m.size();i++)
{
	System.out.println(m.get(i+1));
}
}
}