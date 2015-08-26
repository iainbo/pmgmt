package org.iainbo.dto.Image;

public class FileDTO {

    private Long id;
    private RevisionDTO revisionDTO;
    private String filename;
    private byte[] fileData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RevisionDTO getRevisionDTO() {
        return revisionDTO;
    }

    public void setRevisionDTO(RevisionDTO revisionDTO) {
        this.revisionDTO = revisionDTO;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
