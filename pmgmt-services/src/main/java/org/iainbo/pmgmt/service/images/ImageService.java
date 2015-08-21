package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.factory.DAOFactory;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.RevisionDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.File;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.ImageMapper;
import org.iainbo.pmgmt.service.mapper.RevisionMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
@Named
public class ImageService {

    @Inject
    DAOFactory daoFactory;

    @Inject
    ImageMapper imageMapper;

    @Inject
    RevisionMapper revisionMapper;

    public byte[] getBytesForImage(Long revisionId){
        Revision revision = daoFactory.revisionDAO().findById(revisionId);
        byte[] imageData;
        if(revision.getHeadRevision().equalsIgnoreCase("Y")){
            Revision headRevision = daoFactory.revisionDAO().findHeadRevision(revision.getImage().getId());
            imageData = getImageForRevision(headRevision);
        }
        else{
            imageData = getImageForRevision(revision);
        }
        return imageData;
    }

    public File getFile(Long revisionId){
        File file = daoFactory.fileDAO().findFileForRevision(revisionId);
        return file;
    }

    public byte[] getImageForRevision(Revision revision){
        byte[] imageData = new byte[0];
        try{
            imageData = daoFactory.fileDAO().findFileForRevision(revision.getId()).getFile();
        }catch (NoResultException e){
            System.out.println("More than one image has been found for this ID: " + revision.getImage().getId() + ":" + e);
        }
        return imageData;
    }

    public List<Revision> getAllRevisionsForImage(Long imageId){
        List<Revision> revisionsForImage;
        revisionsForImage = daoFactory.revisionDAO().allRevisionsForImage(imageId);
        return revisionsForImage;
    }

    public boolean persistImage(ImageDTO imageDTO){
        User user = daoFactory.userDAO().find(imageDTO.getRevisionDTO().getUploadedBy().getId());
        Gallery gallery = daoFactory.galleryDAO().find(imageDTO.getGalleryDTO().getId());
        File newFile = new File();
        newFile.setFilename(imageDTO.getRevisionDTO().getFileDTO().getFilename());
        newFile.setFile(imageDTO.getRevisionDTO().getFileDTO().getFileData());
        Revision newRevision = new Revision(user, imageDTO.getRevisionDTO().getDateUploaded(), imageDTO.getRevisionDTO().getHeadRevision(),newFile, imageDTO.getRevisionDTO().getRevisionNumber());
        newFile.setRevision(newRevision);
        Image newImage = new Image(imageDTO.getTitle(), gallery, newRevision);
        newRevision.setImage(newImage);
        daoFactory.imageDAO().create(newImage);
        daoFactory.revisionDAO().create(newRevision);
        daoFactory.fileDAO().create(newFile);
        return true;
    }

    public boolean deleteImage(Long imageId){
        daoFactory.imageDAO().delete(imageId);
        return true;
    }

    public ImageDTO findImageById(Long imageId){
        Image image = daoFactory.imageDAO().findImageByID(imageId);
        ImageDTO imageDTO = imageMapper.imageToImageDTO(image);
        return imageDTO;
    }

    public void checkOutRevision(String userName, Long revisionId){
        Revision revision = daoFactory.revisionDAO().findById(revisionId);
        User user = daoFactory.userDAO().findByUsername(userName);
        revision.setCheckedOut("Y");
        revision.setCheckedOutBy(user);
        daoFactory.revisionDAO().update(revision);
    }

    public boolean updateImage(ImageDTO imageDTO){
        File newFile = newFile(imageDTO);
        Revision newRevision = newRevision(imageDTO, newFile);

        //Set the existing head revision to no longer be head.
        Revision oldRevision = daoFactory.revisionDAO().findHeadRevision(imageDTO.getId());
        oldRevision.setHeadRevision("N");
        oldRevision.setCheckedOut("N");
        daoFactory.revisionDAO().update(oldRevision);

        Image image = daoFactory.imageDAO().findImageByID(imageDTO.getId());
        image.setRevision(newRevision);
        newRevision.setImage(image);
        daoFactory.revisionDAO().create(newRevision);
        daoFactory.fileDAO().create(newFile);
        daoFactory.imageDAO().update(image);
        return true;
    }

    public File newFile(ImageDTO imageDTO){
        File newFile = new File();
        newFile.setFilename(imageDTO.getRevisionDTO().getFileDTO().getFilename());
        newFile.setFile(imageDTO.getRevisionDTO().getFileDTO().getFileData());
        return newFile;
    }

    public Revision newRevision(ImageDTO imageDTO, File file){
        User user = daoFactory.userDAO().find(imageDTO.getRevisionDTO().getUploadedBy().getId());
        Revision newRevision = new Revision(user, imageDTO.getRevisionDTO().getDateUploaded(), imageDTO.getRevisionDTO().getHeadRevision(), file, imageDTO.getRevisionDTO().getRevisionNumber());
        file.setRevision(newRevision);
        return newRevision;
    }

    public RevisionDTO getRevision(Long revisionId){
        Revision revision = daoFactory.revisionDAO().findById(revisionId);
        RevisionDTO revisionDTO = revisionMapper.revisionToRevisionDTO(revision);
        return revisionDTO;
    }
}
