/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entities.ECarpeta;
import Entities.EUsuario;
import Mongo.MongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import util.MyParam;

/**
 *
 * @author Rodry
 */
public class CCarpeta {
    
     private MongoDB con;

    public CCarpeta() {
        con= new MongoDB(MyParam.serverDir, MyParam.port);
        con.setDatabBase(MyParam.nameDB);
    }
     
     public List<ECarpeta> getAllCarpetas(EUsuario user) throws Exception{
         List <ECarpeta> listaC=null;
         if(user!=null){
             BasicDBObject ob= new BasicDBObject();
             ob.append(MyParam.User_Carpeta, user.getNickname());
             List<DBObject> lista=con.getDoc(MyParam.CollecData, ob);
             listaC= new ArrayList<>();
             for (DBObject e : lista) {
                 ECarpeta carpeta= new ECarpeta();
                 carpeta.setId(e.get(MyParam.Ob_Id));
                 carpeta.setIdCarpetaPadre(e.get(MyParam.Ob_Id_Carpeta_Padre));
                 carpeta.setNombre(e.get(MyParam.Nombre_Carpeta).toString());
                 carpeta.setNickUser(e.get(MyParam.User_Carpeta).toString());
                 listaC.add(carpeta);
             }
         }
         return listaC;
     }
    public ECarpeta insertCarpeta(ECarpeta carpeta, EUsuario user) throws Exception{
        ECarpeta carp=null;
        if(carpeta!=null){
            carpeta.setNickUser(user.getNickname());
            BasicDBObject Ob=ECarpeta.classToBasicDBObject(carpeta);
            con.insertDoc(MyParam.CollecData, Ob);
            Ob=(BasicDBObject) con.getDoc(MyParam.CollecData, Ob).get(0);
            carpeta.setId(Ob.get(MyParam.Ob_Id));
            carp=carpeta;
        }
        return carp;
    }
    public boolean eliminarCarpeta(ECarpeta carp) throws Exception{
        boolean rslt=false;
        if(carp!=null && carp.getId()!=null){
            
            con.removeDoc(ECarpeta.classToBasicDBObject(carp), MyParam.CollecData);
            rslt=true;
        }
        return rslt;
    }
    
}
