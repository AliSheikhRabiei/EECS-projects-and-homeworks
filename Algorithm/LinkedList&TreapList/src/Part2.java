import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public class Part2 {

    private static final long MAX_TOTAL_NANOS = 120L * 1_000_000_000L; // around 2 minutes
    private static final boolean VERIFY_FILE_SORTED = true;            //set false to skip checks
    private static final boolean WARMUP = true;                         //JIT warmup on small n
    private static final long RNG_SEED = 42L;                           //reproducible data
    private static final int START_N = 1_024;
    private static final int MAX_N = 10_000_000;                        //hard cap just in case

    public static void main(String[] args) throws Exception {
        System.out.println("File merge sort vs Java Arrays.sort");
        System.out.println("(times include only the sort calls; data generation excluded)");
        System.out.println();
        System.out.printf("%12s  %16s  %16s%n", "n", "File merge sort", "Java merge sort");
        System.out.println("----------------------------------------------------------------");

        long total = 0L;

        if (WARMUP) {
            warmup();
        }

        int n = START_N;
        while (n <= MAX_N && total < MAX_TOTAL_NANOS) {
            //Make random data once, both in-memory array and file from the same values
            int[] data = null;
            try {
                data = new int[n];
            } catch (OutOfMemoryError oom) {
                System.out.printf("OOM at n=%d — stopping.%n", n);
                break;
            }

            Random rnd = new Random(RNG_SEED ^ n); //different per n, deterministic
            for (int i = 0; i < n; i++) data[i] = rnd.nextInt();

            String inFile = "a3_in_" + n + ".bin";
            String outFile = "a3_out_" + n + ".bin";
            writeInts(inFile, data);

            //File merge sort timing
            long t0 = System.nanoTime();
            A3.mergeSort(inFile, outFile);
            long t1 = System.nanoTime();
            long fileNs = t1 - t0;

            if (VERIFY_FILE_SORTED) {
                assertSortedFile(outFile, n);
            }

            //Java merge sort timing
            int[] copy = data.clone();
            long t2 = System.nanoTime();
            Arrays.sort(copy);
            long t3 = System.nanoTime();
            long javaNs = t3 - t2;

            //Print row
            System.out.printf("%12d  %14.3f ms  %14.3f ms%n",
                    n, fileNs / 1e6, javaNs / 1e6);

            total += fileNs + javaNs;

            //Clean up files (A3 cleans its own temps)
            try { Files.deleteIfExists(Path.of(inFile)); } catch (Exception ignored) {}
            try { Files.deleteIfExists(Path.of(outFile)); } catch (Exception ignored) {}

            // Next n (double)
            n = (n < 0x40000000) ? n << 1 : Integer.MAX_VALUE; //to avoid overflow
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("Done. Total time: %.1f s%n", total / 1e9);
    }

    //helpers
    private static void warmup() throws Exception {
        int n = 4096;
        int[] data = new int[n];
        Random rnd = new Random(RNG_SEED);
        for (int i = 0; i < n; i++) data[i] = rnd.nextInt();
        writeInts("warm_in.bin", data);
        A3.mergeSort("warm_in.bin", "warm_out.bin");
        Arrays.sort(data);
        try { Files.deleteIfExists(Path.of("warm_in.bin")); } catch (Exception ignored) {}
        try { Files.deleteIfExists(Path.of("warm_out.bin")); } catch (Exception ignored) {}
    }

    private static void writeInts(String file, int[] values) throws Exception {
        try (DataOutputStream out = new DataOutputStream(
                new java.io.BufferedOutputStream(new FileOutputStream(file), 1 << 16))) {
            for (int v : values) out.writeInt(v);
        }
    }

    //Linear check for non decreasing and count == expected.
    private static void assertSortedFile(String file, int expectedCount) throws Exception {
        int count = 0;
        boolean first = true;
        int prev = 0;
        try (DataInputStream in = new DataInputStream(
                new java.io.BufferedInputStream(new FileInputStream(file), 1 << 16))) {
            while (true) {
                int x = in.readInt();
                if (!first && x < prev)
                    throw new AssertionError("Not sorted at index " + count + ": " + prev + " > " + x);
                prev = x; first = false; count++;
            }
        } catch (EOFException eof) {
            // ok!
        }
        if (count != expectedCount) {
            throw new AssertionError("Count mismatch: expected " + expectedCount + ", got " + count);
        }
    }
}
