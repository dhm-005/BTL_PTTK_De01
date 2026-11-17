package com.pttk.dao;

import com.pttk.model.document.Author1;
import com.pttk.model.document.AuthorDocument1;
import com.pttk.model.document.Category1;
import com.pttk.model.document.Document1;
import com.pttk.model.document.DocumentCopy1;
import com.pttk.model.document.Location1;
import com.pttk.model.document.Publisher1;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentDAO extends DAO {

    public DocumentDAO() {
    }

    public List<Document1> searchDocuments(String keyword) throws SQLException {
        String query = keyword;
        String pattern = "%" + query + "%";

        String sql = "SELECT d.id, d.name, d.edition, d.`language`, d.coverImage, "
                + "d.tblCategory1ID, c.name AS categoryName, "
                + "d.tblPublisher1ID, p.name AS publisherName, "
                + "ad.id AS authorDocID, a.id AS authorID, a.name AS authorName "
                + "FROM tblDocument1 d "
                + "LEFT JOIN tblPublisher1 p ON p.id = d.tblPublisher1ID "
                + "LEFT JOIN tblCategory1 c ON c.id = d.tblCategory1ID "
                + "LEFT JOIN tblAuthorDocument1 ad ON ad.tblDocument1 = d.id "
                + "LEFT JOIN tblAuthor1 a ON a.id = ad.tblAuthor1 "
                + "WHERE (? = '.' OR d.name LIKE ? OR COALESCE(d.ISBN, '') LIKE ? "
                + "OR COALESCE(d.`description`, '') LIKE ? OR COALESCE(a.name, '') LIKE ?) "
                + "ORDER BY d.name, d.publishDate DESC "
                + "LIMIT 100";

        Map<Long, Document1> documentMap = new java.util.LinkedHashMap<>();
        Map<Long, List<AuthorDocument1>> documentAuthorsMap = new java.util.HashMap<>();

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, query);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            statement.setString(4, pattern);
            statement.setString(5, pattern);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long docId = rs.getLong("id");

                    // Lấy hoặc khởi tạo Document1
                    Document1 document = documentMap.get(docId);
                    if (document == null) {
                        document = new Document1();
                        document.setId(docId);
                        document.setName(rs.getString("name"));
                        document.setLanguage(rs.getString("language"));
                        document.setCoverImage(rs.getString("coverImage"));

                        // Xử lý Category
                        Category1 cat = null;
                        Long catId = rs.getLong("tblCategory1ID");
                        if (!rs.wasNull()) {
                            cat = new Category1();
                            cat.setId(catId);
                            cat.setName(rs.getString("categoryName"));
                        }
                        document.setCategory(cat);

                        // Xử lý Publisher
                        Publisher1 p = null;
                        Long pubId = rs.getLong("tblPublisher1ID");
                        if (!rs.wasNull()) {
                            p = new Publisher1();
                            p.setId(pubId);
                            p.setName(rs.getString("publisherName"));
                        }
                        document.setPublisher(p);

                        documentMap.put(docId, document);
                        documentAuthorsMap.put(docId, new ArrayList<>());
                    }

                    // Xử lý Author & AuthorDocument
                    Long authorDocId = rs.getLong("authorDocID");
                    if (!rs.wasNull()) {
                        Long authorId = rs.getLong("authorID");
                        if (!rs.wasNull()) {
                            Author1 author = new Author1();
                            author.setId(authorId);
                            author.setName(rs.getString("authorName"));

                            // Khởi tạo đối tượng AuthorDocument1
                            AuthorDocument1 audoc = new AuthorDocument1();
                            audoc.setId(authorDocId);
                            audoc.setAuthor(author);

                            // Tránh thêm trùng lặp tác giả
                            List<AuthorDocument1> authList = documentAuthorsMap.get(docId);
                            boolean exists = false;
                            for (AuthorDocument1 existing : authList) {
                                if (existing.getId() != null && existing.getId().equals(authorDocId)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                authList.add(audoc);
                            }
                        }
                    }
                }
            }
        }

        // Gán danh sách tác giả cho Document1
        for (Entry<Long, Document1> entry : documentMap.entrySet()) {
            Long docId = entry.getKey();
            Document1 doc = entry.getValue();
            List<AuthorDocument1> authList = documentAuthorsMap.get(docId);
            doc.setAuthors(authList);
        }

        return new ArrayList<>(documentMap.values());
    }

    public Document1 getDetail(Long id) throws SQLException {
        String sql = "SELECT d.id, d.name, d.ISBN, d.pages, d.publishDate, "
                + "d.edition, d.`language`, d.coverImage, d.description, "
                + "d.tblCategory1ID, c.name AS categoryName, "
                + "d.tblPublisher1ID, p.name AS publisherName, "
                + "ad.id AS authorDocID, a.id AS authorID, a.name AS authorName, "
                + "dc.id AS copyID, dc.status, dc.physicalCondition, "
                + "l.id as locationID, l.section, l.floor, l.room, l.shelf "
                + "FROM tblDocument1 d "
                + "LEFT JOIN tblPublisher1 p ON p.id = d.tblPublisher1ID "
                + "LEFT JOIN tblCategory1 c ON c.id = d.tblCategory1ID "
                + "LEFT JOIN tblAuthorDocument1 ad ON ad.tblDocument1 = d.id "
                + "LEFT JOIN tblAuthor1 a ON a.id = ad.tblAuthor1 "
                + "LEFT JOIN tblDocumentCopy1 dc ON d.id = dc.tblDocument1ID "
                + "AND dc.status = 'AVAILABLE' "
                + "AND dc.physicalCondition <> 'BADLY_DAMAGED' "
                + "LEFT JOIN tblLocation1 l ON l.id = dc.tblLocation1ID "
                + "WHERE d.id = ? ";

        Document1 document = null;
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {

                List<AuthorDocument1> authList = new ArrayList<>();
                List<DocumentCopy1> copies = new ArrayList<>();
                
                document = new Document1();
                
                while (rs.next()) {
                    document.setId(id);
                    document.setISBN(rs.getString("ISBN"));
                    document.setName(rs.getString("name"));
                    document.setPages(rs.getInt("pages"));
                    
                    Date pubDate = rs.getDate("publishDate");
                    if (pubDate != null) {
                        document.setPublishDate(pubDate.toLocalDate());
                    }

                    document.setLanguage(rs.getString("language"));
                    document.setEdition(rs.getString("edition"));
                    document.setDescription(rs.getString("description"));

                    document.setCoverImage(rs.getString("coverImage"));

                    // Xử lý Category
                    if (document.getCategory() == null) {
                        Category1 cat = null;
                        Long catId = rs.getLong("tblCategory1ID");
                        if (!rs.wasNull()) {
                            cat = new Category1();
                            cat.setId(catId);
                            cat.setName(rs.getString("categoryName"));
                        }
                        document.setCategory(cat);
                    }

                    // Xử lý Publisher
                    if (document.getPublisher() == null) {
                        Publisher1 p = null;
                        Long pubId = rs.getLong("tblPublisher1ID");
                        if (!rs.wasNull()) {
                            p = new Publisher1();
                            p.setId(pubId);
                            p.setName(rs.getString("publisherName"));
                        }
                        document.setPublisher(p);
                    }

                    // Xử lý Author & AuthorDocument
                    Long authorDocId = rs.getLong("authorDocID");
                    if (!rs.wasNull()) {
                        Long authorId = rs.getLong("authorID");
                        if (!rs.wasNull()) {
                            Author1 author = new Author1();
                            author.setId(authorId);
                            author.setName(rs.getString("authorName"));

                            // Khởi tạo đối tượng AuthorDocument1
                            AuthorDocument1 audoc = new AuthorDocument1();
                            audoc.setId(authorDocId);
                            audoc.setAuthor(author);

                            // Tránh thêm trùng lặp tác giả
                            boolean exists = false;
                            for (AuthorDocument1 existing : authList) {
                                if (existing.getId() != null && existing.getId().equals(authorDocId)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                authList.add(audoc);
                            }
                        }
                    }

//                  + "dc.id AS copyID, dc.status, dc.physicalCondition, "
                    //xử lí copies
                    DocumentCopy1 copy = null;
                    Long copyID = rs.getLong("copyID");
                    if (!rs.wasNull()) {
                        copy = new DocumentCopy1();
                        copy.setId(copyID);
                        copy.setStatus(
                            DocumentCopy1.Status.valueOf(
                                rs.getString("status").trim().toUpperCase()
                            )
                        );
                        copy.setPhysicalCondition(
                            DocumentCopy1.PhysicalCondition.valueOf(
                                rs.getString("physicalCondition").trim().toUpperCase()
                            )
                        );

//                  + "l.id as locationID, l.section, l.floor, l.room, l.shelf "
                        Location1 loc = null;
                        Long locID = rs.getLong("locationID");
                        if (!rs.wasNull()) {
                            loc = new Location1();
                            loc.setId(locID);
                            loc.setSection(rs.getString("section"));
                            loc.setFloor(rs.getString("floor"));
                            loc.setRoom(rs.getString("room"));
                            loc.setShelf(rs.getString("shelf"));
                        }
                        copy.setLocation(loc);

                        // Tránh thêm trùng lặp copy
                        boolean exists = false;
                        for (DocumentCopy1 existing : copies) {
                            if (existing.getId() != null && existing.getId().equals(copyID)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            copies.add(copy);
                        }

                    }
                }
                document.setAuthors(authList);
                document.setCopies(copies);
            }

        } 
        return document;
    }
}
