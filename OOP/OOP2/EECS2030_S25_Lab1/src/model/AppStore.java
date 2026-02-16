package model;

public class AppStore {
    private final String branch;
    private final App[] apps;
    private int appCount;
    private final int maxApps;

    public AppStore(String branch, int maxApps) {
        this.branch = branch;
        this.maxApps = maxApps;
        this.apps = new App[maxApps];
        this.appCount = 0;
    }

    public String getBranch() {
        return branch;
    }

    public App getApp(String name) {
        for (int i = 0; i < appCount; i++) {
            if (apps[i].getName().equals(name)) {
                return apps[i];
            }
        }
        return null;
    }

    public void addApp(App app) {
        if (appCount < maxApps) {
            apps[appCount] = app;
            appCount++;
        }
    }

    public String[] getStableApps(int minUpdates) {
        int count = 0;
        for (int i = 0; i < appCount; i++) {
            if (apps[i].getUpdateHistory().length >= minUpdates) {
                count++;
            }
        }
        
        String[] stableApps = new String[count];
        int index = 0;
        for (int i = 0; i < appCount; i++) {
            App app = apps[i];
            if (app.getUpdateHistory().length >= minUpdates) {
                stableApps[index] = app.getName() + " (" + app.getUpdateHistory().length + 
                                   " versions; Current Version: " + app.getWhatIsNew() + ")";
                index++;
            }
        }
        return stableApps;
    }
}