package org.iainbo.controller.images;

import org.iainbo.controller.user.UserController;
import org.iainbo.dto.Image.FileDTO;
import org.iainbo.dto.Image.ImageDTO;
import org.iainbo.dto.Image.RevisionDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.Image.ImageView;
import org.iainbo.pmgmt.view.Image.RevisionView;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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

    private ImageView selectedImageView;
    private String selectedRevisionId;
    private RevisionView selectedRevisionView;

    @Inject
    GalleryService galleryService;

    @Inject
    UserController userController;

    @Inject
    ImageService imageService;

    @Inject
    GalleryView galleryView;

    @Inject
    ImageView imageView;

    @Inject
    RevisionView revisionView;

    public ImageView getSelectedImageView() {
        return selectedImageView;
    }

    public String getSelectedRevisionId() {
        return selectedRevisionId;
    }

    public void setSelectedRevisionId(String selectedRevisionId) {
        this.selectedRevisionId = selectedRevisionId;
        createSelectedRevisionView(Long.valueOf(this.selectedRevisionId));
    }

    public RevisionView getSelectedRevisionView() {
        return selectedRevisionView;
    }

    public void setSelectedRevisionView(RevisionView selectedRevisionView) {
        this.selectedRevisionView = selectedRevisionView;
    }

    public void setSelectedImageView(String imageId) {
        ImageDTO imageDTO = imageService.findImageById(Long.valueOf(imageId));
        selectedImageView = new ImageView();
        selectedImageView.setId(imageDTO.getId());
        selectedImageView.setTitle(imageDTO.getTitle());
        selectedImageView.setDescription(imageDTO.getDescription());
        selectedImageView.setRevisionView(createSelectedRevisionView(imageDTO.getRevisionDTO().getId()));
        Map<Long, String> revIds = new HashMap<>();
        for(RevisionDTO r : imageDTO.getRevisions()){
            Long id = r.getId();
            String revNo = r.getRevisionNumber();
            revIds.put(id, revNo);
        }
        selectedImageView.setRevisions(revIds);
    }

    public void handleFileUpload(FileUploadEvent event) {
        revisionView.setBytes(event.getFile().getContents());
        revisionView.setFilename(event.getFile().getFileName());

    }

    public void uploadImage(String galleryName){
        if(saveNewImage(galleryName)){
            FacesMessage message = new FacesMessage("Succesful", revisionView.getFilename() + " has been uploaded to " + galleryName + ".");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", revisionView.getFilename() + " has not been uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        resetValues();
    }

    public boolean saveNewImage(String galleryName){
        boolean imagePersisted = false;
        if(revisionView.getBytes() == null || revisionView.getBytes().length == 0){
            imagePersisted = false;
        }else{
            ImageDTO newImageDTO = new ImageDTO();
            RevisionDTO revisionDTO = new RevisionDTO();
            FileDTO fileDTO = new FileDTO();

            newImageDTO.setTitle(imageView.getTitle());
            newImageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryName));
            newImageDTO.setDescription(imageView.getDescription());
            revisionDTO.setHeadRevision("Y");
            revisionDTO.setRevisionComment("Initial Revision");
            revisionDTO.setCheckedOut("N");
            revisionDTO.setRevisionNumber("01");
            revisionDTO.setDateUploaded(new Date());
            revisionDTO.setUploadedBy(userController.getUserDTOForLoggedInUser());
            fileDTO.setFilename(revisionView.getFilename());
            fileDTO.setFileData(revisionView.getBytes());
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
            if(null != galleryView.getImages()){
                galleryView.getImages().add(imageView);
            }else{
                List<ImageView> newImageViewList = new ArrayList<>();
                newImageViewList.add(imageView);
                galleryView.setImages(newImageViewList);
                galleryView.setGalleryIsEmpty(false);
            }
        }
        return imagePersisted;
    }

    public ImageView createNewImageView(ImageDTO imageDTO){
        ImageView imageView = new ImageView();
        imageView.setId(imageDTO.getId());
        imageView.setTitle(imageDTO.getTitle());
        imageView.setDescription(imageDTO.getDescription());
        imageView.setGalleryView(galleryView);
        imageView.setRevisionView(createSelectedRevisionView(imageDTO.getRevisionDTO().getId()));
        imageView.setImageIsCheckedOut(false);
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
        galleryView.setImages(imageViewList);
        if(galleryView.getImages().size() == 0){
            galleryView.setGalleryIsEmpty(true);
        }
        selectedImageView = null;
    }

    public void checkOut(){
        imageService.checkOutRevision(userController.getCurrentUserName(), selectedImageView.getRevisionView().getId());
        setCheckedOutValue(true);
    }

    public StreamedContent getFileForCheckout(){
        InputStream fileStream = new ByteArrayInputStream(imageService.getFile(selectedImageView.getRevisionView().getId()).getFileData());
        String filename = imageService.getFile(selectedImageView.getRevisionView().getId()).getFilename();
        StreamedContent file = new DefaultStreamedContent(fileStream, "image/jpg", filename);
        return file;
    }

    public void checkIn(String imageId){
        if(checkInRevision(Long.valueOf(imageId))){
            FacesMessage message = new FacesMessage("Succesful", selectedImageView.getTitle() + " has been checked in.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", selectedImageView.getTitle() + "check in has failed.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        resetValues();
    }

    public boolean checkInRevision(Long imageId){
        boolean revisionSaved = false;
        ImageDTO imageDTO = imageService.findImageById(imageId);
        if(revisionView.getBytes() == null || revisionView.getBytes().length == 0 || imageDTO == null){
            revisionSaved = false;
        }else{
            imageDTO.setDescription(selectedImageView.getDescription());
            ImageDTO newImageDTO = createRevisionDTOAndFileDTO(imageDTO);
            ImageDTO updatedImage = imageService.updateImage(newImageDTO);
            if(null != updatedImage){
                revisionSaved = true;
                ImageView updatedImageView = createNewImageView(updatedImage);

                List<ImageView> imageViews = galleryView.getImages();
                Iterator<ImageView> imageViewIterator = imageViews.iterator();
                while(imageViewIterator.hasNext()){
                    ImageView i = imageViewIterator.next();
                    if(i.getId() == updatedImageView.getId()){
                        imageViewIterator.remove();
                    }
                }
                imageViews.add(updatedImageView);
                setCheckedOutValue(false);
            }
        }
        return revisionSaved;
    }

    public ImageDTO createRevisionDTOAndFileDTO(ImageDTO imageDTO){
        RevisionDTO revisionDTO = new RevisionDTO();
        FileDTO fileDTO = new FileDTO();
        revisionDTO.setHeadRevision("Y");
        revisionDTO.setRevisionNumber(revisionView.getRevisionNumber());
        revisionDTO.setRevisionComment(revisionView.getRevisionComment());
        revisionDTO.setDateUploaded(new Date());
        revisionDTO.setUploadedBy(userController.getUserDTOForLoggedInUser());
        fileDTO.setFilename(revisionView.getFilename());
        fileDTO.setFileData(revisionView.getBytes());
        fileDTO.setRevisionDTO(revisionDTO);
        revisionDTO.setFileDTO(fileDTO);
        revisionDTO.setImageDTO(imageDTO);
        imageDTO.setRevisionDTO(revisionDTO);
        List<RevisionDTO> revisionDTOs = imageDTO.getRevisions();
        revisionDTOs.add(revisionDTO);
        imageDTO.setRevisions(revisionDTOs);
        return imageDTO;
    }

    public void setCheckedOutValue(boolean value){
        List<ImageView> imageViews = galleryView.getImages();
        for(ImageView i : imageViews){
            if(i.getId() == selectedImageView.getId()){
                i.setImageIsCheckedOut(value);
            }
        }
    }

    public RevisionView createSelectedRevisionView(Long revisionId){
        Long revId = Long.valueOf(revisionId);
        RevisionDTO revisionDTO = imageService.getRevision(revId);
        selectedRevisionView = new RevisionView();
        selectedRevisionView.setId(revisionDTO.getId());
        selectedRevisionView.setRevisionNumber(revisionDTO.getRevisionNumber());
        selectedRevisionView.setRevisionComment(revisionDTO.getRevisionComment());
        selectedRevisionView.setFilename(revisionDTO.getFileDTO().getFilename());

        InputStream fileStream = new ByteArrayInputStream(imageService.getBytesForImage(revId));
        StreamedContent file = new DefaultStreamedContent(fileStream, "image/jpg", selectedRevisionView.getFilename());
        selectedRevisionView.setStreamedContent(file);


        selectedRevisionView.setUploadedBy(revisionDTO.getUploadedBy().getFirstName() + " " + revisionDTO.getUploadedBy().getSurname());
        selectedRevisionView.setUploadedDate(revisionDTO.getDateUploaded().toString());
        return selectedRevisionView;
    }

    public void resetValues(){
        imageView.setTitle(null);
        imageView.setDescription(null);
        revisionView.setBytes(null);
        revisionView.setFilename(null);
        revisionView.setRevisionNumber(null);
        revisionView.setRevisionComment(null);
    }
}
