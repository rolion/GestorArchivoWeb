/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entities.ECarpeta;
import Entities.EExamen;
import Mongo.MongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import util.MyParam;

/**
 *
 * @author Rodry
 */
public class CExamen {
    
    private Mongo.MongoDB con;

    public CExamen() {
        con= new MongoDB(MyParam.serverDir, MyParam.port);
        con.setDatabBase(MyParam.nameDB);
    }
    
    public EExamen saveExam(EExamen exam) throws Exception{
        if(exam!=null && con!=null){
            BasicDBObject ob=EExamen.classToBasiDBObject(exam);
            con.insertDoc(MyParam.CollecData,ob );
            List<DBObject> l=con.getDoc(MyParam.CollecData, ob);
            DBObject ob1= l.get(0);
            ObjectId id=new ObjectId(ob1.get(MyParam.Ob_Id).toString());
            exam.setId(id);
        }else
            exam=null;
        return exam;
    }
    public List<EExamen> getExamen(ECarpeta carp) throws Exception{
        List<EExamen> exam=null;
        if(carp!=null && con!=null){
            BasicDBObject ob=new BasicDBObject();
            ob.append(MyParam.EXAMEN_ID_CARPETA, carp.getId());
            List<DBObject>l=con.getDoc(MyParam.CollecData, ob);
            exam=new ArrayList<EExamen>();
            for (DBObject aux : l) {
                EExamen e= new EExamen();
                e.setColegio((String) aux.get(MyParam.EXAMEN_COLECIO));
                e.setId(aux.get(MyParam.Ob_Id));
                e.setM_ECarpeta(carp);
                e.setNombreAlumno(aux.get(MyParam.EXAMEN_NOMBRE_ALUMNO).toString());
                e.setNombreEntrevistador(aux.get(MyParam.EXAMEN_NOMBRE_ENTREVISTADOR).toString());
                e.setNombreExamen(aux.get(MyParam.EXAMEN_NOMBRE_EXAMEN).toString());
                exam.add(e);
            }
        }
        return exam;
    }
    public boolean removeExam(EExamen exam) throws Exception{
        boolean rslt=false;
        if(exam!=null){
            con.removeDoc(EExamen.classToBasiDBObject(exam), MyParam.CollecData);
            rslt=true;
        }
        return rslt;
    }
    
    
}
