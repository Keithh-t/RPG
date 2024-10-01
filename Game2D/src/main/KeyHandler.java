package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }


    // debug
    boolean checkDrawTime;

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if (gp.gameState == gp.titleState) {

            // CHECKING TITLE SCREEN SUBSTATES
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum == 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum == -1) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    // Character selection
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }
                    // LOAD GAME
                    if (gp.ui.commandNum == 1) {
                        // ADD LATER
                    }
                    // QUIT GAME
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);

                    }


                }
            }

            else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum == 4) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum == -1) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    // START GAME as fighter
                    if (gp.ui.commandNum == 0) {
                        // could add fighter stuff
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    // mage
                    if (gp.ui.commandNum == 1) {
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    // theif
                    if (gp.ui.commandNum == 2) {
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }

                    // back to og screen
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                    }

                }
            }


        }

        //PLAY STATE
        if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }

            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            // pause
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;

            }

            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }

        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            // pause
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }

            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }

        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }

    }




    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;

        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;

        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;

        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;

        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }

    }

}
