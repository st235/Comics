package sasd97.github.com.comics.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import sasd97.github.com.comics.services.NaturalOrderComparator;
import sasd97.github.com.comics.utils.FileUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class DirectoryParser implements IParser {

    private List<File> files = new ArrayList<>();

    @Override
    public void parse(File directory) throws IOException {
        if (!directory.isDirectory()) throw new IOException("Not a directory: " + directory.getName());

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file: files) {
                if (file.isDirectory()) throw new IOException("Directory " + directory.getName() + " includes other directories");
                if (FileUtils.isImage(file.getAbsolutePath())) this.files.add(file);
            }
        }

        Collections.sort(this.files, new NaturalOrderComparator() {
            @Override
            public String stringValue(Object o) {
                return ((File) o).getName();
            }
        });
    }

    @Override
    public void destroy() throws IOException {

    }

    @Override
    public String getType() {
        return "dir";
    }

    @Override
    public InputStream getPage(int number) throws IOException {
        return new FileInputStream(files.get(number));
    }

    @Override
    public int pageAmount() {
        return files.size();
    }
}
