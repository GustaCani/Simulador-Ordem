import java.util.ArrayList;

public class InsertionSortAlgorithm implements SortingAlgorithm {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();
    private ArrayList<Integer> comparisonCounts = new ArrayList<>();

    public InsertionSortAlgorithm(int[] array) {
        this.array = array.clone();
    }

    @Override
    public void sortAndCapture() {
        int swapCount = 0;
        int comparisonCount = 0;

        
        frames.add(array.clone());
        swapCounts.add(swapCount);
        comparisonCounts.add(comparisonCount);

        for (int i = 1; i < array.length; i++) {
            int j = i;
            while (j > 0) {
                comparisonCount++;
                if (array[j] < array[j - 1]) {
                    int aux = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = aux;
                    swapCount++;
                    j--;
                    
                    frames.add(array.clone());
                    swapCounts.add(swapCount);
                    comparisonCounts.add(comparisonCount);
                } else {
                    frames.add(array.clone());
                    swapCounts.add(swapCount);
                    comparisonCounts.add(comparisonCount);
                    break;
                }
            }
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

    public ArrayList<Integer> getComparisonCounts() {
        return comparisonCounts;
    }
}
