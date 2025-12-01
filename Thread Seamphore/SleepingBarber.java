import java.util.concurrent.Semaphore;

public class SleepingBarber {

    static final int CHAIRS = 3;    // Waiting chairs
    static int freeSeats = CHAIRS;

    static Semaphore barberReady = new Semaphore(0);
    static Semaphore customerReady = new Semaphore(0);
    static Semaphore accessSeats = new Semaphore(1);

    // Barber Thread
    static class Barber extends Thread {
        public void run() {
            while (true) {
                try {
                    customerReady.acquire();     // Wait for a customer
                    accessSeats.acquire();        // Lock seats
                    freeSeats++;                  // One seat becomes free
                    barberReady.release();        // Barber ready to cut
                    accessSeats.release();

                    System.out.println("Barber is cutting hair...");
                    Thread.sleep(1000);           // Time to cut hair

                } catch (Exception e) {}
            }
        }
    }

    // Customer Thread
    static class Customer extends Thread {
        String name;

        Customer(String name) {
            this.name = name;
        }

        public void run() {
            try {
                accessSeats.acquire();

                if (freeSeats > 0) {
                    System.out.println(name + " arrived and sitting in waiting area.");
                    freeSeats--;

                    customerReady.release();       // Notify barber
                    accessSeats.release();

                    barberReady.acquire();         // Wait for barber
                    System.out.println(name + " getting haircut.");

                } else {
                    System.out.println(name + " found no seat and left the shop!");
                    accessSeats.release();
                }
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        new Barber().start();

        // Simulate customers arriving
        for (int i = 1; i <= 10; i++) {
            new Customer("Customer " + i).start();
            try {
                Thread.sleep(500); // Customers coming at intervals
            } catch (Exception e) {}
        }
    }
}
