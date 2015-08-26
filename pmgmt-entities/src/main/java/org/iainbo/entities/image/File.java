package org.iainbo.entities.image;

import org.iainbo.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "FILES",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"REVISION_ID"})})
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "ID")
    private Long id;

    @NotNull
    @JoinColumn(name = "REVISION_ID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Revision revision;

    @NotNull
    @Column(name = "FILENAME")
    private String filename;

    @NotNull
    @Column(name = "FILEDATA")
    @Lob
    private byte[] file;

    public File(){

    }

    public File(String filename, byte[] fileData){
        this.filename = filename;
        this.file = file;
    }

    public File(Revision revision, String filename, byte[] file){
        this.revision = revision;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
