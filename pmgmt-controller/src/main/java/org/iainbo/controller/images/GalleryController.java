package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.iainbo.pmgmt.view.user.UserView;
import org.primefaces.event.FileUploadEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class GalleryController implements Serializable{


    private String galleryName;
    private List<String> availableGalleriesNames;

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

    @PostConstruct
    public void init(){
        getGalleryNames();
    }

    public String getNewGalleryName() {
        return galleryName;
    }

    public void setNewGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public List<String> getAvailableGalleriesNames() {
        return availableGalleriesNames;
    }

    public void setAvailableGalleriesNames(List<String> availableGalleriesNames) {
        this.availableGalleriesNames = availableGalleriesNames;
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

        if(imageController.saveNewImage(event.getFile(), galleryView.getGalleryName())){
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage("Error", event.getFile().getFileName() + " has not been uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void getGalleryNames(){
        List<GalleryDTO> allAvailableGalleries = galleryService.getAllAvailableGalleries();
        availableGalleriesNames = new ArrayList<>();
        for(GalleryDTO g : allAvailableGalleries){
            availableGalleriesNames.add(g.getGalleryName());
        }
    }

    public void updateGallery(){
        galleryService.updateExistingGallery(galleryView.getId(), galleryView.getGalleryName());

        System.out.println("The new description is: " + galleryView.getGalleryDescription());
    }

}
