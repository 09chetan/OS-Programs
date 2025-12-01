import java.util.Scanner;

class Priority_Preemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int pid[] = new int[n];   // Process IDs
        int at[] = new int[n];    // Arrival Times
        int bt[] = new int[n];    // Burst Times
        int rt[] = new int[n];    // Remaining Times
        int pr[] = new int[n];    // Priorities
        int ct[] = new int[n];    // Completion Times
        int tat[] = new int[n];   // Turnaround Times
        int wt[] = new int[n];    // Waiting Times

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter arrival time of process " + pid[i] + ": ");
            at[i] = sc.nextInt();
            System.out.print("Enter burst time of process " + pid[i] + ": ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];  // Initially remaining time = burst time
            System.out.print("Enter priority of process " + pid[i] + " (higher number = higher priority): ");
            pr[i] = sc.nextInt();
        }

        int time = 0, completed = 0;

        while (completed < n) {
            int idx = -1;
            int bestPr = Integer.MIN_VALUE;  // because higher number = higher priority

            // Find process with highest priority (highest number)
            for (int i = 0; i < n; i++) {
                if (at[i] <= time && rt[i] > 0 && pr[i] > bestPr) {
                    bestPr = pr[i];
                    idx = i;
                }
            }

            if (idx == -1) {
                time++;
            } else {
                rt[idx]--;  // Run process for 1 time unit
                time++;

                if (rt[idx] == 0) { // Process finished
                    completed++;
                    ct[idx] = time;
                    tat[idx] = ct[idx] - at[idx];
                    wt[idx] = tat[idx] - bt[idx];
                }
            }
        }

        // Output results
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + pr[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
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
