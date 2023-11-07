package interrupt;

/**
 * Vi java không cho phép chúng ta override method từ việc extend hoặc implement interface và ném ra ngoại lệ, nên mình
 * việc một phương thức mới và thao tác trực tiếp với Main Thread luôn, phương thức sẽ ném ra ngoại lệ và ỏ main sẽ bắt
 * @author manhdt14
 * created in 11/7/2023 10:49 PM
 */
public class InterruptWithThrowsException extends Thread {

    public static void throwInterruptedException() throws InterruptedException {

        Thread.sleep(1000);

        Thread.currentThread().interrupt();
        //1
        System.out.println(Thread.currentThread().isInterrupted()); // true
        //1'
//        System.out.println(Thread.interrupted()); // true

        if(Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public static void main(String[] args)  {
        //2
        System.out.println(Thread.currentThread().isInterrupted()); // false
        // 2'
        System.out.println(Thread.interrupted()); // false
        try {
            throwInterruptedException();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted by throwing the exception");
        }
        //3
        System.out.println(Thread.currentThread().isInterrupted()); // false
        //3'
//        System.out.println(Thread.interrupted()); // false
    }
}
