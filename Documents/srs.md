# Giới thiệu

### **1.1 Mục đích**

Tài liệu SRS này được xây dựng nhằm mô tả chi tiết các yêu cầu của hệ thống ứng dụng di động đa nền tảng “Đi chợ tiện lợi”. Mục tiêu của ứng dụng là hỗ trợ người dùng trong việc:

* Quản lý danh sách mua sắm hằng ngày.  
* Theo dõi thực phẩm trong bếp và hạn sử dụng.  
* Lên kế hoạch bữa ăn và quản lý công thức nấu ăn.  
* Tối ưu hóa tiêu thụ thực phẩm, tránh lãng phí.  
* Chia sẻ danh sách và phân công việc mua sắm trong gia đình.

Tài liệu này là cơ sở để nhóm phát triển thống nhất phạm vi, chức năng và yêu cầu phi chức năng của hệ thống trước khi triển khai.

**1.2 Phạm vi hệ thống**

Ứng dụng “Đi chợ tiện lợi” được phát triển cho các nền tảng di động (Android, iOS) với backend server hỗ trợ quản lý dữ liệu. Hệ thống cung cấp các chức năng chính như:

* Quản lý danh sách mua sắm (tạo, cập nhật, chia sẻ).  
* Quản lý thực phẩm trong bếp (lưu trữ, theo dõi hạn sử dụng, nhắc nhở).  
* Quản lý bữa ăn và công thức nấu ăn.  
* Gợi ý món ăn từ nguyên liệu sẵn có.  
* Thống kê và báo cáo thói quen tiêu thụ.  
* Quản trị tài khoản và dữ liệu hệ thống.

Ứng dụng hướng tới đối tượng là người dùng cá nhân và hộ gia đình, đặc biệt là những người thường xuyên đi chợ hoặc nấu ăn tại nhà.

### **1.3 Tài liệu tham khảo**

Mô tả chủ đề bài tập lớn IT4788 – Phát triển ứng dụng đa nền tảng: *“Xây dựng ứng dụng di động đa nền tảng Đi chợ tiện lợi”*.

### **1.4 Tổng quan tài liệu**

Tài liệu này bao gồm:

* Phần Giới thiệu: Trình bày mục đích, phạm vi và đối tượng người dùng.  
* Mô tả tổng quan hệ thống: Nêu bối cảnh, đặc điểm người dùng và ràng buộc hệ thống.  
* Các yêu cầu chức năng: Liệt kê và mô tả chi tiết các use case và chức năng chính.  
* Các yêu cầu phi chức năng: Bao gồm hiệu năng, bảo mật, khả năng mở rộng và tính khả dụng.  
* Phụ lục: Các sơ đồ, biểu đồ hoặc tài liệu liên quan.

# Tổng quan hệ thống

### **2.1 Góc nhìn sản phẩm**

Ứng dụng “Đi chợ tiện lợi” là một hệ thống di động đa nền tảng, bao gồm:

* Ứng dụng client (Android/iOS): Cung cấp giao diện người dùng để quản lý danh sách mua sắm, thực phẩm, công thức nấu ăn và bữa ăn hằng ngày.  
* Server backend: Chịu trách nhiệm lưu trữ dữ liệu người dùng, đồng bộ thông tin giữa các thiết bị, xử lý logic nghiệp vụ và gửi thông báo đẩy.  
* Cơ sở dữ liệu: Lưu trữ dữ liệu về tài khoản người dùng, danh sách mua sắm, thông tin thực phẩm, công thức nấu ăn, lịch bữa ăn, báo cáo và dữ liệu quản trị.

Ứng dụng tương tác thông qua RESTful API với định dạng JSON. Các chức năng chia sẻ trong nhóm gia đình được hỗ trợ thông qua cơ chế đồng bộ dữ liệu trên server

### **2.2 Đặc điểm người dùng**

Hệ thống hướng đến 2 nhóm đối tượng chính:

* Người dùng cá nhân: Người dùng bình thường, sử dụng ứng dụng để quản lý mua sắm, thực phẩm và bữa ăn.  
* Người dùng gia đình: Nhóm người dùng có tài khoản được liên kết, chia sẻ và cập nhật chung danh sách mua sắm.  
* Quản trị viên hệ thống (Admin): Người quản trị có quyền quản lý tài khoản người dùng, danh mục hàng hóa, loại thực phẩm và các thông tin hệ thống.

Đặc điểm người dùng:

* Người dùng cuối thường không có kiến thức kỹ thuật sâu, cần một giao diện đơn giản, trực quan, dễ thao tác.  
* Người dùng gia đình cần chức năng chia sẻ, phân công và thông báo kịp thời để phối hợp hiệu quả.  
* Quản trị viên cần công cụ quản trị dữ liệu và tài khoản để duy trì tính ổn định của hệ thống.

### **2.3 Giới hạn hệ thống**

* Ứng dụng được phát triển trên nền tảng React Native hoặc Flutter để hỗ trợ đa nền tảng (Android và iOS).  
* Hệ thống backend có thể triển khai trên cloud server hoặc local server phục vụ nhóm phát triển.  
* Cơ sở dữ liệu lựa chọn: MySQL/PostgreSQL cho dữ liệu quan hệ, hoặc MongoDB nếu cần tính linh hoạt hơn trong quản lý dữ liệu phi cấu trúc.  
* Hệ thống chỉ hỗ trợ kết nối Internet để đồng bộ dữ liệu. Một số chức năng có thể hoạt động offline (như xem danh sách mua sắm đã lưu), nhưng sẽ đồng bộ khi có mạng.

### **2.4 Các ràng buộc thiết kế và triển khai**

Ngôn ngữ lập trình:

* Frontend: Dart (Flutter) hoặc JavaScript/TypeScript (React Native).  
* Backend: Java/Spring Boot, Node.js hoặc tương đương.

Hiệu năng: Ứng dụng cần phản hồi nhanh (\< 2 giây cho các thao tác thường xuyên).

Bảo mật: Dữ liệu tài khoản người dùng phải được mã hóa, truyền tải qua HTTPS.

Khả năng mở rộng: Hệ thống phải hỗ trợ đồng thời nhiều người dùng trong cùng nhóm gia đình mà không gây xung đột dữ liệu.

### **2.5 Ràng buộc thiết kế**

* Người dùng cần có thiết bị di động thông minh (Android/iOS) và kết nối Internet.  
* Các dịch vụ push notification phụ thuộc vào hạ tầng của Apple (APNs) và Google (Firebase Cloud Messaging).  
* Người dùng phải cấp quyền cho ứng dụng để nhận thông báo.  
* Việc chia sẻ danh sách trong gia đình giả định rằng các thành viên đều có tài khoản và cùng tham gia một nhóm.

# Yêu cầu chức năng

### **3.1 Tổng quan**

Ứng dụng “Đi chợ tiện lợi” cung cấp các nhóm chức năng chính sau:

1. Quản lý danh sách mua sắm.  
2. Quản lý dự định bữa ăn.  
3. Quản lý thực phẩm trong bếp.  
4. Quản lý công thức nấu ăn.  
5. Quản lý danh sách thực phẩm.  
6. Quản lý nhóm.  
7. Thống kê và báo cáo.  
8. Quản trị người dùng.

Mỗi nhóm chức năng sẽ được mô tả chi tiết dưới dạng Use Case.

### **3.2 Các Usecase chính**

![][image1]

#### **UC-01: Quản lý danh sách mua sắm**

Mô tả: Người dùng tạo, chỉnh sửa, xóa, theo dõi danh sách các mặt hàng cần mua khi đi chợ/siêu thị. Giao cho người dùng đảm nhận đi chợ.  
Tác nhân: Người dùng  
Ưu tiên: Cao

#### **UC-02: Quản lý dự định bữa ăn**

Mô tả: Người dùng lên kế hoạch bữa ăn theo ngày hoặc tuần  
Tác nhân: Người dùng  
Ưu tiên: Trung bình

#### **UC-03: Quản lý thực phẩm trong bếp**

Mô tả: Người dùng nhập, chỉnh sửa, theo dõi thông tin thực phẩm tích trữ (trong tủ lạnh, trong bếp)  
Tác nhân: Người dùng  
Ưu tiên: Cao

#### **UC-04: Quản lý công thức nấu ăn**

Mô tả: Người dùng lưu trữ và quản lý các công thức nấu ăn  
Tác nhân: Người dùng, Quản trị viên  
Ưu tiên: Cao

#### **UC-05: Quản lý danh sách thực phẩm**

Mô tả: Người dùng quản lý danh sách các loại thực phẩm  
Tác nhân: Người dùng, Quản trị viên  
Ưu tiên: Cao

#### **UC-06: Quản lý nhóm**

Mô tả: Người dùng tạo/xóa nhóm, thêm/xóa thành viên trong nhóm  
Tác nhân: Người dùng  
Ưu tiên: Cao

#### **UC-07: Thống kê và báo cáo**

Mô tả: Hệ thống tạo báo cáo về mua sắm và tiêu thụ thực phẩm  
Tác nhân: Người dùng  
Ưu tiên: Thấp

#### **UC-08: Quản trị người dùng**

Mô tả: Quản trị viên quản lý tài khoản người dùng  
Tác nhân: Quản trị viên  
Ưu tiên: Thấp

### **3.3 Phân rã Usecase**

#### **3.3.1 UC-01: Quản lý danh sách mua sắm**

![][image2]

##### **UC-01.1: Tạo danh sách mua sắm**

1\. Mã Usecase: UC-01.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và phần mềm khi người dùng tạo danh sách mua sắm  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia vào một group  
5\. Hậu điều kiện: Danh sách mua sắm được tạo ra, người đảm nhiệm nhận được thông báo  
6\. Luồng cơ bản:

* Step 1: Người dùng điền thực phẩm cần mua theo mẫu bảng 3.3.1.1  
* Step 2: Người dùng chọn người đảm nhận (droplist người dùng trong group)  
* Step 3: Người dùng hoàn thành tạo danh sách  
* Step 4: Hệ thống lưu vào database  
* Step 5: Hệ thống gửi thông báo cho người đảm nhận

7\. Luồng thay thế:

* Ở trước bước 1, nếu group người dùng có “thực đơn lên sẵn”, thực hiện usecase UC-01.4 (Gợi ý bổ sung món vào danh sách mua sắm), sau đó tiếp tục bước 1\.

8\. Dữ liệu đầu vào  
*Bảng 3.3.1.1. Đầu vào thực phẩm cần mua*

| STT | Tên trường | Mô tả | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | Tên thực phẩm | Chọn từ droplist | Thịt lợn |
| 2 | Số lượng | Số tự nhiên | 1 |
| 3 | Đơn vị | Text | Hộp |

##### **UC-01.2: Chỉnh sửa danh sách mua sắm**

1\. Mã Usecase: UC-01.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và phần mềm khi người dùng chỉnh sửa danh sách mua sắm  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Group đã có danh sách mua sắm tạo trước đó  
5\. Hậu điều kiện: Danh sách mua sắm được chỉnh sửa, người đảm nhiệm nhận được thông báo  
6\. Luồng cơ bản:

* Step 1: Nếu cần thêm thực phẩm mới, điền thực phẩm phẩm cần mua theo mẫu bảng 3.3.1.1  
* Step 2: Nếu cần xóa thực phẩm có sẵn, chọn xóa thực phẩm  
* Step 3: Nếu cần chỉnh sửa thực phẩm có sẵn, sửa lại các trường trong mẫu bảng 3.3.1.1  
* Step 4: Nếu cần chỉnh sửa người đảm nhận, sửa lại trường người đảm nhận (từ droplist người dùng trong group)  
* Step 5: Người dùng hoàn thành chỉnh sửa danh sách  
* Step 6: Hệ thống update trong database  
* Step 7: Hệ thống gửi thông báo cho người đảm nhiệm và cả người đảm nhiệm cũ (nếu có)

7\. Luồng thay thế:

* Ở trước bước 1, nếu group người dùng có “thực đơn lên sẵn”, thực hiện usecase UC-01.4 (Gợi ý bổ sung món vào danh sách mua sắm), sau đó tiếp tục bước 1\.

##### **UC-01.3: Theo dõi danh sách mua sắm**

1\. Mã Usecase: UC-01.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và phần mềm khi người dùng theo dõi danh sách mua sắm lúc mua hàng  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Group đã có danh sách mua sắm tạo trước đó  
5\. Hậu điều kiện: Thông báo đến toàn group  
6\. Luồng cơ bản:

* Step 1: Người dùng mở danh sách mua sắm (hiển thị dưới dạng checklist).  
* Step 2: Người dùng tick chọn một mặt hàng khi đã mua.  
* Step 3: Hệ thống hiển thị hộp thoại nhập số lượng thực tế mua được.  
* Step 4: Người dùng nhập số lượng thực tế và xác nhận.  
* Step 5: Hệ thống:  
  * Ghi nhận số lượng thực tế đã mua.  
  * Nếu số lượng thực tế \= số lượng dự định → đánh dấu “đã mua”.  
  * Nếu số lượng thực tế \< số lượng dự định → đánh dấu “đã mua” và tạo thêm 1 mục cần mua là số lượng thực phẩm đó còn thiếu.  
* Step 6: Người dùng lặp lại cho các mặt hàng khác.  
* Step 7: Người dùng chọn “Hoàn thành đi chợ”.  
* Step 8: Hệ thống gửi thông báo để toàn group.

##### **UC-01.4: Gợi ý khi bổ sung thực phẩm vào danh sách mua sắm**

1\. Mã Usecase: UC-01.4  
2\. Mô tả: Usecase giúp người dùng được gợi ý thực phẩm liên quan từ bếp, thực đơn đã lên lịch, và công thức nấu ăn để danh sách đi chợ đầy đủ và tối ưu.  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện:

* Người dùng đang cần thêm một mục vào danh sách mua sắm  
* Trong group của người dùng có “thực đơn lên sẵn”

5\. Hậu điều kiện: Thực phẩm cần mua được gợi ý cho người dùng  
6\. Luồng cơ bản:

* Step 1: Hệ thống kiểm tra dữ liệu liên quan:  
  * Thực đơn đã lên lịch: Kiểm tra thực đơn cho những bữa sắp tới  
  * Công thức nấu ăn: Kiểm tra nguyên liệu cần dùng trong thực đơn đó  
  * Bếp: Kiểm tra nguyên liệu đó trong bếp  
* Step 2: Hệ thống kiểm tra điều kiện xem nguyên liệu đó có số lượng ít hơn hay khác đơn vị so với công thức hay không  
* Step 3: Hệ thống gợi ý thực phẩm nên bổ sung theo template ở bảng 3.3.1.2  
* Step 4: Người dùng chọn đồng ý để thêm thực phẩm đó vào danh sách mua sắm

7\. Luồng thay thế

* Ở bước 2, nếu thực phẩm đó đã đủ hoặc thừa, quay lại thực hiện bước 1 để tìm nguyên liệu tiếp theo.  
* Ở bước 4, nếu người dùng chọn không đồng ý thêm thực phẩm, quay lại thực hiện bước 1 để tìm nguyên liệu tiếp theo.

8\. Dữ liệu đầu ra  
*Bảng 3.3.1.2. Template đầu ra cho thực phẩm được gợi ý*

| STT | Điều kiện | Template | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | Thực phẩm không có trong bếp | Bạn cần \<số lượng\> \<đơn vị\> \<thực phẩm\> cho món \<món ăn\> trong thực đơn sắp tới | Bạn cần 200 gram thit lợn cho món thịt kho tàu trong thực đơn sắp tới |
| 2 | Thực phẩm trong bếp có số lượng ít hơn hay khác đơn vị so với trong công thức | Bạn cần \<số lượng\> \<đơn vị\> \<thực phẩm\> cho món \<món ăn\> trong thực đơn sắp tới, hiện đã có \<số lượng trong bếp\> \<đơn vị trong bếp\> \<thực phẩm\> trong bếp | Bạn cần 200 gram thịt lợn phục vụ cho món thịt kho tàu trong thực đơn sắp tới, hiện đã có 1 hộp thịt lợn trong bếp |

#### **3.3.2 UC-02: Quản lý dự định bữa ăn**

![][image3]

##### **UC-02.1: Tạo mới thực đơn**

1\. Mã Usecase: UC-02.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng tạo thực đơn mới cho bữa ăn sắp tới  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia vào một group  
5\. Hậu điều kiện: Thực đơn mới được tạo ra  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn bữa muốn tạo thực đơn  
* Step 2: Người dùng thêm từng món cho thực đơn theo mẫu ở bảng 3.3.2.1  
* Step 3: Người dùng hoàn tất lên thực đơn  
* Step 4: Hệ thống lưu dữ liệu vào database

7\. Luồng thay thế:

* Trước khi đến bước 2, nếu trong bếp có thực phẩm, kích hoạt UC-02.3 (Gợi ý món ăn từ thực phẩm trong bếp), sau đó thực hiện tiếp bước 2\.

8\. Dữ liệu đầu vào  
*Bảng 3.3.2.1. Đầu vào cho từng món trong thực đơn*

| STT | Tên trường | Mô tả | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | Số lượng phần | Số tự nhiên | 1 |
| 2 | Tên món ăn | Text | Thịt kho tàu |

##### **UC-02.3: Gợi ý món ăn từ thực phẩm trong bếp**

1\. Mã Usecase: UC-02.3  
2\. Mô tả: Usecase giúp gợi ý món ăn cho người dùng dựa vào thực phẩm trong bếp  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đang thêm món vào thực đơn, trong bếp đã có sẵn thực phẩm  
5\. Hậu điều kiện: Món ăn được gợi ý cho người dùng  
6\. Luồng cơ bản:

* Step 1: Hệ thống kiểm tra dữ liệu liên quan:  
  * Bếp: Kiểm tra các thực phẩm có sẵn trong bếp  
  * Thực đơn: Kiểm tra các món ăn sử dụng thực phẩm đó làm nguyên liệu chính  
* Step 2: Gợi ý món ăn đó cho người dùng với template như bảng 3.3.2.2  
* Step 3: Người dùng chọn đồng ý để thêm món ăn vào thực đơn

7\. Luồng thay thế:

* Ở bước 3, nếu người dùng chọn không đồng ý thêm món ăn đó, quay lại bước 1 để gợi ý món ăn khác

8\. Dữ liệu đầu ra:  
*Bảng 3.3.2.2. Template đầu ra cho món ăn được gợi ý*

| STT | Điều kiện | Template | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 |  | Trong bếp hiện có \<số lượng\> \<đơn vị\> \<thực phẩm\>, bạn có muốn thêm \<món ăn\> vào thực đơn hay không? | Trong bếp hiện có 1 hộp thịt lợn, bạn có muốn thêm món thịt kho tàu vào thực đơn hay không? |

#### **3.3.3 UC-03: Quản lý thực phẩm trong bếp**

![][image4]

##### **UC-03.1: Thêm thực phẩm vào bếp**

1\. Mã Usecase: UC-03.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng thêm thực phẩm mới vào bếp  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia vào 1 group  
5\. Hậu điều kiện: Thực phẩm mới được thêm vào danh sách thực phẩm trong bếp  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn thêm thực phẩm  
* Step 2: Người dùng điền thông tin thực phẩm theo bảng 3.3.3.1  
* Step 3: Hệ thống lưu dữ liệu vào database

7\. Luồng thay thế:

* Ở bước 2, nếu thực phẩm được thêm trùng với một thực phẩm đã tồn tại, chuyển sang usecase UC-03.2 (Chỉnh sửa thực phẩm trong bếp) ứng với thực phẩm đó.

8\. Dữ liệu đầu vào:  
*Bảng 3.3.3.1. Đầu vào cho mỗi thực phẩm trong bếp*

| STT | Tên trường | Mô tả | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | Tên thực phẩm | Chọn từ droplist | Thịt lợn |
| 2 | Số lượng | Số tự nhiên | 1 |
| 3 | Đơn vị | Text | Hộp |
| 4 | Ngày hết hạn | dd/mm/yyyy | 01/01/2026 |

##### **UC-03.2: Chỉnh sửa thực phẩm trong bếp**

1\. Mã Usecase: UC-03.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng chỉnh sửa thực phẩm trong bếp  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Trong tủ lạnh đã có sẵn thực phẩm  
5\. Hậu điều kiện: Thực phẩm được chỉnh sửa ở danh sách thực phẩm trong bếp  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn thực phẩm cần chỉnh sửa  
* Step 2: Người dùng chỉnh sửa thông tin thực phẩm ở bảng 3.3.3.1 (Lưu ý chỉ được chỉnh sửa số lượng và đơn vị)  
* Step 3: Hệ thống lưu dữ liệu vào database

##### **UC-03.3: Xóa thực phẩm khỏi bếp**

1\. Mã Usecase: UC-03.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng xóa thực phẩm khỏi bếp  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Trong bếp đã có sẵn thực phẩm  
5\. Hậu điều kiện: Thực phẩm được xóa khỏi danh sách thực phẩm trong bếp  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn thực phẩm cần xóa
* Step 2: Hệ thống hiển thị xác nhận xóa
* Step 3: Người dùng xác nhận xóa
* Step 4: Hệ thống xóa thực phẩm khỏi database

7\. Luồng thay thế:

* Ở bước 3, nếu người dùng hủy bỏ, quay lại danh sách thực phẩm

##### **UC-03.4: Nhắc nhở thực phẩm sắp hết hạn**

1\. Mã Usecase: UC-03.4  
2\. Mô tả: Usecase mô tả cách hệ thống tự động nhắc nhở người dùng về thực phẩm sắp hết hạn  
3\. Tác nhân: Hệ thống (tự động)  
4\. Tiền điều kiện: Trong bếp có thực phẩm với ngày hết hạn được thiết lập  
5\. Hậu điều kiện: Người dùng nhận được thông báo về thực phẩm sắp hết hạn  
6\. Luồng cơ bản:

* Step 1: Hệ thống kiểm tra định kỳ (mỗi ngày) danh sách thực phẩm trong bếp
* Step 2: Hệ thống xác định các thực phẩm có ngày hết hạn trong vòng 3 ngày tới
* Step 3: Hệ thống tạo thông báo đẩy (push notification) cho tất cả thành viên trong nhóm
* Step 4: Nội dung thông báo theo template ở bảng 3.3.3.2

7\. Dữ liệu đầu ra:  
*Bảng 3.3.3.2. Template thông báo thực phẩm sắp hết hạn*

| STT | Số lượng thực phẩm | Template | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | 1 thực phẩm | \<Tên thực phẩm\> sẽ hết hạn vào \<ngày hết hạn\> (còn \<số ngày\> ngày) | Thịt lợn sẽ hết hạn vào 03/01/2026 (còn 2 ngày) |
| 2 | Nhiều thực phẩm | Bạn có \<số lượng\> thực phẩm sắp hết hạn. Nhấn để xem chi tiết. | Bạn có 3 thực phẩm sắp hết hạn. Nhấn để xem chi tiết. |

##### **UC-03.5: Xem danh sách thực phẩm trong bếp**

1\. Mã Usecase: UC-03.5  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng xem danh sách thực phẩm  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập và tham gia nhóm  
5\. Hậu điều kiện: Danh sách thực phẩm được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình quản lý bếp
* Step 2: Hệ thống hiển thị danh sách thực phẩm theo bảng 3.3.3.3
* Step 3: Người dùng có thể lọc theo danh mục, sắp xếp theo ngày hết hạn

7\. Dữ liệu đầu ra:  
*Bảng 3.3.3.3. Hiển thị thông tin thực phẩm*

| STT | Trường hiển thị | Mô tả |
| :---- | :---- | :---- |
| 1 | Tên thực phẩm | Tên của thực phẩm |
| 2 | Số lượng | Số lượng hiện có |
| 3 | Đơn vị | Đơn vị tính |
| 4 | Ngày hết hạn | Ngày hết hạn (dd/mm/yyyy) |
| 5 | Trạng thái | Còn tốt / Sắp hết hạn / Hết hạn |

#### **3.3.4 UC-04: Quản lý công thức nấu ăn**

![][image5]

##### **UC-04.1: Tạo công thức nấu ăn chung**

1\. Mã Usecase: UC-04.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa quản trị viên và hệ thống khi quản trị viên tạo một công thức nấu ăn chung cho toàn hệ thống  
3\. Tác nhân: Quản trị viên  
4\. Tiền điều kiện: Quản trị viên đã đăng nhập  
5\. Hậu điều kiện: Công thức nấu ăn chung mới được tạo ra  
6\. Luồng cơ bản:

* Step 1: QTV chọn tạo công thức nấu ăn chung  
* Step 2: QTV điền thông tin cho công thức nấu ăn mới theo bảng 3.3.4.1  
* Step 3: QTV hoàn thành điền thông tin  
* Step 4: Hệ thống lưu công thức nấu ăn chung mới vào database

7\. Dữ liệu đầu vào  
*Bảng 3.3.4.1. Đầu vào cho công thức nấu ăn mới*

| STT | Tên trường | Mô tả | Bắt buộc | Ví dụ |
| :---- | :---- | :---- | :---- | :---- |
| 1 | Tên món ăn | Text | Y | Thịt kho tàu |
|  | Nguyên liệu chính |  |  |  |
| 2.1 | ├── Tên thực phẩm | Chọn từ droplist | Y | Thịt lợn |
| 2.2 | ├── Số lượng | Số tự nhiên | Y | 1 |
| 2.3 | └── Đơn vị | Text | Y | Hộp |
|  | Nguyên liệu phụ 1 |  |  |  |
| 3.1.1 | ├── Tên thực phẩm | Chọn từ playlist | Y | Hành tím |
| 3.1.2 | ├── Số lượng | Số tự nhiên | Y | 1 |
| 3.1.3 | └── Đơn vị | Text | Y | Củ |

##### **UC-04.3: Tạo công thức nấu ăn cá nhân** 

1\. Mã Usecase: UC-04.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng tạo một công thức nấu ăn cá nhân mới (Công thức nấu ăn cá nhân này chỉ có thể được tìm thấy và sử dụng bởi người dùng trong nhóm)  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia vào nhóm  
5\. Hậu điều kiện: Công thức nấu ăn cá nhân được tạo ra  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn tạo công thức nấu ăn cho nhóm  
* Step 2: Người dùng điền đầy đủ thông tin như bảng 3.3.4.1  
* Step 3: Người dùng hoàn thành tạo công thức nấu ăn mới  
* Step 4: Hệ thống lưu dữ liệu vào database

##### **UC-04.2: Chỉnh sửa công thức nấu ăn**

1\. Mã Usecase: UC-04.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng/QTV và hệ thống khi chỉnh sửa công thức nấu ăn  
3\. Tác nhân: Người dùng (đối với công thức cá nhân), Quản trị viên (đối với công thức chung)  
4\. Tiền điều kiện: Công thức nấu ăn đã tồn tại  
5\. Hậu điều kiện: Công thức nấu ăn được cập nhật  
6\. Luồng cơ bản:

* Step 1: Người dùng/QTV chọn công thức nấu ăn cần chỉnh sửa
* Step 2: Hệ thống hiển thị thông tin công thức hiện tại
* Step 3: Người dùng/QTV chỉnh sửa các trường trong bảng 3.3.4.1
* Step 4: Người dùng/QTV hoàn thành chỉnh sửa
* Step 5: Hệ thống cập nhật dữ liệu vào database

7\. Luồng thay thế:

* Ở bước 1, nếu người dùng không có quyền chỉnh sửa công thức (công thức chung hoặc công thức của nhóm khác), hệ thống hiển thị thông báo lỗi

##### **UC-04.4: Xóa công thức nấu ăn**

1\. Mã Usecase: UC-04.4  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng/QTV và hệ thống khi xóa công thức nấu ăn  
3\. Tác nhân: Người dùng (đối với công thức cá nhân), Quản trị viên (đối với công thức chung)  
4\. Tiền điều kiện: Công thức nấu ăn đã tồn tại  
5\. Hậu điều kiện: Công thức nấu ăn được xóa khỏi hệ thống  
6\. Luồng cơ bản:

* Step 1: Người dùng/QTV chọn công thức nấu ăn cần xóa
* Step 2: Hệ thống hiển thị xác nhận xóa
* Step 3: Người dùng/QTV xác nhận xóa
* Step 4: Hệ thống xóa công thức khỏi database

7\. Luồng thay thế:

* Ở bước 1, nếu người dùng không có quyền xóa công thức, hệ thống hiển thị thông báo lỗi
* Ở bước 3, nếu người dùng/QTV hủy bỏ, quay lại danh sách công thức

##### **UC-04.5: Tìm kiếm công thức món ăn**

1\. Mã Usecase: UC-04.5  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người và hệ thống khi người dùng tìm kiếm công thức nấu ăn  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Trả về một danh sách các công thức món ăn thỏa mãn  
6\. Luồng cơ bản:  
Step 1: Người dùng nhập vào thanh tìm kiếm để tìm kiếm trong công thức nấu ăn chung và công thức nấu ăn trong nhóm của người dùng (Tìm theo tên món ăn/nguyên liệu).   
Step 2: Hệ thống trả về danh sách các kết quả tìm kiếm

#### **3.3.5 UC-05: Quản lý danh sách thực phẩm**

![][image6]

##### **UC-05.1: Tạo thực phẩm cá nhân**

1\. Mã Usecase: UC-05.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng tạo một thực phẩm mới (Thực phẩm cá nhân này chỉ có thể được tìm thấy và sử dụng bởi người dùng trong nhóm)  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia vào nhóm  
5\. Hậu điều kiện: Thực phẩm cá nhân được tạo ra  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn tạo thực phẩm cho nhóm  
* Step 2: Người dùng điền đầy đủ thông tin như bảng 3.3.5.1  
* Step 3: Hệ thống kiểm tra tên thực phẩm có trùng không  
* Step 4: Người dùng hoàn thành tạo thực phẩm mới  
* Step 5: Hệ thống lưu dữ liệu vào database

7\. Dữ liệu đầu vào:  
*Bảng 3.3.5.1. Đầu vào tạo thực phẩm mới*

| STT | Tên trường | Mô tả | Ví dụ |
| :---- | :---- | :---- | :---- |
| 1 | Tên thực phẩm | Text | Thịt lợn |
| 2 | Danh mục thực phẩm | Chọn từ droplist | Thịt |

##### **UC-05.2: Tạo thực phẩm chung (Admin)**

1\. Mã Usecase: UC-05.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa QTV và hệ thống khi QTV tạo thực phẩm chung cho toàn hệ thống  
3\. Tác nhân: Quản trị viên  
4\. Tiền điều kiện: QTV đã đăng nhập  
5\. Hậu điều kiện: Thực phẩm chung được tạo ra, có thể sử dụng bởi tất cả người dùng  
6\. Luồng cơ bản:

* Step 1: QTV chọn tạo thực phẩm chung
* Step 2: QTV điền đầy đủ thông tin như bảng 3.3.5.1
* Step 3: Hệ thống kiểm tra tên thực phẩm có trùng không
* Step 4: QTV hoàn thành tạo thực phẩm mới
* Step 5: Hệ thống lưu dữ liệu vào database

##### **UC-05.3: Chỉnh sửa thực phẩm**

1\. Mã Usecase: UC-05.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng/QTV và hệ thống khi chỉnh sửa thông tin thực phẩm  
3\. Tác nhân: Người dùng (đối với thực phẩm cá nhân), Quản trị viên (đối với thực phẩm chung)  
4\. Tiền điều kiện: Thực phẩm đã tồn tại  
5\. Hậu điều kiện: Thông tin thực phẩm được cập nhật  
6\. Luồng cơ bản:

* Step 1: Người dùng/QTV chọn thực phẩm cần chỉnh sửa
* Step 2: Hệ thống hiển thị thông tin hiện tại
* Step 3: Người dùng/QTV chỉnh sửa các trường trong bảng 3.3.5.1
* Step 4: Hệ thống kiểm tra tên thực phẩm có trùng không
* Step 5: Người dùng/QTV hoàn thành chỉnh sửa
* Step 6: Hệ thống cập nhật dữ liệu vào database

##### **UC-05.4: Xóa thực phẩm**

1\. Mã Usecase: UC-05.4  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng/QTV và hệ thống khi xóa thực phẩm  
3\. Tác nhân: Người dùng (đối với thực phẩm cá nhân), Quản trị viên (đối với thực phẩm chung)  
4\. Tiền điều kiện: Thực phẩm đã tồn tại và không được sử dụng trong bất kỳ công thức hoặc bếp nào  
5\. Hậu điều kiện: Thực phẩm được xóa khỏi hệ thống  
6\. Luồng cơ bản:

* Step 1: Người dùng/QTV chọn thực phẩm cần xóa
* Step 2: Hệ thống kiểm tra xem thực phẩm có đang được sử dụng không
* Step 3: Hệ thống hiển thị xác nhận xóa
* Step 4: Người dùng/QTV xác nhận xóa
* Step 5: Hệ thống xóa thực phẩm khỏi database

7\. Luồng thay thế:

* Ở bước 2, nếu thực phẩm đang được sử dụng trong công thức hoặc bếp, hệ thống hiển thị thông báo không thể xóa
* Ở bước 4, nếu người dùng/QTV hủy bỏ, quay lại danh sách thực phẩm

##### **UC-05.5: Tìm kiếm thực phẩm**

1\. Mã Usecase: UC-05.5  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi tìm kiếm thực phẩm  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Danh sách thực phẩm phù hợp được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng nhập từ khóa tìm kiếm (tên thực phẩm hoặc danh mục)
* Step 2: Hệ thống tìm kiếm trong thực phẩm chung và thực phẩm của nhóm
* Step 3: Hệ thống hiển thị danh sách kết quả

#### **3.3.6 UC-06: Quản lý nhóm**

![][image7]

##### **UC-06.1: Tạo nhóm**

1\. Mã Usecase: UC-06.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng tạo nhóm gia đình mới  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Nhóm mới được tạo ra, người dùng trở thành trưởng nhóm  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn tạo nhóm mới
* Step 2: Người dùng điền thông tin nhóm theo bảng 3.3.6.1
* Step 3: Người dùng hoàn thành tạo nhóm
* Step 4: Hệ thống tạo nhóm và gán người dùng làm trưởng nhóm
* Step 5: Hệ thống lưu dữ liệu vào database

7\. Dữ liệu đầu vào:  
*Bảng 3.3.6.1. Đầu vào tạo nhóm*

| STT | Tên trường | Mô tả | Bắt buộc | Ví dụ |
| :---- | :---- | :---- | :---- | :---- |
| 1 | Tên nhóm | Text | Y | Gia đình Nguyễn Văn A |
| 2 | Mô tả | Text | N | Nhóm gia đình 4 người |

##### **UC-06.2: Thêm thành viên vào nhóm**

1\. Mã Usecase: UC-06.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi thêm thành viên vào nhóm  
3\. Tác nhân: Trưởng nhóm hoặc thành viên có quyền  
4\. Tiền điều kiện: Nhóm đã tồn tại, người dùng là trưởng nhóm hoặc có quyền thêm thành viên  
5\. Hậu điều kiện: Thành viên mới được thêm vào nhóm, nhận được thông báo  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn thêm thành viên
* Step 2: Người dùng nhập email/số điện thoại của thành viên mới
* Step 3: Hệ thống tìm kiếm tài khoản trong hệ thống
* Step 4: Hệ thống hiển thị thông tin tài khoản tìm được
* Step 5: Người dùng xác nhận thêm thành viên
* Step 6: Hệ thống gửi lời mời tham gia nhóm
* Step 7: Thành viên mới nhận thông báo và chấp nhận lời mời
* Step 8: Hệ thống thêm thành viên vào nhóm

7\. Luồng thay thế:

* Ở bước 3, nếu không tìm thấy tài khoản, hiển thị thông báo "Không tìm thấy người dùng"
* Ở bước 7, nếu thành viên từ chối lời mời, hệ thống thông báo cho người gửi lời mời

##### **UC-06.3: Xóa thành viên khỏi nhóm**

1\. Mã Usecase: UC-06.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi xóa thành viên khỏi nhóm  
3\. Tác nhân: Trưởng nhóm  
4\. Tiền điều kiện: Nhóm có ít nhất 2 thành viên  
5\. Hậu điều kiện: Thành viên bị xóa khỏi nhóm, nhận được thông báo  
6\. Luồng cơ bản:

* Step 1: Trưởng nhóm chọn thành viên cần xóa
* Step 2: Hệ thống hiển thị xác nhận xóa
* Step 3: Trưởng nhóm xác nhận xóa
* Step 4: Hệ thống xóa thành viên khỏi nhóm
* Step 5: Hệ thống gửi thông báo cho thành viên bị xóa

7\. Luồng thay thế:

* Ở bước 1, không thể xóa trưởng nhóm

##### **UC-06.4: Rời khỏi nhóm**

1\. Mã Usecase: UC-06.4  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi người dùng tự rời khỏi nhóm  
3\. Tác nhân: Người dùng (thành viên nhóm)  
4\. Tiền điều kiện: Người dùng là thành viên của nhóm  
5\. Hậu điều kiện: Người dùng rời khỏi nhóm, các thành viên còn lại nhận được thông báo  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn rời khỏi nhóm
* Step 2: Hệ thống hiển thị xác nhận
* Step 3: Người dùng xác nhận rời nhóm
* Step 4: Hệ thống xóa người dùng khỏi nhóm
* Step 5: Hệ thống gửi thông báo cho các thành viên còn lại

7\. Luồng thay thế:

* Nếu người dùng là trưởng nhóm và nhóm có thành viên khác, hệ thống yêu cầu chuyển quyền trưởng nhóm trước
* Nếu người dùng là thành viên duy nhất, nhóm sẽ bị xóa

##### **UC-06.5: Chuyển quyền trưởng nhóm**

1\. Mã Usecase: UC-06.5  
2\. Mô tả: Usecase mô tả mối quan hệ giữa trưởng nhóm và hệ thống khi chuyển quyền trưởng nhóm cho thành viên khác  
3\. Tác nhân: Trưởng nhóm  
4\. Tiền điều kiện: Nhóm có ít nhất 2 thành viên  
5\. Hậu điều kiện: Quyền trưởng nhóm được chuyển cho thành viên khác  
6\. Luồng cơ bản:

* Step 1: Trưởng nhóm chọn chuyển quyền
* Step 2: Trưởng nhóm chọn thành viên mới làm trưởng nhóm
* Step 3: Hệ thống hiển thị xác nhận
* Step 4: Trưởng nhóm xác nhận
* Step 5: Hệ thống cập nhật vai trò
* Step 6: Hệ thống gửi thông báo cho tất cả thành viên

##### **UC-06.6: Xem thông tin nhóm**

1\. Mã Usecase: UC-06.6  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi xem thông tin nhóm  
3\. Tác nhân: Người dùng (thành viên nhóm)  
4\. Tiền điều kiện: Người dùng là thành viên của nhóm  
5\. Hậu điều kiện: Thông tin nhóm được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình thông tin nhóm
* Step 2: Hệ thống hiển thị thông tin theo bảng 3.3.6.2

7\. Dữ liệu đầu ra:  
*Bảng 3.3.6.2. Hiển thị thông tin nhóm*

| STT | Trường hiển thị | Mô tả |
| :---- | :---- | :---- |
| 1 | Tên nhóm | Tên của nhóm |
| 2 | Trưởng nhóm | Tên và avatar của trưởng nhóm |
| 3 | Số thành viên | Tổng số thành viên |
| 4 | Danh sách thành viên | Tên, avatar, vai trò của từng thành viên |
| 5 | Ngày tạo | Ngày tạo nhóm |

##### **UC-06.7: Xóa nhóm**

1\. Mã Usecase: UC-06.7  
2\. Mô tả: Usecase mô tả mối quan hệ giữa trưởng nhóm và hệ thống khi xóa nhóm  
3\. Tác nhân: Trưởng nhóm  
4\. Tiền điều kiện: Người dùng là trưởng nhóm  
5\. Hậu điều kiện: Nhóm và tất cả dữ liệu liên quan bị xóa, thành viên nhận thông báo  
6\. Luồng cơ bản:

* Step 1: Trưởng nhóm chọn xóa nhóm
* Step 2: Hệ thống hiển thị cảnh báo về việc xóa tất cả dữ liệu
* Step 3: Trưởng nhóm xác nhận xóa
* Step 4: Hệ thống xóa nhóm và dữ liệu liên quan
* Step 5: Hệ thống gửi thông báo cho tất cả thành viên

#### **3.3.7 UC-07: Thống kê và báo cáo**

![][image8]

##### **UC-07.1: Xem báo cáo mua sắm**

1\. Mã Usecase: UC-07.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi xem báo cáo mua sắm  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia nhóm và có dữ liệu mua sắm  
5\. Hậu điều kiện: Báo cáo mua sắm được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình báo cáo
* Step 2: Người dùng chọn loại báo cáo "Mua sắm"
* Step 3: Người dùng chọn khoảng thời gian (tuần/tháng/năm)
* Step 4: Hệ thống tính toán và hiển thị báo cáo theo bảng 3.3.7.1

7\. Dữ liệu đầu ra:  
*Bảng 3.3.7.1. Báo cáo mua sắm*

| STT | Thông tin | Mô tả |
| :---- | :---- | :---- |
| 1 | Tổng số lần đi chợ | Số lần hoàn thành mua sắm |
| 2 | Top 10 thực phẩm mua nhiều nhất | Danh sách và số lượng |
| 3 | Biểu đồ xu hướng mua sắm | Biểu đồ theo thời gian |
| 4 | Thành viên đi chợ nhiều nhất | Tên và số lần |

##### **UC-07.2: Xem báo cáo tiêu thụ thực phẩm**

1\. Mã Usecase: UC-07.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi xem báo cáo tiêu thụ  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia nhóm và có dữ liệu thực phẩm  
5\. Hậu điều kiện: Báo cáo tiêu thụ được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình báo cáo
* Step 2: Người dùng chọn loại báo cáo "Tiêu thụ"
* Step 3: Người dùng chọn khoảng thời gian (tuần/tháng/năm)
* Step 4: Hệ thống tính toán và hiển thị báo cáo theo bảng 3.3.7.2

7\. Dữ liệu đầu ra:  
*Bảng 3.3.7.2. Báo cáo tiêu thụ*

| STT | Thông tin | Mô tả |
| :---- | :---- | :---- |
| 1 | Tổng số thực phẩm đã tiêu thụ | Số lượng và loại |
| 2 | Tỷ lệ lãng phí | % thực phẩm hết hạn chưa dùng |
| 3 | Top món ăn được nấu nhiều nhất | Danh sách món ăn |
| 4 | Biểu đồ phân bổ danh mục | Biểu đồ tròn theo danh mục thực phẩm |

##### **UC-07.3: Xem báo cáo thực đơn**

1\. Mã Usecase: UC-07.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi xem báo cáo thực đơn  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã tham gia nhóm và có dữ liệu thực đơn  
5\. Hậu điều kiện: Báo cáo thực đơn được hiển thị  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình báo cáo
* Step 2: Người dùng chọn loại báo cáo "Thực đơn"
* Step 3: Người dùng chọn khoảng thời gian (tuần/tháng)
* Step 4: Hệ thống hiển thị thống kê theo bảng 3.3.7.3

7\. Dữ liệu đầu ra:  
*Bảng 3.3.7.3. Báo cáo thực đơn*

| STT | Thông tin | Mô tả |
| :---- | :---- | :---- |
| 1 | Tổng số bữa ăn đã lên lịch | Số lượng bữa |
| 2 | Tỷ lệ hoàn thành | % bữa ăn đã thực hiện |
| 3 | Món ăn phổ biến | Top 10 món được chọn nhiều |
| 4 | Thống kê theo bữa | Sáng/Trưa/Tối |

#### **3.3.8 UC-08: Quản trị người dùng**

![][image9]

##### **UC-08.1: Xem danh sách người dùng**

1\. Mã Usecase: UC-08.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa QTV và hệ thống khi xem danh sách người dùng  
3\. Tác nhân: Quản trị viên  
4\. Tiền điều kiện: QTV đã đăng nhập  
5\. Hậu điều kiện: Danh sách người dùng được hiển thị  
6\. Luồng cơ bản:

* Step 1: QTV mở màn hình quản lý người dùng
* Step 2: Hệ thống hiển thị danh sách người dùng theo bảng 3.3.8.1
* Step 3: QTV có thể tìm kiếm, lọc theo trạng thái

7\. Dữ liệu đầu ra:  
*Bảng 3.3.8.1. Hiển thị danh sách người dùng*

| STT | Trường hiển thị | Mô tả |
| :---- | :---- | :---- |
| 1 | ID người dùng | Mã định danh |
| 2 | Tên người dùng | Họ và tên |
| 3 | Email | Địa chỉ email |
| 4 | Số điện thoại | Số điện thoại |
| 5 | Trạng thái | Hoạt động / Bị khóa |
| 6 | Ngày đăng ký | Ngày tạo tài khoản |

##### **UC-08.2: Khóa/Mở khóa tài khoản**

1\. Mã Usecase: UC-08.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa QTV và hệ thống khi khóa hoặc mở khóa tài khoản  
3\. Tác nhân: Quản trị viên  
4\. Tiền điều kiện: Tài khoản người dùng tồn tại  
5\. Hậu điều kiện: Trạng thái tài khoản được thay đổi  
6\. Luồng cơ bản:

* Step 1: QTV chọn tài khoản cần khóa/mở khóa
* Step 2: QTV chọn hành động (khóa/mở khóa)
* Step 3: Hệ thống hiển thị xác nhận
* Step 4: QTV xác nhận
* Step 5: Hệ thống cập nhật trạng thái tài khoản
* Step 6: Hệ thống gửi email thông báo cho người dùng

##### **UC-08.3: Xóa tài khoản**

1\. Mã Usecase: UC-08.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa QTV và hệ thống khi xóa tài khoản người dùng  
3\. Tác nhân: Quản trị viên  
4\. Tiền điều kiện: Tài khoản người dùng tồn tại  
5\. Hậu điều kiện: Tài khoản và dữ liệu liên quan bị xóa  
6\. Luồng cơ bản:

* Step 1: QTV chọn tài khoản cần xóa
* Step 2: Hệ thống hiển thị cảnh báo về việc xóa dữ liệu
* Step 3: QTV xác nhận xóa
* Step 4: Hệ thống xóa tài khoản và dữ liệu liên quan
* Step 5: Hệ thống ghi log hành động

#### **3.3.9 UC-09: Xác thực và phân quyền**

##### **UC-09.1: Đăng ký tài khoản**

1\. Mã Usecase: UC-09.1  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng mới và hệ thống khi đăng ký tài khoản  
3\. Tác nhân: Người dùng chưa có tài khoản  
4\. Tiền điều kiện: Không có  
5\. Hậu điều kiện: Tài khoản mới được tạo, người dùng có thể đăng nhập  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn đăng ký tài khoản
* Step 2: Người dùng điền thông tin theo bảng 3.3.9.1
* Step 3: Hệ thống kiểm tra tính hợp lệ của dữ liệu
* Step 4: Hệ thống kiểm tra email/số điện thoại đã tồn tại chưa
* Step 5: Hệ thống gửi mã xác thực qua email/SMS
* Step 6: Người dùng nhập mã xác thực
* Step 7: Hệ thống xác thực mã và tạo tài khoản
* Step 8: Hệ thống gửi email chào mừng

7\. Luồng thay thế:

* Ở bước 4, nếu email/số điện thoại đã tồn tại, hiển thị thông báo lỗi
* Ở bước 6, nếu mã không đúng hoặc hết hạn, cho phép gửi lại mã

8\. Dữ liệu đầu vào:  
*Bảng 3.3.9.1. Đầu vào đăng ký tài khoản*

| STT | Tên trường | Mô tả | Bắt buộc | Ví dụ |
| :---- | :---- | :---- | :---- | :---- |
| 1 | Họ và tên | Text | Y | Nguyễn Văn A |
| 2 | Email | Email format | Y | nguyenvana@example.com |
| 3 | Số điện thoại | 10 số | Y | 0123456789 |
| 4 | Mật khẩu | Min 8 ký tự, có chữ hoa, số | Y | Password123! |
| 5 | Xác nhận mật khẩu | Phải giống mật khẩu | Y | Password123! |

##### **UC-09.2: Đăng nhập**

1\. Mã Usecase: UC-09.2  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi đăng nhập  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã có tài khoản  
5\. Hậu điều kiện: Người dùng được xác thực và truy cập vào hệ thống  
6\. Luồng cơ bản:

* Step 1: Người dùng mở ứng dụng
* Step 2: Người dùng nhập email và mật khẩu
* Step 3: Hệ thống xác thực thông tin
* Step 4: Hệ thống tạo token và lưu session
* Step 5: Hệ thống chuyển người dùng vào màn hình chính

7\. Luồng thay thế:

* Ở bước 3, nếu thông tin không đúng, hiển thị thông báo lỗi
* Ở bước 3, nếu tài khoản bị khóa, hiển thị thông báo tài khoản bị khóa

##### **UC-09.3: Đăng xuất**

1\. Mã Usecase: UC-09.3  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi đăng xuất  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Session bị xóa, người dùng về màn hình đăng nhập  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn đăng xuất
* Step 2: Hệ thống hiển thị xác nhận
* Step 3: Người dùng xác nhận
* Step 4: Hệ thống xóa token và session
* Step 5: Hệ thống chuyển về màn hình đăng nhập

##### **UC-09.4: Quên mật khẩu**

1\. Mã Usecase: UC-09.4  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi quên mật khẩu  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã có tài khoản  
5\. Hậu điều kiện: Mật khẩu được đặt lại  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn "Quên mật khẩu"
* Step 2: Người dùng nhập email đăng ký
* Step 3: Hệ thống kiểm tra email có tồn tại không
* Step 4: Hệ thống gửi mã xác thực qua email
* Step 5: Người dùng nhập mã xác thực
* Step 6: Hệ thống xác thực mã
* Step 7: Người dùng nhập mật khẩu mới
* Step 8: Hệ thống cập nhật mật khẩu

7\. Luồng thay thế:

* Ở bước 3, nếu email không tồn tại, hiển thị thông báo
* Ở bước 5, nếu mã không đúng, cho phép thử lại (tối đa 3 lần)

##### **UC-09.5: Cập nhật thông tin cá nhân**

1\. Mã Usecase: UC-09.5  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi cập nhật thông tin  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Thông tin cá nhân được cập nhật  
6\. Luồng cơ bản:

* Step 1: Người dùng mở màn hình thông tin cá nhân
* Step 2: Hệ thống hiển thị thông tin hiện tại
* Step 3: Người dùng chỉnh sửa thông tin (tên, số điện thoại, avatar)
* Step 4: Người dùng xác nhận cập nhật
* Step 5: Hệ thống lưu thông tin mới

##### **UC-09.6: Đổi mật khẩu**

1\. Mã Usecase: UC-09.6  
2\. Mô tả: Usecase mô tả mối quan hệ giữa người dùng và hệ thống khi đổi mật khẩu  
3\. Tác nhân: Người dùng  
4\. Tiền điều kiện: Người dùng đã đăng nhập  
5\. Hậu điều kiện: Mật khẩu được thay đổi  
6\. Luồng cơ bản:

* Step 1: Người dùng chọn đổi mật khẩu
* Step 2: Người dùng nhập mật khẩu hiện tại
* Step 3: Hệ thống xác thực mật khẩu hiện tại
* Step 4: Người dùng nhập mật khẩu mới và xác nhận
* Step 5: Hệ thống kiểm tra mật khẩu mới hợp lệ
* Step 6: Hệ thống cập nhật mật khẩu
* Step 7: Hệ thống gửi email thông báo đổi mật khẩu

7\. Luồng thay thế:

* Ở bước 3, nếu mật khẩu hiện tại không đúng, hiển thị lỗi
* Ở bước 5, nếu mật khẩu mới không hợp lệ, hiển thị yêu cầu

# Yêu cầu phi chức năng

### **4.1 Hiệu năng**

* **Thời gian phản hồi:**
  * Các thao tác đơn giản (xem danh sách, tìm kiếm) phải hoàn thành trong vòng 1 giây
  * Các thao tác phức tạp (tạo báo cáo, gợi ý thông minh) phải hoàn thành trong vòng 3 giây
  * Tải trang chính phải hoàn thành trong vòng 2 giây với kết nối 3G

* **Đồng thời:**
  * Hệ thống phải hỗ trợ ít nhất 1000 người dùng truy cập đồng thời
  * Hỗ trợ nhiều thành viên trong cùng một nhóm cập nhật dữ liệu đồng thời mà không xung đột

* **Tối ưu dữ liệu:**
  * Ứng dụng phải tối ưu hóa việc truyền tải dữ liệu, sử dụng cache khi có thể
  * Hình ảnh phải được nén và tối ưu hóa cho thiết bị di động
  * API response size không vượt quá 1MB cho mỗi request

* **Offline mode:**
  * Cho phép xem danh sách mua sắm, thực phẩm trong bếp, công thức đã lưu khi không có mạng
  * Tự động đồng bộ dữ liệu khi có kết nối trở lại
  * Đánh dấu rõ ràng dữ liệu đang chờ đồng bộ

### **4.2 Bảo mật**

* Toàn bộ giao tiếp giữa ứng dụng và server phải được mã hóa bằng HTTPS/TLS.  
* Thông tin tài khoản (mật khẩu, token xác thực) phải được mã hóa và lưu trữ an toàn (sử dụng cơ chế hashing \+ salting, ví dụ bcrypt).  
* Người dùng chỉ có quyền truy cập và chỉnh sửa dữ liệu thuộc về tài khoản hoặc nhóm của mình.  
* Quản trị viên có quyền kiểm soát tài khoản người dùng nhưng không được truy cập dữ liệu cá nhân (danh sách mua sắm, công thức nấu ăn…) trừ khi được cấp phép.

### **4.3 Tính khả dụng**

* Giao diện phải trực quan, dễ thao tác, phù hợp với cả người dùng không am hiểu công nghệ.  
* Ứng dụng hỗ trợ Tiếng Việt.  
* Cung cấp hướng dẫn sử dụng cơ bản ngay trong ứng dụng (onboarding tutorial).  
* Các thông báo nhắc nhở (ví dụ: thực phẩm sắp hết hạn) phải ngắn gọn, dễ hiểu, kịp thời.  
* Đảm bảo ứng dụng hoạt động tốt trên các màn hình điện thoại với kích thước và độ phân giải khác nhau.

### **4.4 Khả năng mở rộng**

* Kiến trúc hệ thống phải hỗ trợ mở rộng theo chiều ngang (horizontal scaling)
* Hỗ trợ thêm các tính năng mới mà không ảnh hưởng đến tính năng hiện tại
* Database phải được thiết kế để dễ dàng thêm bảng và trường mới
* API phải được phiên bản hóa (versioning) để hỗ trợ nhiều phiên bản client

### **4.5 Tương thích**

* **Nền tảng di động:**
  * Android: Hỗ trợ từ phiên bản Android 8.0 (API level 26) trở lên
  * iOS: Hỗ trợ từ phiên bản iOS 13 trở lên

* **Kích thước màn hình:**
  * Hỗ trợ các màn hình từ 4.7 inch đến 6.7 inch
  * Responsive design cho cả điện thoại và tablet

* **Kết nối mạng:**
  * Hoạt động tốt với kết nối 3G trở lên
  * Tối ưu hóa cho mạng di động (giảm thiểu data usage)

### **4.6 Push Notification**

* **Loại thông báo:**
  * Thực phẩm sắp hết hạn (3 ngày trước)
  * Được phân công đi chợ
  * Danh sách mua sắm được cập nhật
  * Hoàn thành đi chợ
  * Lời mời tham gia nhóm
  * Thay đổi vai trò trong nhóm

* **Cơ chế gửi:**
  * Sử dụng Firebase Cloud Messaging (FCM) cho Android
  * Sử dụng Apple Push Notification Service (APNs) cho iOS
  * Người dùng có thể bật/tắt từng loại thông báo
  * Thông báo phải được gửi realtime (delay < 5 giây)

* **Nội dung thông báo:**
  * Ngắn gọn, rõ ràng, có call-to-action
  * Hiển thị thông tin quan trọng (tên thực phẩm, số ngày, tên người)
  * Deep link để mở đúng màn hình liên quan

### **4.7 Đồng bộ dữ liệu**

* Dữ liệu nhóm phải được đồng bộ realtime giữa các thiết bị
* Xử lý xung đột khi nhiều người cập nhật cùng lúc (Last-Write-Wins hoặc Merge)
* Hiển thị thông báo khi dữ liệu được cập nhật bởi người khác
* Hỗ trợ conflict resolution cho các thao tác quan trọng

### **4.8 Khả năng bảo trì**

* Code phải tuân thủ coding standards và best practices của React Native
* Sử dụng TypeScript để tăng tính type-safe
* Documentation đầy đủ cho các component và API
* Unit test coverage ít nhất 70%
* Integration test cho các luồng chính
* Logging đầy đủ để debug và monitor

# Phụ lục

## **5.1 Thiết kế Database (ERD - Tóm tắt)**

### **Các bảng chính:**

1. **User** - Thông tin người dùng
   * id, email, phone, password_hash, full_name, avatar, status, role, created_at

2. **Group** - Thông tin nhóm
   * id, name, description, owner_id, created_at

3. **GroupMember** - Quan hệ người dùng - nhóm
   * id, group_id, user_id, role, joined_at

4. **ShoppingList** - Danh sách mua sắm
   * id, group_id, assignee_id, status, created_by, created_at

5. **ShoppingItem** - Mục trong danh sách mua sắm
   * id, shopping_list_id, food_id, quantity, unit, quantity_bought, status

6. **Food** - Thông tin thực phẩm
   * id, name, category_id, type (common/group), group_id, created_by

7. **FoodCategory** - Danh mục thực phẩm
   * id, name, description

8. **KitchenItem** - Thực phẩm trong bếp
   * id, group_id, food_id, quantity, unit, expiry_date, added_at

9. **Recipe** - Công thức nấu ăn
   * id, name, description, type (common/group), group_id, created_by

10. **RecipeIngredient** - Nguyên liệu công thức
    * id, recipe_id, food_id, quantity, unit, is_main

11. **Meal** - Bữa ăn
    * id, group_id, meal_type (breakfast/lunch/dinner), meal_date, created_by

12. **MealDish** - Món ăn trong bữa
    * id, meal_id, recipe_id, servings

13. **Notification** - Thông báo
    * id, user_id, type, title, message, data, is_read, created_at

## **5.2 API Endpoints (Tóm tắt)**

### **Authentication:**
* POST /api/auth/register - Đăng ký
* POST /api/auth/login - Đăng nhập
* POST /api/auth/logout - Đăng xuất
* POST /api/auth/forgot-password - Quên mật khẩu
* POST /api/auth/reset-password - Đặt lại mật khẩu

### **User:**
* GET /api/users/profile - Xem thông tin cá nhân
* PUT /api/users/profile - Cập nhật thông tin
* PUT /api/users/password - Đổi mật khẩu

### **Group:**
* POST /api/groups - Tạo nhóm
* GET /api/groups - Danh sách nhóm của user
* GET /api/groups/:id - Chi tiết nhóm
* PUT /api/groups/:id - Cập nhật nhóm
* DELETE /api/groups/:id - Xóa nhóm
* POST /api/groups/:id/members - Thêm thành viên
* DELETE /api/groups/:id/members/:userId - Xóa thành viên
* PUT /api/groups/:id/transfer-ownership - Chuyển quyền

### **Shopping List:**
* POST /api/shopping-lists - Tạo danh sách
* GET /api/shopping-lists - Danh sách mua sắm
* GET /api/shopping-lists/:id - Chi tiết
* PUT /api/shopping-lists/:id - Cập nhật
* DELETE /api/shopping-lists/:id - Xóa
* POST /api/shopping-lists/:id/items - Thêm mục
* PUT /api/shopping-lists/:id/items/:itemId - Cập nhật mục
* DELETE /api/shopping-lists/:id/items/:itemId - Xóa mục
* POST /api/shopping-lists/:id/complete - Hoàn thành

### **Kitchen:**
* GET /api/kitchen - Danh sách thực phẩm trong bếp
* POST /api/kitchen - Thêm thực phẩm
* PUT /api/kitchen/:id - Cập nhật thực phẩm
* DELETE /api/kitchen/:id - Xóa thực phẩm
* GET /api/kitchen/expiring - Thực phẩm sắp hết hạn

### **Recipe:**
* GET /api/recipes - Danh sách công thức
* GET /api/recipes/:id - Chi tiết công thức
* POST /api/recipes - Tạo công thức
* PUT /api/recipes/:id - Cập nhật công thức
* DELETE /api/recipes/:id - Xóa công thức
* GET /api/recipes/search - Tìm kiếm công thức
* GET /api/recipes/suggestions - Gợi ý từ bếp

### **Meal:**
* GET /api/meals - Danh sách bữa ăn
* GET /api/meals/:id - Chi tiết bữa ăn
* POST /api/meals - Tạo bữa ăn
* PUT /api/meals/:id - Cập nhật bữa ăn
* DELETE /api/meals/:id - Xóa bữa ăn

### **Food:**
* GET /api/foods - Danh sách thực phẩm
* GET /api/foods/:id - Chi tiết thực phẩm
* POST /api/foods - Tạo thực phẩm
* PUT /api/foods/:id - Cập nhật thực phẩm
* DELETE /api/foods/:id - Xóa thực phẩm
* GET /api/foods/search - Tìm kiếm

### **Report:**
* GET /api/reports/shopping - Báo cáo mua sắm
* GET /api/reports/consumption - Báo cáo tiêu thụ
* GET /api/reports/meal - Báo cáo thực đơn

### **Notification:**
* GET /api/notifications - Danh sách thông báo
* PUT /api/notifications/:id/read - Đánh dấu đã đọc
* PUT /api/notifications/read-all - Đánh dấu tất cả đã đọc

### **Admin:**
* GET /api/admin/users - Danh sách người dùng
* PUT /api/admin/users/:id/status - Khóa/mở khóa
* DELETE /api/admin/users/:id - Xóa người dùng
* POST /api/admin/foods - Tạo thực phẩm chung
* POST /api/admin/recipes - Tạo công thức chung

## **5.3 Cấu trúc thư mục React Native (Đề xuất)**

```
src/
├── api/                  # API client và endpoints
├── assets/              # Hình ảnh, fonts, icons
├── components/          # Shared components
│   ├── common/         # Button, Input, Card, etc.
│   ├── forms/          # Form components
│   └── layouts/        # Layout components
├── config/             # App configuration
├── constants/          # Constants, enums
├── contexts/           # React contexts
├── hooks/              # Custom hooks
├── navigation/         # Navigation setup
├── screens/            # Screen components
│   ├── Auth/          # Login, Register, ForgotPassword
│   ├── Home/
│   ├── Shopping/
│   ├── Kitchen/
│   ├── Recipe/
│   ├── Meal/
│   ├── Group/
│   ├── Report/
│   └── Profile/
├── services/           # Business logic, API services
├── store/              # State management (Redux/MobX)
├── theme/              # Colors, typography, spacing
├── types/              # TypeScript types
├── utils/              # Utility functions
└── App.tsx
```

## **5.4 Công nghệ đề xuất**

### **Frontend (React Native):**
* React Native 0.72+
* TypeScript
* React Navigation 6.x
* Redux Toolkit / Zustand (state management)
* React Query / SWR (data fetching)
* Axios (HTTP client)
* React Hook Form (form handling)
* Yup / Zod (validation)
* React Native Firebase (push notifications)
* AsyncStorage (local storage)
* React Native Vector Icons
* React Native Paper / NativeBase (UI library)

### **Backend:**
* Node.js + Express / NestJS
* TypeScript
* PostgreSQL / MySQL
* Prisma / TypeORM (ORM)
* JWT (authentication)
* Socket.io (realtime sync)
* Firebase Admin SDK (push notifications)
* Multer (file upload)
* Joi / class-validator (validation)

### **DevOps:**
* Docker
* GitHub Actions / GitLab CI
* AWS / Google Cloud / Azure
* Nginx (reverse proxy)

## **5.5 Các màn hình chính (Screen List)**

1. **Authentication:**
   * Login Screen
   * Register Screen
   * Forgot Password Screen
   * Reset Password Screen

2. **Home:**
   * Dashboard Screen (tổng quan nhóm, thông báo)

3. **Shopping:**
   * Shopping List Screen (danh sách mua sắm)
   * Shopping Detail Screen (chi tiết danh sách)
   * Add Shopping Item Screen

4. **Kitchen:**
   * Kitchen Screen (danh sách thực phẩm trong bếp)
   * Add Kitchen Item Screen
   * Kitchen Item Detail Screen
   * Expiring Items Screen (thực phẩm sắp hết hạn)

5. **Recipe:**
   * Recipe List Screen
   * Recipe Detail Screen
   * Add/Edit Recipe Screen
   * Recipe Search Screen

6. **Meal:**
   * Meal Calendar Screen
   * Add Meal Screen
   * Meal Detail Screen

7. **Food Management:**
   * Food List Screen
   * Add/Edit Food Screen

8. **Group:**
   * Group List Screen
   * Group Detail Screen
   * Create Group Screen
   * Add Member Screen
   * Member List Screen

9. **Report:**
   * Shopping Report Screen
   * Consumption Report Screen
   * Meal Report Screen

10. **Profile:**
    * Profile Screen
    * Edit Profile Screen
    * Change Password Screen
    * Settings Screen
    * Notification Settings Screen

11. **Admin:**
    * User Management Screen
    * Food Management Screen
    * Recipe Management Screen

## **5.6 Quy tắc nghiệp vụ quan trọng**

1. **Nhóm (Group):**
   * Mỗi người dùng có thể tham gia nhiều nhóm
   * Mỗi nhóm có một trưởng nhóm (owner)
   * Trưởng nhóm có thể chuyển quyền cho thành viên khác
   * Khi xóa nhóm, tất cả dữ liệu liên quan (danh sách mua sắm, thực phẩm trong bếp, thực đơn) sẽ bị xóa

2. **Danh sách mua sắm:**
   * Danh sách mua sắm thuộc về một nhóm
   * Có thể phân công cho một thành viên cụ thể
   * Khi tick mua xong, nếu số lượng mua < số lượng cần, tự động tạo mục mới cho phần còn thiếu

3. **Thực phẩm trong bếp:**
   * Thực phẩm thuộc về nhóm, tất cả thành viên đều thấy
   * Gửi thông báo trước 3 ngày khi sắp hết hạn
   * Không thể xóa thực phẩm đang được sử dụng trong công thức

4. **Công thức nấu ăn:**
   * Có 2 loại: công thức chung (admin tạo) và công thức nhóm (thành viên tạo)
   * Công thức nhóm chỉ nhóm đó thấy được
   * Phải có ít nhất 1 nguyên liệu chính

5. **Thực đơn:**
   * Thực đơn thuộc về nhóm
   * Mỗi bữa ăn có thể có nhiều món
   * Gợi ý món ăn dựa trên thực phẩm trong bếp

6. **Thông báo:**
   * Thông báo hết hạn: gửi mỗi ngày vào 8:00 sáng
   * Thông báo phân công: gửi ngay khi được phân công
   * Người dùng có thể tắt từng loại thông báo