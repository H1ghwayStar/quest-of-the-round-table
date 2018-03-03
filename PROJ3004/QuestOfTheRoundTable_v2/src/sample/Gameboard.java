package sample;

import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.google.common.collect.*;

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
    public final int[] questEntryNum = {0};
    public final int[] stageNum = {0};


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
            //System.out.println("runGame called");
        }
        numPlayers = controller.numPlayers;
        try {
            controller.updateWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void controlFlow() throws IOException {
        System.out.println("Back in control flow");
            Story storyCard = (Story) draw("Story");
            controller.card = storyCard;

            for(int i = 0; i < numPlayers; i++){
                Player p = playerList.get(i);
                p.playerInStory = false;
                p.cardsSelected.clear();
                p.isHost = false;
                p.hostCardsInHandBeforeQuest = p.cardsInHand.size();
                p.extraCardTest = false;
            }
        System.out.println(playerTurn);
        int tempPlayerTurn = playerTurn;
        if (tempPlayerTurn > numPlayers) {
            tempPlayerTurn = 1;
        }
        Controller.currentPlayer = tempPlayerTurn;
        Controller.playersAsked = 0;
        System.out.println(playerList.get(tempPlayerTurn - 1).getName() + "this is the player we pass to controller");
        if(storyCard instanceof  Quest)
            controller.getHost(playerList.get(tempPlayerTurn - 1) ,(Quest) storyCard);
        else if(storyCard instanceof Tournament)
            controller.participateInTournament(playerList.get(tempPlayerTurn - 1), storyCard);
        else if(storyCard instanceof Event)
            controller.runEvent(storyCard);
        else
            System.out.println("Critical error 9001 in storyDeck this should never happen");
    }

    public void runTournament() throws IOException {
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

    public void runTournamentTie(Story storycard, ArrayList<Player> winner, int eliminatedCompetitors) throws IOException {
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


    public void runQuest(Quest storyCard) throws IOException {
        boolean hosthasBeenFound = false;
        Player host = null;
        boolean otherPlayers = false;
        for(int i = 0; i < playerList.size(); i++){
            Player check = playerList.get(i);
            if(check.isHost){
                hosthasBeenFound = true;
                host = check;
                System.out.println(check.getName());
                System.out.println("Host playerInStory is set to: " + host.playerInStory + " (should be false)");
            }
            if(check.playerInStory)
                otherPlayers = true;
        }

        if(!hosthasBeenFound || !otherPlayers) {
            System.out.println("No host or no other players");
            gameOver = rankUp();
            whosTurn();
            controlFlow();
        } else {
            controller.hostSelectStages(host, storyCard);
        }

    }

    public void hostGetCards(int hostCardsBeforeQuest, int numQuestStages) throws IOException {
        Player host = null;
        int savedI = -1;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).isHost){
                host = playerList.get(i);
                savedI = i;
                break;
            }
        }
        //should NEVER get this null error, if we do something is wrong
        int hostCardsToGive = (hostCardsBeforeQuest -  host.cardsInHand.size()) + numQuestStages;
        System.out.println("Host cards to give: " + hostCardsToGive + "|||||" + hostCardsBeforeQuest + "|" + host.cardsInHand.size() + "|" + numQuestStages);
        for (int i = 0; i < hostCardsToGive; i++) {//allow the host to draw cards equivalent to how many they used + num stages in quest
                 host.cardsInHand.add((Adventure) draw("Adventure"));
        }

        ArrayList<Player> cardStripPlayers = new ArrayList<>();

        for(int i = 0; i < playerList.size(); i++){ //This should get the players in the right order to ask to strip cards after a quest
            if(savedI >= playerList.size())
                savedI = 0;
            if(playerList.get(savedI).cardsInHand.size() > Player.MAX_CARDS_HAND)
                cardStripPlayers.add(playerList.get(savedI));
            savedI++;
        }
        controller.removeExcessCards(cardStripPlayers);

    }


    public void afterQuest() throws IOException {
        gameOver = rankUp();
        whosTurn();
        controlFlow();
    }

    public void runEvent(Event eventCard) throws IOException {
        isEvent(eventCard);
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



    public boolean isTournament(Tournament storyCard, ArrayList<Player> tie, int eliminatedCompetitors) throws IOException {
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

        //System.out.println ("WTF:" + values.length);
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
            if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Weapon) { //Weird crash here last time, what happened?
                for (int y = i - 1; y >= 0; y--) {
                    System.out.println (i + "|" + y);
                    System.out.println ((p.cardsInHand.get(validatedCards.get(i) - 1) + "|" + (p.cardsInHand.get(validatedCards.get(y) - 1))));
                    if (p.cardsInHand.get(validatedCards.get(i) - 1) instanceof Weapon && p.cardsInHand.get(validatedCards.get(y) - 1) instanceof Weapon && ((Weapon)(p.cardsInHand.get(validatedCards.get(i) - 1))).GetName().equals(((Weapon)(p.cardsInHand.get(validatedCards.get(y) - 1))).GetName())) {
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

    public void isQuest(Quest storyCard, Player host, ArrayList<Pair<ArrayList<Adventure>,Integer>> questStages) throws IOException {
        System.out.println ("A new quest! " +  storyCard.GetName() + " with " + storyCard.GetNumStages() + " stages.");
        System.out.println ("Linked Foe: " + storyCard.GetLinkedFoe() + " | " + "Linked Ally: " + storyCard.GetLinkedAlly());
        if (host == null) {
            System.out.println ("CRITICAL ERROR 9001 in isQuest, this should never happen");
        }
        System.out.println (host.getName() + " will be hosting. ");
        int tempPlayerTurn = playerTurn;
        ArrayList <Pair<Player, Integer>> questEntry = new ArrayList <> ();
        for(int i = 0; i < numPlayers; i++){ //we have a host, now check who wants to play. playerTurn order is maintained regardless of who sponsored,
            //that is, left to right from whos turn it is who drew the story card, skipping the sponsor as we go. Meaning whomever drew the card gets first chance
            //at being sponsor, and also first change to play if he declines to be the sponsor
            if(tempPlayerTurn > numPlayers){
                tempPlayerTurn = 1;
            }
            if(playerList.get(tempPlayerTurn -1) != host && playerList.get(tempPlayerTurn - 1).playerInStory){
                System.out.println(playerList.get(tempPlayerTurn - 1).getName() + " will partake in the quest");
                questEntry.add(new Pair<> (playerList.get(tempPlayerTurn - 1), 0));
            }
            tempPlayerTurn++;
        }
        if (questEntry.size() < 1) {
            System.out.println ("No one was brave enough to take the " + storyCard.GetName());
            return;
        }

        //Incrementing through questEntry now will present the correct order of people to play
        int hostCardsBeforeQuest = host.hostCardsInHandBeforeQuest;
        //System.out.println ("Host select appropriate cards for the quest IN ORDER PROMPTED or they will be auto-selected for you.");
        //for (int y = 0; y < host.cardsInHand.size(); y++) {
           // System.out.println ("Card #" + (y + 1) + ": " + host.cardsInHand.get(y).GetName () + " with " + host.cardsInHand.get(y).GetBattlePoints () + " battle points.");
        //}
        //String hostInput = Console.ReadLine ();
        //String[] values = hostInput.Split (',');
        //List<Adventure> questStages = isValidQuest (values, host, storyCard.GetNumStages());
        System.out.println ("Host stages currently always being auto selected.");
        controller.runQuest(questStages, questEntry, stageNum, questEntryNum, hostCardsBeforeQuest);
        return;

        /*for (int i = 0; i < questStages.size(); i++) {
            Adventure a = questStages.get(i).getKey().get(0); //because the lists either lead with a Foe or Test
            if (a instanceof Foe) {
                System.out.println ("Stage " + (i+1) + " is a Foe");
                for (int x = 0; x < questEntry.size(); x++) { //showing player the cards they have, making them choose which cards they want to play
                    Player player = questEntry.get(x).getKey();
                    //String[] values = questPreParse (player); GET FROM GUI, temp sub below
                    String[] values = {"1", "2", "3", "4"};
                    int tempPlayerBattlePoints = validCards (values, player, "yes");
                    tempPlayerBattlePoints += linkedAllyBoost (storyCard.GetLinkedAlly (), player);
                    questEntry.set(x, new Pair<> (player, tempPlayerBattlePoints));
                }
                passedFoeStage (questEntry, questStages.get(i).getValue(), i);
            } else if (a instanceof Test) {
                System.out.println ("Stage " + (i+1) + " is a Test");
                biddingWar ((Test)a, questEntry, storyCard.GetName());
            } else {
                System.out.println ("Critical error in isQuest, quest stages array. 9001. This should never happen!");
            }

            stripCards (); //can;t have more than 12 at point after a stage, applies to host as well (though host having over 12 seems impossible)
            if (questEntry.size() < 1) {
                System.out.println ("All players eliminated");
                break;
            }
        } */

        //int hostUsedCards = storyCard.GetNumStages(); //Am I double counting here?
        //for (int i = 0; i < questStages.size(); i++) {//get rid of all the cards the host used
         //   ArrayList<Adventure> temp = questStages.get(i).getKey();
          //  hostUsedCards += temp.size();
            /*removeHostCards (temp, host); Easy port, wait to do with GUI */

        //}

       // for (int i = 0; i < hostUsedCards; i++) {//allow the host to draw cards equivalent to how many they used + num stages in quest
       //     host.cardsInHand.add((Adventure) draw("Adventure"));
       // }

    /*discardAfterTest (null, -1, -1, " immeadiately after");//can use this method to make the host get rid of cards over limit
    DO IN GUI, easy port
     */
       // stripCards();

       // for (int i = 0; i < questEntry.size(); i++) {//whomever made it through gets shields equivalent to the number of stages
       //     questEntry.get(i).getKey().shields += storyCard.GetNumStages ();
        //}

        //for (Player p : playerList) { //end of quest, make sure to reset this
        //    p.amourOnQuest = false;
        //}
    }

    public boolean canHost(int tempPlayerTurn, int numStages, String linkedFoe){
        boolean twinkies = false;
        Player p = playerList.get(tempPlayerTurn - 1);
        int testCounter = 0, foeCounter = 0;

        ArrayList<Pair<Foe,Integer>> foes = new ArrayList<> ();
        ArrayList<Weapon> weapons = new ArrayList<> ();

        for (int i = 0; i < p.cardsInHand.size(); i++) {
            if (p.cardsInHand.get(i) instanceof Foe) {
                if (p.cardsInHand.get(i).GetName () .equals( linkedFoe)) {
                    foes.add (new Pair<> ((Foe)p.cardsInHand.get(i), ((Foe)p.cardsInHand.get(i)).GetBoostedBattlePoints ()));
                    foeCounter++;
                } else {
                    foes.add (new Pair<> ((Foe)p.cardsInHand.get(i), ((Foe)p.cardsInHand.get(i)).GetBattlePoints ()));
                    foeCounter++;
                }
            } else if (p.cardsInHand.get(i) instanceof Weapon) {
                weapons.add ((Weapon)p.cardsInHand.get(i));
            } else if (p.cardsInHand.get(i) instanceof Test) {
                testCounter = 1;//can only use one test on a quest Dr.Suess
            }
        }

        try {
            ArrayList<Pair<Foe,Integer>> uniqueFoes = new ArrayList<>();
            for(int i = 0; i < foes.size(); i++){
                boolean isIn = false;
                for(int y = 0; y < uniqueFoes.size(); y++){
                    if(uniqueFoes.get(y).getValue() == foes.get(i).getValue())
                        isIn = true;
                }
                if(!isIn)
                    uniqueFoes.add(foes.get(i));
            }
             /*   foes.GroupBy (f => f.Item2)
.Select (grp => grp.First ())
.ToList (); //MAY have an exception here if no foe in list...not sure yet */

            ArrayList<Weapon> uniqueWeapons = new ArrayList<>();
            for(int i = 0; i < weapons.size(); i++){
                boolean isIn = false;
                for(int y = 0; y < uniqueWeapons.size(); y++){
                    if(uniqueWeapons.get(y).battlePoints == weapons.get(i).battlePoints)
                        isIn = true;
                }
                if(!isIn)
                    uniqueWeapons.add(weapons.get(i));
            }

        /*ArrayList<Weapon> uniqueWeapons = weapons.GroupBy (w => w.GetBattlePoints ())
.Select (grp => grp.First ())
.ToList (); //MAY have an exception here if no weapon in list...not sure yet */

            for (int i = 0; i < uniqueFoes.size(); i++) { //FOR TESTING PURPOSES ONLY
                System.out.println (i + ": " + uniqueFoes.get(i).getKey().GetName () + "|" + uniqueFoes.get(i).getValue());
            }
            for (int i = 0; i < uniqueWeapons.size(); i++) { //FOR TESTING PURPOSES ONLY
                System.out.println (i + ": " + uniqueWeapons.get(i).GetName () + "|" + uniqueWeapons.get(i).GetBattlePoints ());
            }

            for(int i = 0; i < p.cardsInHand.size(); i++){
                System.out.println(p.cardsInHand.get(i).GetName());
            }
            //Console.ReadLine ();
            if (numStages <= uniqueFoes.size() + uniqueWeapons.size() + testCounter && numStages <= foeCounter + testCounter) {
                twinkies = true;
            }

            return twinkies;

        }catch (Exception e){
            System.out.println (e.toString());
            System.out.println ("Unique foes / unique weapons group by exception caught");
            return twinkies;
        }
    }

    public ArrayList<Pair<ArrayList<Adventure>,Integer>> autoSelectStages (Player host, int numStages, String linkedFoe){
        ArrayList<Pair<ArrayList<Adventure>,Integer>> stages = new ArrayList<> ();
        ArrayList<Pair<Foe,Integer>> foeList = new ArrayList<> ();
        Test testCard = null;
        //I want every possible weapon combination
        //ArrayList<Weapon> weaponList = new ArrayList<>();
        Set<Weapon> weaponSet = new HashSet<>();

        //List<List<List<Weapon>>> weaponComboList = Lists.newArrayList(); UNDO TO ONE PAST HERE
        //ArrayList<ArrayList<ArrayList<Weapon>>> weaponComboList = new ArrayList<>();
        Set<Set<Weapon>> weaponComboSet = new HashSet<>();

        for (int i = 0; i < host.cardsInHand.size(); i++) {//sort out the weapons and foes
            if (host.cardsInHand.get(i) instanceof Weapon) {
                if (host.cardsInHand.get(i).GetName () .equals( "Sword"))//temp set to 11 to distinguish from horse in the current sorting
                    ((Weapon)host.cardsInHand.get(i)).setBattlePoints (11);
                weaponSet.add ((Weapon)host.cardsInHand.get(i));
            } else if (host.cardsInHand.get(i) instanceof Foe) {
                if (host.cardsInHand.get(i).GetName () .equals( linkedFoe))
                    foeList.add (new Pair<> ((Foe)host.cardsInHand.get(i), ((Foe)host.cardsInHand.get(i)).GetBoostedBattlePoints ()));
                else
                    foeList.add (new Pair<> ((Foe)host.cardsInHand.get(i), ((Foe)host.cardsInHand.get(i)).GetBattlePoints ()));
            } else if (host.cardsInHand.get(i) instanceof Test && testCard == null) {
                testCard = (Test)host.cardsInHand.get(i);
            }
        }

        //Step 1, get every possible k-combination of every possible number of cards, all different objects are treated as different even if they are the same weapon - DONE
        //Note, same objects in memory not paired together, also if {2,1} exists {1,2} is not added and list are in ascending order of battle points
        //But remember for above all different objects are treated as different!! See test output in foreach loops below if not clear
        //Step 2 pass weaponComboList and foeList(not yet created) to makeQuest() (can specify difficulty)
        //Step 3 pick foes as per method and give them weapons, checking that an added weapon packed does not contain an already used weapon with ReferenceEquals()
        //Step 4 make sure all the enemies are stronger than the previous one, if not redo 3 until that is the case
        //Step 5 throw in a test card if necessary (it would be put in if it exists)

        weaponComboSet = Sets.powerSet(weaponSet);

        ArrayList<Weapon> weaponList = new ArrayList<>();
        weaponList.addAll(weaponSet);

        List<List<Weapon>> weaponComboList = new ArrayList<>();
        Iterator iter = weaponComboSet.iterator();
        while(iter.hasNext()){
            weaponComboList.add(new ArrayList<>());
            weaponComboList.get(weaponComboList.size() - 1).addAll((Set<Weapon>)iter.next());
        }

        for(List<Weapon> outerList : weaponComboList){
            System.out.println("===========");
            for(Weapon wep : outerList){
                System.out.println(wep.name);
            }
        }

        /*for (int i = 0; i < weaponList.size(); i++) {//step 1
            List<Weapon> weaponListSizeModified = weaponList.subList(0,i);
            Collection<List<Weapon>> orderPerm = Collections2.permutations(weaponListSizeModified);
            List<List<Weapon>> orderPermList = new ArrayList<>();
            orderPermList.addAll(orderPerm);
            weaponComboList.add(orderPermList);
            //System.out.println  (K_Combinations.GetKCombs(weaponList,i+1).GetType());
        } */
        for (int i = 0; i < weaponList.size(); i++) { //Resetting sword battle points to 10 now that possible comparisons with horse are done
            if (weaponList.get(i).GetName () .equals("Sword"))
                weaponList.get(i).setBattlePoints (10);
        }//That's the beautiful thing about object references and not copies

        Collections.sort(foeList, new Comparator<Pair<Foe, Integer>>() {
            @Override
            public int compare(final Pair<Foe, Integer> o1, final Pair<Foe, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });


        //foeList.Sort ((x, y) => x.Item2.CompareTo (y.Item2)); //sort by battle points(boosted or otherwise)
        for (Pair x : foeList) {
            System.out.println (x.getKey() + "|" + x.getValue());
        }
        System.out.println ("Difficulty? Easy or Hard");
        //String difficulty = Console.ReadLine (); GET FROM GUI
        if(/*difficulty.toLowerCase() == "easy"  || */ true) //for now, get from GUI later
            makeQuest (stages, foeList, weaponComboList, testCard, 0, foeList.size(), numStages, "easy", weaponList, host);
        else
            makeQuest (stages, foeList, weaponComboList, testCard, foeList.size() - 1, -1, numStages, "hard", weaponList, host);
        return stages;
    }

    public boolean compari(int x, int y, String easyOrHard){
        if(easyOrHard.toLowerCase() .equals("easy"))
            return x < y;
        else
            return x > y;
    }

    public int op(int i, String easyOrHard){
        if(easyOrHard.toLowerCase() .equals( "easy"))
            return ++i;
        else
            return --i;
    }

    public void makeQuest(ArrayList<Pair<ArrayList<Adventure>,Integer>> stages, List<Pair<Foe,Integer>> foeList, List<List<Weapon>> weaponComboList, Test testCard,
                          int start, int finish, int numStages, String easyOrHard, ArrayList<Weapon> originalWeaponList, Player host){
        boolean foesFound = false;
        if (testCard != null)
            numStages--;

        Random rand = new Random();
        int [] intArry = ThreadLocalRandom.current().ints(0, foeList.size()).distinct().limit(numStages).toArray();
        int intArryCounter = 0;

        for (int i = start; compari (i, finish, easyOrHard); i = op (i, easyOrHard)) {
            /*Random rand = new Random (UUID.randomUUID().hashCode()); //for 1st nested list
            Random randy = new Random (UUID.randomUUID().hashCode()); //for 2nd nested list
            Random unequipped = new Random(UUID.randomUUID().hashCode()); //odds an enemy doesn't grab a weapon at all */
            //otherwise its all time dependent seeds and they'll all keep spitting out the same stuff, probably why I was having issues with randomness

            int x = rand.nextInt(weaponComboList.size());//Next (0, weaponComboList.size());
            System.out.println(weaponComboList.size() + " line 641 " + x);
            System.out.println(weaponComboList.get(x).size());
           // int y;
           // if(weaponComboList.get(x).size() == 0)
           //     y = 0;
          //  else
           //     y = rand.nextInt(weaponComboList.get(x).size()); //Next (0, weaponComboList [x].size()); used to be randy
            int z = rand.nextInt(4); //Next (0, 4); //see below, 25% chance of not getting a weapon used to be unequppied

            System.out.println ("x: " + x); //FOR TESTING ONLY
            System.out.println (weaponComboList.size() + " | " + weaponComboList.get(x).size()); //FOR TESTING ONLY


            if (weaponComboList.get(x).size() != 0 && z >= 1 ) {//because it can have an empty list in certain same weapon situations...
                ArrayList <Adventure> superTemporary = new ArrayList<>();
                superTemporary.add(foeList.get(intArry[intArryCounter]).getKey());

                List<Weapon> xList = weaponComboList.get(x);
                //List<Weapon> yList = xList.get(y);
                int tempWeaponBattlePoints = 0;
                for(int j = 0; j < xList.size(); j++){
                    tempWeaponBattlePoints += xList.get(j).battlePoints;
                }
                tempWeaponBattlePoints += foeList.get(intArry[intArryCounter]).getValue();

                stages.add (new Pair<ArrayList<Adventure>, Integer> ((superTemporary), tempWeaponBattlePoints));
                stages.get(stages.size() - 1).getKey().addAll(weaponComboList.get(x));
                intArryCounter++;
                /*for(Weapon weap0n : yList){ //This logic will hopefully remove any weapons not already in use, and we wont get our previous loop problems
                    for(int q = weaponComboList.size() - 1; q > -1; q--){
                        w_loop:
                        for(int w = weaponComboList.get(q).size() - 1; w > -1; w-- ){
                            for(int e = weaponComboList.get(q).get(w).size() - 1; e > -1; e--){
                                if(weap0n == weaponComboList.get(q).get(w).get(e)){
                                    weaponComboList.get(q).remove(w);
                                    continue w_loop;
                                }
                            }
                        }
                    }
                } */

            } else { //so an enemy can not get a weapon as well, this is often needed otherwise we are stuck in an infinite loop
                ArrayList <Adventure> superTemporary = new ArrayList<>();
                superTemporary.add(foeList.get(intArry[intArryCounter]).getKey());
                stages.add (new Pair<ArrayList<Adventure>, Integer> ((superTemporary), foeList.get(intArry[intArryCounter]).getValue()));
            }

            if (stages.size() == numStages) {
                foesFound = checkSelectedStageCards (stages);
                if (foesFound)
                    break;
                else {
                    System.out.println("In the other reset");
                    stages.clear();
                    intArry = ThreadLocalRandom.current().ints(0, foeList.size()).distinct().limit(numStages).toArray();
                    intArryCounter = 0;
                }
            }
            if (!compari(op(i,easyOrHard),finish, easyOrHard) && !foesFound) { //we know we've reached the end and haven't found what we're looking for...though will this even run anymore?
                System.out.println ("In the reset");
                if (start < finish)
                    i = start - 1;
                else
                    i = start + 1;
                stages.clear (); //to prevent double allocation of foes
                intArry = ThreadLocalRandom.current().ints(0, foeList.size()).distinct().limit(numStages).toArray();
                intArryCounter = 0;
            }
        }

        System.out.println ("Looks like we've found or foe stages, still need to add a test card (IF NEEDED)");
        if (testCard != null) {
            Random randA = new Random ();
            int a = randA.nextInt (stages.size());

            ArrayList<Adventure> superTemporary = new ArrayList<>();

            superTemporary.add(testCard);
            stages.add(a, new Pair<ArrayList<Adventure>, Integer> (superTemporary, -1)); //Test does not have any battle points
        }

        host.hostCardsInHandBeforeQuest = host.cardsInHand.size();

        for(Pair<ArrayList<Adventure>, Integer> stagePair : stages){ //remove the stage card from the host hand once they've been selected,will need to do this in manual select stages too
            for(Adventure a : stagePair.getKey()){
                for(int i = host.cardsInHand.size() - 1; i > -1; i--){
                    Adventure b = host.cardsInHand.get(i);
                    if(a == b)
                        host.cardsInHand.remove(i);
                }
            }
        }

    }


    public boolean checkSelectedStageCards(ArrayList<Pair<ArrayList<Adventure>,Integer>> stages){
        boolean works = false;
        for (Pair<ArrayList<Adventure>, Integer> cardsList : stages /*var cardsList in stages*/) {
            int tempBattlepointstotal = 0;
            for (Adventure adventureCard : cardsList.getKey() /*var adventureCard in cardsList.Item1*/) {
                if(adventureCard instanceof Weapon)//because Foe battle points have already been accounted for
                    tempBattlepointstotal += adventureCard.GetBattlePoints ();
            }
            cardsList = new Pair<>(cardsList.getKey(), tempBattlepointstotal);
        }

        Collections.sort(stages, new Comparator<Pair<ArrayList<Adventure>, Integer>>() {
            @Override
            public int compare(final Pair<ArrayList<Adventure>, Integer> o1, final Pair<ArrayList<Adventure>, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Pair<ArrayList<Adventure>, Integer> a : stages){//FOR TESTING ONLY
            for (Adventure b : a.getKey()) {
                System.out.println (b);
            }
            System.out.println ("Total: " + a.getValue());
        }
        ArrayList<Integer> battlePointValues = new ArrayList<Integer>();
        for (int i = 0; i < stages.size(); i++) {

            boolean containsCommonItem = false;
            ArrayList<Adventure> firstCompared = stages.get(i).getKey();

            for(int a = 1; a < firstCompared.size() - 1; a++){
                for(int b = a+1; b < firstCompared.size(); b++) {
                    System.out.println(firstCompared.get(a).GetName() + " ||| " + firstCompared.get(b));
                    if (firstCompared.get(a).GetName().equals(firstCompared.get(b).GetName())) {
                        containsCommonItem = true;
                        break;
                    }
                }
            }

            if (containsCommonItem) {
                System.out.println ("Common item found, re-doing stage making.");
                return false;
            }

            for (int y = i+1; y < stages.size(); y++) {
                ArrayList<Adventure> secondCompared = stages.get(y).getKey();
                containsCommonItem = false;

                outer:
                for(int a = 1; a < firstCompared.size(); a++){
                    for(int b = 1; b < secondCompared.size(); b++){
                        System.out.println(firstCompared.get(a).GetName() + " || " + secondCompared.get(b).GetName());
                        if( firstCompared.get(a) == secondCompared.get(b)){
                            containsCommonItem = true;
                            break outer;
                        }

                    }
                }

                //boolean containsCommonItem = stages[i].Item1.Any(x => stages[y].Item1.Any(z => ReferenceEquals(x,z)));
                if (containsCommonItem) {
                    System.out.println ("Common item found, re-doing stage making.");
                    return false;
                }
            }
            battlePointValues.add (stages.get(i).getValue());
        }

        for(Integer x : battlePointValues)
            System.out.println ("In battlePointsValues: " + x);

        Collections.sort(battlePointValues, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return o1.compareTo(o2);
            }
        });

        if (battlePointValues.size() > 0){
            boolean duplicate = false;

            for(int i = 0; i < battlePointValues.size() - 1; i++){
                if(battlePointValues.get(i) == battlePointValues.get(i+1)) {
                    duplicate = true;
                    break;
                }
            }

       /* var results = battlePointValues.GroupBy(i => i)
   .Where(g => g.size()() > 1)
   .Select(g => g.Key);
        var duplicate = results.FirstOrDefault (); //might be more than one, but we dont care. One is enough to ruin it
        System.out.println (duplicate); */
            if (duplicate)
                System.out.println ("Same battle points for a stage, re-doing stage making. ");
            else
                works = true;
        }

        return works;
    }

    public int linkedAllyBoost(String linkedAlly, Player p){
        int battlePoints = 0;
        //Step 1 check for possible linked ally
        //Step 2 check for both Queen Iseult AND Sir Tristan together
    /*var results = from Ally ally in p.alliesInPlay
    where ally.GetName () == linkedAlly
    select ally;
    var candidate = results.FirstOrDefault (); */
        boolean queenI = false;
        boolean sirT = false;

        for (int i = 0; i < p.alliesInPlay.size(); i++) {
            Ally candidate = p.alliesInPlay.get(i);
            if (candidate.GetName ().equals("Sir Percival") && linkedAlly.equals("Sir Percival")) {
                battlePoints += 15; //5 goes to 20 bp on the Search for the Holy Grail Quest
                System.out.println ("Sir Percival boost!!");
            } else if (candidate.GetName ().equals("Sir Gawain") && linkedAlly.equals("Sir Gawain")) {
                battlePoints += 10; //10 goes to 20 bp on the Test of the Green Knight Quest
                System.out.println ("Sir Gawain boost!!");
            } else if (candidate.GetName ().equals("Sir Lancelot") && linkedAlly.equals("Sir Lancelot")) {
                battlePoints += 10; //15 goes to 25 bp on the Quest to Defend the Queen's Honor Quest
                System.out.println ("Sir Lancelot boost!!");
            } else if(candidate.GetName().equals("Queen Iseult")){
                queenI = true;
            } else if(candidate.GetName().equals("Sir Tristan")){
                sirT = true;
            }
        }
        //Step 1 complete, not for step 2
    /*var pairedAllies = from Ally ally in p.alliesInPlay
    where ally.GetName () == "Queen Iseult" || ally.GetName () == "Sir Tristan"
    select ally; */

        if (queenI && sirT) {//should be exactly two with both of them in play
            battlePoints += 10; //10 goes to 20 when Queen Iseult is also in play
            System.out.println ("Sir Tristan boost!!");
        }

        return battlePoints;
    }

    public void passedFoeStage(List<Pair<Player,Integer>> questEntry, int stageBattlePoints, int stageNo){
        for (int x = questEntry.size() - 1; x > -1; x--) { //check if player has passed the stage succesfully
            Pair<Player,Integer> p = questEntry.get(x);
            System.out.println("Player battle score: " + p.getValue() + " | " + "Stage battle score " + stageBattlePoints);
            if (p.getValue() >= stageBattlePoints) {
                System.out.println (p.getKey().getName () + "has passed stage " + (stageNo + 1));
                questEntry.set(x, new Pair<Player, Integer>(p.getKey(), 0));//reset their bp at the end of each round
            } else {
                System.out.println (p.getKey().getName() + " has been eliminated.");
                questEntry.remove(x);
            }
        }
    }

    public ArrayList<Pair<Integer, Integer>> biddingWar(Test test, List<Pair<Player,Integer>> questEntry, String questName){
        int previousMaxBid = test.GetMinBids ();
        if (questEntry.size() == 1 && 3 > previousMaxBid)
            previousMaxBid = 3;//if there's only one player in the quest, the min bids of a test is 3
        if (questName == "Search for the Questing Beast" && test.GetName () == "Test of the Questing Beast")
            previousMaxBid = 4;

        ArrayList <Pair<Integer, Integer>> bidMath = new ArrayList<> (); //first for player max bid, second for free bids

        for (int i = 0; i < questEntry.size(); i++) {
            Player p = questEntry.get(i).getKey();
            if(!p.extraCardTest) {
                p.cardsInHand.add((Adventure) draw("Adventure")); //everyone partaking gets an additional adventure card before each stage
                p.extraCardTest = true;
            }
            int playerMaxBid = p.cardsInHand.size();
            int freeBids = 0;

            if (questName == "Search for the Questing Beast") {
                boolean pelly = false;
                boolean queenI = false;
                boolean sirT = false;
                for(int x = 0; x < p.alliesInPlay.size(); x++){
                    if(p.alliesInPlay.get(x).GetName().equals("King Pellinore")){
                        pelly = true;
                    } else if(p.alliesInPlay.get(x).GetName().equals("Queen Iseult")){
                        queenI = true;
                    }else if(p.alliesInPlay.get(x).GetName().equals("Sir Tristan")){
                        sirT = true;
                    }
                }
                if (pelly) {
                    playerMaxBid += 4;
                    freeBids += 4;
                }
                if(queenI && sirT){
                    playerMaxBid += 2;
                    freeBids += 2;
                }
            }


            for (Ally a : p.alliesInPlay) {
                playerMaxBid += a.GetBids ();
                freeBids += a.GetBids ();
            }

            if (p.amourOnQuest) {
                System.out.println("amour on quest is true");
                playerMaxBid += 1;
                freeBids += 1;
            }

            System.out.println(p.cardsInHand.size());
            System.out.println (p.getName() + " has max bids of " + playerMaxBid + ". (including " + freeBids + " free bids");
            bidMath.add (new Pair<Integer,Integer> (playerMaxBid, freeBids));
        }
        return bidMath;
       /* for (int i = questEntry.size() - 1; i > -1; i--) {
            if (bidMath.get(i).getKey() < previousMaxBid) {
                System.out.println (questEntry.get(i).getKey().getName() + " has been eliminated for insufficient bid ability.");
                questEntry.remove(i);
                bidMath.remove (i);
            }
        } */

        //if (questEntry.size() == 1) {
          //  System.out.println (questEntry.get(0).getKey().getName () + " do you wish to place the min bid of " + previousMaxBid + " to continue?");
        /*string userInput = Console.ReadLine (); DO FROM GUI
        if (!(userInput.ToLower () == "yes" || userInput.ToLower () == "y") || previousMaxBid > bidMath[0].Item1 ) {
            System.out.println ("Okay, then you are eliminated.");
            return;
        } */


    /*while (questEntry.Count > 1) { DO THIS IN GUI WITH CONTROLLER
        for (int i = 0; i < questEntry.Count; i++) {
            if (questEntry.Count == 1)
                break;

            System.out.println (questEntry[i].Item1.getName() + " you must bid higher than " + previousMaxBid + "\n What is your bid?" +
                    " (Invalid bid will results in disqualification");
            string userInput = Console.ReadLine ();
            int x = -1;
            bool parsed = Int32.TryParse (userInput, out x);
            if (parsed && x > previousMaxBid && x <= bidMath [i].Item1) {
                System.out.println ("Okay " + questEntry[i].Item1.getName() + "you have bid successfully with " + x + " bids.");
                previousMaxBid = x;
            } else {
                System.out.println ("Invalid or not enough bids. " + questEntry[i].Item1.getName() +  " has been eliminated.");
                questEntry.RemoveAt (i);
                bidMath.RemoveAt (i);
                i--;
            }
        }
    } */

        /*if (questEntry.size() < 1) {
            System.out.println ("All players eliminated");
            /*discardAfterTest (null, -1, -1); DO IN GUI, easy port */
      /*  } else if (questEntry.size() == 1) {
            System.out.println ("Congrats " + questEntry.get(0).getKey().getName () + " you have passed the test!");
            /*discardAfterTest (questEntry.get(0).getKey(), previousMaxBid, bidMath.get(0).getValue()); DO IN GUI, easy port */
       /* } else {
            System.out.println ("Only one player should pass a test, this should never happen! Error 9001");
        } */
    }

    public void isEvent(Event storyCard){
        System.out.println (storyCard.GetName() + ": " + storyCard.GetDescription());
        ArrayList<Player> playersTargeted = getPlayersTargeted (storyCard.GetWhosAffected());
        int tempPlayerturn = playerTurn;
        for (int i = 0; i < numPlayers; i++) { //add order here using tempPlayerTurn, playerTurn and checking if in list
            if (tempPlayerturn > numPlayers) {
                tempPlayerturn = 1;
            }

        /*var results = from Player player in playersTargeted
        where player == playerList [tempPlayerturn - 1]
        select player;
        var successfulCandidate = results.FirstOrDefault();
        var successfulCandidate = results.FirstOrDefault(); */
            Player successfulCandidate = null;
            for(int j = 0; j < playersTargeted.size(); j++){
                if(playersTargeted.get(j) == playerList.get(tempPlayerturn - 1)){
                    successfulCandidate = playersTargeted.get(j);
                    break;
                }
            }

            if (successfulCandidate != null) {
                System.out.println ("Successful Candidate: " + successfulCandidate.getName());
                Player p = successfulCandidate;
                p.shields += storyCard.GetShieldModifier ();
                if (p.shields < 0)
                    p.shields = 0;
                for (int y = 0; y < storyCard.GetAdventureCardModifier (); y++) {
                    p.cardsInHand.add ((Adventure)draw ("Adventure"));
                }
                if (storyCard.GetEliminateAllies ()) {
                    p.alliesInPlay.clear ();
                }
                if (storyCard.GetWeaponCardModifier () > 0) { //then we know its King's Call to Arms
                    System.out.println (p.getName () + " get rid of one weapon card, if that's not possible, get rid of two foe cards.");
                    for (int y = 0; y < p.cardsInHand.size(); y++) {
                        System.out.println ("Card #" + (y + 1) + ": " + p.cardsInHand.get(y).GetName () + " with " + p.cardsInHand.get(y).GetBattlePoints () + " battle points.");
                    }
                /*string cardsChosen = Console.ReadLine ();
                string[] values = cardsChosen.Split (',');
                validDiscardEvent (values, p, "Weapon"); */
                }
                //kings recognition is handled strictly in getPlayersTargeted and isQuest

                System.out.println (p.getName () + ":");
                for (int y = 0; y < p.cardsInHand.size(); y++) {
                    System.out.println ("Card #" + (y + 1) + ": " + p.cardsInHand.get(y).GetName () + " with " + p.cardsInHand.get(y).GetBattlePoints () + " battle points.");
                }
            /*if (p.cardsInHand.size() > 12) {
                System.out.println ("Alert, " + p.getName () + " you have more than 12 cards. Discard to (at least) 12 or be auto discarded.");
                System.out.println ("Choose the cards you want to discard, seperated by commas. Invalid cards will be ignored.");
                string cardsChosen = Console.ReadLine ();
                string[] values = cardsChosen.Split (',');
                validDiscardEvent (values, p, "Adventure");
            } DONE IN GUI NOW */
            }
            tempPlayerturn++;
        }//end outer for
        //stripCards ();
    }

    public ArrayList<Player> getPlayersTargeted(String whosAffected){ //this gets a list of whos affected, do something similar to temp player turn elsewhere to maintain discard order
        ArrayList<Player> p = new ArrayList<Player> ();
        if(whosAffected == Event.playersTargeted.get(0)){//LowestRankAndShield
            for (int i = 0; i < playerList.size(); i++) {
                if (i == 0) {
                    p.add (playerList.get(i));
                }
                if (i != 0 && Player.rankDictionary.get(playerList.get(i).rank) < Player.rankDictionary.get(p.get(0).rank)) {//even if there's more than one player, it means they are same shield and rank so we can always compare with 0
                    p.clear ();
                    p.add (playerList.get(i));
                }else if (i != 0 && playerList.get(i).rank == p.get(0).rank) {
                    if (playerList.get(i).shields < p.get(0).shields) {
                        p.clear ();
                        p.add (playerList.get(i));
                    } else if (playerList.get(i).shields == p.get(0).shields) {
                        p.add (playerList.get(i));
                    }
                }
            }
        }else if(whosAffected == Event.playersTargeted.get(1)){//All
            for (int i = 0; i < playerList.size(); i++) {
                p.add (playerList.get(i));
            }
        }else if(whosAffected == Event.playersTargeted.get(2)){//AllExceptDrawer
            int tempPlayerTurn = playerTurn;
            for (int i = 0; i < playerList.size(); i++) {//Accessing raw player turn causes problems and shouldn't be used for indexing
                if (tempPlayerTurn > numPlayers)
                    tempPlayerTurn = 1;
                if (i != (tempPlayerTurn - 1)) {
                    p.add (playerList.get(i));
                }
            }
        }else if(whosAffected == Event.playersTargeted.get(3)){//DrawerOnly
            int tempPlayerTurn = playerTurn;
            if (tempPlayerTurn > numPlayers)//Accessing raw player turn causes problems and shouldn't be used for indexing
                tempPlayerTurn = 1;
            //Console.WriteLine (playerTurn - 1);
            p.add (playerList.get(tempPlayerTurn - 1));
        }else if(whosAffected == Event.playersTargeted.get(4)){//HighestRanked
            for (int i = 0; i < playerList.size(); i++) {
                if (i == 0) {
                    p.add (playerList.get(i));
                }
                if (i != 0 && Player.rankDictionary.get(playerList.get(i).rank) > Player.rankDictionary.get(p.get(0).rank)) {
                    p.clear ();
                    p.add (playerList.get(i));
                } else if (i != 0 && playerList.get(i).rank == p.get(0).rank) {
                    p.add (playerList.get(i));
                }
            }
        }else if(whosAffected == Event.playersTargeted.get(5)){//LowestRanked
            for (int i = 0; i < playerList.size(); i++) {
                if (i == 0) {
                    p.add (playerList.get(i));
                }
                if (i != 0 && Player.rankDictionary.get(playerList.get(i).rank) < Player.rankDictionary.get(p.get(0).rank)) {
                    p.clear ();
                    p.add (playerList.get(i));
                } else if (i != 0 && playerList.get(i).rank == p.get(0).rank) {
                    p.add (playerList.get(i));
                }
            }
        }else if(whosAffected == Event.playersTargeted.get(6)){//Next
            QuestBonusShields += 2;
        }
        return p;
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
            //System.out.println ("Unhandled out of adventure cards case");
            adventureDeck = discardAdventureDeck;
            shuffle("adventureDeck");
            discardAdventureDeck = new ArrayList<>();
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

        adventureDeck.add(createAdventureCard("Test", "Test of Valor", 0, 1));
        adventureDeck.add(createAdventureCard("Test", "Test of Valor", 0,1));

        adventureDeck.add(createAdventureCard("Test", "Test of Temptation", 0,1));
        adventureDeck.add(createAdventureCard("Test", "Test of Temptation", 0,1));

        adventureDeck.add(createAdventureCard("Test", "Test of the Questing Beast", 0, 4));
        adventureDeck.add(createAdventureCard("Test", "Test of the Questing Beast", 0, 4));

        adventureDeck.add(createAdventureCard("Test", "Test of Morgan Le Fey", 0, 3));
        adventureDeck.add(createAdventureCard("Test", "Test of Morgan Le Fey", 0, 3));


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
        /*
         storyDeck.add(createEventCard("Chivalrous Deed", 3, 0, false, 0, 0, Event.playersTargeted.get(0), "Player(s) with both lowest rank and least amount of shields, receives 3 shields"));
         storyDeck.add(createEventCard("Pox", -1, 0, false, 0, 0, Event.playersTargeted.get(2), "All players except the player drawing this card lose 1 shield"));
         storyDeck.add(createEventCard("Plague", -2, 0, false, 0, 0, Event.playersTargeted.get(3), "Drawer loses two shields if possible"));
         storyDeck.add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted.get(6), "The next player(s) to complete a Quest will receive 2 extra shields"));
         storyDeck.add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted.get(6), "The next player(s) to complete a Quest will receive 2 extra shields"));
         storyDeck.add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted.get(5), "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
         storyDeck.add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted.get(5), "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
         storyDeck.add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted.get(1), "All Allies in play must be discarded"));
         storyDeck.add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted.get(1), "All Allies in play must be discarded"));
         storyDeck.add(createEventCard("King's Call To Arms", 0, 0, false, 1, 2, Event.playersTargeted.get(4), "The highest ranked player(s) must place 1 weapon in the discard pile. If unable to do so, 2 Foe Cards must be discarded"));
         storyDeck.add(createEventCard("Prosperity Throughout the Realm", 0, 2, false, 0, 0, Event.playersTargeted.get(1), "All players may immeadiately draw two Adventure Cards"));
        */
        /*Tournament*/

        storyDeck.add(createTournamentCard("AT YORK", 0));
        storyDeck.add(createTournamentCard("AT TINTAGEL", 1));
        storyDeck.add(createTournamentCard("AT ORKNEY", 2));
        storyDeck.add(createTournamentCard("AT CAMELOT", 3));

        /*Quest*/
        storyDeck.add(createQuestCard("Journey through the Enchanted Forest", 3, "Evil Knight", "none"));
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
        storyDeck.add(createQuestCard("Test of the Green Knight", 4, "Green Knight", "Sir Gawain"));


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