package model;

public class HealthRecord {
    private String patientName;
    private int historyLimit;
    private Vaccine[] vaccines;
    private String[] sites;
    private String[] dates;
    private int recordCount;
    private String appointmentStatus;

    public HealthRecord(String patientName, int historyLimit) {
        this.setPatientName(patientName);
        this.historyLimit = historyLimit;
        this.vaccines = new Vaccine[historyLimit];
        this.sites = new String[historyLimit];
        this.dates = new String[historyLimit];
        this.recordCount = 0;
        this.appointmentStatus = "No vaccination appointment for " + patientName + " yet";
    }

    public void addRecord(Vaccine v, String site, String date) {
        if (recordCount < historyLimit) {
            vaccines[recordCount] = v;
            sites[recordCount] = site;
            dates[recordCount] = date;
            recordCount++;
        }
    }

    public String getVaccinationReceipt() {
        if (recordCount == 0) {
            return getPatientName() + " has not yet received any doses.";
        }
        String s = "Number of doses " + getPatientName() + " has received: " + recordCount + " ";
        s += "[";
        for (int i = 0; i < recordCount; i++) {
            s += vaccines[i].toString() + " in " + sites[i] + " on " + dates[i];
            if (i < recordCount - 1) s += "; ";
        }
        s += "]";
        return s;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }
    public void setAppointmentStatus(String status) {
        this.appointmentStatus = status;
    }

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
}

