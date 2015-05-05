package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.GalleryDTO;
import org.iainbo.dto.GalleryMetadataDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.gallery.Gallery;
import org.iainbo.entities.gallery.GalleryMetadata;
import org.iainbo.entities.image.Image;
import org.iainbo.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface GalleryMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "galleryName", target = "galleryName"),
            @Mapping(source = "dateCreated", target = "dateCreated"),
            @Mapping(source = "owner", target = "owner"),
            @Mapping(source = "metadataList", target = "galleryMetadataDTOList"),
            @Mapping(source = "images", target = "imageDTOList"),
            @Mapping(source = "thumbnail", target = "thumbnail")
    })
    GalleryDTO galleryToGalleryDTO(Gallery gallery);
    UserDTO userToUserDTO(User user);
    GalleryMetadataDTO galleryMetadataToGalleryMetadataDTO(GalleryMetadata galleryMetadata);
    ImageDTO imageToImageDTO(Image image);
}
