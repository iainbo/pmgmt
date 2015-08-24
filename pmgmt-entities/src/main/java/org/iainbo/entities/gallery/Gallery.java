package org.iainbo.entities.gallery;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "GALLERIES")
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;

    @NotNull
    @Column(unique = true, name = "NAME")
    private String galleryName;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "DATE_CREATED")
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gallery", cascade = CascadeType.ALL)
    @Transient
    private List<Image> images;

    @Column(name = "THUMBNAIL", columnDefinition = "mediumblob")
    private byte[] thumbnail;

    public Gallery(){

    }

    public Gallery(String galleryName, Date date, User user, String description){
        this.galleryName = galleryName;
        this.dateCreated = date;
        this.owner = user;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
