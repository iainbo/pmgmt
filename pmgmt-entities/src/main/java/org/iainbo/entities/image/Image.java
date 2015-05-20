package org.iainbo.entities.image;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "IMAGE")
public class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;


    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;

    @NotNull
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User uploadedBy;

    @NotNull
    @Column(name = "DATE_TAKEN")
    private Date dateTaken;

    @NotNull
    @Column(name = "DATE_UPLOADED")
    private Date dateUploaded;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
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
