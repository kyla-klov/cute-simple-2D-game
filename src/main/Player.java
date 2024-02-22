package main;

public class Player {
	private int day;
	private int energy;
	private int pointsAcquired;
	
	public int getPoints(){
		return pointsAcquired;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	/**
	 * Takes the number of points to add to pointsAcquired and does so 
	 * if the numPoints is not negative.
	 * 
	 * @param numPoints
	 * @throws IllegalArgumentException
	 */
	
	public void addPoints(int numPoints) throws IllegalArgumentException{
		if (numPoints < 0) {
			throw new IllegalArgumentException("inputted points cannot be negative");
		} else {
			pointsAcquired = pointsAcquired + numPoints;
		}
	}
	
	/**
	 * Takes the amount of energy to deduct from energy and if the amount to deduct is
	 * not greater than the remaining energy or it is negative we deduct the required
	 * amount.
	 * 
	 * @param amountEnergy
	 * @throws IllegalArgumentException
	 */
	
	public void removeEnergy(int amountEnergy) throws IllegalArgumentException {
		if (amountEnergy < 0) {
			throw new IllegalArgumentException("inputted energy cannot be negative");
		} else if (amountEnergy > energy) {
			throw new IllegalArgumentException("inputted energy cannot be greater than remaining energy");
		} else {
			energy = energy - amountEnergy;
		}
	}
	
}
