package org.iainbo.dto;

import java.util.Date;

public class ImageDTO {

    private Long id;
    private String title;
    private GalleryDTO galleryDTO;
    private UserDTO uploadedBy;
    private Date dateTaken;
    private Date dateUploaded;

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

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }
}
