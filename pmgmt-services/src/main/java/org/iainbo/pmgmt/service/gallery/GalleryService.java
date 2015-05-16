package org.iainbo.pmgmt.service.gallery;

import org.iainbo.dao.gallery.GalleryDAO;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.pmgmt.service.mapper.GalleryMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named
public class GalleryService {
    @Inject
    GalleryDAO galleryDAO;

    @Inject
    GalleryMapper galleryMapper;

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
        Gallery gallery = galleryDAO.findByGalleryName(galleryName);
        GalleryDTO galleryDTO = galleryMapper.galleryToGalleryDTO(gallery);
        return galleryDTO;
    }

}
