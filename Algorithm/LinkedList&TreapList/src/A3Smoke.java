import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Random;

public class A3Smoke {

    // --- helpers to write/read raw int files ---

    static void writeInts(String file, int[] a) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (int v : a) out.writeInt(v);
        }
    }

    static int[] readInts(String file) throws IOException {
        long n = Files.size(Path.of(file)) / 4;
        int[] a = new int[(int) n];
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            for (int i = 0; i < a.length; i++) a[i] = in.readInt();
        }
        return a;
    }

    static boolean isSortedFile(String file) throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            if (Files.size(Path.of(file)) == 0) return true;
            int prev = in.readInt();
            try {
                while (true) {
                    int x = in.readInt();
                    if (x < prev) return false;
                    prev = x;
                }
            } catch (EOFException eof) { return true; }
        }
    }

    public static void main(String[] args) throws Exception {
        // 1) tiny edge cases
        runCase(new int[] {}, "empty");
        runCase(new int[] {42}, "single");
        runCase(new int[] {3,1,2}, "small");
        runCase(new int[] {5,5,5,5}, "dups");
        runCase(new int[] {-3, 10, 0, -3, 7, 7, 2}, "mixed");

        // 2) randomized medium (e.g., 200_000 ints)
        randomizedCase(200_000, 12345);

        // 3) biggish (optional): 1_000_000 ints if your machine is ok with it
        // randomizedCase(1_000_000, 7);
    }

    private static void runCase(int[] data, String label) throws Exception {
        String in  = "in_" + label + ".bin";
        String out = "out_" + label + ".bin";

        writeInts(in, data);
        A3.mergeSort(in, out);

        // Check: file is sorted and equals Arrays.sort on a copy
        int[] got = readInts(out);
        int[] exp = data.clone(); Arrays.sort(exp);

        if (!Arrays.equals(got, exp)) {
            throw new AssertionError(label + " mismatch: expected " + Arrays.toString(exp)
                    + " but got " + Arrays.toString(got));
        }
        if (!isSortedFile(out)) {
            throw new AssertionError(label + " file not sorted!");
        }
        System.out.println("[OK] " + label + " → " + got.length + " ints");

        // cleanup
        Files.deleteIfExists(Path.of(in));
        Files.deleteIfExists(Path.of(out));
    }

    private static void randomizedCase(int n, int seed) throws Exception {
        String in  = "in_rand.bin";
        String out = "out_rand.bin";
        int[] a = new int[n];
        Random r = new Random(seed);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();

        writeInts(in, a);

        long t0 = System.nanoTime();
        A3.mergeSort(in, out);
        long t1 = System.nanoTime();

        if (!isSortedFile(out)) throw new AssertionError("random file not sorted!");
        System.out.printf("[OK] random %,d ints → file sort: %.3f s%n", n, (t1 - t0)/1e9);

        // (optional) deep correctness: compare first/last 5 ints with a fully-sorted in-RAM copy
        // Beware: Arrays.sort(a) will use lots of RAM for huge n; OK for n ≤ few millions
        if (n <= 2_000_000) {
            int[] copy = a.clone(); Arrays.sort(copy);
            int[] got  = readInts(out);
            if (got.length != copy.length) throw new AssertionError("length mismatch");
            for (int i = 0; i < 5 && i < got.length; i++) {
                if (got[i] != copy[i]) throw new AssertionError("prefix mismatch at " + i);
                if (got[got.length-1-i] != copy[copy.length-1-i])
                    throw new AssertionError("suffix mismatch at " + (got.length-1-i));
            }
        }

        Files.deleteIfExists(Path.of(in));
        Files.deleteIfExists(Path.of(out));
    }
}
