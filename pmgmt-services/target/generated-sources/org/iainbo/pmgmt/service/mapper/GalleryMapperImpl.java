package org.iainbo.pmgmt.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.iainbo.dto.GalleryDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2015-08-26T14:22:46+0100"
)
@ApplicationScoped
public class GalleryMapperImpl extends GalleryMapper {

    @Override
    public GalleryDTO galleryToGalleryDTO(Gallery gallery)  {
        if ( gallery == null ) {
            return null;
        }

        GalleryDTO galleryDTO = new GalleryDTO();

        galleryDTO.setOwner( userToUserDTO( gallery.getOwner() ) );
        galleryDTO.setThumbnail( gallery.getThumbnail() );
        galleryDTO.setDateCreated( gallery.getDateCreated() );
        galleryDTO.setDescription( gallery.getDescription() );
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
