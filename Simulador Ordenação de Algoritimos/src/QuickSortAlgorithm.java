import java.util.ArrayList;

public class QuickSortAlgorithm implements SortingAlgorithm {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();
    private int swapCount = 0;

    public QuickSortAlgorithm(int[] array) {
        this.array = array.clone();
    }

    @Override
    public void sortAndCapture() {
        
        captureState();
        quickSort(0, array.length - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            captureState(); 
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;

            swapCount++;
            captureState(); 
        }
    }

    private void captureState() {
        frames.add(array.clone());
        swapCounts.add(swapCount);
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
