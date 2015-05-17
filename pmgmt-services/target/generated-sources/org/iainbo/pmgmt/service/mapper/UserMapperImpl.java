package org.iainbo.pmgmt.service.mapper;

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2015-05-17T17:03:17+0100"
)
@ApplicationScoped
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user)  {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setPassword( user.getPassword() );
        userDTO.setId( user.getId() );
        userDTO.setUserName( user.getUserName() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setDateCreated( user.getDateCreated() );
        userDTO.setSurname( user.getSurname() );
        userDTO.setEmail( user.getEmail() );


        return userDTO;
    }

}
