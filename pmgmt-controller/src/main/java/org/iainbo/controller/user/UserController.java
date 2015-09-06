package org.iainbo.controller.user;


import org.iainbo.dto.User.UserDTO;
import org.iainbo.pmgmt.service.user.UserService;
import org.iainbo.pmgmt.view.user.UserView;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class UserController implements Serializable{

    @Inject
    UserView userView;

    @Inject
    UserService userService;

    public String getCurrentUserName(){
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    //Method which is called from the header to display the username at the top of the page
    public void getCurrentlyLoggedInUserForPageHeader(){
        String userName = getCurrentUserName();
        if (userService.verifyUserExisits(userName)){
            UserDTO userDTO = userService.getUser(userName);
            userView.setUserName(userDTO.getUserName());
            userView.setFirstName(userDTO.getFirstName());
            userView.setSurname(userDTO.getSurname());
            userView.setFullName(userDTO.getFirstName() + " " + userDTO.getSurname());
        }
    }

    public UserDTO getUserDTOForLoggedInUser(){
        UserDTO userDTO = userService.getUser(getCurrentUserName());
        return userDTO;
    }

}
