package main.utils;

import java.util.HashMap;

public class SyncObject {
    public boolean bool;
    public HashMap<String, Object> sharedObjects = new HashMap<>();
    public SyncObject(boolean b){
        bool = b;
    }
    public SyncObject(){
        bool = true;
    }

    public void put(String key, Object value){
        sharedObjects.put(key,value);
    }
    public Object get(String key){
        return sharedObjects.get(key);
    }

    public String getString(String key){
        return (String)sharedObjects.get(key);
    }
}
