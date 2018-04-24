package main.screen.graphics;

import java.awt.*;

public class RectSprite extends Sprite {

    public static final int OUTLINE_COLOR = 7;
    public static final int FILL_COLOR = 6;
    public static final int X = 2;
    public static final int Y = 3;
    public static final int WIDTH = 4;
    public static final int HEIGHT = 5;
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int FIELD_COUNT = 8;
    public static final String IDENTIFIER = "rect";



    public static void draw(String[] values, Graphics g) {
        Color outlineColor = new Color(Integer.parseInt(values[OUTLINE_COLOR]));
        Color fillColor = new Color(Integer.parseInt(values[FILL_COLOR]));
        int x = Integer.parseInt(values[X]);
        int y = Integer.parseInt(values[Y]);
        int w = Integer.parseInt(values[WIDTH]);
        int h = Integer.parseInt(values[HEIGHT]);

        Color originalColor = g.getColor();
        g.setColor(fillColor);
        g.fillRect(x,y,w,h);
        g.setColor(outlineColor);
        g.drawRect(x,y,w,h);
        g.setColor(originalColor);
    }

    private String name;
    private int lineColor;
    private int fillColor;
    private int width;
    private int height;
    public RectSprite(String name,Color line, Color fill){
        this.name = name;
        lineColor = line.getRGB();
        fillColor = fill.getRGB();
        width = 25;
        height = 25;
    }

    public String getAsString(int x, int y){
        return getAsString(name,lineColor,fillColor,x,y,width,height);
    }

    public static String getAsString(String name,int outLineColor,int fillColor,int x, int y, int w, int h){
        String[] values = new String[FIELD_COUNT];
        values[ID] = IDENTIFIER;
        values[X] = x+"";
        values[Y] = y+"";
        values[WIDTH] = w+"";
        values[HEIGHT] = h+"";
        values[OUTLINE_COLOR] = outLineColor+"";
        values[FILL_COLOR] = fillColor+"";
        values[NAME] = name;

        return toString(values);
    }
}
