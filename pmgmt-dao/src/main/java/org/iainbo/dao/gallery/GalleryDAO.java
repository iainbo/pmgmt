package org.iainbo.dao.gallery;

import org.iainbo.dao.BaseDAO;
import org.iainbo.entities.gallery.Gallery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class GalleryDAO extends BaseDAO<Gallery> {

    public static final String FIND_BY_GALLERY_NAME = "select g from Gallery g where g.galleryName = :galleryName";
    public static final String FIND_GALLERY_METADATA_BY_GALLERY_NAME = "select g from Gallery g left outer join GalleryMetadata gm " +
            "where g.galleryName = :galleryName";
    public static final String FIND_BY_ID = "select g from Gallery g where g.id =:galleryId";


    public GalleryDAO(){
        super(Gallery.class);
    }

    public GalleryDAO(EntityManager entityManager){
        super(Gallery.class);
        this.entityManager = entityManager;
    }

    public List<Gallery> findByGalleryName(String galleryName){
        Query query = entityManager.createQuery(FIND_BY_GALLERY_NAME);
        query.setParameter("galleryName", galleryName);
        List<Gallery> resultList =  query.getResultList();
        return resultList;
    }

    public Gallery create(final Gallery entity){
        entityManager.persist(entity);
        return entity;
    }

    /*public Gallery findById(Long galleryId) throws NoResultException{
        Query query = entityManager.createQuery(FIND_BY_ID);
        query.setParameter("galleryId", galleryId);
        Gallery result = (Gallery) query.getSingleResult();
        return result;
    }*/

}
