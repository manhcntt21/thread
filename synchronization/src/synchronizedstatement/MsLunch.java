package synchronizedstatement;

/**
 * @author manhdt14
 * created in 11/10/2023 9:49 AM
 */
public class MsLunch {
    private long c1 = 0;
    private long c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized(lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized(lock2) {
            c2++;
        }
    }

    public long getC1() {
        return c1;
    }

    public long getC2() {
        return c2;
    }
}
