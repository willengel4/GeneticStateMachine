package geneticStateMachine;

import java.util.ArrayList;

public class Population 
{
	/* A list of genomes that make up the population */
	private ArrayList<Genome> genomes;
	private int m;
	private Genome populationBest;
	private Evaluator evaluator;
	
	public Population(int numGenomes, Evaluator evaluator)
	{
		/* Initialize the genomes list */
		genomes = new ArrayList<Genome>();
		m = numGenomes;
		this.evaluator = evaluator;
	}
	
	public void randomizePopulation(int bitsPerGenome)
	{
		/* Creates the specified number of genomes, each with it's own
		 * random configuration */
		for(int i = 0; i < m; i++)
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
			System.out.print(genomes.get(i).getEncoding() + " ");
		
		System.out.println();
	}
	
	public void evaluatePopulation()
	{
		double fitnessSum = 0.0;
		
		for(Genome g : genomes)
		{
			g.setFitness(evaluator.evaluate(g.getEncoding()));
			fitnessSum += g.getFitness();
			if(populationBest == null || g.compareTo(populationBest) > 0)
				populationBest = g;
		}
		
		System.out.println("avg fitness: " + (fitnessSum / (double)genomes.size()));
	}
	
	public Population createNextGeneration()
	{
		evaluatePopulation();

		int pCopy = 5;
		int pCrossover = 5;
		int pMutate = 1;
		
		Population nextGeneration = new Population(m, evaluator);
		
		for(int i = 0; i < m; i++)
		{
			int lotto = Helper.random.nextInt(pCopy + pCrossover + pMutate);
			
			if(lotto < pCopy)
			{
				nextGeneration.addGenome(selectGenome().copy());
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
				
				nextGeneration.addGenome(offspring1);
				nextGeneration.addGenome(offspring2);
				i++;
			}
			else
			{
				nextGeneration.addGenome(selectGenome().mutate());
			}
		}
		
		return nextGeneration;
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
	
	public Genome getPopulationBest()
	{
		return populationBest;
	}
	
	public void addGenome(Genome g)
	{
		genomes.add(g);
	}
}