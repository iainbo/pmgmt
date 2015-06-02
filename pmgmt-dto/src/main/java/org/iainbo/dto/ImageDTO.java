package org.iainbo.dto;

import java.util.Date;

public class ImageDTO {

    private Long id;
    private String title;
    private GalleryDTO galleryDTO;
    private UserDTO uploadedBy;
    private Date dateUploaded;
    private String filename;
    private byte[] fileData;

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

    public GalleryDTO getGalleryDTO() {
        return galleryDTO;
    }

    public void setGalleryDTO(GalleryDTO galleryDTO) {
        this.galleryDTO = galleryDTO;
    }

    public UserDTO getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserDTO uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
