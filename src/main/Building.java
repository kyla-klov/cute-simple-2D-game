package main;

public class Building {
	// Once name/energyUsage are set they should not be updated
	private String name;
	private int numVisits;
	private int energyUsage;
	private int timeUsage;
	
	public Building(String inputName, int inputEnergyUsage, int inputTimeUsage) throws IllegalArgumentException{
		if (inputEnergyUsage <= 0) {
			throw new IllegalArgumentException("inputEnergyUsage must be positive and non-zero");
		} else if (inputTimeUsage > 16 || inputTimeUsage < 0) {
			throw new IllegalArgumentException("inputTimeUsage cannot be greater than 16 or negative");
		} else {
			name = inputName;
			energyUsage = inputEnergyUsage;
			timeUsage = inputTimeUsage;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public int getTimeUsage() {
		return timeUsage;
	}
	
	public int getNumVisits(){
		return numVisits;
	}
	
	public int getEnergyUsage(){
		return energyUsage;
	}
	
	public void incrementNumVisits(){
		numVisits = numVisits + 1;
	}
}
