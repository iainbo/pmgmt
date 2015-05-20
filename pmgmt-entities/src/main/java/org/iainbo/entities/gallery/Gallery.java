package org.iainbo.entities.gallery;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "GALLERY")
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;

    @NotNull
    @Column(unique = true, name = "NAME")
    private String galleryName;

    @NotNull
    @Column(name = "DATE_CREATED")
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @OneToMany
    @JoinColumn(name = "GALLERY_METADATA_ID")
    @Transient
    private List<GalleryMetadata> metadataList;

    @OneToMany(fetch = FetchType.LAZY)
    @Transient
    private List<Image> images;

    @Column(name = "THUMBNAIL", columnDefinition = "mediumblob")
    private byte[] thumbnail;

    public Gallery(){

    }

    public Gallery(String galleryName, Date date, User user){
        this.galleryName = galleryName;
        this.dateCreated = date;
        this.owner = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String name) {
        this.galleryName = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<GalleryMetadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<GalleryMetadata> metadataList) {
        this.metadataList = metadataList;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
