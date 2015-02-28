/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import util.MyError;
import util.MyParam;

/**
 *
 * @author Rodry
 */
public class MongoDB {
    private MongoHelper mMongoHelper;
    private DB db;
    
    public MongoDB(String serverDir, int port){
            try {
                mMongoHelper=MongoHelper.getMongoHelper(serverDir, port);
            } catch (UnknownHostException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(MongoDB.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    public void setDatabBase(String nameDB){
        this.db=mMongoHelper.getDataBase(nameDB);
    }
    
     public Object insertDoc(String collectionName, BasicDBObject doc) throws Exception{
        if(db!=null){
            try{
                DBCollection coll = db.getCollection(collectionName);
                WriteResult wr=coll.insert(doc);
                return wr;
            }catch(MongoException me){
                System.out.println("Error al insertar el objecto "+doc.toString());
            }
        }else
            throw new Exception(MyError.ErrorDBMongo);
        return null;
    }
    public List<DBObject> getAllDoc(String collectionName, BasicDBObject doc) throws Exception{
        List<DBObject> lista=null;
        if(db!=null){
            DBCollection coll = db.getCollection(collectionName);
            DBCursor cursor = coll.find(new BasicDBObject(), doc);
            try {
                 lista=cursor.toArray();
            } finally {
               cursor.close();
            }
        }else
            throw new Exception(MyError.ErrorDBMongo);
         return lista;
    }
    public List<DBObject> getDoc(String collectionName, 
        BasicDBObject query) throws Exception,MongoException{
         List<DBObject> lista=null;
         if(db!=null){
            DBCollection coll = db.getCollection(collectionName);
            DBCursor cursor = coll.find(query);
            try {
                lista=cursor.toArray();
            } finally {
                cursor.close();
            }
         }else
            throw new Exception(MyError.ErrorDBMongo);
         return lista;
    }
    public boolean removeDoc(BasicDBObject metaData, String collection) 
            throws Exception,MongoException{
        boolean rst=false;
        if(db!=null){
            if(metaData!=null){
                DBCollection coll = db.getCollection(collection);
                coll.remove(metaData);
            }
            else
                throw new Exception(MyError.ErrorDBOjectQuery);
        }else
           throw new Exception(MyError.ErrorDBMongo); 
        return rst;
    }
    public GridFSInputFile saveImage(File imageFile,BasicDBObject metaData) throws Exception, MongoException{
        GridFSInputFile rst=null;
        if(db!=null){
            if(imageFile!=null){
                GridFS gfsPhoto = new GridFS(db, MyParam.CollecPhoto);
                try{
                    GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
                    gfsFile.setContentType("jpg");
                     if(metaData!=null)
                        gfsFile.setMetaData(metaData);
                    gfsFile.save();
                    rst=gfsFile;
                }
                catch(IOException ioe){
                    System.out.println("Error al insertar archivo "+ioe.getMessage());
                }
            }else
                throw new Exception(MyError.ErrorFileNull);
        }else
            throw new Exception(MyError.ErrorDBMongo);   
        return rst;
    }
     public GridFSInputFile saveImage(InputStream imageFile,BasicDBObject metaData, String collection) throws Exception, MongoException{
        GridFSInputFile rst=null;
        if(db!=null){
            if(imageFile!=null){
                GridFS gfsPhoto = new GridFS(db, collection);
                GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
                gfsFile.setFilename(metaData.getString(MyParam.IMAGEN_NOMBRE_DOCUMENTO));
                if(metaData!=null)
                    gfsFile.setMetaData(metaData);
                gfsFile.save();
                rst=gfsFile;
            }else
                throw new Exception(MyError.ErrorFileNull);
        }else
            throw new Exception(MyError.ErrorDBMongo);   
        return rst;
    }
    public GridFSInputFile saveImage(byte[] imageFile,BasicDBObject metaData,String collection) throws Exception, MongoException{
        GridFSInputFile rst=null;
        if(db!=null){
            if(imageFile!=null){
                GridFS gfsPhoto = new GridFS(db, collection);
                GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
                if(metaData!=null)
                    gfsFile.setMetaData(metaData);
                gfsFile.save();
                rst= gfsFile; 
            }else
                throw new Exception(MyError.ErrorFileNull);
        }else
            throw new Exception(MyError.ErrorDBMongo);   
        return rst;
    }
    public List<GridFSDBFile> getImages(BasicDBObject query,String collection) throws Exception, MongoException{
        // if query=null return all;
        List<GridFSDBFile> lista=null;
        if(db!=null){
            if(query!=null){
                GridFS gfsPhoto= new GridFS(db, collection);
                lista=gfsPhoto.find(query);
            }
            else
                throw new Exception(MyError.ErrorDBOjectQuery);        
        }else
            throw new Exception(MyError.ErrorDBMongo);
        return lista;
    }
    public GridFSDBFile getOneImage(BasicDBObject query,String collection) throws Exception, MongoException{
        GridFSDBFile image=null;
        if(db!=null){
            if(query!=null){
                GridFS gfsPhoto= new GridFS(db, collection);
                image=gfsPhoto.findOne(query);
            }
            else
                throw new Exception(MyError.ErrorDBOjectQuery);        
        }else
            throw new Exception(MyError.ErrorDBMongo);
        return image;
    }
    public boolean removeImage(DBObject query,String collection) throws Exception, MongoException{
        boolean result=false;
        if(db!=null)
        {
            GridFS gfsPhoto= new GridFS(db, collection);
            gfsPhoto.remove(query);
            result=true;
        }else{
            throw new Exception(MyError.ErrorDBMongo);
        }
        return result;
    }
    public boolean removeImage(ObjectId id, String collection) throws Exception, MongoException{
        boolean result=false;
        if(db!=null)
        {
            GridFS gfsPhoto= new GridFS(db, collection);
            gfsPhoto.remove(id);
            result=true;
        }else{
            throw new Exception(MyError.ErrorDBMongo);
        }
        return result;
    }
    public boolean removeImage(String filename,String collection) throws Exception, MongoException{
        boolean result=false;
        if(db!=null)
        {
            GridFS gfsPhoto= new GridFS(db,collection);
            gfsPhoto.remove(filename);
            result=true;
        }else{
            throw new Exception(MyError.ErrorDBMongo);
        }
        return result;
    }
}
