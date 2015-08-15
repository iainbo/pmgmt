package org.iainbo.entities.image;

import org.iainbo.entities.common.BaseEntity;
import org.iainbo.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "REVISIONS")
public class Revision extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;

    @NotNull
    @Column(name = "REVISION_NUMBER")
    private String revisionNumber;

    @Column(name = "CHECKED_OUT")
    @Pattern(regexp = "[YN]{1}")
    private String checkedOut;

    @Column(name = "HEAD_REVISION")
    @Pattern(regexp = "[YN]{1}")
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    private String headRevision;

    @NotNull
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User uploadedBy;

    @NotNull
    @Column(name = "DATE_UPLOADED")
    private Date dateUploaded;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "revision",cascade = CascadeType.ALL)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    public Revision(){
        this.headRevision = "Y";
    }

    public Revision(User uploadedBy, Date dateUploaded, String headRevision, File file, String revisionNumber){
        this.uploadedBy = uploadedBy;
        this.dateUploaded = dateUploaded;
        this.headRevision = headRevision;
        this.revisionNumber = revisionNumber;
        this.file = file;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getHeadRevision() {
        return headRevision;
    }

    public void setHeadRevision(String headRevision) {
        this.headRevision = headRevision;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(String revisionNumber) {
        this.revisionNumber = revisionNumber;
    }
}
