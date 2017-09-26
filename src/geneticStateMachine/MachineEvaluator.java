package geneticStateMachine;

import java.util.ArrayList;

public class MachineEvaluator extends Evaluator
{
	private int numStates;
	private int numInputs;
	private int numOutputs;
	private ArrayList<String> inputs;
	private ArrayList<String> outputs;
	
	public MachineEvaluator(int numStates, int numInputs, int numOutputs, ArrayList<String> inputs, ArrayList<String> outputs)
	{
		this.numStates = numStates;
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public double evaluate(String encoding)
	{
		StateMachine stateMachine = new StateMachine(numStates, numInputs, numOutputs, encoding);		
		ArrayList<String> machineOutputs = stateMachine.execute(inputs);
		double fitness = 0.0;

		for(int i = 0; i < machineOutputs.size(); i++)
			if(machineOutputs.get(i).equals(outputs.get(i)))
				fitness += 1.0;
		
		return fitness;
	}
}
