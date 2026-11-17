<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.borrowreturn.LoanDetail1"%>
<%@page import="com.pttk.model.borrowreturn.LoanRecord1"%>
<%@page import="com.pttk.model.document.DocumentCopy1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    LoanRecord1 record = (LoanRecord1) request.getAttribute("loanRecord");
    String warningMessage = (String) request.getAttribute("warningMessage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <title>Phiếu mượn</title>
    </head>
    <body>
        <h1>Phiếu mượn</h1>
        <% if (warningMessage != null) { %>
            <p><%= JspUtils.escapeHtml(warningMessage) %></p>
        <% } %>
        <p>Mã phiếu: <%= JspUtils.escapeHtml(record.getId()) %></p>
        <p>Ngày mượn: <%= JspUtils.escapeHtml(record.getLoanDate()) %></p>
        <p>Bạn đọc: <%= JspUtils.escapeHtml(record.getReader1().getFullname()) %></p>
        <p>Thủ thư: <%= JspUtils.escapeHtml(record.getLibrarian1().getFullname()) %></p>

        <table border="1">
            <tr><th>Tài liệu</th><th>Mã bản sao</th><th>Hạn trả</th><th>Trạng thái</th></tr>
            <% for (LoanDetail1 detail : record.getDetails()) {
                DocumentCopy1 copy = detail.getCopy(); %>
                <tr>
                    <td><%= JspUtils.escapeHtml(copy.getDocument() == null
                            ? "" : copy.getDocument().getName()) %></td>
                    <td><%= JspUtils.escapeHtml(copy.getBarCode()) %></td>
                    <td><%= JspUtils.escapeHtml(detail.getDueDate()) %></td>
                    <td><%= JspUtils.escapeHtml(detail.getStatus()) %></td>
                </tr>
            <% } %>
        </table>

        <form action="loan/print" method="post">
            <button type="submit">In phiếu mượn</button>
        </form>
        <p><a href="loan/temporary">Quay lại trang mượn</a></p>
        <% if (Boolean.TRUE.equals(request.getAttribute("shouldPrint"))) { %>
            <script>window.print();</script>
        <% } %>
    </body>
</html>
