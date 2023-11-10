package synchronizedstatement;

/**
 * @author manhdt14
 * created in 11/10/2023 9:58 AM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // synchronized statements
//        MsLunch ms = new MsLunch();
        // synchronized method
        MsLunch2 ms = new MsLunch2();

        long start = System.currentTimeMillis();
        System.out.println(start);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                ms.inc1();
            }
            System.out.println(ms.getC1());
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                ms.inc2();
            }
            System.out.println(ms.getC2());
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("Time = " + (end - start)/1000.0);
    }
}
