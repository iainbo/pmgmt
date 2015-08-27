package org.iainbo.pmgmt.view.gallery;

import org.iainbo.pmgmt.view.Image.ImageView;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class GalleryView implements Serializable{

    private Long id;
    private String galleryName;
    private String owner;
    private boolean galleryIsEmpty;
    private byte[] thumbnail;
    private List<ImageView> images;
    private String numberOfImages;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isGalleryIsEmpty() {
        return galleryIsEmpty;
    }

    public void setGalleryIsEmpty(boolean galleryIsEmpty) {
        this.galleryIsEmpty = galleryIsEmpty;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
