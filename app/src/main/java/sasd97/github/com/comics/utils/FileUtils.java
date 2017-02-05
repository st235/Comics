package sasd97.github.com.comics.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public final class FileUtils {

    public static boolean isImage(String filename) {
        return filename.toLowerCase().matches(".*\\.(jpg|jpeg|bmp|gif|png|webp)$");
    }

    public static boolean isZip(String filename) {
        return filename.toLowerCase().matches(".*\\.(zip|cbz)$");
    }

    public static boolean isRar(String filename) {
        return filename.toLowerCase().matches(".*\\.(rar|cbr)$");
    }

    public static boolean isTarball(String filename) {
        return filename.toLowerCase().matches(".*\\.(cbt)$");
    }

    public static boolean isSevenZ(String filename) {
        return filename.toLowerCase().matches(".*\\.(cb7|7z)$");
    }

    public static boolean isPdf(String filename) {
        return filename.toLowerCase().matches(".*\\.(pdf)$");
    }

    public static boolean isArchive(String filename) {
        return isZip(filename) || isRar(filename) || isTarball(filename) || isSevenZ(filename);
    }
}
