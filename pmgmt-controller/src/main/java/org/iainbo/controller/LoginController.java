package org.iainbo.controller;


import org.iainbo.dto.UserDTO;
import org.iainbo.pmgmt.service.AuthenticationService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

    @Inject
    AuthenticationService authenticationService;

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

    public void checkUserDetails(){

        String enteredUserName = getUserName();
        String enteredPassword = getPassword();
        System.out.println("Username is: " + enteredUserName);

        if(authenticationService.verifyUserExisits(enteredUserName)){
            UserDTO userDTO = authenticationService.getUser(enteredUserName);
            if(userDTO.getPassword().equals(enteredPassword)){
                addGrowlMessage("User is Authenticated!");
            }else {
                addGrowlMessage("Password is incorrect!");
            }
        }else{
            addGrowlMessage("User does not exist!");
        }
    }

    public void addGrowlMessage(String newMessage){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, newMessage,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void currentUser(){
        String currentUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        setUserName(currentUser);
        System.out.println(currentUser);
    }
}
