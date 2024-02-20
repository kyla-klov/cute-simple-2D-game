package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// entity class will be the super class for the player, monster and NPC classes

public class Entity {

	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, rest_down1, rest_down2, rest_up1, rest_up2, rest_left1, rest_left2, rest_right1, rest_right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
}
