package com.watchman.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.watchman.exceptions.DatabaseException;
import com.watchman.services.DatabaseService;

public class Database<T extends DatabaseService & Serializable> {
    final static private String DIRECTORY = "com/watchman/database/";
    final private File FILE;
    
    private Map<String, T> DATA;
    private boolean DATABASE_EXIST = true;

    static final private Map<String, ?> CACHE = new HashMap<>(1);

    static boolean databaseExist(String DATABASE_NAME){
        if(CACHE.containsKey(DATABASE_NAME)) return (boolean) CACHE.get(DATABASE_NAME);
        return new File(DIRECTORY, DATABASE_NAME).exists();
    }
    public Database(final String DATABASE_NAME) throws DatabaseException{
        FILE = new File(DIRECTORY, DATABASE_NAME);

        try{
            if(FILE.exists()){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
                DATA = (Map<String, T>) ois.readObject();
                ois.close();
            }
            else{
                DATA = new HashMap<>(1);
            }
        }
        catch(FileNotFoundException e){
            throw new DatabaseException("Database not found ("+DATABASE_NAME+")!");
        }
        catch(IOException e){
            e.printStackTrace();
            throw new DatabaseException("Something went Wrong!");
        }
        catch(ClassNotFoundException | ClassCastException e){
            throw new DatabaseException("Trying to access Invalid Type of Data");
        }   
    }
    final public boolean save(){
        if(!DATABASE_EXIST) return false;

        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(DATA);
            oos.close();
            oos.flush();
            return true;
        }
        catch(IOException e){
            restore();
            return false;
        }
    }
    final public boolean restore(){
        if(!DATABASE_EXIST) return false;
        
        try{
            if(FILE.exists()){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
                DATA = (Map<String, T>) ois.readObject();
                ois.close();
            }
            else{
                DATA = new HashMap<>(1);
            }
            return true;
        }
        catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
            new DatabaseException("Could not restore!").printStackTrace();;
            return false;
        }
    }   
    final public T get(String UID){
        if(!DATABASE_EXIST) return null;
        if(exist(UID)) return DATA.get(UID);
        return null;
    }
    final public List<T> getAll(){
        if(!DATABASE_EXIST) return null;
        return new ArrayList<T>(DATA.values());
    }
    final public boolean exist(String UID){
        return DATA.containsKey(UID);
    }
    final public Database<T> add(T data){
        if(!DATABASE_EXIST) return null;

        if(exist(data.getID())){
            new DatabaseException("ID must be Unique").printStackTrace();;
            return null;
        }

        DATA.put(data.getID(), data);

        return this;
    }
    final public Database<T> addAll(List<T> dataList){
        if(!DATABASE_EXIST) return null;

        for(T data : dataList) add(data);

        return this;
    }
    final public Database<T> delete(String UID){
        if(!DATABASE_EXIST) return null;
        
        DATA.remove(UID);

        return this;
    }
    final public Database<T> clear(){
        if(!DATABASE_EXIST) return null;

        DATA.clear();
        
        return this;
    }
    final public boolean deleteDatabase(){
        if(FILE.delete()){
            DATA.clear();
            DATABASE_EXIST = false;
            return true;
        }
        return false;
    }
}
