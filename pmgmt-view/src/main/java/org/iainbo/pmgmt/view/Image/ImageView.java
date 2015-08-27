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
    private String uploadedBy;
    private String dateUploaded;
    private String filename;
    private String revisionNo;
    private Long revisionId;
    private byte[] fileData;
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

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getRevisionNo() {
        return revisionNo;
    }

    public void setRevisionNo(String revisionNo) {
        this.revisionNo = revisionNo;
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
