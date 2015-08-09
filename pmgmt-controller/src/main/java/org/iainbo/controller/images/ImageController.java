package org.iainbo.controller.images;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{

    private UploadedFile newFile;
    private String newFileName;
    private String newImageTitle;
    private String newImageDescription;
    private String galleryToBeLoadedTo;

    public UploadedFile getNewFile() {
        return newFile;
    }

    public void setNewFile(UploadedFile newFile) {
        this.newFile = newFile;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getNewImageTitle() {
        return newImageTitle;
    }

    public void setNewImageTitle(String newImageTitle) {
        this.newImageTitle = newImageTitle;
    }

    public String getNewImageDescription() {
        return newImageDescription;
    }

    public void setNewImageDescription(String newImageDescription) {
        this.newImageDescription = newImageDescription;
    }

    public String getGalleryToBeLoadedTo() {
        return galleryToBeLoadedTo;
    }

    public void setGalleryToBeLoadedTo(String galleryToBeLoadedTo) {
        this.galleryToBeLoadedTo = galleryToBeLoadedTo;
    }

    public void handleFileUpload(FileUploadEvent event) {
        newFile = event.getFile();
        newFileName = newFile.getFileName();

    }

    public void uploadImage(){
        System.out.println("The title is: " + getNewImageTitle());
        System.out.println("The Description is: " + getNewImageDescription());
        System.out.println("The Gallery is: " + getGalleryToBeLoadedTo());
    }
}
