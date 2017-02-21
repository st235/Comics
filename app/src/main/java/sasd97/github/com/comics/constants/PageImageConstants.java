package sasd97.github.com.comics.constants;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class PageImageConstants {

    public static final int MAX_WIDTH = 1600;
    public static final int MAX_HEIGHT = 2000;

    public enum PageViewMode {
        ASPECT_FILL(0),
        ASPECT_FIT(1),
        FIT_WIDTH(2);

        public final int native_int;

        PageViewMode(int n) {
            native_int = n;
        }
    }
}
