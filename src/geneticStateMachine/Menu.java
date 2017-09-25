package geneticStateMachine;

import java.util.ArrayList;

public class Menu 
{
	public static void main(String[] args) 
	{
		StateMachine goalMachine = new StateMachine(2, 2, 2, "000111100");
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		inputs.add("0");
		inputs.add("0");
		inputs.add("1");
		inputs.add("0");
		inputs.add("0");
		inputs.add("1");
		inputs.add("1");
		inputs.add("1");
		ArrayList<String> outputs = goalMachine.execute(inputs);
		
		Population population = new Population(150, 9);
		
		for(int i = 0; i < 300; i++)
		{
			population.createNextGeneration(2, 2, 2, inputs, outputs);
		}
		
		System.out.println(population.getBestGenome().getEncoding());
		System.out.println(population.getBestGenome().getFitness());
	}
}
