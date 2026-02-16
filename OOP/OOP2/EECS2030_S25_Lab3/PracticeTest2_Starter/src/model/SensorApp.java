package model;

public class SensorApp extends WeatherApp {

	public SensorApp(String name, int MaxStation) {
		this.name=name;
		this.maxSatation=MaxStation;
		this.numStation=0;
		this.weatherStation=new WeatherStation[MaxStation];
	}
	
	public String appType() {
		return "Sensor App";
	}
	
//	@Override
//	public String toString() {
//		String s;
//		if(this.numStation==0)
//		s=String.format("Weather %s %s is connected to no stations.", this.appType(),this.name);
//		
//		else {
//			s=String.format("Weather %s %s is connected to %d stations: <", this.appType(),this.name,this.numStation);
//			for(int i=0; i<this.numStation; i++) {
//				s+=this.weatherStation[i].getName();
//				if(i<this.numStation-1) {
//					s+=", ";
//				}
//			}
//			s+=">.";
//		}
//		
//		return s;
//	}
//	"Weather Dummy App Dummy5678 is connected to 1 stations: <Station@NorthYork>."
//	("Weather Sensor App Sensor1234 is connected to no stations."

	public DummyApp[] getConnectedDummysOf(String station) {
		// TODO Auto-generated method stub
		int index=-1;
		for(int i=0; i<this.numStation; i++) {
			if(this.weatherStation[i].getName().equals(station)) {
				index=i;
			}
		}
		if(index<0) {
			DummyApp[] dummys=new DummyApp[0];
			return dummys;
		}
		else {
			DummyApp[] dummys=this.weatherStation[index].getDummyApps();
			return dummys;
		}
		
	}

}
