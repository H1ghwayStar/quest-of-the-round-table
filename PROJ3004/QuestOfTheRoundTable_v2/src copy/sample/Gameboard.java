package sample;

import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gameboard {

    public ArrayList<Adventure> adventureDeck = new ArrayList<>();//125
    public ArrayList<Adventure> discardAdventureDeck = new ArrayList<>();
    public Controller controller;

    public ArrayList<Story> storyDeck = new ArrayList<Story>();//28
    public ArrayList<Story> discardStoryDeck = new ArrayList<Story> ();

    public ArrayList<Player> playerList = new ArrayList<>();
    public int numPlayers = 0;
    public boolean gameOver = false;
    public int playerTurn = 1;


    public int QuestBonusShields = 0; //For Kings Recognition

    public void initializePlayers(Player p1, Player p2, Player p3, Player p4){
        controller = Controller.getInstance();
        numPlayers = controller.numPlayers;

        playerList.add(p1);
        playerList.add(p2);
        if(p3 != null)
            playerList.add(p3);
        if(p4 != null)
            playerList.add(p4);
    }

    public void runGame() throws Exception {
        createDeck();
        shuffle("adventureDeck");
        shuffle("storyDeck");
        deal();

        for(int i = 0; i < playerList.size(); i++){
            Player p = playerList.get(i);
            System.out.println(p.getName());
            System.out.println("----------");
            for(int y = 0; y < p.cardsInHand.size(); y++){
                System.out.println(p.cardsInHand.get(y).GetName());
            }
            System.out.println("=====================");
        }
        numPlayers = controller.numPlayers;
        try {
            controller.updateWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void controlFlow(){
        System.out.println("Back in control flow");
            Story storyCard = (Story) draw("Story");
            controller.card = storyCard;

            for(int i = 0; i < numPlayers; i++){
                Player p = playerList.get(i);
                p.playerInStory = false;
                p.cardsSelected.clear();
            }
            int subtracter = 1;
        System.out.println(playerTurn);
        int tempPlayerTurn = playerTurn;
        if (tempPlayerTurn > numPlayers) {
            tempPlayerTurn = 1;
        }
        Controller.currentPlayer = tempPlayerTurn;
        Controller.playersAsked = 0;
        System.out.println(playerList.get(tempPlayerTurn - subtracter).getName() + "this is the player we pass to pIT");
            controller.participateInTournament(playerList.get(tempPlayerTurn - subtracter));

    }

    public void runTournament(){
        System.out.println("Entered run tournament");
        boolean tied = isTournament((Tournament) discardStoryDeck.get(discardStoryDeck.size() - 1), null, 0);
        System.out.println("passed is tournament");
        if(!tied)
            return;
        for (int x = 0; x < playerList.size(); x++) {//FOR TESTING ONLY, making sure the right person got the number of shields
            System.out.println(playerList.get(x).shields);
            System.out.println(playerList.get(x).cardsInHand.size());
        }
        gameOver = rankUp();
        whosTurn();
        controlFlow();
    }

    public void runTournamentTie(Story storycard, ArrayList<Player> winner, int eliminatedCompetitors){
        System.out.println("Entered run tournament TIE");
        isTournament((Tournament) storycard, winner, eliminatedCompetitors);
        System.out.println("passed is tournament TIE");
        for (int x = 0; x < playerList.size(); x++) {//FOR TESTING ONLY, making sure the right person got the number of shields
            System.out.println(playerList.get(x).shields);
            System.out.println(playerList.get(x).cardsInHand.size());
        }
        gameOver = rankUp();
        whosTurn();
        controlFlow();
    }

    public int whosTurn(){
        if (playerTurn > numPlayers) {
            System.out.println("player turn reset");
            playerTurn = 1;
        }
        System.out.println("Entered who's turn: " + playerTurn);
        return playerTurn++;
    }

    public boolean rankUp(){
        boolean hasWon = false;
        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            if (p.rank == "Squire" && p.shields >= 5) {
                p.rank = "Knight";
                p.shields -= 5;
            }
            if (p.rank == "Knight" && p.shields >= 7) {
                p.rank = "Champion Knight";
                p.shields -= 7;
            }
            if (p.rank == "Champion Knight" && p.shields >= 10) {
                //Player has won and become a knight of the round
                hasWon = true;
            }
        }
        return hasWon;
    }



    public boolean isTournament(Tournament storyCard, ArrayList<Player> tie, int eliminatedCompetitors){
        String tournamentName = storyCard.GetName ();
        int bonusShields = storyCard.GetShieldModifier();
        ArrayList <Pair<Player, Integer>> tournamentEntry = new ArrayList <> ();
        if (tie == null) {
           // System.out.println ("Tournament starting " + tournamentName + "!!! Players that wish to enter, type y, other for no\n");
            int tempPlayerTurn = playerTurn;
            for (int i = 0; i < numPlayers; i++) { //seeing which player wants to play
                if (tempPlayerTurn > numPlayers) {
                    tempPlayerTurn = 1;
                }
                //System.out.println (playerList.get(tempPlayerTurn - 1).getName () + "?: ");
                //String userInput = "y";//Do with mouse clicks now
                //System.out.println ((tempPlayerTurn - 1) + "|" + (playerTurn - 1));
                //controller.participateInTournament();
                if(playerList.get(tempPlayerTurn - 1).playerInStory) {
                    tournamentEntry.add(new Pair<>(playerList.get(tempPlayerTurn - 1), 0));
                    System.out.println(playerList.get(tempPlayerTurn - 1).getName() + " has joined the tourney");
                }

                tempPlayerTurn++;
            }
            //playerTurn++;
        } else {
            System.out.println ("There's been a tie! Players will face off to decide the champion!");
            for (int i = 0; i < tie.size(); i++) {
                System.out.println (tie.get(i).getName() + " will be participating");
                tournamentEntry.add(new Pair<> (tie.get(i), 0));
            }
        }
        if (tournamentEntry.size() == 0) {
            System.out.println ("No one entered the tournament");
            return true;
        }
        if (tournamentEntry.size() == 1) {
            System.out.println ("Congrats " + tournamentEntry.get(0).getKey().getName () + " you have won the tournament " + tournamentName + " and recieved " + (bonusShields+1) + " shields!");
            tournamentEntry.get(0).getKey().shields += bonusShields + 1;//cause only one player entered
            return true;
        }
        for (int x = 0; x < tournamentEntry.size(); x++) { //FOR TESTING ONLY
            System.out.println (tournamentEntry.get(x).getKey().getName() + " | " + tournamentEntry.get(x).getKey().cardsInHand.size());
        }

        for (int i = 0; i < tournamentEntry.size(); i++) { //all players that wish to play get an additional adventure card. Assuming this is true for tiebreakes as well
            tournamentEntry.get(i).getKey().cardsInHand.add ((Adventure)draw ("Adventure"));
        }

        for (int x = 0; x < tournamentEntry.size(); x++) { //FOR TESTING ONLY
            System.out.println (tournamentEntry.get(x).getKey().getName() + " | " + tournamentEntry.get(x).getKey().cardsInHand.size());
        }


        bonusShields += tournamentEntry.size() + eliminatedCompetitors;
        for (int i = 0; i < tournamentEntry.size(); i++) { //showing player the cards they have, making them choose which cards they want to play
            Player player = tournamentEntry.get(i).getKey();
            for (int y = 0; y < player.cardsInHand.size(); y++) {
                if(y == 0 && player.cardsInHand.size() > 12) {
                    //controller.selectingCardsToRemove(player);//Testing
                    System.out.println("Alert, " + player.getName() + " you have more than 12 cards. Discard to (at least) 12 or be auto discarded.");
                }
                System.out.println ("Card #" + (y+1) + ": " + player.cardsInHand.get(y).GetName() + " with " + player.cardsInHand.get(y).GetBattlePoints() + " battle points.");
            }
            System.out.println ("Choose the cards you want to use for the tournament by card#, separated by commas. Invalid cards will be ignored.");
            String cardsChosen = "1,2,3,4"; //Have to modify this with mouse clicks
            String[] values = cardsChosen.split (",");
            tournamentEntry.set(i,new Pair<>(player,validCards(values, player, "no")));
        }
        ArrayList<Player> winner = roundWinner (tournamentEntry);
        if (winner.size() > 1 && tie == null) {
            for(int i = 0; i < winner.size(); i++){
                winner.get(i).cardsSelected.clear();
            }
            controller.participateInTournamentTie(storyCard, winner, (bonusShields - winner.size()), null);
            return false;
            //isTournament (storyCard, winner, (bonusShields - winner.size())); //careful not to enter this loop twice, or it's inifinite loop time baby! At most one recursive call should happen
        }else if(winner.size() > 1 && tie != null){
            System.out.println ("Tie breaker can't be decided, everyone gets shields!");
            for (int i = 0; i < winner.size(); i++) {
                winner.get(i).shields += bonusShields;
            }
        } else {
            System.out.println ("Congrats " + winner.get(0).getName () + " you have won the tournament " + tournamentName + " and received " + bonusShields + " shields!");
            winner.get(0).shields += bonusShields;
        }
        //stripCards (); //if player has more than 12 cards, time to get rid of them CALL STRIP CARDS LATER - STILL NEED TO DO
        return true;
    }

    public void stripCards() {
        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            while (p.cardsInHand.size() > Player.MAX_CARDS_HAND) {
                System.out.println ("Auto-removed " + p.cardsInHand.get(p.cardsInHand.size() - 1).GetName());
                p.discardPile.add (p.cardsInHand.get(p.cardsInHand.size() - 1));
                p.cardsInHand.remove(p.cardsInHand.size() - 1);
            }
        }
    }

    public ArrayList<Player> roundWinner(ArrayList<Pair<Player, Integer>> competitors){ //Return a winner, need to account for a draw
        ArrayList<Player> winner = new ArrayList<Player>(); //this should always return at least one person
        int winnerBP = 0;
        for (int i = 0; i < competitors.size(); i++) {
            if (i == 0) {
                winner.add(competitors.get(i).getKey());
                winnerBP = competitors.get(i).getValue();
            }
            if (i != 0 && competitors.get(i).getValue() > winnerBP) {
                winner.clear();
                winner.add (competitors.get(i).getKey());
                winnerBP = competitors.get(i).getValue();
            }else if(i != 0 && competitors.get(i).getValue() == winnerBP){
                winner.add (competitors.get(i).getKey());
            }
        }
        return winner;
    }

    public int validCards(String[] values, Player p, String quest){//Should be usable for tournament and quest
        ArrayList<Integer> validatedCards = p.cardsSelected;
        int battlePoints = 0;

        for (int i = 0; i < p.alliesInPlay.size(); i++) { //doing it up here so we don't double count ally battle points, as they are added into this array down below
            battlePoints += p.alliesInPlay.get(i).GetBattlePoints ();
        }

        System.out.println ("WTF:" + values.length);
        /*for (int i = 0; i < values.length; i++) { //Making sure values that aren't acceptable ints are stripped, unnecessary for GUI
            int temp = -1;
            boolean tempBool = int.TryParse (values [i], out temp);
            if (tempBool && temp > 0 && temp <= p.cardsInHand.size()) {
                validatedCards.Add (temp);
            }
        } */
         //validatedCards = validatedCards.stream().distinct().collect(Collectors.toList());
         Set<Integer> hs = new HashSet<>();
         hs.addAll(validatedCards);
         validatedCards.clear();
         validatedCards.addAll(hs);

        for(int i = 0; i < validatedCards.size(); i++){
            int old = validatedCards.get(i);
            validatedCards.set(i, old + 1);
        }
        System.out.println ("WTF#2 :" + validatedCards.size());
        boolean amourPlayer = false;
        boolean firstamourSet = false;
        Collections.sort(validatedCards);
        for (int i = validatedCards.size() - 1; i >= 0; i--) { //No two weapons of the same type can be played, no foe cards, only one Amour
            if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Weapon) {
                for (int y = i - 1; y >= 0; y--) {
                    System.out.println (i + "|" + y);
                    System.out.println ((p.cardsInHand.get(validatedCards.get(i) - 1) + "|" + (p.cardsInHand.get(validatedCards.get(y) - 1))));
                    if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Weapon && p.cardsInHand.get(validatedCards.get(y) - 1) instanceof Weapon && ((Weapon)(p.cardsInHand.get(validatedCards.get(i) - 1))).equals(((Weapon)(p.cardsInHand.get(validatedCards.get(y) - 1))))) {
                        validatedCards.remove (y); //unintended bonus of removing duplicate of the same weapon (excact same object) added twice
                        i = validatedCards.size() - 1; //if we remove from the end here and i doesn't decrement, we are in trouble
                        System.out.println ("WTF#2 :" + validatedCards.size());
                    }
                }
            } else if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Amour && !amourPlayer && quest == "no") {
                amourPlayer = true;
            } else if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Amour && amourPlayer && quest == "no") {
                validatedCards.remove (i);
            } else if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Amour && p.amourOnQuest && quest == "yes") {
                validatedCards.remove (i);
            }else if(p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Amour && !p.amourOnQuest && quest == "yes"){
               /*System.out.println (p.getName() + " played(special quest amour case) " + p.cardsInHand[validatedCards [i] - 1].GetName());
               p.discardPile.Add (p.cardsInHand[validatedCards [i] - 1]);//dont want to double count amours anyway, also only one amour is allowed so additional amours are ignored
               p.cardsInHand.RemoveAt(validatedCards [i] - 1);
               validatedCards.RemoveAt (i);*/
                p.amourOnQuest = true;
                firstamourSet = true;
            } else if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Foe) {
                validatedCards.remove (i);
            } else if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Test) {
                validatedCards.remove (i);
            } else if(p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Ally){//allys stay on after
                p.alliesInPlay.add((Ally)p.cardsInHand.get(validatedCards.get(i) - 1));
            }
        }//end main for
        //So now all the indexes specified are valid inside of validated cards
        for (int i = validatedCards.size() - 1; i >= 0; i--) {
            battlePoints += p.cardsInHand.get(validatedCards.get(i) - 1).GetBattlePoints (); //because a player isn't going to see card #'s starting at 0, rather they start at 1
            System.out.println (p.getName() + " played " + p.cardsInHand.get(validatedCards.get(i) - 1).GetName());
            p.discardPile.add (p.cardsInHand.get(validatedCards.get(i) - 1));
            p.cardsInHand.remove(validatedCards.get(i) - 1);
        }
        battlePoints += Player.rankDictionary.get(p.rank);
        if (quest == "yes" && p.amourOnQuest && !firstamourSet)
            battlePoints += 10;
        System.out.println(p.getName() + "'s rank is " + Player.rankDictionary.get(p.rank));
        System.out.println ("Total battle points for player including rank " + battlePoints);
        return battlePoints;
    }

    public void deal(){
        for (int i = 0; i < 12; i++) {
            for (int y = 0; y < playerList.size(); y++) {
                playerList.get(y).cardsInHand.add(adventureDeck.get(adventureDeck.size() - 1));
                discardAdventureDeck.add(adventureDeck.get(adventureDeck.size() - 1));
                adventureDeck.remove(adventureDeck.size() - 1);
            }
        }
    }

    public void shuffle(String whichDeck){
        Random generator = new Random(UUID.randomUUID().hashCode());

        switch (whichDeck) {
            case "storyDeck":
                for (int y = 0; y < 3; y++) {
                    for (int i = 0; i < storyDeck.size(); i++) {
                        //We should only ever shuffle a full story deck, for right now that's only at the start
                        //Will have to deal with situation: All story cards are used, but no one has won
                        int tempInt = generator.nextInt(storyDeck.size());
                        Story swapCard = storyDeck.get(tempInt);
                        storyDeck.set(tempInt, storyDeck.get(i));
                        storyDeck.set(i,swapCard);
                    }
                }
                break;
            default:
                for (int y = 0; y < 3; y++) {
                    for (int i = 0; i < adventureDeck.size(); i++) {
                        //Shuffling at the start is a full deck, but it could be a different number next time
                        int tempInt = generator.nextInt(adventureDeck.size());
                        Adventure swapCard = adventureDeck.get(tempInt);
                        adventureDeck.set(tempInt, adventureDeck.get(i));
                        adventureDeck.set(i, swapCard);
                    }
                }
                break;
        }
    }

    public Card draw(String whichDeck){
        if (storyDeck.size() == 0) {
            storyDeck = discardStoryDeck;
            shuffle ("storyDeck");
            discardStoryDeck = new ArrayList<>();
        }
        if (adventureDeck.size() == 0) {
            System.out.println ("Unhandled out of adventure cards case");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //if we get here, need a case
        }
        switch (whichDeck) {
            case "Story":
                System.out.println (storyDeck.get(storyDeck.size() - 1).GetName ());
                discardStoryDeck.add (storyDeck.get(storyDeck.size() - 1));
                storyDeck.remove(storyDeck.size() - 1);
                return discardStoryDeck.get(discardStoryDeck.size() - 1);
            default://For Adventure card drawing
                System.out.println (adventureDeck.get(adventureDeck.size() - 1).GetName ());
                discardAdventureDeck.add (adventureDeck.get(adventureDeck.size() - 1)); //Not sure if good use for this yet
                adventureDeck.remove(adventureDeck.size() - 1);
                return discardAdventureDeck.get(discardAdventureDeck.size() - 1);
        }
    }

    public void createDeck() {
        int cardCounter = 0;

        //ADVENTURE DECK

        /* AMOUR */

        for (int i = 0; i < 8; i++) {
            adventureDeck.add(createAdventureCard("Amour", "Amour", 10, 1));
            cardCounter++;
        }

        /* WEAPON */

        for (int i = cardCounter; i < 10; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Excalibur", 30, 0));
            cardCounter++;
        }

        for (int i = cardCounter; i < 16; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Lance", 20, 0));
            cardCounter++;
        }

        for (int i = cardCounter; i < 24; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Battle-ax", 15, 0));
            cardCounter++;
        }

        for (int i = cardCounter; i < 40; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Sword", 10, 0));
            cardCounter++;
        }

        for (int i = cardCounter; i < 51; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Horse", 10, 0));
            cardCounter++;
        }

        for (int i = cardCounter; i < 57; i++) {
            adventureDeck.add(createAdventureCard("Weapon", "Dagger", 5, 0));
            cardCounter++;
        }

        /* FOE */
        for (int i = cardCounter; i < 58; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Dragon", 50, 70));
            cardCounter++;
        }

        for (int i = cardCounter; i < 60; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Giant", 40, 40));
            cardCounter++;
        }

        for (int i = cardCounter; i < 64; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Mordred", 30, 30));
            cardCounter++;
        }

        for (int i = cardCounter; i < 66; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Green Knight", 25, 40));
            cardCounter++;
        }

        for (int i = cardCounter; i < 69; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Black Knight", 25, 35));
            cardCounter++;
        }

        for (int i = cardCounter; i < 75; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Evil Knight", 20, 30));
            cardCounter++;
        }

        for (int i = cardCounter; i < 83; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Saxon Knight", 15, 25));
            cardCounter++;
        }

        for (int i = cardCounter; i < 90; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Robber Knight", 15, 15));
            cardCounter++;
        }

        for (int i = cardCounter; i < 95; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Saxons", 10, 20));
            cardCounter++;
        }

        for (int i = cardCounter; i < 99; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Boar", 5, 15));
            cardCounter++;
        }

        for (int i = cardCounter; i < 107; i++) {
            adventureDeck.add(createAdventureCard("Foe", "Thieves", 5, 5));
            cardCounter++;
        }

        /*TEST*/
        adventureDeck.add(createAdventureCard("Test", "Test of Valor", 0, 0));
        adventureDeck.add(createAdventureCard("Test", "Test of Valor", 0,0));

        adventureDeck.add(createAdventureCard("Test", "Test of Temptation", 0,0));
        adventureDeck.add(createAdventureCard("Test", "Test of Temptation", 0,0));

        adventureDeck.add(createAdventureCard("Test", "Test of the Questing Beast", 0, 0));
        adventureDeck.add(createAdventureCard("Test", "Test of the Questing Beast", 0, 0));

        adventureDeck.add(createAdventureCard("Test", "Test of Morgan Le Fey", 3, 0));
        adventureDeck.add(createAdventureCard("Test", "Test of Morgan Le Fey", 3, 0));


        /*Allies*/
        adventureDeck.add(createAdventureCard("Ally", "Sir Galahad", 15, 0));
        adventureDeck.add(createAdventureCard("Ally", "Sir Gawain", 10, 0));
        adventureDeck.add(createAdventureCard("Ally", "King Pellinore", 10, 0));
        adventureDeck.add(createAdventureCard("Ally", "Sir Percival", 5, 0));
        adventureDeck.add(createAdventureCard("Ally", "Sir Tristan", 10, 0));
        adventureDeck.add(createAdventureCard("Ally", "King Arthur", 10, 2));
        adventureDeck.add(createAdventureCard("Ally", "Queen Guinevere", 0, 3));
        adventureDeck.add(createAdventureCard("Ally", "Merlin", 0, 0));
        adventureDeck.add(createAdventureCard("Ally", "Queen Iseult", 0, 2));
        adventureDeck.add(createAdventureCard("Ally", "Sir Lancelot", 15, 0));


        //STORY DECK
        cardCounter = 0;

        /* Tournament */
        /*EVENT*/
         /*storyDeck.add(createEventCard("Chivalrous Deed", 3, 0, false, 0, 0, Event.playersTargeted.get(0), "Player(s) with both lowest rank and least amount of shields, receives 3 shields"));
         storyDeck.add(createEventCard("Pox", -1, 0, false, 0, 0, Event.playersTargeted.get(2), "All players except the player drawing this card lose 1 shield"));
         storyDeck.add(createEventCard("Plague", -2, 0, false, 0, 0, Event.playersTargeted.get(3), "Drawer loses two shields if possible"));
         storyDeck.add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted.get(6), "The next player(s) to complete a Quest will receive 2 extra shields"));
         storyDeck.add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted.get(6), "The next player(s) to complete a Quest will receive 2 extra shields"));
         storyDeck.add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted.get(5), "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
         storyDeck.add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted.get(5), "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
         storyDeck.add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted.get(1), "All Allies in play must be discarded"));
         storyDeck.add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted.get(1), "All Allies in play must be discarded"));
         storyDeck.add(createEventCard("King's Call To Arms", 0, 0, false, 1, 2, Event.playersTargeted.get(4), "The highest ranked player(s) must place 1 weapon in the discard pile. If unable to do so, 2 Foe Cards must be discarded"));
         storyDeck.add(createEventCard("Prosperity Throughout the Realm", 0, 2, false, 0, 0, Event.playersTargeted.get(1), "All players may immeadiately draw two Adventure Cards")); */

        /*Tournament*/
        storyDeck.add(createTournamentCard("AT YORK", 0));
        storyDeck.add(createTournamentCard("AT TINTAGEL", 1));
        storyDeck.add(createTournamentCard("AT ORKNEY", 2));
        storyDeck.add(createTournamentCard("AT CAMELOT", 3));

        /*Quest*/
        /*storyDeck.add(createQuestCard("Journey through the Enchanted Forest", 3, "Evil Knight", "none"));
        storyDeck.add(createQuestCard("Vanquish King Arthur's Enemies", 3, "none", "none"));
        storyDeck.add(createQuestCard("Vanquish King Arthur's Enemies", 3, "none", "none"));
        storyDeck.add(createQuestCard("Repel the Saxon Raiders", 2, "All Saxons", "none"));
        storyDeck.add(createQuestCard("Repel the Saxon Raiders", 2, "All Saxons", "none"));
        storyDeck.add(createQuestCard("Boar Hunt", 2, "Boar", "none"));
        storyDeck.add(createQuestCard("Boar Hunt", 2, "Boar", "none"));
        storyDeck.add(createQuestCard("Search for the Questing Beast", 4, "none", "King Pellinore"));
        storyDeck.add(createQuestCard("Defend the Queen's Honor", 4, "All", "Sir Lancelot"));
        storyDeck.add(createQuestCard("Slay the Dragon", 3, "Dragon", "none"));
        storyDeck.add(createQuestCard("Rescue the Fair Maiden", 3, "Black Knight", "none"));
        storyDeck.add(createQuestCard("Search for the Holy Grail", 5, "All", "Sir Percival"));
        storyDeck.add(createQuestCard("Test of the Green Knight", 4, "Green Knight", "Sir Gawain")); */

    }

    public Story createEventCard(String name, int shieldModifier, int adventureCardModifier, boolean eliminiateAllies, int weaponCardModifier,
                                 int foeCardModifier, String whosAffected, String description){
        Event eve = new Event(name, shieldModifier, adventureCardModifier, eliminiateAllies, weaponCardModifier, foeCardModifier, whosAffected, description);
        return eve;
    }

    public Story createTournamentCard(String name, int shieldModifier){
        Tournament tournament = new Tournament(name, shieldModifier);
        return tournament;
    }

    public Story createQuestCard(String name, int numStages, String linkedFoe, String linkedAlly){
        Quest quest = new Quest(name, numStages, linkedFoe, linkedAlly);
        return quest;
    }

    public Adventure createAdventureCard(String type, String name, int battlePoints, int bids) {
        switch (type) {
            case "Foe":
                Foe foe = new Foe(name, battlePoints, bids);//using bids as boosted battle points here..not good practice?
                return foe;
            case "Weapon":
                Weapon weapon = new Weapon(name, battlePoints);
                return weapon;
            case "Ally":
                Ally ally = new Ally(name, battlePoints, bids);
                return ally;
            case "Amour":
                Amour amour = new Amour();
                return amour;
            case "Test":
                Test test = new Test(name, bids);
                return test;
            default:
                System.out.println("Deck creation err, abort");
                Foe apocalypse = new Foe("Critical Err", 9001, -1);
                return apocalypse;
        }
    }

}