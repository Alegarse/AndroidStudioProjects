package com.example.carplus3g365v2.Modelos;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static File getTemporaryPictureFile(String name) {
        final File path = new File(Environment.getExternalStorageDirectory(), "carplus3G");
        if (!path.exists()) {
            path.mkdir();
        }

        return new File(path, name + ".jpg");
    }

}
