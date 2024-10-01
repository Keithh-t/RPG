package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }


    public void checkTile(Entity entity) {
        int left = entity.worldX + entity.hitbox.x;
        int right = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int up = entity.worldY + entity.hitbox.y;
        int down = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        int colLeft = left/gp.tileSize;
        int colRight = right/gp.tileSize;
        int rowUp = up/gp.tileSize;
        int rowDown = down/gp.tileSize;

        String direction = entity.direction;

        int tile1, tile2;

        switch(direction) {
            case "up":
                rowUp = (up - entity.speed)/gp.tileSize;
                tile1 = gp.tileM.mapTileNum[colLeft][rowUp];
                tile2 = gp.tileM.mapTileNum[colRight][rowUp];

                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "down":
                rowDown = (down + entity.speed)/gp.tileSize;
                tile1 = gp.tileM.mapTileNum[colLeft][rowDown];
                tile2 = gp.tileM.mapTileNum[colRight][rowDown];

                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "left":
                colLeft = (left - entity.speed)/gp.tileSize;
                tile1 = gp.tileM.mapTileNum[colLeft][rowUp];
                tile2 = gp.tileM.mapTileNum[colLeft][rowDown];

                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "right":
                colRight = (right + entity.speed)/gp.tileSize;
                tile1 = gp.tileM.mapTileNum[colRight][rowUp];
                tile2 = gp.tileM.mapTileNum[colRight][rowDown];

                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    // checks if hitboxes of all objects intersect with entity
    public int checkObject (Entity entity, boolean player) {

        int index = -1;
        for (int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] == null)
                continue;

            // check entity hitbox
            entity.hitbox.x = entity.worldX + entity.hitbox.x;
            entity.hitbox.y = entity.worldY + entity.hitbox.y;

            // object hitbox
            gp.obj[i].hitbox.x = gp.obj[i].worldX + gp.obj[i].hitbox.x;
            gp.obj[i].hitbox.y = gp.obj[i].worldY + gp.obj[i].hitbox.y;


            switch (entity.direction) {
                case "up":
                    entity.hitbox.y -= entity.speed;
                    if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
                       if(gp.obj[i].collision) {
                           entity.collisionOn = true;

                       }
                       if (player) {
                           index = i;
                       }
                }
                    break;
                case "down":
                    entity.hitbox.y += entity.speed;
                    if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
                        if(gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "left":
                    entity.hitbox.x -= entity.speed;
                    if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
                        if(gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "right":
                    entity.hitbox.x += entity.speed;
                    if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
                        if(gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
            }

            entity.hitbox.x = entity.hitboxDefaultX;
            entity.hitbox.y = entity.hitboxDefaultY;
            gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
            gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;


        }

        return index;

    }

    // npc or monster collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = -1;
        for (int i = 0; i < target.length; i++) {
            if(target[i] == null)
                continue;

            // check entity hitbox
            entity.hitbox.x = entity.worldX + entity.hitbox.x;
            entity.hitbox.y = entity.worldY + entity.hitbox.y;

            // target hitbox
            target[i].hitbox.x = target[i].worldX + target[i].hitbox.x;
            target[i].hitbox.y = target[i].worldY + target[i].hitbox.y;


            switch (entity.direction) {
                case "up":
                    entity.hitbox.y -= entity.speed;
                    if (entity.hitbox.intersects(target[i].hitbox)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "down":
                    entity.hitbox.y += entity.speed;
                    if (entity.hitbox.intersects(target[i].hitbox)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "left":
                    entity.hitbox.x -= entity.speed;
                    if (entity.hitbox.intersects(target[i].hitbox)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "right":
                    entity.hitbox.x += entity.speed;
                    if (entity.hitbox.intersects(target[i].hitbox)) {
                        entity.collisionOn = true;
                        index = i;

                    }
                    break;
            }


            entity.hitbox.x = entity.hitboxDefaultX;
            entity.hitbox.y = entity.hitboxDefaultY;
            target[i].hitbox.x = target[i].hitboxDefaultX;
            target[i].hitbox.y = target[i].hitboxDefaultY;

        }

        return index;

    }

    // I CHANGED TO BOOLEAN INSTEAD OF VOID TO HANDLE SPEAK
    public boolean checkPlayer(Entity entity) {

        boolean hitPlayer = false;
        // check entity hitbox
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;

        // player hitbox
        gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;


        switch (entity.direction) {
            case "up":
                entity.hitbox.y -= entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.hitbox.y += entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.hitbox.x -= entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.hitbox.x += entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
        }

        // added lines to check to speak
        gp.player.interactBox.x = gp.player.worldX;
        gp.player.interactBox.y = gp.player.worldY;
        entity.interactBox.x = entity.worldX;
        entity.interactBox.y = entity.worldY;
        if (entity.interactBox.intersects(gp.player.interactBox)) {
            hitPlayer = true;
        }



        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        gp.player.hitbox.x = gp.player.hitboxDefaultX;
        gp.player.hitbox.y = gp.player.hitboxDefaultY;

        return hitPlayer;

    }





}
