package com.pttk.servlet.reader;

import com.pttk.dao.DocumentDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.pttk.model.document.Document1;
import com.pttk.utils.AuthUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
    "/document/search-page",
    "/document/search",
    "/document/detail"
})
public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String path = request.getServletPath();
        
        switch (path) {
            case "/document/search-page":
                getSearchPage(request, response);
                break;
            case "/document/search":
                System.out.println("Thực hiện tìm kiếm tài liệu");
                handleSearchByKeyword(request, response);
                break;
            case "/document/detail":
                System.out.println("Thực hiện lấy chi tiết tài liệu");
                listDetail(request, response);
                break;
            default:
                System.out.println("Lỗi");
                response.sendRedirect(request.getContextPath() + "/reader/search-page");
                break;
        }
        
    }
    
    public void getSearchPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "reader")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/reader/searchDocumentView.jsp").forward(request, response);
    }
    
    public void handleSearchByKeyword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        if (!AuthUtils.isRole(request, response, "reader")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String keyword = request.getParameter("keyword");
        if(keyword == null || keyword.isBlank()){
            response.sendRedirect(request.getContextPath() + "/document/search-page");
            return;
        }

        request.setAttribute("keyword", keyword.trim());
        
        List<Document1> documents;
        try (DocumentDAO documentDAO = new DocumentDAO()) {
            documents = documentDAO.searchDocuments(keyword);
        } catch (SQLException | IllegalStateException e) {
            getServletContext().log("Could not search library documents.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tìm kiếm tài liệu lúc này.");
            return;
        }
        request.setAttribute("documents", documents);
        request.getRequestDispatcher("/reader/searchDocumentView.jsp").forward(request, response);
        
    }
    
    public void listDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String documentId = request.getParameter("documentId");
        try (DocumentDAO documentDAO = new DocumentDAO()) {
            Document1 document = documentDAO.getDetail(Long.valueOf(documentId));
            request.setAttribute("document", document);
            request.getRequestDispatcher("/reader/documentDetailView.jsp").forward(request, response);
        }  catch (SQLException | IllegalStateException e) {
            getServletContext().log("Could not load document details.", e);
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Không thể tải chi tiết tài liệu lúc này."
            );
        }
    }
}
