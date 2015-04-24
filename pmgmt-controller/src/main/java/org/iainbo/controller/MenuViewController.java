package org.iainbo.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean

public class MenuViewController {

    public void save() {
        addMessage("Success", "Data saved");
    }

    public void update() {
        addMessage("Success", "Data updated");
    }

    public void delete() {
        addMessage("Success", "Data deleted");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/pmgmt/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
