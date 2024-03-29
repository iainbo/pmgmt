package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.factory.DAOFactory;
import org.iainbo.dto.Gallery.GalleryDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.GalleryMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Named
public class GalleryService {

    @Inject
    DAOFactory daoFactory;

    @Inject
    GalleryMapper galleryMapper;

    public List<GalleryDTO> getAllAvailableGalleries(){
        List<Gallery> availableGalleries = daoFactory.galleryDAO().findAll();
        List<GalleryDTO> availableGalleriesDTO = new ArrayList<>();
        for(Gallery g : availableGalleries){
            GalleryDTO galleryDTO = galleryMapper.galleryToGalleryDTO(g);
            availableGalleriesDTO.add(galleryDTO);
        }
        return availableGalleriesDTO;
    }

    public GalleryDTO galleryDTOByName(String galleryName){
        GalleryDTO galleryDTO = new GalleryDTO();
        List<Gallery> results = daoFactory.galleryDAO().findByGalleryName(galleryName);
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
        List<Gallery> results = daoFactory.galleryDAO().findByGalleryName(galleryName);
        if(results.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean createGallery(String galleryName, String userName, String description){
        User user = daoFactory.userDAO().findByUsername(userName);
        Gallery newGallery = new Gallery(galleryName, new Date(), user, description);
        daoFactory.galleryDAO().create(newGallery);
        return true;
    }

    public List<Image> getImagesForGallery(Gallery gallery){
        List<Image> imagesForGallery;
        imagesForGallery = daoFactory.imageDAO().findAllImagesForGallery(gallery);
        for(Image image : imagesForGallery){
            Long imageId = image.getId();
            Revision headRevision = daoFactory.revisionDAO().findHeadRevision(imageId);
            image.setRevision(headRevision);
            List<Revision> revisions = daoFactory.revisionDAO().allRevisionsForImage(imageId);
            image.setRevisions(revisions);
        }
        return imagesForGallery;
    }

    public byte[] getThumbnailForGallery(Long galleryId){
        Gallery gallery;
        byte[] thumb = new byte[0];
        try{
            gallery = daoFactory.galleryDAO().find(galleryId);
            thumb = gallery.getThumbnail();
        }catch(NoResultException e){
            System.out.println(e + "No results for: " + galleryId);
        }
        return thumb;
    }

    public boolean updateExistingGallery(Long galleryId, String newGalleryName, byte[] file, String newDescription){
        Gallery gallery = daoFactory.galleryDAO().find(galleryId);
        if(!gallery.getGalleryName().equals(newGalleryName)){
            gallery.setGalleryName(newGalleryName);
        }
        if(!gallery.getDescription().equals(newDescription)){
            gallery.setDescription(newDescription);
        }
        if(file != null){
            gallery.setThumbnail(file);
        }
        daoFactory.galleryDAO().update(gallery);
        return true;
    }

    public void deleteGallery(Long galleryId){
        daoFactory.galleryDAO().delete(galleryId);
    }

}
