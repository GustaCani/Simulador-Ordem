import java.util.ArrayList;

public class HeapSortAlgorithm implements SortingAlgorithm {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();
    private int swapCount = 0;

    public HeapSortAlgorithm(int[] array) {
        this.array = array.clone();
    }

    @Override
    public void sortAndCapture() {
        int n = array.length;

        
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }

        
        for (int i = n - 1; i > 0; i--) {
            swap(0, i);
            heapify(i, 0);
        }
    }

    private void heapify(int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        
        if (largest != i) {
            swap(i, largest);
            heapify(n, largest);
        }
    }

    private void swap(int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;

            swapCount++;
            frames.add(array.clone());
            swapCounts.add(swapCount);
        }
    }

    @Override
    public ArrayList<int[]> getFrames() {
        return frames;
    }

    @Override
    public ArrayList<Integer> getSwapCounts() {
        return swapCounts;
    }
}
