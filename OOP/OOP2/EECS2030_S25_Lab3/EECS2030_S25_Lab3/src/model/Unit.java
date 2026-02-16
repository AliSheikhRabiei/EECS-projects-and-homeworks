package model;

public class Unit {
	private int length;
	private int width;
	private double len;
	private double wid;
	private String name;
	private int mesurment;
	
	public Unit(String name, int width, int length) {
		this.name=name;
		this.length=length;
		this.width=width;
		this.mesurment=0;
		this.len=length*0.3048;
		this.wid=width*0.3048;
	}
	public String toString() {
		String s;
		if(this.mesurment==0) {
		s=String.format("A unit of %d square feet (%d' wide and %d' long) functioning as %s",
				this.length*this.width, this.width, this.length, this.name);
		}
		else if(this.mesurment==1) {
		s=String.format("A unit of %.2f square meters (%.2f m wide and %.2f m long) functioning as %s",
				this.len*this.wid, this.wid, this.len, this.name);
		}
		else {
			s="";
		}
		return s;
	}
	public void toggleMeasurement() {
		// TODO Auto-generated method stub
		if(this.mesurment==0) {
			this.mesurment=1;
		}
		else if(this.mesurment==1) {
			this.mesurment=0;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	
	public boolean equals(Object unit) {
		if(this==unit) return true;
		if(!(unit instanceof Unit)) return false;
		
		boolean s=false;
		if(this.name.equals(((Unit) unit).name) && ((this.length*this.width)==((Unit) unit).length*((Unit) unit).width)) {
			s=true;
		}
			
		return s;
	}
	public int getArea() {
		// TODO Auto-generated method stub
		return this.width*this.length;
	}
}
