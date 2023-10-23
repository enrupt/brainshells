package io.brainshells.cardsocr;

import java.awt.image.BufferedImage;

public class Extractor {
    public static final int ROW_WIDTH = 355;
    public static final int ROW_HEIGHT = 90;
    public static final int[] ROW_TOPLEFT = new int[]{143, 585};
    public static final int[] RANK_TOPLEFT = new int[]{6, 6};
    public static final int RANK_WIDTH = 30;
    public static final int RANK_HEIGHT = 27;
    public static final int[] SUIT_TOPLEFT = new int[]{26, 49};
    public static final int SUIT_WIDTH = 32;
    public static final int SUIT_HEIGHT = 35;
    public static final int CARD_WIDTH = 71;

    public static BufferedImage extractRow(BufferedImage field) {
        return field.getSubimage(ROW_TOPLEFT[0], ROW_TOPLEFT[1], ROW_WIDTH, ROW_HEIGHT);
    }
    public static BufferedImage extractRank(BufferedImage card) {
        return card.getSubimage(RANK_TOPLEFT[0], RANK_TOPLEFT[1], RANK_WIDTH, RANK_HEIGHT);
    }
    public static BufferedImage extractSuit(BufferedImage card) {
        return card.getSubimage(SUIT_TOPLEFT[0], SUIT_TOPLEFT[1], SUIT_WIDTH, SUIT_HEIGHT);
    }
}
