package main;

public class Building {
	// Once name/energyUsage are set they should not be updated
	private String name;
	private int numVisits;
	private int energyUsage;
	
	public Building(String inputName, int inputEnergyUsage) throws IllegalArgumentException{
		if (inputEnergyUsage <= 0) {
			throw new IllegalArgumentException("inputEnergyUsage must be positive and non-zero");
		} else {
			name = inputName;
			energyUsage = inputEnergyUsage;
		}
	}
	
	public String getName(){
		return name;
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
