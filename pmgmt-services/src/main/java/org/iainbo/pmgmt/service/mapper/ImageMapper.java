package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.Gallery.GalleryDTO;
import org.iainbo.dto.Image.ImageDTO;
import org.iainbo.dto.Image.RevisionDTO;
import org.iainbo.dto.User.UserDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface ImageMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "revision", target = "revisionDTO"),
            @Mapping(source = "gallery", target = "galleryDTO"),
            @Mapping(source = "revisions", target = "revisions")
    })
    ImageDTO imageToImageDTO(Image image);
    RevisionDTO revisionToRevisionDTO(Revision revision);
    GalleryDTO galleryToGalleryDTO(Gallery gallery);
    UserDTO userToUserDTO(User user);
}
