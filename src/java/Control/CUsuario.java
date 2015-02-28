/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entities.EUsuario;
import Mongo.MongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import util.MyParam;

/**
 *
 * @author Rodry
 */
@ManagedBean(name = "cUsuario")
@RequestScoped
public class CUsuario {
    
    MongoDB con;

    public CUsuario() {
        con=new MongoDB(MyParam.serverDir, MyParam.port);
        con.setDatabBase(MyParam.nameDB);
    }
    public boolean validarUsuario(EUsuario usuario) throws Exception{
        boolean rslt=false;
        List result=null;
        result =con.getDoc(MyParam.CollecData,EUsuario.classToBasicDBOject(usuario));
        if(result!=null && result.size()>0)
            rslt=true;
        return rslt;
    }
    public Object nuevoUsuario(EUsuario usuario) throws Exception{
         Object id=null;
         if( con!=null){
             id =con.insertDoc(MyParam.CollecData, EUsuario.classToBasicDBOject(usuario));
             System.out.println(id);
         }else
             throw new Exception("No hay conexion con la base de datos");
         return id;
    }
    public List<DBObject> getUsuario(EUsuario usuario) throws Exception{
        List<DBObject> listUsuario=null;
        if( con!=null){
            listUsuario=con.getDoc(MyParam.CollecData, EUsuario.classToBasicDBOject(usuario));
        }else{
             throw new Exception("No hay conexion con la base de datos");
        }
        return listUsuario;
    }
    public List getAllUsuario() throws Exception{
        List<EUsuario> lUsuario=null;
        if(con!=null){
            BasicDBObject query= new BasicDBObject();
            query.append(MyParam.Nick_Name, "");
            query.append(MyParam.Pass_Word, "");
            List<DBObject> lista=con.getAllDoc(MyParam.CollecData, query);
            lUsuario=new ArrayList<>();
            for (DBObject obUser : lista) {
                EUsuario user=new EUsuario();
                user.setNickname((String) obUser.get(MyParam.Nick_Name));
                user.setPassword(obUser.get(MyParam.Pass_Word).toString());
                lUsuario.add(user);
            }
        }
        return lUsuario;
    }
    
}
