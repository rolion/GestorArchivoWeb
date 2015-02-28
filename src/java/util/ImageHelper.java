/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Control.CImagen;
import Entities.EImagen;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import org.bson.types.ObjectId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rodry
 */
@ManagedBean(name = "imageHelper")
@SessionScoped
public class ImageHelper {

    /**
     * Creates a new instance of ImageHelper
     */
    private StreamedContent Image;
    private CImagen mCImage;
    public StreamedContent getImage() {
         FacesContext context = FacesContext.getCurrentInstance();
         EImagen image=null;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
          
             return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            BufferedImage bImage;
             try {
                 image=this.mCImage.getImage(id);

             } catch (Exception ex) {
                 Logger.getLogger(ImageHelper.class.getName()).log(Level.SEVERE, null, ex);
             }
            if(image==null)
                return new DefaultStreamedContent();
            else
                return new DefaultStreamedContent(image.getImageStream()); 
           // return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()));
        }
    }

    public void setImage(StreamedContent Image) {
        this.Image = Image;
    }
    public ImageHelper() {
        this.mCImage=new CImagen();
    }
    private BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
    
    return bi;
}
    
}
