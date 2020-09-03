import java.io.File;

import de.brampf.*;

public class App {
    public static void main(final String[] args) throws Exception {

        String path = "";
        File f = new File(".");
        path = f.getAbsolutePath();

        System.out.println(path);
        FitsFile.parse("./res/UITfuv2582gc.fits");

        System.out.println("DONE");

    }
}
