
package com.pttk.servlet.librarian;

import com.pttk.dao.DocumentCopyDAO;
import com.pttk.dao.ReaderDAO;
import com.pttk.model.document.DocumentCopy1;
import com.pttk.model.user.Reader1;
import com.pttk.utils.AuthUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(urlPatterns = {
    "/copy/search-page",
    "/copy/search"
})
public class DocumentCopyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        switch (path) {
            case "/copy/search-page":
                getSearchCopyPage(request, response);
                break;
            case "/copy/search":
                handleSearchByBarcode(request, response);
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
    
    public void getSearchCopyPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/librarian/searchCopyView.jsp").forward(request, response);
    }
    
    public void handleSearchByBarcode(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String barcode = request.getParameter("barcode");
        if (barcode == null || barcode.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/copy/search-page");
            return;
        }
        
        DocumentCopy1 copy = null;
        try(DocumentCopyDAO copyDAO = new DocumentCopyDAO()){   
            copy = copyDAO.getCopyByBarcode(barcode);
        } catch (SQLException | IllegalStateException e) {
            getServletContext().log("Could not search library documents.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tìm kiếm bản sao tài liệu lúc này.");
            return;
        }
        
        request.getSession().setAttribute("tempCopy", copy);
        request.setAttribute("barcode", barcode);
        request.setAttribute("searched", true);
        request.getRequestDispatcher("/librarian/searchCopyView.jsp").forward(request, response);
    }
}

