package org.iainbo.dto;

import java.util.Date;
import java.util.List;

public class GalleryDTO {

    private Long id;
    private String galleryName;
    private Date dateCreated;
    private UserDTO owner;
    private List<GalleryMetadataDTO> galleryMetadataDTOList;
    private List<ImageDTO> imageDTOList;
    private byte[] thumbnail;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public List<GalleryMetadataDTO> getGalleryMetadataDTOList() {
        return galleryMetadataDTOList;
    }

    public void setGalleryMetadataDTOList(List<GalleryMetadataDTO> galleryMetadataDTOList) {
        this.galleryMetadataDTOList = galleryMetadataDTOList;
    }

    public List<ImageDTO> getImageDTOList() {
        return imageDTOList;
    }

    public void setImageDTOList(List<ImageDTO> imageDTOList) {
        this.imageDTOList = imageDTOList;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
