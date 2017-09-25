package geneticStateMachine;

import java.util.ArrayList;
import java.util.HashMap;

public class StateMachine 
{
	/* Stores the number of states, inputs, and outputs that the machine should have */
	private int numStates;
	private int numInputs;
	private int numOutputs;

	/* Stores all of the states, accessible by bit code */
	private HashMap<String, State> states;
	
	/* Stores the state that the machine is currently in */
	private State currentState;
	
	/* Constructs the machine. numStates, numInputs, 
	 * and numOutputs must be 2^n (1, 2, 4, 8, ...).
	 * Encoding should be in a very concise format */
	public StateMachine(int numStates, int numInputs, int numOutputs, String encoding)
	{
		this.numStates = numStates;
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		buildMachineFromEncoding(encoding);
	}
	
	/* Executes a list of instructions (state transfers) */
	public void execute(ArrayList<String> instructions)
	{
		for(String i : instructions)
		{
			State nextState = currentState.getNextState(i);
			String output = currentState.getOutput(i);
			System.out.println("Next State: " + nextState.getEncoding() + " Output: " + output);
			currentState = nextState;
		}
	}
	
	/* Builds a state machine given the encoding string */
	public void buildMachineFromEncoding(String encoding)
	{
		/* Initialize the states hash map */
		states = new HashMap<String, State>();
		
		/* Stores the initial state, next states, and outputs from the encoding string */
		String initialState = "";
		ArrayList<String> nextStates = new ArrayList<String>();
		ArrayList<String> outputs = new ArrayList<String>();
		
		/* Gets the initial state from the encoding string */
		for(int i = 0; i < Helper.log2(numStates); i++)
			initialState = initialState + encoding.charAt(i);
		
		/* Gets the next states and outputs from the encoding string */
		String token = "";
		for(int i = Helper.log2(numStates), f = 1; i < encoding.length(); i++, f++)
		{
			token = token + encoding.charAt(i);

			if(f == Helper.log2(numStates))
			{
				nextStates.add(token);
				token = "";
			}
			
			if(f == Helper.log2(numStates) + Helper.log2(numOutputs))
			{
				outputs.add(token);
				token = "";
				f = 0;
			}
		}
		
		/* Add states. Each state will have a bit code equal to 
		 * the binary representation of i */
		for(int i = 0; i < numStates; i++)
		{
			String bitCode = Helper.toBinary(i, true, Helper.log2(numStates));
			states.put(bitCode, new State(bitCode));
		}
		
		/* Initializes the current state of the machine */
		currentState = states.get(initialState);
		
		/* Process the nextStates and outputs generated from the encoding
		 * Essentially, it reads the next stateBitCode, inputBitCode, 
		 * nextStateBitCode, and outputBitCode, and performs the mappings */
		int instructionIndex = 0;
		for(int i = 0; i < numStates; i++)
		{
			for(int f = 0; f < numInputs; f++)
			{
				/* Gets codes (essentially this is a row in the state table) */
				String stateBitCode = Helper.toBinary(i, true, Helper.log2(numStates));
				String inputBitCode = Helper.toBinary(f, true, Helper.log2(numInputs));
				String nextStateBitCode = nextStates.get(instructionIndex);
				String outputBitCode = outputs.get(instructionIndex);
				
				System.out.println(stateBitCode + " " + inputBitCode + " " + nextStateBitCode + " " + outputBitCode);
				
				/* Perform the mappings */
				states.get(stateBitCode).setNextState(inputBitCode, states.get(nextStateBitCode));
				states.get(stateBitCode).setOutput(inputBitCode, outputBitCode);
				
				/* Go to the next line in the "state table" */
				instructionIndex++;
			}
		}
	}
}
