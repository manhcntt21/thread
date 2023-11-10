package threadinterference;

/**
 * @author manhdt14
 * created in 11/10/2023 8:42 AM
 */
public class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
