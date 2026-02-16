package model;

public abstract class SeqEvaluator {
    private SeqOperation[] ops;
    private int size;

    public SeqEvaluator(int capacity) {
        this.ops = new SeqOperation[capacity];
        this.size = 0;
    }

    public void addOperation(String op, int[] seq1, int[] seq2)
            throws IllegalOperationException {
        SeqOperation toAdd = null;

        if ("op:projection".equals(op)) {
            toAdd = new Projection(seq1, seq2);
        } else if ("op:occursWithin".equals(op)) {
            toAdd = new OccursWithin(seq1, seq2);
        } else if ("op:sumsOfPrefixes".equals(op)) {
            toAdd = new SumsOfPrefixes(seq1);
        } else {
            throw new IllegalOperationException();
        }

        this.ops[this.size] = toAdd;
        this.size++;
    }

    protected int getSize() { return this.size; }
    protected SeqOperation getOp(int i) { return this.ops[i]; }

    protected static String arrayToString(int[] a) {
        return SeqOperation.arrayToString(a);
    }
}
