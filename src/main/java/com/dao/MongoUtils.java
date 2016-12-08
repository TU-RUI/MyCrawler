package com.dao;

import org.mongodb.morphia.Morphia;

import com.model.PicInfoContent;
import com.model.UserInfoContent;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@SuppressWarnings("deprecation")
public class MongoUtils {
    private static MongoUtils mongoUtils = null;
    private Morphia morphia = null;
    private Mongo mongo = null;
    private DB db = null;
    private DBCollection picCollection = null;
    private DBCollection userCollection = null;
    //10.23.102.122:32800;10.23.102.125:32800
    private static final String dbName = "test";
    private static final String host = "10.23.102.122";
    private static final int port = 32800;
    private static final String picCollectionName = "pic";
    private static final String userCollectionName = "user";
    
    //初始化
    private MongoUtils(){
        morphia = new Morphia();
        morphia.map(PicInfoContent.class);
        morphia.map(UserInfoContent.class);
        MongoOptions mongoOptions = new MongoOptions();
        mongoOptions.setConnectionsPerHost(200);
        mongoOptions.setSocketKeepAlive(true);
        mongo = new Mongo(new ServerAddress(host,port),mongoOptions);
        db = mongo.getDB(dbName);
        picCollection = db.getCollection(picCollectionName);
        userCollection = db.getCollection(userCollectionName);
    }
    
    //获取实例
    public static MongoUtils getInstance(){
        if(mongoUtils == null){
            synchronized(MongoUtils.class){
                if(mongoUtils == null){
                    mongoUtils = new MongoUtils();
                }
            }
        }
        return mongoUtils;
    }
    
    //关闭连接
    public void close(){
        if(mongo!=null){
            mongo.close();
        }
        morphia = null;
        mongo = null;
        db = null;
        userCollection = null;
        picCollection = null;
    }
    
    //添加Pic信息
    public boolean addPicEntity(PicInfoContent entity){
        try {
            DBObject object = morphia.toDBObject(entity);
            picCollection.save(object, WriteConcern.SAFE);
            return true;
        } catch (MongoException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
    
    //添加User
    public boolean addUserEntity(UserInfoContent entity){
        try {
            DBObject object = morphia.toDBObject(entity);
            userCollection.save(object, WriteConcern.SAFE);
            return true;
        } catch (MongoException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
    
}
