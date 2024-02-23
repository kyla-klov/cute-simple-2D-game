package main;

public class StudyArea extends Building{
	
	public StudyArea(String inputName, int inputEnergyUsage) {
		super(inputName, inputEnergyUsage);
	}
	
	/**
	 * Generates the number of points achieved for the number of times
	 * the player visits the building. The equation -x^2 + 28x has been
	 * chosen as the peak of the graph corresponds to 14 visits which is
	 * what we perceive to be the optimal over 7 days.
	 * 
	 * @param numVisits
	 * @return pointsAcquired
	 * @throws IllegalArgumentException
	 */
	public int generatePoints(int numVisits) throws IllegalArgumentException{
		if (numVisits < 0) {
			throw new IllegalArgumentException("numVisits cannot be negative");
		}
		
		int pointsAcquired = -(numVisits * numVisits) + 28*numVisits;
		
		if (pointsAcquired < 0){
			pointsAcquired = 0;
		}
		
		return pointsAcquired;
	}
}
