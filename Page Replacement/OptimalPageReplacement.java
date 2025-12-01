import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        int[] pages = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
        int capacity = 4;
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("Optimal Page Replacement Process:\n");

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            if (!frames.contains(page)) {
                if (frames.size() == capacity) {
                    int indexToReplace = findOptimal(frames, pages, i + 1);
                    frames.set(indexToReplace, page);
                } else {
                    frames.add(page);
                }
                pageFaults++;
            }
            System.out.println("Frames: " + frames);
        }

        System.out.println("\nTotal Page Faults (Optimal): " + pageFaults);
    }

    static int findOptimal(List<Integer> frames, int[] pages, int start) {
        int farthest = start, indexToReplace = 0;
        for (int i = 0; i < frames.size(); i++) {
            int page = frames.get(i);
            int j;
            for (j = start; j < pages.length; j++) {
                if (pages[j] == page) break;
            }
            if (j == pages.length) return i;
            if (j > farthest) {
                farthest = j;
                indexToReplace = i;
            }
        }
        return indexToReplace;
    }
}
