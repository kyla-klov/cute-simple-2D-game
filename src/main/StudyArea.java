package main;

public class StudyArea extends Building{
	
	public StudyArea(String inputName) {
		super(inputName);
	}
	
	/**
	 * Generates the number of points achieved for the number of times
	 * the player visits the building. The equation -x^2 + 28x has been
	 * chosen as the peak of the graph corresponds to 14 visits which is
	 * what t
	 * 
	 * @param numVisits
	 * @return pointsAcquired
	 */
	public int generateGeneratePoints(int numVisits){
		int pointsAcquired = -(numVisits * numVisits) + 28*numVisits;
		
		if (pointsAcquired < 0){
			pointsAcquired = 0;
		}
		
		return pointsAcquired;
	}
}
