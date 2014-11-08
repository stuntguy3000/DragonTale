package me.stuntguy3000.java.dragontale.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion {
    private Animation animation;
    private int height;
    private boolean remove;
    private BufferedImage[] sprites;
    private int width;
    private int x;
    private int xMap;
    private int y;
    private int yMap;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));

            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(animation.getImage(), x + xMap - width / 2, y + yMap - height / 2, null);
    }

    public void setMapPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update() {
        animation.update();

        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }
}
    