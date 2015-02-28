/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodry
 */
public class MongoHelper {
    private static MongoClient con;
    private static MongoHelper mMongoHelper;
    private MongoHelper(String serverDir, int port){
        try {
            con=new MongoClient(serverDir,port);
        } catch (UnknownHostException ex) {
            System.out.println("Error al conectar con el servidor");
            System.out.println(ex.getMessage());
            Logger.getLogger(MongoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MongoHelper getMongoHelper(String serverDir, int port) 
            throws UnknownHostException{
       if(mMongoHelper==null)
           mMongoHelper= new MongoHelper(serverDir,port);
           
       
       return mMongoHelper;
    }
    public DB getDataBase(String nameDB){
        if(con!=null)
            return con.getDB(nameDB);
        else 
            return null;
    };
   
}
