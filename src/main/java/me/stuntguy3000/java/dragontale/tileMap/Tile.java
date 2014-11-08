package me.stuntguy3000.java.dragontale.tileMap;

import java.awt.image.BufferedImage;

public class Tile {
    public static final int BLOCKED = 1;
    public static final int NORMAL = 0;
    private BufferedImage image;
    private int type;

    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
    