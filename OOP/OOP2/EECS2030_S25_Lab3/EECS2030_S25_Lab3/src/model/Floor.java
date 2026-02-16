package model;

public class Floor {
	private int cap;
	private int used;
	private Unit[] units;
	private int num;
	
	public Floor(int capacity) {
		this.cap=capacity;
		this.used=0;
		this.units=new Unit[capacity];
		this.num=0;
	}
	@Override
	public String toString() {
		String s;
		s=String.format("Floor's utilized space is %d sq ft (%d sq ft remaining): [", this.used, this.cap-this.used);
		for(int i=0; i<this.num; i++) {
			s+=this.units[i].getName()+": "+this.units[i].getArea()+" sq ft ("+this.units[i].getWidth()+"\' by "+this.units[i].getLength()+"\')";
//					[Master Bedroom: 144 sq ft (18' by 8')]	
			if(i<this.num-1) {
				s+=", ";
			}
		}
		s+="]";
		return s;
	}
	public void addUnit(String name, int w, int l) throws InsufficientFloorSpaceException {
		// TODO Auto-generated method stub
		if((this.cap-this.used)<(w*l)) {
			throw new InsufficientFloorSpaceException();
		}
		else {
			Unit u=new Unit(name, w, l);
			this.units[num]= u;
			this.num++;
			this.used+=u.getArea();
		}
	}
	
	
	public boolean equals(Object f) {
		if(this==f) return true;
		if(!(f instanceof Floor)) return false;
		
		boolean s=false;
		Floor fl=(Floor) f;
		if(this.cap!=fl.cap) return false;
		if(this.used!=fl.used) return false;
		if(this.num!=fl.num) return false;
		
        boolean[] used = new boolean[num];
        for (int i = 0; i < num; i++) {
            boolean found = false;
            for (int j = 0; j < num; j++) {
                if (!used[j] && units[i].equals(fl.units[j])) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
	}
}
