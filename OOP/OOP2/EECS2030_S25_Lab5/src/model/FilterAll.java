package model;

public class FilterAll extends SeqEvaluator {
    public FilterAll(int capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        int n = getSize();
        int incompatible = 0;

        String[] tokens = new String[n];
        for (int i = 0; i < n; i++) {
            SeqOperation op = getOp(i);
            if (op.isBooleanResult()) {
                tokens[i] = op.getBooleanResult() ? "true" : "_";
            } else {
                incompatible++;
            }
        }

        if (incompatible > 0) {
            return "Filter cannot be evaluated due to " + incompatible + " incompatile operations.";
        }

        String res = "Filter result is: ";
        for (int i = 0; i < n; i++) {
            if (i > 0) res += ", ";
            res += tokens[i];
        }
        return res;
    }
}
