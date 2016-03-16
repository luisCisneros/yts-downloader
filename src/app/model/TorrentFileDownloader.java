package app.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luis on 07/03/16.
 */
public class TorrentFileDownloader {

    public int download(Movie movie, Path path) throws IOException {
        int filesWritten = 0;
        String[] torrentLinks = {movie.getDownloadLink720p(), movie.getDownloadLink1080p()};
        String quality = "720p";
        for (String link : torrentLinks) {
            // Maybe this if is not necessary
            if (!link.isEmpty()) {
                String fileName = String.format("%1$s (%2$d) [%3$s].torrent", movie.getName().replace(":", " -"), movie.getYear(), quality);
                Path fullPath = path.resolve(fileName);
                download(link, fullPath);
                filesWritten++;
            }
            quality = "1080p";
        }
        return filesWritten;
    }

    public boolean download(String link, Path path) throws IOException {
        boolean isFileSaved = false;
        if (/*Files.notExists(path) &&*/ !link.isEmpty()) {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Chrome");
            ReadableByteChannel readableByteChannel = Channels.newChannel(urlConnection.getInputStream());
            try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile())) {
                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                isFileSaved = true;
            }
        }
        return isFileSaved;
    }

    public boolean download(Movie movie, Path path, SearchParameters.Quality quality) throws IOException {
        String fileName = String.format("%1$s (%2$d) [%3$s].torrent", movie.getName().replace(":", " -"), movie.getYear(), quality.getValue());
        Path fullPath = path.resolve(fileName);

        switch (quality) {
            case HD_720P:
                return download(movie.getDownloadLink720p(), fullPath);

            case HD_1080P:
                return download(movie.getDownloadLink1080p(), fullPath);

            default:
                return false;
        }
    }

    /*
    public String extractTorrentFileName(String rawURL) {
        Pattern pattern = Pattern.compile("/((\\d|\\w)+\\.torrent)");
        Matcher matcher = pattern.matcher(rawURL);
        String fileName = "";
        if (matcher.find()) {
            fileName = matcher.group(1);
        }
        return fileName;
    }*/
}
