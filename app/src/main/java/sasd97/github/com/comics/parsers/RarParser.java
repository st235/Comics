package sasd97.github.com.comics.parsers;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sasd97.github.com.comics.services.NaturalOrderComparator;
import sasd97.github.com.comics.utils.FileUtils;
import sasd97.github.com.comics.utils.ServiceUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public final class RarParser implements IParser {

    private List<FileHeader> headers = new ArrayList<>();
    private Archive archive;
    private File cache;
    private boolean solid = false;

    @Override
    public void parse(File file) throws IOException {
        try {
            archive = new Archive(file);
        } catch (RarException e) {
            e.printStackTrace();
            throw new IOException("Probably not an archive");
        }

        FileHeader header = archive.nextFileHeader();
        while (header != null) {
            if (!header.isDirectory()) {
                String name = getHeaderName(header);
                if (FileUtils.isImage(name)) headers.add(header);
            }

            header = archive.nextFileHeader();
        }

        Collections.sort(headers, new NaturalOrderComparator() {
            @Override
            public String stringValue(Object o) {
                return getHeaderName((FileHeader) o);
            }
        });
    }

    @Override
    public void destroy() throws IOException {
        if (cache != null) {
            for (File f : cache.listFiles()) {
                f.delete();
            }
            cache.delete();
        }
        archive.close();
    }

    @Override
    public String getType() {
        return "rar";
    }

    @Override
    public InputStream getPage(int number) throws IOException {
        if (archive.getMainHeader().isSolid()) {
            synchronized (this) {
                if (!solid) {
                    for (FileHeader h : archive.getFileHeaders()) {
                        if (!h.isDirectory() && FileUtils.isImage(getHeaderName(h))) {
                            getPageStream(h);
                        }
                    }
                    solid = true;
                }
            }
        }
        return getPageStream(headers.get(number));
    }

    @Override
    public int pageAmount() {
        return headers.size();
    }

    public void setCache(File cacheDir) {
        cache = cacheDir;
        if (!cache.exists()) {
            cache.mkdir();
        }
        if (cache.listFiles() != null) {
            for (File f : cache.listFiles()) {
                f.delete();
            }
        }
    }

    private String getHeaderName(FileHeader header) {
        return header.isUnicode() ? header.getFileNameW() : header.getFileNameString();
    }

    private InputStream getPageStream(FileHeader header) throws IOException {
        try {
            if (cache != null) {
                String name = getHeaderName(header);
                File cacheFile = new File(cache, ServiceUtils.MD5(name));

                if (cacheFile.exists()) {
                    return new FileInputStream(cacheFile);
                }

                synchronized (this) {
                    if (!cacheFile.exists()) {
                        FileOutputStream os = new FileOutputStream(cacheFile);
                        try {
                            archive.extractFile(header, os);
                        }
                        catch (Exception e) {
                            os.close();
                            cacheFile.delete();
                            throw e;
                        }
                        os.close();
                    }
                }
                return new FileInputStream(cacheFile);
            }
            return archive.getInputStream(header);
        }
        catch (RarException e) {
            throw new IOException("unable to parse rar");
        }
    }
}
