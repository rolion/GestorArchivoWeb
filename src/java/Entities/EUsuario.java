package Entities;

import com.mongodb.BasicDBObject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.MyParam;



/**
 * @author Rodry
 * @version 1.0
 * @created 15-ene.-2015 10:03:32
 */
@ManagedBean
@SessionScoped
public class EUsuario {

    private String nickname;
    private String password;
    private Object _id;

    public EUsuario(BasicDBObject user) {
        this._id=user.get(MyParam.Ob_Id);
        this.nickname=user.getString(MyParam.Nick_Name);
        this.password=user.getString(MyParam.Pass_Word);
    }
    
    public static BasicDBObject classToBasicDBOject(EUsuario u){
        BasicDBObject mBasicDBObject= null;
        if(u!=null){
            mBasicDBObject=new BasicDBObject();
            mBasicDBObject.append(MyParam.Nick_Name, u.getNickname());
            mBasicDBObject.append(MyParam.Pass_Word, u.getPassword());
            if(u.getId()!=null)
                mBasicDBObject.append(MyParam.Ob_Id, u.getId());
        }
        return mBasicDBObject;
    }

    public EUsuario(){
        this._id=null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getId() {
        return _id;
    }

    public void setId(Object _id) {
        this._id = _id;
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return nickname;
    }

}