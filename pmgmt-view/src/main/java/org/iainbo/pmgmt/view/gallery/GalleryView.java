package org.iainbo.pmgmt.view.gallery;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class GalleryView implements Serializable{

    private String galleryName;
    private String owner;

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
}
