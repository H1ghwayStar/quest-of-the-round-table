package sample;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.SocketPermission;
import java.util.Collections;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javax.swing.*;
import java.util.ArrayList;

import java.lang.*;




import java.io.IOException;
import java.util.List;

public class Controller{

    @FXML private Rectangle text3a;
    @FXML private Text text3b;
    @FXML private Button addPlayer3;
    @FXML private javafx.scene.control.TextField textField3c;
    @FXML private ComboBox comboBox3d;
    @FXML private Button done3;
    @FXML private Button addPlayer4;
    @FXML private Button removePlayer3;
    @FXML private Rectangle text4a;
    @FXML private Text text4b;
    @FXML private javafx.scene.control.TextField textField4c;
    @FXML private ComboBox comboBox4d;
    @FXML private Button done4;
    @FXML private Button removePlayer4;
    @FXML private ComboBox comboBox1d;
    @FXML private ComboBox comboBox2d;
    @FXML private javafx.scene.control.TextField textField1c;
    @FXML private javafx.scene.control.TextField textField2c;
    @FXML private Button done1;
    @FXML private Button done2;
    @FXML private Button donePlayerSelect;
    @FXML private TableView tableView;
    public Stage newStage;
    public int numPlayers = 2;
    public Player player1;
    public Player player2;
    public Player player3;
    public Player player4;
    //int[] playerParticipating = {};
   // final int[] playerParticipating = {2};
    final List<Boolean> playerParticipating = new ArrayList<>();
    public final int[] position = {0};
    StackPane sp = new StackPane();
    private int counter2 = 1;

    public Story card = null;
    private static Controller _instance;
    Gameboard play;

    public Alert myAlerts;

    public Controller(){
        myAlerts = new Alert();
        _instance = this;
        play = new Gameboard();
    }

    public static Controller getInstance(){
        return _instance;
    }

    public static int currentPlayer = 1;
    public static int playersAsked = 0;

    public void quitClicked(ActionEvent e){
        String musicFile = "src/buttonClick.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        myAlerts.displayQuit("Quitting...", "Are you sure?");
    }

    public void instructionClicked(ActionEvent e){
        String musicFile = "src/buttonClick.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        myAlerts.displayInstructions();
    }

    public void settingsClicked(ActionEvent e){
        String musicFile = "src/buttonClick.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        myAlerts.displaySettings();
    }

    public void playClicked(ActionEvent e){
        try {
            String musicFile = "src/buttonClick.wav";

            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            Parent playRoot = FXMLLoader.load(getClass().getResource("NumPlayers.fxml"));

            newStage = new Stage();
            newStage.setTitle("Player Setup");
            newStage.setScene(new Scene(playRoot, 800, 600));
            playRoot.getStylesheets().add("/style.css");
            newStage.show();
            //populateDrop();




            //comboBox3d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
            //comboBox4d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
           // Alert.
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        comboBox2d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");

        // sleep(1000)
    }
    @FXML
    public void populateDrop(){

        comboBox1d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
        comboBox2d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
        comboBox3d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
        comboBox4d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
    }
//
//    @FXML
//    public void populateDrop2(ActionEvent e){
//        comboBox2d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
//    }
//
//    @FXML
//    public void populateDrop3(ActionEvent e){
//        comboBox3d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
//    }
//
//    @FXML
//    public void populateDrop4(ActionEvent e){
//        comboBox4d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");
//    }


    @FXML
    public void addButtonPlayer3(ActionEvent e){
        text3a.setVisible(true);
        text3b.setVisible(true);
        textField3c.setVisible(true);
        comboBox3d.setVisible(true);
        done3.setVisible(true);
        addPlayer3.setVisible(false);
        addPlayer4.setVisible(true);
        removePlayer3.setVisible(true);
        numPlayers++;
//        comboBox1d.getItems().addAll("Normal Mode", "Easy AI", "Difficult AI");


    }

    @FXML
    public void addButtonPlayer4(ActionEvent e){
        text4a.setVisible(true);
        text4b.setVisible(true);
        textField4c.setVisible(true);
        comboBox4d.setVisible(true);
        done4.setVisible(true);
        addPlayer3.setVisible(false);
        removePlayer3.setVisible(false);
        addPlayer4.setVisible(false);
        removePlayer4.setVisible(true);
        numPlayers++;
    }

    @FXML
    public void removeButtonPlayer3(ActionEvent e){
        text3a.setVisible(false);
        text3b.setVisible(false);
        textField3c.setVisible(false);
        comboBox3d.setVisible(false);
        done3.setVisible(false);
        addPlayer3.setVisible(true);
        addPlayer4.setVisible(false);
        removePlayer3.setVisible(false);
        textField3c.setEditable(true);
        textField3c.setMouseTransparent(false);
        textField3c.setFocusTraversable(true);
        textField3c.clear();
        textField3c.setStyle("-fx-background-color: white;");
        numPlayers--;

        //ChoiceBox<String> ddrop = new ChoiceBox<>();


    }

    @FXML
    public void removeButtonPlayer4(ActionEvent e){
        text4a.setVisible(false);
        text4b.setVisible(false);
        textField4c.setVisible(false);
        comboBox4d.setVisible(false);
        done4.setVisible(false);
        addPlayer3.setVisible(false);
        removePlayer3.setVisible(true);
        addPlayer4.setVisible(true);
        removePlayer4.setVisible(false);
        textField4c.setEditable(true);
        textField4c.setMouseTransparent(false);
        textField4c.setFocusTraversable(true);
        textField4c.clear();
        textField4c.setStyle("-fx-background-color: white;");
        numPlayers--;
    }

    @FXML
    public void doneButton1(ActionEvent e){
        textField1c.setEditable(false);
        textField1c.setMouseTransparent(true);
        textField1c.setFocusTraversable(false);
        textField1c.setStyle("-fx-background-color: lightgray;");
    }
    @FXML
    public void doneButton2(ActionEvent e){
        textField2c.setEditable(false);
        textField2c.setMouseTransparent(true);
        textField2c.setFocusTraversable(false);
        textField2c.setStyle("-fx-background-color: lightgray;");
    }
    @FXML
    public void doneButton3(ActionEvent e){
        textField3c.setEditable(false);
        textField3c.setMouseTransparent(true);
        textField3c.setFocusTraversable(false);
        textField3c.setStyle("-fx-background-color: lightgray;");
    }
    @FXML
    public void doneButton4(ActionEvent e){
        textField4c.setEditable(false);
        textField4c.setMouseTransparent(true);
        textField4c.setFocusTraversable(false);
        textField4c.setStyle("-fx-background-color: lightgray;");
    }
    @FXML
    public void closeSetup(ActionEvent e){
        Stage stage = (Stage) donePlayerSelect.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void playerInitialize(ActionEvent e) throws Exception {
        String musicFile = "src/gameStart1.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Stage stage = (Stage) donePlayerSelect.getScene().getWindow();
        stage.close();
        String player1name = textField1c.getText();
        if(player1name.equals(""))
            player1name = "Player 1";
        player1 = new Player(player1name, "Squire", 0);
        String player2name = textField2c.getText();
        if(player2name.equals(""))
            player2name = "Player 2";
        player2 = new Player(player2name, "Squire", 0);
        player3 = null;
        player4 = null;
        if(numPlayers > 2){
            String player3name = textField3c.getText();
            player3 = new Player(player3name, "Squire", 0);
            System.out.println(player3.rank);
            if(numPlayers == 4){
                String player4name = textField4c.getText();
                player4 = new Player(player4name, "Squire", 0);
                System.out.println(player4.rank);
            }
        }
        System.out.println(player1name + player2.rank);
        System.out.println(player2name + player2.rank);
        play.initializePlayers(player1, player2, player3, player4);
        try {
            play.runGame();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Stage arenaStage2 = new Stage();
        arenaStage2.setTitle("Registration Form FXML Application");
        arenaStage2.setScene(new Scene(sp, 1366, 768));
        sp.getStylesheets().add("/gameboard.css");
        arenaStage2.show();
    }

    @FXML
    public void updateWindow(){
        System.out.println();

        if(currentPlayer > numPlayers)
            currentPlayer = 1;

        if(currentPlayer == 1){
            sp.getChildren().clear();
            designingUI(player1);
            System.out.println("current player 1");
        }
        else if(currentPlayer == 2){
            sp.getChildren().clear();
            designingUI(player2);
            System.out.println("current player 2");
        }
        else if(currentPlayer == 3){
            sp.getChildren().clear();
            designingUI(player3);
            System.out.println("current player 3");
        }
        else if(currentPlayer == 4){
            sp.getChildren().clear();
            designingUI(player4);
            System.out.println("current player 4");
        }
        if(card != null){
            drawStoryCard(card);
        }
    }

    public void designingUI(Player player){


        Label pname = new Label("Player Name");
        pname.setTranslateX(-595);
        pname.setTranslateY(220);
        //pname.setFont(20);
        TextField nameBox = new TextField();
        nameBox.setTranslateY(220);
        nameBox.setMaxWidth(200.0);
        nameBox.setTranslateX(-450);
        //nameBox.setStyle(("-fx-background-color: transparent;"));
        nameBox.setText(player.getName());
        Rectangle playerStats = new Rectangle(0,0,600,300);
        playerStats.setFill(Color.TRANSPARENT);
        playerStats.setStroke(Color.BLACK);
        Button nextPlayer = new Button("Next Player");
        nextPlayer.setTranslateX(370);


        ImageView scroll = new ImageView("/resources/newScroll.png");
        scroll.setFitHeight(300);
        scroll.setFitWidth(300);
        scroll.setTranslateX(470);
        scroll.setTranslateY(-230);

        Label nameT = new Label("Name");
        Label rankT = new Label("Rank");
        Label numShieldsT = new Label("Shields");

        TextField name1 = new TextField();
        TextField name2 = new TextField();
        TextField name3 = new TextField();
        TextField name4 = new TextField();

        TextField rank1 = new TextField();
        TextField rank2 = new TextField();
        TextField rank3 = new TextField();
        TextField rank4 = new TextField();

        TextField numShields1 = new TextField();
        TextField numShields2 = new TextField();
        TextField numShields3 = new TextField();
        TextField numShields4 = new TextField();

        //statsSoFar.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        nameT.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        rankT.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        numShieldsT.setFont(Font.font("Verdana", FontWeight.BOLD, 13));


        if(player2 != null){
            name2.setText(player2.getName());
            rank2.setText(player2.rank);
            numShields2.setText(player2.shields + "");
        }
        if(player3 != null){
            name3.setText(player3.getName());
            rank3.setText(player3.rank);
            numShields3.setText(player3.shields + "");
        }
        if(player4 != null){
            name4.setText(player4.getName());
            rank4.setText(player4.rank);
            numShields4.setText(player4.shields + "");
        }
        name1.setMaxWidth(75);
        name1.setText(player1.getName());
        name2.setMaxWidth(75);
        name3.setMaxWidth(75);
        name4.setMaxWidth(75);
        rank1.setMaxWidth(60);
        rank1.setText(player1.rank);
        rank2.setMaxWidth(60);
        rank3.setMaxWidth(60);
        rank4.setMaxWidth(60);
        numShields1.setMaxWidth(50);
        numShields1.setText(player1.shields + "");
        numShields2.setMaxWidth(50);
        numShields3.setMaxWidth(50);
        numShields4.setMaxWidth(50);

        nameT.setTranslateX(400);
        nameT.setTranslateY(-300);
        rankT.setTranslateX(470);
        rankT.setTranslateY(-300);
        numShieldsT.setTranslateX(530);
        numShieldsT.setTranslateY(-300);

        name1.setTranslateX(400);
        name1.setTranslateY(-270);
        name1.setStyle(("-fx-background-color: transparent;"));
        name2.setTranslateX(400);
        name2.setTranslateY(-240);
        name2.setStyle(("-fx-background-color: transparent;"));
        name3.setTranslateX(400);
        name3.setTranslateY(-210);
        name3.setStyle(("-fx-background-color: transparent;"));
        name4.setTranslateX(400);
        name4.setTranslateY(-180);
        name4.setStyle(("-fx-background-color: transparent;"));

        rank1.setTranslateX(470);
        rank1.setTranslateY(-270);
        rank1.setStyle(("-fx-background-color: transparent;"));
        rank2.setTranslateX(470);
        rank2.setTranslateY(-240);
        rank2.setStyle(("-fx-background-color: transparent;"));
        rank3.setTranslateX(470);
        rank3.setTranslateY(-210);
        rank3.setStyle(("-fx-background-color: transparent;"));
        rank4.setTranslateX(470);
        rank4.setTranslateY(-180);
        rank4.setStyle(("-fx-background-color: transparent;"));

        numShields1.setTranslateX(530);
        numShields1.setTranslateY(-270);
        numShields1.setStyle(("-fx-background-color: transparent;"));
        numShields2.setTranslateX(530);
        numShields2.setTranslateY(-240);
        numShields2.setStyle(("-fx-background-color: transparent;"));
        numShields3.setTranslateX(530);
        numShields3.setTranslateY(-210);
        numShields3.setStyle(("-fx-background-color: transparent;"));
        numShields4.setTranslateX(530);
        numShields4.setTranslateY(-180);
        numShields4.setStyle(("-fx-background-color: transparent;"));




        //Button doneSelectingCards = new Button("Done");

        System.out.println("Num Player " + numPlayers);
        nextPlayer.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                //updateWindow();
                System.out.println("currentPlayer is " + currentPlayer);
                if(currentPlayer == numPlayers){
                    //updateWindow();
                    currentPlayer = 0;
                    currentPlayer++;
                    updateWindow();
                    play.controlFlow();
                }
                else{
                    currentPlayer++;
                    updateWindow();
                }
            }
        });
        sp.getChildren().add(nextPlayer);
        sp.getChildren().add(playerStats);
        sp.getChildren().add(nameBox);
        sp.getChildren().add(pname);
//        sp.getChildren().add(scoreBoard);
        sp.getChildren().add(scroll);
        sp.getChildren().addAll(nameT, rankT, numShieldsT,name1,name2,name3,name4,rank1,rank2,rank3,rank4,numShields1,numShields2,numShields3,numShields4);
        //System.out.println(sp.getChildren().size());
        List<String> cardsList = new ArrayList<>();
        int count = 0;
        for (int y = 0; y < player.cardsInHand.size(); y++) {
            //System.out.println(player.cardsInHand.get(y).GetName());
            //Button b = new Button(player.cardsInHand.get(y).GetName());
            //b.setTranslateX(-600+count);
            //b.setTranslateY(300);
            cardsList.add(player.cardsInHand.get(y).GetName());
            //count = count + 50;
        }
        String location;
        System.out.println(player.hashCode());
        for(int i = 0; i<cardsList.size(); i++){
            //cardsList.get(i).setTranslateX(-600+count);
            //count = count + 100;
            //System.out.println(cardsList.get(i));
            location = getImage(cardsList.get(i));
            Image image = new Image(location);
            ImageView imageView = new ImageView();
            int finalI = i;
            imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    if(event.getClickCount() == 1) {
                        imageView.setTranslateY(240);
                        System.out.println("index of card is  " + finalI);
                        player.cardsSelected.add(finalI);
                    }
                    else if(event.getClickCount() == 2){
                        imageView.setTranslateY(300);
                        System.out.println("index of card is  " + finalI);
                        for(int x = 0; x < player.cardsSelected.size(); x++) {
                            int remover = player.cardsSelected.indexOf(finalI);
                            if(remover != -1) {
                                player.cardsSelected.remove(remover);
                            }
                        }
                    }
                }
            });


            imageView.setFitHeight(125*0.75);
            imageView.setFitWidth(173*0.75);
            imageView.setTranslateX(-600+i*100);
            imageView.setTranslateY(300);
            imageView.setImage(image);
            sp.getChildren().add(imageView);
        }
//        for(int i = 0; i < cardsSelected.size(); i++){
//            System.out.println(cardsList);
//        }
    }

    public void drawStoryCard(Story card){
        System.out.println("Story card drawn on board");
        String location = getImage(card.GetName());
        Image img = new Image(location);
        ImageView imgView = new ImageView();
        imgView.setFitHeight(125*0.75);
        imgView.setFitWidth(173*0.75);
        imgView.setTranslateX(-500);
        imgView.setTranslateY(0);
        imgView.setImage(img);
        sp.getChildren().add(imgView);
    }

    public int switchFunction(Player curPlayer){
        int x = -1;
        if(player1 == curPlayer){
            x = 1;
        }else if(player2 == curPlayer){
            x = 2;
        }else if(player3 == curPlayer){
            x = 3;
        }else if(player4 == curPlayer){
            x = 4;
        }
        return x;
    }

    public void participateInTournamentTie(Story storycard, ArrayList<Player> winner, int eliminatedCompetitors, int[] pos){
         currentPlayer = switchFunction(winner.get(position[0]));
         if(currentPlayer == -1)
             System.out.printf("CRITICAL ERROR 9001 in PITT");

        position[0]++;
        updateWindow();
        System.out.println("Entered - .................. ");
        System.out.println("number of objects in sp before function " + sp.getChildren().size());
        //sp.getChildren().clear();
        Rectangle playerStats2 = new Rectangle(0, 0, 400, 100);
        playerStats2.setTranslateY(-200);
        playerStats2.setFill(Color.LIGHTGREEN);
        playerStats2.setStroke(Color.BLACK);
        Label label2 = new Label("There Has been a tie. Select cards you want \nto play for tie breaker stage and press ok.");
        label2.setTranslateY(-200);
        Button okBtn = new Button("OK");
        okBtn.setTranslateY(-170);
        sp.getChildren().add(playerStats2);
        sp.getChildren().add(label2);
        sp.getChildren().add(okBtn);
        /*for (int i = 0; i < winner.size(); i++) {
            System.out.println("Testing" + winner.get(i).cardsSelected);
            System.out.println("Testing" + winner.get(i).getName());
            winner.get(i).cardsSelected.clear();
        } */

        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if(position[0] < winner.size()){
                    updateWindow();
                    participateInTournamentTie(storycard, winner, eliminatedCompetitors, position);
                }else{
                    updateWindow();
                    play.runTournamentTie(storycard, winner, eliminatedCompetitors);
                }
            }
        });
    }



    public void participateInTournament(Player p){
        updateWindow();
        //playerParticipating.clear();
        //currentPlayer = play.playerTurn; //set this outside from gameboard, also players asked
        Rectangle playerStats = new Rectangle(0,0,760,100);
        playerStats.setTranslateY(-200);
        playerStats.setTranslateX(-50);
        playerStats.setFill(Color.GREENYELLOW);
        playerStats.setOpacity(12);
        playerStats.setStroke(Color.BLACK);
        Label label1 = new Label(p.getName() + " If you would like to participate in the tournament, select the cards you want to play, and press Yes. Otherwise, no.");
        label1.setTranslateY(-200);
        label1.setTranslateX(-50);
        Button yesbtn = new Button("Yes");
        yesbtn.setTranslateX(-75);
        yesbtn.setTranslateY(-175);
        Button nobtn = new Button("No");
        nobtn.setTranslateY(-175);
        nobtn.setTranslateX(-25);
        sp.getChildren().add(playerStats);
        sp.getChildren().add(label1);
        sp.getChildren().add(yesbtn);
        sp.getChildren().add(nobtn);
        //updateWindow();
        yesbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerParticipating.add(true);
                currentPlayer++;
                playersAsked++;
                if(currentPlayer > numPlayers)
                    currentPlayer = 1;
                if(playersAsked < numPlayers) {
                    System.out.println("players asked is less than num players");
                    p.playerInStory = true;

                    sp.getChildren().remove(playerStats);
                    sp.getChildren().remove(label1);
                    sp.getChildren().remove(yesbtn);
                    sp.getChildren().remove(nobtn);

                    updateWindow();
                    participateInTournament(play.playerList.get(currentPlayer - 1));

                }else{
                    p.playerInStory = true;
                    updateWindow();
                    play.runTournament();
                }
            }
        });
        nobtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerParticipating.add(false);
                currentPlayer++;
                playersAsked++;
                if (currentPlayer > numPlayers)
                    currentPlayer = 1;
                if (playersAsked < numPlayers) {
                    System.out.println("players asked is less than num players");
                    p.playerInStory = false;

                    sp.getChildren().remove(playerStats);
                    sp.getChildren().remove(label1);
                    sp.getChildren().remove(yesbtn);
                    sp.getChildren().remove(nobtn);

                    updateWindow();
                    participateInTournament(play.playerList.get(currentPlayer - 1));

                } else {
                    p.playerInStory = false;
                    updateWindow();
                    play.runTournament();
                }
            }
        });

//        if(p.cardsInHand.size() > 12){
//            label1.setText("Select the cards you want to remove");
//
//        }
    }

    public void selectingCardsToRemove(Player p){

        Rectangle selectCards = new Rectangle(0,0,400,200);
        selectCards.setFill(Color.LIGHTBLUE);
        selectCards.setStroke(Color.BLACK);
        Label label1 = new Label(p.getName() + " You have more than 12 cards in hand!!, Select Cards you want to remove otherwise auto remove will happen?");
        label1.setTranslateY(-200);

        sp.getChildren().add(selectCards);
        sp.getChildren().add(label1);

    }

    public String getImage(String cardName){
        String imageLocation;
        switch(cardName){
            case "Thieves" :
                imageLocation = "/resources/thieves.jpg";
                break;
            case "Boar" :
                imageLocation = "/resources/boar.jpg";
                break;
            case "Amour" :
                imageLocation = "/resources/amour.jpg";
                break;
            case "Battle-ax" :
                imageLocation = "/resources/battle_ax.jpg";
                break;
            case "Black Knight" :
                imageLocation = "/resources/black_knight.jpg";
                break;
            case "Dagger" :
                imageLocation = "/resources/dagger.jpg";
                break;
            case "Dragon" :
                imageLocation = "/resources/dragon.jpg";
                break;
            case "Evil Knight" :
                imageLocation = "/resources/evil_knight.jpg";
                break;
            case "Excalibur" :
                imageLocation = "/resources/excalibur.jpg";
                break;
            case "Giant" :
                imageLocation = "/resources/giant.jpg";
                break;
            case "Green Knight" :
                imageLocation = "/resources/green_knight.jpg";
                break;
            case "Horse" :
                imageLocation = "/resources/horse.jpg";
                break;
            case "King Pellinore" :
                imageLocation = "/resources/king_pellinore.jpg";
                break;
            case "Lance" :
                imageLocation = "/resources/lance.jpg";
                break;
            case "Merlin" :
                imageLocation = "/resources/merlin.jpg";
                break;
            case "Mordred" :
                imageLocation = "/resources/mordred.jpg";
                break;
            case "Queen Guinevere" :
                imageLocation = "/resources/queen_guinevere.jpg";
                break;
            case "Queen Iseult" :
                imageLocation = "/resources/queen_iseult.jpg";
                break;
            case "Robber Knight" :
                imageLocation = "/resources/robber_knight.jpg";
                break;
            case "Saxon Knight" :
                imageLocation = "/resources/saxon_knight.jpg";
                break;
            case "Saxons" :
                imageLocation = "/resources/saxons.jpg";
                break;
            case "Sir Galahad" :
                imageLocation = "/resources/sir_galahad.jpg";
                break;
            case "Sir Gawain" :
                imageLocation = "/resources/sir_gawain.jpg";
                break;
            case "Sir Lancelot" :
                imageLocation = "/resources/sir_lancelot.jpg";
                break;
            case "Sir Percival" :
                imageLocation = "/resources/sir_percival.jpg";
                break;
            case "Sir Tristan" :
                imageLocation = "/resources/sir_tristan.jpg";
                break;
            case "Sword" :
                imageLocation = "/resources/sword.jpg";
                break;
            case "Test of Valor" :
                imageLocation = "/resources/test_valor.jpg";
                break;
            case "Test of Morgan Le Fey" :
                imageLocation = "/resources/test_morganlefey.jpg";
                break;
            case "Test of the Questing Beast" :
                imageLocation = "/resources/test_questingbeast.jpg";
                break;
            case "Test of Temptation" :
                imageLocation = "/resources/test_temptation.jpg";
                break;
            case "AT CAMELOT" :
                imageLocation = "/resources/tournament_atcamelot.jpg";
                break;
            case "AT ORKNEY" :
                imageLocation = "/resources/tournament_atorkney.jpg";
                break;
            case "AT TINTAGEL" :
                imageLocation = "/resources/tournament_attintagel.jpg";
                break;
            case "AT YORK" :
                imageLocation = "/resources/tournament_atyork.jpg";
                break;
            default:
                imageLocation = "/resources/squire.jpg";
        }
        return imageLocation;
    }
}
