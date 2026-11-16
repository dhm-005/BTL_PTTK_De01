
package com.pttk.model.document;


public class Publisher1 {
    private Long id;
    private String name;
    private String logoImage;
    private String website;
    private String description;

    public Publisher1() {
    }

    public Publisher1(Long id, String name, String logoImage, String website, String description) {
        this.id = id;
        this.name = name;
        this.logoImage = logoImage;
        this.website = website;
        this.description = description;
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

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
