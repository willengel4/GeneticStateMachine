package geneticStateMachine;

import java.util.ArrayList;

public class Menu 
{
	public static void main(String[] args) 
	{
		StateMachine stateMachine = new StateMachine(4, 2, 2, "00010000010100010111010000");
	
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("1");
		instructions.add("0");
		instructions.add("1");
		instructions.add("0");
		instructions.add("0");
		instructions.add("1");
		instructions.add("1");
		
		stateMachine.execute(instructions);
	}
}
