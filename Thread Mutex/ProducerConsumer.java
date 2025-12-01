import java.util.LinkedList;

class Buffer {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int CAPACITY = 5;

    // Mutex lock is provided by 'synchronized'
    public synchronized void produce(int item) throws InterruptedException {
        while (list.size() == CAPACITY) {
            wait(); // Buffer full → producer waits
        }

        list.add(item);
        System.out.println("Producer produced: " + item);

        notify(); // Notify consumer
    }

    public synchronized int consume() throws InterruptedException {
        while (list.isEmpty()) {
            wait(); // Buffer empty → consumer waits
        }

        int item = list.removeFirst();
        System.out.println("Consumer consumed: " + item);

        notify(); // Notify producer
        return item;
    }
}

class Producer extends Thread {
    Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        int value = 1;
        try {
            while (true) {
                buffer.produce(value++);
                Thread.sleep(500); // Slows down output
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {
                buffer.consume();
                Thread.sleep(800); // Slows consumer
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);

        p.start();
        c.start();
    }
}
