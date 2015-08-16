package org.iainbo.entities.image;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.gallery.Gallery;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "IMAGES")
public class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;

    @Transient
    private Revision revision;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image", cascade = CascadeType.ALL)
    private List<Revision> revisions;

    public Image(){

    }

    public Image(String title, Gallery gallery, Revision revision){
        this.title = title;
        this.gallery = gallery;
        this.revision = revision;
    }

    public Image(String title, Gallery gallery, Revision revision, List<Revision> revisions){
        this.title = title;
        this.gallery = gallery;
        this.revision = revision;
        this.revisions = revisions;
    }

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

    public Revision getRevision() {
        for(Revision headRevision : getRevisions()){
            if(headRevision.getHeadRevision().equalsIgnoreCase("Y")){
                revision = headRevision;
            }
        }
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
