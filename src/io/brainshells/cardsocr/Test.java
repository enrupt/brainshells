package io.brainshells.cardsocr;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            Parser p = new Parser("thin", false);
            for(File f: Utils.listFilesInDir("/Users/ko.bykov/Desktop/imgs_marked/")) {
                String right = f.getName().replace(".png", "");
                String guess = String.join("", p.parseCards(f));
                if (!right.equals(guess)) {
                    System.out.println(right + " vs "+guess);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
