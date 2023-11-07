package interrupt;

/**
 * @author manhdt14
 * created in 11/4/2023 7:15 PM
 */
public class InterruptWithTryCatch implements Runnable {
    @Override
    public void run() {
        String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (int i = 0; i < importantInfo.length; i++) {
            //Pause for 4 seconds
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("InterruptException");
            }
            //Print a message
            System.out.println(importantInfo[i]);
        }
    }

    public static void main(String args[]) {
        // khởi tạo runnable object
        InterruptWithTryCatch interruptWithTryCatch = new InterruptWithTryCatch();
        InterruptWithTryCatch interruptWithTryCatch2 = new InterruptWithTryCatch();

        // khởi tạo thread
        Thread t1 = new Thread(interruptWithTryCatch);
        Thread t2 = new Thread(interruptWithTryCatch2);

        // start
        t1.start();
//        t1.interrupt();
        t2.start();
    }
}
