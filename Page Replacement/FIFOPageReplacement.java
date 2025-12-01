import java.util.*;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        int[] pages = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1}; // Reference string
        int capacity = 3; // Number of frames

        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();

        int pageFaults = 0;

        for (int page : pages) {
            if (!set.contains(page)) {
                if (set.size() == capacity) {
                    int removed = queue.poll();
                    set.remove(removed);
                }
                set.add(page);
                queue.add(page);
                pageFaults++;
            }
            System.out.println("Frames: " + queue);
        }

        System.out.println("\nTotal Page Faults (FIFO): " + pageFaults);
    }
}
