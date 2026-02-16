package model;

public class WeatherPerson {
	private static WeatherPerson instance = null;
	private String name;
	private WeatherApp[] apps;
	private int noa;
	private int nod;
	private int nos;
	
	/*
	 * Singleton
	 */
	private  WeatherPerson() {
		this.apps=new WeatherApp[100];
		this.noa=0;
		this.nos=0;
		this.nod=0;
	}

	public static WeatherPerson getInstance() {
		if (instance == null) {
			instance = new WeatherPerson();
		}
		return instance;
	}

	public void setName(String name) {
		this.name=name;

	}

	public String getName()
	{
		return this.name;
	}
	/*
	 * Methods
	 */

	public void addApp(WeatherApp w) {
		this.apps[this.noa++]=w;
		
		if(w instanceof DummyApp) {
			this.nod++;
		}
		else {
			this.nos++;
		}
	}
	
	public WeatherApp[] getWeahterApps() {
	  
	    return this.apps;
	}
	
	public String toString()
	{	
		return String.format("%s has  %d sensor apps and %d dummy apps.", this.name, this.nos,this.nod);

	}
	
	public static void resetWeatherPerson() {
		instance=null;
	}
	

}
