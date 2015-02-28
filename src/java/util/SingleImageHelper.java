/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Control.CImagen;
import Entities.EImagen;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Rodry
 */
@ManagedBean(name = "nSingleImagenes")
@SessionScoped
public class SingleImageHelper {
    
    private UploadedFile singleFile;
    private StreamedContent Image;
    private CImagen mCImage;

    public StreamedContent getImage() {
         FacesContext context = FacesContext.getCurrentInstance();
         EImagen image=null;
         DefaultStreamedContent content=null;
         try{
//           if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
//          
//             content= new DefaultStreamedContent();
//            }else{
                if(singleFile==null)
                    content= new DefaultStreamedContent();
                else
                    content= new DefaultStreamedContent(singleFile.getInputstream(),singleFile.getContentType()); 
               // return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()));
//           }
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
         return content;
    }

    public void setImage(StreamedContent Image) {
        this.Image = Image;
    }
    
    public void handleSingleImageUpLoad(FileUploadEvent event){
        singleFile=event.getFile();
    }
    
}
