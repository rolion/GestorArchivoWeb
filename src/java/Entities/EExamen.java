package Entities;


import Entities.ECarpeta;
import com.mongodb.BasicDBObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import util.MyParam;



/**
 * @author Rodry
 * @version 1.0
 * @created 15-ene.-2015 10:03:31
 */
@ManagedBean(name="eExamen")
@RequestScoped
public class EExamen {

    private String colegio;
    private Object id;
    private String nombreAlumno;
    private String nombreEntrevistador;
    private String nombreExamen;
    public ECarpeta m_ECarpeta;
    public List<EImagen> img;
   

    public EExamen(BasicDBObject examen){
        this.id=examen.get(MyParam.Ob_Id);
        this.colegio=examen.getString(MyParam.EXAMEN_COLECIO);
        this.nombreAlumno=examen.getString(MyParam.EXAMEN_NOMBRE_ALUMNO);
        this.nombreEntrevistador=examen.getString(MyParam.EXAMEN_NOMBRE_ENTREVISTADOR);
        this.nombreExamen=examen.getString(MyParam.EXAMEN_NOMBRE_EXAMEN);
        this.img=new ArrayList<>();
    }
    public static BasicDBObject classToBasiDBObject(EExamen examen){
        BasicDBObject mBasicDBObject=null;
        if(examen!=null){
            mBasicDBObject=new BasicDBObject();
            mBasicDBObject.append(MyParam.EXAMEN_COLECIO, examen.getColegio());
            mBasicDBObject.append(MyParam.EXAMEN_NOMBRE_ALUMNO, examen.getNombreAlumno());
            mBasicDBObject.append(MyParam.EXAMEN_NOMBRE_ENTREVISTADOR, examen.getNombreEntrevistador());
            mBasicDBObject.append(MyParam.EXAMEN_NOMBRE_EXAMEN,examen.getNombreExamen());
            mBasicDBObject.append(MyParam.EXAMEN_ID_CARPETA, examen.getM_ECarpeta().getId());
            if(examen.getId()!=null)
                mBasicDBObject.append(MyParam.Ob_Id,examen.getId());
        }
        return mBasicDBObject;
    }

    public List<EImagen> getImg() {
        return img;
    }

    public void setImg(List<EImagen> img) {
        this.img = img;
    }

  
    public void addImg(EImagen img){
        if(this.img==null){
            this.img=new ArrayList<>();
        }
        this.img.add(img);
    }
    public EExamen() {
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNombreEntrevistador() {
        return nombreEntrevistador;
    }

    public void setNombreEntrevistador(String nombreEntrevistador) {
        this.nombreEntrevistador = nombreEntrevistador;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public ECarpeta getM_ECarpeta() {
        return m_ECarpeta;
    }

    public void setM_ECarpeta(ECarpeta m_ECarpeta) {
        this.m_ECarpeta = m_ECarpeta;
    }
    
    public void finalize() throws Throwable {

    }

    @Override
    public String toString() {
        return  nombreExamen ;
    }

}