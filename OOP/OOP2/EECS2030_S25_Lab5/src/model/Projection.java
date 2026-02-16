package model;

public class Projection extends BinarySeqOperation {
    private int[] result;

    public Projection(int[] s1, int[] s2) {
        super(s1, s2);
        int[] a = getSeq1();
        int[] b = getSeq2();

        int count = 0;
        for (int i = 0; i < b.length; i++) {
            if (contains(a, b[i])) count++;
        }

        this.result = new int[count];
        int k = 0;
        for (int i = 0; i < b.length; i++) {
            if (contains(a, b[i])) {
                this.result[k] = b[i];
                k++;
            }
        }
    }

    public boolean isSequenceResult() { return true; }
    public boolean isBooleanResult()  { return false; }
    public int[] getSequenceResult()  { return copy(this.result); }
    public boolean getBooleanResult() { return false; }

    @Override
    public String toString() {
        return "Projecting " + arrayToString(getSeq1()) +
               " to " + arrayToString(getSeq2()) +
               " results in: " + arrayToString(this.result);
    }
}
