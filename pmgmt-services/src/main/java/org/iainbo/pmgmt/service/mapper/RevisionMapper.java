package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.RevisionDTO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.image.Revision;
import org.iainbo.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface RevisionMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "revisionNumber", target = "revisionNumber"),
            @Mapping(source = "checkedOut", target = "checkedOut"),
            @Mapping(source = "headRevision", target = "headRevision"),
            @Mapping(source = "uploadedBy", target = "uploadedBy")
    })
    RevisionDTO revisionToRevisionDTO(Revision revision);
    UserDTO userToUserDTO(User user);
}
