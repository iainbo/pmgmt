package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.gallery.GalleryDAO;
import org.iainbo.dao.image.ImageDAO;
import org.iainbo.dao.user.UserDAO;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.GalleryMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Named
public class GalleryService {
    @Inject
    GalleryDAO galleryDAO;

    @Inject
    ImageDAO imageDAO;

    @Inject
    GalleryMapper galleryMapper;

    @Inject
    UserDAO userDAO;

    public List<GalleryDTO> getAllAvailableGalleries(){
        List<Gallery> availableGalleries = galleryDAO.findAll();
        List<GalleryDTO> availableGalleriesDTO = new ArrayList<>();
        for(Gallery g : availableGalleries){
            GalleryDTO galleryDTO = galleryMapper.galleryToGalleryDTO(g);
            availableGalleriesDTO.add(galleryDTO);
        }
        return availableGalleriesDTO;
    }

    public GalleryDTO galleryDTOByName(String galleryName){
        GalleryDTO galleryDTO = new GalleryDTO();
        List<Gallery> results = galleryDAO.findByGalleryName(galleryName);
        //GalleryDAO returns a results list which needs to be iterated through.
        // There is a unique constraint on the database so the list will only contain 1 result.
        for(Gallery gallery :results){
            List<Image> images = getImagesForGallery(gallery);
            gallery.setImages(images);
            galleryDTO = galleryMapper.galleryToGalleryDTO(gallery);
        }
        return galleryDTO;
    }

    public boolean galleryExists(String galleryName) {
        List<Gallery> results = galleryDAO.findByGalleryName(galleryName);
        if(results.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean createGallery(String galleryName, String userName){
        User user = userDAO.findByUsername(userName);
        Gallery newGallery = new Gallery(galleryName, new Date(), user);
        galleryDAO.create(newGallery);
        return true;
    }

    public List<Image> getImagesForGallery(Gallery gallery){
        List<Image> imagesForGallery;
        imagesForGallery = imageDAO.findAllImagesForGallery(gallery);
        return imagesForGallery;
    }



}
