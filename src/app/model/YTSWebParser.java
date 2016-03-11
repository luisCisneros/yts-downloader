package app.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luis on 07/03/16.
 */
public class YTSWebParser {

    // Manage Exceptions
    public ArrayList<Movie> findMovies(SearchParameters searchParameters) throws IOException {
        String url = String.format("https://yts.ag/browse-movies/%1$s/%2$s/%3$s/%4$d/%5$s",
                searchParameters.getSearchTerm(),
                searchParameters.getQuality().getValue(),
                searchParameters.getGenre().getValue(),
                searchParameters.getRating().getValue(),
                searchParameters.getOrderBy().getValue());
        Document document = Jsoup.connect(url).userAgent("Chrome").get();
        Elements movieWrappers = document.getElementsByClass("browse-movie-wrap");
        String pagination = document.getElementsByClass("pagination-bordered").text();
        int lastPage = totalPages(pagination);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int currentPage = 1; currentPage <= lastPage; currentPage++) {
            if (currentPage > 1) {
                document = Jsoup.connect(url + "?page=" + currentPage).userAgent("Chrome").get();
                movieWrappers = document.getElementsByClass("browse-movie-wrap");
            }
            for (Element movieWrapper : movieWrappers) {
                String name = movieWrapper.getElementsByClass("browse-movie-title").text();
                int year = Integer.parseInt(movieWrapper.getElementsByClass("browse-movie-year").text());
                float rating = extractRating(movieWrapper.getElementsByClass("rating").text());
                String torrentLink1080p = movieWrapper.getElementsByAttributeValue("title", "Download " + name + " 1080p Torrent").attr("href");
                String torrentLink720p = movieWrapper.getElementsByAttributeValue("title", "Download " + name + " 720p Torrent").attr("href");
                Movie movie = new Movie(name, year, rating, torrentLink720p, torrentLink1080p);
                movies.add(movie);
            }
        }
        return movies;
    }

    public void printMovies(ArrayList<Movie> movies) {
        int count = 0;
        for (Movie movie : movies) {
            System.out.println("*** Movie " + ++count + " ***");
            System.out.println("Movie: " + movie.getName());
            System.out.println("Year: " + movie.getYear());
            System.out.println("Rating: " + movie.getRating());
            System.out.println("1080p link: " + movie.getDownloadLink1080p());
            System.out.println("720p link: " + movie.getDownloadLink720p());
            System.out.println("Is checked?: " + movie.isChecked());
            System.out.println();
        }
    }

    private int totalPages(String pagination) {
        Pattern pattern = Pattern.compile("\\d of (\\d+)");
        Matcher matcher = pattern.matcher(pagination);
        int totalPages = -1;
        if (matcher.find()) {
            totalPages = Integer.parseInt(matcher.group(1));
        }
        return totalPages;
    }

    public float extractRating(String rawRating) {
        Pattern pattern = Pattern.compile("(\\d(\\.\\d)?) / 10");
        Matcher matcher = pattern.matcher(rawRating);
        float rating = 0f;
        if (matcher.find()) {
            rating = Float.parseFloat(matcher.group(1));
        }
        return rating;
    }
}
