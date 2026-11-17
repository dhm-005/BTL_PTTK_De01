
package com.pttk.dao;

import com.pttk.model.borrowreturn.Reservation1;
import com.pttk.model.document.Document1;
import com.pttk.model.user.Librarian1;
import com.pttk.model.user.Reader1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ReservationDAO extends DAO {

    public ReservationDAO() {
    }

    public boolean hasActiveReservationForAnotherReader(Long documentId, Long readerId)
            throws SQLException {
        String sql = "SELECT 1 FROM tblReservation1 "
                + "WHERE tblDocument1ID = ? AND (memberID IS NULL OR memberID <> ?) "
                + "AND status = ? "
                + "AND expiryDate >= CURRENT_TIMESTAMP "
                + "LIMIT 1";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, documentId);
            statement.setLong(2, readerId);
            statement.setString(3, Reservation1.Status.ACTIVE.name());
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int processAfterLoan(List<Reservation1> reservations) throws SQLException {
        if (reservations == null || reservations.isEmpty()) {
            throw new IllegalArgumentException("Không có tài liệu để đối chiếu đặt trước.");
        }

        Reservation1 firstReservation = reservations.get(0);
        Reader1 reader = firstReservation == null ? null : firstReservation.getReader();
        Librarian1 librarian = firstReservation == null ? null : firstReservation.getLibrarian();
        if (reader == null || reader.getId() == null
                || librarian == null || librarian.getId() == null
                || firstReservation.getStatus() != Reservation1.Status.DONE
                || firstReservation.getProcessDate() == null) {
            throw new IllegalArgumentException("Thông tin đặt trước cần xử lý không hợp lệ.");
        }

        Long readerId = reader.getId();
        Long librarianId = librarian.getId();
        Set<Long> documentIds = new LinkedHashSet<>();
        for (Reservation1 reservation : reservations) {
            Document1 document = reservation == null ? null : reservation.getDocument();
            if (reservation == null
                    || reservation.getReader() == null
                    || !Objects.equals(readerId, reservation.getReader().getId())
                    || reservation.getLibrarian() == null
                    || !Objects.equals(librarianId, reservation.getLibrarian().getId())
                    || reservation.getStatus() != Reservation1.Status.DONE
                    || reservation.getProcessDate() == null
                    || document == null || document.getId() == null) {
                throw new IllegalArgumentException("Thông tin đặt trước cần xử lý không hợp lệ.");
            }
            documentIds.add(document.getId());
        }

        String placeholders = String.join(", ", java.util.Collections.nCopies(documentIds.size(), "?"));
        String sql = "UPDATE tblReservation1 "
                + "SET status = ?, processDate = ?, librarianID = ? "
                + "WHERE memberID = ? AND status = ? AND expiryDate >= ? "
                + "AND tblDocument1ID IN (" + placeholders + ")";
        Timestamp processDate = Timestamp.valueOf(firstReservation.getProcessDate());

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            int parameter = 1;
            statement.setString(parameter++, firstReservation.getStatus().name());
            statement.setTimestamp(parameter++, processDate);
            statement.setLong(parameter++, librarianId);
            statement.setLong(parameter++, readerId);
            statement.setString(parameter++, Reservation1.Status.ACTIVE.name());
            statement.setTimestamp(parameter++, processDate);
            for (Long documentId : documentIds) {
                statement.setLong(parameter++, documentId);
            }
            return statement.executeUpdate();
        }
    }
}
