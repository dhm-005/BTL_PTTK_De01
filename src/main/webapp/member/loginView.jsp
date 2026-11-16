<%@page import="com.pttk.utils.JspUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đăng nhập - LibMan</title>
        <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
    </head>
    <body>
        <main class="login-card">
            <a class="back-link" href="${pageContext.request.contextPath}/">← Trang chủ</a>
            <p class="eyebrow">LIBMAN</p>
            <h1>Chào mừng trở lại</h1>
            <p class="subtitle">Đăng nhập bằng tài khoản bạn đọc thư viện.</p>
            <% if (request.getAttribute("loginError") != null) { %>
                <p class="error"><%= JspUtils.escapeHtml(request.getAttribute("loginError")) %></p>
            <% } %>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <label for="username">Tên đăng nhập</label>
                <input id="username" name="username" type="text" autocomplete="username"
                       maxlength="255" required>
                <label for="password">Mật khẩu</label>
                <input id="password" name="password" type="password" autocomplete="current-password"
                       maxlength="255" required>
                <button type="submit">Đăng nhập</button>
            </form>
        </main>
    </body>
</html>
