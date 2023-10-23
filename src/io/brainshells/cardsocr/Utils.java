package io.brainshells.cardsocr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static void genThinTemplates() throws IOException {

        Parser p = new Parser("acc_992_thrsh075", false);
        Map<String, BufferedImage> rankTemplates = p.loadTemplates(Parser.RANKS, "ranks");
        Map<String, BufferedImage> suitTemplates = p.loadTemplates(Parser.SUITS, "suits");

        for(File f: Utils.listFilesInDir("/Users/ko.bykov/Desktop/imgs_marked/")) {
            BufferedImage row = Extractor.extractRow(ImageIO.read(f));
            char[] right = f.getName().replace(".png", "").toCharArray();
            int cardLeft = 0;
            for (int i = 0; i < right.length; ) {
                char r = right[i];
                BufferedImage rT;
                BufferedImage rS;
                if (r == '1') {
                    rT = rankTemplates.get("10");
                    rS = suitTemplates.get(String.valueOf(right[i + 2]));
                    i+=3;
                } else {
                    rT = rankTemplates.get(String.valueOf(r));
                    rS = suitTemplates.get(String.valueOf(right[i + 1]));
                    i+=2;
                }
                BufferedImage card = row.getSubimage(cardLeft, 0, Extractor.CARD_WIDTH, Extractor.ROW_HEIGHT);
                BufferedImage rank = Filler.fill(Extractor.extractRank(card));
                BufferedImage suit = Filler.fill(Extractor.extractSuit(card));
                cardLeft+=Extractor.CARD_WIDTH;
                tune(rank, rT);
                tune(suit, rS);
            }
        }

        for (int i = 0; i < Parser.RANKS.length; i++) {
            ImageIO.write(rankTemplates.get(Parser.RANKS[i]), "png", new File("./ranks/"+Parser.RANKS[i]+".png"));
        }
        for (int i = 0; i < Parser.SUITS.length; i++) {
            ImageIO.write(suitTemplates.get(Parser.SUITS[i]), "png", new File("./suits/"+Parser.SUITS[i]+".png"));
        }
    }

    public static void tune(BufferedImage needle, BufferedImage template) {
        int n_h = needle.getHeight();
        int n_w = needle.getWidth();
        int h = Math.min(template.getHeight(), n_h);
        int w = Math.min(template.getWidth(), n_w);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (needle.getRGB(i,j) != Filler.FILL && needle.getRGB(i,j) != template.getRGB(i,j)) {
                    template.setRGB(i, j, Filler.BACKGROUND);
                }
            }
        }
    }

    private static void genSimpleTemplates() throws IOException {
        String[] rankFiles = new String[]{
                "2c7h3c10c.png","3c7c2d.png","4sJdKdKc.png",
                "5cKh2h.png","6cKc2c8s8h.png", "7dJc6sAh5s.png",
                "8dAsQc2c8s.png","9d5d5hKh.png",
                "10s2sJc.png","Jd4s2h.png",
                "QdAd4d7cQc.png", "Kc10h2h.png", "Ad5h8d8s.png"
        };
        for (int i = 0; i < Parser.RANKS.length; i++) {
            File f = new File("/Users/ko.bykov/Desktop/imgs_marked/"+rankFiles[i]);
            BufferedImage rank = Extractor.extractRank(Extractor.extractRow(ImageIO.read(f)));
            rank = Filler.fill((rank));
            ImageIO.write(rank, "png", new File("./ranks/"+Parser.RANKS[i]+".png"));
        }
        String[] suitFiles = new String[]{
                "2c9s9d.png",
                "3s7sKc8d.png", "4hAs2d2s.png", "10d5d4d.png"
        };
        for (int i = 0; i < Parser.SUITS.length; i++) {
            File f = new File("/Users/ko.bykov/Desktop/imgs_marked/"+suitFiles[i]);
            BufferedImage suit = Filler.fill(Extractor.extractSuit(Extractor.extractRow(ImageIO.read(f))));
            ImageIO.write(suit, "png", new File("./suits/"+Parser.SUITS[i]+".png"));
        }
    }

    public static Set<File> listFilesInDir(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory()).collect(Collectors.toSet());
    }
}
