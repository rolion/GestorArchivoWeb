/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Control.CUsuario;
import Entities.EUsuario;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import util.MyParam;

/**
 *
 * @author Rodry
 */
@ManagedBean(name = "nLogin")
@RequestScoped
public class NLogin {

    /**
     * Creates a new instance of NLogin
     */
    private CUsuario mCUsuario;
    public NLogin() {
        mCUsuario= new CUsuario();
    }
    
    public String validarUsuario(EUsuario user){
        String rslt="No Valido";
        try{
            if(mCUsuario.validarUsuario(user)){
                rslt="Principal?faces-redirect=true";
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getSessionMap().put(MyParam.Nick_Name, user.getNickname());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            rslt="No Valido";
        }
        return rslt;
    }
    public String guardarUsuario(EUsuario user){
        String rslt="error";
        if(user!=null){
            try {
                if(!mCUsuario.validarUsuario(user)){
                    mCUsuario.nuevoUsuario(user);
                    showMessage("Usuario", "El Usuario:"+user.getNickname()+" se Guardo Correctamente");
                    rslt="";
                }else{
                     showMessage("Usuario", "El Usuario:"+user.getNickname()+" ya existe");
                     rslt="";
                }
            } catch (Exception ex) {
                rslt="error";
                Logger.getLogger(NLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rslt;
    }
    private void showMessage(String title, String message){
         FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(title,  
                           message) );
    }
}
