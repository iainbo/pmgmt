package org.iainbo.pmgmt.service.user;

import org.iainbo.dao.factory.DAOFactory;
import org.iainbo.dto.User.UserDTO;
import org.iainbo.entities.user.User;
import org.iainbo.pmgmt.service.mapper.UserMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Stateless
@Named
public class UserService {

    @Inject
    DAOFactory daoFactory;

    @Inject
    UserMapper userMapper;

    public boolean verifyUserExisits(String userName){
        boolean userExists = false;
        try{
            if(daoFactory.userDAO().findByUsername(userName) != null){
                userExists = true;
            }
        }catch(NoResultException e){
            userExists = false;
        }

        return userExists;
    }

    public UserDTO getUser(String userName){
        User user = daoFactory.userDAO().findByUsername(userName);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }
}