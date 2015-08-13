package org.iainbo.dto;

public class ImageDTO {

    private Long id;
    private String title;
    private String description;
    private GalleryDTO galleryDTO;
    private RevisionDTO revisionDTO;

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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GalleryDTO getGalleryDTO() {
        return galleryDTO;
    }

    public void setGalleryDTO(GalleryDTO galleryDTO) {
        this.galleryDTO = galleryDTO;
    }

    public RevisionDTO getRevisionDTO() {
        return revisionDTO;
    }

    public void setRevisionDTO(RevisionDTO revisionDTO) {
        this.revisionDTO = revisionDTO;
    }
}
