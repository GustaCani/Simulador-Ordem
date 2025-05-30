import java.util.ArrayList;

public class MergeSortAlgorithm implements SortingAlgorithm {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();
    private int swapCount = 0;

    public MergeSortAlgorithm(int[] array) {
        this.array = array.clone();
    }

    @Override
    public void sortAndCapture() {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                swapCount++; 
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
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
