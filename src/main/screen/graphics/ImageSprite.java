package main.screen.graphics;

import java.awt.*;

public class ImageSprite extends Sprite {

    public static final int X = 2;
    public static final int Y = 3;
    public static final int ROTATE = 4;
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int FIELD_COUNT = 5;
    public static final String IDENTIFIER = "png";

    private String name;
    public ImageSprite(String name){
        this.name = name;
    }

    public String getAsString(int x, int y, int rotation){
        return getAsString(name,x,y,rotation);
    }

    public static void draw(String[] values, Graphics g){
        int x = Integer.parseInt(values[X]);
        int y = Integer.parseInt(values[Y]);
        int r = Integer.parseInt(values[ROTATE]);

        g.drawImage((Image)(Sprite.getResource(values[NAME])),x,y,null);
    }

    public static String getAsString(String name,int x, int y, int rotation){
        String[] values = new String[FIELD_COUNT];
        values[ID] = IDENTIFIER;
        values[X] = x+"";
        values[Y] = y+"";
        values[ROTATE] = rotation+"";
        values[NAME] = name;

        return toString(values);
    }
}
