package org.iainbo.dto;

public class ImageDTO {

    private Long id;
    private String title;
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
