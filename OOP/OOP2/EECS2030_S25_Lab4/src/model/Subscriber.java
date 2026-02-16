package model;

public class Subscriber extends Follower {
    private String[] recommended;
    private int      numRecommended;
    private final int maxRecommended;

    public Subscriber(String name, int maxChannels, int maxRecommended) {
        super(name, maxChannels);
        this.maxRecommended = maxRecommended;
        this.recommended    = new String[maxRecommended];
        this.numRecommended = 0;
    }

    @Override
    protected String getPrefix() {
        return "Subscriber";
    }

    public void recommendVideo(String videoName) {
        if (numRecommended >= maxRecommended) return;
        for (int i = 0; i < numRecommended; i++) {
            if (recommended[i].equals(videoName)) return;
        }
        recommended[numRecommended++] = videoName;
    }

    public int getNumVideos() {
        return numRecommended;
    }

    public String[] getVideos() {
        String[] copy = new String[numRecommended];
        for (int i = 0; i < numRecommended; i++) {
            copy[i] = recommended[i];
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPrefix()).append(" ").append(name)
          .append(" follows ");
        if (numChannels == 0) {
            sb.append("no channels");
        } else {
            sb.append("[");
            for (int i = 0; i < numChannels; i++) {
                sb.append(channels[i].getName());
                if (i < numChannels - 1) sb.append(", ");
            }
            sb.append("]");
        }
        if (numRecommended == 0) {
            sb.append(" and has no recommended videos.");
        } else {
            sb.append(" and is recommended <");
            for (int i = 0; i < numRecommended; i++) {
                sb.append(recommended[i]);
                if (i < numRecommended - 1) sb.append(", ");
            }
            sb.append(">.");
        }
        return sb.toString();
    }


    public void watch(String videoName, int minutes) {
        for (int c = 0; c < numChannels; c++) {
            Channel chan = channels[c];
            String[] vids = chan.getVideos();
            for (String v : vids) {
                if (v.equals(videoName)) {
                    for (Follower fol : chan.getFollowers()) {
                        if (fol instanceof Monitor) {
                            ((Monitor)fol).recordWatch(chan, minutes);
                        }
                    }
                    return;
                }
            }
        }
    }
}
