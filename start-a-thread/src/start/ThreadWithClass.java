package start;

/**
 * @author manhdt14
 * created in 11/4/2023 2:26 PM
 */
public class ThreadWithClass extends Thread {
    // định nghĩa lại hành động cho thread
    @Override
    public void run() {
        System.out.println("Thread started Running...");
    }

    public static void main(String[] args) {
        ThreadWithClass thread = new ThreadWithClass();
        // khởi tạo thread
        thread.start();
    }
}
