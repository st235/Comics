package sasd97.github.com.comics.parsers;

import java.io.File;
import java.io.IOException;

import sasd97.github.com.comics.utils.FileUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class ParserFactory {

    public static IParser create(String file) {
        return create(new File(file));
    }

    public static IParser create(File file) {
        IParser parser = null;
        String fileName = file.getAbsolutePath().toLowerCase();
        
        if (file.isDirectory()) {
            parser = new DirectoryParser();
        }
        if (FileUtils.isZip(fileName)) {
            //parser = new ZipParser();
        }
        else if (FileUtils.isRar(fileName)) {
            parser = new RarParser();
        }
        else if (FileUtils.isTarball(fileName)) {
           // parser = new TarParser();
        }
        else if (FileUtils.isSevenZ(fileName)) {
           // parser = new SevenZParser();
        }
        else if (FileUtils.isPdf(fileName)) {
           // parser = new PdfParser();
        }
        return tryParse(parser, file);
    }

    private static IParser tryParse(IParser parser, File file) {
        if (parser == null) {
            return null;
        }
        try {
            parser.parse(file);
        }
        catch (IOException e) {
            return null;
        }

        if (parser instanceof DirectoryParser && parser.pageAmount() < 4)
            return null;

        return parser;
    }
}
