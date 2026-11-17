SET foreign_key_checks = 0;

-- ==========================================
-- 2. INSERT DỮ LIỆU MẪU
-- ==========================================

-- 1. tblPublisher1
INSERT INTO tblPublisher1 (id, name, logoImage, website, `description`) VALUES
(1, 'NXB Giáo dục Việt Nam', 'assets/img/nxbgd.png', 'https://nxbgd.vn', 'Nhà xuất bản giáo trình đại học và phổ thông'),
(2, 'NXB Thông tin và Truyền thông', 'assets/img/nxbtttt.png', 'https://nxbtttt.vn', 'Chuyên sách công nghệ thông tin, viễn thông'),
(3, 'O''Reilly Media', 'assets/img/oreilly.png', 'https://oreilly.com', 'Sách kỹ thuật phần mềm và khoa học dữ liệu quốc tế'),
(4, 'Viện Hàn lâm Khoa học và Công nghệ Việt Nam', 'assets/img/vast.png', 'https://vast.gov.vn', 'Cơ quan nghiên cứu khoa học và công nghệ hàng đầu Việt Nam'),
(5, 'Trường Đại học Y Hà Nội', 'assets/img/hmu.png', 'https://hmu.edu.vn', 'Đơn vị xuất bản các ấn phẩm và tạp chí nghiên cứu y khoa');

-- 2. tblCategory1
INSERT INTO tblCategory1 (id, name, `description`) VALUES
(1, 'Sách', 'Sách tham khảo, chuyên khảo và tài liệu đọc mở rộng'),
(2, 'Giáo trình', 'Giáo trình giảng dạy và học tập chính thức theo chương trình đào tạo'),
(3, 'Tạp chí', 'Tạp chí khoa học, ấn phẩm định kỳ và kỷ yếu nghiên cứu');

-- 3. tblAuthor1
INSERT INTO tblAuthor1 (id, name, image, bio) VALUES
(1, 'Nguyễn Văn A', 'assets/img/author1.jpg', 'Giảng viên Khoa Công nghệ thông tin'),
(2, 'Trần Thị B', 'assets/img/author2.jpg', 'Chuyên gia Phân tích thiết kế hệ thống'),
(3, 'Robert C. Martin', 'assets/img/unclebob.jpg', 'Tác giả Clean Code và Clean Architecture'),
(4, 'Nhiều tác giả', 'assets/img/authors.jpg', 'Tập thể các nhà khoa học, giảng viên và chuyên gia nghiên cứu');

-- 4. tblDocument1
INSERT INTO tblDocument1 (id, name, ISBN, `description`, publishDate, edition, `language`, pages, coverImage, tblPublisher1ID, tblCategory1ID) VALUES
(1, 'Phân tích thiết kế hệ thống thông tin', '978-604-0-12345-1', 'Giáo trình hướng đối tượng UML và thiết kế CSDL', '2024-05-10', 'Tái bản lần 3', 'Tiếng Việt', 320, NULL, 1, 2),
(2, 'Lập trình Web với Java Servlet & JSP', '978-604-80-5432-1', 'Xây dựng ứng dụng web theo mô hình MVC', '2025-01-15', 'Lần 1', 'Tiếng Việt', 280, 'assets/img/java.jpg', 2, 1),
(3, 'Clean Code', '978-0132350884', 'A Handbook of Agile Software Craftsmanship', '2008-08-01', '1st Edition', 'English', 464, 'assets/img/cleancode.jpg', 3, 1),
(4, 'Tạp chí khoa học và công nghệ', NULL, 'Tạp chí công bố các công trình nghiên cứu khoa học và công nghệ mới nhất.', '2026-09-01', 'Số 9, Năm 2026', 'Tiếng Việt', 150, 'assets/img/cover1.jpg', 4, 3),
(5, 'Tạp chí Khoa học Nghiên cứu Y học', NULL, 'Tạp chí khoa học chuyên ngành y học, công bố các công trình nghiên cứu và kết quả nghiên cứu trong lĩnh vực y khoa.', NULL, NULL, 'Tiếng Việt', 120, 'assets/img/cover2.jpg', 5, 3);

-- 5. tblAuthorDocument1
INSERT INTO tblAuthorDocument1 (id, tblDocument1, tblAuthor1) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1),
(4, 3, 3),
(5, 4, 4),
(6, 5, 4);

-- 6. tblLocation1
INSERT INTO tblLocation1 (id, section, floor, room, shelf, note) VALUES
(1, 'Khu A - CNTT', 2, 'Phòng Đọc 201', 'Kệ A1', 'Sách giáo trình chuyên ngành'),
(2, 'Khu B - Ngoại văn', 3, 'Phòng Đọc 302', 'Kệ B4', 'Sách tiếng Anh nguyên bản'),
(3, 'Khu A', 2, 'Phòng Tạp chí', 'Kệ T03', 'Khu vực lưu trữ tạp chí khoa học và y học');

-- 7. tblLoanPolicy1
INSERT INTO tblLoanPolicy1 (id, maxRenewalCount, maxBorrowed, loanPeriod, maxReservation, reservationPeriod, updateDate, renewalCondition) VALUES
(1, 2, 5, 14, 2, 3, '2026-01-01 08:00:00', 'Không có sách quá hạn và chưa vượt quá số lần gia hạn'),
(2, 3, 10, 30, 5, 5, '2026-01-01 08:00:00', 'Áp dụng cho giảng viên và nghiên cứu sinh');

-- 8. tblReaderGroup1
INSERT INTO tblReaderGroup1 (id, name, `description`, tblLoanPolicy1ID) VALUES
(1, 'Sinh viên', 'Sinh viên hệ đại học chính quy', 1),
(2, 'Giảng viên', 'Cán bộ giảng dạy trong trường', 2);

-- 9. tblMember1 (hashPassword đều là '1234')
INSERT INTO tblMember1 (id, username, hashPassword, accountStatus, fullName, birthday, email, phone, profilePicture, role, tblReaderGroup1ID) VALUES
(1, 'reader1', '$2a$12$5UoLcu1j0rTmdGkvkOep0uZxd0Z2HC3EjdAZJYUjegypUv5OsL/Uu', 'ACTIVE', 'Lê Minh Tuấn', '2004-03-12', 'tuan.lm@example.com', '0912345671', 'assets/img/avatar1.png', 'READER', 1),
(2, 'reader2', '$2a$12$5UoLcu1j0rTmdGkvkOep0uZxd0Z2HC3EjdAZJYUjegypUv5OsL/Uu', 'ACTIVE', 'Phạm Thu Hà', '2004-09-25', 'ha.pt@example.com', '0912345672', 'assets/img/avatar2.png', 'READER', 1),
(3, 'librarian1', '$2a$12$5UoLcu1j0rTmdGkvkOep0uZxd0Z2HC3EjdAZJYUjegypUv5OsL/Uu', 'ACTIVE', 'Nguyễn Thị Lan', '1992-07-18', 'lan.nt@library.vn', '0987654321', 'assets/img/lib1.png', 'LIBRARIAN', NULL),
(4, 'manager1', '$2a$12$5UoLcu1j0rTmdGkvkOep0uZxd0Z2HC3EjdAZJYUjegypUv5OsL/Uu', 'ACTIVE', 'Trần Quốc Bảo', '1985-11-02', 'bao.tq@library.vn', '0987654322', 'assets/img/mgr1.png', 'MANAGER', NULL);

-- 10. tblSupplier1
INSERT INTO tblSupplier1 (id, `name`, address, email, phone) VALUES
(1, 'Công ty Sách Fahasa', '60-62 Lê Lợi, Q.1, TP.HCM', 'kinhdoanh@fahasa.com', '02838225446'),
(2, 'Nhà sách Trí Tuệ', '187 Giảng Võ, Hà Nội', 'contact@tritue.vn', '02438515567');

-- 11. tblRecevingRecord1
INSERT INTO tblRecevingRecord1 (id, receivingDate, totalAmount, tblSupplier1ID, librarianID) VALUES
(1, '2026-02-10 09:30:00', 1500000.00, 1, 3),
(2, '2026-03-05 14:15:00', 2200000.00, 2, 3);

-- 12. tblDocumentCopy1
INSERT INTO tblDocumentCopy1 (id, barcode, status, physicalCondition, conditionDescription, price, tblLocation1ID, tblDocument1ID, tblRecevingRecord1ID) VALUES
(1, 'DOC-001-01', 'AVAILABLE', 'NEW', 'Sách mới nguyên vẹn', 120000.00, 1, 1, 1),
(2, 'DOC-001-02', 'LOANED', 'OLD', 'Bìa hơi gập nhẹ', 120000.00, 1, 1, 1),
(3, 'DOC-002-01', 'AVAILABLE', 'OLD', 'Sách cũ', 150000.00, 1, 2, 1),
(4, 'DOC-003-01', 'AVAILABLE', 'SLIGHTLY_DAMAGED', 'Bản in bìa cứng', 450000.00, 2, 3, 2),
(5, 'DOC-003-02', 'DEACCESSED', 'BADLY_DAMAGED', 'Rách hỏng nhiều trang, thanh lý', 450000.00, 2, 3, 2),
(6, 'DOC-004-01', 'AVAILABLE', 'NEW', 'Tạp chí mới phát hành', 80000.00, 3, 4, 1),
(7, 'DOC-005-01', 'AVAILABLE', 'NEW', 'Tạp chí lưu trữ trên kệ', 95000.00, 3, 5, 2),
(8, 'DOC-005-02', 'LOANED', 'OLD', 'Đang có bạn đọc mượn', 95000.00, 3, 5, 2);

-- 13. tblDeaccession1
INSERT INTO tblDeaccession1 (id, date, reason, tblDocumentCopy1ID, managerID) VALUES
(1, '2026-10-01 10:00:00', 'Hư hỏng vật lý không thể phục chế', 5, 4);

-- 14. tblReservation1
INSERT INTO tblReservation1 (id, requestDate, expiryDate, status, processDate, tblDocument1ID, memberID, librarianID) VALUES
(1, '2026-11-10 08:30:00', '2026-11-13 17:00:00', 'APPROVED', '2026-11-10 09:00:00', 1, 2, 3);

-- 15. tblInventoryCheck1
INSERT INTO tblInventoryCheck1 (id, date, content, status, note, librarianID) VALUES
(1, '2026-10-15 08:00:00', 'Kiểm kê định kỳ kho sách CNTT Quý 4/2026', 'COMPLETED', 'Đủ số lượng trên kệ', 3);

-- 16. tblInventoryDetail1
INSERT INTO tblInventoryDetail1 (id, result, actualCondition, note, tblInventoryCheck1ID, tblDocumentCopy1ID) VALUES
(1, 'MATCHED', 'Good', 'Đúng vị trí kệ A1', 1, 1),
(2, 'MATCHED', 'Good', 'Đúng vị trí kệ A1', 1, 3);

-- 17. tblLoanRecord1
INSERT INTO tblLoanRecord1 (id, loanDate, `status`, readerID, librarianID) VALUES
(1, '2026-11-01 10:00:00', 'ACTIVE', 1, 3),
(2, '2026-10-10 14:00:00', 'COMPLETED', 2, 3);

-- 18. tblLoanDetail1
INSERT INTO tblLoanDetail1 (id, dueDate, renewalCount, status, tblLoanRecord1ID, tblDocumentCopy1ID) VALUES
(1, '2026-11-22', 1, 'BORROWING', 1, 2),
(2, '2026-10-24', 0, 'RETURNED', 2, 3);

-- 19. tblReturnRecord1
INSERT INTO tblReturnRecord1 (id, returnDate, librarianID) VALUES
(1, '2026-10-26 15:20:00', 3);

-- 20. tblReturnDetail1
INSERT INTO tblReturnDetail1 (id, conditionAtReturn, tblLoanDetail1ID, tblReturnRecord1ID) VALUES
(1, 'Good - Trả trễ 2 ngày', 2, 1);

-- 21. tblCardService1
INSERT INTO tblCardService1 (id, `type`, fee, effectiveTerm, isActive) VALUES
(1, 'Cấp thẻ mới sinh viên (1 năm)', 50000.00, '2027-12-31 23:59:59', TRUE),
(2, 'Cấp lại thẻ bị mất', 30000.00, '2027-12-31 23:59:59', TRUE);

-- 22. tblLibraryCardRequest1
INSERT INTO tblLibraryCardRequest1 (id, method, createdAt, feeApplied, approvalStatus, note, tblCardService1ID, librarianID) VALUES
(1, 'ONLINE', '2026-09-01 09:00:00', 50000.00, 'APPROVED', 'Đã xác minh thẻ sinh viên', 1, 3),
(2, 'OFFLINE', '2026-11-05 10:30:00', 30000.00, 'APPROVED', 'Cấp lại do mất thẻ cũ', 2, 3);

-- 23. tblCardRegistration1
INSERT INTO tblCardRegistration1 (tblLibraryCardRequest1ID, readerName, readerEmail, readerPicture) VALUES
(1, 'Lê Minh Tuấn', 'tuan.lm@example.com', 'assets/img/avatar1.png');

-- 24. tblCardReissue1
INSERT INTO tblCardReissue1 (id, reason, readerID) VALUES
(2, 'Làm rơi mất ví', 1);

-- 25. tblLibraryCard1
INSERT INTO tblLibraryCard1 (id, barcode, issueDate, expiryDate, status, tblLibraryCardRequest1ID, readerID) VALUES
(1, '2026-0001', '2026-09-02', '2027-09-02', 'ACTIVE', 2, 1);

-- 26. tblPayment1
INSERT INTO tblPayment1 (id, amount, status, payDate, content, method) VALUES
(1, 50000.00, 'PAID', '2026-09-01 09:15:00', 'Thanh toán phí làm thẻ thư viện mới', 'BANK_TRANSFER'),
(2, 10000.00, 'PAID', '2026-10-26 15:25:00', 'Thanh toán phí phạt trả sách trễ 2 ngày', 'CASH');

-- 27. tblCardPayment1
INSERT INTO tblCardPayment1 (tblPayment1ID, tblLibraryCardRequest1ID) VALUES
(1, 1);

-- 28. tblFinePayment1
INSERT INTO tblFinePayment1 (tblPayment1ID, tblReturnRecord1) VALUES
(2, 1);

-- 29. tblViolationType1
INSERT INTO tblViolationType1 (id, name, finePercent, `description`, handlingRule) VALUES
(1, 'Trả tài liệu quá hạn', 5, 'Phạt 5% giá trị sách cho mỗi ngày quá hạn', 'Nộp phạt tiền mặt hoặc chuyển khoản'),
(2, 'Làm hư hỏng tài liệu', 100, 'Bồi thường 100% giá trị cuốn sách', 'Đền bù tiền hoặc mua sách mới cùng loại');

-- 30. tblViolation1
INSERT INTO tblViolation1 (id, violationDate, content, status, note, fineApplied, tblViolationType1ID) VALUES
(1, '2026-10-26', 'Trả cuốn Lập trình Web với Java trễ 2 ngày', 'RESOLVED', 'Độc giả đã nộp phạt đầy đủ', 10000.00, 1);

-- 31. tblLoanRenewalRequest1
INSERT INTO tblLoanRenewalRequest1 (id, requestDate, reason, status, processDate, note, tblLoanDetail1ID, librarianID) VALUES
(1, '2026-11-12 11:00:00', 'Cần thêm thời gian ôn tập thi cuối kỳ', 'APPROVED', '2026-11-12 13:30:00', 'Đồng ý gia hạn thêm 7 ngày', 1, 3);

-- 32. tblLoanRenewal1
INSERT INTO tblLoanRenewal1 (id, renewalDate, oldDueTime, newDueTime, tblLoanRenewalRequest1ID) VALUES
(1, '2026-11-12 13:30:00', '2026-11-15 17:00:00', '2026-11-22 17:00:00', 1);

SET foreign_key_checks = 1;