package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("QUEST OF THE ROUND TABLE");
        primaryStage.setScene(new Scene(root, 800, 600));
        root.getStylesheets().add("/root.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        String musicFile = "src/opening.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        mediaPlayer.play();
        launch(args);
    }
}
