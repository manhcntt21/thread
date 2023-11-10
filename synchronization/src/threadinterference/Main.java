package threadinterference;

/**
 * @author manhdt14
 * created in 11/10/2023 4:06 AM
 */
public class Main {
    public static void main(String[] args) {
//         thread interference
        Counter counter = new Counter();


        Thread t1 = new Thread(() -> {
            for(int i = 0 ; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0 ; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            // main thread has to wait t1, t2 to complete
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // final result is unpredictable and can vary between different runs of the program
        System.out.println(counter.value());
    }
}
