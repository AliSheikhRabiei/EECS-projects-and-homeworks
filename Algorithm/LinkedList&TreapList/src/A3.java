import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
  External natural merge sort for a binary file of 32bit ints.
 One public static method, mergsort; only constant RAM allocations used. 
 */
public final class A3 {

    private A3() {} //it is non instantiable

    /**
     Sorts a binary file of 32bit ints and writes the sorted ints to outputFile.
     Temporary files are created in the working directory and deleted afterwards, 
     or at least they should be deleted!
     */
    public static void mergeSort(String inputFile, String outputFile) {
        if (inputFile == null || outputFile == null)
            throw new IllegalArgumentException("null file name");

        Path in   = Path.of(inputFile);
        Path out  = Path.of(outputFile);
        String salt = Long.toUnsignedString(System.nanoTime(), 36) + "_"
        + Long.toUnsignedString(Thread.currentThread().getId(), 36);
        
        Path a    = Path.of("__a3_tmp_A_"    + salt + ".bin");
        Path b    = Path.of("__a3_tmp_B_"    + salt + ".bin");
        Path tmp0 = Path.of("__a3_tmp_OUT0_" + salt + ".bin");
        Path tmp1 = Path.of("__a3_tmp_OUT1_" + salt + ".bin");
        
        // no input gives empty output
        try {
            if (!Files.exists(in)) { 
                Files.deleteIfExists(out);
                Files.createFile(out);
                return;
            }
            long bytes = Files.size(in);
            if (bytes == 0) {
                Files.deleteIfExists(out);
                Files.createFile(out);
                return;
            }

            Path current = in;    // file we will split in this pass
            boolean flip = false; // choose tmp0/tmp1 alternately as merge destination

            while (true) {
                //split current into natural runs alternating to A and B
                Files.deleteIfExists(a);
                Files.deleteIfExists(b);
                int runCount = splitRuns(current, a, b);

                if (runCount <= 1) {
                    //already sorted; just copy to out
                    if (!current.equals(out)) {
                        Files.deleteIfExists(out);
                        Files.copy(current, out, StandardCopyOption.REPLACE_EXISTING);
                    }
                    break;
                }

                //merge A and B into dst
                Path dst = flip ? tmp1 : tmp0;
                Files.deleteIfExists(dst);
                int mergedRuns = mergeRuns(a, b, dst);

                if (mergedRuns <= 1) {
                    Files.deleteIfExists(out);
                    Files.move(dst, out, StandardCopyOption.REPLACE_EXISTING);
                    break;
                }
                
                if (current.equals(tmp0) || current.equals(tmp1)) {
                    try { Files.deleteIfExists(current); } catch (IOException ignored) {}
                }

                //also delete the other tmp from the previous alternation
                Path otherTmp = dst.equals(tmp0) ? tmp1 : tmp0;
                try { Files.deleteIfExists(otherTmp); } catch (IOException ignored) {}
                
                //Prepare next pass: read from the file we just wrote.
                //Do NOT delete 'current' if it is the original input and keep user input intact.
                if (!current.equals(in) && !current.equals(dst)) {
                    try { Files.deleteIfExists(current); } catch (IOException ignored) {}
                }
                //Also delete the unused tmp from the previous alternation to keep dir tidy.
                Path unusedTmp = flip ? tmp0 : tmp1;
                try { Files.deleteIfExists(unusedTmp); } catch (IOException ignored) {}

                current = dst;
                flip = !flip;
            }

        } catch (IOException io) {
            throw new RuntimeException("I/O error in external merge sort: " + io.getMessage(), io);
        } finally {
            //cleaning up of temps
            try { Files.deleteIfExists(a); }    catch (IOException ignored) {}
            try { Files.deleteIfExists(b); }    catch (IOException ignored) {}
            try { Files.deleteIfExists(tmp0); } catch (IOException ignored) {}
            try { Files.deleteIfExists(tmp1); } catch (IOException ignored) {}
        }
    }

    //it write natural ascending runs to A and B

    private static int splitRuns(Path src, Path outA, Path outB) throws IOException {
        DataInputStream in = null;
        DataOutputStream oa = null, ob = null;
        try {
            in = new DataInputStream(new FileInputStream(src.toFile()));
            oa = new DataOutputStream(new FileOutputStream(outA.toFile()));
            ob = new DataOutputStream(new FileOutputStream(outB.toFile()));
            
            // starting with A
            boolean toA = true;               
            DataOutputStream dst = oa;
            int runsA = 0, runsB = 0;

            int prev;
            try {
                prev = in.readInt();
            } catch (EOFException eof) {
                return 0; // empty
            }

            dst.writeInt(prev);
            runsA++; //first run started in A

            while (true) {
                int x;
                try {
                    x = in.readInt();
                } catch (EOFException eof) {
                    break;
                }

                if (x >= prev) {
                    dst.writeInt(x); //continue current run
                } else {
                    // boundary flip file and start new run
                    toA = !toA;
                    dst = toA ? oa : ob;
                    if (toA) runsA++; else runsB++;
                    dst.writeInt(x);
                }
                prev = x;
            }
            return runsA + runsB;
        } finally {
            closeQuietly(in);
            closeQuietly(oa);
            closeQuietly(ob);
        }
    }

    //merge one run from A with one run from B repeatedly

    private static int mergeRuns(Path inA, Path inB, Path dst) throws IOException {
        RunReader ra = null, rb = null;
        DataOutputStream out = null;
        int outRuns = 0;

        try {
            ra = new RunReader(new DataInputStream(new FileInputStream(inA.toFile())));
            rb = new RunReader(new DataInputStream(new FileInputStream(inB.toFile())));
            out = new DataOutputStream(new FileOutputStream(dst.toFile()));

            while (ra.hasMore() || rb.hasMore()) {
                boolean aActive = ra.startRun();
                boolean bActive = rb.startRun();

                if (!aActive && !bActive) break;
                outRuns++;

                boolean aHas = aActive;
                boolean bHas = bActive;

                while (aHas && bHas) {
                    if (ra.curr <= rb.curr) {
                        out.writeInt(ra.curr);
                        aHas = ra.advanceWithinRun();
                    } else {
                        out.writeInt(rb.curr);
                        bHas = rb.advanceWithinRun();
                    }
                }
                while (aHas) {
                    out.writeInt(ra.curr);
                    aHas = ra.advanceWithinRun();
                }
                while (bHas) {
                    out.writeInt(rb.curr);
                    bHas = rb.advanceWithinRun();
                }
            }
            return outRuns;
        } finally {
            closeQuietly(out);
            if (ra != null) ra.close();
            if (rb != null) rb.close();
        }
    }

    //small helper to read one run at a time with O(1) state

    private static final class RunReader implements Closeable {
        private final DataInputStream in;
        private boolean eof = false;

        int curr;               //current value available to emit
        private int prev;       //last emitted in this run
        private boolean hasCurr = false;

        private boolean hasLook = false; //lookahead when boundary hit
        private int look;

        RunReader(DataInputStream in) { this.in = in; }

        boolean hasMore() { return hasLook || hasCurr || !eof; }

        //Begin a run if possible; leaves curr set.
        boolean startRun() throws IOException {
            if (hasCurr) return true;
            if (hasLook) {
                curr = look; hasLook = false;
                prev = curr; hasCurr = true;
                return true;
            }
            if (eof) return false;
            try {
                curr = in.readInt();
            } catch (EOFException e) {
                eof = true; return false;
            }
            prev = curr; hasCurr = true;
            return true;
        }

        //Advance within current run; returns true if still in same run
        boolean advanceWithinRun() throws IOException {
            if (!hasCurr) return false;
            int x;
            try {
                x = in.readInt();
            } catch (EOFException e) {
                eof = true; hasCurr = false; return false;
            }
            //stash lookahead for next run
            if (x < prev) { 
                look = x; hasLook = true; hasCurr = false;
                return false;
            } else {
                prev = x; curr = x; return true;
            }
        }

        @Override public void close() { closeQuietly(in); }
    }

    private static void closeQuietly(Closeable c) {
        if (c == null) return;
        try { c.close(); } catch (IOException ignored) {}
    }
}
