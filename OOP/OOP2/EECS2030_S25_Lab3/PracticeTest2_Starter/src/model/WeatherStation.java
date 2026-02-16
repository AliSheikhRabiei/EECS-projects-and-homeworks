package model;

public class WeatherStation {
	
	private String name;
	private WeatherApp[] weatherApps;
	private int numApps;
	private int MAXAPPS=2;
	
	public WeatherStation(String name) {
		super();
		this.name = name;
		this.weatherApps= new WeatherApp[MAXAPPS];
		this.numApps=0;
	}

	@Override
	public String toString() {
		String s;
		if(this.numApps==0)
		s=String.format("%s has no connected apps.",this.name);
		
		else {
			s=String.format("%s is connected by %d apps: <",this.name,this.numApps);
			for(int i=0; i<this.numApps; i++) {
				s+="Weather "+ this.weatherApps[i].appType()+" "+this.weatherApps[i].getName();
				if(i<this.numApps-1) {
					s+=", ";
				}
			}
			s+=">.";
		}
		
		return s;
	}

	public WeatherApp[] getInternalAppsArray() {
		// TODO Auto-generated method stub
		return this.weatherApps;
	}

	public WeatherApp[] getApps() {
		// TODO Auto-generated method stub
		WeatherApp[] copy=new WeatherApp[this.numApps];
		for(int i=0; i<this.numApps; i++) {
			copy[i]=this.weatherApps[i];
		}
		return copy;
	}

	public void connect(WeatherApp app) {
		// TODO Auto-generated method stub
		if(this.numApps==this.MAXAPPS) {
			MAXAPPS+=3;
			WeatherApp[] temp=new WeatherApp[MAXAPPS];
			for(int i=0; i<this.numApps; i++) {
				temp[i]=this.weatherApps[i];
			}
			this.weatherApps=temp;
		}
		
		this.weatherApps[this.numApps]=app;
		this.numApps++;
		app.addStation(this);
	}

	public String getName() {
		return name;
	}

	public DummyApp[] getDummyApps() {
		// TODO Auto-generated method stub
		int count=0;
		DummyApp[] temp=new DummyApp[this.numApps];
		for(int i=0; i<this.numApps; i++) {
			WeatherApp app=this.weatherApps[i];
			if(app instanceof DummyApp) {
				temp[count]=(DummyApp) app;
				count++;
			}
		}
		DummyApp[] dummys=new DummyApp[count];
		for(int i=0; i<count; i++) {
			dummys[i]=temp[i];
		}
		return dummys;
	}
	
	

}



