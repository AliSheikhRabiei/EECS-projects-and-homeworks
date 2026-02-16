import java.util.Arrays;

public class HeapDemo {
    public static void main(String[] args) {
        int[] data = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};

        System.out.println("Original array:");
        System.out.println(Arrays.toString(data));

        MaxHeap heap = new MaxHeap(data);

        System.out.println("\nHeap as array:");
        heap.printAsArray();

        System.out.println("\nHeap as tree:");
        heap.printAsTree();

        System.out.println("\nHEAP-MAXIMUM:");
        System.out.println(heap.heapMaximum());

        System.out.println("\nHEAP-EXTRACT-MAX:");
        System.out.println(heap.heapExtractMax());

        System.out.println("\nHeap after extract-max (array):");
        heap.printAsArray();

        System.out.println("\nInsert 15:");
        heap.maxHeapInsert(15);

        System.out.println("\nHeap after insert (tree):");
        heap.printAsTree();

        int[] toSort = {5, 1, 4, 2, 8, 0, 3, 3, -2};
        System.out.println("\nBefore heapsort:");
        System.out.println(Arrays.toString(toSort));

        MaxHeap.heapSort(toSort);

        System.out.println("After heapsort:");
        System.out.println(Arrays.toString(toSort));
    }
}
