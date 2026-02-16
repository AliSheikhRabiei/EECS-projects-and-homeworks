package model;

public class SumsOfPrefixes extends SeqOperation {
    private int[] input;
    private int[] sums;

    public SumsOfPrefixes(int[] seq) {
        this.input = copy(seq);
        int n = this.input == null ? 0 : this.input.length;
        this.sums = new int[n + 1];
        int running = 0;
        this.sums[0] = 0;
        for (int i = 0; i < n; i++) {
            running += this.input[i];
            this.sums[i + 1] = running;
        }
    }

    public boolean isSequenceResult() { return true; }
    public boolean isBooleanResult()  { return false; }
    public int[] getSequenceResult()  { return copy(this.sums); }
    public boolean getBooleanResult() { return false; }

    @Override
    public String toString() {
        return "Sums of prefixes of " + arrayToString(this.input) +
               " is: " + arrayToString(this.sums);
    }
}
