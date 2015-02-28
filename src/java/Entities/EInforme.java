package Entities;


import Entities.ECarpeta;
import com.mongodb.BasicDBObject;
import util.MyParam;
/**
 * @author Rodry
 * @version 1.0
 * @created 15-ene.-2015 10:03:31
 */
public class EInforme {

    private String colegio;
    private String fechaRealizacion;
    private Object _id;
    private String nombreAlumno;
    private String edad;
    private String Entrevistador;
    public ECarpeta m_ECarpeta;

    public EInforme(BasicDBObject informe){
        this.colegio=informe.getString(MyParam.INFORME_COLEIO);
        this.fechaRealizacion=informe.getString(MyParam.INFORME_FECHA_REALIZACION);
        this._id=informe.get(MyParam.Ob_Id);
        this.nombreAlumno=informe.getString(MyParam.INFORME_NOMBRE_ALUMNO);
        this.edad=informe.getString(MyParam.INFORME_EDAD_ALUMNO);
        this.Entrevistador=informe.getString(MyParam.INFORME_Entrevistador);
    }

    public static BasicDBObject classToBasicDBObject(EInforme informe){
        BasicDBObject mBasicDBObject=null;
        if(informe!=null){
            mBasicDBObject=new BasicDBObject();
            mBasicDBObject.append(MyParam.INFORME_COLEIO, informe.getColegio());
            mBasicDBObject.append(MyParam.INFORME_FECHA_REALIZACION, informe.getFechaRealizacion());
            mBasicDBObject.append(MyParam.INFORME_NOMBRE_ALUMNO, informe.getNombreAlumno());
            mBasicDBObject.append(MyParam.INFORME_EDAD_ALUMNO, informe.getEdad());
            mBasicDBObject.append(MyParam.INFORME_Entrevistador, informe.getEntrevistador());
            if(informe.getId()!=null){
                mBasicDBObject.append(MyParam.Ob_Id, informe.getId());
                        
            }
        }
        return mBasicDBObject;
    }
    public EInforme() {
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public ECarpeta getCarpeta() {
        return m_ECarpeta;
    }

    public void setCarpeta(ECarpeta m_ECarpeta) {
        this.m_ECarpeta = m_ECarpeta;
    }


    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public Object getId() {
        return _id;
    }

    public void setId(Object _id) {
        this._id = _id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getEntrevistador() {
        return Entrevistador;
    }

    public void setEntrevistador(String Entrevistador) {
        this.Entrevistador = Entrevistador;
    }
    @Override
    public void finalize() throws Throwable {
        super.finalize();

    }

}