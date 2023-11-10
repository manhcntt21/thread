package synchronizedstatement;

/**
 * @author manhdt14
 * created in 11/10/2023 9:40 PM
 */
public class MsLunch2 {
    private long c1 = 0;
    private long c2 = 0;

    public synchronized void inc1() {
        c1++;
    }

    public synchronized void inc2() {
        c2++;
    }

    public long getC1() {
        return c1;
    }

    public long getC2() {
        return c2;
    }
}