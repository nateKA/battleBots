package main.screen.graphics;

import javax.swing.*;
import java.awt.*;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;

public abstract class Sprite  {
    /*
    static variables
     */
    public static final int FIELD_COUNT = -1;
    public static final String IDENTIFIER = "sprite";

    private static HashMap<String,Object> resources = new HashMap<>();

    /*
    static methods
     */
    public static String toString(String[] values){
        String s = "";
        for(int i = 0; i < values.length; i++){
            if(i > 0)s+=":";
            s += values[i];
        }
        return s;
    }

    /**
     * Default add/remove/get resources
     * @param resourceName
     * @param resource
     */
    public static void addResource(String resourceName, Object resource){
        resources.put(resourceName,resource);
    }
    public static void removeResource(String resourceName){
        resources.remove(resourceName);
    }
    public static Object getResource(String resourceName){
        return resources.get(resourceName);
    }

    /**
     * Adds an image as a resource
     */
    public static void addImageResource(String resourceName, String imagePath){
        Image image = new ImageIcon(imagePath).getImage();
        addResource(resourceName,image);
    }

    /*
    abstract methods
     */
    public static void draw(String[] values, Graphics g){
        throw new IllegalStateException(
                "Must Override the Sprite method [public static void draw(String[] values, Graphics g)]");
    }

    /*
    class variables
     */
    private String resourceName;



}
