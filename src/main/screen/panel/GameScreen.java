package main.screen.panel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GameScreen extends JFrame {



	private GamePanel game;
	public GameScreen(){
		initComponents();
	}

	private void initComponents(){
		game = new GamePanel();

		game.setBackground(new Color(255, 255, 255));
		game.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


		// add the component to the frame to see it!
		this.setContentPane(game);
		// be nice to testers..
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();

	}
}
