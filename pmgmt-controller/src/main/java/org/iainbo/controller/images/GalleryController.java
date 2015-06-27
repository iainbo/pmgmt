package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.ImageDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.iainbo.pmgmt.view.user.UserView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@ViewScoped
public class GalleryController implements Serializable{


    private String galleryName;

    @Inject
    GalleryService galleryService;

    @Inject
    LoginController loginController;

    @Inject
    GalleryDashBoardController galleryDashBoardController;

    @Inject
    ImageService imageService;

    @Inject
    UserView userView;

    @Inject
    GalleryView galleryView;


    public String getNewGalleryName() {
        return galleryName;
    }

    public void setNewGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public void addGallery(){
        if(galleryService.galleryExists(getNewGalleryName())){
            addMessage(FacesMessage.SEVERITY_ERROR, "Gallery already exists!");
        }
        else{
            String userName = userView.getUserName();
            if(galleryService.createGallery(getNewGalleryName(), userName)){
                addMessage(FacesMessage.SEVERITY_INFO, "Gallery Created!");
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addMessage(FacesMessage.Severity severity, String detail) {
        FacesMessage message = new FacesMessage(severity, detail, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleFileUpload(FileUploadEvent event) {

        if(saveNewImage(event.getFile())){
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", event.getFile().getFileName() + " has not been uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean saveNewImage(UploadedFile file){
        byte[] bytes = file.getContents();
        String filename = file.getFileName();
        boolean imagePersisted = false;
        if(bytes == null || bytes.length == 0){
            imagePersisted = false;
        }else{
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setTitle("Temporary Title");
            imageDTO.setDateUploaded(new Date());
            imageDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());
            imageDTO.setFileData(bytes);
            imageDTO.setFilename(filename);
            imageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryView.getGalleryName()));
            if(imageService.persistImage(imageDTO)){
                imagePersisted = true;
            }
        }
        return imagePersisted;
    }
}
