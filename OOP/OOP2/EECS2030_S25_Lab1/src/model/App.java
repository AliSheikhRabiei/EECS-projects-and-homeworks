package model;

public class App {
    private final String name;
    private final Log[] updateHistory;
    private final int[] ratings;
    private int logCount;
    private int ratingCount;
    private final int maxRatings;

    public App(String name, int maxRatings) {
        this.name = name;
        this.maxRatings = maxRatings;
        this.updateHistory = new Log[20];
        this.ratings = new int[maxRatings];
        this.logCount = 0;
        this.ratingCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getWhatIsNew() {
        if (logCount == 0) return "n/a";
        return updateHistory[logCount - 1].toString();
    }

    public Log[] getUpdateHistory() {
        Log[] result = new Log[logCount];
        for (int i = 0; i < logCount; i++) {
            result[i] = updateHistory[i];
        }
        return result;
    }

    public Log getVersionInfo(String version) {
        for (int i = 0; i < logCount; i++) {
            if (updateHistory[i].getVersion().equals(version)) {
                return updateHistory[i];
            }
        }
        return null;
    }

    public void releaseUpdate(String version) {
        if (logCount < 20) {
            updateHistory[logCount] = new Log(version);
            logCount++;
        }
    }

    public void submitRating(int rating) {
        if (ratingCount < maxRatings) {
            ratings[ratingCount] = rating;
            ratingCount++;
        }
    }

    public String getRatingReport() {
        if (ratingCount == 0) return "No ratings submitted so far!";
        
        int[] counts = new int[5];
        int sum = 0;
        
        for (int i = 0; i < ratingCount; i++) {
            int rating = ratings[i];
            counts[5 - rating]++;
            sum += rating;
        }
        
        double average = (double) sum / ratingCount;
        return String.format("Average of %d ratings: %.1f (Score 5: %d, Score 4: %d, Score 3: %d, Score 2: %d, Score 1: %d)",
                ratingCount, average, counts[0], counts[1], counts[2], counts[3], counts[4]);
    }

    @Override
    public String toString() {
        return name + " (Current Version: " + getWhatIsNew() + "; Average Rating: " + 
               (ratingCount > 0 ? String.format("%.1f", (double) sumRatings() / ratingCount) : "n/a") + ")";
    }
    
    private int sumRatings() {
        int sum = 0;
        for (int i = 0; i < ratingCount; i++) {
            sum += ratings[i];
        }
        return sum;
    }
}