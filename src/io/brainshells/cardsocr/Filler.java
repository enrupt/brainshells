package io.brainshells.cardsocr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Filler {
    public static final int BACKGROUND = Color.GRAY.getRGB();
    public static final int FILL = Color.PINK.getRGB();
    /**  Fills background with gray and the res with pink colors  */
    public static BufferedImage fill(BufferedImage img) {
        return fillShape(fillBackground(img));
    }
    private static BufferedImage fillBackground(BufferedImage img) {
        int backgrnd = img.getRGB(0,0);
        int h = img.getHeight();
        int w = img.getWidth();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (img.getRGB(i,j) == backgrnd) {
                    img.setRGB(i, j, BACKGROUND);
                }
            }
        }
        return  img;
    }
    private static BufferedImage fillShape(BufferedImage img) {
        int h = img.getHeight();
        int w = img.getWidth();
        boolean[][] visited = new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (img.getRGB(i,j) != BACKGROUND) {
                    fillInOrder(img, i, j, visited, FILL);
                }
            }
        }
        return img;
    }
    private static void fillInOrder(BufferedImage img, int i, int j, boolean[][] visited, int c) {
        int h = img.getHeight();
        int w = img.getWidth();
        if (i < 0 || i > w - 1 || j < 0 || j > h - 1 || visited[i][j] || img.getRGB(i,j) == BACKGROUND) {
            return;
        }
        visited[i][j] = true;
        img.setRGB(i, j, c);
        fillInOrder(img, i+1, j, visited, c);
        fillInOrder(img, i-1, j, visited, c);
        fillInOrder(img, i, j-1, visited, c);
        fillInOrder(img, i, j+1, visited, c);
    }
}
