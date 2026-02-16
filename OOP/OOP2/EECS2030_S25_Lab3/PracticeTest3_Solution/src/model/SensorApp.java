package model;

public class SensorApp extends WeatherApp {
	
	public SensorApp(String name, int maxNumOfChannels) {
		super(name, maxNumOfChannels);
	}

	public DummyApp[] getConnectedDummysOf(String stationName) {
		int index = this.indexOfStation(stationName);
		DummyApp[] dumymyApps;
		if(index < 0) {
			dumymyApps = new DummyApp[0];
		}
		else {
			dumymyApps = this.stations[index].getDummyApps(); 
		}
		return dumymyApps;
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
			result = String.format("Weather Sensor App %s is connected to no stations.", 
					this.name);
		}
		else {
			result = String.format("Weather Sensor App %s is connected to %d stations: %s.", 
					this.name, this.nos, listOfStations);
		}

		return result;
	}
}
