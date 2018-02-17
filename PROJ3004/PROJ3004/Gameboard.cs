using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace PROJ3004
{
    public class Gameboard
    {
        public List<Adventure> adventureDeck = new List<Adventure>();//125
        public List<Adventure> discardAdventureDeck = new List<Adventure>();

        public List<Story> storyDeck = new List<Story>();//28
        public List<Story> discardStoryDeck = new List<Story>();
        public string[] tornamentTestScript = System.IO.File.ReadAllLines("test3.txt");//TESTING
        //public string[] eventtTestScript = System.IO.File.ReadAllLines("test3.txt");//TESTING

        public List<Player> playerList = new List<Player>();
        public int numPlayers = 0;
        public bool gameOver = false;
        public int playerTurn = 1;
        public bool testingMode = false;//TESTING
        public int QuestBonusShields = 0;
        public int fileCounter = -1; //TESTING

        public static void Main(string[] args) //Move all creation to a seperate function, only keep main control loop in here moving forward!
        {
            Gameboard play = new Gameboard();

            Console.WriteLine("\n<-------------------->Welcome Nerds <----------------------->\n");

            Console.WriteLine("Would you like to run the the testing mode? Enter 'yes' or 'no' ");
            string userInput = Console.ReadLine();

            if (userInput == "yes")
            {
                play.testingMode = true;
            }

            play.createDeck();  //could move everything except play.runGame() into runGame()...think about this...yes I should do this moving forward IMPLEMENT THIS CHANGE
            play.shuffle("adventureDeck");
            play.shuffle("storyDeck");

            play.initializePlayers(); //make the players
            play.deal(); //Giving everyone 12 adventure cards

            for (int i = 0; i < play.playerList.Count; i++)
            {//FOR TESTING ONLY, trying to figure wtf is going on
                Player p = play.playerList[i];
                Console.WriteLine(p.getName());
                Console.WriteLine("-----------------");
                for (int x = 0; x < p.cardsInHand.Count; x++)
                {
                    Console.WriteLine(p.cardsInHand[x].GetName());
                }
                Console.WriteLine("-------------");
            }

            play.runGame();


        }

        /*Control Loop */
        public void runGame()
        {
            while (!gameOver)
            {
                //First thing that needs to be done, P1 has to draw a story card
                Story storyCard = (Story)draw("Story");
                if (storyCard is Quest)
                {

                }
                else if (storyCard is Tournament)
                {
                    Console.WriteLine("<------------ Tournament Card Picked Up ------------>");
                    isTournament((Tournament)storyCard);
                    for (int x = 0; x < playerList.Count; x++)
                    {//FOR TESTING ONLY, making sure the right person got the number of shields
                        Console.WriteLine(playerList[x].shields);
                        Console.WriteLine(playerList[x].cardsInHand.Count);
                    }
                    gameOver = rankUp();
                    //Remove it afterwards */
                    Console.WriteLine("Ranks so Far are : ");
                    for (int i = 0; i < playerList.Count; i++)
                    { //printing current rank of each player.
                        Console.WriteLine(playerList[i].getName() + ": " + playerList[i].rank + " with " + playerList[i].shields + " sheilds.");
                    }
                    // till here */
                    whosTurn(); //This is now working
                }
                else if (storyCard is Event)
                {
                    Console.WriteLine("<------------ Event Card Picked Up ------------>");
                    //Console.ReadLine();
                    isEvent((Event)storyCard);
                    gameOver = rankUp();
                    //Remove it afterwards */
                    Console.WriteLine("Ranks so Far are : ");
                    for (int i = 0; i < playerList.Count; i++)
                    { //printing current rank of each player.
                        Console.WriteLine(playerList[i].getName() + ": " + playerList[i].rank + " with " + playerList[i].shields + " sheilds.");
                    }
                    // till here */
                    whosTurn();

                }
                else
                {
                    Console.WriteLine("Critical error 9001 in card type, this should never happen");
                }

            }
            Console.WriteLine("Someone has won...expand on this");
        }

        public int whosTurn()
        {
            if (playerTurn > numPlayers)
            {
                playerTurn = 1;
            }
            Console.WriteLine("Entered who's turn: " + playerTurn);
            return playerTurn++;
        }

        public bool rankUp()
        {
            bool hasWon = false;
            for (int i = 0; i < playerList.Count; i++)
            {
                Player p = playerList[i];
                if (p.rank == "Squire" && p.shields >= 5)
                {
                    p.rank = "Knight";
                    p.shields -= 5;
                }
                if (p.rank == "Knight" && p.shields >= 7)
                {
                    p.rank = "Champion Knight";
                    p.shields -= 7;
                }
                if (p.rank == "Champion Knight" && p.shields >= 10)
                {
                    //Player has won and become a knight of the round
                    hasWon = true;
                }
            }
            return hasWon;
        }

        public void isEvent(Event storyCard)
        {
            Console.WriteLine(storyCard.GetName() + ": " + storyCard.GetDescription());
            List<Player> playersTargeted = getPlayersTargeted(storyCard.GetWhosAffected());
            int tempPlayerturn = playerTurn;
            for (int i = 0; i < numPlayers; i++)
            { //add order here using tempPlayerTurn, playerTurn and checking if in list
                if (tempPlayerturn > numPlayers)
                {
                    tempPlayerturn = 1;
                }

                var results = from Player player in playersTargeted
                              where player == playerList[tempPlayerturn - 1]
                              select player;
                var successfulCandidate = results.FirstOrDefault();

                if (successfulCandidate != null)
                {
                    Console.WriteLine("Successful Candidate: " + successfulCandidate.getName());
                    Player p = successfulCandidate;
                    p.shields += storyCard.GetShieldModifier();
                    if (p.shields < 0)
                        p.shields = 0;
                    for (int y = 0; y < storyCard.GetAdventureCardModifier(); y++)
                    {
                        p.cardsInHand.Add((Adventure)draw("Adventure"));
                    }
                    if (storyCard.GetEliminateAllies())
                    {
                        p.alliesInPlay.Clear();
                    }
                    if (storyCard.GetWeaponCardModifier() > 0)
                    { //then we know its King's Call to Arms
                        Console.WriteLine(p.getName() + " get rid of one weapon card, if that's not possible, get rid of two foe cards.");
                        for (int y = 0; y < p.cardsInHand.Count; y++)
                        {
                            Console.WriteLine("Card #" + (y + 1) + ": " + p.cardsInHand[y].GetName() + " with " + p.cardsInHand[y].GetBattlePoints() + " battle points.");
                        }
                        //string cardsChosen = Console.ReadLine();
                        //removing random card
                        string cardsChosen;
                        //string userInput;
                        if (testingMode == true)
                        {

                            cardsChosen = " ";//TESTING
                            Console.WriteLine("USER ENTERD -> " + "For Forcing Discard  ");//TESTING
                        }
                        else
                        {
                            cardsChosen = Console.ReadLine();
                        }

                        string[] values = cardsChosen.Split(',');
                        validDiscardEvent(values, p, "Weapon");
                    }
                    //kings recognition is handled strictly in getPlayersTargeted and isQuest

                    Console.WriteLine(p.getName() + ":");
                    for (int y = 0; y < p.cardsInHand.Count; y++)
                    {
                        Console.WriteLine("Card #" + (y + 1) + ": " + p.cardsInHand[y].GetName() + " with " + p.cardsInHand[y].GetBattlePoints() + " battle points.");
                    }
                    if (p.cardsInHand.Count > 12)
                    {
                        Console.WriteLine("Alert, " + p.getName() + " you have more than 12 cards. Discard to (at least) 12 or be auto discarded.");
                        Console.WriteLine("Choose the cards you want to discard, seperated by commas. Invalid cards will be ignored.");


                        //string cardsChosen = Console.ReadLine();

                        string cardsChosen;
                        //string userInput;
                        if (testingMode == true)
                        {

                            cardsChosen = " ";//TESTING
                            Console.WriteLine("USER ENTERD -> " + " Forcing Discard ");//TESTING
                        }
                        else
                        {
                            cardsChosen = Console.ReadLine();
                        }

                        string[] values = cardsChosen.Split(',');
                        validDiscardEvent(values, p, "Adventure");
                    }
                }
                tempPlayerturn++;
            }//end outer for
            stripCards();
        }

        public void validDiscardEvent(string[] values, Player p, string type)
        {
            List<int> validatedCards = new List<int>();
            for (int i = 0; i < values.Length; i++)
            { //Making sure values that aren't acceptable ints are stripped
                int temp = -1;
                bool tempBool = int.TryParse(values[i], out temp);
                if (tempBool && temp > 0 && temp <= p.cardsInHand.Count)
                {
                    validatedCards.Add(temp);
                }
            }
            validatedCards.Sort();
            if (type == "Adventure")
            {
                for (int i = validatedCards.Count - 1; i >= 0; i--)
                {
                    Console.WriteLine(p.getName() + " discarded " + p.cardsInHand[validatedCards[i] - 1].GetName());
                    p.discardPile.Add(p.cardsInHand[validatedCards[i] - 1]);
                    p.cardsInHand.RemoveAt(validatedCards[i] - 1);
                }//here they can get rid of more than two adventure cards if they want..but why would they? Also not getting rid of enough is handled later in stripCards()
            }
            else if (type == "Weapon")
            {//Gets rid of exactly one weapon card if possible. If more than one chosen, one closest to the back is gotten rid of
                bool oneWeapon = false;
                for (int i = validatedCards.Count - 1; i >= 0; i--)
                {
                    if (p.cardsInHand[validatedCards[i] - 1] is Weapon)
                    {
                        oneWeapon = true;
                        Console.WriteLine(p.getName() + " discarded " + p.cardsInHand[validatedCards[i] - 1].GetName());
                        p.discardPile.Add(p.cardsInHand[validatedCards[i] - 1]);
                        p.cardsInHand.RemoveAt(validatedCards[i] - 1);
                        break;
                    }
                }
                if (!oneWeapon)
                {
                    Console.WriteLine("No valid weapon card specified, attempting to  auto-discard a weapon...");
                    for (int i = p.cardsInHand.Count - 1; i >= 0; i--)
                    {
                        Console.WriteLine("WTF3: " + i);
                        if (p.cardsInHand[i] is Weapon)
                        {
                            Console.WriteLine(p.getName() + " auto-discarded " + p.cardsInHand[i].GetName());
                            p.discardPile.Add(p.cardsInHand[i]);
                            p.cardsInHand.RemoveAt(i);
                            return;
                        }
                    }
                    //Unrelated, any variable from an out scope is also known. Even if declared after in the method body
                    Console.WriteLine("Player has no weapon cards! Checking if specified input has foe card(s)");
                    int foeDiscardCounter = 0; //Gets rid of up to two foe cards, if more than two are given, two closest to the back are taken away
                    for (int i = validatedCards.Count - 1; i >= 0; i--)
                    {
                        if (foeDiscardCounter >= 2)
                            break;
                        if (p.cardsInHand[validatedCards[i] - 1] is Foe)
                        {
                            foeDiscardCounter++;
                            Console.WriteLine(p.getName() + " discarded " + p.cardsInHand[validatedCards[i] - 1].GetName());
                            p.discardPile.Add(p.cardsInHand[validatedCards[i] - 1]);
                            p.cardsInHand.RemoveAt(validatedCards[i] - 1);
                        }
                    }
                    if (foeDiscardCounter < 2)
                    {
                        Console.WriteLine(foeDiscardCounter + " was discarded. Need to be two. Auto-discarding if possible...");
                        for (int i = p.cardsInHand.Count - 1; i >= 0; i--)
                        {
                            if (foeDiscardCounter >= 2)
                                break;
                            Console.WriteLine("Printing array size --------------> " + p.cardsInHand.Count
                                             );
                            Console.WriteLine("Printing i --------------> " + i);
                            Console.WriteLine("TEST---------> " + p.cardsInHand[i]);
                            if (p.cardsInHand[i] is Foe)
                            {
                                foeDiscardCounter++;
                                Console.WriteLine(p.getName() + " auto-discarded " + p.cardsInHand[i].GetName());
                                p.discardPile.Add(p.cardsInHand[i]);
                                p.cardsInHand.RemoveAt(i);
                            }
                        }
                    }
                }
            }
        }

        public List<Player> getPlayersTargeted(string whosAffected)
        { //this gets a list of whos affected, do something similar to temp player turn elsewhere to maintain discard order
            List<Player> p = new List<Player>();
            if (whosAffected == Event.playersTargeted[0])
            {//LowestRankAndShield
                for (int i = 0; i < playerList.Count; i++)
                {
                    if (i == 0)
                    {
                        p.Add(playerList[i]);
                    }
                    if (i != 0 && Player.rankDictionary[playerList[i].rank] < Player.rankDictionary[p[0].rank])
                    {//even if there's more than one player, it means they are same shield and rank so we can always compare with 0
                        p.Clear();
                        p.Add(playerList[i]);
                    }
                    else if (i != 0 && playerList[i].rank == p[0].rank)
                    {
                        if (playerList[i].shields < p[0].shields)
                        {
                            p.Clear();
                            p.Add(playerList[i]);
                        }
                        else if (playerList[i].shields == p[0].shields)
                        {
                            p.Add(playerList[i]);
                        }
                    }
                }
            }
            else if (whosAffected == Event.playersTargeted[1])
            {//All
                for (int i = 0; i < playerList.Count; i++)
                {
                    p.Add(playerList[i]);
                }
            }
            else if (whosAffected == Event.playersTargeted[2])
            {//AllExceptDrawer
                int tempPlayerTurn = playerTurn;
                for (int i = 0; i < playerList.Count; i++)
                {//Accessing raw player turn causes problems and shouldn't be used for indexing
                    if (tempPlayerTurn > numPlayers)
                        tempPlayerTurn = 1;
                    if (i != (tempPlayerTurn - 1))
                    {
                        p.Add(playerList[i]);
                    }
                }
            }
            else if (whosAffected == Event.playersTargeted[3])
            {//DrawerOnly
                int tempPlayerTurn = playerTurn;
                if (tempPlayerTurn > numPlayers)//Accessing raw player turn causes problems and shouldn't be used for indexing
                    tempPlayerTurn = 1;
                //Console.WriteLine (playerTurn - 1);
                p.Add(playerList[tempPlayerTurn - 1]);
            }
            else if (whosAffected == Event.playersTargeted[4])
            {//HighestRanked
                for (int i = 0; i < playerList.Count; i++)
                {
                    if (i == 0)
                    {
                        p.Add(playerList[i]);
                    }
                    if (i != 0 && Player.rankDictionary[playerList[i].rank] > Player.rankDictionary[p[0].rank])
                    {
                        p.Clear();
                        p.Add(playerList[i]);
                    }
                    else if (i != 0 && playerList[i].rank == p[0].rank)
                    {
                        p.Add(playerList[i]);
                    }
                }
            }
            else if (whosAffected == Event.playersTargeted[5])
            {//LowestRanked
                for (int i = 0; i < playerList.Count; i++)
                {
                    if (i == 0)
                    {
                        p.Add(playerList[i]);
                    }
                    if (i != 0 && Player.rankDictionary[playerList[i].rank] < Player.rankDictionary[p[0].rank])
                    {
                        p.Clear();
                        p.Add(playerList[i]);
                    }
                    else if (i != 0 && playerList[i].rank == p[0].rank)
                    {
                        p.Add(playerList[i]);
                    }
                }
            }
            else if (whosAffected == Event.playersTargeted[6])
            {//Next
                QuestBonusShields += 2;
            }
            return p;
        }

        public void isTournament(Tournament storyCard, List<Player> tie = null)
        {
            string tournamentName = storyCard.GetName();
            int bonusShields = storyCard.GetShieldModifier();
            List<Pair<Player, int>> tournamentEntry = new List<Pair<Player, int>>();
            if (tie == null)
            {
                Console.WriteLine("Tournament starting " + tournamentName + "!!! Players that wish to enter, type y, other for no\n");
                int tempPlayerTurn = playerTurn;
                for (int i = 0; i < numPlayers; i++)
                { //seeing which player wants to play
                    if (tempPlayerTurn > numPlayers)
                    {
                        tempPlayerTurn = 1;
                    }

                    //Testing if the file has reached the end, if yes stop testiing.
                    if (testingMode == true)
                    {
                        if (tornamentTestScript.Length == ++fileCounter)//TESTING
                        {
                            Console.WriteLine("\n < -----------------File Read Completed ----------------->");
                            Console.ReadLine();
                        }
                        else
                        {//TESTING
                            fileCounter--;
                        }
                    }

                    string userInput;
                    if (testingMode == true)
                    {

                        userInput = tornamentTestScript[++fileCounter];//TESTING
                        Console.WriteLine("USER ENTERD -> " + tornamentTestScript[fileCounter]);//TESTING
                    }
                    else
                    {
                        userInput = Console.ReadLine();
                    }
                    Console.Write(playerList[tempPlayerTurn - 1].getName() + "?: ");
                    //Console.WriteLine((tempPlayerTurn - 1) + "|" + (playerTurn - 1));
                    //Console.ReadLine();
                    if (userInput == "y" || userInput == "Y")
                    {
                        tournamentEntry.Add(new Pair<Player, int>(playerList[tempPlayerTurn - 1], 0));
                    }
                    tempPlayerTurn++;
                }
                //playerTurn++;
            }
            else
            {
                Console.WriteLine("There's been a tie! Players will face off to decide the champion!");
                for (int i = 0; i < tie.Count; i++)
                {
                    Console.WriteLine(tie[i].getName() + " will be participating");
                    tournamentEntry.Add(new Pair<Player, int>(tie[i], 0));
                }
            }
            if (tournamentEntry.Count == 0)
            {
                Console.WriteLine("No one entered the tournament");
                return;
            }
            if (tournamentEntry.Count == 1)
            {
                Console.WriteLine("Congrats " + tournamentEntry[0].Item1.getName() + " you have won the tournament " + tournamentName + " and recieved " + (bonusShields + 1) + " shields!");
                tournamentEntry[0].Item1.shields += bonusShields + 1;//cause only one player entered
                return;
            }
            for (int x = 0; x < tournamentEntry.Count; x++)
            { //FOR TESTING ONLY
                Console.WriteLine(tournamentEntry[x].Item1.getName() + " | " + tournamentEntry[x].Item1.cardsInHand.Count);
            }

            for (int i = 0; i < tournamentEntry.Count; i++)
            { //all players that wish to play get an additional adventure card. Assuming this is true for tiebreakes as well
                tournamentEntry[i].Item1.cardsInHand.Add((Adventure)draw("Adventure"));
            }

            for (int x = 0; x < tournamentEntry.Count; x++)
            { //FOR TESTING ONLY
                Console.WriteLine(tournamentEntry[x].Item1.getName() + " | " + tournamentEntry[x].Item1.cardsInHand.Count);
            }

            //Console.ReadLine();

            bonusShields += tournamentEntry.Count;
            for (int i = 0; i < tournamentEntry.Count; i++)
            { //showing player the cards they have, making them choose which cards they want to play
                Player player = tournamentEntry[i].Item1;
                for (int y = 0; y < player.cardsInHand.Count; y++)
                {
                    if (y == 0 && player.cardsInHand.Count > 12)
                        Console.WriteLine("Alert, " + player.getName() + " you have more than 12 cards. Discard to (at least) 12 or be auto discarded.");
                    Console.WriteLine("Card #" + (y + 1) + ": " + player.cardsInHand[y].GetName() + " with " + player.cardsInHand[y].GetBattlePoints() + " battle points.");
                }
                Console.WriteLine("Choose the cards you want to use for the tournament by card#, seperated by commas. Invalid cards will be ignored.");
                string cardsChosen;
                //string userInput;
                if (testingMode == true)
                {


                    cardsChosen = tornamentTestScript[++fileCounter];//TESTING
                    Console.WriteLine("USER ENTERD -> " + tornamentTestScript[fileCounter]);//TESTING
                }
                else
                {
                    cardsChosen = Console.ReadLine();
                }

                string[] values = cardsChosen.Split(',');
                tournamentEntry[i].Item2 = validCards(values, player);
            }
            List<Player> winner = roundWinner(tournamentEntry);
            if (winner.Count > 1 && tie == null)
            {
                isTournament(storyCard, winner); //careful not to enter this loop twice, or it's inifinite loop time baby! At most one recursive call should happen
            }
            else if (winner.Count > 1 && tie != null)
            {
                Console.WriteLine("Tie breaker can't be decided, everyone gets shields!");
                for (int i = 0; i < winner.Count; i++)
                {
                    winner[i].shields += bonusShields;
                }
            }
            else
            {
                Console.WriteLine("Congrats " + winner[0].getName() + " you have won the tournament " + tournamentName + " and recieved " + bonusShields + " shields!");
                winner[0].shields += bonusShields;
            }
            stripCards(); //if player has more than 12 cards, time to get rid of them
        }

        public void stripCards()
        {
            for (int i = 0; i < playerList.Count; i++)
            {
                Player p = playerList[i];
                while (p.cardsInHand.Count > Player.MAX_CARDS_HAND)
                {
                    p.discardPile.Add(p.cardsInHand[p.cardsInHand.Count - 1]);
                    p.cardsInHand.RemoveAt(p.cardsInHand.Count - 1);
                }
            }
        }

        public List<Player> roundWinner(List<Pair<Player, int>> competitors)
        { //Return a winner, need to account for a draw
            List<Player> winner = new List<Player>(); //this should always return at least one person
            int winnerBP = 0;
            for (int i = 0; i < competitors.Count; i++)
            {
                if (i == 0)
                {
                    winner.Add(competitors[i].Item1);
                    winnerBP = competitors[i].Item2;
                }
                if (i != 0 && competitors[i].Item2 > winnerBP)
                {
                    winner.Clear();
                    winner.Add(competitors[i].Item1);
                    winnerBP = competitors[i].Item2;
                }
                else if (i != 0 && competitors[i].Item2 == winnerBP)
                {
                    winner.Add(competitors[i].Item1);
                }
            }
            return winner;
        }

        public int validCards(string[] values, Player p)
        {//Should be usable for tournament and quest
            List<int> validatedCards = new List<int>();
            int battlePoints = 0;

            for (int i = 0; i < p.alliesInPlay.Count; i++)
            { //doing it up here so we don't double count ally battle points, as they are added into this array down below
                battlePoints += p.alliesInPlay[i].GetBattlePoints();
            }

            Console.WriteLine("WTF:" + values.Length);
            for (int i = 0; i < values.Length; i++)
            { //Making sure values that aren't acceptable ints are stripped
                int temp = -1;
                bool tempBool = int.TryParse(values[i], out temp);
                if (tempBool && temp > 0 && temp <= p.cardsInHand.Count)
                {
                    validatedCards.Add(temp);
                }
            }
            Console.WriteLine("WTF#2 :" + validatedCards.Count);
            bool amourPlayer = false;
            validatedCards.Sort(); //don't want to remove from the middle and mess up indexes
            for (int i = validatedCards.Count - 1; i >= 0; i--)
            { //No two weapons of the same type can be played, no foe cards, only one Amour
                if (p.cardsInHand[validatedCards[i] - 1] is Weapon)
                {
                    for (int y = i - 1; y >= 0; y--)
                    {
                        Console.WriteLine(i + "|" + y);
                        Console.WriteLine((p.cardsInHand[validatedCards[i] - 1]) + "|" + (p.cardsInHand[validatedCards[y] - 1]));
                        if (p.cardsInHand[validatedCards[i] - 1] is Weapon && p.cardsInHand[validatedCards[y] - 1] is Weapon && ((Weapon)(p.cardsInHand[validatedCards[i] - 1])) == ((Weapon)(p.cardsInHand[validatedCards[y] - 1])))
                        {
                            validatedCards.RemoveAt(y); //unintended bonus of removing duplicate of the same weapon (excact same object) added twice
                            i = validatedCards.Count - 1; //if we remove from the end here and i doesn't decrement, we are in trouble
                            Console.WriteLine("WTF#2 :" + validatedCards.Count);
                        }
                    }
                }
                else if (p.cardsInHand[validatedCards[i] - 1] is Amour && !amourPlayer)
                {
                    amourPlayer = true;
                }
                else if (p.cardsInHand[validatedCards[i] - 1] is Amour && amourPlayer)
                {
                    validatedCards.RemoveAt(i);
                }
                else if (p.cardsInHand[validatedCards[i] - 1] is Foe)
                {
                    validatedCards.RemoveAt(i);
                }
                else if (p.cardsInHand[validatedCards[i] - 1] is Test)
                {
                    validatedCards.RemoveAt(i);
                }
                else if (p.cardsInHand[validatedCards[i] - 1] is Ally)
                {//allys stay on after
                    p.alliesInPlay.Add((Ally)p.cardsInHand[validatedCards[i] - 1]);
                }
            }//end main for
             //So now all the indexes specified are valid inside of validated cards
            for (int i = validatedCards.Count - 1; i >= 0; i--)
            {
                battlePoints += p.cardsInHand[validatedCards[i] - 1].GetBattlePoints(); //because a player isn't going to see card #'s starting at 0, rather they start at 1
                Console.WriteLine(p.getName() + " played " + p.cardsInHand[validatedCards[i] - 1].GetName());
                p.discardPile.Add(p.cardsInHand[validatedCards[i] - 1]);
                p.cardsInHand.RemoveAt(validatedCards[i] - 1);
            }
            battlePoints += Player.rankDictionary[p.rank];
            Console.WriteLine("Total battle points for player not including rank " + battlePoints);
            battlePoints += Player.rankDictionary[p.rank];
            Console.WriteLine("Total battle points for player including rank " + battlePoints + "\n");
            //Console.WriteLine("Ranks so Far are : ");
            //for (int i = 0; i < playerList.Count; i++)
            //{ //printing current rank of each player.
            //    Console.WriteLine(playerList[i].getName() + ": " + playerList[i].rank + " with " + playerList[i].shields + " sheilds.");
            //}
            return battlePoints;
        }


        /* INITIALIZATION FUNCTIONS */
        public void initializePlayers()
        {
            Console.WriteLine("Please enter the number of players that wish to play:");
            //string userInput = Console.ReadLine();

            string userInput;
            if (testingMode == true)
            {//TESTING

                userInput = tornamentTestScript[++fileCounter];//TESTING
                Console.WriteLine("USER ENTERD -> " + tornamentTestScript[fileCounter]);//TESTING
            }
            else
            {
                userInput = Console.ReadLine();
            }

            if (!(int.TryParse(userInput, out numPlayers) && numPlayers > 1 && numPlayers < 5))
            {
                Console.WriteLine("Invalid input, defaulting to four players");
                numPlayers = 4;
            }

            for (int i = 0; i < numPlayers; i++)
            {
                Console.WriteLine("Player" + (i + 1) + " please enter your name:");
                //userInput = Console.ReadLine();

                if (testingMode == true)
                {

                    userInput = tornamentTestScript[++fileCounter];//TESTING
                    Console.WriteLine("USER ENTERD -> " + tornamentTestScript[fileCounter]);//TESTING
                }
                else
                {
                    userInput = Console.ReadLine();
                }
                if (string.IsNullOrEmpty(userInput) || string.IsNullOrWhiteSpace(userInput))
                {
                    Console.WriteLine("Player" + (i + 1) + " name set to 'Idiot who can't follow instructions #" + (i + 1) + "'");
                    string name = "Idiot who can't follow instructions #" + i;
                    playerList.Add(new Player(name));
                }
                else
                {
                    Console.WriteLine("Player" + (i + 1) + " name set to " + userInput);
                    playerList.Add(new Player(userInput));
                }
            }
        }

        /* CARD OPERATION FUNCTIONS */
        public void deal()
        {
            for (int i = 0; i < 12; i++)
            {
                for (int y = 0; y < playerList.Count; y++)
                {
                    playerList[y].cardsInHand.Add(adventureDeck[adventureDeck.Count - 1]);
                    adventureDeck.RemoveAt(adventureDeck.Count - 1);
                }
            }
        }

        public void shuffle(string whichDeck)
        {
            Random rand = new Random();
            switch (whichDeck)
            {
                case "storyDeck":
                    for (int y = 0; y < 3; y++)
                    {
                        for (int i = 0; i < storyDeck.Count; i++)
                        {
                            //We should only ever shuffle a full story deck, for right now that's only at the start
                            //Will have to deal with situation: All story cards are used, but no one has won
                            int tempInt = rand.Next(0, storyDeck.Count);
                            Story swapCard = storyDeck[tempInt];
                            storyDeck[tempInt] = storyDeck[i];
                            storyDeck[i] = swapCard;
                        }
                    }
                    break;
                default:
                    for (int y = 0; y < 3; y++)
                    {
                        for (int i = 0; i < adventureDeck.Count; i++)
                        {
                            //Shuffling at the start is a full deck, but it could be a different number next time
                            int tempInt = rand.Next(0, adventureDeck.Count);
                            Adventure swapCard = adventureDeck[tempInt];
                            adventureDeck[tempInt] = adventureDeck[i];
                            adventureDeck[i] = swapCard;
                        }
                    }
                    break;
            }
        }

        public Card draw(string whichDeck, int numCards = 1)
        {
            if (storyDeck.Count == 0)
            {
                storyDeck = discardStoryDeck;
                shuffle("storyDeck");
                discardStoryDeck = new List<Story>();
            }
            if (adventureDeck.Count == 0)
            {
                //if we get here, need a case
            }
            switch (whichDeck)
            {
                case "Story":
                    Console.WriteLine(storyDeck[storyDeck.Count - 1].GetName());
                    discardStoryDeck.Add(storyDeck[storyDeck.Count - 1]);
                    storyDeck.RemoveAt(storyDeck.Count - 1);
                    return discardStoryDeck[discardStoryDeck.Count - 1];
                default://For Adventure card drawing
                    Console.WriteLine(adventureDeck[adventureDeck.Count - 1].GetName());
                    discardAdventureDeck.Add(adventureDeck[adventureDeck.Count - 1]);
                    adventureDeck.RemoveAt(adventureDeck.Count - 1);
                    return discardAdventureDeck[discardAdventureDeck.Count - 1];
            }
        }
        /* CREATE DECK AND CARD FUNCTIONS */

        public void createDeck()
        {
            #region
            int cardCounter = 0;

            //ADVENTURE DECK

            /* AMOUR */

            for (int i = 0; i < 8; i++)
            {
                adventureDeck.Add(createAdventureCard("Amour"));
                cardCounter++;
            }

            /* WEAPON */

            for (int i = cardCounter; i < 10; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Excalibur", 30));
                cardCounter++;
            }

            for (int i = cardCounter; i < 16; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Lance", 20));
                cardCounter++;
            }

            for (int i = cardCounter; i < 24; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Battle-ax", 15));
                cardCounter++;
            }

            for (int i = cardCounter; i < 40; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Sword", 10));
                cardCounter++;
            }

            for (int i = cardCounter; i < 51; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Horse", 10));
                cardCounter++;
            }

            for (int i = cardCounter; i < 57; i++)
            {
                adventureDeck.Add(createAdventureCard("Weapon", "Dagger", 5));
                cardCounter++;
            }

            /* FOE */
            for (int i = cardCounter; i < 58; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Dragon", 50));
                cardCounter++;
            }

            for (int i = cardCounter; i < 60; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Giant", 40));
                cardCounter++;
            }

            for (int i = cardCounter; i < 64; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Mordred", 30));
                cardCounter++;
            }

            for (int i = cardCounter; i < 66; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Green Knight", 25));
                cardCounter++;
            }

            for (int i = cardCounter; i < 69; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Black Knight", 25));
                cardCounter++;
            }

            for (int i = cardCounter; i < 75; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Evil Knight", 20));
                cardCounter++;
            }

            for (int i = cardCounter; i < 83; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Saxon Knight", 15));
                cardCounter++;
            }

            for (int i = cardCounter; i < 90; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Robber Knight", 15));
                cardCounter++;
            }

            for (int i = cardCounter; i < 95; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Saxons", 10));
                cardCounter++;
            }

            for (int i = cardCounter; i < 99; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Boar", 5));
                cardCounter++;
            }

            for (int i = cardCounter; i < 107; i++)
            {
                adventureDeck.Add(createAdventureCard("Foe", "Thieves", 5));
                cardCounter++;
            }

            /*TEST*/
            adventureDeck.Add(createAdventureCard("Test", "Test of Valor", 0));
            adventureDeck.Add(createAdventureCard("Test", "Test of Valor", 0));

            adventureDeck.Add(createAdventureCard("Test", "Test of Temptation", 0));
            adventureDeck.Add(createAdventureCard("Test", "Test of Temptation", 0));

            adventureDeck.Add(createAdventureCard("Test", "Test of Questing Beast", 0));
            adventureDeck.Add(createAdventureCard("Test", "Test of Questing Beast", 0));

            adventureDeck.Add(createAdventureCard("Test", "Test of Morgan Le Fey", 3));
            adventureDeck.Add(createAdventureCard("Test", "Test of Morgan Le Fey", 3));


            /*Allies*/
            adventureDeck.Add(createAdventureCard("Ally", "Sir Galahad", 15, 0));
            adventureDeck.Add(createAdventureCard("Ally", "Sir Gawain", 10, 0));
            adventureDeck.Add(createAdventureCard("Ally", "King Pellinore", 10, 0));
            adventureDeck.Add(createAdventureCard("Ally", "Sir Percival", 5, 0));
            adventureDeck.Add(createAdventureCard("Ally", "Sir Tristan", 10, 0));
            adventureDeck.Add(createAdventureCard("Ally", "King Arthur", 10, 2));
            adventureDeck.Add(createAdventureCard("Ally", "Queen Guinevere", 0, 3));
            adventureDeck.Add(createAdventureCard("Ally", "Merlin", 0, 0));
            adventureDeck.Add(createAdventureCard("Ally", "Queen Iseult", 0, 2));
            adventureDeck.Add(createAdventureCard("Ally", "Sir Lancelot", 15, 0));


            //STORY DECK
            cardCounter = 0;

            /*EVENT*/
            storyDeck.Add(createEventCard("Chivalrous Deed", 3, 0, false, 0, 0, Event.playersTargeted[0], "Player(s) with both lowest rank and least amount of shields, receives 3 shields"));
            storyDeck.Add(createEventCard("Pox", -1, 0, false, 0, 0, Event.playersTargeted[2], "All players except the player drawing this card lose 1 shield"));
            storyDeck.Add(createEventCard("Plague", -2, 0, false, 0, 0, Event.playersTargeted[3], "Drawer loses two shields if possible"));
            storyDeck.Add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted[6], "The next player(s) to complete a Quest will receive 2 extra shields"));
            storyDeck.Add(createEventCard("King's Recognition", 2, 0, false, 0, 0, Event.playersTargeted[6], "The next player(s) to complete a Quest will receive 2 extra shields"));
            storyDeck.Add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted[5], "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
            storyDeck.Add(createEventCard("Queen's Favor", 0, 2, false, 0, 0, Event.playersTargeted[5], "The lowest ranked player(s) immediately receives 2 Adventure Cards"));
            storyDeck.Add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted[1], "All Allies in play must be discarded"));
            storyDeck.Add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0, Event.playersTargeted[1], "All Allies in play must be discarded"));
            storyDeck.Add(createEventCard("King's Call To Arms", 0, 0, false, 1, 2, Event.playersTargeted[4], "The highest ranked player(s) must place 1 weapon in the discard pile. If unable to do so, 2 Foe Cards must be discarded"));
            storyDeck.Add(createEventCard("Prosperity Throughout the Realm", 0, 2, false, 0, 0, Event.playersTargeted[1], "All players may immeadiately draw two Adventure Cards"));

            /*Tournament*/
            storyDeck.Add(createTournamentCard("AT YORK", 0));
            storyDeck.Add(createTournamentCard("AT TINTAGEL", 1));
            storyDeck.Add(createTournamentCard("AT ORKNEY", 2));
            storyDeck.Add(createTournamentCard("AT CAMELOT", 3));

            /*Quest*/
            /*storyDeck.Add(createQuestCard("Journey through the Enchanted Forest", 3, "Evil Knight"));
            storyDeck.Add(createQuestCard("Vanquish King Arthur's Enemies", 3, "none"));
            storyDeck.Add(createQuestCard("Vanquish King Arthur's Enemies", 3, "none"));
            storyDeck.Add(createQuestCard("Repel the Saxon Raiders", 2, "All Saxons"));
            storyDeck.Add(createQuestCard("Repel the Saxon Raiders", 2, "All Saxons"));
            storyDeck.Add(createQuestCard("Boar Hunt", 2, "Boar"));
            storyDeck.Add(createQuestCard("Boar Hunt", 2, "Boar"));
            storyDeck.Add(createQuestCard("Search for the Questing Beast", 4, "none", "King Pellinore"));
            storyDeck.Add(createQuestCard("Defend the Queen's Honor", 4, "All", "Sir Lancelot"));
            storyDeck.Add(createQuestCard("Slay the Dragon", 3, "Dragon"));
            storyDeck.Add(createQuestCard("Rescue the Fair Maiden", 3, "Black Knight"));
            storyDeck.Add(createQuestCard("Search for the Holy Grail", 5, "All", "Sir Percival"));
            storyDeck.Add(createQuestCard("Search for the Holy Grail", 4, "Green Knight", "Sir Gawain"));*/

            #endregion
        }

        public Story createEventCard(string name, int shieldModifier, int adventureCardModifier, bool eliminiateAllies, int weaponCardModifier,
            int foeCardModifier, string whosAffected, string description)
        {
            Event eve = new Event(name, shieldModifier, adventureCardModifier, eliminiateAllies, weaponCardModifier, foeCardModifier, whosAffected, description);
            return eve;
        }

        public Story createTournamentCard(string name, int shieldModifier)
        {
            Tournament tournament = new Tournament(name, shieldModifier);
            return tournament;
        }

        public Story createQuestCard(string name, int numStages, string linkedFoe, string linkedAlly = "none")
        {
            Quest quest = new Quest(name, numStages, linkedFoe, linkedAlly);
            return quest;
        }

        public Adventure createAdventureCard(string type, string name = "", int battlePoints = 0, int bids = 0)
        {
            switch (type)
            {
                case "Foe":
                    Foe foe = new Foe(name, battlePoints);
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
                    Console.WriteLine("Deck creation err, abort");
                    Foe apocalypse = new Foe("Critical Err", 9001);
                    return apocalypse;
            }
        }
    }
}
