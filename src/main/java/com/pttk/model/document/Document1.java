package com.pttk.model.document;

import java.time.LocalDate;

public class Document1 {

    private Long id;
    private String name;
    private String ISBN;
    private String description;
    private LocalDate publishDate;
    private String edition;
    private String language;
    private Integer pages;
    private String coverImage;
    private Publisher1 publisher;
    private Category1 category;
    private DocumentCopy1[] copies;
    private AuthorDocument1[] authors;

    public AuthorDocument1[] getAuthors() {
        return authors;
    }

    public void setAuthors(AuthorDocument1[] authors) {
        this.authors = authors;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Publisher1 getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher1 publisher) {
        this.publisher = publisher;
    }

    public Category1 getCategory() {
        return category;
    }

    public void setCategory(Category1 category) {
        this.category = category;
    }

    public DocumentCopy1[] getCopies() {
        return copies;
    }

    public void setCopies(DocumentCopy1[] copies) {
        this.copies = copies;
    }

}
