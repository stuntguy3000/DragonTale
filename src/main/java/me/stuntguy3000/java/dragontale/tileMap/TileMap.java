package me.stuntguy3000.java.dragontale.tileMap;

import me.stuntguy3000.java.dragontale.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {
    private int colOffset;
    private int height;
    private int[][] map;
    private int numCols;
    private int numColsToDraw;
    private int numRows;
    private int numRowsToDraw;
    private int numTilesAcross;
    private int rowOffset;
    private BufferedImage tileSet;
    private int tileSize;
    private Tile[][] tiles;
    private double tween;
    private int width;
    private double x;
    private int xMax;
    private int xMin;
    private double y;
    private int yMax;
    private int yMin;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;

        numRowsToDraw = (GamePanel.HEIGHT / tileSize) + 2;
        numColsToDraw = (GamePanel.WIDTH / tileSize) + 2;

        tween = 0.07;
    }

    public void draw(Graphics2D graphics2D) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) {
                break;
            }

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols) {
                    break;
                }

                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                graphics2D.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
            }
        }
    }

    private void fixBounds() {
        if (x < xMin) {
            x = xMin;
        }
        if (y < yMin) {
            y = yMin;
        }
        if (x > xMax) {
            x = xMax;
        }
        if (y > xMax) {
            y = xMax;
        }
    }

    public int getHeight() {
        return height;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;

        return tiles[r][c].getType();
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

    public void loadMap(String mapFile) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            numCols = Integer.parseInt(bufferedReader.readLine());
            numRows = Integer.parseInt(bufferedReader.readLine());

            map = new int[numRows][numCols];

            width = numCols * tileSize;
            height = numRows * tileSize;

            xMin = GamePanel.WIDTH - width;
            xMax = 0;
            yMin = GamePanel.HEIGHT - height;
            yMax = 0;

            String delims = "\\s+";

            for (int rows = 0; rows < numRows; rows++) {
                String line = bufferedReader.readLine();
                String[] tokens = line.split(delims);

                for (int cols = 0; cols < numCols; cols++) {
                    map[rows][cols] = Integer.parseInt(tokens[cols]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTiles(String imageFile) {
        try {
            tileSet = ImageIO.read(getClass().getResourceAsStream(imageFile));

            numTilesAcross = tileSet.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;
            for (int i = 0; i < numTilesAcross; i++) {
                subImage = tileSet.getSubimage(i * tileSize, 0, tileSize, tileSize);
                tiles[0][i] = new Tile(subImage, Tile.NORMAL);

                subImage = tileSet.getSubimage(i * tileSize, tileSize, tileSize, tileSize);
                tiles[1][i] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    public void setTween(double tween) {
        this.tween = tween;
    }
}
    