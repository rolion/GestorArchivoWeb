package Entities;


import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import util.MyParam;



/**
 * @author Rodry
 * @version 1.0
 * @created 15-ene.-2015 10:03:32
 */
@ManagedBean()
@SessionScoped
public class EImagen {

    private String contentType;
    private Object id;
    private Object idDoc;
    private String fechaUpload;
    private String nombreArchivo;
    private String tipoImagen;
    private GridFSDBFile imageFile;
    private InputStream imageStream;
    private DefaultStreamedContent img;
    public EImagen(GridFSDBFile imageFile){
        this.id=imageFile.getId();
        this.idDoc=imageFile.getMetaData().get(MyParam.IMAGEN_ID_DOC);
        this.fechaUpload=imageFile.getUploadDate().toString();
        this.nombreArchivo=imageFile.getFilename();
        this.contentType=imageFile.getContentType();
        this.tipoImagen=imageFile.getMetaData().get(MyParam.IMAGEN_TIPO_IMAGEN).toString();
        this.imageStream=imageFile.getInputStream();
        this.imageFile=imageFile;
    }
    public File writetoFile(){
        File f= new File("picPrueba.jpg");
        try {
            this.imageFile.writeTo(f);
        } catch (IOException ex) {
            Logger.getLogger(EImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    public EImagen() {
    }

    public DefaultStreamedContent getImg() {
           
        return new DefaultStreamedContent(this.imageStream,"image/png"); 
    }

    public void setImg(DefaultStreamedContent img) {
        this.img = img;
    }
    
    public static BasicDBObject classToBasiDBObject(EImagen image){
        BasicDBObject mBasicDBObject=null;
        if(image!=null){
            mBasicDBObject=new BasicDBObject();
            mBasicDBObject.append(MyParam.IMAGEN_TIPO_IMAGEN, image.getTipoImagen());
            mBasicDBObject.append(MyParam.IMAGEN_ID_DOC, image.getIdDoc());
            mBasicDBObject.append(MyParam.IMAGEN_NOMBRE_DOCUMENTO, image.getNombreArchivo());
            mBasicDBObject.append(MyParam.IMAGEN_CONTENT_TYPE, image.getContentType());
            
        }
        return mBasicDBObject;
    }
    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }
    
    public GridFSDBFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(GridFSDBFile imageFile) {
        this.imageFile = imageFile;
    }
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(Object idDoc) {
        this.idDoc = idDoc;
    }

    public String getFechaUpload() {
        return fechaUpload;
    }

    public void setFechaUpload(String fechaUpload) {
        this.fechaUpload = fechaUpload;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();

    }

    @Override
    public String toString() {
        if(this.nombreArchivo==null || this.nombreArchivo.length()<1)
            return "Sin Nombre";
        return  nombreArchivo ;
    }

}