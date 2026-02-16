package model;

public class DummyApp extends WeatherApp{
	public DummyApp(String name, int max){
		super(name, max);
	}
	
	public String toString() {
		String result = null; 
		String listOfStations = "<";
		for(int i = 0; i < this.nos; i ++) {
			listOfStations += this.stations[i].getName();
			if(i < this.nos - 1) {
				listOfStations += ", ";
			}
		}
		listOfStations += ">";
		
		if(this.nos == 0) {
			result = String.format("Weather Dummy App %s is connected to no stations.", 
					this.name);
		}
		else {
			result = String.format("Weather Dummy App %s is connected to %d stations: %s.", 
					this.name, this.nos, listOfStations);
		}
		return result;
	}
	
	
}