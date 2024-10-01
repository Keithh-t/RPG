package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity {


    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();

    }
    // loads images for npc directions
    public void getImage() {

        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");

    }

    public void setDialogue() {
        dialogue[0] = "Hello, young one";
        dialogue[1] = "I see you're a new face \naround these parts";
        dialogue[2] = "Let me just tell you, keys \nare the key to success around here";
        dialogue[3] = "See what I did there..... hehe";
        dialogue[4] = "Good luck on your quest";


    }





    // sets characters behaviour
    public void setAction() {
        actionCounter++;
        if (actionCounter < 120)
            return;
        //Random random = new Random();
        //int i = random.nextInt(100) + 1; //random number between 1 and 100
        double j = Math.random()*100;

        if (j <= 25) {
            direction = "up";
        }
        else if (j <= 50) {
            direction = "down";
        }
        else if (j <= 75) {
            direction = "left";
        }
        else {
            direction = "right";
        }
        actionCounter = 0;


    }

    public void speak() {
        super.speak();

    }


}
