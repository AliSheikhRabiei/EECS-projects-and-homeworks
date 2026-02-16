package model;

public class VaccinationSite {
    private String siteName;
    private int limit;
    private Vaccine[] vaccines;
    private int[] supply;
    private int kindCount;
    private int totalSupply;
    private HealthRecord[] appointments;
    private int appointmentCount;

    public VaccinationSite(String siteName, int limit) {
        this.siteName = siteName;
        this.limit = limit;
        this.vaccines = new Vaccine[4];
        this.supply = new int[4];
        this.kindCount = 0;
        this.totalSupply = 0;
        this.appointments = new HealthRecord[200];
        this.appointmentCount = 0;
    }

    public void addDistribution(Vaccine v, int doses)
            throws UnrecognizedVaccineCodeNameException, TooMuchDistributionException {
        // check recognition
        boolean recognized = false;
        for (String cn : Vaccine.getRecognized()) {
            if (cn.equals(v.getCodeName())) { recognized = true; break; }
        }
        if (!recognized) throw new UnrecognizedVaccineCodeNameException();
        if (totalSupply + doses > limit) throw new TooMuchDistributionException();
        // update or add
        for (int i = 0; i < kindCount; i++) {
            if (vaccines[i].getCodeName().equals(v.getCodeName())) {
                supply[i] += doses;
                totalSupply += doses;
                return;
            }
        }
        // new kind
        if (kindCount < vaccines.length) {
            vaccines[kindCount] = v;
            supply[kindCount] = doses;
            kindCount++;
            totalSupply += doses;
        }
    }

    public int getNumberOfAvailableDoses() {
        return totalSupply;
    }
    public int getNumberOfAvailableDoses(String codeName) {
        for (int i = 0; i < kindCount; i++) {
            if (vaccines[i].getCodeName().equals(codeName)) {
                return supply[i];
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = siteName + " has " + totalSupply + " available doses: <";
        for (int i = 0; i < kindCount; i++) {
            s += supply[i] + " doses of " + vaccines[i].getManufacturer();
            if (i < kindCount - 1) s += ", ";
        }
        s += ">";
        return s;
    }

    public void bookAppointment(HealthRecord rec) throws InsufficientVaccineDosesException {
        if (appointmentCount < 200 && appointmentCount < totalSupply) {
            appointments[appointmentCount++] = rec;
            rec.setAppointmentStatus("Last vaccination appointment for "
                + rec.getPatientName() + " with " + siteName + " succeeded");
        } else {
            rec.setAppointmentStatus("Last vaccination appointment for "
                + rec.getPatientName() + " with " + siteName + " failed");
            throw new InsufficientVaccineDosesException();
        }
    }

    public void administer(String date) {
        // consume in distribution order
        for (int j = 0; j < appointmentCount; j++) {
            HealthRecord rec = appointments[j];
            for (int i = 0; i < kindCount; i++) {
                if (supply[i] > 0) {
                    supply[i]--;
                    totalSupply--;
                    rec.addRecord(vaccines[i], siteName, date);
                    break;
                }
            }
        }
        // clear appointments
        appointmentCount = 0;
    }
}

