SET foreign_key_checks = 0;

-- ==========================================
-- 1. TẠO CẤU TRÚC 32 BẢNG
-- ==========================================

-- 1. tblPublisher1
CREATE TABLE IF NOT EXISTS tblPublisher1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    logoImage VARCHAR(255),
    website VARCHAR(255),
    `description` VARCHAR(255)
);

-- 2. tblCategory1
CREATE TABLE IF NOT EXISTS tblCategory1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `description` VARCHAR(255)
);

-- 3. tblDocument1
CREATE TABLE IF NOT EXISTS tblDocument1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ISBN VARCHAR(255) UNIQUE,
    `description` VARCHAR(255),
    publishDate DATE,
    edition VARCHAR(255),
    `language` VARCHAR(255),
    pages INT,
    coverImage VARCHAR(255),
    tblPublisher1ID BIGINT,
    tblCategory1ID BIGINT,
    FOREIGN KEY (tblPublisher1ID) REFERENCES tblPublisher1(id),
    FOREIGN KEY (tblCategory1ID) REFERENCES tblCategory1(id)
);

-- 4. tblAuthor1
CREATE TABLE IF NOT EXISTS tblAuthor1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    bio VARCHAR(255)
);

-- 5. tblAuthorDocument1
CREATE TABLE IF NOT EXISTS tblAuthorDocument1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tblDocument1 BIGINT,
    tblAuthor1 BIGINT,
    FOREIGN KEY (tblDocument1) REFERENCES tblDocument1(id),
    FOREIGN KEY (tblAuthor1) REFERENCES tblAuthor1(id)
);

-- 6. tblLocation1
CREATE TABLE IF NOT EXISTS tblLocation1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    section VARCHAR(255) NOT NULL,
    floor BIGINT NOT NULL,
    room VARCHAR(255) NOT NULL,
    shelf VARCHAR(255),
    note VARCHAR(255)
);

-- 7. tblDocumentCopy1
CREATE TABLE IF NOT EXISTS tblDocumentCopy1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    physicalCondition VARCHAR(255) NOT NULL,
    conditionDescription VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    tblLocation1ID BIGINT,
    tblDocument1ID BIGINT,
    tblRecevingRecord1ID BIGINT,
    FOREIGN KEY (tblLocation1ID) REFERENCES tblLocation1(id),
    FOREIGN KEY (tblDocument1ID) REFERENCES tblDocument1(id),
    FOREIGN KEY (tblRecevingRecord1ID) REFERENCES tblRecevingRecord1(id)
);

-- 8. tblDeaccession1
CREATE TABLE IF NOT EXISTS tblDeaccession1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    reason VARCHAR(255) NOT NULL,
    tblDocumentCopy1ID BIGINT,
    managerID BIGINT,
    FOREIGN KEY (tblDocumentCopy1ID) REFERENCES tblDocumentCopy1(id),
    FOREIGN KEY (managerID) REFERENCES tblMember1(id)
);

-- 9. tblReservation1
CREATE TABLE IF NOT EXISTS tblReservation1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    requestDate DATETIME NOT NULL,
    expiryDate DATETIME NOT NULL,
    status VARCHAR(255) NOT NULL,
    processDate DATETIME,
    tblDocument1ID BIGINT,
    memberID BIGINT,
    librarianID BIGINT,
    FOREIGN KEY (tblDocument1ID) REFERENCES tblDocument1(id),
    FOREIGN KEY (memberID) REFERENCES tblMember1(id),
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 10. tblLoanPolicy1
CREATE TABLE IF NOT EXISTS tblLoanPolicy1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    maxRenewalCount INT NOT NULL,
    maxBorrowed INT NOT NULL,
    loanPeriod INT NOT NULL,
    maxReservation INT NOT NULL,
    reservationPeriod INT NOT NULL,
    updateDate DATETIME NOT NULL,
    renewalCondition VARCHAR(255) NOT NULL
);

-- 11. tblReaderGroup1
CREATE TABLE IF NOT EXISTS tblReaderGroup1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    tblLoanPolicy1ID BIGINT,
    FOREIGN KEY (tblLoanPolicy1ID) REFERENCES tblLoanPolicy1(id)
);

-- 12. tblMember1
CREATE TABLE IF NOT EXISTS tblMember1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    hashPassword VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    fullName VARCHAR(255) NOT NULL,
    birthday DATE,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(255),
    profilePicture VARCHAR(255),
    readerCode VARCHAR(255) UNIQUE,
    role VARCHAR(255) NOT NULL,
    tblReaderGroup1ID BIGINT,
    FOREIGN KEY (tblReaderGroup1ID) REFERENCES tblReaderGroup1(id)
);

-- 13. tblRecevingRecord1
CREATE TABLE IF NOT EXISTS tblRecevingRecord1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    receivingDate DATETIME NOT NULL,
    totalAmount DECIMAL(10,2) NOT NULL,
    tblSupplier1ID BIGINT,
    librarianID BIGINT,
    FOREIGN KEY (tblSupplier1ID) REFERENCES tblSupplier1(id),
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 14. tblSupplier1
CREATE TABLE IF NOT EXISTS tblSupplier1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255) NOT NULL
);

-- 15. tblInventoryCheck1
CREATE TABLE IF NOT EXISTS tblInventoryCheck1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    content VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    librarianID BIGINT,
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 16. tblInventoryDetail1
CREATE TABLE IF NOT EXISTS tblInventoryDetail1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    result VARCHAR(255) NOT NULL,
    actualCondition VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    tblInventoryCheck1ID BIGINT,
    tblDocumentCopy1ID BIGINT,
    FOREIGN KEY (tblInventoryCheck1ID) REFERENCES tblInventoryCheck1(id),
    FOREIGN KEY (tblDocumentCopy1ID) REFERENCES tblDocumentCopy1(id)
);

-- 17. tblLoanRecord1
CREATE TABLE IF NOT EXISTS tblLoanRecord1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loanDate DATETIME NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    readerID BIGINT,
    librarianID BIGINT,
    FOREIGN KEY (readerID) REFERENCES tblMember1(id),
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 18. tblLoanDetail1
CREATE TABLE IF NOT EXISTS tblLoanDetail1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dueDate DATE NOT NULL,
    renewalCount INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    tblLoanRecord1ID BIGINT,
    tblDocumentCopy1ID BIGINT,
    FOREIGN KEY (tblLoanRecord1ID) REFERENCES tblLoanRecord1(id),
    FOREIGN KEY (tblDocumentCopy1ID) REFERENCES tblDocumentCopy1(id)
);

-- 19. tblReturnRecord1
CREATE TABLE IF NOT EXISTS tblReturnRecord1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    returnDate DATETIME NOT NULL,
    librarianID BIGINT,
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 20. tblReturnDetail1
CREATE TABLE IF NOT EXISTS tblReturnDetail1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conditionAtReturn VARCHAR(255) NOT NULL,
    tblLoanDetail1ID BIGINT,
    tblReturnRecord1ID BIGINT,
    FOREIGN KEY (tblLoanDetail1ID) REFERENCES tblLoanDetail1(id),
    FOREIGN KEY (tblReturnRecord1ID) REFERENCES tblReturnRecord1(id)
);

-- 21. tblLibraryCard1
CREATE TABLE IF NOT EXISTS tblLibraryCard1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(255) UNIQUE NOT NULL,
    issueDate DATE NOT NULL,
    expiryDate DATE NOT NULL,
    status VARCHAR(255) NOT NULL,
    tblLibraryCardRequest1ID BIGINT,
    readerID BIGINT,
    FOREIGN KEY (tblLibraryCardRequest1ID) REFERENCES tblLibraryCardRequest1(id),
    FOREIGN KEY (readerID) REFERENCES tblMember1(id)
);

-- 22. tblLibraryCardRequest1
CREATE TABLE IF NOT EXISTS tblLibraryCardRequest1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    method VARCHAR(255) NOT NULL,
    createdAt DATETIME NOT NULL,
    feeApplied DECIMAL(10,2) NOT NULL,
    approvalStatus VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    tblCardService1ID BIGINT,
    librarianID BIGINT,
    FOREIGN KEY (tblCardService1ID) REFERENCES tblCardService1(id),
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 23. tblCardRegistration1
CREATE TABLE IF NOT EXISTS tblCardRegistration1 (
    tblLibraryCardRequest1ID BIGINT PRIMARY KEY,
    readerName VARCHAR(255) NOT NULL,
    readerEmail VARCHAR(255) NOT NULL,
    readerPicture VARCHAR(255),
    FOREIGN KEY (tblLibraryCardRequest1ID) REFERENCES tblLibraryCardRequest1(id)
);

-- 24. tblCardService1
CREATE TABLE IF NOT EXISTS tblCardService1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `type` VARCHAR(255) NOT NULL,
    fee DECIMAL(10,2) NOT NULL,
    effectiveTerm DATETIME NOT NULL,
    isActive BOOLEAN NOT NULL
);

-- 25. tblCardReissue1
CREATE TABLE IF NOT EXISTS tblCardReissue1 (
    id BIGINT PRIMARY KEY,
    reason VARCHAR(255),
    readerID BIGINT,
    FOREIGN KEY (id) REFERENCES tblLibraryCardRequest1(id),
    FOREIGN KEY (readerID) REFERENCES tblMember1(id)
);

-- 26. tblPayment1
CREATE TABLE IF NOT EXISTS tblPayment1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    payDate DATETIME NOT NULL,
    content VARCHAR(255),
    method VARCHAR(255) NOT NULL
);

-- 27. tblCardPayment1
CREATE TABLE IF NOT EXISTS tblCardPayment1 (
    tblPayment1ID BIGINT PRIMARY KEY,
    tblLibraryCardRequest1ID BIGINT,
    FOREIGN KEY (tblPayment1ID) REFERENCES tblPayment1(id),
    FOREIGN KEY (tblLibraryCardRequest1ID) REFERENCES tblLibraryCardRequest1(id)
);

-- 28. tblFinePayment1
CREATE TABLE IF NOT EXISTS tblFinePayment1 (
    tblPayment1ID BIGINT PRIMARY KEY,
    tblReturnRecord1 BIGINT,
    FOREIGN KEY (tblPayment1ID) REFERENCES tblPayment1(id),
    FOREIGN KEY (tblReturnRecord1) REFERENCES tblReturnRecord1(id)
);

-- 29. tblViolationType1
CREATE TABLE IF NOT EXISTS tblViolationType1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    finePercent INT NOT NULL,
    `description` VARCHAR(255),
    handlingRule VARCHAR(255) NOT NULL
);

-- 30. tblViolation1
CREATE TABLE IF NOT EXISTS tblViolation1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    violationDate DATE NOT NULL,
    content VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    fineApplied DECIMAL(10,2),
    tblViolationType1ID BIGINT,
    FOREIGN KEY (tblViolationType1ID) REFERENCES tblViolationType1(id)
);

-- 31. tblLoanRenewalRequest1
CREATE TABLE IF NOT EXISTS tblLoanRenewalRequest1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    requestDate DATETIME,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    processDate DATETIME NOT NULL,
    note VARCHAR(255),
    tblLoanDetail1ID BIGINT,
    librarianID BIGINT,
    FOREIGN KEY (tblLoanDetail1ID) REFERENCES tblLoanDetail1(id),
    FOREIGN KEY (librarianID) REFERENCES tblMember1(id)
);

-- 32. tblLoanRenewal1
CREATE TABLE IF NOT EXISTS tblLoanRenewal1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    renewalDate DATETIME NOT NULL,
    oldDueTime DATETIME NOT NULL,
    newDueTime DATETIME NOT NULL,
    tblLoanRenewalRequest1ID BIGINT,
    FOREIGN KEY (tblLoanRenewalRequest1ID) REFERENCES tblLoanRenewalRequest1(id)
);


SET foreign_key_checks = 1;