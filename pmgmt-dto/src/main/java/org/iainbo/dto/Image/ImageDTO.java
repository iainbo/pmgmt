package org.iainbo.dto.Image;

import org.iainbo.dto.Gallery.GalleryDTO;

import java.util.List;

public class ImageDTO {

    private Long id;
    private String title;
    private String description;
    private GalleryDTO galleryDTO;
    private RevisionDTO revisionDTO;
    private List<RevisionDTO> revisions;

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

    public List<RevisionDTO> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<RevisionDTO> revisions) {
        this.revisions = revisions;
    }
}
