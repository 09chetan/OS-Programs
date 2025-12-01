import java.util.*;

public class LRUPageReplacement {
    public static void main(String[] args) {
        int[] pages = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
        int capacity = 3;

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("LRU Page Replacement Process:\n");

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == capacity) {
                    frames.remove(0); // Remove least recently used
                }
                frames.add(page);
                pageFaults++;
            } else {
                frames.remove(Integer.valueOf(page)); // Move to most recent
                frames.add(page);
            }
            System.out.println("Frames: " + frames);
        }

        System.out.println("\nTotal Page Faults (LRU): " + pageFaults);
    }
}
