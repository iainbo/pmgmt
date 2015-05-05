package org.iainbo.pmgmt.service;

import org.iainbo.dao.user.UserDAO;
import org.iainbo.dto.UserDTO;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.UserMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

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
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }
}
