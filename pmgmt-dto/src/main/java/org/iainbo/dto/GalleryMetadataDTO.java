package org.iainbo.dto;

public class GalleryMetadataDTO {

    private Long id;
    private GalleryDTO galleryDTO;
    private String name;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GalleryDTO getGalleryDTO() {
        return galleryDTO;
    }

    public void setGalleryDTO(GalleryDTO galleryDTO) {
        this.galleryDTO = galleryDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
