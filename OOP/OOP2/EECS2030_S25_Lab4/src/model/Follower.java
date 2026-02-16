package model;

public abstract class Follower {
    protected String   name;
    protected Channel[] channels;
    protected int       numChannels;
    private   final int maxChannels;

    protected Follower(String name, int maxChannels) {
        this.name        = name;
        this.maxChannels = maxChannels;
        this.channels    = new Channel[maxChannels];
        this.numChannels = 0;
    }

    protected abstract String getPrefix();

    // add and  remove Channel
    void addChannel(Channel c) {
        if (numChannels >= maxChannels) return;
        for (int i = 0; i < numChannels; i++) {
            if (channels[i] == c) return;
        }
        channels[numChannels++] = c;
    }

    void removeChannel(Channel c) {
        int idx = -1;
        for (int i = 0; i < numChannels; i++) {
            if (channels[i] == c) { idx = i; break; }
        }
        if (idx < 0) return;
        for (int i = idx; i < numChannels - 1; i++) {
            channels[i] = channels[i + 1];
        }
        channels[--numChannels] = null;
    }

    public String getName() {
        return name;
    }

    // how many channels they currently follow 
    public int getNumChannels() {
        return numChannels;
    }

    // fresh array of length numChannels with those Channel objects 
    public Channel[] getChannels() {
        Channel[] copy = new Channel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            copy[i] = channels[i];
        }
        return copy;
    }
}
