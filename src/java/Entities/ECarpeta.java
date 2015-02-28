package Entities;

import com.mongodb.BasicDBObject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import util.MyParam;



/**
 * @author Rodry
 * @version 1.0
 * @created 15-ene.-2015 10:03:32
 */
@ManagedBean
@RequestScoped
public class ECarpeta {

    private Object id;
    private Object idCarpetaPadre;
    private String nombre;
    private String nickUser;

    public ECarpeta(BasicDBObject carpeta){
        this.id=carpeta.get(MyParam.Ob_Id);
        this.idCarpetaPadre=carpeta.get(MyParam.Ob_Id_Carpeta_Padre);
        this.nombre=carpeta.getString(MyParam.Nombre_Carpeta);
        this.nickUser=carpeta.getString(MyParam.User_Carpeta);
    }

    public ECarpeta() {
    }

    public Object getId() {
        return id;
    }

    public static BasicDBObject classToBasicDBObject(ECarpeta carpeta){
        BasicDBObject mBasicDBObject=null;
        if(carpeta!=null)
        {
            mBasicDBObject= new BasicDBObject();
            if(carpeta.getIdCarpetaPadre()!=null)
                mBasicDBObject.append(MyParam.Ob_Id_Carpeta_Padre, carpeta.getIdCarpetaPadre());
            mBasicDBObject.append(MyParam.Nombre_Carpeta, carpeta.getNombre());
            mBasicDBObject.append(MyParam.User_Carpeta, carpeta.getNickUser());
            if(carpeta.getId()!=null)
                mBasicDBObject.append(MyParam.Ob_Id, carpeta.getId());
                    
        }
        return mBasicDBObject;
    }
    public String getNickUser() {
        return nickUser;
    }

    public void setNickUser(String nickUser) {
        this.nickUser = nickUser;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getIdCarpetaPadre() {
        return idCarpetaPadre;
    }

    public void setIdCarpetaPadre(Object idCarpetaPadre) {
        this.idCarpetaPadre = idCarpetaPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    @Override
    public void finalize() throws Throwable {
        super.finalize();

    }

    @Override
    public String toString() {
        return this.nombre ;
    }
    

}