package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author manhdt14
 * created in 11/11/2023 1:16 AM
 */
public class AtomicAccessExample {
    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()-> {
            for(int i = 0; i < 100000; i++) {
                counter.incrementAndGet();
            }
        });

        Thread t2 = new Thread(()-> {
            for(int i = 0; i < 100000; i++) {
                counter.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // kết quả luôn là 200.000 dù có chạy như thế nào đi nữa
        System.out.println(counter.get());
    }
}
