package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.Image.FileDTO;
import org.iainbo.dto.Image.RevisionDTO;
import org.iainbo.dto.User.UserDTO;
import org.iainbo.entities.image.File;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface FileMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "revision", target = "revisionDTO"),
            @Mapping(source = "filename", target = "filename"),
            @Mapping(source = "file", target = "fileData")
    })
    FileDTO fileToFileDTO(File file);
    RevisionDTO revisionToRevisionDTO(Revision revision);
    UserDTO userToUserDTO(User user);
}
