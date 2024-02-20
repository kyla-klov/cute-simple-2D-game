package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		// These will give the halfway point of the game screen
		// AND because these are final variables then they can't change so that the camera can follow the character
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.height = 32;
		solidArea.width = 32;
		
		setDefualtValues();
		getPlayerImage();
	}
	
	public void setDefualtValues() {
		worldX = gp.tileSize * 13;
		worldY = gp.tileSize * 11;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		
		try {
			// All moving animations 
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_right_2.png"));
			
			// All resting animations
			rest_up1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_up_1.png"));
			rest_up2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_up_2.png"));
			rest_down1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_1.png"));
			rest_down2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_2.png"));
			rest_left1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_left_1.png"));
			rest_left2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_left_2.png"));
			rest_right1 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_right_1.png"));
			rest_right2 = ImageIO.read(getClass().getResourceAsStream("/player/bunny_rest_right_2.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
	    // This method gets called 60 times per second

	    // Determine if the player is moving diagonally
	    boolean isMovingDiagonally = (keyH.upPressed || keyH.downPressed) && (keyH.leftPressed || keyH.rightPressed);
	    // Calculate the normalised speed for diagonal movement
	    double normalizedSpeed = isMovingDiagonally ? (speed / Math.sqrt(2)) * 1.07 : speed; // the 1.07 increases the diagonal speed by 7%
	    
	 // Store the original position
	    int originalX = worldX;
	    int originalY = worldY;

	    // Predict movement
	    if(keyH.upPressed) {
	        worldY -= normalizedSpeed;
	    }
	    if(keyH.downPressed) {
	        worldY += normalizedSpeed;
	    }
	    if(keyH.leftPressed) {
	        worldX -= normalizedSpeed;
	    }
	    if(keyH.rightPressed) {
	        worldX += normalizedSpeed;
	    }
	    
	    // Check for collision with the new position
	    collisionOn = false;
	    gp.cChecker.CheckTile(this);

	    // Revert to original position if collision is detected
	    if(collisionOn) {
	        worldX = originalX;
	        worldY = originalY;
	    } else {
	        // Update direction only if movement is allowed
	        if(keyH.upPressed) {
	            direction = "up";
	        }
	        if(keyH.downPressed) {
	            direction = "down";
	        }
	        if(keyH.leftPressed) {
	            direction = "left";
	        }
	        if(keyH.rightPressed) {
	            direction = "right";
	        }
	    }

	    // Handle sprite animation  based on movement
	    handleSpriteAnimation();
	}

	private void handleSpriteAnimation() {
	    if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
	        spriteCounter++; // Every frame increase this counter
	        if(spriteCounter > 15) { // When counter reaches 15, it will change the sprite, therefore, player sprite animation is every 15 frames
	            spriteNum = spriteNum == 1 ? 2 : 1;
	            spriteCounter = 0;
	        }
	    } else {
	        // Reset sprite to default ANIMATED state if not moving
	    	spriteCounter++;
	        if(spriteCounter > 25) {
	            spriteNum = spriteNum == 1 ? 2 : 1;
	            spriteCounter = 0;
	        }
	    }
	}



	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white); // set colour for the rectangle we are going to draw
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize); //draw rectangle with x, y, width, height
	
		BufferedImage image = null;

	    // Determine if the sprite is moving based on spriteNum and direction
	    boolean isMoving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

	    switch (direction) {
	        case "up":
	            if (isMoving) {
	                image = (spriteNum == 1) ? up1 : up2;
	            } else {
	                image = (spriteNum == 1) ? rest_up1 : rest_up2;
	            }
	            break;
	        case "down":
	            if (isMoving) {
	                image = (spriteNum == 1) ? down1 : down2;
	            } else {
	                image = (spriteNum == 1) ? rest_down1 : rest_down2;
	            }
	            break;
	        case "left":
	            if (isMoving) {
	                image = (spriteNum == 1) ? left1 : left2;
	            } else {
	                image = (spriteNum == 1) ? rest_left1 : rest_left2;
	            }
	            break;
	        case "right":
	            if (isMoving) {
	                image = (spriteNum == 1) ? right1 : right2;
	            } else {
	                image = (spriteNum == 1) ? rest_right1 : rest_right2;
	            }
	            break;
	    }

	    // Draw the determined image
	    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

	}
}
