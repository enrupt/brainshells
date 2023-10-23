package io.brainshells.cardsocr;

import java.io.File;
import java.io.IOException;

public class Solution {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("No folder path specified");
        }
        Parser p = new Parser("thin", false);
        for(File f: Utils.listFilesInDir(args[0])) {
            System.out.println(f.getName() + " " + String.join("", p.parseCards(f)));
        }
    }
}
