package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.FileDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.RevisionDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.gallery.ImageView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{

    private UploadedFile newFile;
    private byte[] fileBytes;
    private String newFileName;
    private String newImageTitle;
    private String newImageDescription;
    private String galleryToBeLoadedTo;
    private String imageToBeDeleted;

    @Inject
    private ImageView imageView;

    @Inject
    GalleryService galleryService;

    @Inject
    LoginController loginController;

    @Inject
    ImageService imageService;

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

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getImageToBeDeleted() {
        return imageToBeDeleted;
    }

    public void setImageToBeDeleted(String imageToBeDeleted) {
        this.imageToBeDeleted = imageToBeDeleted;
    }

    public void handleFileUpload(FileUploadEvent event) {
        newFile = event.getFile();
        fileBytes = newFile.getContents();
        newFileName = newFile.getFileName();

    }

    public void uploadImage(){

        if(fileBytes == null || fileBytes.length == 0){
            System.out.println("The File is empty.");
        }else{
            ImageDTO imageDTO = new ImageDTO();
            RevisionDTO revisionDTO = new RevisionDTO();
            FileDTO fileDTO = new FileDTO();

            imageDTO.setTitle(newImageTitle);
            imageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryToBeLoadedTo));

            revisionDTO.setHeadRevision("Y");
            revisionDTO.setDateUploaded(new Date());
            revisionDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());

            fileDTO.setFilename(newFileName);
            fileDTO.setFileData(fileBytes);
            fileDTO.setRevisionDTO(revisionDTO);

            revisionDTO.setFileDTO(fileDTO);
            revisionDTO.setImageDTO(imageDTO);

            imageDTO.setRevisionDTO(revisionDTO);

            if(imageService.persistImage(imageDTO)){
                System.out.println("The image has been saved.");
            }
        }

    }

    public void deleteImage(){
        imageService.deleteImage(Long.valueOf(getImageToBeDeleted()));
    }

    public void retrieveSelectedImage(Long imageId){
        ImageDTO imageDTO = imageService.findImageById(imageId);
        imageView.setTitle(imageDTO.getTitle());

        //imageView.setDescription(imageDTO.getDescription());
        //System.out.println("Setting user");
        //imageView.setUploadedBy(imageDTO.getRevisionDTO().getUploadedBy().getFirstName() + " " + imageDTO.getRevisionDTO().getUploadedBy().getSurname());
        //System.out.println("The user is: " + imageView.getUploadedBy());
        //imageView.setDateUploaded(imageDTO.getRevisionDTO().getDateUploaded());
    }
}
