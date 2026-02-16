package model;

public class WeatherStation { 

	private String name;

	private WeatherApp[] apps; /* polymorphic array */
	private int noa; /* number of apps */

	public WeatherStation(String name) {
		this.name = name;
		this.apps = new WeatherApp[2];
		this.noa = 0;
	}

	public WeatherApp[] getInternalAppsArray()
	{
		return this.apps;
	}

	public WeatherApp[] getApps() {
		WeatherApp[] result = new WeatherApp[this.noa];
		for (int i = 0; i < this.noa; i++) {
			result[i]=this.apps[i];
		}
		return result;
	}

	public DummyApp[] getDummyApps() {
		int count = 0;
		DummyApp[] temp = new DummyApp[this.noa];
		for(int i = 0; i < this.noa; i ++) {
			WeatherApp app = this.apps[i];
			/*
			 * Check if the weather app is an instance of DummyApp
			 * If so, add to temp
			 */
			if(app instanceof DummyApp) {
				temp[count] = (DummyApp) app;
				count ++;
			}
		}
		
		DummyApp[] dummys = new DummyApp[count];
		for(int i = 0; i < count; i ++) {
			dummys[i] = temp[i];
		}
		return dummys;
	}

	public void connect(WeatherApp app) {
		// Resize array if full --> add 3 more spaces
		if (this.noa == this.apps.length) {
			WeatherApp[] newArray = new WeatherApp[this.apps.length + 3];
			for (int i = 0; i < this.apps.length; i++) {
				newArray[i] = this.apps[i];
			}
			this.apps = newArray;
		}

		/* Updates on the current channel. */
		this.apps[this.noa] = app;
		this.noa++;

		/* Updates on the parameter `app`. */
		app.link(this);
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		String result = null;

		String listOfFollowers = "<";
		for(int i = 0; i < this.noa; i ++) {
			String type = null;
			WeatherApp f = this.apps[i];
			if(f instanceof SensorApp) {
				type = "Weather Sensor App";
			}
			else if(f instanceof DummyApp) {
				type = "Weather Dummy App";
			}
			listOfFollowers += type + " " + f.getName();
			if(i < this.noa - 1) {
				listOfFollowers += ", ";
			}
		}
		listOfFollowers += ">";

		if(this.noa == 0) {
			result = String.format("%s has no connected apps.", 
					this.name);
		}
		else {
			result = String.format("%s is connected by %d apps: %s.", 
					this.name, this.noa, listOfFollowers);
		}
		return result;
	}
}
