package org.iainbo.pmgmt.service.images;

import org.iainbo.dao.factory.DAOFactory;
import org.iainbo.dto.Image.FileDTO;
import org.iainbo.dto.Image.ImageDTO;
import org.iainbo.dto.Image.RevisionDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.File;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.FileMapper;
import org.iainbo.pmgmt.service.mapper.ImageMapper;
import org.iainbo.pmgmt.service.mapper.RevisionMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.ArrayList;
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

    @Inject
    FileMapper fileMapper;

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

    public FileDTO getFile(Long revisionId){
        File file = daoFactory.fileDAO().findFileForRevision(revisionId);
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);
        return fileDTO;
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


    public ImageDTO persistImage(ImageDTO imageDTO){
        Gallery gallery = daoFactory.galleryDAO().find(imageDTO.getGalleryDTO().getId());
        File newFile = new File();
        newFile.setFilename(imageDTO.getRevisionDTO().getFileDTO().getFilename());
        newFile.setFile(imageDTO.getRevisionDTO().getFileDTO().getFileData());
        Revision newRevision = newRevision(imageDTO, newFile);
        List<Revision> revisions = new ArrayList<>();
        revisions.add(newRevision);
        newFile.setRevision(newRevision);
        Image newImage = new Image(imageDTO.getTitle(), gallery, newRevision, revisions, imageDTO.getDescription());
        newRevision.setImage(newImage);
        daoFactory.imageDAO().create(newImage);
        daoFactory.revisionDAO().create(newRevision);
        daoFactory.fileDAO().create(newFile);

        Image savedImage = daoFactory.imageDAO().findImageByTitleAndGallery(newImage.getTitle(), newImage.getGallery().getId());
        ImageDTO newSavedImageDTO = imageMapper.imageToImageDTO(savedImage);
        return newSavedImageDTO;
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

    public ImageDTO updateImage(ImageDTO imageDTO){
        File newFile = newFile(imageDTO);
        Revision newRevision = newRevision(imageDTO, newFile);

        //Set the existing head revision to no longer be head.
        Revision oldRevision = daoFactory.revisionDAO().findHeadRevision(imageDTO.getId());
        oldRevision.setHeadRevision("N");
        oldRevision.setCheckedOut("N");
        daoFactory.revisionDAO().update(oldRevision);

        Image image = daoFactory.imageDAO().findImageByID(imageDTO.getId());
        image.setDescription(imageDTO.getDescription());
        image.setRevision(newRevision);
        newRevision.setImage(image);
        daoFactory.revisionDAO().create(newRevision);
        daoFactory.fileDAO().create(newFile);
        daoFactory.imageDAO().update(image);

        Image updatedImage = daoFactory.imageDAO().findImageByID(imageDTO.getId());
        ImageDTO updatedImageDTO = imageMapper.imageToImageDTO(updatedImage);
        return updatedImageDTO;
    }

    public File newFile(ImageDTO imageDTO){
        File newFile = new File();
        newFile.setFilename(imageDTO.getRevisionDTO().getFileDTO().getFilename());
        newFile.setFile(imageDTO.getRevisionDTO().getFileDTO().getFileData());
        return newFile;
    }

    public Revision newRevision(ImageDTO imageDTO, File file){
        User user = daoFactory.userDAO().find(imageDTO.getRevisionDTO().getUploadedBy().getId());
        Revision newRevision = new Revision(user, imageDTO.getRevisionDTO().getDateUploaded(),
                imageDTO.getRevisionDTO().getHeadRevision(), file,
                imageDTO.getRevisionDTO().getRevisionNumber(), imageDTO.getRevisionDTO().getRevisionComment());
        file.setRevision(newRevision);
        return newRevision;
    }

    public RevisionDTO getRevision(Long revisionId){
        Revision revision = daoFactory.revisionDAO().findById(revisionId);
        RevisionDTO revisionDTO = revisionMapper.revisionToRevisionDTO(revision);
        return revisionDTO;
    }
}
