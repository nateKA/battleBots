package main.drivers;

import main.screen.graphics.Sprite;
import main.screen.panel.GameScreen;
import main.utils.SpriteHouse;

import java.awt.*;

public class GamePanelDriver {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SpriteHouse.initSprites();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameScreen frame = new GameScreen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
