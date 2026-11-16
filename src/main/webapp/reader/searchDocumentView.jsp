<%@page import="com.pttk.model.user.Member1"%>
<%@page import="com.pttk.model.user.Reader1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.pttk.model.document.Document1, java.util.List"%>
<%@page import="com.pttk.model.document.AuthorDocument1"%>
<%@page import="com.pttk.utils.JspUtils"%>

<%
    List<Document1> documents = (List<Document1>) request.getAttribute("documents");
    String keyword = (String) request.getAttribute("keyword");
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Tìm kiếm tài liệu</title>

        <base href="${pageContext.request.contextPath}/">

        <link href="assets/css/main.css" rel="stylesheet">
        <link href="assets/css/searchView.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />
        
        <main>
            <h1>Tìm kiếm tài liệu</h1>
            <form action="document/search" method="get">
                <input type="search" name="keyword" value="<%= JspUtils.escapeHtml(keyword)%>"
                       placeholder="Tên tài liệu, ISBN, mô tả hoặc tác giả" aria-label="Từ khóa tìm kiếm" maxlength="255">
                <button type="submit">Tìm kiếm</button>
            </form>

            <% if (documents != null) {%>
                <p class="count">Tìm thấy <%= documents == null ? 0 : documents.size()%> tài liệu.</p>
                
                <% if (!documents.isEmpty()) { %>
                    <% int cnt = 1; %>
                    <table>
                        <thead><tr>
                            <th>STT</th>
                            <th>Mã tài liệu</th>
                            <th>Tên tài liệu</th>
                            <th>Loại tài liệu</th>
                            <th>Ấn bản</th>
                            <th>Ngôn ngữ</th>
                            <th>Tác giả</th>
                            <th>NXB</th>
                        </tr></thead>
                        <tbody>
                            <% for (Document1 document : documents) {%>
                            <tr class="clickable" 
                                data-href="document/detail?documentId=<%= document.getId()%>">
                                
                                <td><%= cnt++%></td>
                                <td><%= document.getId()%></td>
                                <td><%= JspUtils.escapeHtml(document.getName())%></td>
                                <td><%= JspUtils.escapeHtml(document.getCategory() != null
                                        ? document.getCategory().getName() : "-")%></td>
                                <td><%= JspUtils.escapeHtml(document.getEdition() != null ? document.getEdition(): "-")%></td>
                                <td><%= JspUtils.escapeHtml(document.getLanguage() != null ? document.getLanguage(): "-")%></td>

                                <td>
                                    <% AuthorDocument1[] authors = document.getAuthors(); %>
                                    <% if (authors != null && authors.length > 0) { %>
                                        <% for (int i = 0; i < authors.length; i++) {
                                            AuthorDocument1 ad = authors[i];
                                            if (ad != null && ad.getAuthor() != null) {%>

                                            <a href="author/info?authorId=<%= ad.getAuthor().getId()%>">
                                                <%= JspUtils.escapeHtml(ad.getAuthor().getName())%>
                                            </a><%= i < authors.length - 1 ? ", " : ""%>

                                            <% }
                                        }
                                    } else { %>
                                    -
                                    <% }%>
                                </td>

                                <td><%= JspUtils.escapeHtml(document.getPublisher() != null ? document.getPublisher().getName() : "")%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>Không tìm thấy tài liệu phù hợp. Hãy thử từ khóa khác.</p>
                <% } %>
            <% } %>
        </main>
        <script src="assets/js/utils.js"></script>
    </body>
</html>
