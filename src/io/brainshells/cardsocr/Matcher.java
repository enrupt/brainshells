package io.brainshells.cardsocr;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Matcher {
    public static final float MATCH_THRESHOLD = 0.75f;

    public static String findMatch(BufferedImage needle, Map<String, BufferedImage> templates) {
        int n_h = needle.getHeight();
        int n_w = needle.getWidth();
        String bestMatch = null;
        float bestScore = Float.MIN_VALUE;
        for(String key: templates.keySet()) {
            BufferedImage template = templates.get(key);
            int h = Math.min(template.getHeight(), n_h);
            int w = Math.min(template.getWidth(), n_w);
            int eq = 0;
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (needle.getRGB(i,j) == template.getRGB(i,j)) {
                        eq++;
                    }
                }
            }
            float match = (float)eq/(h*w);

            if (match > MATCH_THRESHOLD && match > bestScore) {
                bestScore = match;
                bestMatch = key;
            }
        }
        return bestMatch;
    }
}
