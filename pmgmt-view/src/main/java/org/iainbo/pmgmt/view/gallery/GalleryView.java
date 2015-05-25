package org.iainbo.pmgmt.view.gallery;

import org.iainbo.dto.ImageDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GalleryView implements Serializable{

    private String galleryName;
    private String owner;
    private boolean containsImages;
    private List<ImageDTO> images;
    private String numberOfImages;

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isContainsImages() {
        return containsImages;
    }

    public void setContainsImages(boolean containsImages) {
        this.containsImages = containsImages;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public String getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(String numberOfImages) {
        this.numberOfImages = numberOfImages;
    }
}
