package model;

public class Channel {
    private String name;
    private Follower[] followers;
    private int    numFollowers;
    private String[] videos;
    private int      numVideos;
    private final int maxFollowers;
    private final int maxVideos;

    public Channel(String name, int maxFollowers, int maxVideos) {
        this.name         = name;
        this.maxFollowers = maxFollowers;
        this.maxVideos    = maxVideos;
        this.followers    = new Follower[maxFollowers];
        this.videos       = new String[maxVideos];
        this.numFollowers = 0;
        this.numVideos    = 0;
    }
    
 // capacity and duplicates checker
    public void follow(Follower f) {
        if (numFollowers >= maxFollowers) return;          
        for (int i = 0; i < numFollowers; i++) {           
            if (followers[i] == f) return;
        }
        followers[numFollowers++] = f;
        f.addChannel(this);
    }

    public void unfollow(Follower f) {
        int idx = -1;
        for (int i = 0; i < numFollowers; i++) {
            if (followers[i] == f) { idx = i; break; }
        }
        if (idx < 0) return;
        for (int i = idx; i < numFollowers - 1; i++) {
            followers[i] = followers[i + 1];
        }
        followers[--numFollowers] = null;
        f.removeChannel(this);
    }

    public void releaseANewVideo(String videoName) {
        if (numVideos < maxVideos) {
            videos[numVideos++] = videoName;
        }
        for (int i = 0; i < numFollowers; i++) {
            Follower fol = followers[i];
            if (fol instanceof Subscriber) {
                ((Subscriber)fol).recommendVideo(videoName);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" released ");
        if (numVideos == 0) {
            sb.append("no videos");
        } else {
            sb.append("<");
            for (int i = 0; i < numVideos; i++) {
                sb.append(videos[i]);
                if (i < numVideos - 1) sb.append(", ");
            }
            sb.append(">");
        }
        sb.append(" and ");
        if (numFollowers == 0) {
            sb.append("has no followers.");
        } else {
            sb.append("is followed by [");
            for (int i = 0; i < numFollowers; i++) {
                sb.append(followers[i].getPrefix())
                  .append(" ")
                  .append(followers[i].getName());
                if (i < numFollowers - 1) sb.append(", ");
            }
            sb.append("].");
        }
        return sb.toString();
    }

    // how many videos have been released so far 
    public int getNumVideos() {
        return numVideos;
    }

    // a fresh array (length = numVideos) of all released video names 
    public String[] getVideos() {
        String[] copy = new String[numVideos];
        for (int i = 0; i < numVideos; i++) {
            copy[i] = videos[i];
        }
        return copy;
    }

    // how many followers right now 
    public int getNumFollowers() {
        return numFollowers;
    }

    // a fresh array (length = numFollowers) of all follower objects 
    public Follower[] getFollowers() {
        Follower[] copy = new Follower[numFollowers];
        for (int i = 0; i < numFollowers; i++) {
            copy[i] = followers[i];
        }
        return copy;
    }

    public String getName() {
        return name;
    }
}
