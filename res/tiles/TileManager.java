package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	public List<int[][]> mapLayers; // List to hold multiple layers of map data
	int currentBottomLayerIndex = 0; // To toggle between two bottom layers
	int frameCounter = 0; // To count frames for switching maps
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[38]; // The number represent how many types of tiles there are
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow]; // Instantiate map tiles
		
		getTileImage(); //Ensures tiles are initialised here
		// Need to call the loadMap method
		//loadMap("/maps/map01.txt"); // Parameter her is the map path in case there are multiple maps that need to be loaded
		mapLayers = new ArrayList<>(); // Initialise the map layers list
        loadMap("/maps/bottom_ground_layer.txt");
        loadMap("/maps/ground_layer_1.txt"); // Load additional ground layer on top
        
	}

	public void getTileImage() {
	    String[] tilePaths = {
	        "/tiles/ground_1.png", // tile[0]
	        "/tiles/ground_2.png", // tile[1]
	        "/tiles/ground_3.png", // tile[2]
	        "/tiles/water_1.png", // tile[3]
	        "/tiles/water_2.png", // tile[4]
	        "/tiles/ground_bottom_left.png", // tile[5]
	        "/tiles/ground_bottom_right.png", // tile[6]
	        "/tiles/ground_bottom.png", // tile[7]
	        "/tiles/ground_inner_bottom_left.png", // tile[8]
	        "/tiles/ground_inner_bottom_right.png", // tile[9]
	        "/tiles/ground_inner_top_left.png", // tile[10]
	        "/tiles/ground_inner_top_right.png", // tile[11]
	        "/tiles/ground_left.png", // tile[12]
	        "/tiles/ground_right.png", // tile[13]
	        "/tiles/ground_top_left.png", // tile[14]
	        "/tiles/ground_top_right.png", // tile[15]
	        "/tiles/ground_top.png", // tile[16]
	        "/tiles/dirt_top_1.png", // tile[17]
	        "/tiles/dirt_top_2.png", // tile[18]
	        "/tiles/dirt_top_left_corner.png", // tile[19]
	        "/tiles/dirt_top_right_corner.png", // tile[20]
	        "/tiles/dirt_bottom_1.png", // tile[21]
	        "/tiles/dirt_bottom_2.png", // tile[22]
	        "/tiles/dirt_bottom_left_corner.png", // tile[23]
	        "/tiles/dirt_bottom_right_corner.png", // tile[24]
	        "/tiles/dirt_left_1.png", // tile[25]
	        "/tiles/dirt_left_2.png", // tile[26]
	        "/tiles/dirt_right_1.png", // tile[27]
	        "/tiles/dirt_right_2.png", // tile[28]
	        "/tiles/hill_bottom_left.png", // tile[29]
	        "/tiles/hill_bottom_right.png", // tile[30]
	        "/tiles/hill_bottom.png", // tile[31]
	        "/tiles/hill_center.png", // tile[32]
	        "/tiles/hill_left.png", // tile[33]
	        "/tiles/hill_right.png", // tile[34]
	        "/tiles/hill_top_left.png", // tile[35]
	        "/tiles/hill_top_right.png", // tile[36]
	        "/tiles/hill_top.png", // tile[37]

	    };

	    for (int i = 0; i < tilePaths.length; i++) {
	    	// Ensure the Tile object is initialised at this index
	        if (tile[i] == null) {
	            tile[i] = new Tile();
	        }
	        // Load the tile image
	        try {
	            tile[i].image = ImageIO.read(getClass().getResourceAsStream(tilePaths[i]));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // Set collision properties for specific tiles
	    // It's safer to set collision after confirming tiles are initialised and loaded
	    int[] collisionTiles = {29, 30, 31, 32, 33, 34, 35, 36, 37};
	    for (int tileIndex : collisionTiles) {
	        if (tile[tileIndex] != null) { // Check if the tile object exists before accessing it
	            tile[tileIndex].collision = true;
	        }
	    }
	        
	}

//	private void loadTileImage(int index, String path) {
//	    try {
//	        tile[index] = new Tile();
//	        tile[index].image = ImageIO.read(getClass().getResourceAsStream(path));
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
//	
	
    public void update() {
        frameCounter++;
        if (frameCounter >= 30) {
            currentBottomLayerIndex = (currentBottomLayerIndex + 1) % 2; // Toggle between 0 and 1
            frameCounter = 0; // Reset frame counter
        }
    }


	/** To increase the games performance, the tiles that are only visible
	 * on the screen are loaded which cuts out extra processing.
     * This is especially helpful if u have a really big map.
     * To check if this is working then just remove the + or - 1
     * from screenXStart, screenYStart, screenXEnd, screenYEnd.
     */
	
	public void draw(Graphics2D g2) {
	    // Calculate visible area
	    int screenXStart = (gp.player.worldX - gp.player.screenX) / gp.tileSize;
	    int screenYStart = (gp.player.worldY - gp.player.screenY) / gp.tileSize;
	    int screenXEnd = screenXStart + gp.screenWidth / gp.tileSize;
	    int screenYEnd = screenYStart + gp.screenHeight / gp.tileSize;

	    // Draw base water layer across the entire visible area
	    drawBaseWaterLayer(g2, screenXStart, screenYStart, screenXEnd, screenYEnd);

	    // Draw map layers on top of the base water layer
	    for (int[][] layer : mapLayers) {
	        drawLayer(g2, layer, screenXStart, screenYStart, screenXEnd, screenYEnd);
	    }
	}

	private void drawBaseWaterLayer(Graphics2D g2, int screenXStart, int screenYStart, int screenXEnd, int screenYEnd) {
	    // Ensure that base water layer covers entire visible area plus one tile buffer around it to avoid empty edges
	    screenXStart -= 1;
	    screenYStart -= 1;
	    screenXEnd += 1;
	    screenYEnd += 1;

	    for (int worldRow = screenYStart; worldRow <= screenYEnd; worldRow++) {
	        for (int worldCol = screenXStart; worldCol <= screenXEnd; worldCol++) {
	            int waterTileIndex = (currentBottomLayerIndex == 0) ? 3 : 4; // Alternating between water_1 and water_2
	            int worldX = worldCol * gp.tileSize;
	            int worldY = worldRow * gp.tileSize;
	            int screenX = worldX - gp.player.worldX + gp.player.screenX;
	            int screenY = worldY - gp.player.worldY + gp.player.screenY;

	            // Draw the water tile
	            if (waterTileIndex >= 0 && waterTileIndex < tile.length) {
	                g2.drawImage(tile[waterTileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	            }
	        }
	    }
	}


	// Modified drawLayer to only draw within the visible area
	private void drawLayer(Graphics2D g2, int[][] layer, int screenXStart, int screenYStart, int screenXEnd, int screenYEnd) {
	    for (int worldRow = screenYStart; worldRow <= screenYEnd; worldRow++) {
	        for (int worldCol = screenXStart; worldCol <= screenXEnd; worldCol++) {
	            if (worldCol >= 0 && worldCol < gp.maxWorldCol && worldRow >= 0 && worldRow < gp.maxWorldRow) {
	                int tileNum = layer[worldCol][worldRow];
	                int worldX = worldCol * gp.tileSize;
	                int worldY = worldRow * gp.tileSize;
	                int screenX = worldX - gp.player.worldX + gp.player.screenX;
	                int screenY = worldY - gp.player.worldY + gp.player.screenY;

	                if (tileNum >= 0 && tileNum < tile.length) {
	                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	                }
	            }
	        }
	    }
	}
    

	public void loadMap(String filePath) {
	    try {
	        InputStream is = getClass().getResourceAsStream(filePath);
	        if (is == null) {
	            System.err.println("Map file not found: " + filePath);
	            return; // Exit if file not found
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        // Initialise the layer with -1 for each tile
	        int[][] layer = new int[gp.maxWorldCol][gp.maxWorldRow];
	        for (int i = 0; i < gp.maxWorldCol; i++) {
	            for (int j = 0; j < gp.maxWorldRow; j++) {
	                layer[i][j] = -1; // Set default value to -1
	            }
	        }

	        int row = 0;
	        String line;
	        while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
	            String[] numbers = line.split(" ");
	            for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
	                layer[col][row] = Integer.parseInt(numbers[col]);
	            }
	            row++;
	        }

	        mapLayers.add(layer);
	        br.close();
	    } catch (Exception e) {
	        System.err.println("Error loading map: " + filePath);
	        e.printStackTrace();
	    }
	}


}
