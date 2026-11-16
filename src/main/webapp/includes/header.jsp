<%@page import="com.pttk.utils.JspUtils"%>
<%@page import="com.pttk.model.user.Librarian1"%>
<%@page import="com.pttk.model.user.LibrarianManager1"%>
<%@page import="com.pttk.model.user.Reader1"%>
<%@page import="com.pttk.model.user.Member1"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header class="site-header">
    <a class="brand" href="">LibMan</a>
    <nav class="site-nav">
        <%  Member1 mb = (Member1) session.getAttribute("member"); %>
        <% if (mb != null) { %>
        <div class="user-info">
            <%
                String def = "assets/img/default-avatar.png";
                String avatar = mb.getProfilePicture();
            %>

            <img class="avt" src="<%= JspUtils.getOrDefaultFile(application, avatar, def) %>" alt="Ảnh đại diện">
            <div class="user-meta">
                <div class="name"><%= JspUtils.escapeHtml(mb.getFullname()) %></div>
                <div class="role">
                    <%
                        if (mb instanceof Reader1) out.print("Bạn đọc");
                        else if (mb instanceof LibrarianManager1) out.print("Quản lý thư viện");
                        else if (mb instanceof Librarian1) out.print("Thủ thư");
                        else out.print("Chưa xác định");
                    %>
                </div>
            </div>
        </div>
        <form action="logout" method="post" class="logout-form">
            <button class="nav-link" type=submit>Đăng xuất</button>
        </form>
        <% } else { %>
            <a href="login" class="nav-link">Đăng nhập</a>
        <% } %>
    </nav>
</header>
