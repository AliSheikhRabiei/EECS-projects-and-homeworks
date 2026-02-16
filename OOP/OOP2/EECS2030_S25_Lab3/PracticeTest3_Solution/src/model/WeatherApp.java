package model;

public class WeatherApp {
	protected String name; // inherited to all sub-classes

	protected WeatherStation[] stations;
	protected int nos; /* number of stations */

	public WeatherApp(String name, int maxNumOfStations) {
		this.name = name;
		this.stations = new WeatherStation[maxNumOfStations];
		this.nos = 0;
	} 

	protected void link(WeatherStation c) {
		this.stations[this.nos] = c;
		this.nos ++;
	} 
	protected int indexOfStation(String name) {
		int index = -1;
		for(int i = 0; i < this.nos; i ++) {
			if(this.stations[i].getName().equals(name)) {
				index = i;
			}
		}
		return index;
	}

	public String getName() {
		return this.name;
	}
}
