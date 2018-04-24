package main.utils;

import main.screen.graphics.ImageSprite;
import main.screen.graphics.RectSprite;
import main.screen.graphics.Sprite;

import javax.swing.*;
import java.awt.*;

public class SpriteHouse {
    public static final RectSprite blueRect = new RectSprite("blue",new Color(0,0,0),new Color(0,0,255));
    public static final RectSprite redRect = new RectSprite("red",new Color(0,0,0),new Color(255,0,0));
    public static final ImageSprite sumo = new ImageSprite("sumo");
    public static final ImageSprite sumoBlue = new ImageSprite("sumoBlue");

    public static void initSprites(){
        Sprite.addImageResource("sumo","./src/main/resources/pics/Sumoio.png");
        Sprite.addImageResource("sumoBlue","./src/main/resources/pics/sumoBlue.png");
    }
}
