package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.factory.DAOFactory;
import org.iainbo.dto.ImageDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.File;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.ImageMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Stateless
@Named
public class ImageService {

    @Inject
    DAOFactory daoFactory;

    @Inject
    ImageMapper imageMapper;

    public byte[] getBytesForImage(Long imageId){
        Revision revision = daoFactory.revisionDAO().findHeadRevision(imageId);
        byte[] imageData = new byte[0];
        try{
            imageData = daoFactory.fileDAO().findFileForRevision(revision.getId()).getFile();
        }catch (NoResultException e){
            System.out.println("More than one image has been found for this ID: " + imageId + ":" + e);
        }
        return imageData;
    }

    public boolean persistImage(ImageDTO imageDTO){
        User user = daoFactory.userDAO().find(imageDTO.getRevisionDTO().getUploadedBy().getId());
        Gallery gallery = daoFactory.galleryDAO().find(imageDTO.getGalleryDTO().getId());
        File newFile = new File();
        newFile.setFilename(imageDTO.getRevisionDTO().getFileDTO().getFilename());
        newFile.setFile(imageDTO.getRevisionDTO().getFileDTO().getFileData());
        Revision newRevision = new Revision(user, imageDTO.getRevisionDTO().getDateUploaded(), imageDTO.getRevisionDTO().getHeadRevision(),newFile);
        newFile.setRevision(newRevision);
        Image newImage = new Image(imageDTO.getTitle(), gallery, newRevision);
        newRevision.setImage(newImage);
        daoFactory.imageDAO().create(newImage);
        daoFactory.revisionDAO().create(newRevision);
        daoFactory.fileDAO().create(newFile);
        return true;
    }

    public void deleteImage(Long imageId){
        daoFactory.imageDAO().delete(imageId);
    }

    public ImageDTO findImageById(Long imageId){
        Image image = daoFactory.imageDAO().findImageByID(imageId);
        ImageDTO imageDTO = imageMapper.imageToImageDTO(image);
        return imageDTO;
    }
}
