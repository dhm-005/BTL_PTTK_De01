<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.borrowreturn.LoanDetail1"%>
<%@page import="com.pttk.model.borrowreturn.LoanRecord1"%>
<%@page import="com.pttk.model.document.DocumentCopy1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    LoanRecord1 record = (LoanRecord1) request.getAttribute("loanRecord");
    String warningMessage = (String) request.getAttribute("warningMessage");
 
    // định dạng ngày giờ theo ý muốn
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    String formattedLoanDate = (record.getLoanDate() != null) ? record.getLoanDate().format(formatter) : "";

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        
        <title>Phiếu mượn</title>
        <link href="assets/css/detailView.css" rel="stylesheet">
        <link href="assets/css/printView.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />
        
        <main class="print-view">
            <div class="detail-card print-card">
                <h2 class="print-title">PHIẾU MƯỢN SÁCH</h2>
                
                <% if (warningMessage != null) { %>
                    <p class="print-warning"><%= JspUtils.escapeHtml(warningMessage) %></p>
                <% } %>
                
                <div class="print-parties">
                    <div>
                        <p><strong>Số phiếu:</strong> <%= JspUtils.escapeHtml(record.getId()) %></p>
                        <p><strong>Ngày mượn:</strong> <%= JspUtils.escapeHtml(formattedLoanDate) %></p>
                    </div>
                    <div>
                        <p><strong>Người mượn:</strong> <%= JspUtils.escapeHtml(record.getReader1().getFullname()) %></p>
                        <p><strong>Mã số bạn đọc:</strong> <%= JspUtils.escapeHtml(record.getReader1().getId()) %></p>
                    </div>
                </div>

                <p class="print-loan-heading">1. Danh sách mượn</p>
                <table class="print-loan-table">
                    <tr>
                        <th class="print-sequence">STT</th>
                        <th>Mã tài liệu</th>
                        <th>Tên tài liệu</th>
                        <th>Hạn trả</th>
                    </tr>
                    <% 
                        int stt = 1;
                        for (LoanDetail1 detail : record.getDetails()) {
                            DocumentCopy1 copy = detail.getCopy(); 
                    %>
                        <tr>
                            <td class="print-sequence"><%= stt++ %></td>
                            <td><%= JspUtils.escapeHtml(copy.getBarCode()) %></td>
                            <td><%= JspUtils.escapeHtml(copy.getDocument() == null ? "" : copy.getDocument().getName()) %></td>
                            <td><%= JspUtils.escapeHtml(detail.getDueDate()) %></td>
                        </tr>
                    <% } %>
                </table>

                <!-- Điều khoản mượn sách -->
                <div class="print-terms">
                    <p class="print-section-title">2. Điều khoản mượn sách</p>
                    <p class="print-terms-copy">
                        Bạn đọc có trách nhiệm trả sách đúng hạn, tình trạng sách phải như lúc ban đầu. 
                        Trong trường hợp có vi phạm, phải nộp phạt theo đúng quy định của thư viện.
                    </p>
                </div>

                <!-- Chữ ký xác nhận -->
                <div class="print-signatures">
                    <div class="print-signature">
                        <p class="print-signature-title">Người mượn sách</p>
                        <p class="print-signature-note">(Ký tên)</p>
                    </div>
                    <div class="print-signature">
                        <p class="print-signature-title">Thủ thư</p>
                        <p class="print-signature-note">(Ký tên)</p>
                    </div>
                </div>
            </div>

            <form action="loan/print" method="post">
                <button type="submit" class="button-a green">In phiếu mượn</button>
            </form>
        </main>
        
        <% if (Boolean.TRUE.equals(request.getAttribute("shouldPrint"))) { %>
            <script>window.print();</script>
        <% } %>
    </body>
</html>