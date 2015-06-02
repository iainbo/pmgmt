package org.iainbo.controller.images;

import org.iainbo.controller.LoginController;
import org.iainbo.dto.ImageDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{

    @Inject
    ImageService imageService;

    @Inject
    LoginController loginController;

    @Inject
    GalleryService galleryService;

    @Inject
    GalleryController galleryController;

    public boolean saveNewImage(UploadedFile file){
        boolean imagePersisted = false;
        System.out.println("In Image Controller!");
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setTitle("Temporary Title");
        imageDTO.setDateUploaded(new Date());
        imageDTO.setUploadedBy(loginController.getUserDTOForLoggedInUser());
        imageDTO.setFileData(file.getContents());
        imageDTO.setFilename(file.getFileName());
        imageDTO.setGalleryDTO(galleryService.galleryDTOByName(galleryController.getNewGalleryName()));
        if(imageService.persistImage(imageDTO)){
            imagePersisted = true;
        }
        return imagePersisted;
    }
}
