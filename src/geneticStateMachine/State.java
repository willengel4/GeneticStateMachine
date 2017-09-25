package geneticStateMachine;

import java.util.HashMap;

public class State 
{
	/* Represents input to next state mappings */
	private HashMap<String, State> nextStateGiveInput;
	
	/* Represents input to output mappings */
	private HashMap<String, String> outputGiveInput;
	
	/* Represents the encoding of the state */
	private String encoding;
	
	/* Construct a State, gives it's encoding */
	public State(String encoding)
	{
		this.encoding = encoding;
		nextStateGiveInput = new HashMap<String, State>();
		outputGiveInput = new HashMap<String, String>();
	}
	
	/* Sets the nextState associated with an input */
	public void setNextState(String input, State nextState)
	{
		nextStateGiveInput.put(input, nextState);
	}
	
	/* Sets the output associated with an input */
	public void setOutput(String input, String output)
	{
		outputGiveInput.put(input, output);
	}
	
	/* Gets the next state given an input */
	public State getNextState(String input)
	{
		return nextStateGiveInput.get(input);
	}
	
	/* Gets the output given an input */
	public String getOutput(String input)
	{
		return outputGiveInput.get(input);
	}
	
	/* Returns the encoding */
	public String getEncoding()
	{
		return encoding;
	}
}
