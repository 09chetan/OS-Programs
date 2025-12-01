import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    static class Philosopher extends Thread {
        int id;
        ReentrantLock leftFork;
        ReentrantLock rightFork;

        Philosopher(int id, ReentrantLock left, ReentrantLock right) {
            this.id = id;
            this.leftFork = left;
            this.rightFork = right;
        }

        public void run() {
            try {
                while (true) {
                    think();

                    // Trick to avoid deadlock
                    if (id == 4) { 
                        rightFork.lock();
                        leftFork.lock();
                    } else {
                        leftFork.lock();
                        rightFork.lock();
                    }

                    eat();

                    leftFork.unlock();
                    rightFork.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void think() {
            System.out.println("Philosopher " + id + " is thinking.");
        }

        void eat() {
            System.out.println("Philosopher " + id + " is eating.");
        }
    }

    public static void main(String[] args) {
        ReentrantLock[] forks = new ReentrantLock[5];
        for (int i = 0; i < 5; i++) forks[i] = new ReentrantLock();

        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < 5; i++) {
            ReentrantLock leftFork = forks[i];
            ReentrantLock rightFork = forks[(i + 1) % 5];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            philosophers[i].start();
        }
    }
}
