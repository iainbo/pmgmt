package org.iainbo.pmgmt.service.gallery;

import org.iainbo.dao.gallery.GalleryDAO;
import org.iainbo.dao.user.UserDAO;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.entities.gallery.Gallery;
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
        for(Gallery gallery :results){
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



}
