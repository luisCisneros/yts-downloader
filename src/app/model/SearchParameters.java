package app.model;

/**
 * Created by luis on 07/03/16.
 */
public class SearchParameters {

    public enum Quality {

        ALL("all", "All"),
        HD_1080P("1080p", "1080p"),
        HD_720P("720p", "720p"),
        HD_3D("3D", "3D");

        private final String value;
        private final String label;

        Quality(String value, String label) {
            this.value = value;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Genre {

        ALL("all", "All"),
        ACTION("action", "Action"),
        ADVENTURE("adventure", "Adventure"),
        ANIMATION("animation", "Animation"),
        BIOGRAPHY("biography", "Biography"),
        COMEDY("comedy", "Comedy"),
        CRIME("crime", "Crime"),
        DOCUMENTARY("documentary", "Documentary"),
        DRAMA("drama", "Drama"),
        FAMILY("family", "Family"),
        FANTASY("fantasy", "Fantasy"),
        FILM_NOIR("film-noir", "Film-Noir"),
        GAME_SHOW("game-show", "Game-Show"),
        HISTORY("history", "History"),
        HORROR("horror", "Horror"),
        MUSIC("music", "Music"),
        MUSICAL("musical", "Musical"),
        MYSTERY("mystery", "Mistery"),
        NEWS("news", "News"),
        REALITY_TV("reality-tv", "Reality-TV"),
        ROMANCE("romance", "Romance"),
        SCI_FI("sci-fi", "Sci-Fi"),
        SPORT("sport", "Sport"),
        TALK_SHOW("talk-show", "Talk-Show"),
        THRILLER("thriller", "Thriller"),
        WAR("war", "War"),
        WESTERN("western", "Western");

        private final String value;
        private final String label;

        Genre(String value, String label) {

            this.value = value;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Rating {

        ALL(0, "All"),
        NINE_PLUS(9, "9+"),
        EIGHT_PLUS(8, "8+"),
        SEVEN_PLUS(7, "7+"),
        SIX_PLUS(6, "6+"),
        FIVE_PLUS(5, "5+"),
        FOUR_PLUS(4, "4+"),
        THREE_PLUS(3, "3+"),
        TWO_PLUS(2, "2+"),
        ONE_PLUS(1, "1+");

        private final int value;
        private final String label;

        Rating(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum OrderBy {

        LATEST("latest", "Latest"),
        OLDEST("oldest", "Oldest"),
        SEEDS("seeds", "Seeds"),
        PEERS("peers", "Peers"),
        YEAR("year", "Year"),
        RATING("rating", "Rating"),
        LIKES("likes", "Likes"),
        ALPHABETICAL("alphabetical", "Alphabetical"),
        DOWNLOADS("dowloads", "Downloads");

        private final String value;
        private final String label;

        OrderBy(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private String searchTerm;
    private Quality quality;
    private Genre genre;
    private Rating rating;
    private OrderBy orderBy;

    public SearchParameters(String searchTerm, Quality quality,  Genre genre, Rating rating, OrderBy orderBy) {
        this.searchTerm = searchTerm;
        this.quality = quality;
        this.genre = genre;
        this.rating = rating;
        this.orderBy = orderBy;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }
}
