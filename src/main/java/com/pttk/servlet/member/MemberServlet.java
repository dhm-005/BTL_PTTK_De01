package com.pttk.servlet.member;

import java.io.IOException;
import java.sql.SQLException;

import com.pttk.dao.MemberDAO;
import com.pttk.model.user.Member1;
import com.pttk.utils.AuthUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"", "/login", "/logout"})
public class MemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/login":
                request.getRequestDispatcher("/member/loginView.jsp").forward(request, response);
                break;
            case "":
            case "/":
                showHome(request, response);
                break;
            case "/logout":
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/login":
                login(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "":
            case "/":
                showHome(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (AuthUtils.isRole(request, response, "reader")) {
            request.getRequestDispatcher("/reader/mainReaderView.jsp")
                    .forward(request, response);
        } else if (AuthUtils.isRole(request, response, "librarian")) {
            request.getRequestDispatcher("/librarian/mainLibrarianView.jsp")
                    .forward(request, response);
        } else if (AuthUtils.isRole(request, response, "manager")) {
            request.getRequestDispatcher("/manager/mainManagerView.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("loginError", "Vui lòng nhập tên đăng nhập và mật khẩu.");
            request.getRequestDispatcher("/member/loginView.jsp").forward(request, response);
            return;
        }

        Member1 mb;
        try (MemberDAO memberDAO = new MemberDAO()) {
            mb = memberDAO.auth(username.trim(), password);
        } catch (SQLException | IllegalStateException e) {
            getServletContext().log("Could not authenticate member.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể đăng nhập lúc này. Vui lòng thử lại sau.");
            return;
        }

        if (mb == null) {
            request.setAttribute("loginError", "Tên đăng nhập hoặc mật khẩu không đúng.");
            // Forward thẳng về file JSP
            request.getRequestDispatcher("/member/loginView.jsp").forward(request, response);
            return;
        }

        mb.setPassword(null);
        HttpSession session = request.getSession(true);
        request.changeSessionId();
        session.setAttribute("member", mb);

        // Phân luồng dựa trên chính kiểu lớp thực tế của mb
        response.sendRedirect(request.getContextPath() + "/");

    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
