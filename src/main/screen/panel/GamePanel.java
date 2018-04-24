package main.screen.panel;

import main.comm.serverIO.client.receive.ClientReceiveListener;
import main.comm.serverIO.client.receive.ClientReceiverThread;
import main.screen.graphics.ImageSprite;
import main.screen.graphics.RectSprite;
import main.utils.GameUtil;
import main.utils.SyncObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener,ClientReceiveListener {

	private static final long serialVersionUID = 7233884509567756146L;
	private String paintString = "paint";


	ActionListener timerHelp = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
            //System.out.println(dustHash.size());

            repaint();
        }
	};
	Timer gameTimer;
	long startTime;


	public GamePanel(){


		gameTimer = new Timer(1000/ GameUtil.FRAME_RATE, timerHelp);

	    addMouseListener(new MyMouseListener());
	    addMouseMotionListener(new MyMouseListener());
	    try {
			Socket s = new Socket("192.168.1.4",8080);
			Thread receive = new Thread(new ClientReceiverThread(s,this));
			receive.start();
		}catch (Exception e){
	    	e.printStackTrace();
		}

		setFont(new Font("Arial", Font.BOLD, 12));
	    System.out.println("Screen opened");

	    startTime = System.currentTimeMillis();
	    gameTimer.start();

	    addKeyListener(this);
	    setFocusable(true);




        setPreferredSize(new Dimension(690,690));

	}


	public void handle(String pString){
	       paintString = pString;

    }


    private String getPaintString(){
        return paintString;
    }


	/**
	 * Paints the GUI
	 */
	@Override
	public void paintComponent(Graphics g){


		g.drawImage(GameUtil.SUMO_RING, 0, 0, null);
		g.setColor(Color.white);

		for(String obj : paintString.split(";")){
			try {
				String split[] = obj.split(":");

				if (split[0].equals("string")) {
					int x = Integer.parseInt(split[2]);
					int y = Integer.parseInt(split[3]);
					g.drawString(split[1], x, y);
				}
				if(split[0].equals(RectSprite.IDENTIFIER)){
					RectSprite.draw(split,g);
				}else if(split[0].equals(ImageSprite.IDENTIFIER)){
					ImageSprite.draw(split,g);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
//		Color dTemp = g.getColor();
//		g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(), 20));
//		g.drawString(name1+" : "+p1score, (GameUtil.mapSize/2)-((name1+":"+p1score).length()*3), 200);
//		g.drawString("Press \'P\' exit", 10, GameUtil.mapSize-20);
//		g.setColor(dTemp);


	}

	public class MyMouseListener implements MouseListener, MouseMotionListener{
		public void mouseDragged(MouseEvent e){

		}

		public void mouseMoved(MouseEvent e){

		}

	    @Override
	    public void mouseClicked(MouseEvent e){

	    }

	    @Override
	    public void mousePressed(MouseEvent e){

	    }


	    @Override
	    public void mouseReleased(MouseEvent e){

	    }

	    @Override
	    public void mouseEntered(MouseEvent e){

	    }

	    @Override
	    public void mouseExited(MouseEvent e){

	    }
	  }
	@Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == 80 ) {

        }
    }

}
