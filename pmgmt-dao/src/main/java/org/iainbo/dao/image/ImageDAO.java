package org.iainbo.dao.image;

import org.iainbo.dao.BaseDAO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.Image;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ImageDAO extends BaseDAO {

    private static final String FIND_BY_IMAGE_TITLE = "select i from Image i where i.title =:imageTitle";
    private static final String FIND_BY_IMAGES_FOR_GALLERY = "select i from Image i where i.gallery =:gallery";
    private static final String FIND_IMAGE_BY_ID = "select i from Image i where i.id =:imageId";

    public ImageDAO(){super(Image.class);}

    public ImageDAO(EntityManager entityManager){
        super(Image.class);
        this.entityManager = entityManager;
    }

    public Image findImageByTitle(String imageTitle){
        Query query = entityManager.createQuery(FIND_BY_IMAGE_TITLE);
        query.setParameter("imageTitle", imageTitle);
        Image result = (Image) query.getSingleResult();
        return result;
    }

    public List<Image> findAllImagesForGallery(Gallery galleryId){
        Query query = entityManager.createQuery(FIND_BY_IMAGES_FOR_GALLERY);
        query.setParameter("gallery", galleryId);
        List<Image> results = query.getResultList();
        return results;
    }

    public Image findImageByID(Long imageId) throws NoResultException{
        Query query = entityManager.createQuery(FIND_IMAGE_BY_ID);
        query.setParameter("imageId", imageId);
        Image result = (Image) query.getSingleResult();
        return result;
    }

    public Image create(final Image entity){
        entityManager.persist(entity);
        return entity;
    }
}
