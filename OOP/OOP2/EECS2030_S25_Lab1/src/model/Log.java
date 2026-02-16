package model;

public class Log {
    private final String version;
    private final String[] fixes;
    private int fixCount;

    public Log(String version) {
        this.version = version;
        this.fixes = new String[10];
        this.fixCount = 0;
    }

    public String getVersion() {
        return version;
    }

    public int getNumberOfFixes() {
        return fixCount;
    }

    public String getFixes() {
        if (fixCount == 0) return "[]";
        
        String result = "[";
        for (int i = 0; i < fixCount; i++) {
            result += fixes[i];
            if (i < fixCount - 1) {
                result += ", ";
            }
        }
        return result + "]";
    }

    public void addFix(String fix) {
        if (fixCount < 10) {
            fixes[fixCount] = fix;
            fixCount++;
        }
    }

    @Override
    public String toString() {
        return "Version " + version + " contains " + fixCount + " fixes " + getFixes();
    }
}