package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // screen settings
    final int originalTileSize = 16;       // to construct 16*16 tiles
    final int scale = 3;                    // scale tiles by 3 to fit on larger screens
    public final int tileSize = originalTileSize*scale;

    public final int maxScreenCols = 16;
    public final int maxScreenRows = 12;

    public final int screenWidth = maxScreenCols*tileSize;
    public final int screenHeight = maxScreenRows*tileSize;


    // world settings

    public final int maxWorldCols = 50;
    public final int maxWorldRows = 50;


    // system
    Thread gameThread;      // keeps it running
    public KeyHandler keyhandler = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    public CollisionCheck collisionC = new CollisionCheck(this);
    public AssetPlace setAsset = new AssetPlace(this);
    Sound music = new Sound();
    Sound effects = new Sound();
    public UI ui =  new UI(this);

    //entity and object
    public Player player = new Player(this, keyhandler);
    public SuperObject[] obj = new SuperObject[10]; // how many objects can be displayed at once
    public Entity[] npc = new Entity[10];

    // Game State
    public int titleState = 0;
    public int gameState = titleState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    int fps = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyhandler);
        setFocusable(true);
    }

    public void setGame() {
        setAsset.setObject();
        setAsset.setNPC();
        // playMusic(0);
        gameState = titleState;

    }



    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();



    }


    public void run() {
        // 1 bil nanoseconds in a second
        double drawInterval = 1000000000.0 /fps;
        long startTime = System.nanoTime();
        double delta = 0;


        // game loop to paint at desired fos
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - startTime) / drawInterval;
            startTime = currentTime;
            if (delta >= 1) {
                // we want to update info
                update();

                repaint();      // this is how we call paint component
                delta--;
            }

        }
    }

    public void update() {
        if (gameState == playState) {
            // player
            player.update();
            //npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

        }
        if (gameState == pauseState) {


        }



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //debug checks
        long drawStart = 0;
        if (keyhandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE STATE
        if (gameState == titleState) {
            ui.draw(g2);
        }

        //EVERYTHING ELSE FOR NOW
        else {

            // draw tiles before players and entities for correct overlay
            tileM.draw(g2);

            //object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }

            }

            //npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }

            }


            // player
            player.draw(g2);

            //ui
            ui.draw(g2);


        }

        //debug
        if (keyhandler.checkDrawTime) {
            long drawStop = System.nanoTime();
            long passed = drawStop - drawStart;

            g2.drawString("draw time = " + passed, 10,400);
            System.out.println("passed = " + passed);
        }

        g2.setColor(Color.WHITE);



        g2.dispose();

    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        effects.setFile(i);
        effects.play();
    }



}
