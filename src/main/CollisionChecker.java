package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    public void CheckTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int leftCol = entityLeftWorldX / gp.tileSize;
        int rightCol = (entityRightWorldX - 1) / gp.tileSize;
        int topRow = entityTopWorldY / gp.tileSize;
        int bottomRow = (entityBottomWorldY - 1) / gp.tileSize;

        // First, check for empty tile collision on the bottom layer
        int[][] bottomLayer = gp.tileM.mapLayers.get(0); // Assuming the bottom layer is the first in the list
        if (isEmptyTile(bottomLayer, leftCol, topRow) || isEmptyTile(bottomLayer, rightCol, topRow)
            || isEmptyTile(bottomLayer, leftCol, bottomRow) || isEmptyTile(bottomLayer, rightCol, bottomRow)) {
            entity.collisionOn = true;
            return; // Collision detected with empty tile on bottom layer
        }

        // Then, check other layers for collision tiles
        for (int layerIndex = gp.tileM.mapLayers.size() - 1; layerIndex >= 1; layerIndex--) { // Start from top layer
            int[][] layer = gp.tileM.mapLayers.get(layerIndex);
            if (isCollisionTile(layer, leftCol, topRow) || isCollisionTile(layer, rightCol, topRow)
                || isCollisionTile(layer, leftCol, bottomRow) || isCollisionTile(layer, rightCol, bottomRow)) {
                entity.collisionOn = true;
                return; // Collision detected with a tile
            }
        }

        entity.collisionOn = false; // No collision detected
    }

    private boolean isEmptyTile(int[][] layer, int col, int row) {
        // Check if the tile is empty (-1) in the bottom layer
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            int tileNum = layer[col][row];
            return tileNum == -1; // Empty tile
        }
        return false;
    }

    private boolean isCollisionTile(int[][] layer, int col, int row) {
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            int tileNum = layer[col][row];
            return tileNum >= 0 && tileNum < gp.tileM.tile.length && gp.tileM.tile[tileNum].collision;
        }
        return false;
    }

}
