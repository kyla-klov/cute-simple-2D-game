package main;

public class Building {
	// Once name is set it should not be updated
	private String name;
	private int numVisits;
	
	public Building(String inputName){
		name = inputName;
	}
	
	public String getName(){
		return name;
	}
	
	public int getNumVisits(){
		return numVisits;
	}
}
