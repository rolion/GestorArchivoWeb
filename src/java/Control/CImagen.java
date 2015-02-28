/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entities.EExamen;
import Entities.EImagen;
import Mongo.MongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import util.MyParam;

/**
 *
 * @author Rodry
 */
public class CImagen {
    private MongoDB con;

    public CImagen() {
        con= new MongoDB(MyParam.serverDir, MyParam.port);
        con.setDatabBase(MyParam.nameDB);
    }
    public EImagen saveImage(EImagen imagen) throws Exception{
        EImagen rslt=null;
        if(con!=null && imagen!=null){
            BasicDBObject ob= EImagen.classToBasiDBObject(imagen);
            GridFSInputFile file= con.saveImage(imagen.getImageStream(), ob, MyParam.CollecPhoto);
            if(file!=null){
                imagen.setId(file.getId());
                
                imagen.setFechaUpload(file.getUploadDate().toString());
                rslt=imagen;
            }
        }
        return rslt;
    }
    public List<EImagen> getImagenesExamen(EExamen examen) throws Exception{
        List<EImagen> listaImagenes=null;
        if(examen!=null && con!=null){
            BasicDBObject ob= new BasicDBObject();
            ob.append("metadata."+MyParam.IMAGEN_TIPO_IMAGEN, MyParam.TIPO_IMAGEN_1);
            ob.append("metadata."+MyParam.IMAGEN_ID_DOC, examen.getId());
            List<GridFSDBFile> l=con.getImages(ob,MyParam.CollecPhoto);
            listaImagenes=new ArrayList<>();
            for (GridFSDBFile e : l) {
                listaImagenes.add(new EImagen(e));
            }
        }
        return listaImagenes;
    }
    public boolean removeImageExamen(EImagen img) throws Exception{
        boolean rslt=false;
        if(img!=null){
            con.removeImage((ObjectId) img.getId(), MyParam.CollecPhoto);
            rslt=true;
        }
        return rslt;
    }
    public EImagen getImage(String id) throws Exception{
        EImagen image=null;
        if(id!=null && con!=null && id.length()>0){
            BasicDBObject ob= new BasicDBObject();
            ob.append(MyParam.Ob_Id, new ObjectId(id));
            GridFSDBFile l=con.getOneImage(ob, MyParam.CollecPhoto);
            if(l!=null){
                image=new EImagen(l);
            }
        }
        return image;
    }
    
}
