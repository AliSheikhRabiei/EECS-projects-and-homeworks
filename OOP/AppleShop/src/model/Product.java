package model;

//template for individual apples
//random comment just to follow the video

public class Product {
	
    private String model;
    private String finish;
    private int storage;
    private boolean hasCellularConnectivity;
    private double originalPrice;
    private double discountValue;
 
    //constructors 
    public Product() {
    }

    public Product(String model, double originalPrice) {
        this.model = model;
        this.originalPrice = originalPrice;
    }
    
//    public Product(String model, String finish, int storage, boolean hasCellular, double originalPrice, double discount) {
//        this.model = model;
//        this.finish = finish;
//        this.storage = storage;
//        this.hasCellularConnectivity = hasCellular;
//        this.originalPrice = originalPrice;
//        this.discountValue = discount;
//    }

    //accessories
    //getters
    public String getModel() {
        return this.model;
    }

    public String getFinish() {
        return this.finish;
    }

    public int getStorage() {
        return this.storage;
    }

    public boolean hasCellularConnectivity() {
        return this.hasCellularConnectivity;
    }

    public double getOriginalPrice() {
        return this.originalPrice;
    }

    public double getDiscountValue() {
        return this.discountValue;
    }

    //mutators
	//setters
	public void setHasCellularConnectivity(boolean hasCellularConnectivity) {
		this.hasCellularConnectivity = hasCellularConnectivity;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
    
	//i dont want the variable shadowing here so i just use this and wont add extra valuable to save space
    public double getPrice() {
        return this.originalPrice - this.discountValue;
    
    }

    //tostrings
    @Override
    public String toString() {
    	return model + " " + finish + " " + storage + "GB (CellularConnectivity: " + hasCellularConnectivity + "): $("
    			+ String.format("%.2f", originalPrice) + " - "  + String.format("%.2f", discountValue) + ")";
    	
    }
    
//	@Override
//	public String toString() {
//		
//		return "Product [model=" + model + ", finish=" + finish + ", storage=" + storage + ", hasCellularConnectivity="
//				+ hasCellularConnectivity + ", originalPrice=" + originalPrice + ", discountValue=" + discountValue
//				+ "]";
//	}
//    
    
}
