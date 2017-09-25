package geneticStateMachine;

public class Genome 
{
	private String encoding;
	private double fitness;
	
	public Genome(String encoding)
	{
		this.encoding = encoding;
		this.fitness = 0.0;
	}
		
	public Genome mutate()
	{
		int mutationPoint = Helper.random.nextInt(encoding.length());
		char replaceChar = encoding.charAt(mutationPoint) == '0' ? '1' : '0';
		String firstPart = mutationPoint == 0 ? "" : encoding.substring(0, mutationPoint);
		String thirdPart = mutationPoint == encoding.length() ? "" : encoding.substring(mutationPoint + 1);
		return new Genome(firstPart + replaceChar + thirdPart);
	}
	
	public Genome copy()
	{
		return new Genome(encoding);
	}
	
	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}
	
	public double getFitness()
	{
		return fitness;
	}
	
	public String getEncoding()
	{
		return encoding;
	}
}
