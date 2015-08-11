package org.iainbo.pmgmt.view.gallery;

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
    private List<ImageView> images;
    private String numberOfImages;
    private String galleryDescription;

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

    public List<ImageView> getImages() {
        return images;
    }

    public void setImages(List<ImageView> images) {
        this.images = images;
    }

    public String getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(String numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public String getGalleryDescription() {
        return galleryDescription;
    }

    public void setGalleryDescription(String galleryDescription) {
        this.galleryDescription = galleryDescription;
    }
}
