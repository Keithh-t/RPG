package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font appleChancery, papyrus, baskerville_40, baskerville_80;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";
    int commandNum = 0;
    public int titleScreenState = 0;        // creating sub states in title screen

    public boolean gameFinished = false;
    double playTime;
    DecimalFormat df = new DecimalFormat("#0.00");


    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Apple Chancery.ttf");
            appleChancery = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Papyrus.ttf");
            papyrus = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        baskerville_40 = new Font("Baskerville", Font.PLAIN, 40);
        baskerville_80 = new Font("Baskerville", Font.BOLD, 80);

        // Create HUD Object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;


    }

    public void showMessage (String text) {
        message = text;
        messageOn = true;

    }

    public void draw (Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(papyrus);
        g2.setColor(Color.WHITE);


        //TITLE STATE STUFF
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // play state stuff
        if (gp.gameState == gp.playState) {
            drawPlayerLife();

        }
        // pause state stuff
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }

        // dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
            drawPlayerLife();
        }

    }

    public void drawTitleScreen() {

        // CHECK TITLE SCREEN SUBSTATE
        if (titleScreenState == 0) {
            g2.setColor(new Color(70, 90, 110));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
            String text = "BTEC";
            int x = getCenteredX(text);
            int y = gp.tileSize * 3;

            //shadow
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
            //main text
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //image to display(if any)
            x = gp.screenHeight / 2 + gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);


            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            text = "NEW GAME";
            x = getCenteredX(text);
            y += gp.tileSize * 4;
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.drawString(text, x, y);
            text = "LOAD GAME";
            x = getCenteredX(text);
            y += gp.tileSize;
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.drawString(text, x, y);
            text = "QUIT";
            x = getCenteredX(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        // next substate.... balnk canvas for later ideas like character class selection
        if (titleScreenState == 1) {
            // class selection
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Choose your class";
            int x = getCenteredX(text);
            int y = gp.tileSize* 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getCenteredX(text);
            y += gp.tileSize* 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Mage";
            x = getCenteredX(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Bandit";
            x = getCenteredX(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getCenteredX(text);
            y += gp.tileSize* 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

    }

    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;

        // draw blank hearts to represent max
        for (int i = 0; i < gp.player.maxLife/2; i++) {
            g2.drawImage(heart_blank,x ,y, null);
            x += gp.tileSize;
        }

        // draw current life
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        for (int i = 0; i < gp.player.life; i++) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            x += gp.tileSize;
        }



    }

    public void drawPauseScreen () {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "Game Paused";
        int x = getCenteredX(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);

    }

    public void drawDialogueScreen() {
        //Window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 3;
        drawWindow(x,y,width,height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += gp.tileSize*2/3;
        }


    }
    public void drawWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y + 5, width-10, height-10, 35, 35);
    }

    public int getCenteredX (String text) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        return x;
    }

}
