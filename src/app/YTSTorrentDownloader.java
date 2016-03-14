package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class YTSTorrentDownloader extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("view/main_window.fxml"));
        Scene scene = new Scene(root, 810, 450);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("YTS Torrents Downloader");
        primaryStage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
