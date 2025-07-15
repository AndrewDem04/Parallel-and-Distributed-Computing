import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinMergeSort {
    public static void printArray(int[] arr) {
        for (int num : arr) System.out.print(num + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("Given array is:");
        printArray(arr);

        ForkJoinPool pool = new ForkJoinPool();
        RecursiveMergeSortTask task = new RecursiveMergeSortTask(arr, 0, arr.length - 1);
        pool.invoke(task);

        System.out.println("\nSorted array is:");
        printArray(arr);
    }
}

class RecursiveMergeSortTask extends RecursiveAction {
    private int[] arr;
    private int l, r;

    public RecursiveMergeSortTask(int[] arr, int l, int r) {
        this.arr = arr;
        this.l = l;
        this.r = r;
    }

    @Override
    protected void compute() {
        if (l < r) {
            int m = l + (r - l) / 2;

            RecursiveMergeSortTask leftTask = new RecursiveMergeSortTask(arr, l, m);
            RecursiveMergeSortTask rightTask = new RecursiveMergeSortTask(arr, m + 1, r);

            invokeAll(leftTask, rightTask);

            merge(arr, l, m, r);
        }
    }

    private void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
}

