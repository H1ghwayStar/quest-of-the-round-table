package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;




public class Main extends Application {

    //static Logger logger;
    static PrintStream out;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("QUEST OF THE ROUND TABLE");
        primaryStage.setScene(new Scene(root, 800, 600));
        root.getStylesheets().add("/root.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        //String musicFile = ClassLoader.getSystemResource("opening.mp3").toString();

        //Media sound = new Media(new File(musicFile).toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        //mediaPlayer.play();

        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(out);

        System.out.println("test");

        launch(args);
    }
}
