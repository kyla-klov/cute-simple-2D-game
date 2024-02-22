package main;

public class Building {
	// Once name is set it should not be updated
	private String name;
	
	public Building(String inputName){
		name = inputName;
	}
	
	public String getName(){
		return name;
	}
}
