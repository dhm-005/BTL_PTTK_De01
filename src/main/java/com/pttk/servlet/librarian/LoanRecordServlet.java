
package com.pttk.servlet.librarian;

import com.pttk.dao.LoanDetailDAO;
import com.pttk.dao.LoanRecordDAO;
import com.pttk.dao.ReservationDAO;
import com.pttk.model.borrowreturn.LoanDetail1;
import com.pttk.model.borrowreturn.LoanRecord1;
import com.pttk.model.borrowreturn.Reservation1;
import com.pttk.model.document.DocumentCopy1;
import com.pttk.model.user.Librarian1;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = {
    "/loan/temporary",
    "/loan/create",
    "/loan/print"
})
public class LoanRecordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        switch (path) {
            case "/loan/temporary":
                listUnfinishedLoan(request, response);
                listTemporary(request, response);
                break;
            case "/loan/create":
                response.sendRedirect(request.getContextPath() + "/loan/temporary");
                break;
            case "/loan/print":
                handlePrint(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        switch (path) {
            case "/loan/create":
                handleCreateLoan(request, response);
                break;
            case "/loan/temporary":
                listUnfinishedLoan(request, response);
                listTemporary(request, response);
                break;
            case "/loan/print":
                handlePrint(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    public void listTemporary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        HttpSession session = request.getSession();
        DocumentCopy1 copy = (DocumentCopy1) session.getAttribute("tempCopy");
        session.removeAttribute("tempCopy");
        if (!"true".equals(request.getParameter("addCopy"))) {
            copy = null;
        }
        
        List<DocumentCopy1> temporaryCopyList = 
                (List<DocumentCopy1>) session.getAttribute("temporaryCopyList");
        if (temporaryCopyList == null) {
            temporaryCopyList = new ArrayList<>();
        }
        
        Reader1 reader = (Reader1) session.getAttribute("reader");
        List<LoanDetail1> unfinishedLoanList =
                (List<LoanDetail1>) session.getAttribute("unfinishedLoanList");
        if (copy != null && request.getAttribute("errorMessage") == null) {
            if (reader == null || reader.getGroup() == null || reader.getGroup().getPolicy() == null) {
                request.setAttribute("errorMessage", "Chọn bạn đọc có chính sách mượn trước khi thêm sách.");
            } else if (unfinishedLoanList.size() + temporaryCopyList.size()
                    >= reader.getGroup().getPolicy().getMaxBorrowed()) {
                request.setAttribute("errorMessage", "Đã chạm giới hạn mượn, không thể thêm bản sao.");
            } else if (copy.getStatus() != DocumentCopy1.Status.AVAILABLE
                    && copy.getStatus() != DocumentCopy1.Status.RESERVED) {
                request.setAttribute("errorMessage", "Bản sao này hiện không thể cho mượn.");
            } else if (containsCopy(temporaryCopyList, copy.getId())) {
                request.setAttribute("errorMessage", "Bản sao này đã có trong danh sách tạm.");
            } else {
                temporaryCopyList.add(copy);
            }
        }
        
        session.setAttribute("temporaryCopyList", temporaryCopyList);
        request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
    }
    
    public void handleCreateLoan(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        HttpSession session = request.getSession();
        Reader1 reader = (Reader1) session.getAttribute("reader");
        List<DocumentCopy1> temporaryCopyList = (List<DocumentCopy1>) session.getAttribute("temporaryCopyList");
        Librarian1 librarian = (Librarian1) session.getAttribute("member");

        if (request.getAttribute("errorMessage") != null) {
            request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
            return;
        }

        List<LoanDetail1> unfinishedLoanList =
                (List<LoanDetail1>) session.getAttribute("unfinishedLoanList");
        if (reader == null) {
            request.setAttribute("errorMessage", "Vui lòng chọn bạn đọc trước khi tạo phiếu mượn.");
        } else if (reader.getGroup() == null || reader.getGroup().getPolicy() == null) {
            request.setAttribute("errorMessage", "Bạn đọc chưa được thiết lập chính sách mượn.");
        } else if (librarian == null || librarian.getId() == null) {
            request.setAttribute("errorMessage", "Không tìm thấy thông tin thủ thư đang đăng nhập.");
        } else if (temporaryCopyList == null || temporaryCopyList.isEmpty()) {
            request.setAttribute("errorMessage", "Danh sách mượn tạm đang trống.");
        } else if (unfinishedLoanList.size() + temporaryCopyList.size()
                > reader.getGroup().getPolicy().getMaxBorrowed()) {
            request.setAttribute("errorMessage", "Số sách đang mượn và mượn thêm vượt quá giới hạn "
                    + reader.getGroup().getPolicy().getMaxBorrowed() + ".");
        }
        if (request.getAttribute("errorMessage") != null) {
            request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
            return;
        }

        int loanPeriod = reader.getGroup().getPolicy().getLoanPeriod();
        List<LoanDetail1> details = new ArrayList<>();
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            for (DocumentCopy1 copy : temporaryCopyList) {
                if (copy.getId() == null || copy.getDocument() == null
                        || copy.getDocument().getId() == null) {
                    request.setAttribute("errorMessage", "Thông tin bản sao hoặc tài liệu không hợp lệ.");
                    request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
                    return;
                }
                if (copy.getStatus() != DocumentCopy1.Status.AVAILABLE
                        && copy.getStatus() != DocumentCopy1.Status.RESERVED) {
                    request.setAttribute("errorMessage", "Bản sao " + copy.getBarCode()
                            + " hiện không thể cho mượn.");
                    request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
                    return;
                }
                if (reservationDAO.hasActiveReservationForAnotherReader(
                        copy.getDocument().getId(), reader.getId())) {
                    request.setAttribute("errorMessage", "Tài liệu "
                            + copy.getDocument().getName()
                            + " đang có bạn đọc khác đặt trước, không thể cho mượn.");
                    request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
                    return;
                }

                LoanDetail1 detail = new LoanDetail1();
                detail.setDueDate(LocalDate.now().plusDays(loanPeriod));
                detail.setCopy(copy);
                detail.setStatus(LoanDetail1.Status.BORROWING);
                details.add(detail);
            }
        } catch (SQLException | IllegalStateException ex) {
            getServletContext().log("Could not check reservations for a loan.", ex);
            request.setAttribute("errorMessage", "Không thể kiểm tra đặt trước lúc này: " + ex.getMessage());
            request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
            return;
        }

        LoanRecord1 record = new LoanRecord1();
        record.setReader1(reader);
        record.setLibrarian1(librarian);
        record.setLoanDate(LocalDateTime.now());
        record.setStatus(LoanRecord1.Status.ACTIVE);
        record.setDetails(details);
        
        boolean loanCreated = false;
        String postCreateWarning = null;
        try (LoanRecordDAO recordDAO = new LoanRecordDAO()) {
            boolean result = recordDAO.createLoanRecord(record);
            if (result) {
                loanCreated = true;
            } else {
                request.setAttribute("errorMessage",
                        "Không tạo được phiếu mượn do dữ liệu bạn đọc, thủ thư hoặc bản sao không hợp lệ.");
            }
        } catch (SQLException | IllegalStateException | IllegalArgumentException ex) {
            getServletContext().log("Could not create loan record.", ex);
            if (loanCreated) {
                postCreateWarning = "Phiếu mượn đã được lưu nhưng có lỗi khi kết thúc kết nối: " + ex.getMessage();
            } else {
                request.setAttribute("errorMessage", "Tạo phiếu mượn thất bại: " + ex.getMessage());
            }
        }

        if (loanCreated) {
            session.removeAttribute("temporaryCopyList");
            unfinishedLoanList.addAll(details);
            session.setAttribute("unfinishedLoanList", unfinishedLoanList);

            try (ReservationDAO reservationDAO = new ReservationDAO()) {
                List<Reservation1> processedReservations = new ArrayList<>();
                LocalDateTime processDate = LocalDateTime.now();
                for (LoanDetail1 detail : details) {
                    Reservation1 reservation = new Reservation1();
                    reservation.setDocument(detail.getCopy().getDocument());
                    reservation.setReader(reader);
                    reservation.setLibrarian(librarian);
                    reservation.setStatus(Reservation1.Status.DONE);
                    reservation.setProcessDate(processDate);
                    processedReservations.add(reservation);
                }
                reservationDAO.processAfterLoan(processedReservations);
            } catch (SQLException | IllegalStateException | IllegalArgumentException ex) {
                getServletContext().log("Loan created but matching reservations could not be processed.", ex);
                postCreateWarning = "Phiếu mượn đã tạo nhưng không cập nhật được đặt trước: " + ex.getMessage();
            }

            session.setAttribute("loanRecordToPrint", record);
            if (postCreateWarning == null) {
                session.removeAttribute("loanPrintWarning");
            } else {
                session.setAttribute("loanPrintWarning", postCreateWarning);
            }
            response.sendRedirect(request.getContextPath() + "/loan/print");
            return;
        }
        request.getRequestDispatcher("/librarian/borrowDocumentView.jsp").forward(request, response);
    }

    private void handlePrint(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!AuthUtils.isRole(request, response, "librarian")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        HttpSession session = request.getSession();
        LoanRecord1 record = (LoanRecord1) session.getAttribute("loanRecordToPrint");
        if (record == null) {
            response.sendRedirect(request.getContextPath() + "/loan/temporary");
            return;
        }

        request.setAttribute("loanRecord", record);
        request.setAttribute("warningMessage", session.getAttribute("loanPrintWarning"));
        request.setAttribute("shouldPrint", "POST".equalsIgnoreCase(request.getMethod()));
        request.getRequestDispatcher("/librarian/printLoanRecordView.jsp").forward(request, response);
    }
    
    public void listUnfinishedLoan(HttpServletRequest request, HttpServletResponse response) {
        Reader1 reader = (Reader1) request.getSession().getAttribute("reader");
        if (reader == null || reader.getId() == null) {
            request.getSession().setAttribute("unfinishedLoanList", new ArrayList<LoanDetail1>());
            return;
        }

        try (LoanDetailDAO loanDetailDAO = new LoanDetailDAO()) {
            List<LoanDetail1> unfinishedLoanList = loanDetailDAO.getUnfinishedLoan(reader.getId());
            request.getSession().setAttribute("unfinishedLoanList", unfinishedLoanList);
        } catch (SQLException ex) {
            getServletContext().log("Could not load unfinished loans.", ex);
            request.setAttribute("errorMessage", "Không thể tải danh sách sách chưa trả: " + ex.getMessage());
        } catch (IllegalStateException ex) {
            getServletContext().log("Could not connect to load unfinished loans.", ex);
            request.setAttribute("errorMessage", "Không thể tải danh sách sách chưa trả: " + ex.getMessage());
        }
    }

    private boolean containsCopy(List<DocumentCopy1> copies, Long copyId) {
        if (copyId == null) {
            return false;
        }
        for (DocumentCopy1 copy : copies) {
            if (copyId.equals(copy.getId())) {
                return true;
            }
        }
        return false;
    }
}
