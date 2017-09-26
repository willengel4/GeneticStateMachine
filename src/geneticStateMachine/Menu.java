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
		
		MachineEvaluator evaluator = new MachineEvaluator(2, 2, 2, inputs, outputs);
		
		Population currentPopulation = new Population(25, evaluator);
		currentPopulation.randomizePopulation(9);
		Genome bestGenome = null;
		
		for(int i = 0; i < 20; i++)
		{
			/* Evaluates current generation and creates the next generation
			 * Stores the next generation in nextGeneration */
			Population nextGeneration = currentPopulation.createNextGeneration();
			
			/* If the current population's best genome performs better than
			 * the current best genome, replace best genome with the current
			 * population's best genome */
			if(bestGenome == null || currentPopulation.getPopulationBest().getFitness() > bestGenome.getFitness())
				bestGenome = currentPopulation.getPopulationBest();
			
			currentPopulation = nextGeneration;
		}
		
		System.out.println(bestGenome.getEncoding());
		System.out.println(bestGenome.getFitness());
	}
}
