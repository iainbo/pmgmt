package org.iainbo.service;

import org.iainbo.dao.UserDAO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.User;
import org.iainbo.service.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named
public class AuthenticationService {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    public boolean verifyUserExisits(String userName){
        boolean userExists = false;
        try{
            if(userDAO.findByUsername(userName) != null){
                userExists = true;
            }
        }catch(NoResultException e){
            userExists = false;
        }

        return userExists;
    }

    public UserDTO getUser(String userName){
        User user = userDAO.findByUsername(userName);
        UserDTO userDTO = new UserDTO();
        userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }
}