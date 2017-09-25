package geneticStateMachine;

import java.util.ArrayList;

public class Population 
{
	/* A list of genomes that make up the population */
	private ArrayList<Genome> genomes;
	private int generation;
	private int m;
	private Genome bestGenome;
	
	public Population(int numGenomes, int bitsPerGenome)
	{
		/* Initialize the genomes list */
		genomes = new ArrayList<Genome>();
		generation = 0;
		m = numGenomes;
		
		/* Creates the specified number of genomes, each with it's own
		 * random configuration */
		for(int i = 0; i < numGenomes; i++)
		{
			StringBuilder machineBuilder = new StringBuilder();
			for(int f = 0; f < bitsPerGenome; f++)
				machineBuilder.append(Helper.random.nextInt(2));
			genomes.add(new Genome(machineBuilder.toString()));
		}
	}
	
	public void printPopulation()
	{
		for(int i = 0; i < genomes.size(); i++)
		{
			System.out.print(genomes.get(i).getEncoding() + " ");
		}
		
		System.out.println();
	}
	
	public void evaluatePopulation(int numInputs, int numOutputs, int numStates, ArrayList<String> inputs, ArrayList<String> outputs)
	{
		for(Genome g : genomes)
		{
			double fitness = 0.0;
			StateMachine machine = new StateMachine(numStates, numInputs, numOutputs, g.getEncoding());
			
			ArrayList<String> machineOutputs = machine.execute(inputs);
			for(int i = 0; i < machineOutputs.size(); i++)
				if(machineOutputs.get(i).equals(outputs.get(i)))
					fitness++;
			
			g.setFitness(fitness);
			if(bestGenome == null || fitness > bestGenome.getFitness())
				bestGenome = g;
		}
	}
	
	public void createNextGeneration(int numInputs, int numOutputs, int numStates, ArrayList<String> inputs, ArrayList<String> outputs)
	{
		evaluatePopulation(numStates, numInputs, numOutputs, inputs, outputs);

		int pCopy = 5;
		int pCrossover = 5;
		int pMutate = 1;
		
		ArrayList<Genome> nextGeneration = new ArrayList<Genome>();
		
		for(int i = 0; i < m; i++)
		{
			int lotto = Helper.random.nextInt(pCopy + pCrossover + pMutate);
			
			if(lotto < pCopy)
			{
				nextGeneration.add(selectGenome().copy());
			}
			else if(lotto < pCopy + pCrossover)
			{
				Genome g1 = selectGenome();
				Genome g2 = selectGenome();
				int splitPoint = Helper.random.nextInt(g1.getEncoding().length() - 1) + 1;
				
				String fragment1 = g1.getEncoding().substring(0, splitPoint);
				String fragment2 = g2.getEncoding().substring(0, splitPoint);
				String remainder1 = g1.getEncoding().substring(splitPoint);
				String remainder2 = g2.getEncoding().substring(splitPoint);
				
				Genome offspring1 = new Genome(fragment1 + remainder2);
				Genome offspring2 = new Genome(fragment2 + remainder1);
				
				nextGeneration.add(offspring1);
				nextGeneration.add(offspring2);
				i++;
			}
			else
			{
				nextGeneration.add(selectGenome().mutate());
			}
		}
		
		genomes.clear();
		genomes.addAll(nextGeneration);
	}
	
	private Genome selectGenome()
	{
		double fitnessSum = 0.0;
		for(int i = 0; i < genomes.size(); i++)
			fitnessSum += genomes.get(i).getFitness();
		
		double lotto = Helper.random.nextDouble() * fitnessSum;
		double lo = 0.0, hi = 0.0;
				
		for(int i = 0; i < genomes.size(); i++)
		{
			hi = lo + genomes.get(i).getFitness();
						
			if(lotto <= hi)
				return genomes.get(i);
			
			lo = hi;
		}
		
		return null;
	}
	
	public Genome getBestGenome()
	{
		return bestGenome;
	}
}