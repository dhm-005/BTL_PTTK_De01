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
        <title>Tìm bản sao tài liệu</title>
    </head>
    <body>
        <h1>Tìm bản sao tài liệu</h1>
        <form action="copy/search" method="get">
            <input type="search" name="barcode" value="<%= JspUtils.escapeHtml(barcode) %>"
                   placeholder="Mã vạch bản sao">
            <button type="submit">Tìm kiếm</button>
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
                <button type="submit">Tiếp tục</button>
            </form>
        <% } %>
        <p><a href="loan/temporary">Quay lại danh sách mượn</a></p>
    </body>
</html>
