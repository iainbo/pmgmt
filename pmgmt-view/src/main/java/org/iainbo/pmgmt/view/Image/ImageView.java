package org.iainbo.pmgmt.view.Image;

import org.iainbo.pmgmt.view.gallery.GalleryView;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@ViewScoped
public class ImageView implements Serializable{

    private Long id;
    private String title;
    private String Description;
    private GalleryView galleryView;
    private RevisionView revisionView;
    private Long revisionId;
    private boolean imageIsCheckedOut;
    private Map<Long, String> revisions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public GalleryView getGalleryView() {
        return galleryView;
    }

    public void setGalleryView(GalleryView galleryView) {
        this.galleryView = galleryView;
    }

    public RevisionView getRevisionView() {
        return revisionView;
    }

    public void setRevisionView(RevisionView revisionView) {
        this.revisionView = revisionView;
    }

    public Long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Long revisionId) {
        this.revisionId = revisionId;
    }

    public boolean isImageIsCheckedOut() {
        return imageIsCheckedOut;
    }

    public void setImageIsCheckedOut(boolean imageIsCheckedOut) {
        this.imageIsCheckedOut = imageIsCheckedOut;
    }

    public Map<Long, String> getRevisions() {
        return revisions;
    }

    public void setRevisions(Map<Long, String> revisions) {
        this.revisions = revisions;
    }
}
