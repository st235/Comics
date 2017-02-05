package sasd97.github.com.comics.services;

import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;
import java.io.InputStream;

import sasd97.github.com.comics.parsers.IParser;


public class LocalComicHandler extends RequestHandler {

    private final static String HANDLER_URI = "localcomic";
    private IParser mParser;

    public LocalComicHandler(IParser parser) {
        mParser = parser;
    }

    @Override
    public boolean canHandleRequest(Request request) {
        return HANDLER_URI.equals(request.uri.getScheme());
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        int pageNum = Integer.parseInt(request.uri.getFragment());
        InputStream stream = mParser.getPage(pageNum);
        return new Result(stream, Picasso.LoadedFrom.DISK);
    }

    public Uri getPageUri(int pageNum) {
        return new Uri.Builder()
                .scheme(HANDLER_URI)
                .authority("")
                .fragment(Integer.toString(pageNum))
                .build();
    }
}
