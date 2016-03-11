package app.model;

import javafx.beans.property.*;

/**
 * Created by luis on 07/03/16.
 */
public class Movie {

    private StringProperty name;
    private IntegerProperty year;
    private FloatProperty rating;
    private String downloadLink1080p;
    private String downloadLink720p;
    private BooleanProperty checked;
    private StringProperty hd1080pAvailability;
    private StringProperty hd720pAvailability;

    public enum LinkAvailability {
        AVAILABLE("Yes"), NOT_AVAILABLE("No");

        private final String value;

        LinkAvailability(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Movie() {

    }

    public Movie(String name, int year, float rating, String downloadLink720p, String downloadLink1080p) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.rating = new SimpleFloatProperty(rating);
        this.downloadLink720p = downloadLink720p;
        this.downloadLink1080p = downloadLink1080p;
        this.checked = new SimpleBooleanProperty(false);
        this.hd720pAvailability = new SimpleStringProperty(downloadLink720p.isEmpty() ?
                LinkAvailability.NOT_AVAILABLE.getValue() :
                LinkAvailability.AVAILABLE.getValue());
        this.hd1080pAvailability = new SimpleStringProperty(downloadLink1080p.isEmpty() ?
                LinkAvailability.NOT_AVAILABLE.getValue() :
                LinkAvailability.AVAILABLE.getValue());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public float getRating() {
        return rating.get();
    }

    public void setRating(float rating) {
        this.rating.set(rating);
    }

    public FloatProperty ratingProperty() {
        return rating;
    }

    public String getDownloadLink1080p() {
        return downloadLink1080p;
    }

    public void setDownloadLink1080p(String downloadLink1080p) {
        this.downloadLink1080p = downloadLink1080p;
    }

    public String getDownloadLink720p() {
        return downloadLink720p;
    }

    public void setDownloadLink720p(String downloadLink720p) {
        this.downloadLink720p = downloadLink720p;
    }

    public boolean isChecked() {
        return checked.get();
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public String getHd1080pAvailability() {
        return hd1080pAvailability.get();
    }

    public StringProperty hd1080pAvailabilityProperty() {
        return hd1080pAvailability;
    }

    public void setHd1080pAvailability(LinkAvailability hd1080pAvailability) {
        this.hd1080pAvailability.set(hd1080pAvailability.getValue());
    }

    public String getHd720pAvailability() {
        return hd720pAvailability.get();
    }

    public StringProperty hd720pAvailabilityProperty() {
        return hd720pAvailability;
    }

    public void setHd720pAvailability(LinkAvailability hd720pAvailability) {
        this.hd720pAvailability.set(hd720pAvailability.getValue());
    }
}
