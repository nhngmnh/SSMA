# 20251 - CHỦ ĐỀ BÀI TẬP LỚN

**IT4788 – Phát triển ứng dụng đa nền tảng**

**Tên đề tài:** Xây dựng ứng dụng di động đa nền tảng "Đi chợ tiện lợi"

---

## Mục lục

- [A. Mô tả API Backend](#a-mô-tả-api-backend)
  - [Đường dẫn cơ bản](#đường-dẫn-cơ-bản)
  - [Danh sách mã Response](#danh-sách-mã-response)
  - [Danh sách API](#danh-sách-api)
    - [1. User Management](#1-user-management)
    - [2. Group Management](#2-group-management)
    - [3. Admin - Category Management](#3-admin---category-management)
    - [4. Admin - Unit Management](#4-admin---unit-management)
    - [5. Food Management](#5-food-management)
    - [6. Fridge Management](#6-fridge-management)
    - [7. Shopping List Management](#7-shopping-list-management)
    - [8. Meal Plan Management](#8-meal-plan-management)
    - [9. Recipe Management](#9-recipe-management)
    - [10. Report & Statistics](#10-report--statistics)
    - [11. Notification Management](#11-notification-management)
- [B. Chi tiết Use Cases và Luồng nghiệp vụ](#b-chi-tiết-use-cases-và-luồng-nghiệp-vụ)
  - [1. Quản lý danh sách mua sắm](#1-quản-lý-danh-sách-mua-sắm)
  - [2. Quản lý dự định bữa ăn](#2-quản-lý-dự-định-bữa-ăn)
  - [3. Quản lý thực phẩm trong bếp](#3-quản-lý-thực-phẩm-trong-bếp)
  - [4. Quản lý công thức nấu ăn](#4-quản-lý-công-thức-nấu-ăn)
  - [5. Quản lý nhóm](#5-quản-lý-nhóm)
- [C. Lưu ý kỹ thuật](#c-lưu-ý-kỹ-thuật)
- [D. Workflow Examples](#d-workflow-examples)
- [E. Best Practices](#e-best-practices)
- [F. Deployment & Environment](#f-deployment--environment)
- [G. Changelog](#g-changelog)

---

## A. Mô tả API Backend

### Đường dẫn cơ bản

```
https://ABC.def/it4788/
```

> **Lưu ý:** Tên miền `ABC.def` tự thiết lập, cho phép local.

Ví dụ sử dụng API Login:
```
https://ABC.def/it4788/login?...
```

---

## Danh sách mã Response

| Code | Message |
|------|---------|
| 00005 | Vui lòng cung cấp đầy đủ thông tin để gửi mã. |
| 00006 | Truy cập bị từ chối. Không có token được cung cấp. |
| 00007 | ID người dùng không hợp lệ. |
| 00008 | Đã xảy ra lỗi máy chủ nội bộ, vui lòng thử lại. |
| 00009 | Không thể tìm thấy người dùng đã xác minh với mã và ID được cung cấp. Hãy đảm bảo rằng tài khoản đã được xác minh và kích hoạt. |
| 00011 | Phiên của bạn đã hết hạn, vui lòng đăng nhập lại. |
| 00012 | Token không hợp lệ. Token có thể đã hết hạn. |
| 00017 | Truy cập bị từ chối. Bạn không có quyền truy cập. |
| 00019 | Truy cập bị từ chối. Bạn không có quyền truy cập. |
| 00021 | Truy cập bị từ chối. Bạn không có quyền truy cập. |
| 00022 | Không có ID được cung cấp trong tham số. Vui lòng nhập một ID. |
| 00023 | ID được cung cấp không phải là một đối tượng ID hợp lệ. |
| 00024 | Quá nhiều yêu cầu. |
| 00025 | Vui lòng cung cấp tất cả các trường bắt buộc! |
| 00026 | Vui lòng cung cấp một địa chỉ email hợp lệ! |
| 00027 | Vui lòng cung cấp mật khẩu dài hơn 6 ký tự và ngắn hơn 20 ký tự. |
| 00028 | Vui lòng cung cấp một tên dài hơn 3 ký tự và ngắn hơn 30 ký tự. |
| 00029 | Vui lòng cung cấp một địa chỉ email hợp lệ! |
| 00032 | Một tài khoản với địa chỉ email này đã tồn tại. |
| 00035 | Bạn đã đăng ký thành công. |
| 00036 | Không tìm thấy tài khoản với địa chỉ email này. |
| 00038 | Vui lòng cung cấp tất cả các trường bắt buộc! |
| 00039 | Vui lòng cung cấp một địa chỉ email hợp lệ! |
| 00040 | Vui lòng cung cấp mật khẩu dài hơn 6 ký tự và ngắn hơn 20 ký tự. |
| 00042 | Không tìm thấy tài khoản với địa chỉ email này. |
| 00043 | Email của bạn chưa được kích hoạt, vui lòng đăng ký trước. |
| 00044 | Email của bạn chưa được xác minh, vui lòng xác minh email của bạn. |
| 00045 | Bạn đã nhập một email hoặc mật khẩu không hợp lệ. |
| 00047 | Bạn đã đăng nhập thành công. |
| 00048 | Mã đã được gửi đến email của bạn thành công. |
| 00050 | Đăng xuất thành công. |
| 00052 | Không thể tìm thấy người dùng. |
| 00053 | Vui lòng gửi một mã xác nhận. |
| 00054 | Mã bạn nhập không khớp với mã chúng tôi đã gửi đến email của bạn. Vui lòng kiểm tra lại. |
| 00055 | Token không hợp lệ. Token có thể đã hết hạn. |
| 00058 | Địa chỉ email của bạn đã được xác minh thành công. |
| 00059 | Vui lòng cung cấp token làm mới. |
| 00061 | Token được cung cấp không khớp với người dùng, vui lòng đăng nhập. |
| 00062 | Token đã hết hạn, vui lòng đăng nhập. |
| 00063 | Không thể xác minh token, vui lòng đăng nhập. |
| 00065 | Token đã được làm mới thành công. |
| 00066 | Vui lòng cung cấp một mật khẩu dài hơn 6 và ngắn hơn 20 ký tự. |
| 00068 | Mật khẩu mới đã được tạo thành công. |
| 00069 | Vui lòng cung cấp mật khẩu cũ và mới dài hơn 6 ký tự và ngắn hơn 20 ký tự. |
| 00072 | Mật khẩu cũ của bạn không khớp với mật khẩu bạn nhập, vui lòng nhập mật khẩu đúng. |
| 00073 | Mật khẩu mới của bạn không nên giống với mật khẩu cũ, vui lòng thử một mật khẩu khác. |
| 00076 | Mật khẩu của bạn đã được thay đổi thành công. |
| 00077 | Vui lòng cung cấp một tên dài hơn 3 ký tự và ngắn hơn 30 ký tự. |
| 00078 | Các tùy chọn giới tính hợp lệ, female-male-other, vui lòng cung cấp một trong số chúng. |
| 00079 | Các tùy chọn ngôn ngữ hợp lệ, tr-en, vui lòng cung cấp một trong số chúng. |
| 00080 | Vui lòng cung cấp một ngày sinh hợp lệ. |
| 00081 | Vui lòng cung cấp một tên người dùng dài hơn 3 ký tự và ngắn hơn 15 ký tự. |
| 00084 | Đã có một người dùng với tên người dùng này, vui lòng nhập tên khác. |
| 00086 | Thông tin hồ sơ của bạn đã được thay đổi thành công. |
| 00089 | Thông tin người dùng đã được lấy thành công. |
| 00092 | Tài khoản của bạn đã bị xóa thành công. |
| 00093 | Không thể tạo nhóm, bạn đã thuộc về một nhóm rồi. |
| 00095 | Tạo nhóm thành công. |
| 00096 | Bạn không thuộc về nhóm nào. |
| 00098 | Thành công. |
| 00099 | Người này đã thuộc về một nhóm. |
| 00099 X | Không tồn tại user này. |
| 00100 | Thiếu username. |
| 00102 | Người dùng thêm vào nhóm thành công. |
| 00103 | Người này chưa vào nhóm nào. |
| 00104 | Bạn không phải admin, không thể xóa. |
| 00106 | Xóa thành công. |
| 00107 | Thiếu username. |
| 00109 | Lấy log hệ thống thành công. |
| 00110 | Lấy các unit thành công. |
| 00112 | Thiếu thông tin tên của đơn vị. |
| 00113 | Đã tồn tại đơn vị có tên này. |
| 00114 | Server error. |
| 00115 | Server error. |
| 00116 | Tạo đơn vị thành công. |
| 00117 | Thiếu thông tin name cũ, name mới. |
| 00118 | Tên cũ trùng với tên mới. |
| 00119 | Không tìm thấy đơn vị với tên cung cấp. |
| 00120 | Server error. |
| 00121 | Server error. |
| 00122 | Sửa đổi đơn vị thành công. |
| 00123 | Thiếu thông tin tên của đơn vị. |
| 00125 | Không tìm thấy đơn vị với tên cung cấp. |
| 00126 | Server error. |
| 00127 | Server error. |
| 00128 | Xóa đơn vị thành công. |
| 00129 | Lấy các category thành công. |
| 00131 | Thiếu thông tin tên của category. |
| 00132 | Đã tồn tại category có tên này. |
| 00133 | Server error. |
| 00134 | Server error. |
| 00135 | Tạo category thành công. |
| 00136 | Thiếu thông tin name cũ, name mới. |
| 00137 | Tên cũ trùng với tên mới. |
| 00138 | Không tìm thấy category với tên cung cấp. |
| 00138 X | Tên mới đã tồn tại. |
| 00139 | Server error. |
| 00140 | Server error. |
| 00141 | Sửa đổi category thành công. |
| 00142 | Thiếu thông tin tên của category. |
| 00143 | Không tìm thấy category với tên cung cấp. |
| 00144 | Server error. |
| 00145 | Server error. |
| 00146 | Xóa category thành công. |
| 00147 | Vui lòng cung cấp tất cả các trường bắt buộc! |
| 00148 | Vui lòng cung cấp tên của thực phẩm hợp lệ! |
| 00149 | Vui lòng cung cấp tên của category của thực phẩm. |
| 00150 | Vui lòng cung cấp tên đơn vị đo của thực phẩm. |
| 00151 | Đã tồn tại thức ăn với tên này. |
| 00152 | Server error. |
| 00153 | Không tìm thấy đơn vị với tên cung cấp. |
| 00154 | Server error. |
| 00155 | Không tìm thấy category với tên cung cấp. |
| 00156 X | Hãy vào nhóm trước để tạo thực phẩm. |
| 00157 | Server error. |
| 00158 | Đăng tải ảnh thất bại. |
| 00159 | Server error. |
| 00160 | Tạo thực phẩm thành công. |
| 00161 | Vui lòng cung cấp tất cả các trường bắt buộc! |
| 00162 | Vui lòng cung cấp tên thực phẩm hợp lệ! |
| 00163 | Vui lòng cung cấp ít nhất một trong các trường sau, newName, newCategory, newUnit. |
| 00164 | Vui lòng cung cấp một danh mục mới hợp lệ cho thực phẩm. |
| 00165 | Vui lòng cung cấp một đơn vị mới hợp lệ cho thực phẩm. |
| 00166 | Vui lòng cung cấp một tên mới hợp lệ cho thực phẩm. |
| 00167 | Thực phẩm với tên đã cung cấp không tồn tại. |
| 00167 X | Bạn không có quyền chỉnh sửa. |
| 00168 | Server error. |
| 00169 | Không tìm thấy đơn vị với tên đã cung cấp. |
| 00171 | Không tìm thấy danh mục với tên đã cung cấp. |
| 00173 | Một thực phẩm với tên này đã tồn tại. |
| 00178 | Thành công. |
| 00179 | Vui lòng cung cấp tên thực phẩm. |
| 00180 | Không tìm thấy thực phẩm với tên đã cung cấp. |
| 00181 | Bạn không có quyền. |
| 00184 | Xóa thực phẩm thành công. |
| 00185 | Bạn chưa vào nhóm nào. |
| 00188 | Lấy danh sách thực phẩm thành công. |
| 00190 | Vui lòng cung cấp một tên thực phẩm hợp lệ! |
| 00191 | Vui lòng cung cấp một giá trị 'sử dụng trong khoảng' hợp lệ! |
| 00192 | Vui lòng cung cấp một số lượng hợp lệ! |
| 00193 | Định dạng ghi chú không hợp lệ! |
| 00194 | Thực phẩm không tồn tại. |
| 00196 | Người dùng không có quyền do không thuộc nhóm. |
| 00198 | Thực phẩm không thuộc quyền quản trị của nhóm. |
| 00199 | Mục trong tủ lạnh cho thực phẩm đã tồn tại. |
| 00202 | Mục trong tủ lạnh được tạo thành công. |
| 00203 | Vui cung cấp tất cả các trường cần thiết. |
| 00204 | Vui lòng cung cấp id của item tủ lạnh. |
| 00204 X | Vui lòng cung cấp ít nhất một trong các trường sau, newQuantity, newNote, newUseWithin. |
| 00205 | Vui lòng cung cấp một giá trị 'sử dụng trong' hợp lệ! |
| 00206 | Vui lòng cung cấp một lượng hợp lệ! |
| 00207 | Định dạng ghi chú mới không hợp lệ! |
| 00207 | Định dạng tên thức ăn mới không hợp lệ! |
| 00208 | Thực phẩm không tồn tại. |
| 00210 | Người dùng không thuộc bất kỳ nhóm nào. |
| 00212 | Tủ lạnh không thuộc quản trị viên nhóm. |
| 00213 | Mục tủ lạnh không tồn tại. |
| 00214 X | Tên thực phẩm mới không tồn tại. |
| 00216 | Cập nhật mục tủ lạnh thành công. |
| 00217 | Vui lòng cung cấp tên thực phẩm. |
| 00218 | Không tìm thấy thực phẩm với tên đã cung cấp. |
| 00219 | Bạn không có quyền. |
| 00221 | Mục trong tủ lạnh liên kết với thực phẩm này chưa được tạo. |
| 00224 | Xóa mục trong tủ lạnh thành công. |
| 00225 | Bạn chưa vào nhóm nào. |
| 00228 | Lấy danh sách đồ tủ lạnh thành công. |
| 00229 | Vui lòng cung cấp tên thực phẩm. |
| 00230 | Không tìm thấy thực phẩm với tên đã cung cấp. |
| 00232 | Bạn không có quyền. |
| 00234 | Mục trong tủ lạnh liên kết với thực phẩm này chưa được tạo. |
| 00237 | Lấy item cụ thể thành công. |
| 00238 | Vui cung cấp tất cả các trường cần thiết. |
| 00239 | Vui lòng cung cấp tên. |
| 00240 | Vui lòng cung cấp assignToUsername. |
| 00241 | Định dạng ghi chú không hợp lệ. |
| 00242 | Định dạng ngày không hợp lệ. |
| 00243 | Truy cập không được ủy quyền. Bạn không có quyền. |
| 00245 | Tên người dùng được gán không tồn tại. |
| 00246 | Truy cập không được ủy quyền. Bạn không có quyền gán danh sách mua sắm cho người dùng này. |
| 00249 | Danh sách mua sắm đã được tạo thành công. |
| 00250 | Vui cung cấp tất cả các trường cần thiết. |
| 00251 | Vui lòng cung cấp id danh sách. |
| 00252 | Vui lòng cung cấp ít nhất một trong những trường sau, newName, newAssignToUsername, newNote, newDate. |
| 00253 | Định dạng tên mới không hợp lệ. |
| 00254 | Định dạng tên người được giao mới không hợp lệ. |
| 00255 | Định dạng ghi chú mới không hợp lệ. |
| 00256 | Định dạng ngày mới không hợp lệ. |
| 00258 | Người dùng không phải là quản trị viên nhóm. |
| 00260 | Không tìm thấy danh sách mua sắm. |
| 00261 | Người dùng không phải là quản trị viên của danh sách mua sắm này. |
| 00262 | Người dùng không tồn tại. |
| 00263 | Người dùng không có quyền gán danh sách này cho tên người dùng. |
| 00266 | Cập nhật danh sách mua sắm thành công. |
| 00267 | Cung cấp các trường cần thiết. |
| 00268 | Vui lòng cung cấp id danh sách. |
| 00270 | Người dùng không phải là quản trị viên nhóm. |
| 00272 | Không tìm thấy danh sách mua sắm. |
| 00273 | Người dùng không phải là quản trị viên của danh sách mua sắm này. |
| 00275 | Xóa danh sách mua sắm thành công. |
| 00276 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00277 | Vui lòng cung cấp một ID của danh sách. |
| 00278 | Vui lòng cung cấp một mảng nhiệm vụ. |
| 00279 | Vui lòng cung cấp một mảng nhiệm vụ với các trường hợp lệ. |
| 00281 | Người dùng không phải là quản trị viên của nhóm. |
| 00283 | Không tìm thấy danh sách mua sắm. |
| 00284 | Người dùng không phải là quản trị viên của danh sách mua sắm này. |
| 00285 | Không tìm thấy một món ăn với tên cung cấp trong mảng. |
| 00285 X | Loại thức ăn này đã có trong danh sách rồi. |
| 00287 | Thêm nhiệm vụ thành công. |
| 00288 | Người dùng này chưa thuộc nhóm nào. |
| 00292 | Lấy danh sách các shopping list thành công. |
| 00293 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00294 | Vui lòng cung cấp một ID nhiệm vụ trong trường taskId. |
| 00296 | Không tìm thấy nhiệm vụ với ID đã cung cấp. |
| 00297 | Người dùng không phải là quản trị viên nhóm. |
| 00299 | Xóa nhiệm vụ thành công. |
| 00300 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00301 | Vui lòng cung cấp một ID nhiệm vụ trong trường taskId. |
| 00302 | Vui lòng cung cấp ít nhất một trong các trường sau, newFoodName, newQuantity. |
| 00303 | Vui lòng cung cấp một newFoodName hợp lệ. |
| 00304 | Vui lòng cung cấp một newQuantity hợp lệ. |
| 00306 | Không tìm thấy nhiệm vụ với ID đã cung cấp. |
| 00307 | Người dùng không phải là quản trị viên nhóm. |
| 00308 | Không tìm thấy nhiệm vụ với tên đã cung cấp. |
| 00309 | Thực phẩm này đã tồn tại trong danh sách mua hàng hiện tại. |
| 00312 | Cập nhật nhiệm vụ thành công. |
| 00313 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00314 | Vui lòng cung cấp một tên thực phẩm hợp lệ. |
| 00315 | Vui lòng cung cấp một dấu thời gian hợp lệ. |
| 00316 | Vui lòng cung cấp một tên hợp lệ cho bữa ăn, sáng, trưa, tối. |
| 00317 | Không tìm thấy thực phẩm với tên đã cung cấp. |
| 00319 | Người dùng không phải là quản trị viên nhóm. |
| 00322 | Thêm kế hoạch bữa ăn thành công. |
| 00323 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00324 | Vui lòng cung cấp một ID kế hoạch hợp lệ. |
| 00325 | Không tìm thấy kế hoạch với ID đã cung cấp. |
| 00327 | Người dùng không phải là quản trị viên nhóm. |
| 00330 | Kế hoạch bữa ăn của bạn đã được xóa thành công. |
| 00331 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00332 | Vui lòng cung cấp một ID kế hoạch! |
| 00333 | Vui lòng cung cấp ít nhất một trong các trường sau, newFoodName, newTimestamp, newName. |
| 00334 | Vui lòng cung cấp một tên thực phẩm mới hợp lệ! |
| 00335 | Vui lòng cung cấp một dấu thời gian hợp lệ! |
| 00336 | Vui lòng cung cấp một tên hợp lệ, sáng, trưa, tối! |
| 00337 | Không tìm thấy kế hoạch với ID đã cung cấp. |
| 00339 | Người dùng không phải là quản trị viên nhóm. |
| 00341 | Tên thực phẩm mới không tồn tại. |
| 00344 | Cập nhật kế hoạch bữa ăn thành công. |
| 00345 | Bạn chưa vào nhóm nào. |
| 00348 | Lấy danh sách thành công. |
| 00349 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00350 | Vui lòng cung cấp một tên thực phẩm hợp lệ. |
| 00351 | Vui lòng cung cấp một tên công thức hợp lệ. |
| 00352 | Vui lòng cung cấp một mô tả công thức hợp lệ. |
| 00353 | Vui lòng cung cấp nội dung HTML công thức hợp lệ. |
| 00354 | Không tìm thấy thực phẩm với tên đã cung cấp. |
| 00357 | Thêm công thức nấu ăn thành công. |
| 00358 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00359 | Vui lòng cung cấp một ID công thức! |
| 00360 | Vui lòng cung cấp ít nhất một trong các trường sau, newFoodName, newDescription, newHtmlContent, newName. |
| 00361 | Vui lòng cung cấp một tên thực phẩm mới hợp lệ! |
| 00362 | Vui lòng cung cấp một mô tả mới hợp lệ! |
| 00363 | Vui lòng cung cấp nội dung HTML mới hợp lệ! |
| 00364 | Vui lòng cung cấp một tên công thức mới hợp lệ! |
| 00365 | Không tìm thấy công thức với ID đã cung cấp. |
| 00367 | Tên thực phẩm mới không tồn tại. |
| 00370 | Cập nhật công thức nấu ăn thành công. |
| 00371 | Vui lòng cung cấp tất cả các trường bắt buộc. |
| 00372 | Vui lòng cung cấp một ID công thức hợp lệ. |
| 00373 | Không tìm thấy công thức với ID đã cung cấp. |
| 00376 | Công thức của bạn đã được xóa thành công. |
| 00378 | Lấy các công thức thành công. |

---

## Danh sách API

### 1. User Management

#### Login
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/login/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Đăng nhập vào hệ thống, trả về access token và refresh token
- **Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00047",
    "message": "Bạn đã đăng nhập thành công.",
    "data": {
      "accessToken": "string",
      "refreshToken": "string",
      "user": {
        "id": "string",
        "email": "string",
        "name": "string",
        "username": "string"
      }
    }
  }
  ```

#### Register
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Đăng ký tài khoản mới, yêu cầu xác thực email sau khi đăng ký
- **Body:**
  ```json
  {
    "email": "string",
    "password": "string",
    "name": "string",
    "language": "string",
    "timezone": "string",
    "deviceId": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00035",
    "message": "Bạn đã đăng ký thành công.",
    "data": {
      "userId": "string",
      "email": "string",
      "verificationRequired": true
    }
  }
  ```

#### Logout
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/logout/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Đăng xuất khỏi hệ thống, vô hiệu hóa token hiện tại
- **Response Example:**
  ```json
  {
    "code": "00050",
    "message": "Đăng xuất thành công."
  }
  ```

#### Refresh Token
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/refresh-token/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Làm mới access token khi hết hạn
- **Body:**
  ```json
  {
    "refreshToken": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00065",
    "message": "Token đã được làm mới thành công.",
    "data": {
      "accessToken": "string",
      "refreshToken": "string"
    }
  }
  ```

#### Send Verification Code
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/send-verification-code/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Gửi mã xác thực đến email người dùng
- **Body:**
  ```json
  {
    "email": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00048",
    "message": "Mã đã được gửi đến email của bạn thành công."
  }
  ```

#### Get User
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `user/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy thông tin chi tiết của người dùng hiện tại
- **Response Example:**
  ```json
  {
    "code": "00089",
    "message": "Thông tin người dùng đã được lấy thành công.",
    "data": {
      "id": "string",
      "email": "string",
      "name": "string",
      "username": "string",
      "gender": "string",
      "birthdate": "string",
      "language": "string",
      "image": "string",
      "groupId": "string"
    }
  }
  ```

#### Delete User
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `user/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa tài khoản người dùng và tất cả dữ liệu liên quan
- **Response Example:**
  ```json
  {
    "code": "00092",
    "message": "Tài khoản của bạn đã bị xóa thành công."
  }
  ```

#### Verify Email
- **Method:** POST
- **Authorization:** No Auth
- **Path:** `user/verify-email/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xác thực email bằng mã đã gửi
- **Body:**
  ```json
  {
    "code": "string",
    "token": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00058",
    "message": "Địa chỉ email của bạn đã được xác minh thành công."
  }
  ```

#### Change Password
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `user/change-password/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Thay đổi mật khẩu (yêu cầu mật khẩu cũ)
- **Body:**
  ```json
  {
    "oldPassword": "string",
    "newPassword": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00076",
    "message": "Mật khẩu của bạn đã được thay đổi thành công."
  }
  ```

#### Edit User (Image Upload Including)
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `user/`
- **Content Type:** `multipart/form-data`
- **Description:** Cập nhật thông tin người dùng, bao gồm ảnh đại diện
- **Body:**
  ```json
  {
    "username": "string",
    "name": "string",
    "gender": "string",
    "birthdate": "string",
    "language": "string",
    "image": "File"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00086",
    "message": "Thông tin hồ sơ của bạn đã được thay đổi thành công.",
    "data": {
      "id": "string",
      "username": "string",
      "name": "string",
      "imageUrl": "string"
    }
  }
  ```

---

### 2. Group Management

#### Create Group
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `user/group/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Tạo nhóm mới, người tạo tự động trở thành trưởng nhóm
- **Body:**
  ```json
  {
    "name": "string",
    "description": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00095",
    "message": "Tạo nhóm thành công.",
    "data": {
      "groupId": "string",
      "name": "string",
      "adminId": "string",
      "createdAt": "timestamp"
    }
  }
  ```

#### Add Member
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `user/group/add/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Thêm thành viên mới vào nhóm (chỉ admin/trưởng nhóm)
- **Body:**
  ```json
  {
    "username": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00102",
    "message": "Người dùng thêm vào nhóm thành công.",
    "data": {
      "userId": "string",
      "username": "string",
      "addedAt": "timestamp"
    }
  }
  ```

#### Delete Member
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `user/group/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa thành viên khỏi nhóm (chỉ admin/trưởng nhóm)
- **Body:**
  ```json
  {
    "username": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00106",
    "message": "Xóa thành công."
  }
  ```

#### Get Group Members
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `user/group/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy thông tin nhóm và danh sách thành viên
- **Response Example:**
  ```json
  {
    "code": "00098",
    "message": "Thành công.",
    "data": {
      "groupId": "string",
      "name": "string",
      "adminId": "string",
      "adminName": "string",
      "memberCount": "number",
      "createdAt": "timestamp",
      "members": [
        {
          "userId": "string",
          "username": "string",
          "name": "string",
          "image": "string",
          "role": "admin|member",
          "joinedAt": "timestamp"
        }
      ]
    }
  }
  ```

---

### 3. Admin - Category Management

#### Create a Category
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `admin/category/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Body:**
  ```json
  {
    "name": "string"
  }
  ```

#### Get All Categories
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `admin/category/`
- **Content Type:** `application/x-www-form-urlencoded`

#### Edit Category by Name
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `admin/category/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Body:**
  ```json
  {
    "oldName": "string",
    "newName": "string"
  }
  ```

#### Delete Category by Name
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `admin/category/`
- **Body:**
  ```json
  {
    "name": "string"
  }
  ```

---

### 4. Admin - Unit Management

#### Create a Unit
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `admin/unit/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Body:**
  ```json
  {
    "unitName": "string"
  }
  ```

#### Get All Units
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `admin/unit/`
- **Content Type:** `application/x-www-form-urlencoded`

#### Edit Unit by Name
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `admin/unit/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Body:**
  ```json
  {
    "oldName": "string",
    "newName": "string"
  }
  ```

#### Delete Unit by Name
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `admin/unit/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Body:**
  ```json
  {
    "unitName": "string"
  }
  ```

#### Get Logs
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `admin/logs/`
- **Content Type:** `application/x-www-form-urlencoded`

---

### 5. Food Management

#### Create Food (Image Upload Include)
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `food/`
- **Content Type:** `multipart/form-data`
- **Description:** Tạo thực phẩm mới cho nhóm (chỉ thành viên nhóm mới tạo được)
- **Body:**
  ```json
  {
    "name": "string",
    "foodCategoryName": "string",
    "unitName": "string",
    "image": "File"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00160",
    "message": "Tạo thực phẩm thành công.",
    "data": {
      "foodId": "string",
      "name": "string",
      "category": "string",
      "unit": "string",
      "imageUrl": "string",
      "groupId": "string"
    }
  }
  ```

#### Update Food
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `food/`
- **Content Type:** `multipart/form-data`
- **Description:** Cập nhật thông tin thực phẩm (chỉ nhóm sở hữu hoặc admin)
- **Body:**
  ```json
  {
    "name": "string",
    "newName": "string",
    "newCategory": "string",
    "newUnit": "string",
    "image": "File"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00178",
    "message": "Thành công."
  }
  ```

#### Delete Food
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `food/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa thực phẩm (không thể xóa nếu đang được sử dụng)
- **Body:**
  ```json
  {
    "name": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00184",
    "message": "Xóa thực phẩm thành công."
  }
  ```

#### Get All Foods in Group
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `food/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách tất cả thực phẩm (chung + của nhóm)
- **Response Example:**
  ```json
  {
    "code": "00188",
    "message": "Lấy danh sách thực phẩm thành công.",
    "data": [
      {
        "foodId": "string",
        "name": "string",
        "category": "string",
        "unit": "string",
        "imageUrl": "string",
        "isPublic": "boolean",
        "groupId": "string"
      }
    ]
  }
  ```

#### Search Food
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `food/search/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?keyword=<search_term>&category=<category_name>`
- **Description:** Tìm kiếm thực phẩm theo tên hoặc danh mục

#### Get Units
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `food/unit/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách tất cả đơn vị đo lường
- **Response Example:**
  ```json
  {
    "code": "00110",
    "message": "Lấy các unit thành công.",
    "data": [
      {
        "unitId": "string",
        "name": "string"
      }
    ]
  }
  ```

#### Get Categories
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `food/category/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách tất cả danh mục thực phẩm
- **Response Example:**
  ```json
  {
    "code": "00129",
    "message": "Lấy các category thành công.",
    "data": [
      {
        "categoryId": "string",
        "name": "string"
      }
    ]
  }
  ```

---

### 6. Fridge Management

#### Create Fridge Item
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `fridge/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Thêm thực phẩm vào bếp/tủ lạnh với thông tin số lượng và hạn sử dụng
- **Body:**
  ```json
  {
    "foodName": "string",
    "useWithin": "number",
    "quantity": "number",
    "note": "string"
  }
  ```
- **Note:** `useWithin` là số ngày sử dụng trong khoảng bao nhiêu ngày
- **Response Example:**
  ```json
  {
    "code": "00202",
    "message": "Mục trong tủ lạnh được tạo thành công.",
    "data": {
      "itemId": "string",
      "foodName": "string",
      "quantity": "number",
      "unit": "string",
      "expiryDate": "date",
      "addedAt": "timestamp"
    }
  }
  ```

#### Update Fridge Item
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `fridge/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Cập nhật thông tin thực phẩm trong bếp (số lượng, hạn sử dụng, ghi chú)
- **Body:**
  ```json
  {
    "itemId": "string",
    "newNote": "string",
    "newQuantity": "number",
    "newUseWithin": "number",
    "newFoodName": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00216",
    "message": "Cập nhật mục tủ lạnh thành công."
  }
  ```

#### Delete Fridge Item
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `fridge/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa thực phẩm khỏi bếp/tủ lạnh
- **Body:**
  ```json
  {
    "foodName": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00224",
    "message": "Xóa mục trong tủ lạnh thành công."
  }
  ```

#### Get All Fridge Items (in Group)
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `fridge/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách tất cả thực phẩm trong bếp của nhóm
- **Response Example:**
  ```json
  {
    "code": "00228",
    "message": "Lấy danh sách đồ tủ lạnh thành công.",
    "data": [
      {
        "itemId": "string",
        "foodName": "string",
        "foodImage": "string",
        "quantity": "number",
        "unit": "string",
        "expiryDate": "date",
        "daysUntilExpiry": "number",
        "status": "fresh|expiring_soon|expired",
        "note": "string",
        "addedBy": "string",
        "addedAt": "timestamp"
      }
    ]
  }
  ```

#### Specific Item
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `fridge/:foodName/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `:foodName` - Tên thực phẩm cần lấy thông tin
- **Description:** Lấy thông tin chi tiết của một thực phẩm trong bếp
- **Response Example:**
  ```json
  {
    "code": "00237",
    "message": "Lấy item cụ thể thành công.",
    "data": {
      "itemId": "string",
      "foodName": "string",
      "foodImage": "string",
      "quantity": "number",
      "unit": "string",
      "expiryDate": "date",
      "daysUntilExpiry": "number",
      "status": "fresh|expiring_soon|expired",
      "note": "string",
      "addedBy": "string",
      "addedAt": "timestamp",
      "history": []
    }
  }
  ```

#### Get Expiring Items
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `fridge/expiring/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?days=<number>` (mặc định là 3 ngày)
- **Description:** Lấy danh sách thực phẩm sắp hết hạn

---

### 7. Shopping List Management

#### Create Shopping List for a User in Group
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `shopping/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Tạo danh sách mua sắm mới và gán cho một thành viên trong nhóm
- **Body:**
  ```json
  {
    "name": "string",
    "assignToUsername": "string",
    "note": "string",
    "date": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00249",
    "message": "Danh sách mua sắm đã được tạo thành công.",
    "data": {
      "listId": "string",
      "name": "string",
      "assignedTo": "string",
      "createdBy": "string",
      "createdAt": "timestamp"
    }
  }
  ```

#### Get All Shopping Lists
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `shopping/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách tất cả các shopping list trong nhóm
- **Response Example:**
  ```json
  {
    "code": "00292",
    "message": "Lấy danh sách các shopping list thành công.",
    "data": [
      {
        "listId": "string",
        "name": "string",
        "assignedTo": "string",
        "date": "string",
        "note": "string",
        "tasks": []
      }
    ]
  }
  ```

#### Update Shopping List
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `shopping/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Cập nhật thông tin danh sách mua sắm
- **Body:**
  ```json
  {
    "listId": "string",
    "newName": "string",
    "newAssignToUsername": "string",
    "newDate": "string",
    "newNote": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00266",
    "message": "Cập nhật danh sách mua sắm thành công."
  }
  ```

#### Delete Shopping List
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `shopping/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa danh sách mua sắm
- **Body:**
  ```json
  {
    "listId": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00275",
    "message": "Xóa danh sách mua sắm thành công."
  }
  ```

#### Create Tasks
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `shopping/task/`
- **Content Type:** `application/json`
- **Description:** Thêm các nhiệm vụ (thực phẩm cần mua) vào danh sách mua sắm
- **Body:**
  ```json
  {
    "listId": "number",
    "tasks": [
      {
        "foodName": "string",
        "quantity": "string"
      }
    ]
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00287",
    "message": "Thêm nhiệm vụ thành công."
  }
  ```

#### Get List of Task
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `shopping/task/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách các nhiệm vụ (task) trong shopping list

#### Delete Task
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `shopping/task/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa một nhiệm vụ khỏi danh sách mua sắm
- **Body:**
  ```json
  {
    "taskId": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00299",
    "message": "Xóa nhiệm vụ thành công."
  }
  ```

#### Update Task
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `shopping/task/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Cập nhật thông tin nhiệm vụ trong danh sách mua sắm
- **Body:**
  ```json
  {
    "taskId": "string",
    "newFoodName": "string",
    "newQuantity": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00312",
    "message": "Cập nhật nhiệm vụ thành công."
  }
  ```

---

### 8. Meal Plan Management

#### Create Meal Plan
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `meal/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Tạo kế hoạch bữa ăn mới cho nhóm
- **Body:**
  ```json
  {
    "foodName": "string",
    "timestamp": "string",
    "name": "string"
  }
  ```
- **Note:** `name` có thể là "sáng", "trưa", hoặc "tối"
- **Response Example:**
  ```json
  {
    "code": "00322",
    "message": "Thêm kế hoạch bữa ăn thành công.",
    "data": {
      "planId": "string",
      "foodName": "string",
      "timestamp": "string",
      "mealType": "string"
    }
  }
  ```

#### Get Meal Plan by Date
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `meal/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?date=<yyyy-mm-dd>`
- **Description:** Lấy kế hoạch bữa ăn theo ngày
- **Response Example:**
  ```json
  {
    "code": "00348",
    "message": "Lấy danh sách thành công.",
    "data": [
      {
        "planId": "string",
        "foodName": "string",
        "mealType": "string",
        "timestamp": "string",
        "portions": "number"
      }
    ]
  }
  ```

#### Update Meal Plan
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `meal/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Cập nhật thông tin kế hoạch bữa ăn
- **Body:**
  ```json
  {
    "planId": "string",
    "newFoodName": "string",
    "newTimestamp": "string",
    "newName": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00344",
    "message": "Cập nhật kế hoạch bữa ăn thành công."
  }
  ```

#### Delete Plan
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `meal/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa kế hoạch bữa ăn
- **Body:**
  ```json
  {
    "planId": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00330",
    "message": "Kế hoạch bữa ăn của bạn đã được xóa thành công."
  }
  ```

---

### 9. Recipe Management

#### Create Recipe
- **Method:** POST
- **Authorization:** Bearer Token
- **Path:** `recipe/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Tạo công thức nấu ăn mới (cá nhân cho nhóm hoặc chung cho hệ thống)
- **Body:**
  ```json
  {
    "foodName": "string",
    "name": "string",
    "htmlContent": "string",
    "description": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00357",
    "message": "Thêm công thức nấu ăn thành công.",
    "data": {
      "recipeId": "string",
      "name": "string",
      "foodName": "string",
      "createdBy": "string"
    }
  }
  ```

#### Get Recipes by Food ID
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `recipe/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?foodId=<id>`
- **Description:** Lấy danh sách công thức nấu ăn theo ID thực phẩm
- **Response Example:**
  ```json
  {
    "code": "00378",
    "message": "Lấy các công thức thành công.",
    "data": [
      {
        "recipeId": "string",
        "name": "string",
        "foodName": "string",
        "description": "string",
        "htmlContent": "string",
        "isPublic": "boolean"
      }
    ]
  }
  ```

#### Search Recipes
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `recipe/search/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?keyword=<search_term>`
- **Description:** Tìm kiếm công thức nấu ăn theo tên món ăn hoặc nguyên liệu

#### Update Recipe
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `recipe/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Cập nhật thông tin công thức nấu ăn
- **Body:**
  ```json
  {
    "recipeId": "string",
    "newHtmlContent": "string",
    "newDescription": "string",
    "newFoodName": "string",
    "newName": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00370",
    "message": "Cập nhật công thức nấu ăn thành công."
  }
  ```

#### Delete Recipe
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `recipe/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa công thức nấu ăn
- **Body:**
  ```json
  {
    "recipeId": "string"
  }
  ```
- **Response Example:**
  ```json
  {
    "code": "00376",
    "message": "Công thức của bạn đã được xóa thành công."
  }
  ```

---

### 10. Report & Statistics

#### Get Shopping Report
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `report/shopping/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?period=<week|month|year>&startDate=<date>&endDate=<date>`
- **Description:** Lấy báo cáo thống kê mua sắm theo khoảng thời gian
- **Response Example:**
  ```json
  {
    "code": "00098",
    "message": "Thành công.",
    "data": {
      "totalShoppingTrips": "number",
      "topFoods": [
        {
          "foodName": "string",
          "quantity": "number",
          "frequency": "number"
        }
      ],
      "mostActiveUser": {
        "username": "string",
        "trips": "number"
      },
      "trendData": []
    }
  }
  ```

#### Get Consumption Report
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `report/consumption/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?period=<week|month|year>&startDate=<date>&endDate=<date>`
- **Description:** Lấy báo cáo thống kê tiêu thụ thực phẩm
- **Response Example:**
  ```json
  {
    "code": "00098",
    "message": "Thành công.",
    "data": {
      "totalConsumed": "number",
      "wastePercentage": "number",
      "topDishes": [
        {
          "dishName": "string",
          "timesCooked": "number"
        }
      ],
      "categoryDistribution": []
    }
  }
  ```

#### Get Meal Plan Report
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `report/mealplan/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Query Params:** `?period=<week|month>&startDate=<date>&endDate=<date>`
- **Description:** Lấy báo cáo thống kê thực đơn
- **Response Example:**
  ```json
  {
    "code": "00098",
    "message": "Thành công.",
    "data": {
      "totalMealsPlanned": "number",
      "completionRate": "number",
      "popularDishes": [],
      "mealTypeDistribution": {
        "breakfast": "number",
        "lunch": "number",
        "dinner": "number"
      }
    }
  }
  ```

---

### 11. Notification Management

#### Get User Notifications
- **Method:** GET
- **Authorization:** Bearer Token
- **Path:** `notification/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Lấy danh sách thông báo của người dùng
- **Response Example:**
  ```json
  {
    "code": "00098",
    "message": "Thành công.",
    "data": [
      {
        "notificationId": "string",
        "title": "string",
        "message": "string",
        "type": "string",
        "isRead": "boolean",
        "createdAt": "timestamp"
      }
    ]
  }
  ```

#### Mark Notification as Read
- **Method:** PUT
- **Authorization:** Bearer Token
- **Path:** `notification/read/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Đánh dấu thông báo đã đọc
- **Body:**
  ```json
  {
    "notificationId": "string"
  }
  ```

#### Delete Notification
- **Method:** DELETE
- **Authorization:** Bearer Token
- **Path:** `notification/`
- **Content Type:** `application/x-www-form-urlencoded`
- **Description:** Xóa thông báo
- **Body:**
  ```json
  {
    "notificationId": "string"
  }
  ```

---

## B. Chi tiết Use Cases và Luồng nghiệp vụ

### 1. Quản lý danh sách mua sắm

#### UC-01.1: Tạo danh sách mua sắm

**Mô tả:** Người dùng tạo danh sách mua sắm mới và gán cho thành viên trong nhóm

**Luồng chính:**
1. Người dùng điền thông tin thực phẩm cần mua (tên, số lượng, đơn vị)
2. Người dùng chọn người đảm nhận từ danh sách thành viên
3. Hệ thống lưu danh sách và gửi thông báo cho người được gán

**API liên quan:**
- POST `shopping/` - Tạo danh sách mua sắm
- POST `shopping/task/` - Thêm các nhiệm vụ vào danh sách

#### UC-01.2: Chỉnh sửa danh sách mua sắm

**Mô tả:** Người dùng chỉnh sửa danh sách mua sắm đã tồn tại

**Luồng chính:**
1. Người dùng có thể thêm/xóa/sửa thực phẩm trong danh sách
2. Người dùng có thể thay đổi người đảm nhận
3. Hệ thống cập nhật và gửi thông báo

**API liên quan:**
- PUT `shopping/` - Cập nhật thông tin danh sách
- POST `shopping/task/` - Thêm nhiệm vụ mới
- PUT `shopping/task/` - Cập nhật nhiệm vụ
- DELETE `shopping/task/` - Xóa nhiệm vụ

#### UC-01.3: Theo dõi danh sách mua sắm

**Mô tả:** Người dùng theo dõi và đánh dấu các mặt hàng đã mua

**Luồng chính:**
1. Người dùng mở danh sách dưới dạng checklist
2. Người dùng tick chọn và nhập số lượng thực tế mua được
3. Hệ thống ghi nhận và tạo mục mới nếu thiếu
4. Người dùng hoàn thành và thông báo gửi đến toàn nhóm

**API liên quan:**
- GET `shopping/task/` - Xem danh sách nhiệm vụ
- PUT `shopping/task/` - Cập nhật trạng thái nhiệm vụ

#### UC-01.4: Gợi ý bổ sung thực phẩm

**Mô tả:** Hệ thống gợi ý thực phẩm cần mua dựa trên thực đơn và công thức

**Luồng chính:**
1. Hệ thống kiểm tra thực đơn sắp tới
2. Hệ thống kiểm tra công thức và nguyên liệu trong bếp
3. Hệ thống gợi ý thực phẩm cần mua nếu thiếu
4. Người dùng chọn đồng ý để thêm vào danh sách

**API liên quan:**
- GET `meal/?date=<date>` - Lấy thực đơn
- GET `recipe/?foodId=<id>` - Lấy công thức
- GET `fridge/` - Kiểm tra thực phẩm trong bếp
- POST `shopping/task/` - Thêm gợi ý vào danh sách

### 2. Quản lý dự định bữa ăn

#### UC-02.1: Tạo mới thực đơn

**Mô tả:** Người dùng tạo thực đơn cho bữa ăn sắp tới

**Luồng chính:**
1. Người dùng chọn bữa ăn (sáng/trưa/tối) và ngày
2. Người dùng thêm món ăn và số lượng phần
3. Hệ thống lưu thực đơn

**API liên quan:**
- POST `meal/` - Tạo kế hoạch bữa ăn

#### UC-02.3: Gợi ý món ăn từ thực phẩm trong bếp

**Mô tả:** Hệ thống gợi ý món ăn dựa trên thực phẩm có sẵn

**Luồng chính:**
1. Hệ thống kiểm tra thực phẩm trong bếp
2. Hệ thống tìm món ăn sử dụng thực phẩm đó
3. Gợi ý món ăn cho người dùng

**API liên quan:**
- GET `fridge/` - Lấy danh sách thực phẩm trong bếp
- GET `recipe/search/?keyword=<food>` - Tìm công thức

### 3. Quản lý thực phẩm trong bếp

#### UC-03.1: Thêm thực phẩm vào bếp

**Mô tả:** Người dùng thêm thực phẩm mới vào bếp

**API liên quan:**
- POST `fridge/` - Tạo mục thực phẩm trong bếp

#### UC-03.2: Chỉnh sửa thực phẩm trong bếp

**Mô tả:** Người dùng cập nhật thông tin thực phẩm

**API liên quan:**
- PUT `fridge/` - Cập nhật mục thực phẩm

#### UC-03.3: Xóa thực phẩm khỏi bếp

**Mô tả:** Người dùng xóa thực phẩm đã hết

**API liên quan:**
- DELETE `fridge/` - Xóa mục thực phẩm

#### UC-03.4: Nhắc nhở thực phẩm sắp hết hạn

**Mô tả:** Hệ thống tự động nhắc nhở về thực phẩm sắp hết hạn (3 ngày)

**Luồng chính:**
1. Hệ thống kiểm tra định kỳ hàng ngày
2. Tìm thực phẩm hết hạn trong 3 ngày
3. Tạo và gửi push notification cho nhóm

**Thông báo mẫu:**
- 1 thực phẩm: "{Tên thực phẩm} sẽ hết hạn vào {ngày} (còn {n} ngày)"
- Nhiều thực phẩm: "Bạn có {n} thực phẩm sắp hết hạn. Nhấn để xem chi tiết."

### 4. Quản lý công thức nấu ăn

#### UC-04.1: Tạo công thức nấu ăn chung (Admin)

**Mô tả:** QTV tạo công thức chung cho toàn hệ thống

**API liên quan:**
- POST `recipe/` - Tạo công thức (với quyền admin)

#### UC-04.3: Tạo công thức nấu ăn cá nhân

**Mô tả:** Người dùng tạo công thức riêng cho nhóm

**API liên quan:**
- POST `recipe/` - Tạo công thức (scope: nhóm)

#### UC-04.5: Tìm kiếm công thức món ăn

**Mô tả:** Tìm kiếm công thức theo tên món/nguyên liệu

**API liên quan:**
- GET `recipe/search/?keyword=<term>` - Tìm kiếm công thức

### 5. Quản lý nhóm

#### UC-06.1: Tạo nhóm

**Mô tả:** Người dùng tạo nhóm mới và trở thành trưởng nhóm

**API liên quan:**
- POST `user/group/` - Tạo nhóm

#### UC-06.2: Thêm thành viên vào nhóm

**Mô tả:** Trưởng nhóm thêm thành viên mới

**API liên quan:**
- POST `user/group/add/` - Thêm thành viên

#### UC-06.3: Xóa thành viên khỏi nhóm

**Mô tả:** Trưởng nhóm xóa thành viên

**API liên quan:**
- DELETE `user/group/` - Xóa thành viên

#### UC-06.6: Xem thông tin nhóm

**Mô tả:** Xem danh sách thành viên và thông tin nhóm

**API liên quan:**
- GET `user/group/` - Lấy thông tin nhóm

---

## C. Lưu ý kỹ thuật

### Authentication & Authorization

- Tất cả API (trừ login, register, verify email) yêu cầu Bearer Token
- Token được trả về sau khi login thành công
- Token có thời gian hết hạn, sử dụng refresh token để gia hạn
- Admin API yêu cầu quyền quản trị viên

### Error Handling

Tất cả response đều có cấu trúc:
```json
{
  "code": "xxxxx",
  "message": "Chi tiết thông báo",
  "data": {}  // Optional
}
```

Tham khảo bảng mã response ở phần A để biết chi tiết các mã lỗi.

### File Upload

API hỗ trợ upload file (ảnh) sử dụng `multipart/form-data`:
- `user/` (PUT) - Upload ảnh đại diện
- `food/` (POST) - Upload ảnh thực phẩm

### Pagination

Các API trả về danh sách có thể hỗ trợ phân trang:
- Query params: `?page=<number>&limit=<number>`
- Response bao gồm: `totalItems`, `totalPages`, `currentPage`

### Date Format

Tất cả các trường ngày tháng sử dụng định dạng:
- Date: `yyyy-mm-dd`
- DateTime: ISO 8601 format hoặc Unix timestamp

---

## D. Workflow Examples

### Workflow 1: Quy trình mua sắm hoàn chỉnh

1. **Tạo thực đơn tuần**
   - POST `meal/` - Thêm các món ăn cho tuần

2. **Hệ thống gợi ý thực phẩm cần mua**
   - GET `meal/?date=<date>` - Lấy thực đơn
   - GET `fridge/` - Kiểm tra thực phẩm sẵn có
   - Tính toán thực phẩm thiếu

3. **Tạo danh sách mua sắm**
   - POST `shopping/` - Tạo danh sách
   - POST `shopping/task/` - Thêm các món cần mua

4. **Đi chợ và cập nhật**
   - PUT `shopping/task/` - Đánh dấu đã mua
   - POST `fridge/` - Thêm thực phẩm vào bếp

### Workflow 2: Quản lý thực phẩm hết hạn

1. **Hệ thống kiểm tra hàng ngày**
   - Quét database thực phẩm
   - Tìm items hết hạn trong 3 ngày

2. **Gửi thông báo**
   - Push notification đến tất cả thành viên nhóm

3. **Người dùng xử lý**
   - GET `fridge/` - Xem danh sách
   - Chọn món ăn từ thực phẩm sắp hết hạn
   - POST `meal/` - Lên kế hoạch nấu

4. **Sau khi sử dụng**
   - DELETE `fridge/` - Xóa hoặc cập nhật số lượng

### Workflow 3: Chia sẻ trong nhóm gia đình

1. **Tạo nhóm**
   - POST `user/group/` - Người đầu tiên tạo nhóm

2. **Mời thành viên**
   - POST `user/group/add/` - Thêm từng thành viên

3. **Phân công việc**
   - POST `shopping/` - Tạo danh sách, gán cho thành viên
   - Thành viên được gán nhận thông báo

4. **Cập nhật và đồng bộ**
   - Mọi thay đổi tự động đồng bộ cho toàn nhóm
   - Thông báo real-time về các hoạt động

---

## E. Best Practices

### 1. Security

- Luôn sử dụng HTTPS cho tất cả API calls
- Không lưu token ở localStorage, sử dụng secure cookie hoặc encrypted storage
- Validate tất cả input phía client trước khi gửi
- Implement rate limiting để tránh abuse

### 2. Performance

- Cache dữ liệu tĩnh (categories, units) ở client
- Sử dụng pagination cho danh sách lớn
- Minimize số lượng API calls bằng cách batch requests khi có thể
- Implement offline mode với local database sync

### 3. User Experience

- Hiển thị loading states khi gọi API
- Implement retry mechanism cho failed requests
- Provide clear error messages
- Use optimistic updates cho UX tốt hơn

### 4. Data Sync

- Implement conflict resolution cho concurrent edits
- Use timestamps để track changes
- Provide sync status indicators
- Allow manual sync trigger

---

## F. Deployment & Environment

### Development
```
Base URL: http://localhost:8080/it4788/
```

### Staging
```
Base URL: https://staging.freshfridge.com/it4788/
```

### Production
```
Base URL: https://api.freshfridge.com/it4788/
```

### Environment Variables
- `API_BASE_URL` - Base URL của API
- `API_TIMEOUT` - Timeout cho requests (ms)
- `FCM_SERVER_KEY` - Firebase Cloud Messaging key
- `APNS_KEY` - Apple Push Notification Service key

---

## G. Changelog

### Version 1.0.0 (Initial Release)
- User Management APIs
- Group Management APIs
- Food Management APIs
- Fridge Management APIs
- Shopping List Management APIs
- Meal Plan Management APIs
- Recipe Management APIs
- Admin APIs (Category, Unit, Logs)
- Notification System

---

*Tài liệu này được cập nhật lần cuối: November 2025*
*Phiên bản API: 1.0.0*