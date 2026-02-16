package model;

public class DummyApp extends WeatherApp {

	public DummyApp(String name, int MaxStation) {
		this.name=name;
		this.maxSatation=MaxStation;
		this.numStation=0;
		this.weatherStation=new WeatherStation[MaxStation];
	}
	
	public String appType() {
		return "Dummy App";
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
		
//		return s;
//	}
	
}
