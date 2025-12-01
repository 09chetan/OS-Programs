import java.util.concurrent.Semaphore;

class ReaderWriter {
    static Semaphore mutex = new Semaphore(1);
    static Semaphore write = new Semaphore(1);
    static int readCount = 0;

    static class Reader extends Thread {
        public void run() {
            try {
                // entry
                mutex.acquire();
                readCount++;
                if (readCount == 1)
                    write.acquire();    // first reader locks writer
                mutex.release();

                // reading section
                System.out.println(Thread.currentThread().getName() + " is reading");

                // exit
                mutex.acquire();
                readCount--;
                if (readCount == 0)
                    write.release();    // last reader releases writer
                mutex.release();

            } catch (Exception e) { }
        }
    }

    static class Writer extends Thread {
        public void run() {
            try {
                write.acquire();  // writer locks all
                System.out.println(Thread.currentThread().getName() + " is writing");
                write.release();
            } catch (Exception e) { }
        }
    }

    public static void main(String[] args) {
        new Reader().start();
        new Reader().start();
        new Writer().start();
        new Reader().start();
        new Writer().start();
    }
}
