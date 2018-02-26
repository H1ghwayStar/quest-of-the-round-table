package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollBar;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Alert {

    public void displayNumPlayers(String title, String message){
        try {
            Parent playRoot = FXMLLoader.load(getClass().getResource("NumPlayers.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("Game running");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayInstructions(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Rules");
        window.setMinWidth(600);
        window.setMinHeight(600);
        Label label = new Label();
        label.setText("Instructions");
        Button close = new Button("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        VBox layout = new VBox(10);
        ScrollBar sc = new ScrollBar();
        sc.setMin(0);
        sc.setMax(100);
        sc.setValue(50);
        layout.getChildren().addAll(label,close, sc);
        layout.setAlignment(Pos.CENTER);



        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    public void displayQuit(String title, String message){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button yes = new Button("Yes, quit");
        Button no = new Button ("No, don't quit");

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yes,no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void displaySettings(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Settings");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Settings");
        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,back);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
