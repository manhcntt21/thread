package synchronizedstatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manhdt14
 * created in 11/11/2023 4:30 AM
 */
public class Test {
    private String lastName;
    private int nameCount = 0;
    List nameList = new ArrayList();

    public void addName(String name) {
        synchronized(this) {
            lastName = name;
            nameCount++;
        }
        nameList.add(name);
    }

    public void print() {
        this.nameList.forEach(i -> System.out.println(i));
    }
    public static void main(String[] args)  {
        Test test = new Test();
        for(int i = 0 ; i < 50000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    test.addName(Thread.currentThread().getName());
                }
            }, "t" + i);
            t.start();
        }

        test.print();
    }
}
