package geneticStateMachine;
import java.util.Random;

public class Helper 
{
	public static int log2(int input)
	{
		return (int)(Math.log(input) / Math.log(2));
	}
	
	public static String toBinary(int input, boolean fill, int length)
	{	
		String s = Integer.toBinaryString(input);
	
		if(fill)
			for(int i = s.length(); i < length; i++)
				s = "0" + s;
		
		return s;
	}
	
	public static String genBitCode(int state, int stateLength, int input, int inputLength)
	{
		return toBinary(state, true, stateLength) + toBinary(input, true, inputLength);
	}
	
	public static Random random = new Random();
}
