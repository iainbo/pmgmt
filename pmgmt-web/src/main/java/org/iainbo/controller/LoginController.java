package org.iainbo.controller;


import org.iainbo.dto.UserDTO;
import org.iainbo.service.AuthenticationService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

    @Inject
    AuthenticationService authenticationService;

    //variables only added till the rest of the structure is in place
    private boolean loginStatus;

    private String userName;

    private String password;

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkUserDetails(){
        boolean userAuthenticated = false;
        String enteredUserName = getUserName();
        String enteredPassword = getPassword();
        System.out.println("Username is: " + enteredUserName);
        if(authenticationService.getUser(enteredUserName) != null){
            UserDTO userDTO = authenticationService.getUser(enteredUserName);
            if(userDTO.getPassword().equals(enteredPassword)){
                userAuthenticated = true;
            }
        }
        System.out.println(userAuthenticated);
        return userAuthenticated;
    }
}
