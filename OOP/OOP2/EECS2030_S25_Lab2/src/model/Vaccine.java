package model;

public class Vaccine {
    private String codeName;
    private String type;
    private String manufacturer;
    private static final String[] recognized = {
        "mRNA-1273", "BNT162b2", "Ad26.COV2.S", "AZD1222"
    };

    public Vaccine(String codeName, String type, String manufacturer) {
        this.codeName = codeName;
        this.type = type;
        this.manufacturer = manufacturer;
    }

    public String getCodeName() {
        return codeName;
    }
    public String getManufacturer() {
        return manufacturer;
    }

    private boolean isRecognized() {
        for (int i = 0; i < getRecognized().length; i++) {
            if (getRecognized()[i].equals(codeName)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String prefix = isRecognized() ? "Recognized vaccine: " : "Unrecognized vaccine: ";
        return prefix + codeName + " (" + type + "; " + manufacturer + ")";
    }

	public static String[] getRecognized() {
		return recognized;
	}
}

