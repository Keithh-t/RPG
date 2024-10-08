package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;             // stores all types of tiles to be used
    public int[][] mapTileNum;      // represents the tiles at each part of the map


    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];            // 10 types of tiles(for now)
        mapTileNum = new int[gp.maxWorldCols][gp.maxWorldRows];

        getTileImage();

        // chooses map to load into
        loadMap("/maps/worldV2.txt");
    }

    // sets image for each tile and sets collisions
    public void getTileImage() {


        //placeholders
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        // tiles to be used
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", false);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);


    }
    public void setup (int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image =  uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    // reads map data from a file and fills array
    public void loadMap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRows; row++) {
                String line = br.readLine();
                String[] nums = line.split(" ");
                for (int col = 0; col < gp.maxWorldCols; col++) {
                    mapTileNum[col][row] = Integer.parseInt(nums[col]);
                }
            }
            br.close();

        } catch(Exception e) {

        }

    }

    // draws world map relative to players position in world
    public void draw(Graphics2D g2) {


        // col & row = worldcol & row
        for (int col = 0; col < gp.maxWorldCols; col++) {

            for (int row = 0; row < gp.maxWorldRows; row++) {
                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                int tileNum = mapTileNum[col][row];


                // draws only if visible on screen to save performance
                if (worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
                ){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }

            }


        }
    }

}
