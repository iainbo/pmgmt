package org.iainbo.controller.gallery;

import org.iainbo.controller.LoginController;
import org.iainbo.pmgmt.service.gallery.GalleryService;
import org.iainbo.pmgmt.view.user.UserView;

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


    private String galleryName;

    @Inject
    GalleryService galleryService;

    @Inject
    LoginController loginController;

    @Inject
    GalleryDashBoardController galleryDashBoardController;

    @Inject
    UserView userView;


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
}
