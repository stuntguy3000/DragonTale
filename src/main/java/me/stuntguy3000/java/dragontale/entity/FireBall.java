package me.stuntguy3000.java.dragontale.entity;

import me.stuntguy3000.java.dragontale.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FireBall extends MapObject {

    private boolean hit;
    private BufferedImage[] hitSprites;
    private boolean remove;
    private BufferedImage[] sprites;

    public FireBall(TileMap tileMap, boolean right) {
        super(tileMap);

        facingRight = right;

        moveSpeed = 3.8;

        if (right) {
            dX = moveSpeed;
        } else {
            dX = -moveSpeed;
        }

        width = 30;
        height = 30;
        cWidth = 14;
        cHeight = 14;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));

            sprites = new BufferedImage[4];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);

            }

            hitSprites = new BufferedImage[3];

            for (int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spriteSheet.getSubimage(i * width, height, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        setMapPosition();
        super.draw(graphics2D);
    }

    public void setHit() {
        if (hit) {
            return;
        }

        hit = true;

        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dX = 0;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        if (dX == 0 && !hit) {
            setHit();
        }

        animation.update();

        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }
}
    