import java.util.Random;

public class TreapListTester2 {

    private static final int[] SIZES = {10, 100, 1_000, 10_000, 100_000, 1_000_000};
    private static final int REPEATS = 3;          // average across repeats
    private static final int GET_SAMPLES = 200_000; // cap reads so runs finish quickly

    public static void main(String[] args) {
        System.out.println("TreapList micro-benchmarks (ns/op, lower is better)");
        benchAdd();
        benchGet();
        benchRemove();
    }

    // ---- ADD ----
    private static void benchAdd() {
        System.out.println("\nADD(index, value)");
        for (int n : SIZES) {
            double front=0, mid=0, end=0, rnd=0;
            for (int r = 0; r < REPEATS; r++) {
                front += timeAdd(n, Mode.FRONT);
                mid   += timeAdd(n, Mode.MIDDLE);
                end   += timeAdd(n, Mode.END);
                rnd   += timeAdd(n, Mode.RANDOM);
            }
            System.out.printf("n=%-8d  front=%8.1f  mid=%8.1f  end=%8.1f  random=%8.1f%n",
                    n, front/REPEATS, mid/REPEATS, end/REPEATS, rnd/REPEATS);
        }
    }

    private static double timeAdd(int n, Mode mode) {
        TreapList<Integer> xs = new TreapList<>();
        Random rnd = new Random(1);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            int idx = switch (mode) {
                case FRONT  -> 0;
                case END    -> xs.size();
                case MIDDLE -> xs.size() / 2;
                case RANDOM -> (xs.size() == 0) ? 0 : rnd.nextInt(xs.size()+1);
            };
            xs.add(idx, i);
        }
        long t1 = System.nanoTime();
        return (t1 - t0) / (double) Math.max(1, n);
    }

    // ---- GET ----
    private static void benchGet() {
        System.out.println("\nGET(index)");
        for (int n : SIZES) {
            TreapList<Integer> xs = prefilled(n);
            Random rnd = new Random(2);
            int reads = Math.min(n, GET_SAMPLES);
            long tFront=0, tMid=0, tEnd=0, tRnd=0;

            long t0 = System.nanoTime();
            for (int i = 0; i < reads; i++) xs.get(0);
            long t1 = System.nanoTime();

            long t2 = System.nanoTime();
            for (int i = 0; i < reads; i++) xs.get(xs.size()/2);
            long t3 = System.nanoTime();

            long t4 = System.nanoTime();
            for (int i = 0; i < reads; i++) xs.get(xs.size()-1);
            long t5 = System.nanoTime();

            long t6 = System.nanoTime();
            for (int i = 0; i < reads; i++) xs.get(rnd.nextInt(xs.size()));
            long t7 = System.nanoTime();

            tFront = (t1 - t0) / Math.max(1, reads);
            tMid   = (t3 - t2) / Math.max(1, reads);
            tEnd   = (t5 - t4) / Math.max(1, reads);
            tRnd   = (t7 - t6) / Math.max(1, reads);

            System.out.printf("n=%-8d  front=%8d  mid=%8d  end=%8d  random=%8d%n",
                    n, tFront, tMid, tEnd, tRnd);
        }
    }

    // ---- REMOVE ----
    private static void benchRemove() {
        System.out.println("\nREMOVE(index)");
        for (int n : SIZES) {
            double front=0, mid=0, end=0, rnd=0;
            for (int r = 0; r < REPEATS; r++) {
                front += timeRemove(n, Mode.FRONT);
                mid   += timeRemove(n, Mode.MIDDLE);
                end   += timeRemove(n, Mode.END);
                rnd   += timeRemove(n, Mode.RANDOM);
            }
            System.out.printf("n=%-8d  front=%8.1f  mid=%8.1f  end=%8.1f  random=%8.1f%n",
                    n, front/REPEATS, mid/REPEATS, end/REPEATS, rnd/REPEATS);
        }
    }

    private static double timeRemove(int n, Mode mode) {
        TreapList<Integer> xs = prefilled(n);
        Random rnd = new Random(3);
        long t0 = System.nanoTime();
        for (int k = 0; k < n; k++) {
            int idx = switch (mode) {
                case FRONT  -> 0;
                case END    -> xs.size()-1;
                case MIDDLE -> xs.size()/2;
                case RANDOM -> rnd.nextInt(xs.size());
            };
            xs.remove(idx);
        }
        long t1 = System.nanoTime();
        return (t1 - t0) / (double) Math.max(1, n);
    }

    private static TreapList<Integer> prefilled(int n) {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < n; i++) xs.add(i);
        return xs;
    }

    private enum Mode { FRONT, MIDDLE, END, RANDOM }
}
