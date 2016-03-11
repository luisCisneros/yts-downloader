package app.controller;

import app.YTSTorrentDownloader;
import app.model.Movie;
import app.model.SearchParameters;
import app.model.SearchParameters.*;
import app.model.TorrentFileDownloader;
import app.model.YTSWebParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML private TextField searchTermTextField;
    @FXML private ProgressIndicator searchingProgressIndicator;
    @FXML private ChoiceBox<Quality> qualityChoiceBox;
    @FXML private ChoiceBox<Genre> genreChoiceBox;
    @FXML private ChoiceBox<Rating> ratingChoiceBox;
    @FXML private TableView<Movie> moviesTableView;
    @FXML private TableColumn<Movie, Boolean> checkedTableColumn;
    @FXML private CheckBox selectAllCheckBox;
    @FXML private Button downloadButton;
    @FXML private RadioButton hd720pRadioButton;
    @FXML private RadioButton hd1080pRadioButton;
    @FXML private RadioButton downloadAllRadioButton;
    @FXML private ProgressIndicator downloadProgressIndicator;
    @FXML private Label downloadLabel;

    private ArrayList<Movie> movies;
    private ObservableList<Movie> observableMovies;
    private YTSWebParser webParser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qualityChoiceBox.setItems(FXCollections.observableArrayList(Quality.values()));
        qualityChoiceBox.setValue(Quality.ALL);
        genreChoiceBox.setItems(FXCollections.observableArrayList(Genre.values()));
        genreChoiceBox.setValue(Genre.ALL);
        ratingChoiceBox.setItems(FXCollections.observableArrayList(Rating.values()));
        ratingChoiceBox.setValue(Rating.ALL);

        checkedTableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkedTableColumn));

        observableMovies = FXCollections.observableArrayList();
        movies = new ArrayList<>();
        webParser = new YTSWebParser();
    }

    @FXML
    protected void searchMovies(ActionEvent event) {
        searchingProgressIndicator.setVisible(true);
        String searchTerm = searchTermTextField.getText();
        Quality quality = qualityChoiceBox.getValue();
        Genre genre = genreChoiceBox.getValue();
        Rating rating = ratingChoiceBox.getValue();
        SearchParameters searchParameters = new SearchParameters(searchTerm, quality, genre, rating, OrderBy.ALPHABETICAL);

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                movies.clear();
                movies.addAll(webParser.findMovies(searchParameters));
                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (!movies.isEmpty()) {
                    observableMovies.setAll(movies);
                    moviesTableView.setItems(observableMovies);
                    searchingProgressIndicator.setVisible(false);
                    selectAllCheckBox.setDisable(false);
                    selectAllCheckBox.setSelected(false);
                    downloadButton.setDisable(false);
                    downloadAllRadioButton.setDisable(false);
                    hd720pRadioButton.setDisable(false);
                    hd1080pRadioButton.setDisable(false);
                } else {
                    searchingProgressIndicator.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("No search results");
                    alert.setContentText("There seems to be no movies to match the search term.");
                    alert.showAndWait();
                }
            }
        });

        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                searchingProgressIndicator.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("An error occurred");
                alert.setContentText("An error occurred while performing a search: " + event.getSource().getException().getMessage());
                alert.showAndWait();
            }
        });

        new Thread(task).start();
    }

    @FXML
    protected void downloadTorrents(ActionEvent event) {
        boolean atLeastOneMovieIsSelected = false;

        for (Movie movie : observableMovies) {
            if (movie.isChecked()) {
                atLeastOneMovieIsSelected = true;
                break;
            }
        }

        if (atLeastOneMovieIsSelected) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Location to save torrent files");
            File destinationDirectory = directoryChooser.showDialog(YTSTorrentDownloader.getStage());
            Task task = new Task() {
                @Override
                protected Integer call() throws Exception {
                    TorrentFileDownloader torrentFileDownloader = new TorrentFileDownloader();
                    int counter = 0;
                    int progress = 0;
                    Path path = destinationDirectory.toPath();
                    if (downloadAllRadioButton.isSelected()) {
                        for (Movie movie : observableMovies) {
                            if (movie.isChecked()) {
                                counter += torrentFileDownloader.download(movie, path);
                            }
                            updateProgress(++progress, observableMovies.size());
                        }
                    } else if (hd1080pRadioButton.isSelected()) {
                        for (Movie movie : observableMovies) {
                            if (movie.isChecked()) {
                                counter += torrentFileDownloader.download(movie, path, Quality.HD_1080P) ? 1 : 0;
                            }
                            updateProgress(++progress, observableMovies.size());
                        }
                    } else if (hd720pRadioButton.isSelected()) {
                        for (Movie movie : observableMovies) {
                            if (movie.isChecked()) {
                                counter += torrentFileDownloader.download(movie, path, Quality.HD_720P) ? 1 : 0;
                            }
                            updateProgress(++progress, observableMovies.size());
                        }
                    }
                    return counter;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Download complete");
                    alert.setContentText(task.getValue() + " files saved.");
                    alert.showAndWait();
                    downloadProgressIndicator.setVisible(false);
                    downloadLabel.setVisible(false);
                }
            });

            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("An error occurred");
                    alert.setContentText("An error occurred while trying to download the files: " + event.getSource().getException().getMessage());
                    alert.showAndWait();
                    downloadProgressIndicator.setVisible(false);
                    downloadLabel.setVisible(false);
                }
            });

            if (destinationDirectory != null) {
                downloadProgressIndicator.setVisible(true);
                downloadLabel.setVisible(true);
                downloadProgressIndicator.progressProperty().bind(task.progressProperty());
                new Thread(task).start();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No movies selected");
            alert.setContentText("Please select at least one movie to download.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void selectAllMovies(ActionEvent event) {
        if (selectAllCheckBox.isSelected()) {
            for (Movie movie : observableMovies) {
                movie.setChecked(true);
            }
        } else {
            for (Movie movie : observableMovies) {
                movie.setChecked(false);
            }
        }
    }
}
