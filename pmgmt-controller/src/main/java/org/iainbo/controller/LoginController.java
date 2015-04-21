package org.iainbo.controller;


import org.iainbo.dto.UserDTO;
import org.iainbo.pmgmt.service.AuthenticationService;
import org.iainbo.pmgmt.view.User.UserView;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginController implements Serializable{

    @Inject
    UserView userView;

    @Inject
    AuthenticationService authenticationService;

    public void getCurrentlyLoggedInUser(){
        String userName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if (authenticationService.verifyUserExisits(userName)){
            UserDTO userDTO = new UserDTO();
            userDTO = authenticationService.getUser(userName);
            userView.setUserName(userDTO.getUserName());
            userView.setFirstName(userDTO.getFirstName());
            userView.setSurname(userDTO.getSurname());
            userView.setFullName(userDTO.getFirstName() + " " + userDTO.getSurname());
        }
    }

}
