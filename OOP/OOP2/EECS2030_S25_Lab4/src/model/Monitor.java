package model;

public class Monitor extends Follower {
    private int[]   viewCounts;
    private int[]   maxTimes;
    private double[] totalTimes;

    public Monitor(String name, int maxChannels) {
        super(name, maxChannels);
        this.viewCounts = new int[maxChannels];
        this.maxTimes   = new int[maxChannels];
        this.totalTimes = new double[maxChannels];
    }

    @Override
    protected String getPrefix() {
        return "Monitor";
    }

    void recordWatch(Channel chan, int minutes) {
//        int idx = -1;
        for (int i = 0; i < numChannels; i++) {
            if (channels[i] == chan) {
//                idx = i;
                viewCounts[i]++;
                if (minutes > maxTimes[i]) maxTimes[i] = minutes;
                totalTimes[i] += minutes;
                break;
            }
        }
//        if (idx < 0) return;

//        viewCounts[idx]++;
//        if (minutes > maxTimes[idx]) maxTimes[idx] = minutes;
//        totalTimes[idx] += minutes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPrefix()).append(" ").append(name)
          .append(" follows ");
        if (numChannels == 0) {
            sb.append("no channels.");
        } else {
            sb.append("[");
            for (int i = 0; i < numChannels; i++) {
                sb.append(channels[i].getName());
                if (viewCounts[i] > 0) {
                    double avg = totalTimes[i] / viewCounts[i];
                    sb.append(" {#views: ")
                      .append(viewCounts[i])
                      .append(", max watch time: ")
                      .append(maxTimes[i])
                      .append(", avg watch time: ")
                      .append(String.format("%.2f", avg))
                      .append("}");
                }
                if (i < numChannels - 1) sb.append(", ");
            }
            sb.append("].");
        }
        return sb.toString();
    }
}
