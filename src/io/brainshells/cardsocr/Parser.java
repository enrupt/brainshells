package io.brainshells.cardsocr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static final String[] RANKS = new String[]{ "2","3","4","5","6", "7","8","9","10","J","Q","K","A" };
    public static final String[] SUITS = new String[]{ "c", "s", "h", "d"  };
    private final String template;
    private final boolean debug;

    public Parser(String template, boolean debug) {
        this.template = template;
        this.debug = debug;
    }

    public List<String> parseCards(File f) throws IOException {
        return parseCards(Extractor.extractRow(ImageIO.read(f)));
    }

    public Map<String, BufferedImage> loadTemplates(String[] type, String folder) throws IOException {
        Map<String, BufferedImage> rankTemplates = new HashMap<>();
        for (String r: type) {
            InputStream is = Parser.class.getResourceAsStream("/io/brainshells/cardsocr/"+template+"/"+folder+"/"+r+".png");
            rankTemplates.put(r, ImageIO.read(is));
        }
        return rankTemplates;
    }

    public List<String> parseCards(BufferedImage row) throws IOException {
        List<String> result = new ArrayList<>();
        Map<String, BufferedImage> rankTemplates = loadTemplates(RANKS, "ranks");
        Map<String, BufferedImage> suitTemplates = loadTemplates(SUITS, "suits");

        for (int cardLeft = 0; cardLeft < Extractor.ROW_WIDTH; cardLeft+=Extractor.CARD_WIDTH) {
            BufferedImage card = row.getSubimage(cardLeft, 0, Extractor.CARD_WIDTH, Extractor.ROW_HEIGHT);
            if (debug) {
                log("card", cardLeft, card);
            }

            BufferedImage rank = Filler.fill(Extractor.extractRank(card));
            if (debug) {
                log("rank", cardLeft, rank);
            }

            String matchRank = Matcher.findMatch(rank, rankTemplates);
            if (matchRank == null) {
                break;
            }
            BufferedImage suit = Filler.fill(Extractor.extractSuit(card));
            if (debug) {
                log("suit", cardLeft, suit);
            }
            String matchSuit = Matcher.findMatch(suit, suitTemplates);
            if (matchSuit == null) {
                break;
            }
            result.add(matchRank+matchSuit);
        }
        return result;
    }

    private static void log(String pref, int shift, BufferedImage img) throws IOException {
        ImageIO.write(img, "png", new File("/"+pref+shift+".png"));
    }
}
