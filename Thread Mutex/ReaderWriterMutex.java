public class ReaderWriterMutex {

    static int readCount = 0;       // how many readers currently reading
    static boolean writing = false; // is any writer writing?
    static final Object mutex = new Object();

    // Reader Thread
    static class Reader extends Thread {
        String name;

        Reader(String name) {
            this.name = name;
        }

        public void run() {
            synchronized (mutex) {
                // Reader waits if writer is writing
                while (writing) {
                    try { mutex.wait(); } catch (InterruptedException e) {}
                }

                readCount++;
                System.out.println(name + " started reading. Total readers = " + readCount);
            }

            // simulate reading
            try { Thread.sleep(800); } catch (InterruptedException e) {}

            synchronized (mutex) {
                readCount--;
                System.out.println(name + " finished reading. Remaining readers = " + readCount);

                if (readCount == 0)
                    mutex.notifyAll(); // notify writer waiting
            }
        }
    }

    // Writer Thread
    static class Writer extends Thread {
        String name;

        Writer(String name) {
            this.name = name;
        }

        public void run() {
            synchronized (mutex) {
                // Writer waits if:
                // 1. Someone writing
                // 2. Any reader reading
                while (writing || readCount > 0) {
                    try { mutex.wait(); } catch (InterruptedException e) {}
                }

                writing = true;
                System.out.println(name + " started writing...");
            }

            // simulate writing
            try { Thread.sleep(1500); } catch (InterruptedException e) {}

            synchronized (mutex) {
                writing = false;
                System.out.println(name + " finished writing.");

                mutex.notifyAll(); // wake up readers & writers
            }
        }
    }

    public static void main(String[] args) {

        // create threads
        new Reader("Reader 1").start();
        new Reader("Reader 2").start();
        new Writer("Writer 1").start();
        new Reader("Reader 3").start();
        new Writer("Writer 2").start();
    }
}
