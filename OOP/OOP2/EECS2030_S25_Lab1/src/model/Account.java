package model;

public class Account {
    private final String owner;
    private AppStore store;
    private final App[] downloadedApps;
    private int downloadCount;
    private String status;

    public Account(String owner, AppStore store) {
        this.owner = owner;
        this.store = store;
        this.downloadedApps = new App[50];
        this.downloadCount = 0;
        this.status = "An account linked to the " + store.getBranch() + 
                      " store is created for " + owner + ".";
    }

    public String toString() {
        return status;
    }

    public String[] getNamesOfDownloadedApps() {
        String[] names = new String[downloadCount];
        for (int i = 0; i < downloadCount; i++) {
            names[i] = downloadedApps[i].getName();
        }
        return names;
    }

    public App[] getObjectsOfDownloadedApps() {
        App[] apps = new App[downloadCount];
        for (int i = 0; i < downloadCount; i++) {
            apps[i] = downloadedApps[i];
        }
        return apps;
    }

    public void download(String appName) {
        App app = store.getApp(appName);
        if (app == null) {
            status = "Error: " + appName + " not found in the " + store.getBranch() + " store.";
            return;
        }
        
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedApps[i].getName().equals(appName)) {
                status = "Error: " + appName + " has already been downloaded for " + owner + ".";
                return;
            }
        }
        
        if (downloadCount < 50) {
            downloadedApps[downloadCount] = app;
            downloadCount++;
            status = appName + " is successfully downloaded for " + owner + ".";
        }
    }

    public void uninstall(String appName) {
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedApps[i].getName().equals(appName)) {
                for (int j = i; j < downloadCount - 1; j++) {
                    downloadedApps[j] = downloadedApps[j + 1];
                }
                downloadedApps[downloadCount - 1] = null;
                downloadCount--;
                status = appName + " is successfully uninstalled for " + owner + ".";
                return;
            }
        }
        status = "Error: " + appName + " has not been downloaded for " + owner + ".";
    }

    public void submitRating(String appName, int rating) {
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedApps[i].getName().equals(appName)) {
                downloadedApps[i].submitRating(rating);
                status = "Rating score " + rating + " of " + owner + 
                         " is successfully submitted for " + appName + ".";
                return;
            }
        }
        status = "Error: " + appName + " is not a downloaded app for " + owner + ".";
    }

    public void switchStore(AppStore newStore) {
        this.store = newStore;
        status = "Account for " + owner + " is now linked to the " + newStore.getBranch() + " store.";
    }
}