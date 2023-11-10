Trong concurrency programming, có 2 đơn vị thực thi cơ bản: processes, threads, dẫn tới trong java khi nói về khái niệm multitasking sẽ đề cập tới hai khai niệm tương ứng, chủ yếu nói về multithreading.
  - Multiprocessing: liên quan đến số lượng core của máy tính
  - Multithreading:

Concurrency có thể thực hiện ngay cả trên các hệ thống đơn giản.
***
### Processes:
- Có môi trường thực thi khép kín.
- Có run-time resource cơ bản, riêng tư và hoàn chỉnh, mỗi process có không gian bộ nhớ riêng.
- các OS thường hỗ trợ giao tiếp giữa các processes qua IPC (Intel Process Communication), không chỉ trên cùng same OS mà còn giữa các different OSs.
- Hầu hết sự triển khai của JVM chạy như là một single process, một Java application có thể tạo thêm processes bằng `ProcessBuilder`.
### Thread:
- Thỉnh thoảng được gọi là lightweight processes
- Yêu cầu ít tài nguyên hệ thông hơn process khi tạo
- Tồn tại trong một process và mọi processes đều có ít nhất một thread
- Thread chia sẻ tài nguyên process bao gồm memory và open files, điều này làm cho việc giap tiếp hiệu quả những cũng dẫn tới nhiều vấn đề.
***
Một số phần tìm hiểu và làm rõ
- [thread basic](./thread-basic)
- [synchronization](./synchronization)