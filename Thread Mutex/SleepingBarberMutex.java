import java.util.LinkedList;
import java.util.Queue;

public class SleepingBarberMutex {

    static final int CHAIRS = 3;     // waiting chairs
    static int freeSeats = CHAIRS;   // shared variable

    static final Object mutex = new Object();   // our lock
    static Queue<String> waitingRoom = new LinkedList<>();

    // Barber Thread
    static class Barber extends Thread {
        public void run() {
            while (true) {
                synchronized (mutex) {
                    while (waitingRoom.isEmpty()) {
                        System.out.println("Barber is sleeping...");
                        try {
                            mutex.wait();  // Barber sleeps
                        } catch (InterruptedException e) {}
                    }

                    // Customer available
                    String customer = waitingRoom.poll();
                    freeSeats++;
                    System.out.println("Barber is cutting hair of " + customer);
                }

                // Simulating haircut time
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
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
            synchronized (mutex) {
                if (freeSeats > 0) {
                    freeSeats--;
                    waitingRoom.add(name);
                    System.out.println(name + " is waiting.");

                    mutex.notify(); // Wake up barber
                } else {
                    System.out.println(name + " found no seats and left!");
                }
            }
        }
    }

    public static void main(String[] args) {
        new Barber().start();

        // simulate customers arriving
        for (int i = 1; i <= 10; i++) {
            new Customer("Customer " + i).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
    }
}
