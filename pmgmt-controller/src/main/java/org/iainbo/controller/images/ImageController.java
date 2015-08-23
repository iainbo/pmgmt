package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.FileDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.RevisionDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.Image.ImageView;
import org.iainbo.pmgmt.view.Image.RevisionView;
import org.iainbo.pmgmt.view.gallery.GalleryView;
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
import java.util.*;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{

    private UploadedFile newFile;
    private String newFileName;
    private String newImageTitle;
    private String newImageDescription;
    private ImageView selectedImageView;
    private String newRevisionNumber;
    private ImageDTO imageDTO;
    private String selectedRevisionId;
    RevisionView selectedRevisionView;

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

    public ImageView getSelectedImageView() {
        return selectedImageView;
    }

    public String getSelectedRevisionId() {
        return selectedRevisionId;
    }

    public void setSelectedRevisionId(String selectedRevisionId) {
        this.selectedRevisionId = selectedRevisionId;
        getSelectedRevisionView(this.selectedRevisionId);
    }

    public RevisionView getSelectedRevisionView() {
        return selectedRevisionView;
    }

    public void setSelectedRevisionView(RevisionView selectedRevisionView) {
        this.selectedRevisionView = selectedRevisionView;
    }

    public void setSelectedImageView(String imageId) {
        imageDTO = imageService.findImageById(Long.valueOf(imageId));
        selectedImageView = new ImageView();
        selectedImageView.setId(imageDTO.getId());
        selectedImageView.setTitle(imageDTO.getTitle());
        selectedImageView.setDescription(imageDTO.getDescription());
        selectedImageView.setRevisionNo(imageDTO.getRevisionDTO().getRevisionNumber());
        selectedImageView.setRevisionId(imageDTO.getRevisionDTO().getId());
        String date = imageDTO.getRevisionDTO().getDateUploaded().toString();
        selectedImageView.setDateUploaded(date);
        String uploadedBy = imageDTO.getRevisionDTO().getUploadedBy().getFirstName() + " " + imageDTO.getRevisionDTO().getUploadedBy().getSurname();
        selectedImageView.setUploadedBy(uploadedBy);
        Map<Long, String> revIds = new HashMap<>();
        for(RevisionDTO r : imageDTO.getRevisions()){
            Long id = r.getId();
            String revNo = r.getRevisionNumber();
            revIds.put(id, revNo);
        }
        selectedImageView.setRevisions(revIds);
    }

    public String getNewRevisionNumber() {
        return newRevisionNumber;
    }

    public void setNewRevisionNumber(String newRevisionNumber) {
        this.newRevisionNumber = newRevisionNumber;
    }

    public void handleFileUpload(FileUploadEvent event) {
        newFile = event.getFile();
        newFileName = newFile.getFileName();

    }

    public void uploadImage(String galleryName){
        if(saveNewImage(newFile, galleryName)){
            FacesMessage message = new FacesMessage("Succesful", newFileName + " has been uploaded to " + galleryName + ".");
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
            ImageDTO newImageDTO = new ImageDTO();
            RevisionDTO revisionDTO = new RevisionDTO();
            FileDTO fileDTO = new FileDTO();

            newImageDTO.setTitle(newImageTitle);
            newImageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryName));
            newImageDTO.setDescription(newImageDescription);
            revisionDTO.setHeadRevision("Y");
            revisionDTO.setRevisionComment("Initial Revision");
            revisionDTO.setCheckedOut("N");
            revisionDTO.setRevisionNumber("01");
            revisionDTO.setDateUploaded(new Date());
            revisionDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());
            fileDTO.setFilename(filename);
            fileDTO.setFileData(bytes);
            fileDTO.setRevisionDTO(revisionDTO);
            revisionDTO.setFileDTO(fileDTO);
            revisionDTO.setImageDTO(newImageDTO);
            newImageDTO.setRevisionDTO(revisionDTO);

            ImageDTO savedImage = imageService.persistImage(newImageDTO);
            if(null != savedImage){
                imagePersisted = true;
                newImageDTO.setId(savedImage.getId());
                newImageDTO.getRevisionDTO().setId(savedImage.getRevisionDTO().getId());
            }
            ImageView imageView = createNewImageView(newImageDTO);
            galleryView.getImages().add(imageView);
        }
        return imagePersisted;
    }

    public ImageView createNewImageView(ImageDTO imageDTO){
        ImageView imageView = new ImageView();
        imageView.setId(imageDTO.getId());
        imageView.setTitle(imageDTO.getTitle());
        imageView.setDescription(imageDTO.getDescription());
        imageView.setGalleryView(galleryView);
        imageView.setRevisionId(imageDTO.getRevisionDTO().getId());
        imageView.setImageIsCheckedOut(false);
        imageView.setUploadedBy(imageDTO.getRevisionDTO().getUploadedBy().getFirstName() + " " + imageDTO.getRevisionDTO().getUploadedBy().getSurname());
        imageView.setFilename(newFileName);
        return imageView;
    }

    public void deleteImage(){
        if(imageService.deleteImage(selectedImageView.getId())){
            FacesMessage message = new FacesMessage("Succesful", selectedImageView.getTitle() + " has been deleted.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", selectedImageView.getTitle() + " has not been deleted.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
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
        List<RevisionDTO> revisionDTOs = imageDTO.getRevisions();
        revisionDTOs.add(revisionDTO);
        imageDTO.setRevisions(revisionDTOs);
    }

    public void setCheckedOutValue(boolean value){
        List<ImageView> imageViews = galleryView.getImages();
        for(ImageView i : imageViews){
            if(i.getId() == imageDTO.getId()){
                i.setImageIsCheckedOut(value);
            }
        }
    }

    public void getSelectedRevisionView(String revisionId){
        Long revId = Long.valueOf(revisionId);
        RevisionDTO revisionDTO = imageService.getRevision(revId);
        selectedRevisionView = new RevisionView();
        selectedRevisionView.setId(revisionDTO.getId());
        selectedRevisionView.setRevisionNumber(revisionDTO.getRevisionNumber());
        selectedRevisionView.setRevisionComment(revisionDTO.getRevisionComment());
        selectedRevisionView.setFilename(revisionDTO.getFileDTO().getFilename());

        InputStream fileStream = new ByteArrayInputStream(imageService.getBytesForImage(revId));
        StreamedContent file = new DefaultStreamedContent(fileStream, "image/jpg", selectedRevisionView.getFilename());
        selectedRevisionView.setFileData(file);


        selectedRevisionView.setUploadedBy(revisionDTO.getUploadedBy().getFirstName() + " " + revisionDTO.getUploadedBy().getSurname());
        selectedRevisionView.setUploadedDate(revisionDTO.getDateUploaded().toString());
    }
}
