import java.util.Arrays;

public class MaxHeap {
    private int[] A;        
    private int heapSize;

    // Build heap from an arbitrary array 
    public MaxHeap(int[] input) {
        this.A = new int[input.length + 1]; // A[0] unused
        this.heapSize = input.length;

        for (int i = 0; i < input.length; i++) {
            A[i + 1] = input[i];
        }
        buildMaxHeap();
    }

    // Empty heap with capacity
    public MaxHeap(int capacity) {
        this.A = new int[capacity + 1]; // A[0] unused
        this.heapSize = 0;
    }

    //index helpers 
    private int parent(int i) { return i / 2; }
    private int left(int i) { return 2 * i; }
    private int right(int i) { return 2 * i + 1; }

    //MAX-HEAPIFY (iterative)
    public void maxHeapify(int i) {
        while (true) {
            int l = left(i);
            int r = right(i);
            int largest = i;

            if (l <= heapSize && A[l] > A[largest]) largest = l;
            if (r <= heapSize && A[r] > A[largest]) largest = r;

            if (largest == i) return;

            swap(i, largest);
            i = largest;
        }
    }

    //BUILD-MAX-HEAP
    public void buildMaxHeap() {
        for (int i = heapSize / 2; i >= 1; i--) {
            maxHeapify(i);
        }
    }

    // HEAP-MAXIMUM 
    public int heapMaximum() {
        if (heapSize < 1) throw new IllegalStateException("Heap underflow");
        return A[1];
    }

    // HEAP-EXTRACT-MAX 
    public int heapExtractMax() {
        if (heapSize < 1) throw new IllegalStateException("Heap underflow");
        int max = A[1];
        A[1] = A[heapSize];
        heapSize--;
        if (heapSize >= 1) maxHeapify(1);
        return max;
    }

    // increase key (bubble up)
    private void heapIncreaseKey(int i, int key) {
        if (key < A[i]) throw new IllegalArgumentException("new key is smaller than current key");
        A[i] = key;
        while (i > 1 && A[parent(i)] < A[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // MAX-HEAP-INSERT 
    public void maxHeapInsert(int key) {
        ensureCapacity(heapSize + 1);
        heapSize++;
        A[heapSize] = Integer.MIN_VALUE; 
        heapIncreaseKey(heapSize, key);
    }

    // printAsArray 
    public void printAsArray() {
        int[] view = new int[heapSize];
        for (int i = 1; i <= heapSize; i++) view[i - 1] = A[i];
        System.out.println(Arrays.toString(view));
    }

    // printAsTree (sideways)
    // Right subtree first, then node, then left subtree.
    public void printAsTree() {
        printAsTreeRec(1, 0);
    }

    private void printAsTreeRec(int i, int depth) {
        if (i > heapSize) return;

        int r = right(i);
        int l = left(i);

        printAsTreeRec(r, depth + 1);

        for (int k = 0; k < depth; k++) System.out.print("  ");
        System.out.println(A[i]);

        printAsTreeRec(l, depth + 1);
    }

    // Heapsort in place
    public static void heapSort(int[] arr) {
        // Copy arr into 
        int[] A = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) A[i + 1] = arr[i];

        int heapSize = arr.length;

        // BUILD MAX HEAP
        for (int i = heapSize / 2; i >= 1; i--) {
            maxHeapifyStatic(A, heapSize, i);
        }

        // HEAPSORT loop
        for (int i = arr.length; i >= 2; i--) {
            swapStatic(A, 1, i);
            heapSize--;
            maxHeapifyStatic(A, heapSize, 1);
        }

        for (int i = 0; i < arr.length; i++) arr[i] = A[i + 1];
    }

    // static heapify helper for heapsort
    private static void maxHeapifyStatic(int[] A, int heapSize, int i) {
        while (true) {
            int l = 2 * i;
            int r = 2 * i + 1;
            int largest = i;

            if (l <= heapSize && A[l] > A[largest]) largest = l;
            if (r <= heapSize && A[r] > A[largest]) largest = r;

            if (largest == i) return;

            swapStatic(A, i, largest);
            i = largest;
        }
    }

    private static void swapStatic(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    private void swap(int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    private void ensureCapacity(int neededHeapSize) {
        if (neededHeapSize < A.length) return;
        A = Arrays.copyOf(A, A.length * 2);
    }

    public int size() {
        return heapSize;
    }
}
