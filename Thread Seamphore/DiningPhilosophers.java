import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    static Semaphore chopstick[] = new Semaphore[5];

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++)
            chopstick[i] = new Semaphore(1);

        for (int i = 0; i < 5; i++) {
            int philosopher = i;
            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("Philosopher " + philosopher + " thinking...");
                        Thread.sleep(500);

                        // Pick left chopstick
                        chopstick[philosopher].acquire();

                        // Pick right chopstick
                        chopstick[(philosopher + 1) % 5].acquire();

                        System.out.println("Philosopher " + philosopher + " eating...");
                        Thread.sleep(500);

                        // Release both
                        chopstick[philosopher].release();
                        chopstick[(philosopher + 1) % 5].release();
                    }
                } catch (Exception e) {}
            }).start();
        }
    }
}
