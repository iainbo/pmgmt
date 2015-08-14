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
    private String selectedImageId;
    private ImageView selectedImageView;
    private String newRevisionNumber;

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

    public ImageView getSelectedImageView() {
        return selectedImageView;
    }

    public void setSelectedImageView(String imageId) {
        ImageDTO imageDTO = imageService.findImageById(Long.valueOf(imageId));
        selectedImageView = new ImageView();
        selectedImageView.setId(imageDTO.getId());
        selectedImageView.setTitle(imageDTO.getTitle());
        selectedImageView.setRevisionNo(imageDTO.getRevisionDTO().getRevisionNumber());
        String date = imageDTO.getRevisionDTO().getDateUploaded().toString();
        selectedImageView.setDateUploaded(date);
        String uploadedBy = imageDTO.getRevisionDTO().getUploadedBy().getFirstName() + " " + imageDTO.getRevisionDTO().getUploadedBy().getSurname();
        selectedImageView.setUploadedBy(uploadedBy);
    }

    public String getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(String selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public String getNewRevisionNumber() {
        return newRevisionNumber;
    }

    public void setNewRevisionNumber(String newRevisionNumber) {
        this.newRevisionNumber = newRevisionNumber;
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
        imageService.deleteImage(selectedImageView.getId());
    }
}
