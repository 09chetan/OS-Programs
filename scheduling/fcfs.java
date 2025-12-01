import java.util.Scanner;

 class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int pid[] = new int[n]; // process IDs
        int at[] = new int[n];  // arrival times
        int bt[] = new int[n];  // burst times
        int ct[] = new int[n];  // completion times
        int tat[] = new int[n]; // turnaround times
        int wt[] = new int[n];  // waiting times

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter arrival time of process " + (i + 1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Enter burst time of process " + (i + 1) + ": ");
            bt[i] = sc.nextInt();
        }

        // Calculate Completion, TAT, WT
        int time = 0;
        for (int i = 0; i < n; i++) {
            if (time < at[i]) {
                time = at[i]; // CPU waits if process has not arrived
            }
            time += bt[i];
            ct[i] = time;
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
        }

        // Output results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        // Calculate averages
        float avgTAT = 0, avgWT = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += tat[i];
            avgWT += wt[i];
        }
        System.out.println("\nAverage Turnaround Time = " + (avgTAT / n));
        System.out.println("Average Waiting Time = " + (avgWT / n));
    }
}
