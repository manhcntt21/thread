package join;

/**
 * @author manhdt14
 * created in 11/9/2023 5:44 PM
 */
public class JoinThreadExample implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread started " + Thread.currentThread().getName());
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
                return;
            }
        }
        System.out.println("Thread ended " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        System.out.println("Main Thread start: " + Thread.currentThread().getName());

        JoinThreadExample runnable = new JoinThreadExample();
        Thread t1 = new Thread(runnable, "t1");

        // start thread t1
        t1.start();


        {
//             interrupt Main Thread to raise InterruptException
//             uncomment when you want "handle interrupt 2!"
//             Thread.currentThread().interrupt();

            try {
                t1.join();
            } catch (InterruptedException e) {
                System.out.println("Handle interrupt 2!");
                e.printStackTrace();
                return;
            }
        }

        {
//            Main Thread sẽ chỉ đợi t1 thread trong 1s
//            try {
//                t1.join(1000);
//            } catch (InterruptedException e) {
//                System.out.println("Handle interrupt 2!");
//                e.printStackTrace();
//                return;
//            }
        }
        System.out.println("Main Thread finished");
    }
}
