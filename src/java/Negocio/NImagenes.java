/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Control.CImagen;
import Entities.EExamen;
import Entities.EImagen;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rodry
 */
public class NImagenes {
    private CImagen mCImagen;

    public NImagenes() {
        mCImagen=new CImagen();
    }
    
    public List<EImagen> getImagenes(EExamen exam){
        List<EImagen> listaImagenes=null;
        if(exam!=null){
            try {
                listaImagenes=mCImagen.getImagenesExamen(exam);
            } catch (Exception ex) {
                showMessage("Error", "No se pudo recuperar el examen:"+exam.getNombreExamen());
                Logger.getLogger(NImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaImagenes;
    }
    
    private void showMessage(String title, String message){
         FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(title,  
                           message) );
    }
    public EImagen saveImage(EImagen img){
        if(img!=null){
            try {
                img=mCImagen.saveImage(img);
            } catch (Exception ex) {
                showMessage("Error", "No se pudo guardar la imagen:"+img.getNombreArchivo());
                Logger.getLogger(NImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return img;
    }
    public boolean removeImage(EImagen img){
        boolean rslt=false;
        if(img!=null){
            try {
                if(!this.mCImagen.removeImageExamen(img))
                    showMessage("Error", "No se pudo eliminar la imagen:"+img.getNombreArchivo());
                else
                    rslt=true;
            } catch (Exception ex) {
                Logger.getLogger(NImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rslt;
    }
    
}
