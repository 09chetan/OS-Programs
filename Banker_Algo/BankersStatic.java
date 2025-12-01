
import java.util.*;

public class BankersStatic {

    public static void main(String[] args) {

        // -------- STATIC DATA --------
        int processes = 5;
        int resources = 3;

        String[] processNames = {"P1", "P2", "P3", "P4", "P5"};

        int[][] allocation = {
                {0, 1, 0}, // P1
                {2, 0, 0}, // P2
                {3, 0, 2}, // P3
                {2, 1, 1}, // P4
                {0, 0, 2}  // P5
        };

        int[][] max = {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {4, 2, 2},
                {5, 3, 3}
        };

        // Total resources given
        int[] total = {10, 5, 7};

        // -------- CALCULATE AVAILABLE --------
        int[] available = new int[resources];
        int[] allocatedSum = new int[resources];

        for (int j = 0; j < resources; j++) {
            for (int i = 0; i < processes; i++) {
                allocatedSum[j] += allocation[i][j];
            }
            available[j] = total[j] - allocatedSum[j];
        }

        // -------- CALCULATE NEED MATRIX --------
        int[][] need = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // PRINT TABLES
        System.out.println("\n===== GIVEN TABLE (Auto Calculated) =====\n");
        System.out.println("Process |   Allocation     |    Max Need     |   Need (Remaining)");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < processes; i++) {
            System.out.printf("%-7s |   %d %3d %3d      |    %d %3d %3d    |    %d %3d %3d\n",
                    processNames[i],
                    allocation[i][0], allocation[i][1], allocation[i][2],
                    max[i][0], max[i][1], max[i][2],
                    need[i][0], need[i][1], need[i][2]
            );
        }

        System.out.println("\nTotal Resources     :  " + Arrays.toString(total));
        System.out.println("Allocated Resources :  " + Arrays.toString(allocatedSum));
        System.out.println("Available Resources :  " + Arrays.toString(available));

        // -------- SAFE SEQUENCE CHECK --------
        boolean[] finished = new boolean[processes];
        int[] safeSequence = new int[processes];
        int count = 0;

        int[] work = Arrays.copyOf(available, resources);

        while (count < processes) {
            boolean found = false;

            for (int p = 0; p < processes; p++) {
                if (!finished[p]) {

                    int j;
                    for (j = 0; j < resources; j++) {
                        if (need[p][j] > work[j]) {
                            break;
                        }
                    }

                    if (j == resources) {
                        // Allocate resources
                        for (int k = 0; k < resources; k++) {
                            work[k] += allocation[p][k];
                        }
                        safeSequence[count++] = p;
                        finished[p] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("\n❌ System is NOT in a SAFE STATE!");
                return;
            }
        }

        // PRINT SAFE SEQUENCE
        System.out.println("\n===== SAFE STATE =====");
        System.out.print("Safe Sequence: ");
        for (int i = 0; i < processes; i++) {
            System.out.print(processNames[safeSequence[i]] + " ");
        }
        System.out.println();
    }
}