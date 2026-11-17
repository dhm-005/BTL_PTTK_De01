<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.borrowreturn.LoanDetail1"%>
<%@page import="com.pttk.model.document.DocumentCopy1"%>
<%@page import="com.pttk.model.user.Reader1"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Reader1 reader = (Reader1) session.getAttribute("reader");
    List<DocumentCopy1> temporaryCopyList =
            (List<DocumentCopy1>) session.getAttribute("temporaryCopyList");
    List<LoanDetail1> unfinishedLoanList =
            (List<LoanDetail1>) session.getAttribute("unfinishedLoanList");
    int temporarySize = temporaryCopyList == null ? 0 : temporaryCopyList.size();
    int unfinishedSize = unfinishedLoanList == null ? 0 : unfinishedLoanList.size();
    int maxBorrowed = reader != null && reader.getGroup() != null
            && reader.getGroup().getPolicy() != null
            ? reader.getGroup().getPolicy().getMaxBorrowed() : 0;
    boolean limitReached = reader == null || temporarySize + unfinishedSize >= maxBorrowed;
    boolean cannotConfirm = reader == null || temporarySize == 0
            || reader.getGroup() == null || reader.getGroup().getPolicy() == null
            || temporarySize + unfinishedSize > maxBorrowed;
    String errorMessage = (String) request.getAttribute("errorMessage");
    String successMessage = (String) request.getAttribute("successMessage");
    String warningMessage = (String) request.getAttribute("warningMessage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <title>Lập phiếu mượn</title>
    </head>
    <body>
        <h1>Lập phiếu mượn</h1>
        <% if (errorMessage != null) { %>
            <p><%= JspUtils.escapeHtml(errorMessage) %></p>
        <% } %>
        <% if (successMessage != null) { %>
            <p><%= JspUtils.escapeHtml(successMessage) %></p>
        <% } %>
        <% if (warningMessage != null) { %>
            <p><%= JspUtils.escapeHtml(warningMessage) %></p>
        <% } %>

        <h2>Thông tin bạn đọc</h2>
        <% if (reader == null) { %>
            <p>Chưa chọn bạn đọc.</p>
        <% } else { %>
            <table border="1">
                <tr><th>Mã bạn đọc</th><td><%= JspUtils.escapeHtml(reader.getId()) %></td></tr>
                <tr><th>Họ tên</th><td><%= JspUtils.escapeHtml(reader.getFullname()) %></td></tr>
                <tr><th>Trạng thái</th><td><%= JspUtils.escapeHtml(reader.getAccountStatus()) %></td></tr>
                <tr><th>Nhóm bạn đọc</th><td><%= JspUtils.escapeHtml(
                        reader.getGroup() == null ? "" : reader.getGroup().getName()) %></td></tr>
            </table>
        <% } %>

        <h2>Danh sách đang mượn/chưa trả (<%= unfinishedSize %>)</h2>
        <table border="1">
            <tr><th>Tài liệu</th><th>Mã bản sao</th><th>Hạn trả</th><th>Trạng thái</th></tr>
            <% if (unfinishedLoanList != null && !unfinishedLoanList.isEmpty()) {
                for (LoanDetail1 detail : unfinishedLoanList) {
                    DocumentCopy1 copy = detail.getCopy(); %>
                    <tr>
                        <td><%= JspUtils.escapeHtml(copy == null || copy.getDocument() == null
                                ? "" : copy.getDocument().getName()) %></td>
                        <td><%= JspUtils.escapeHtml(copy == null ? "" : copy.getBarCode()) %></td>
                        <td><%= JspUtils.escapeHtml(detail.getDueDate()) %></td>
                        <td><%= JspUtils.escapeHtml(detail.getStatus()) %></td>
                    </tr>
                <% }
            } else { %>
                <tr><td colspan="4">Không có sách đang mượn.</td></tr>
            <% } %>
        </table>

        <h2>Danh sách tạm thời (<%= temporarySize %>)</h2>
        <table border="1">
            <tr><th>Tài liệu</th><th>Mã bản sao</th><th>Trạng thái</th></tr>
            <% if (temporaryCopyList != null && !temporaryCopyList.isEmpty()) {
                for (DocumentCopy1 copy : temporaryCopyList) { %>
                    <tr>
                        <td><%= JspUtils.escapeHtml(copy.getDocument() == null
                                ? "" : copy.getDocument().getName()) %></td>
                        <td><%= JspUtils.escapeHtml(copy.getBarCode()) %></td>
                        <td><%= JspUtils.escapeHtml(copy.getStatus()) %></td>
                    </tr>
                <% }
            } else { %>
                <tr><td colspan="3">Chưa chọn bản sao nào.</td></tr>
            <% } %>
        </table>

        <p>Giới hạn mượn: <%= unfinishedSize + temporarySize %> / <%= maxBorrowed %></p>
        <% if (limitReached) { %>
            <p>Đã chạm giới hạn mượn, không thể tiếp tục.</p>
        <% } %>
        <form action="copy/search-page" method="get">
            <button type="submit" <%= limitReached ? "disabled" : "" %>>Tiếp tục chọn sách</button>
        </form>
        <form action="loan/create" method="post">
            <button type="submit" <%= cannotConfirm ? "disabled" : "" %>>
                Xác nhận và in phiếu
            </button>
        </form>
    </body>
</html>
