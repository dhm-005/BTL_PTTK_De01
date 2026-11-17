<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.user.Reader1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Reader1 reader = (Reader1) request.getSession().getAttribute("reader");
    String cardBarcode = (String) request.getAttribute("cardBarcode");
    boolean searched = Boolean.TRUE.equals(request.getAttribute("searched"));
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="${pageContext.request.contextPath}/">
        <link href="assets/css/main.css" rel="stylesheet">
        
        <title>Tìm kiếm bạn đọc</title>
        <link href="assets/css/searchView.css" rel="stylesheet">
        <link href="assets/css/detailView.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"></jsp:include>
        
        <main>
        <form action="reader/search" method="get" class="search-form">
            <input type="search" name="cardBarcode" value="<%= JspUtils.escapeHtml(cardBarcode) %>"
               placeholder="Mã thẻ thư viện, VD: 1234-5678" >
            <button type="submit">Tìm kiếm</button>
            <button type="button" class="btn-scan"> Quét thẻ </button>
        </form>
        
        <% if(reader == null) { %>
        
            <% if(searched){%>
            <p class="empty-msg">Không tìm thấy thông tin bạn đọc.</p>
            <%}%>
            
        <%} else { %>
        
        
        <div class="detail-card">
            <div class="detail-top">
                    <div class="cover-box">
                        <%  String def = "assets/img/default-avatar.png";
                            String avt = JspUtils.getOrDefaultFile(
                                application, reader.getProfilePicture(), def
                            );
                        %>
                            
                        <img src="<%= JspUtils.escapeHtml(avt)%>" alt="Avatar">
                    </div>

                    <table class="info-table top-table">
                        <tbody>
                            <tr class="highlight-row">
                                <td>Mã bạn đọc</td>
                                <td><%= JspUtils.escapeHtml(reader.getId())%></td>
                            </tr>
                            <tr>
                                <td>Tên bạn đọc</td>
                                <td><%= JspUtils.escapeHtml(reader.getFullname())%></td>
                            </tr>
                            <tr>
                                <td>Sinh nhật</td>
                                <td><%= JspUtils.escapeHtml(reader.getBirthday())%></td>
                            </tr>
                            <tr>
                                <td>Trạng thái tài khoản</td>
                                <td><%= JspUtils.escapeHtml(reader.getAccountStatus()) %></td>
                            </tr>
                        </tbody>
                    </table>
            </div>
        </div>
        
        <a href="loan/temporary">Tiếp tục</a>
        </main>
        <% } %>
    </body>
</html>
