package model;

public abstract class SeqOperation {

    public abstract boolean isSequenceResult();
    public abstract boolean isBooleanResult();

    public abstract int[] getSequenceResult();   
    public abstract boolean getBooleanResult();  

    protected static String arrayToString(int[] a) {
        String s = "[";
        if (a != null && a.length > 0) {
            for (int i = 0; i < a.length; i++) {
                if (i > 0) s += ", ";
                s += a[i];
            }
        }
        s += "]";
        return s;
    }

    protected static int[] copy(int[] a) {
        if (a == null) return null;
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) c[i] = a[i];
        return c;
    }
}
