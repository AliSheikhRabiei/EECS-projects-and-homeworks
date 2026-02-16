package model;

public class Unit {
    private String function;
    private int widthFeet;
    private int lengthFeet;
    private boolean inFeet; 

    public Unit(String function, int widthFeet, int lengthFeet) {
        this.function   = function;
        this.widthFeet  = widthFeet;
        this.lengthFeet = lengthFeet;
        this.inFeet     = true;
    }

    public Unit(Unit other) {
        this.function   = other.function;
        this.widthFeet  = other.widthFeet;
        this.lengthFeet = other.lengthFeet;
        this.inFeet     = other.inFeet;
    }

    public void toggleMeasurement() {
        inFeet = !inFeet;
    }

    public int getAreaFeet() {
        return widthFeet * lengthFeet;
    }

    public String getFunction() {
        return function;
    }
    public int getWidthFeet() {
        return widthFeet;
    }
    public int getLengthFeet() {
        return lengthFeet;
    }

    @Override
    public String toString() {
        if (inFeet) {
            int area = getAreaFeet();
            return String.format(
                "A unit of %d square feet (%d' wide and %d' long) functioning as %s",
                area, widthFeet, lengthFeet, function
            );
        } else {
            double w = widthFeet  * 0.3048;
            double l = lengthFeet * 0.3048;
            double areaM = w * l;
            return String.format(
                "A unit of %.2f square meters (%.2f m wide and %.2f m long) functioning as %s",
                areaM, w, l, function
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit u = (Unit) o;
        return this.function.equals(u.function)
            && this.getAreaFeet() == u.getAreaFeet();
    }
}
