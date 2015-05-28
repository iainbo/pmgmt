package org.iainbo.dao.factory;

import org.iainbo.dao.gallery.GalleryDAO;
import org.iainbo.dao.image.ImageDAO;
import org.iainbo.dao.user.UserDAO;

import javax.ejb.Lock;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@ApplicationScoped
@Lock
public class DAOFactory implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public DAOFactory(){

    }

    public DAOFactory(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public GalleryDAO galleryDAO(){
        return new GalleryDAO(entityManager);
    }

    public UserDAO userDAO(){
        return new UserDAO(entityManager);
    }

    public ImageDAO imageDAO(){
        return new ImageDAO(entityManager);
    }
}
