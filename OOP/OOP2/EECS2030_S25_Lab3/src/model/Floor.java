package model;

public class Floor {
    private int maxCapacity;    
    private Unit[] units;
    private int size;           

    public Floor(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.units = new Unit[maxCapacity];
        this.size = 0;
    }

    public Floor(Floor other) {
        this.maxCapacity = other.maxCapacity;
        this.units       = new Unit[other.maxCapacity];
        this.size        = other.size;
        for (int i = 0; i < size; i++) {
            this.units[i] = new Unit(other.units[i]);
        }
    }

    private int utilized() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += units[i].getAreaFeet();
        }
        return sum;
    }

    public void addUnit(String function, int width, int length)
            throws InsufficientFloorSpaceException {
        int area = width * length;
        if (utilized() + area > maxCapacity) {
            throw new InsufficientFloorSpaceException();
        }
        units[size++] = new Unit(function, width, length);
    }

    @Override
    public String toString() {
        int used = utilized();
        int rem  = maxCapacity - used;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "Floor's utilized space is %d sq ft (%d sq ft remaining): [",
            used, rem
        ));
        for (int i = 0; i < size; i++) {
            Unit u = units[i];
            sb.append(String.format(
                "%s: %d sq ft (%d' by %d')",
                u.getFunction(),
                u.getAreaFeet(),
                u.getWidthFeet(),
                u.getLengthFeet()
            ));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Floor)) return false;
        Floor f = (Floor) o;
        if (this.maxCapacity != f.maxCapacity) return false;
        if (this.size != f.size) return false;

        boolean[] used = new boolean[size];
        for (int i = 0; i < size; i++) {
            boolean found = false;
            for (int j = 0; j < size; j++) {
                if (!used[j] && units[i].equals(f.units[j])) {
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


