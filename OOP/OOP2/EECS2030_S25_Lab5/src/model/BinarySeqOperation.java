package model;

public abstract class BinarySeqOperation extends SeqOperation {
    private int[] seq1;
    private int[] seq2;

    protected BinarySeqOperation(int[] s1, int[] s2) {
        this.seq1 = copy(s1);
        this.seq2 = copy(s2);
    }

    protected int[] getSeq1() { return copy(this.seq1); }
    protected int[] getSeq2() { return copy(this.seq2); }

    protected static boolean contains(int[] a, int v) {
        if (a == null) return false;
        for (int i = 0; i < a.length; i++) if (a[i] == v) return true;
        return false;
    }
}
