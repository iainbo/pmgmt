package org.iainbo.pmgmt.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.dto.GalleryMetadataDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.gallery.GalleryMetadata;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2015-06-27T15:37:08+0100"
)
@ApplicationScoped
public class GalleryMapperImpl implements GalleryMapper {

    @Override
    public GalleryDTO galleryToGalleryDTO(Gallery gallery)  {
        if ( gallery == null ) {
            return null;
        }

        GalleryDTO galleryDTO = new GalleryDTO();

        galleryDTO.setOwner( userToUserDTO( gallery.getOwner() ) );
        galleryDTO.setThumbnail( gallery.getThumbnail() );
        galleryDTO.setDateCreated( gallery.getDateCreated() );
        galleryDTO.setGalleryMetadataDTOList( galleryMetadataListToGalleryMetadataDTOList( gallery.getMetadataList() ) );
        galleryDTO.setId( gallery.getId() );
        galleryDTO.setGalleryName( gallery.getGalleryName() );
        galleryDTO.setImageDTOList( imageListToImageDTOList( gallery.getImages() ) );


        return galleryDTO;
    }


    @Override
    public UserDTO userToUserDTO(User user)  {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName( user.getFirstName() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setDateCreated( user.getDateCreated() );
        userDTO.setSurname( user.getSurname() );
        userDTO.setId( user.getId() );
        userDTO.setUserName( user.getUserName() );
        userDTO.setEmail( user.getEmail() );


        return userDTO;
    }


    @Override
    public GalleryMetadataDTO galleryMetadataToGalleryMetadataDTO(GalleryMetadata galleryMetadata)  {
        if ( galleryMetadata == null ) {
            return null;
        }

        GalleryMetadataDTO galleryMetadataDTO = new GalleryMetadataDTO();

        galleryMetadataDTO.setName( galleryMetadata.getName() );
        galleryMetadataDTO.setId( galleryMetadata.getId() );
        galleryMetadataDTO.setValue( galleryMetadata.getValue() );


        return galleryMetadataDTO;
    }


    @Override
    public ImageDTO imageToImageDTO(Image image)  {
        if ( image == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setFilename( image.getFilename() );
        imageDTO.setDateUploaded( image.getDateUploaded() );
        imageDTO.setId( image.getId() );
        imageDTO.setTitle( image.getTitle() );
        imageDTO.setUploadedBy( userToUserDTO( image.getUploadedBy() ) );


        return imageDTO;
    }



    protected List<GalleryMetadataDTO> galleryMetadataListToGalleryMetadataDTOList(List<GalleryMetadata> list)  {
        if ( list == null ) {
            return null;
        }

        List<GalleryMetadataDTO> list_ = new ArrayList<GalleryMetadataDTO>();

        for ( GalleryMetadata galleryMetadata : list ) {
            list_.add( galleryMetadataToGalleryMetadataDTO( galleryMetadata ) );
        }

        return list_;
    }



    protected List<ImageDTO> imageListToImageDTOList(List<Image> list)  {
        if ( list == null ) {
            return null;
        }

        List<ImageDTO> list_ = new ArrayList<ImageDTO>();

        for ( Image image : list ) {
            list_.add( imageToImageDTO( image ) );
        }

        return list_;
    }

}
