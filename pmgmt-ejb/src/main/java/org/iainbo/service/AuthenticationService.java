package org.iainbo.service;

import org.iainbo.dao.UserDAO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.User;
import org.iainbo.service.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named
public class AuthenticationService {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    public UserDTO getUser(String userName){
        User user = userDAO.findByUsername(userName);
        UserDTO userDTO = new UserDTO();
        userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }
}
