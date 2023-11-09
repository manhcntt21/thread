/**
 * @author manhdt14
 * created in 11/10/2023 2:05 AM
 */
public class SimpleThread {
    // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable {
        public void run() {
            String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
            };

            try {
                for (int i = 0;
                     i < importantInfo.length;
                     i++) {
                    // Pause for 4 seconds
                    Thread.sleep(4000);
                    // Print a message
                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Delay, in milliseconds before
        // we interrupt MessageLoop
        // thread (default 10s).
        long patience = 1000 * 10;

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();

        Thread t = new Thread(new MessageLoop(), "MessageLoop");
        t.start();
        threadMessage("Waiting for MessageLoop thread to finish");

        while (t.isAlive()) {
            threadMessage("Still waiting...");
            t.join(15000);

            if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // interrupt để báo hiệu luồng đó kết thúc, nhưng nó không bắt buộc, thread đó vẫn có thể chạy
                // chạy t.join để buộc Main Thread đợi thread t kết thúc
                // trong ví dụ này: do thời gian quá ngắn nên chưa thấy được tác dụng nếu tác vụ kết thúc trong thời gian dài thì có thể thấy được hiệu quả
                t.join();
            }
        }
        threadMessage("Finally!");
    }
}
