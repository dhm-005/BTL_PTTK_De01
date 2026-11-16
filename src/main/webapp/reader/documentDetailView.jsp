<%@page import="java.util.LinkedHashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.pttk.model.document.Location1"%>
<%@page import="com.pttk.model.document.DocumentCopy1"%>
<%@page import="com.pttk.model.document.AuthorDocument1"%>
<%@page import="com.pttk.model.document.Document1"%>
<%@page import="com.pttk.utils.JspUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chi tiết tài liệu</title>
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        <link href="assets/css/detailView.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <%
            Document1 document = (Document1) request.getAttribute("document");
        %>

        <main class="detail-page">
            <h1>Chi tiết tài liệu</h1>

            <% if (document == null || document.getId() == null) { %>
                <p class="empty-msg">Không tìm thấy thông tin tài liệu.</p>
                <div class="actions">
                    <a class="btn-back" href="document/search-page">Quay lại</a>
                </div>
            <% } else { 
                String defCover = "assets/img/default-cover.jpg";
                String cover = JspUtils.getOrDefaultFile(application, document.getCoverImage(), defCover);

                AuthorDocument1[] authors = document.getAuthors();
                DocumentCopy1[] copies = document.getCopies();
                int availableCount = (copies != null) ? copies.length : 0;

                // Gom các vị trí lưu trữ duy nhất từ danh sách bản sao
                Set<String> locations = new LinkedHashSet<>();
                if (copies != null) {
                    for (DocumentCopy1 c : copies) {
                        if (c != null && c.getLocation() != null) {
                            Location1 loc = c.getLocation();
                            String locText = String.format("Khu %s – Tầng %s – Phòng %s – Kệ %s",
                                    loc.getSection() != null ? loc.getSection() : "-",
                                    loc.getFloor() != null ? loc.getFloor() : "-",
                                    loc.getRoom() != null ? loc.getRoom() : "-",
                                    loc.getShelf() != null ? loc.getShelf() : "-");
                            locations.add(locText);
                        }
                    }
                }
            %>

            <div class="detail-card">

                <!-- Phần trên có ảnh -->
                <div class="detail-top">
                    <div class="cover-box">
                        <img src="<%= JspUtils.escapeHtml(cover) %>" alt="Ảnh tài liệu">
                    </div>

                    <table class="info-table top-table">
                        <tbody>
                            <tr class="highlight-row">
                                <th>Mã tài liệu:</th>
                                <td><%= document.getId() %></td>
                            </tr>
                            <tr>
                                <th>Tên tài liệu:</th>
                                <td><strong><%= JspUtils.escapeHtml(document.getName()) %></strong></td>
                            </tr>
                            <tr>
                                <th>Tác giả:</th>
                                <td>
                                    <% if (authors != null && authors.length > 0) {
                                        for (int i = 0; i < authors.length; i++) {
                                            if (authors[i] != null && authors[i].getAuthor() != null) { %>
                                                <a href="author/info?authorId=<%= authors[i].getAuthor().getId() %>">
                                                    <%= JspUtils.escapeHtml(authors[i].getAuthor().getName()) %>
                                                </a><%= i < authors.length - 1 ? ", " : "" %>
                                            <% }
                                        }
                                    } else { %>-<% } %>
                                </td>
                            </tr>
                            <tr>
                                <th>Loại tài liệu:</th>
                                <td><%= JspUtils.escapeHtml(document.getCategory() != null ? document.getCategory().getName() : "-") %></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Phần dưới -->
                <table class="info-table bottom-table">
                    <colgroup>
                        <col class="col-label-sm">
                        <col class="col-val-sm">
                        <col class="col-label-md">
                        <col class="col-val-lg">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>ISBN</th>
                            <td><%= JspUtils.escapeHtml(document.getISBN() != null && !document.getISBN().isEmpty() ? document.getISBN() : "-") %></td>
                            <th>Nhà xuất bản</th>
                            <td><%= JspUtils.escapeHtml(document.getPublisher() != null ? document.getPublisher().getName() : "-") %></td>
                        </tr>
                        <tr>
                            <th>Số trang</th>
                            <td><%= document.getPages() > 0 ? document.getPages() : "-" %></td>
                            <th>Ngày xuất bản</th>
                            <td><%= document.getPublishDate() != null ? document.getPublishDate().toString() : "-" %></td>
                        </tr>
                        <tr>
                            <th>Ngôn ngữ</th>
                            <td><%= JspUtils.escapeHtml(document.getLanguage() != null && !document.getLanguage().isEmpty() ? document.getLanguage() : "-") %></td>
                            <th>Ấn bản</th>
                            <td><%= JspUtils.escapeHtml(document.getEdition() != null && !document.getEdition().isEmpty() ? document.getEdition() : "-") %></td>
                        </tr>
                        <tr>
                            <th>Số lượng hiện có</th>
                            <td colspan="3"><%= availableCount %> bản sẵn sàng</td>
                        </tr>
                        <tr>
                            <th>Mô tả</th>
                            <td colspan="3" class="desc-cell"><%= JspUtils.escapeHtml(document.getDescription() != null && !document.getDescription().isEmpty() ? document.getDescription() : "-") %></td>
                        </tr>
                        <tr>
                            <th>Vị trí lưu trữ</th>
                            <td colspan="3">
                                <% if (!locations.isEmpty()) {
                                    for (String loc : locations) { %>
                                        <div>- <%= JspUtils.escapeHtml(loc) %></div>
                                    <% }
                                } else { %>
                                    Chưa có bản sao sẵn sàng trên kệ
                                <% } %>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="actions">
                <button type="button" class="btn-back" onclick="history.back()">Quay lại</button>
            </div>

            <% } %>
        </main>
    </body>
</html>