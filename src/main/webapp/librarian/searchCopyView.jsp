<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.document.DocumentCopy1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DocumentCopy1 copy = (DocumentCopy1) session.getAttribute("tempCopy");
    String barcode = (String) request.getAttribute("barcode");
    boolean searched = Boolean.TRUE.equals(request.getAttribute("searched"));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        
        <title>Tìm bản sao tài liệu</title>
        <link href="assets/css/searchView.css" rel="stylesheet">
        <link href="assets/css/detailView.css" rel="stylesheet">
        
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        
        <main>
        <form action="copy/search" method="get" class="search-form">
            <input type="search" name="barcode" value="<%= JspUtils.escapeHtml(barcode) %>"
                   placeholder="Mã vạch bản sao">
            <button type="submit">Tìm kiếm</button>
            <button type="button" class="btn-scan"> Quét thẻ </button>
        </form>

        <% if (searched && copy == null) { %>
            <p>Không tìm thấy bản sao có mã vạch này.</p>
        <% } else if (copy != null) { %>
            <p>Tài liệu: <%= JspUtils.escapeHtml(
                    copy.getDocument() == null ? "" : copy.getDocument().getName()) %></p>
            <p>Mã vạch: <%= JspUtils.escapeHtml(copy.getBarCode()) %></p>
            <p>Trạng thái: <%= JspUtils.escapeHtml(copy.getStatus()) %></p>
            <form action="loan/temporary" method="post">
                <input type="hidden" name="addCopy" value="true">
                
                <a href="loan/temporary" class="button-a purple">Quay lại danh sách mượn</a>
                <button type="submit" class="button-a green">Tiếp tục</button>
            </form>
        <% } %>
        
        
        </main>
        
    </body>
</html>
