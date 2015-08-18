package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.FileDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.RevisionDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.iainbo.pmgmt.view.gallery.ImageView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{

    private UploadedFile newFile;
    private byte[] fileBytes;
    private String newFileName;
    private String newImageTitle;
    private String newImageDescription;
    private String galleryToBeLoadedTo;
    private ImageView selectedImageView;
    private String newRevisionNumber;
    private ImageDTO imageDTO;

    @Inject
    GalleryService galleryService;

    @Inject
    LoginController loginController;

    @Inject
    ImageService imageService;

    @Inject
    GalleryView galleryView;

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
        imageDTO = imageService.findImageById(Long.valueOf(imageId));
        selectedImageView = new ImageView();
        selectedImageView.setId(imageDTO.getId());
        selectedImageView.setTitle(imageDTO.getTitle());
        selectedImageView.setRevisionNo(imageDTO.getRevisionDTO().getRevisionNumber());
        selectedImageView.setRevisionId(imageDTO.getRevisionDTO().getId());
        String date = imageDTO.getRevisionDTO().getDateUploaded().toString();
        selectedImageView.setDateUploaded(date);
        String uploadedBy = imageDTO.getRevisionDTO().getUploadedBy().getFirstName() + " " + imageDTO.getRevisionDTO().getUploadedBy().getSurname();
        selectedImageView.setUploadedBy(uploadedBy);
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
        if(saveNewImage(newFile, galleryToBeLoadedTo)){
            FacesMessage message = new FacesMessage("Succesful", newFileName + " has been uploaded to " + galleryToBeLoadedTo + ".");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", newFileName + " has not been uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }


    }

    public boolean saveNewImage(UploadedFile file, String galleryName){
        byte[] bytes = file.getContents();
        String filename = file.getFileName();
        boolean imagePersisted = false;
        if(bytes == null || bytes.length == 0){
            imagePersisted = false;
        }else{
            ImageDTO imageDTO = new ImageDTO();
            RevisionDTO revisionDTO = new RevisionDTO();
            FileDTO fileDTO = new FileDTO();

            imageDTO.setTitle(newImageTitle);
            imageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryName));
            revisionDTO.setHeadRevision("Y");
            revisionDTO.setRevisionNumber("01");
            revisionDTO.setDateUploaded(new Date());
            revisionDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());
            fileDTO.setFilename(filename);
            fileDTO.setFileData(bytes);
            fileDTO.setRevisionDTO(revisionDTO);
            revisionDTO.setFileDTO(fileDTO);
            revisionDTO.setImageDTO(imageDTO);
            imageDTO.setRevisionDTO(revisionDTO);

            if(imageService.persistImage(imageDTO)){
                imagePersisted = true;
            }
        }
        return imagePersisted;
    }

    public void deleteImage(){
        imageService.deleteImage(selectedImageView.getId());
        List<ImageView> imageViewList = galleryView.getImages();
        Iterator<ImageView> imageViewIterator = imageViewList.iterator();
        while(imageViewIterator.hasNext()){
            ImageView i = imageViewIterator.next();
            if(i.getId() == selectedImageView.getId()){
                imageViewIterator.remove();
            }
        }
        selectedImageView = null;
    }

    public void checkOut(){
        imageService.checkOutRevision(loginController.getCurrentUserName(), selectedImageView.getRevisionId());
        setCheckedOutValue(true);
    }

    public StreamedContent getFileForCheckout(){
        InputStream fileStream = new ByteArrayInputStream(imageService.getFile(selectedImageView.getRevisionId()).getFile());
        String filename = imageService.getFile(selectedImageView.getRevisionId()).getFilename();
        StreamedContent file = new DefaultStreamedContent(fileStream, "image/jpg", filename);
        return file;
    }

    public void checkIn(String imageId){
        if(checkInRevision(newFile, Long.valueOf(imageId))){
            FacesMessage message = new FacesMessage("Succesful", selectedImageView.getTitle() + " has been checked in.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", selectedImageView.getTitle() + "check in has failed.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean checkInRevision(UploadedFile file, Long imageId){
        byte[] bytes = file.getContents();
        boolean revisionSaved = false;
        ImageDTO imageDTO = imageService.findImageById(imageId);
        if(bytes == null || bytes.length == 0 || imageDTO == null){
            revisionSaved = false;
        }else{
            createRevisionDTOAndFileDTO(imageDTO, bytes);
            if(imageService.updateImage(imageDTO)){
                revisionSaved = true;
            }
        }
        setCheckedOutValue(false);
        return revisionSaved;
    }

    public void createRevisionDTOAndFileDTO(ImageDTO imageDTO, byte[] bytes){
        RevisionDTO revisionDTO = new RevisionDTO();
        FileDTO fileDTO = new FileDTO();
        revisionDTO.setHeadRevision("Y");
        revisionDTO.setRevisionNumber(getNewRevisionNumber());
        revisionDTO.setDateUploaded(new Date());
        revisionDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());
        fileDTO.setFilename(getNewFileName());
        fileDTO.setFileData(bytes);
        fileDTO.setRevisionDTO(revisionDTO);
        revisionDTO.setFileDTO(fileDTO);
        revisionDTO.setImageDTO(imageDTO);
        imageDTO.setRevisionDTO(revisionDTO);
    }

    public void setCheckedOutValue(boolean value){
        List<ImageView> imageViews = galleryView.getImages();
        for(ImageView i : imageViews){
            if(i.getId() == imageDTO.getId()){
                i.setImageIsCheckedOut(value);
            }
        }
    }
}
