/**
 * @author manhdt14
 * created in 11/4/2023 2:32 PM
 */
public class ThreadWithInterface implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running successfully");
    }

    public static void main(String[] args) {
        ThreadWithInterface runner = new ThreadWithInterface();

        Thread thread = new Thread(runner);
        // bắt đầu một thread
        thread.start();

    }
}
