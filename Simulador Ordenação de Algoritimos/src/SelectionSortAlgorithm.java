import java.util.ArrayList;

public class SelectionSortAlgorithm implements SortingAlgorithm {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();

    public SelectionSortAlgorithm(int[] array) {
        this.array = array.clone();
    }

    @Override
    public void sortAndCapture() {
        int swapCount = 0;
        for (int i = 0; i < array.length; i++) {
            int i_menor = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i_menor]) {
                    i_menor = j;
                }
            }
            if (i != i_menor) {
                int aux = array[i];
                array[i] = array[i_menor];
                array[i_menor] = aux;
                swapCount++;
                
                frames.add(array.clone());
                swapCounts.add(swapCount);
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
}
