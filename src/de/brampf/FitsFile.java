package de.brampf;

import java.io.FileInputStream;
import java.util.LinkedList;

public class FitsFile {

    static int CARD_LENGTH = 80;

    public LinkedList<HDU> hdus = new LinkedList<>(); 

    public static FitsFile parse(final String stringPath) {

        FitsFile file = new FitsFile();

        try {
            final FileInputStream input = new FileInputStream(stringPath);

            while (input.available() > 0) {
                HDU hdu = new HDU(input);
                file.hdus.add(hdu);
            }

            input.close();

        } catch (final Exception e) {
            //TODO: handle exception
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return file;
    }
}
