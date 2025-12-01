import java.util.Scanner;

class SJF_NonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int pid[] = new int[n];  // Process IDs
        int at[] = new int[n];   // Arrival Times
        int bt[] = new int[n];   // Burst Times
        int ct[] = new int[n];   // Completion Times
        int tat[] = new int[n];  // Turnaround Times
        int wt[] = new int[n];   // Waiting Times
        boolean done[] = new boolean[n]; // Track finished processes

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter arrival time of process " + pid[i] + ": ");
            at[i] = sc.nextInt();
            System.out.print("Enter burst time of process " + pid[i] + ": ");
            bt[i] = sc.nextInt();
        }

        int time = 0, completed = 0;
        while (completed < n) {
            int idx = -1;
            int minBT = Integer.MAX_VALUE;

            // Find process with minimum burst time among arrived ones
            for (int i = 0; i < n; i++) {
                if (!done[i] && at[i] <= time && bt[i] < minBT) {
                    minBT = bt[i];
                    idx = i;
                }
            }

            if (idx == -1) {
                time++; // No process arrived yet
            } else {
                // Execute the chosen process
                time += bt[idx];
                ct[idx] = time;
                tat[idx] = ct[idx] - at[idx];
                wt[idx] = tat[idx] - bt[idx];
                done[idx] = true;
                completed++;
            }
        }

        // Output results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        // Averages
        float avgTAT = 0, avgWT = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += tat[i];
            avgWT += wt[i];
        }
        System.out.println("\nAverage Turnaround Time = " + (avgTAT / n));
        System.out.println("Average Waiting Time = " + (avgWT / n));
    }
}
