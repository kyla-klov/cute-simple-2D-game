package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 bit tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 12;
	public final int screenWidth = tileSize * getMaxScreenCol(); // 768 pixels
	public final int screenHeight = tileSize * getMaxScreenRow(); // 576 pixels
	
	public int getMaxScreenCol() {
		return maxScreenCol;
	}


	public int getMaxScreenRow() {
		return maxScreenRow;
	}
	
	// WORLD SETTINGS
	// these can be change to fit the game 
	public final int maxWorldCol = 34;
	public final int maxWorldRow = 21;
	public final int worldWidth  = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	// FPS
	int FPS = 60;
	
	// Instantiating the tile set
	TileManager tileM = new TileManager(this);

	// Instantiating the control keys
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	// Instantiating the player sprite
	public Player player = new Player(this, keyH);
	
	// Set player's default position
	int playerX = 200;
	int playerY = 200;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}


	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
//Game loop 1
			// METHOD 1: sleep method
			// To make the frames 60FPS
//	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS; // 0.0166667 seconds
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		
//		
//		while(gameThread != null) {	
//			
//			// 1 UPDATE: update information such as character positions
//			// if we make the update 30 then the character  can move 30 frame per second
//			// same if the game is 60fps and so on
//			update();
//			
//			// 2 DRAW: draw the screen with the updated information
//			repaint(); 
//			
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//	
//		
//	}
	
// Game loop 2 
		// METHOD 2: delta/accumulator method
		// To make the frames 60FPS
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			// Output the number of frames
			if(timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		
		player.update();
		tileM.update();
	}

	public void paintComponent(Graphics g) {
		
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			tileM.draw(g2); // Tile set must be called before the character sprite
			
			player.draw(g2); // Character sprite is called after and will not be under the tile set
			
			g2.dispose();
	}

}

