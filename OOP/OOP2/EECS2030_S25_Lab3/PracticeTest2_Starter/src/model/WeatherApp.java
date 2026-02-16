package model;

public class WeatherApp {
	
	protected String name;
	protected Object maxSatation;
	protected WeatherStation[] weatherStation;
	protected int numStation;
	
	public void addStation(WeatherStation weatherStation) {
		// TODO Auto-generated method stub
		this.weatherStation[this.numStation]=weatherStation;
		this.numStation++;
	}

	public String getName() {
		return name;
	}
	
	public String appType() {
		return null;
	}

	
	@Override
	public String toString() {
		String s;
		if(this.numStation==0)
		s=String.format("Weather %s %s is connected to no stations.", this.appType(),this.name);
		
		else {
			s=String.format("Weather %s %s is connected to %d stations: <", this.appType(),this.name,this.numStation);
			for(int i=0; i<this.numStation; i++) {
				s+=this.weatherStation[i].getName();
				if(i<this.numStation-1) {
					s+=", ";
				}
			}
			s+=">.";
		}
		return s;
	}
}
