<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        
        <title>Trang chủ</title>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"></jsp:include>
        
        <a href="reader/search-page">Cho mượn tài liệu</a>
    </body>
</html>
