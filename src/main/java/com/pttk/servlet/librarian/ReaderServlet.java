
package com.pttk.servlet.librarian;

import com.pttk.dao.ReaderDAO;
import com.pttk.model.user.Reader1;
import com.pttk.utils.AuthUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(urlPatterns = {
    "/reader/search-page",
    "/reader/search"
})
public class ReaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        switch (path) {
            case "/reader/search-page":
                getSearchReaderPage(request, response);
                break;
            case "/reader/search":
                handleSearchByCardBarcode(request, response);
                break;
            default:
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy đường dẫn: " + path
                );
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        switch (path) {
            default:
                request.getRequestDispatcher("");
                break;
        }
    }
    
    public void getSearchReaderPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/librarian/searchReaderView.jsp").forward(request, response);
    }
    
    public void handleSearchByCardBarcode(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String cardBarcode = request.getParameter("cardBarcode");
        if (cardBarcode == null || cardBarcode.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/reader/search-page");
            return;
        }
        
        Reader1 reader = null;
        try(ReaderDAO readerDAO = new ReaderDAO()){   
            reader = readerDAO.getReaderByCardBarcode(cardBarcode);
        } catch (SQLException | IllegalStateException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tìm kiếm bạn đọc lúc này.");
            return;
        }
        
        HttpSession session = request.getSession();
        Reader1 selectedReader = (Reader1) session.getAttribute("reader");
        if (selectedReader == null || reader == null
                || !Objects.equals(selectedReader.getId(), reader.getId())) {
            session.removeAttribute("temporaryCopyList");
            session.removeAttribute("unfinishedLoanList");
        }
        session.setAttribute("reader", reader);
        request.setAttribute("cardBarcode", cardBarcode);
        request.setAttribute("searched", true);
        request.getRequestDispatcher("/librarian/searchReaderView.jsp").forward(request, response);
    }
}

