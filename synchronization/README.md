Outline
1. [thread interference](#thread-interference)
2. [memory consistency errors](#memory-consistency-errors)
3. [synchronization](#synchronization)
4. [intrinsic locks](#intrinsic-lock)
   1. [synchronized statements](#synchronized-statements)
   2. [re-entrant synchronization](#re-entrant-synchronization)
5. [atomic access](#atomic-access)
***
Các threads giao tiếp chủ yếu bằng cách chia sẻ truy cập vào các fields và objects reference fields refer to

Hình thức giao tiếp này cực kì hiệu quả nhưng có thể xảy ra 2 lỗi và công cụ cần thiết để ngăn chặn hai lỗi này là *synchronization*
- [thread interference](#thread-interference)
- [memory consistency errors](#memory consistency errors)

Tuy nhiên, synchroniztion có thể gây ra thread contention(xung đột luồng), do hai hoặc nhiều luồng cố gắng truy cập cùng một tài nguyên khiên cho thời gian chạy Java thực thi một hoặc nhiều luồng chậm hơn hoặc thậm chí tạm dừng việc thực thi của chúng.

`Startvation và livelock` là những dạng của thread contention
***
## thread interference (rối loạn luồng)
- còn được gọi là data race/race condition
- xảy ra khi mà hai hay nhiều luồng cùng truy cập cùng một dữ liệu trong khoảng thời gian overlap 

Vì không có `synchroniztion` nên thread interference đã xảy ra
```java
public class Main {
    public static void main(String[] args) {
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
```

Với một biểu thức hậu tố có thể chia làm ba bước:
- nhận giá trị c
- tăng giá trị c lên 1
- ghi giá trị đã tăng vào lại ô nhớ

Khi hai thread t1, t2 chạy đồng thời sẽ có thể nhận giá trị đã tăng hoặc chưa tăng của thread kia và ngược lại, dẫn đến kết quả kì lạ. Cần một khoảng thời gian lập đủ lớn để xảy ra tình huống

Ví dụ một kích bản: thread t1 tăng giá trị counter cùng lúc đó thread t2 thực hiện giảm giá trị counter và t1 chạy trước t2. Với giá trị c khởi tạo ban đầu là 0
- t1 nhận giá trị c
- t2 nhận giá trị c
- t1 tăng c => c = 1
- t2 giảm c -> c = -1
- t1 ghi c => c = 1
- t2 ghi c => c = -1
Lúc này giá trị c của t1 đã bị t2 ghi đè.

Bên cạnh đó còn nhiều tình huống khác có thể xảy ra do, do vậy kết quả sẽ không thể dự đoán được
***
## memory consistency errors
- xảy ra khi mà các threads khác nhau có inconsistent view về cùng một dữ liệu
- nguyên nhân sâu xa tương đối phức tạp, không chỉ gói gọn trong ngôn ngữ Java, trong việc tìm hiểu basic cũng chưa cần quan tâm, hầu hết là tìm cách phòng tránh nó

Chìa khoá để tránh memory consistency errors là hiểu về *happens-before* relationship. Mối quan hệ này đảm bảo rằng bộ nhớ được ghi bới một câu lệnh cụ thể sẽ được hiện thị cho một câu lệnh cụ thể khác

Ví dụ 
- chúng ta khai báo: `int counter = 0;`, biến này được chia sẻ giữa hai thead A, B. Giả sử A tăng counter lên 1: `counter++`
- sau đó, thread B in giá trị counter; `System.out.println(counter);`, giá trị này có thể là 0 vì không có gì đảm bảo rằng sự thay đổi của thread A có thể nhìn thấy từ thread B, trừ khi chúng ta thiết lập *happens-before relationship* giữa 2 câu lệnh này. 

Có rất nhiều hành động tạo ra mối quan hệ này, một trong số chúng là `synchronization`
- tất cả statement được thực thi bởi một terminated thread có mối quan hệ happens-before với các statement theo sau successful join
- tất cả các statement trước Thread.start() có mối quan hệ happens-before với các statement của new thread.
- ...
***
## synchronization
Một trong những giải pháp để tránh thread inteference và memory consistency errors là dùng synnchronization. Java cung cấp 2 kiểu
- [synchronized method](./src/threadinterference/SynchronizedCounter.java)
- synchronized statement (phức tạp hơn kiểu 1)

Tác dụng của việc chỉ định phương thức là synchronized như trong [ví dụ](./src/threadinterference/SynchronizedCounter.java):
- không thể tồn tại hai lời gọi của một synchronized method của một object để xen vào, khi một thread A gọi synchronized method các thread khác phải suspend execution cho đến khi thread A thực hiện xong
- nó tự động thiết lập happens-before relationship với bất kì lời gọi tiếp theo của cùng đối tượng đó trên synchronized method

*Qua đó, nó đảm bảo sự thay đổi của object được nhất quán giữa tất cả các thread.*

*Chú ý:*
- constructor không thể synchronized, việc sử dụng với constructor sẽ gây ra syntax error


Bên cạnh đó còn một số phương pháp khác:
- khai báo biến với final
- khai báo biến với volatile
- tạo immutable object
- sử dụng atomic operation
- hạn chế truy cập cùng đối tượng bởi nhiều luồng
- ...
***
## intrinsic locks
Synchronization được xây dựa trên một thực thể bên trong gọi là *intrinsic lock* or *monitor lock*, nó đóng vai trò trong cả hai khía cạnh của synchronization
- giảm sát quyền truy cập độc quyền của trạng thái đối tượng
- thiết lập mối happens-before relationship

Mọi object đều có một intrinsic lock gắn liền với nó, vì vậy một thread muốn truy cập độc quyền và nhất quán vào các trường của đối tượng cần có được intrinsic lock của đối tượng trước khi thực hiện điều đó

Trong thời gian một thread sở hữu intrinsic lock, các thread khác sẽ bị block khi cố gắng lấy khoá. Sau khi thread giải phóng khoá nó sẽ thiết lập happens-before relationship với bất kì thread nào lấy khoá sau đó.

Khi một thread gọi một synchronized method, nó tự động yêu cầu intrinsic lock và giải phóng khoá khi phương thức return.

Đối với trường hợp **static method**, thread sẽ yêu cầu intrinsic lock cho class và nó khác hoàn toàn với bất kì intrinsic lock của instance. Như vậy, nó sẽ lock toàn bộ phương thức của class
***
### synchronized statements
Không giống như synchronized method, synchronized statement phải chỉ định rõ object cung cấp intrinsic lock
```java
public void addName(String name) {
    synchronized(this) {
        lastName = name;
        nameCount++;
    }
    nameList.add(name);
}
```
Trong ví dụ trên addName() cần synchronize để thay đổi lastName, nameCount, lúc này lastName, nameCount sẽ bị lock để đảm bảo không có thread nào có thể truy cập như synchronized method. 

- Việc gọi các phương thức khác (add method) của các đối tượng khác (nameList) trong synchronized code sẽ tạo ra một vài vấn đề liên quan đến `thread contention`, đề cập ở phần `liveness`. Hay nói cách khác, việc gọi các phương thức khác trong lúc giữ monitor lock, sẽ liên quan đến vấn đề trong `liveness`. Được trình bày ở phần tiếp theo.

- Giả sử có nhiều thread thực hiện đồng thời phương thức addName(), không có gì đảm bảo luồng nào sẽ chèn vào `nameList` trước, có thể trong thời gian một luồng giải phỏng khoá, một luồng khác đã thêm vào `nameList`. [Ví dụ](./src/synchronizedstatement/Test.java)
- Invoking other objects' methods from synchronized code can create problems that are described in the section on Liveness.
  - Đầu tiên: trong sychronized code, nghĩa là no đang giữ khoá và ngăn cản các thread khác sử dụng các tài nguyên chia sẻ
  - Nếu thời gian thực hiên các phương thức khác quá lâu, sẽ dần đến các vấn đề trong liveness


Ngoài ra, synchronized statements cũng ưu ích cho việc cải thiện tính concurrency ([MsLunch2](./src/synchronizedstatement/MsLunch2.java) chậm hơn [MsLunch](./src/synchronizedstatement/MsLunch.java)) vì các thread truy cập `inc1()` và `inc2()` không block lẫn nhau như synchronized method. Vì vậy chúng có thể chạy đồng thời và độc lập, khiến thời gian chạy nhanh hơn.
***
### re-entrant synchronization
Một thread không thể có được lock đang chiếm giữ bởi một thread khác, tuy nhiên một thread có thể giữ cùng một khoá nhiều lần không bị block
```java
public class ReentrantExample {
    private final Object lock = new Object();

    public synchronized void outerMethod() {
        // do something

        innerMethod(); // it can acquire the lock again 

        // do something
    }

    public synchronized void innerMethod() {
        // do something
    }
}
```
Ưu điểm: đơn giản hoá thiết kế của một số thuật toán và cấu trúc dữ liệu nơi mà các phương thức có thể cần gọi lại nhau đệ quy hoặc nơi mà một phương thức cấp cao hơn cần khoá giống như một phương thức cấp thấp hơn mà nó gọi
***
## atomic access
atomic action chứa các tiêu chí sau:
- một hành động thực hiện hoàn toàn mà không bị giãn đoạn, hoàn thành đầu đủ hoặc không hoàn thành cái gì
- nó xuất hiện như là một hành động xảy ra tức thì dưới góc độ của một thread khác
- không thể bị chia nhỏ
- không side effect cho đến khi nó được hoàn tất

một số hành động có thể chỉ định atomic
- read & write là atomic đối với các reference variables và hấu hết các primitive variables (ngoại trừ long và double)
- read & write là atomic đối với **tất cả** các biến khai báo với từ khoá `volatile`

atomic không thể bị interleaved, vì vậy có thể sử dụng mà không sợ `thread interference`, tuy nhiên vẫn có thể xảy ra `memory consistency errors`. Sử dụng `volatile` để giảm nguy cơ `memory consistency errors`.
- volatile thiết lập happens-before relationships

*chú ý*:
- Việc sử dụng atomic variable access là đơn giản hơn so với accessing these through synchronized code, nhưng yêu cầu người lập trình phải xử lý thêm để tránh memory consistency errors.