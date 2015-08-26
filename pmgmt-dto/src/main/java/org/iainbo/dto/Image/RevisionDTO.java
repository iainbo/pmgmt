package org.iainbo.dto.Image;

import org.iainbo.dto.User.UserDTO;

import java.util.Date;

public class RevisionDTO {

    private Long id;
    private String revisionNumber;
    private String checkedOut;
    private UserDTO checkedOutBy;
    private String headRevision;
    private String revisionComment;
    private UserDTO uploadedBy;
    private Date dateUploaded;
    private ImageDTO imageDTO;
    private FileDTO fileDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public UserDTO getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(UserDTO checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public String getHeadRevision() {
        return headRevision;
    }

    public void setHeadRevision(String headRevision) {
        this.headRevision = headRevision;
    }

    public String getRevisionComment() {
        return revisionComment;
    }

    public void setRevisionComment(String revisionComment) {
        this.revisionComment = revisionComment;
    }

    public UserDTO getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserDTO uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public ImageDTO getImageDTO() {
        return imageDTO;
    }

    public void setImageDTO(ImageDTO imageDTO) {
        this.imageDTO = imageDTO;
    }

    public FileDTO getFileDTO() {
        return fileDTO;
    }

    public void setFileDTO(FileDTO fileDTO) {
        this.fileDTO = fileDTO;
    }

    public String getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(String revisionNumber) {
        this.revisionNumber = revisionNumber;
    }
}
