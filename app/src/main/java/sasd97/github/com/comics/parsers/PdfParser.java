package sasd97.github.com.comics.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class PdfParser implements IParser {

    @Override
    public void parse(File file) throws IOException {

    }

    @Override
    public void destroy() throws IOException {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public InputStream getPage(int number) throws IOException {
        return null;
    }

    @Override
    public int pageAmount() {
        return 0;
    }
}
