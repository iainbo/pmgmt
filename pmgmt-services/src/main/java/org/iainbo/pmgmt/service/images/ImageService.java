package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.factory.DAOFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Stateless
@Named
public class ImageService {

    @Inject
    DAOFactory daoFactory;

    public byte[] getBytesForImage(Long imageId){
        byte[] imageData = new byte[0];
        try{
            imageData = daoFactory.imageDAO().findImageByID(imageId).getFile();
        }catch (NoResultException e){
            System.out.println("More than one image has been found for this ID: " + imageId + ":" + e);
        }
        return imageData;
    }
}
