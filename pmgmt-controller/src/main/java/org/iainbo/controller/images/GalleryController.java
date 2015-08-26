package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.iainbo.pmgmt.view.user.UserView;
import org.primefaces.event.FileUploadEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class GalleryController implements Serializable{


    private String newGalleryName;
    private String newDescription;
    private byte[] thumbnail;

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

    @Inject
    ImageController imageController;


    public String getNewGalleryName() {
        return newGalleryName;
    }

    public void setNewGalleryName(String galleryName) {
        this.newGalleryName = galleryName;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public void addGallery(){
        if(galleryService.galleryExists(getNewGalleryName())){
            addMessage(FacesMessage.SEVERITY_ERROR, "Gallery already exists!");
        }
        else{
            String userName = userView.getUserName();
            if(galleryService.createGallery(getNewGalleryName(), userName, getNewDescription())){
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


    public void updateGallery(){
        if(galleryService.updateExistingGallery(galleryView.getId(), galleryView.getGalleryName(), thumbnail, galleryView.getDescription())){
        FacesMessage message = new FacesMessage("Succesful", galleryView.getGalleryName() + " has been updated.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }else{
        FacesMessage message = new FacesMessage("Error", galleryView.getGalleryName() + " has not been updated.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    }

    public String deleteGallery(String galleryId){
        galleryService.deleteGallery(Long.valueOf(galleryId));
        return "adminHome";
    }

    public void uploadThumbnail(FileUploadEvent event) {
        thumbnail = event.getFile().getContents();

    }
}
