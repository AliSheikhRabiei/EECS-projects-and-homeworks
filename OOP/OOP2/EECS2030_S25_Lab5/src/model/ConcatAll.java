package model;

public class ConcatAll extends SeqEvaluator {
    public ConcatAll(int capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        int n = getSize();
        int incompatible = 0;

        int total = 0;
        int[][] parts = new int[n][];
        for (int i = 0; i < n; i++) {
            SeqOperation op = getOp(i);
            if (op.isSequenceResult()) {
                parts[i] = op.getSequenceResult();
                total += (parts[i] == null ? 0 : parts[i].length);
            } else {
                incompatible++;
            }
        }

        if (incompatible > 0) {
            return "Concat cannot be evaluated due to " + incompatible + " incompatile operations.";
        }

        int[] concat = new int[total];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int[] p = parts[i];
            for (int j = 0; j < p.length; j++) {
                concat[k++] = p[j];
            }
        }

        String s = "Concat(";
        for (int i = 0; i < n; i++) {
            if (i > 0) s += ", ";
            s += arrayToString(parts[i]);
        }
        s += ") = " + arrayToString(concat);
        return s;
    }
}
