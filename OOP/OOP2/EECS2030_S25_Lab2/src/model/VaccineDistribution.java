package model;

public class VaccineDistribution {
    private Vaccine vaccine;
    private int doses;

    public VaccineDistribution(Vaccine vaccine, int doses) {
        this.vaccine = vaccine;
        this.doses = doses;
    }

    @Override
    public String toString() {
        return doses + " doses of " + vaccine.getCodeName() + " by " + vaccine.getManufacturer();
    }
}