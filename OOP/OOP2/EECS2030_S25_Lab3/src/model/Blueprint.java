package model;

public class Blueprint {
    private int totalFloors;
    private Floor[] floors;
    private int size; 


    public Blueprint(int totalFloors) {
        this.totalFloors = totalFloors;
        this.floors      = new Floor[totalFloors];
        this.size        = 0;
    }

    public Blueprint(Blueprint other) {
        this.totalFloors = other.totalFloors;
        this.floors      = new Floor[other.totalFloors];
        this.size        = other.size;
        for (int i = 0; i < size; i++) {
            this.floors[i] = new Floor(other.floors[i]);
        }
    }

    public void addFloorPlan(Floor f) {
        floors[size++] = new Floor(f);
    }

    public Floor[] getFloors() {
        Floor[] copy = new Floor[size];
        for (int i = 0; i < size; i++) {
            copy[i] = new Floor(floors[i]);
        }
        return copy;
    }

    @Override
    public String toString() {
        double percent = (size * 100.0) / totalFloors;
        return String.format(
            "%.1f percents of building blueprint completed (%d out of %d floors)",
            percent, size, totalFloors
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blueprint)) return false;
        Blueprint b = (Blueprint)o;
        if (this.totalFloors != b.totalFloors) return false;
        if (this.size != b.size) return false;
        boolean[] used = new boolean[size];
        for (int i = 0; i < size; i++) {
            boolean found = false;
            for (int j = 0; j < size; j++) {
                if (!used[j] && this.floors[i].equals(b.floors[j])) {
                    used[j] = true;
                    found  = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}
