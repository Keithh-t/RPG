package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    // loads gamepanel

    // keyhandler to accept inputs
    KeyHandler keyhandler;

    // location of player on screen
    public final int screenX;
    public final int screenY;


    // constructor initializes gp and keyhandler
    public Player (GamePanel gp, KeyHandler keyhandler) {
        super(gp);

        this.keyhandler = keyhandler;

        // centers player on screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // x, y ,width, height of hitbox
        hitbox = new Rectangle(8, 16, 32, 32);

        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        // methods to set default world positioning of player and obtain image
        setDefaultVals();
        getPlayerImage();
    }

    // setting default world values
    public void setDefaultVals() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //player status
        maxLife = 8;
        life = maxLife;
    }

    // loads images for player directions
    public void getPlayerImage() {

        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }

    // moves player based on input and collisions, counter for alternating images
    public void update() {

        if (keyhandler.upPressed || keyhandler.downPressed ||
                keyhandler.leftPressed || keyhandler.rightPressed) {

            if (keyhandler.upPressed) {
                direction = "up";
                spriteCounter++;
            } else if (keyhandler.downPressed) {
                direction = "down";
                spriteCounter++;
            } else if (keyhandler.leftPressed) {
                direction = "left";
                spriteCounter++;
            } else if (keyhandler.rightPressed) {
                direction = "right";
                spriteCounter++;
            }

            if (spriteCounter > 12) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }

            collisionOn = false;
            // check tile collision
            gp.collisionC.checkTile(this);
            // check object collision
            int objIndex = gp.collisionC.checkObject(this, true);
            interactObj(objIndex);

            // check npc collision
            int npcIndex = gp.collisionC.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // check for collision then move
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        else {
            standCounter++;
            if (standCounter == 10) {
                spriteNum = 1;
                standCounter = 0;
            }
        }


    }


    public void interactObj(int i) {

        if (i == -1)
            return;

    }

    public void interactNPC(int i) {

        if (i == -1) {
            return;
        }

        if (gp.keyhandler.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }


    }



    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // changes image based on direction and counter
        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);




    }
}
