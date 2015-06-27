package org.iainbo.controller.images;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class ImageController implements Serializable{
//Class not used at present!!
    /*@Inject
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
    }*/
}
