package threadinterference;

/**
 * @author manhdt14
 * created in 11/10/2023 4:01 AM
 */
public class Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }
}
