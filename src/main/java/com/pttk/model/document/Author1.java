
package com.pttk.model.document;


public class Author1 {
    private Long id;
    private String name;
    private String image;
    private String bio;

    public Author1() {
    }

    public Author1(Long id, String name, String image, String bio) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.bio = bio;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
    
}
