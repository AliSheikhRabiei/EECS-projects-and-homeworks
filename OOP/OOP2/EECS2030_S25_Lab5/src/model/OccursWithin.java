package model;

public class OccursWithin extends BinarySeqOperation {
    private boolean occurs;

    public OccursWithin(int[] s1, int[] s2) {
        super(s1, s2);
        int[] a = getSeq1(); 
        int[] b = getSeq2();

        if (a.length == 0) {               
            this.occurs = true;
            return;
        }
        if (b.length < a.length) {
            this.occurs = false;
            return;
        }

        boolean found = false;
        for (int i = 0; i <= b.length - a.length && !found; i++) {
            int j = 0;
            while (j < a.length && b[i + j] == a[j]) j++;
            if (j == a.length) found = true;
        }
        this.occurs = found;
    }

    public boolean isSequenceResult() { return false; }
    public boolean isBooleanResult()  { return true; }
    public int[] getSequenceResult()  { return null; }
    public boolean getBooleanResult() { return this.occurs; }

    @Override
    public String toString() {
        String mid = this.occurs ? " occurs within " : " does not occur within ";
        return arrayToString(getSeq1()) + mid + arrayToString(getSeq2());
    }
}
