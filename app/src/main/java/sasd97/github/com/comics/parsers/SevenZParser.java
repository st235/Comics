package sasd97.github.com.comics.parsers;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sasd97.github.com.comics.services.NaturalOrderComparator;
import sasd97.github.com.comics.utils.FileUtils;

public class SevenZParser implements IParser {
    private List<SevenZEntry> mEntries;

    private class SevenZEntry {
        final SevenZArchiveEntry entry;
        final byte[] bytes;

        public SevenZEntry(SevenZArchiveEntry entry, byte[] bytes) {
            this.entry = entry;
            this.bytes = bytes;
        }
    }

    @Override
    public void parse(File file) throws IOException {
        mEntries = new ArrayList<>();
        SevenZFile sevenZFile = new SevenZFile(file);

        SevenZArchiveEntry entry = sevenZFile.getNextEntry();
        while (entry != null) {
            if (entry.isDirectory()) {
                continue;
            }
            if (FileUtils.isImage(entry.getName())) {
                byte[] content = new byte[(int)entry.getSize()];
                sevenZFile.read(content);
                mEntries.add(new SevenZEntry(entry, content));
            }
            entry = sevenZFile.getNextEntry();
        }

        Collections.sort(mEntries, new NaturalOrderComparator() {
            @Override
            public String stringValue(Object o) {
                return ((SevenZEntry) o).entry.getName();
            }
        });
    }

    @Override
    public int pageAmount() {
        return mEntries.size();
    }

    @Override
    public InputStream getPage(int num) throws IOException {
        return new ByteArrayInputStream(mEntries.get(num).bytes);
    }

    @Override
    public String getType() {
        return "tar";
    }

    @Override
    public void destroy() throws IOException {

    }
}
