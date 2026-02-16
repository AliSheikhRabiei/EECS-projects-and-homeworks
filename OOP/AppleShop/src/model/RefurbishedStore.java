package model;


public class RefurbishedStore {
	
	private Entry[] entries;
	private int noe;
	private final int MAX_CAPACITY = 5; //weak store
	
	public RefurbishedStore() {
		this.entries = new Entry[MAX_CAPACITY];
		this.noe  = 0;
	}
	
	public Entry[] getPrivateEntriesArray() {
		return this.entries;
	}
	
	public Entry[] getEntries() {
		Entry[] es = new Entry[this.noe];
		 for(int i=0; i<noe; i++) {
			 es[i]=this.entries[i];
		 }
		 return es;
	}
	
	public void addEntry(Entry e) {
		this.entries[noe]=e;
		this.noe++;
	}
	
	public void addEntry(String sn, Product p) {
//		Entry ne= new Entry(sn, p);
//		this.entries[noe]=ne;
//		this.noe++;
		this.addEntry(new Entry(sn, p));
	}
	
	public void addEntry(String sn, String model, double originalPrice) {
//		Product p= new Product(model, originalPrice);
//		Entry ne= new Entry(sn, p);
//		this.entries[noe]=ne;
//		this.noe++;
		this.addEntry(new Entry(sn, new Product(model, originalPrice)));
	}
	
	public int getNumberOfEntries() {
		return this.noe;
	}
	
	public Product getProduct(String sn) {
		int index=-1;
		for(int i=0; i<this.noe; i++) {
			Entry e= this.entries[i];
			if(e.getSerialNumber().equals(sn)) {
				index = i;
			}
		}
		if(index<0) {
			return null; 
		}
		else {
			return this.entries[index].getProduct();
		}
	}
	
	public String[] getSpaceGreyOrPro() {
		int count =0;
		int[] indices=new int[this.noe];
		for(int i=0; i<this.noe; i++) {
			Product p= this.entries[i].getProduct();
			if(p.getModel().contains("Pro") || (p.getFinish() != null && p.getFinish().equals("Space Grey"))) {
				indices[count]= i;
				count++;
			}	
		}
		String[] sns= new String[count];
		for(int i=0; i<count; i++) {
			sns[i]= this.entries[indices[i]].getSerialNumber();
		}
		return sns;
	}
	
	public String[] getSpaceGreyPro() {
		int count =0;
		int[] indices=new int[this.noe];
		for(int i=0; i<this.noe; i++) {
			Product p= this.entries[i].getProduct();
			if(p.getModel().contains("Pro") && (p.getFinish() != null) && p.getFinish().equals("Space Grey")) {
				indices[count]= i;
				count++;
			}	
		}
		String[] sns= new String[count];
		for(int i=0; i<count; i++) {
			sns[i]= this.entries[indices[i]].getSerialNumber();
		}
		return sns;
	}
}












