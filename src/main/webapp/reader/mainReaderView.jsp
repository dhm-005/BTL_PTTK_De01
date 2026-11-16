<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        
        <title>LibMan - Thư viện</title>
        <link href="assets/css/index.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />
        <main class="home">
            <section class="hero">
                <p class="eyebrow">THƯ VIỆN SỐ LIBMAN</p>
                <h1>Tri thức bắt đầu từ một trang sách.</h1>
                <p class="intro">Tra cứu sách, tạp chí và tài liệu trong bộ sưu tập thư viện.</p>
                <a href="document/search-page">Bắt đầu tìm kiếm</a>
            </section>
        </main>
    </body>
</html>
