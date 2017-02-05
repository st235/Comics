package sasd97.github.com.comics.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public interface IParser {
    void parse(File file) throws IOException;
    void destroy() throws IOException;

    String getType();
    InputStream getPage(int number) throws IOException;
    int pageAmount();
}
