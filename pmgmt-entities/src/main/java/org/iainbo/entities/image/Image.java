package org.iainbo.entities.image;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.gallery.Gallery;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;

    @Transient
    private Revision revision;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image", cascade = CascadeType.ALL)
    private Set<Revision> revisions;

    public Image(){

    }

    public Image(String title, Gallery gallery, Revision revision){
        this.title = title;
        this.gallery = gallery;
        this.revision = revision;
    }

    public Image(String title, Gallery gallery, Revision revision, Set<Revision> revisions){
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

    public Set<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(Set<Revision> revisions) {
        this.revisions = revisions;
    }
}
