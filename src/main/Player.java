package main;

public class Player {
	private int day;
	private int time;
	private int energy;

	public int getDay(){
		return day;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	public int getTime(){
		return time;
	}
	
	/**
	 * Takes the amount of energy to deduct from energy and if the amount to deduct is
	 * not greater than the remaining energy or it is not negative we deduct the required
	 * amount. A 1 is return if the energy is deducted else -1 is returned.
	 * 
	 * @param amountEnergy
	 * @throws IllegalArgumentException
	 * @returns 1 if successful else -1
	 */
	
	public int removeEnergy(int amountEnergy) throws IllegalArgumentException {
		if (amountEnergy < 0) {
			throw new IllegalArgumentException("inputted energy cannot be negative");
		} else if (amountEnergy > energy) {
			return -1;
		} else {
			energy = energy - amountEnergy;
			return 1;
		}
	}
	
	/**
	 * Takes the amount of time we want to pass and passes this time if the numHours +
	 * time is less than or equal to 16 and numHours is non negative. If the time is passed
	 * a 1 is returned else a -1 is returned.
	 * 
	 * @param numHours
	 * @return 1 is successful else -1
	 * @throws IllegalArgumentException
	 */
	
	public int passTime(int numHours) throws IllegalArgumentException{
		if (numHours + time > 16) {
			return -1;
		} else if (numHours < 0) {
			throw new IllegalArgumentException("numHours cannot be negative");
		} else {
			time = time + numHours;
			return 1;
		}
	}
	
	public void incrementDay(){
		if (day < 7) {
			day = day + 1;
		}
	}
	
}
