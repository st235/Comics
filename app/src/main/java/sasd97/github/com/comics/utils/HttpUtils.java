package sasd97.github.com.comics.utils;

import sasd97.github.com.comics.constants.HttpConstants;

/**
 * Created by alexander on 06/05/2017.
 */

public class HttpUtils {

    private HttpUtils() {
    }

    public static String obtainComicsCoverUrl(String id) {
        return String.format("%1$s/downloadPageCover?comicsId=%2$s", HttpConstants.FILE_API_URL, id);
    }
}
