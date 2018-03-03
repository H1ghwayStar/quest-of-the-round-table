package sample;


import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;

import com.google.common.io.Resources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.util.Pair;


import java.util.ArrayList;

import java.lang.*;




import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;

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
    final int[] BidsSoFar = {0};
    private final int IMGWIDTH = 200;
    private final int IMGHEIGHT = 300;
    final int[] minBidsTest = {0};
    //int[] playerParticipating = {};
   // final int[] playerParticipating = {2};
    final List<Boolean> playerParticipating = new ArrayList<>();
    public final int[] position = {0};
    //public final int[] questEntryNumber = {0};
    //public final int[] stageNumber = {0};
    StackPane sp = new StackPane();
    private int counter2 = 1;
    public final int[] excessCardsPlayersAsked = {0};
    private final int[] biddingCount = {0};
    public Story card = null;
    private static Controller _instance;
    private Story cardForDrawing;
    StackPane allAlies = new StackPane();
    Gameboard play;

    public Alert myAlerts;

    public Controller() throws IOException {
        myAlerts = new Alert();
        _instance = this;
        play = new Gameboard();

    }

    public static Controller getInstance(){
        return _instance;
    }

    //static Logger logger = Logger.getLogger(Controller.class);
    //FileHandler fh = new FileHandler("C:/temp/MyLogFile.log", true);

    public static int currentPlayer = 1;
    public static int playersAsked = 0;

    public void quitClicked(ActionEvent e){
//        String musicFile = ClassLoader.getSystemResource("buttonClick.wav").toString();
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
        myAlerts.displayQuit("Quitting...", "Are you sure?");
    }

    public void instructionClicked(ActionEvent e){
//        String musicFile = "src/buttonClick.wav";
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
//        myAlerts.displayInstructions();
    }

    public void settingsClicked(ActionEvent e){
//        String musicFile = "src/buttonClick.wav";
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
        myAlerts.displaySettings();
    }


    public void playClicked(ActionEvent e){
        try {
//            String musicFile = "src/buttonClick.wav";
//
//            Media sound = new Media(new File(musicFile).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.play();
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

        comboBox1d.getItems().addAll("Normal Mode");
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
//        String musicFile =  ClassLoader.getSystemResource("gameStart1.wav").toString();
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
        //logger.trace("Entering player initialize");
        Stage stage = (Stage) donePlayerSelect.getScene().getWindow();
        stage.close();
        String player1name = textField1c.getText();
        if(player1name.equals(""))
            player1name = "Player 1";
        player1 = new Player(player1name, "Squire", 0);
        String player2name = textField2c.getText();
        if(player2name.equals(""))
            player2name = "Player 2";
        if(comboBox2d.getValue().toString() == "Normal Mode"){
            player2 = new Player(player2name, "Squire", 0);
        } else if(comboBox2d.getValue().toString() == "Easy AI"){
            System.out.println("comboboxes are gay");
            player2 = new AIPlayer(player2name, "Squire", StrategyFactory.getStrategyMethod("passive"));
        }else if(comboBox2d.getValue().toString() == "Difficult AI"){
            player3 = new AIPlayer(player2name, "Squire", StrategyFactory.getStrategyMethod("aggressive"));
        }

        player3 = null;
        player4 = null;
        if(numPlayers > 2){
            String player3name = textField3c.getText();
            if(player3name.equals("")){
                player3name = "Player 3";
            }
            if(comboBox3d.getValue().toString() == "Normal Mode") {
                player3 = new Player(player3name, "Squire", 0);
            }else if(comboBox3d.getValue().toString() == "Easy AI"){
                player3 = new AIPlayer(player3name, "Squire", StrategyFactory.getStrategyMethod("passive"));
            }else if(comboBox3d.getValue().toString() == "Difficult AI"){
                player3 = new AIPlayer(player3name, "Squire", StrategyFactory.getStrategyMethod("aggressive"));
            }
            //System.out.println(player3.rank);
            if(numPlayers == 4){
                String player4name = textField4c.getText();
                if(player4name.equals("")){
                    player4name = "Player 4";
                }
                if(comboBox4d.getValue().toString() == "Normal Mode") {
                    player4 = new Player(player4name, "Squire", 0);
                }else if(comboBox4d.getValue().toString() == "Easy AI"){
                    player4 = new AIPlayer(player4name, "Squire", StrategyFactory.getStrategyMethod("passive"));
                }else if(comboBox4d.getValue().toString() == "Difficult AI"){
                    player4 = new AIPlayer(player4name, "Squire", StrategyFactory.getStrategyMethod("aggressive"));
                }
                //player4 = new Player(player4name, "Squire", 0);
                System.out.println(player4.rank);
            }
        }
        //System.out.println(player1name + player2.rank);
        //System.out.println(player2name + player2.rank);
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
    public void updateWindow() throws IOException {
        System.out.println("");
        //displayAlliesInPlay(switchFunctionFromInt(currentPlayer));

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

    public void designingUI(Player player) throws IOException {

/*        ScrollPane cardHolder = new ScrollPane();
        cardHolder.setPrefSize(600,200);
        cardHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.getChildren().add(cardHolder);
        BorderPane.setMargin(cardHolder, new Insets(0,0,10,10));

*/

        displayAlliesInPlay();

        HBox cardHolder = new HBox(-60);
        cardHolder.setTranslateY(550);
        cardHolder.setTranslateX(-80);
        cardHolder.setMaxWidth(700);
        sp.getChildren().add(cardHolder);




        Label pname = new Label("Player Name");
        pname.setTranslateX(-595);
        pname.setTranslateY(180);
        //pname.setFont(20);
        TextField nameBox = new TextField();
        nameBox.setTranslateY(180);
        nameBox.setMaxWidth(200.0);
        nameBox.setTranslateX(-450);
        //nameBox.setStyle(("-fx-background-color: transparent;"));
        nameBox.setText(player.getName());
        Button nextPlayer = new Button("Next Player");
        nextPlayer.setTranslateX(370);

        //String loc = ResourceBundle.getBundle("newScroll.png").toString();
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        File file = new File(loader.getResource("newScroll.png").getFile());
//        InputStream is = loader.getResourceAsStream("newScroll.png");
//        System.out.println(file.getAbsolutePath());

        URL imageURL = ClassLoader.getSystemResource("newScroll.png");
        //
        //System.out.println("url of the new scroll is: " +imageURL.toString());
        //System.out.println(is);
        Image v = new Image(ClassLoader.getSystemResource("newScroll.png").toString());
        ImageView scroll = new ImageView();
        scroll.setImage(v);
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
                    try {
                        updateWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        play.controlFlow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    currentPlayer++;
                    try {
                        updateWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        sp.getChildren().add(nextPlayer);
        sp.getChildren().add(nameBox);
        sp.getChildren().add(pname);
//        sp.getChildren().add(scoreBoard);
        sp.getChildren().add(scroll);
        sp.getChildren().addAll(nameT, rankT, numShieldsT,name1,name2,name3,name4,rank1,rank2,rank3,rank4,numShields1,numShields2,numShields3,numShields4);
        //System.out.println(sp.getChildren().size());
        List<String> cardsList = new ArrayList<>();
        //ListView cardList = new ListView();
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
        //System.out.println(player.hashCode());
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
                        imageView.setTranslateY(-80);
                        System.out.println("index of card  selected is  " + finalI);
                        player.cardsSelected.add(finalI);
                    }
                    else if(event.getClickCount() == 2){
                        imageView.setTranslateY(0);
                        System.out.println("index of card deselected is  " + finalI);
                        for(int x = 0; x < player.cardsSelected.size(); x++) {
                            int remover = player.cardsSelected.indexOf(finalI);
                            if(remover != -1) {
                                player.cardsSelected.remove(remover);
                            }
                        }
                    }
                }
            });
            //Bounds boundsInScene;
//            imageView.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
//                @Override
//                public void handle(javafx.scene.input.MouseEvent event) {
//                    imageView.setFitHeight(110);
//                    imageView.setFitWidth(140);
////                    imageView.setTranslateZ(100);
//                    //boundsInScene = imageView.localToScene(imageView.getBoundsInLocal());
//                    imageView.toFront();
//
//                }
//            });
//
//            imageView.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
//                @Override
//                public void handle(javafx.scene.input.MouseEvent event) {
//                    imageView.setFitHeight(93.75);
//                    imageView.setFitWidth(129.75);
//                    imageView.toFront();
//                }
//            });



            imageView.setFitHeight(213);
            imageView.setFitWidth(150);
            //imageView.setTranslateX(-600+i*100);
            //imageView.setTranslateY(300);
            imageView.setImage(image);

            cardHolder.getChildren().add(imageView);
            //cardHolder.setContent(imageView);

        }
//        for(int i = 0; i < cardsSelected.size(); i++){
//            System.out.println(cardsList);
//        }
    }

    public void drawStoryCard(Story card) throws IOException {
        String location;
        if(card == null){
            location = getImage(cardForDrawing.GetName());
        } else {
            if(card != cardForDrawing){
                cardForDrawing = card;
            }
            location = getImage(card.GetName());
            cardForDrawing = card;
        }
        //displayAlliesInPlay(switchFunctionFromInt(currentPlayer)); //maybe maybe not
        Image img = new Image(location);
        ImageView imgView = new ImageView();
        imgView.setFitHeight(IMGHEIGHT);
        imgView.setFitWidth(IMGWIDTH);
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

     public Player switchFunctionFromInt(int currPlayer){
        Player x = null;
        switch (currPlayer) {
            case 1:
                x = player1;
                break;
            case 2:
                x = player2;
                break;
            case 3:
                x = player3;
                break;
            case 4:
                x = player4;
                break;
        }
         //System.out.println();
        return x;
    }

    public void participateInTournamentTie(Story storycard, ArrayList<Player> winner, int eliminatedCompetitors, int[] pos) throws IOException {
         currentPlayer = switchFunction(winner.get(position[0]));
         if(currentPlayer == -1)
             System.out.printf("CRITICAL ERROR 9001 in PITT");


        Player tempPlayer = winner.get(position[0]);
        position[0]++;
        updateWindow();
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

        if(tempPlayer instanceof  AIPlayer){
            System.out.println("Checking if AI Player will play in Tournament tie");
            ((AIPlayer)tempPlayer).strategy.tournamentStrategy((AIPlayer)tempPlayer, true);
            tieOkay(storycard, winner, eliminatedCompetitors, pos);
        }

        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tieOkay(storycard, winner, eliminatedCompetitors, pos);
            }
        });
    }

    public void tieOkay(Story storycard, ArrayList<Player> winner, int eliminatedCompetitors, int[] pos){
        if(position[0] < winner.size()){
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                participateInTournamentTie(storycard, winner, eliminatedCompetitors, position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                play.runTournamentTie(storycard, winner, eliminatedCompetitors);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void participateInTournament(Player p, Story story) throws IOException {
        updateWindow();
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
        drawStoryCard(story);
        //updateWindow();

        if(p instanceof  AIPlayer){
            System.out.println("Checking if AI Player will play in Tournament");
            boolean willPlay = ((AIPlayer)p).strategy.tournamentStrategy((AIPlayer)p, false);
            if(willPlay) {
                System.out.println("willPlay true");
                saverYes(p, story);
                return;
            }
            else {
                System.out.println("willPlay false");
                saverNo(p, story);
                return;
            }
        }

        yesbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                saverYes(p,story);
                sp.getChildren().remove(playerStats);
                sp.getChildren().remove(label1);
                sp.getChildren().remove(yesbtn);
                sp.getChildren().remove(nobtn);
                return;
            }
        });
        nobtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                saverNo(p, story);
                sp.getChildren().remove(playerStats);
                sp.getChildren().remove(label1);
                sp.getChildren().remove(yesbtn);
                sp.getChildren().remove(nobtn);
                return;
            }
        });
//        if(p.cardsInHand.size() > 12){
//            label1.setText("Select the cards you want to remove");
//
//        }
    }

    public void saverYes(Player p, Story story){
        System.out.println("Yes button fired");
        playerParticipating.add(true);
        currentPlayer++;
        playersAsked++;
        if(currentPlayer > numPlayers)
            currentPlayer = 1;

        if(playersAsked < numPlayers) {
            System.out.println("players asked is less than num players");
            p.playerInStory = true;



            try {
                updateWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                participateInTournament(play.playerList.get(currentPlayer - 1), story);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }else{
            p.playerInStory = true;
            try {
                updateWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                play.runTournament();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void saverNo(Player p, Story story){
        System.out.println("No button fired");
        playerParticipating.add(false);
        currentPlayer++;
        playersAsked++;
        if (currentPlayer > numPlayers)
            currentPlayer = 1;
        if (playersAsked < numPlayers) {
            System.out.println("players asked is less than num players");
            p.playerInStory = false;

            try {
                updateWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                participateInTournament(play.playerList.get(currentPlayer - 1), story);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } else {
            p.playerInStory = false;
            try {
                updateWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                play.runTournament();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void runTest(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                        int hostCardsBeforeQuest){
        Label question = new Label();
        Adventure a = questStages.get(stageNum[0]).getKey().get(0);
        Test testCard = null;
        try{
            testCard = (Test) a;
        }catch(Exception e){//error in cast, this should never happen
            e.printStackTrace();
        }

        if(testCard == null)
            return;
        if(biddingCount[0] == 0)
            minBidsTest[0] = testCard.GetMinBids();

        Rectangle alertBox = new Rectangle(0, 0, 760, 100);
        alertBox.setTranslateY(-200);
        alertBox.setTranslateX(-50);
        alertBox.setFill(Color.LIGHTGOLDENRODYELLOW);
        question.setText("This stage is a test. Enter the cards you would like for this bidding war. Press ok when you're done.");
        question.setTranslateY(-200);
        question.setTranslateX(-50);
        sp.getChildren().addAll(alertBox,question);

        ArrayList<Adventure> tempCardArray = questStages.get(stageNum[0]).getKey();
        try {
            drawQuestStagesBack(0, tempCardArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player p = questEntry.get(questEntryNum[0]).getKey();
        final ArrayList<Pair<Integer, Integer>> playerBids = play.biddingWar(testCard, questEntry, cardForDrawing.GetName()); //max bids, free bids
        //drawQuestStagesBack();
        BidsSoFar[0] = minBidsTest[0];
        Button incr = new Button("+");
        incr.setTranslateX(-30);
        incr.setTranslateY(-30);
        Button decr = new Button("-");
        decr.setTranslateX(-30);
        decr.setTranslateY(30);
        TextField display = new TextField(minBidsTest[0]+"");
        display.setTranslateX(-30);
        display.setTranslateY(0);
        display.setMaxWidth(130);
        Button ok = new Button("Ok");
        ok.setTranslateX(-105);
        ok.setTranslateY(-175);
        Button no = new Button("No");
        no.setTranslateX(-55);
        no.setTranslateY(-175);
        sp.getChildren().addAll(incr, decr, display, ok, no);

        incr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(BidsSoFar[0] == playerBids.get(0).getKey()){
                    display.setText(BidsSoFar[0] + "");
                    display.setStyle("-fx-background-color: Red;");
                }
                else if(BidsSoFar[0] < playerBids.get(0).getKey()){
                    BidsSoFar[0]++;
                    display.setText(BidsSoFar[0] + "");
                    display.setStyle("-fx-background-color: White;");
                }
            }
        });

        decr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                if(minBidsTest[0] != 0){
//                    BidsSoFar[0] = minBidsTest[0];
//                    display.setText(BidsSoFar[0] + "");
//                }
                if((BidsSoFar[0] == minBidsTest[0])){
                    display.setText(BidsSoFar[0]+"");
                    display.setStyle("-fx-background-color: Red;");
                }
                else if(BidsSoFar[0] > minBidsTest[0]){
                    BidsSoFar[0]--;
                    display.setText(BidsSoFar[0] + "");
                    display.setStyle("-fx-background-color: White;");
                }
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runTestOkay(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                if(questEntry.size() == 1){
                    no.setVisible(false);
                    sp.getChildren().removeAll(display, incr, decr);
                    question.setText("Select " + BidsSoFar[0] + " cards to remove");
                    ok.setVisible(false);
                    //stageNum[0]++;
                    //runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                }
                return;
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runTestNo(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                if(questEntry.size() == 1) {
                    display.setText(BidsSoFar[0] + "");
                    question.setText("Select " + BidsSoFar[0] + " cards to remove");
                    ok.setVisible(false);
                }
                return;
            }
        });


        //return;
    }

    public void runTestOkay(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                            int hostCardsBeforeQuest){
        minBidsTest[0] = BidsSoFar[0] + 1;
        biddingCount[0]++;
        int questEntryIter = 0;
        Player x = switchFunctionFromInt(currentPlayer);
        if(questEntry.size() > 0){
            if(questEntry.size() == 1){
                x.cardsSelected.clear();
                removeCardsFromBiddingWar(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest, x, BidsSoFar[0]);
                //stageNum[0]++;
                //runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                return;
            }

            for(int i =0; i < questEntry.size(); i++){
                if(questEntry.get(i).getKey().equals(x)){
                    questEntryIter = i;
                }
            }
            questEntryIter++;
            if(questEntryIter < questEntry.size()){
                x = questEntry.get(questEntryIter).getKey();
                currentPlayer = switchFunction(x);
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runTest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
            }else {
                x = questEntry.get(0).getKey();
                currentPlayer = switchFunction(x);
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runTest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
            }
        }
    }

    public void runTestNo(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                          int hostCardsBeforeQuest){
        biddingCount[0]++;
        Pair<Player, Integer> p = null;
        Player x = switchFunctionFromInt(currentPlayer);
        for(int i = 0; i <questEntry.size(); i++){
            if(questEntry.get(i).getKey().equals(x)){
                p = questEntry.get(i);
                questEntry.remove(p);
            }
        }
        if(questEntry.size() == 1){
            BidsSoFar[0]--;
            x = questEntry.get(0).getKey();
            currentPlayer = switchFunction(x);
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            removeCardsFromBiddingWar(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest, x, BidsSoFar[0]);
        }
        else {
            if (questEntry.size() != 0) {
                currentPlayer = switchFunction(questEntry.get(0).getKey());
                System.out.println("current player is: " + currentPlayer);
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runTest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
            } else if (questEntry.size() == 0) {
                biddingCount[0] = 0;
                try {
                    play.hostGetCards(hostCardsBeforeQuest, questStages.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }



    public void removeCardsFromBiddingWar(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                                          int hostCardsBeforeQuest, Player p, int cardsToRemove){
        Rectangle alertBoxBiddingWarWon = new Rectangle(0, 0, 760, 100);
        alertBoxBiddingWarWon.setTranslateY(-200);
        alertBoxBiddingWarWon.setTranslateX(-50);
        alertBoxBiddingWarWon.setFill(Color.LIGHTGOLDENRODYELLOW);
        Label questionBiddingWarWon = new Label();
        questionBiddingWarWon.setText("Select " + BidsSoFar[0] + " cards to remove");
        questionBiddingWarWon.setTranslateY(-200);
        questionBiddingWarWon.setTranslateX(-50);
        Button removeCardsBids = new Button("Ok");
        sp.getChildren().addAll(alertBoxBiddingWarWon, questionBiddingWarWon);
        removeCardsBids.setTranslateY(-175);
        removeCardsBids.setTranslateX(-55);
        sp.getChildren().add(removeCardsBids);

        if(p instanceof  AIPlayer){
            ((AIPlayer)p).strategy.discardStrategy((AIPlayer) p, cardsToRemove);
            removeCardBid(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest, p);
            return;
        }

        removeCardsBids.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCardBid(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest, p);
                return;
            }
        });

    }

    public void removeCardBid(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                              int hostCardsBeforeQuest, Player p){
        stageNum[0]++;
        System.out.println("size of cards in hand for player p is " + p.cardsInHand.size());
        Collections.sort(p.cardsSelected); // i am assuming this is sorting it in acceding order, according to what i saw on google.
        ArrayList<Integer> cardsSelectedTemp = p.cardsSelected;
        ArrayList<Adventure> cardsInHandTemp = p.cardsInHand;
        ArrayList<Adventure> cardsToRemove = new ArrayList<>();
        for(int i = 0; i < cardsSelectedTemp.size(); i++){
            for(int j = 0; j < cardsInHandTemp.size(); j++){
                if(cardsSelectedTemp.get(i) == j){
                    cardsToRemove.add(cardsInHandTemp.get(j));
                }
            }
        }
        for(int y = cardsSelectedTemp.size() - 1; y > -1; y--){
            for(int a = cardsInHandTemp.size() - 1; a > -1; a--){
                if(cardsToRemove.get(y).equals(cardsInHandTemp.get(a))){
                    p.cardsInHand.remove(cardsToRemove.get(y));
                }
            }
        }
        System.out.println("size of cards in hand for player is now " + p.cardsInHand.size());
        p.cardsSelected.clear();
        try {
            runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return;
    }

    public void runQuest(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                            int hostCardsBeforeQuest) throws IOException {

        if(questEntry.size() == 0){
            play.afterQuest();//untested
            return;
        }
        //updateWindow();
        //drawQuestStages(null, 5); //comment out after testing
        //System.out.println("stageNum " + stageNum[0] + " | questStages.size() " + questStages.size());
        if(stageNum[0] != questStages.size()) {
            System.out.println("In runQuest (controller)");
            if(questEntryNum[0] >= questEntry.size()) //untested
                questEntryNum[0] = 0;
            currentPlayer = switchFunction(questEntry.get(questEntryNum[0]).getKey());
            updateWindow();
            ArrayList<Adventure> questStageToDrawCards = questStages.get(stageNum[0]).getKey();
            //drawQuestStagesBack(questStageToDrawCards.size());
            Rectangle alertBox = new Rectangle(0, 0, 760, 100);
            alertBox.setTranslateY(-200);
            alertBox.setTranslateX(-50);
            alertBox.setFill(Color.LIGHTGOLDENRODYELLOW);
            Label question = new Label();
            question.setTranslateY(-200);
            question.setTranslateX(-50);
            Button ok = new Button("Ok");
            ok.setTranslateX(-75);
            ok.setTranslateY(-175);
            Adventure a = questStages.get(stageNum[0]).getKey().get(0);
            String location = getImage(a.name);
            drawStoryCard(null);
            if(a instanceof Foe) {//so we don't double draw for test here
                questEntry.get(questEntryNum[0]).getKey().cardsInHand.add((Adventure) play.draw("Adventure"));
                drawQuestStagesBack(questStageToDrawCards.size(),null);
            }
            sp.getChildren().addAll(alertBox, question, ok);
            String nextStage = "foe";
            if (a instanceof Test) {
                nextStage = "test";
                question.setText("This stage is a test. Enter the cards you would like for this bidding war. Press ok when you're done.");
                ArrayList <Adventure> tempCards = questStages.get(stageNum[0]).getKey();
                drawQuestStagesBack(questStageToDrawCards.size(), tempCards);
                //questStageToDrawCards = questStages.get(stageNum[0]).getKey();
                //drawQuestStages(null, 1);
            } else {
                question.setText("Select cards you would like to play for this stage and then press ok to continue. The card is a " + nextStage);
            }
            //int counter = 0;
            //Adventure a = questStages.get(stageNum[0]).getKey().get(0);

            if (nextStage.equals("test")) {
                ok.setVisible(false);
                runTest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                //
                // System.out.println("Run Test Completed --------------------------------------------------------------------------------->");
                //updateWindow();
                //stageNum[0]++; - ask sharath about what to do
                return;
            }

            Player p = questEntry.get(questEntryNum[0]).getKey();

            if(p instanceof  AIPlayer){
                //System.out.println("OH EM GEE");
                ((AIPlayer)p).strategy.questStrategyFoe((AIPlayer) p, stageNum[0]);
                runQuestOkay(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                return;
            }

            ok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    runQuestOkay(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                    return;
                }
            });
        }
        else {
            play.afterQuest(); //untested
            return;
        }
    }

    public void runQuestOkay(ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                             int hostCardsBeforeQuest){
        //System.out.println("questEntryNum[0] " + questEntryNum[0]);

        if (questEntry.size() == 0) {
            System.out.println("Everyone has been eliminated (above)");
            try {
                play.hostGetCards(hostCardsBeforeQuest, questStages.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        questEntry.set(questEntryNum[0], new Pair<>(questEntry.get(questEntryNum[0]).getKey(), play.validCards(null, questEntry.get(questEntryNum[0]).getKey(), "yes")));
        questEntryNum[0]++;

        if (questEntryNum[0] >= questEntry.size()) { //so everyone has been asked, could be greater because of eliminations
            play.passedFoeStage(questEntry, questStages.get(stageNum[0]).getValue(), stageNum[0]);
            ArrayList<Adventure> tempCardsForStage = questStages.get(stageNum[0]).getKey();
           // System.out.println("size of tempCardsForStage is " + tempCardsForStage.size());
            drawQuestCardFront(tempCardsForStage, questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
            return;
        }
        currentPlayer = switchFunction(questEntry.get(questEntryNum[0]).getKey());
        try {
            updateWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        questEntry.get(questEntryNum[0]).getKey().cardsSelected.clear();
        try {
            //ArrayList<Adventure> cardsToFlipForStage = questStages.get(stageNum[0]).getKey();
            ArrayList<Adventure> testOrNotCard = questStages.get(stageNum[0]).getKey();
            drawQuestStagesBack(questStages.get(stageNum[0]).getKey().size(), testOrNotCard);
            runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawQuestStagesBack(int cardsInStage, ArrayList<Adventure> testCard) throws IOException {
        updateWindow();
        HBox questStage = new HBox(5);
        questStage.setMaxHeight(IMGHEIGHT*.6);
        String loc;
        sp.getChildren().add(questStage);
        questStage.setTranslateX(300);
        questStage.setTranslateY(0);
        questStage.getChildren().clear();

        Adventure a;
        if((testCard == null) || (testCard.size() == 0)){
            a = null;
        } else {
            a = testCard.get(0);
        }
        if((a instanceof Foe) || a == null){
            loc = getImage("back");
            Image questImage1 = new Image(loc);
            ImageView imgViewTester = new ImageView();
            imgViewTester.setFitHeight(IMGHEIGHT*.6);
            imgViewTester.setFitWidth(IMGWIDTH*.6);
            questStage.getChildren().add(imgViewTester);
            imgViewTester.setImage(questImage1);
        } else {
            loc = getImage(a.name);
            Image questImage2 = new Image(loc);
            ImageView imgViewTester2 = new ImageView();
            imgViewTester2.setFitHeight(IMGHEIGHT*.6);
            imgViewTester2.setFitWidth(IMGWIDTH*.6);
            questStage.getChildren().add(imgViewTester2);
            imgViewTester2.setImage(questImage2);
            Rectangle alertBox2 = new Rectangle(0, 0, 760, 100);
            alertBox2.setTranslateY(-200);
            alertBox2.setTranslateX(-50);
            alertBox2.setFill(Color.LIGHTGOLDENRODYELLOW);
            Label question2 = new Label();
            question2.setText("This stage is a test. Enter the cards you would like for this bidding war. Press ok when you're done.");
            question2.setTranslateY(-200);
            question2.setTranslateX(-50);
            sp.getChildren().addAll(alertBox2,question2);
        }
        return;
    }

    public void drawQuestCardFront(ArrayList<Adventure> card, ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages,  ArrayList <Pair<Player, Integer>> questEntry, final int[] stageNum, final int[] questEntryNum,
                                   int hostCardsBeforeQuest){
        System.out.println("drawQuestStages ran");
        sp.getChildren().clear();
        HBox questStageReveal = new HBox(5);
        questStageReveal.setMaxHeight(IMGHEIGHT*.6);
        String loc;
        sp.getChildren().add(questStageReveal);
        questStageReveal.setTranslateX(300);
        questStageReveal.setTranslateY(0);
        ArrayList<Adventure> cardsToFlipInQuest = questStages.get(stageNum[0]).getKey();
        for(int i=0; i<cardsToFlipInQuest.size(); i++){
            String location = getImage(cardsToFlipInQuest.get(i).name);
            Image questImage1 = new Image(location);
            ImageView cardToFlip = new ImageView();
            cardToFlip.setImage(questImage1);
            cardToFlip.setFitHeight(IMGHEIGHT*.6);
            cardToFlip.setFitWidth(IMGWIDTH*.6);
            questStageReveal.getChildren().add(cardToFlip);
        }
        Button drawQuestFrontOk = new Button();
        if(questEntry.size() == stageNum[0]){
            drawQuestFrontOk.setText("End quest");
        }else {
            drawQuestFrontOk.setText("Next stage");
        }
        Rectangle text = new Rectangle(0,0,400,200);
        text.setTranslateY(-200);
        text.setFill(Color.LIGHTSEAGREEN);
        Label forRectangle = new Label();
        forRectangle.setText("End of the stage, here is the card: ");
        forRectangle.setTranslateY(-200);
        sp.getChildren().addAll(drawQuestFrontOk, text, forRectangle);
        drawQuestFrontOk.setTranslateX(0);
        drawQuestFrontOk.setTranslateY(-180);
        drawQuestFrontOk.toFront();
        drawQuestFrontOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (questEntry.size() == 0) {
                    System.out.println("Everyone has been eliminated");
                    try {
                        play.hostGetCards(hostCardsBeforeQuest, questStages.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                            /*
                            ORDER
                            call
                            play.hostGetCards(); which calls
                            controller.removeExcessCards(Playerlist p) (runs for host first, then all players) which calls
                            play.afterQuest();
                            */
                    return;
                }

//                        try {
//                            Thread.sleep(250);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                questEntryNum[0] = 0;
                stageNum[0]++;
                if (stageNum[0] == questStages.size()) {
                    System.out.println("quest is done");
                    for(int i = 0; i < questEntry.size(); i++){
                        questEntry.get(i).getKey().shields += questStages.size();
                    }
                    try {
                        play.hostGetCards(hostCardsBeforeQuest, questStages.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        updateWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //DO ORDER HERE AS well
                    return;
                }

                currentPlayer = switchFunction(questEntry.get(questEntryNum[0]).getKey());
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                questEntry.get(questEntryNum[0]).getKey().cardsSelected.clear();
                try {
                    ArrayList<Adventure> cardsToFlipForStage = questStages.get(stageNum[0]).getKey();
                    drawQuestStagesBack(cardsToFlipForStage.size(), cardsToFlipForStage);
                    runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        /*else {
            play.afterQuest(); //untested
            return;
        } */


        return;
    }

    public void removeExcessCards(ArrayList<Player> cardStripPlayers) throws IOException {

        if(cardStripPlayers.size() == excessCardsPlayersAsked[0] ){
            play.stripCards();
            play.afterQuest();
            return;
        }else {
            System.out.println("Size of cih is "+ cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsInHand.size());
            //this line below sets the window to the first player in cardstripplayer to be asked
            cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected.clear(); // clear the players cards selected list
            currentPlayer = switchFunction(cardStripPlayers.get(excessCardsPlayersAsked[0]));
            updateWindow();
            System.out.println("Player " + currentPlayer + " cards selected size is " + cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected.size());
            Rectangle alertBox = new Rectangle(0, 0, 760, 100);
            alertBox.setTranslateY(-200);
            alertBox.setTranslateX(-50);
            alertBox.setFill(Color.YELLOWGREEN);
            Label question = new Label();
            question.setText("Select the cards you would like to remove. and then press ok");
            question.setTranslateY(-200);
            question.setTranslateX(-50);
            Button ok = new Button("Ok");
            ok.setTranslateX(-75);
            ok.setTranslateY(-175);
            sp.getChildren().addAll(alertBox, question, ok);
            //Button b = new Button("Ok");

            Player p = cardStripPlayers.get(excessCardsPlayersAsked[0]);
            if(p instanceof AIPlayer){
                ((AIPlayer)p).strategy.discardStrategy((AIPlayer) p, p.cardsInHand.size() - Player.MAX_CARDS_HAND);
                removeExcessCardsOkay(cardStripPlayers);
                return;
            }

            ok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removeExcessCardsOkay(cardStripPlayers);
                    return;
                }
            });

        }
    }

    public void removeExcessCardsOkay(ArrayList<Player> cardStripPlayers){
        System.out.println("cards selected size is: "+cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected.size());
        Collections.sort(cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected); // i am assuming this is sorting it in acceding order, according to what i saw on google.
        ArrayList<Integer> cardsSelectedTemp = cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected;
        ArrayList<Adventure> cardsInHandTemp = cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsInHand;
        ArrayList<Adventure> cardsToRemove = new ArrayList<>();
        for(int i = 0; i < cardsSelectedTemp.size(); i++){
            for(int j = 0; j < cardsInHandTemp.size(); j++){
                if(cardsSelectedTemp.get(i) == j){
                    cardsToRemove.add(cardsInHandTemp.get(j));
                }
            }
        }
        for(int x = cardsSelectedTemp.size() - 1; x > -1; x--){
            for(int a = cardsInHandTemp.size() - 1; a > -1; a--){
                if(cardsToRemove.get(x).equals(cardsInHandTemp.get(a))){
                    cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected.remove(cardsSelectedTemp.get(x));
                }
            }
        }
                    /*
                    for(int i =cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected.size(); i > 0; i--){ //cards selected in reverse
                        for(int j= cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsInHand.size() - 1; j > 0; j--){ // cards in hand in reverse
                            //cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsInHand.remove(cardStripPlayers.get(excessCardsPlayersAsked[0]).cardsSelected[i]);
                        }
                    }*/
        excessCardsPlayersAsked[0]++;
        if (excessCardsPlayersAsked[0] == cardStripPlayers.size()) {
            excessCardsPlayersAsked[0] = 0;
            play.stripCards();
            try {
                play.afterQuest();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            removeExcessCards(cardStripPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hostSelectStages(Player host, Quest questCard) throws IOException {
        //move this logic inside a button click after so control flow is  proper. For now it is auto selected
        ArrayList<Pair<ArrayList<Adventure>, Integer>> stages = play.autoSelectStages(host, questCard.GetNumStages(), questCard.GetLinkedFoe()); //This is the auto select option
        play.isQuest(questCard, host, stages);
    }

    public void participateInQuest(Player p, Quest q) throws IOException {
        updateWindow();
        System.out.println("We get to participateInQuest");
        if(p.isHost){
            //System.out.println(p.getName() + " is already the host.");
            //System.out.println("curr player " + currentPlayer +  " | " + " players asked " +  playersAsked);
            currentPlayer++;
            playersAsked++;
            if(currentPlayer > numPlayers)
                currentPlayer = 1;
            participateInQuest(play.playerList.get(currentPlayer - 1), q);
            return;
        }
        Rectangle alertBox = new Rectangle(0,0, 760, 100);
        alertBox.setTranslateY(-200);
        alertBox.setTranslateX(-50);
        alertBox.setFill(Color.LIGHTBLUE);
        alertBox.setStroke(Color.BLACK);
        Label question = new Label(p.getName() + " Would you like to participate in this quest? ");
        question.setTranslateY(-200);
        question.setTranslateX(-50);
        Button yes = new Button("Yes");
        yes.setTranslateX(-75);
        yes.setTranslateY(-175);
        Button no = new Button("No");
        no.setTranslateY(-175);
        no.setTranslateX(-25);
        sp.getChildren().addAll(alertBox, question, yes, no);

        if(p instanceof  AIPlayer){
            System.out.println("Checking if AI Player will play quest");
            boolean willPlay = ((AIPlayer)p).strategy.willPlayQuest();
            if(willPlay) {
                System.out.println("willPlay true");
                participateInQuestYes(p, q);
            }
            else {
                System.out.println("willPlay false");
                participateInQuestNo(p,  q);
            }
        }

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                participateInQuestYes(p, q);
                return;
            }
        });
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                participateInQuestNo(p, q);
                return;
            }
        });
    }

    public void participateInQuestYes(Player p, Quest q){
        p.playerInStory = true;
        p.cardsSelected.clear();
        currentPlayer++;
        playersAsked++;
        //System.out.println("curr player " + currentPlayer +  " | " + " players asked " +  playersAsked);
        if(currentPlayer > numPlayers)
            currentPlayer = 1;

        if(playersAsked < numPlayers) {
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                participateInQuest(play.playerList.get(currentPlayer - 1), q);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //System.out.println("entered else");
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                play.runQuest(q);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void participateInQuestNo(Player p, Quest q){
        p.playerInStory = false;
        p.cardsSelected.clear();
        currentPlayer++;
        playersAsked++;
        if(currentPlayer > numPlayers)
            currentPlayer = 1;

        if(playersAsked < numPlayers) {
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                participateInQuest(play.playerList.get(currentPlayer - 1), q);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                play.runQuest(q);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getHost(Player p, Quest q) throws IOException {
        updateWindow();
        Rectangle playerHost = new Rectangle(0,0,760,100);
        playerHost.setTranslateY(-200);
        playerHost.setTranslateX(-50);
        playerHost.setFill(Color.GREENYELLOW);
        playerHost.setStroke(Color.BLACK);
        Label question = new Label(p.getName() + " Would you like to host this quest? ");
        question.setTranslateY(-200);
        question.setTranslateX(-50);
        Button yesButton = new Button("Yes");
        yesButton.setTranslateX(-75);
        yesButton.setTranslateY(-175);
        Button noButton = new Button("No");
        noButton.setTranslateY(-175);
        noButton.setTranslateX(-25);
        drawStoryCard(q);
        sp.getChildren().addAll(playerHost, question, yesButton, noButton);

        if(p instanceof  AIPlayer){
            System.out.println("Checking if AI Player will try to host quest");
            boolean willPlay = ((AIPlayer)p).strategy.willHost();
            if(willPlay) {
                System.out.println("willPlay true");
                hostYes(p, q);
            }
            else {
                System.out.println("willPlay false");
                hostNo(p,  q);
            }
        }

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hostYes(p, q);
                return;
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hostNo(p, q);
                return;
            }
        });
    }

    public void hostYes(Player p, Quest q){
        System.out.println("Stages in quest " + q.GetNumStages());
        boolean hostable = play.canHost(currentPlayer, q.GetNumStages(), q.GetLinkedFoe());
        if(hostable) {
            System.out.println(p.getName() + " is host has been set to true");
            p.isHost = true;
        }
        currentPlayer++;
        playersAsked++;
        if(currentPlayer > numPlayers)
            currentPlayer = 1;
        if(playersAsked < numPlayers) {
            if(hostable) {
                System.out.println("Host has been found and it is " + p.getName()); //show host on gui somewhere while quest runs
                currentPlayer = play.playerTurn;
                playersAsked = 0;
                if(currentPlayer > numPlayers)
                    currentPlayer = 1;
                try {
                    participateInQuest(play.playerList.get(currentPlayer - 1), q); //used to be p passed in
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(p.getName() + " you are unable to host");
                try {
                    getHost(play.playerList.get(currentPlayer - 1), q);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else{
            if(hostable) {
                System.out.println("Host has been found and it is " + p.getName()); //show host on gui somewhere while quest runs
                currentPlayer = play.playerTurn;
                playersAsked = 0;
                try {
                    participateInQuest(p, q); //used to be p passed in
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //should only get here if no host if found
                try {
                    updateWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("No host is found!"); //Show this somewhere on GUI for a couple seconds
                try {
                    play.runQuest(q);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void hostNo(Player p, Quest q){
        currentPlayer++;
        playersAsked++;
        if (currentPlayer > numPlayers)
            currentPlayer = 1;
        if (playersAsked < numPlayers) {
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                getHost(play.playerList.get(currentPlayer - 1), q);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //should only get here if no host is found
            try {
                updateWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("No host is found!"); //Show this somewhere on GUI for a couple seconds
            try {
                play.runQuest(q);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runEvent(Story storyCard) throws IOException {
        updateWindow();
        Rectangle eventAlert = new Rectangle();
        eventAlert.setTranslateY(-200);
        eventAlert.setTranslateX(-50);
        eventAlert.setFill(Color.BLUEVIOLET);
        Label eventOccured = new Label("An event has occurred through the the land ");
        eventOccured.setTranslateY(-200);
        eventOccured.setTranslateX(-50);
        Button ok = new Button("Cool");
        ok.setTranslateX(-75);
        ok.setTranslateY(-175);
        updateWindow();
        drawStoryCard(storyCard);
        sp.getChildren().addAll(eventAlert, eventOccured, ok);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    play.runEvent((sample.Event) storyCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void displayAlliesInPlay() throws IOException {

//        if(sp.getChildren().contains(allAlies) == false) {
//            sp.getChildren().add(allAlies);
//        }
        sp.getChildren().add(allAlies);
        //updateWindow();
        allAlies.setStyle("-fx-border-color: Green");
        allAlies.setMaxWidth(1000);
        allAlies.setMaxHeight(IMGHEIGHT*.6);
        allAlies.setTranslateX(-150);
        allAlies.setTranslateY(-300);

        HBox p1Allies = new HBox(-50);
        p1Allies.setStyle("-fx-border-color: Blue");
        p1Allies.setMaxWidth(200);
        p1Allies.setMaxHeight(IMGHEIGHT*.6);
        p1Allies.setTranslateX(-400);
        //p1Allies.toFront();

        HBox p2Allies = new HBox(-50);
        p2Allies.setStyle("-fx-border-color: Purple");
        p2Allies.setMaxWidth(200);
        p2Allies.setMaxHeight(IMGHEIGHT*.6);
        p2Allies.setTranslateX(-100);

        HBox p3Allies = new HBox(-50);
        p3Allies.setStyle("-fx-border-color: yellow");
        p3Allies.setMaxWidth(200);
        p3Allies.setMaxHeight(IMGHEIGHT*.6);
        p3Allies.setTranslateX(150);

        HBox p4Allies = new HBox(-50);
        p4Allies.setStyle("-fx-border-color: Orange");
        p4Allies.setMaxWidth(200);
        p4Allies.setMaxHeight(IMGHEIGHT*.6);
        p4Allies.setTranslateX(400);
        //
        // System.out.println("This should be false  ---------------" + allAlies.getChildren().contains(p1Allies));
        int sizeV = 0;
        String location = "";
        HBox hBoxInUse = new HBox();

        for(int i = 0; i < player1.alliesInPlay.size(); i++){

            location = getImage(player1.alliesInPlay.get(i).GetName());
            Image image = new Image(location);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(IMGHEIGHT*.6);
            imageView.setFitWidth(IMGWIDTH*.6);;
            imageView.setImage(image);

            if(player1.alliesInPlay.size() == p1Allies.getChildren().size()){
                //return;
            }
            else if(p1Allies.getChildren().size() < player1.alliesInPlay.size()){
                p1Allies.getChildren().add(imageView);
            }
            else if(player1.alliesInPlay.size() == 0 && p1Allies.getChildren().size() != 0){
                p1Allies.getChildren().clear();
            }
            else{
                System.out.println("Critical Error, this should never happen - Display allies in play");
            }

        }

        for(int i = 0; i < player2.alliesInPlay.size(); i++){

            location = getImage(player2.alliesInPlay.get(i).GetName());
            Image image = new Image(location);
            System.out.println("");
            ImageView imageView = new ImageView();
            imageView.setFitHeight(IMGHEIGHT*.6);
            imageView.setFitWidth(IMGWIDTH*.6);;
            imageView.setImage(image);
            p2Allies.getChildren().add(imageView);

            if(player2.alliesInPlay.size() == p2Allies.getChildren().size()){
                //return;
            }
            else if(p2Allies.getChildren().size() < player2.alliesInPlay.size()){
                p2Allies.getChildren().add(imageView);
            }
            else if(player2.alliesInPlay.size() == 0 && p2Allies.getChildren().size() != 0){
                p2Allies.getChildren().clear();
            }
            else{
                System.out.println("Critical Error, this should never happen - Display allies in play");
            }
        }


        if(!(player3 == null)) {

            for(int i = 0; i < player3.alliesInPlay.size(); i++){

                location = getImage(player3.alliesInPlay.get(i).GetName());
                Image image = new Image(location);
                System.out.println("");
                ImageView imageView = new ImageView();
                System.out.println("");
                imageView.setFitHeight(IMGHEIGHT*.6);
                imageView.setFitWidth(IMGWIDTH*.6);;
                imageView.setImage(image);
                p3Allies.getChildren().add(imageView);

                if(player3.alliesInPlay.size() == p3Allies.getChildren().size()){
                    //return;
                }
                else if(p3Allies.getChildren().size() < player3.alliesInPlay.size()){
                    p3Allies.getChildren().add(imageView);
                }
                else if(player3.alliesInPlay.size() == 0 && p3Allies.getChildren().size() != 0){
                    p3Allies.getChildren().clear();
                }
                else{
                    System.out.println("Critical Error, this should never happen - Display allies in play");
                }
            }
        }

        if(!(player4 == null)) {

            for(int i = 0; i < player4.alliesInPlay.size(); i++){

                location = getImage(player4.alliesInPlay.get(i).GetName());
                Image image = new Image(location);
                ImageView imageView = new ImageView();
                imageView.setFitHeight(IMGHEIGHT*.6);
                imageView.setFitWidth(IMGWIDTH*.6);;
                imageView.setImage(image);
                System.out.println("");
                p4Allies.getChildren().add(imageView);

                if(player4.alliesInPlay.size() == p4Allies.getChildren().size()){
                    //return;
                }
                else if(p4Allies.getChildren().size() < player4.alliesInPlay.size()){
                    p4Allies.getChildren().add(imageView);
                }
                else if(player4.alliesInPlay.size() == 0 && p4Allies.getChildren().size() != 0){
                    p4Allies.getChildren().clear();
                }
                else{
                    System.out.println("Critical Error, this should never happen - Display allies in play");
                }
            }
        }

        allAlies.getChildren().addAll(p1Allies, p2Allies, p3Allies, p4Allies);
        //System.out.println("This is the length of all allies "+allAlies.getChildren().size());


        System.out.println("Player 1 number allies displayed " + p1Allies.getChildren().size() + "player one actual allies in play " + player1.alliesInPlay.size());
        System.out.println("Player 2 number allies displayed " + p2Allies.getChildren().size() + "player two actual allies in play " + player2.alliesInPlay.size());
        if(!(player3 == null))
            System.out.println("Player 3 number allies displayed " + p3Allies.getChildren().size() + "player three actual allies in play " + player3.alliesInPlay.size());
        if(!(player4 == null))
            System.out.println("Player 4 number allies displayed " + p4Allies.getChildren().size() + "player four actual allies in play " + player4.alliesInPlay.size());

//        p1Allies.toFront();
//        p2Allies.toFront();
//        p3Allies.toFront();
//        p4Allies.toFront();
    }

    public String getImage(String cardName){
        String imageLocation;
        switch(cardName){
            case "Thieves" :
                imageLocation = ClassLoader.getSystemResource("thieves.png").toString();
                break;
            case "Boar" :
                imageLocation = ClassLoader.getSystemResource("boar.png").toString();
                break;
            case "Amour" :
                imageLocation = ClassLoader.getSystemResource("amour.png").toString();
                break;
            case "Battle-ax" :
                imageLocation = ClassLoader.getSystemResource("battle_ax.png").toString();
                break;
            case "Black Knight" :
                imageLocation = ClassLoader.getSystemResource("black_knight.png").toString();
                break;
            case "Dagger" :
                imageLocation = ClassLoader.getSystemResource("dagger.png").toString();
                break;
            case "Dragon" :
                imageLocation = ClassLoader.getSystemResource("dragon.png").toString();
                break;
            case "Evil Knight" :
                imageLocation = ClassLoader.getSystemResource("evil_knight.png").toString();
                break;
            case "Excalibur" :
                imageLocation = ClassLoader.getSystemResource("excalibur.png").toString();
                break;
            case "Giant" :
                imageLocation = ClassLoader.getSystemResource("giant.png").toString();
                break;
            case "Green Knight" :
                imageLocation = ClassLoader.getSystemResource("green_knight.png").toString();
                break;
            case "Horse" :
                imageLocation = ClassLoader.getSystemResource("horse.png").toString();
                break;
            case "King Pellinore" :
                imageLocation = ClassLoader.getSystemResource("king_pellinore.png").toString();
                break;
            case "Lance" :
                imageLocation = ClassLoader.getSystemResource("lance.png").toString();
                break;
            case "Merlin" :
                imageLocation = ClassLoader.getSystemResource("merlin.png").toString();
                break;
            case "Mordred" :
                imageLocation = ClassLoader.getSystemResource("mordred.png").toString();
                break;
            case "Queen Guinevere" :
                imageLocation = ClassLoader.getSystemResource("queen_guinevere.png").toString();
                break;
            case "Queen Iseult" :
                imageLocation = ClassLoader.getSystemResource("queen_iseult.png").toString();
                break;
            case "Robber Knight" :
                imageLocation = ClassLoader.getSystemResource("robber_knight.png").toString();
                break;
            case "Saxon Knight" :
                imageLocation = ClassLoader.getSystemResource("saxon_knight.png").toString();
                break;
            case "Saxons" :
                imageLocation = ClassLoader.getSystemResource("saxons.png").toString();
                break;
            case "Sir Galahad" :
                imageLocation = ClassLoader.getSystemResource("sir_galahad.png").toString();
                break;
            case "Sir Gawain" :
                imageLocation = ClassLoader.getSystemResource("sir_gawain.png").toString();
                break;
            case "Sir Lancelot" :
                imageLocation = ClassLoader.getSystemResource("sir_lancelot.png").toString();
                break;
            case "Sir Percival" :
                imageLocation = ClassLoader.getSystemResource("sir_percival.png").toString();
                break;
            case "Sir Tristan" :
                imageLocation = ClassLoader.getSystemResource("sir_tristan.png").toString();
                break;
            case "Sword" :
                imageLocation = ClassLoader.getSystemResource("sword.png").toString();
                break;
            case "Test of Valor" :
                imageLocation = ClassLoader.getSystemResource("test_ofvalor.png").toString();
                break;
            case "Test of Morgan Le Fey" :
                imageLocation = ClassLoader.getSystemResource("test_ofmorganlefey.png").toString();
                break;
            case "Test of the Questing Beast" :
                imageLocation = ClassLoader.getSystemResource("test_questingbeast.png").toString();
                break;
            case "Test of Temptation" :
                imageLocation = ClassLoader.getSystemResource("test_temptation.png").toString();
                break;
            case "AT CAMELOT" :
                imageLocation = ClassLoader.getSystemResource("tournament_atcamelot.png").toString();
                break;
            case "AT ORKNEY" :
                imageLocation = ClassLoader.getSystemResource("tournament_atorkney.png").toString();
                break;
            case "AT TINTAGEL" :
                imageLocation = ClassLoader.getSystemResource("tournament_attintagel.png").toString();
                break;
            case "AT YORK" :
                imageLocation = ClassLoader.getSystemResource("tournament_atyork.png").toString();
                break;
            case "Defend the Queen's Honor" :
                imageLocation = ClassLoader.getSystemResource("defend_queenshonor.png").toString();
                break;
            case "Search for the Questing Beast" :
                imageLocation = ClassLoader.getSystemResource("search_questingbeast.png").toString();
                    break;
            case "Journey through the Enchanted Forest" :
                imageLocation = ClassLoader.getSystemResource("journey_enchanted.png").toString();
                break;
            case "Repel the Saxon Raiders" :
                imageLocation = ClassLoader.getSystemResource("repel_raiders.png").toString();
                break;
            case "Rescue the Fair Maiden" :
                imageLocation = ClassLoader.getSystemResource("rescue_fairmaiden.png").toString();
                break;
            case "Search for the Holy Grail" :
                imageLocation = ClassLoader.getSystemResource("search_holygrail.png").toString();
                break;
            case "Slay the Dragon" :
                imageLocation = ClassLoader.getSystemResource("slay_dragon.png").toString();
                break;
            case "Test of the Green Knight" :
                imageLocation = ClassLoader.getSystemResource("test_ofgreenknight.png").toString();
                break;
            case "Vanquish King Arthur's Enemies" :
                imageLocation = ClassLoader.getSystemResource("vanquish_king.png").toString();
                break;
            case "Boar Hunt" :
                imageLocation = ClassLoader.getSystemResource("boar_hunt.png").toString();
                break;
            case "Chivalrous Deed" :
                imageLocation = ClassLoader.getSystemResource("chivalrous_deed.png").toString();
                break;
            case "Pox" :
                imageLocation = ClassLoader.getSystemResource("pox.png").toString();
                break;
            case "Prosperity Throughout the Realm" :
                imageLocation = ClassLoader.getSystemResource("prosperity.png").toString();
                break;
            case "Plague" :
                imageLocation = ClassLoader.getSystemResource("plague.png").toString();
                break;
            case "King's Recognition" :
                imageLocation = ClassLoader.getSystemResource("king_recognition.png").toString();
                break;
            case "Queen's Favor" :
                imageLocation = ClassLoader.getSystemResource("queens_favor.png").toString();
                break;
            case "Court Called to Camelot" :
                imageLocation = ClassLoader.getSystemResource("court_camelot.png").toString();
                break;
            case "King Arthur" :
                imageLocation = ClassLoader.getSystemResource("king_arthur.png").toString();
                break;
            case "back" :
                imageLocation = ClassLoader.getSystemResource("back.png").toString();
                break;
            default:
                System.out.println("Default case card missing is: " + cardName);
                imageLocation = "/QuestOfTheRoundTable_v2/resources/squire.jpg";
        }
        return imageLocation;
    }
    public String readResource(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }
}
