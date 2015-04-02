package org.iainbo.pmgmt.service.mapper;

import org.iainbo.dto.UserDTO;
import org.iainbo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "userName", target = "userName"),
            @Mapping(source = "password", target = "password")
    })
    UserDTO userToUserDTO(User user);
}
