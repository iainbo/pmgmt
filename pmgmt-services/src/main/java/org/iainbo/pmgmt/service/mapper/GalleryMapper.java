package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.*;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.File;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public abstract class GalleryMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "galleryName", target = "galleryName"),
            @Mapping(source = "dateCreated", target = "dateCreated"),
            @Mapping(source = "owner", target = "owner"),
            @Mapping(source = "images", target = "imageDTOList"),
            @Mapping(source = "thumbnail", target = "thumbnail")
    })
    public abstract GalleryDTO galleryToGalleryDTO(Gallery gallery);
    public abstract UserDTO userToUserDTO(User user);

    public ImageDTO imageToImageDTO(Image image){
        if ( image == null ) {
            return null;
        }
        RevisionMapper revisionMapper = new RevisionMapper() {
            @Override
            public RevisionDTO revisionToRevisionDTO(Revision revision) {
                RevisionDTO revisionDTO = new RevisionDTO();

                revisionDTO.setCheckedOut( revision.getCheckedOut() );
                revisionDTO.setCheckedOutBy(userToUserDTO(revision.getCheckedOutBy()));
                revisionDTO.setId( revision.getId() );
                revisionDTO.setRevisionNumber( revision.getRevisionNumber() );
                revisionDTO.setUploadedBy( userToUserDTO( revision.getUploadedBy() ) );
                revisionDTO.setHeadRevision( revision.getHeadRevision() );
                revisionDTO.setDateUploaded( revision.getDateUploaded() );
                revisionDTO.setFileDTO(fileToFileDTO(revision.getFile()));


                return revisionDTO;
            }

            @Override
            public UserDTO userToUserDTO(User user) {
                return null;
            }

            @Override
            public FileDTO fileToFileDTO(File file) {
                return null;
            }
        };
        ImageDTO imageDTO = new ImageDTO();
        RevisionDTO revisionDTO = revisionMapper.revisionToRevisionDTO(image.getRevision());
        imageDTO.setRevisionDTO(revisionDTO);
        imageDTO.setDescription( image.getDescription() );
        imageDTO.setId( image.getId() );
        imageDTO.setTitle( image.getTitle() );


        return imageDTO;
    }
}
