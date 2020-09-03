package de.brampf;

import java.io.FileInputStream;

public class FitsFile {

    static int CARD_LENGTH = 80;

    public static void parse(final String stringPath) {

        try {
            final FileInputStream input = new FileInputStream(stringPath);

            final byte[] reader = new byte[CARD_LENGTH];
            boolean end = false;

            while (end == false) {
                input.read(reader, 0, CARD_LENGTH);
                HeaderBlock hb = new HeaderBlock(reader);

                System.out.println(hb.toString());
                System.out.println(hb.raw);

                if (hb.keyword.equals("END")) {
                    end = true;
                } 
            }

            input.close();

        } catch (final Exception e) {
        //TODO: handle exception
        System.out.print(e.getLocalizedMessage());
    }

    }
}
