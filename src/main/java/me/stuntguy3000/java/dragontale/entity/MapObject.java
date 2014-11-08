package me.stuntguy3000.java.dragontale.entity;

import me.stuntguy3000.java.dragontale.GamePanel;
import me.stuntguy3000.java.dragontale.tileMap.Tile;
import me.stuntguy3000.java.dragontale.tileMap.TileMap;

import java.awt.*;

public abstract class MapObject {
    protected Animation animation;
    protected boolean bottomLeft;
    protected boolean bottomRight;
    protected int cHeight;
    protected int cWidth;
    protected int currentAction;
    protected int currentCol;
    protected int currentRow;
    protected double dX;
    protected double dY;
    protected boolean down;
    protected boolean facingLeft;
    protected boolean facingRight;
    protected double fallSpeed;
    protected boolean falling;
    protected int height;
    protected double jumpStart;
    protected boolean jumping;
    protected boolean left;
    protected double maxFallSpeed;
    protected double maxSpeed;
    protected double moveSpeed;
    protected int previousAction;
    protected boolean right;
    protected double stopJumpSpeed;
    protected double stopSpeed;
    protected TileMap tileMap;
    protected int tileSize;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean up;
    protected int width;
    protected double x;
    protected double xDestination;
    protected double xMap;
    protected double xTemp;
    protected double y;
    protected double yDestination;
    protected double yMap;
    protected double yTemp;

    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        this.tileSize = tileMap.getTileSize();
    }

    public void calculateCorners(double x, double y) {
        int leftTile = (int) (x - cWidth / 2) / tileSize;
        int rightTile = (int) (x + cWidth / 2 - 1) / tileSize;
        int topTile = (int) (y - cHeight / 2) / tileSize;
        int bottomTile = (int) (y + cHeight / 2 - 1) / tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }

    public void checkTileMapCollision() {
        currentCol = (int) x / tileSize;
        currentRow = (int) y / tileSize;

        xDestination = x + dX;
        yDestination = y + dY;

        xTemp = x;
        yTemp = y;

        calculateCorners(x, yDestination);
        if (dY < 0) {
            if (topLeft || topRight) {
                dY = 0;
                yTemp = currentRow * tileSize + cHeight / 2;
            } else {
                yTemp += dY;
            }
        } else if (dY > 0) {
            if (bottomLeft || bottomRight) {
                dY = 0;
                falling = false;
                yTemp = (currentRow + 1) * tileSize - cHeight / 2;
            } else {
                yTemp += dY;
            }
        }

        calculateCorners(xDestination, y);
        if (dX < 0) {
            if (topLeft || bottomLeft) {
                dX = 0;
                xTemp = currentCol * tileSize + cWidth / 2;
            } else {
                xTemp += dX;
            }
        } else if (dX > 0) {
            if (topRight || bottomRight) {
                dX = 0;
                xTemp = (currentCol + 1) * tileSize - cWidth / 2;
            } else {
                xTemp += dX;
            }
        }

        if (!falling) {
            calculateCorners(x, yDestination + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        if (facingRight) {
            graphics2D.drawImage(animation.getImage(), (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
        } else {
            graphics2D.drawImage(animation.getImage(), (int) (x + xMap - width / 2 + width), (int) (y + yMap - height / 2), -width, height, null);
        }
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) x - cWidth, (int) y - cHeight, cWidth, cHeight);
    }

    public int getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getcHeight() {
        return cHeight;
    }

    public int getcWidth() {
        return cWidth;
    }

    public boolean intersects(MapObject mapObject) {
        Rectangle rectangle1 = getRectangle();
        Rectangle rectangle2 = mapObject.getRectangle();

        return rectangle1.intersects(rectangle2);
    }

    public boolean notOnScreen() {
        return x + xMap + width < 0 ||
                x + xMap - width > GamePanel.WIDTH ||
                y + yMap + height < 0 ||
                y + yMap - height > GamePanel.HEIGHT;
    }

    public void setDown(boolean value) {
        this.down = value;
    }

    public void setJumping(boolean value) {
        this.jumping = value;
    }

    public void setLeft(boolean value) {
        this.left = value;
    }

    public void setMapPosition() {
        xMap = tileMap.getX();
        yMap = tileMap.getY();
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setRight(boolean value) {
        this.right = value;
    }

    public void setUp(boolean value) {
        this.up = value;
    }

    public void setVector(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }
}
    