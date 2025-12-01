import java.util.concurrent.Semaphore;

class ProducerConsumer {
    static int buffer[] = new int[5];  // Buffer size = 5
    static int index = 0;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(5); 
    static Semaphore full = new Semaphore(0);

    static class Producer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    empty.acquire();      // Wait for empty space
                    mutex.acquire();      // Enter critical section

                    buffer[index++] = i;
                    System.out.println("Producer produced: " + i);

                    mutex.release();  
                    full.release();       // Increase filled count
                    Thread.sleep(300);
                }
            } catch (Exception e) {}
        }
    }

    static class Consumer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    full.acquire();       // Wait if no item
                    mutex.acquire();

                    int item = buffer[--index];
                    System.out.println("Consumer consumed: " + item);

                    mutex.release();
                    empty.release();      // Increase empty count
                    Thread.sleep(500);
                }
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }
}
