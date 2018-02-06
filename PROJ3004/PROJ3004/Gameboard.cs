using System;
using System.Collections;
using System.Collections.Generic;

namespace PROJ3004
{
    public class Gameboard
    {
		public List<Adventure> adventureDeck  = new List<Adventure>();//125
		public List<Adventure> discardAdventureDeck = new List<Adventure> ();

		public List<Story> storyDeck = new List<Story>();//28
		public List<Story> discardStoryDeck = new List<Story> ();

		public List<Player> playerList = new List<Player> ();
		public int numPlayers = 0;
		public bool winner = false;
		public int playerTurn = 1;

        public static void Main(string[] args) //Move all creation to a seperate function, only keep main control loop in here moving forward!
        {
			Gameboard play = new Gameboard ();

			play.createDeck ();	
			play.shuffle ("adventureDeck");
			play.shuffle ("storyDeck");

			play.initializePlayers (); //make the players
			play.deal (); //Giving everyone 12 adventure cards

			for (int i = 0; i < play.playerList.Count; i++) {//FOR TESTING ONLY, trying to figure wtf is going on
				Player p = play. playerList[i];
				Console.WriteLine (p.getName());
				Console.WriteLine ("-----------------");
				for (int x = 0; x < p.cardsInHand.Count; x++) {
					Console.WriteLine (p.cardsInHand[x].GetName());
				}
				Console.WriteLine ("-------------");
			}

			play.runGame ();


        }

		/*Control Loop */ 
		public void runGame(){
			while (!winner) {
				//First thing that needs to be done, P1 has to draw a story card
				Story storyCard = (Story) draw("Story");
				if (storyCard is Quest) {

				} else if (storyCard is Tournament) {
					isTournament ((Tournament)storyCard);
					for (int x = 0; x < playerList.Count; x++) {//FOR TESTING ONLY, making sure the right person got the number of shields
						Console.WriteLine (playerList[x].shields);
						Console.WriteLine (playerList[x].cardsInHand.Count);
					}
					whosTurn (); //This is still very wrong
				} else if (storyCard is Event) {

				} else {
					Console.WriteLine ("Critical error 9001 in card type, this should never happen");
				}
					
			}
		}

		public int whosTurn(){
			if (playerTurn > numPlayers) {
				playerTurn = 1;
			}
			return playerTurn++;
		}

		public void isTournament(Tournament storyCard){
			string tournamentName = storyCard.GetName ();
			int bonusShields = storyCard.GetShieldModifier();
			Console.WriteLine ("Tournament starting " + tournamentName + "!!! Players that wish to enter, type y, other for no\n");
			List <Pair<Player, int>> tournamentEntry = new List <Pair<Player, int>> ();
			int tempPlayerTurn = playerTurn;
			for (int i = 0; i < numPlayers; i++) { //seeing which player wants to play
				Console.Write (playerList[tempPlayerTurn-1].getName() + "?: ");
				string userInput = Console.ReadLine ();
				Console.WriteLine ((tempPlayerTurn - 1) + "|" + (playerTurn - 1));
				Console.ReadLine ();
				if (userInput == "y" || userInput == "Y") {
					tournamentEntry.Add (new Pair<Player, int>(playerList [tempPlayerTurn - 1],0));
				}
				tempPlayerTurn++;
			}
			playerTurn++; 
			for (int x = 0; x < tournamentEntry.Count; x++) { //FOR TESTING ONLY
				Console.WriteLine (tournamentEntry[x].Item1.getName() + " | " + tournamentEntry[x].Item1.cardsInHand.Count);
			}

			for (int i = 0; i < tournamentEntry.Count; i++) { //all players that wish to play get an additional adventure card
				tournamentEntry [i].Item1.cardsInHand.Add ((Adventure)draw ("Adventure"));
			}

			for (int x = 0; x < tournamentEntry.Count; x++) { //FOR TESTING ONLY
				Console.WriteLine (tournamentEntry[x].Item1.getName() + " | " + tournamentEntry[x].Item1.cardsInHand.Count);
			}

			Console.ReadLine ();

			bonusShields += tournamentEntry.Count;
			for (int i = 0; i < tournamentEntry.Count; i++) { //showing player the cards they have, making them choose which cards they want to play
				Player player = tournamentEntry[i].Item1;
				for (int y = 0; y < player.cardsInHand.Count; y++) {
					if(y == 0 && player.cardsInHand.Count > 12) 
						Console.WriteLine ("Alert, " + player.getName() + " you have more than 12 cards. Discard to (at least) 12 or be auto discarded.");
					Console.WriteLine ("Card #" + (y+1) + ": " + player.cardsInHand[y].GetName() + " with " + player.cardsInHand[y].GetBattlePoints() + " battle points.");
				}
				Console.WriteLine ("Choose the cards you want to use for the tournament by card#, seperated by commas. Invalid cards will be ignored.");
				string cardsChosen = Console.ReadLine ();
				string[] values = cardsChosen.Split (',');
				tournamentEntry[i].Item2 = validCards (values, player);
			}
			Player winner = roundWinner (tournamentEntry);
			Console.WriteLine ("Congrats " + winner.getName() + " you have won the tournament " + tournamentName + " and recieved " + bonusShields + " shields!" );
			winner.shields += bonusShields;
		}

		public Player roundWinner(List<Pair<Player, int>> competitors){
			Player winner = null; //potentially unsafe
			int winnerBP = 0;
			for (int i = 0; i < competitors.Count; i++) {
				if (i == 0) {
					winner = competitors [i].Item1;
					winnerBP = competitors [i].Item2;
				}
				if (competitors [i].Item2 > winnerBP) {
					winnerBP = competitors [i].Item2;
					winner = competitors [i].Item1;
				}
			}
			return winner;
		}

		public int validCards(string[] values, Player p){//Should be usable for tournament and quest
			List<int> validatedCards = new List<int> ();
			int battlePoints = 0;
			Console.WriteLine ("WTF:" + values.Length);
			for (int i = 0; i < values.Length; i++) { //Making sure values that aren't acceptable ints are stripped
				int temp = -1; 
				bool tempBool = int.TryParse (values [i], out temp);
				if (tempBool && temp > 0 && temp <= p.cardsInHand.Count) {
					validatedCards.Add (temp);
				}
			}
			Console.WriteLine ("WTF#2 :" + validatedCards.Count);
			bool amourPlayer = false;
			validatedCards.Sort(); //don't want to remove from the middle and mess up indexes
			for (int i = validatedCards.Count - 1; i >= 0; i--) { //No two weapons of the same type can be played, no foe cards, only one Amour
				if (p.cardsInHand [validatedCards [i] - 1] is Weapon) {
					for (int y = i - 1; y >= 0; y--) {
						Console.WriteLine (i + "|" + y);
						Console.WriteLine ((p.cardsInHand [validatedCards [i] - 1]) + "|" + (p.cardsInHand [validatedCards [y] - 1]));
						if (p.cardsInHand [validatedCards [y] - 1] is Weapon && ((Weapon)(p.cardsInHand [validatedCards [i] - 1])) == ((Weapon)(p.cardsInHand [validatedCards [y] - 1]))) {
							validatedCards.RemoveAt (y); //unintended bonus of removing duplicate of the same weapon (excact same object) added twice
							i = validatedCards.Count - 1; //if we remove from the end here and i doesn't decrement, we are in trouble
							Console.WriteLine ("WTF#2 :" + validatedCards.Count);
						}
					}
				} else if (p.cardsInHand [validatedCards [i] - 1] is Amour && !amourPlayer) {
					amourPlayer = true;
				} else if (p.cardsInHand [validatedCards [i] - 1] is Amour && amourPlayer) {
					validatedCards.RemoveAt (i);
				} else if (p.cardsInHand [validatedCards [i] - 1] is Foe) {
					validatedCards.RemoveAt (i);
				} else if (p.cardsInHand [validatedCards [i] - 1] is Test) {
					validatedCards.RemoveAt (i);
				}
			}//end main for
			//So now all the indexes specified are valid inside of validated cards
			for (int i = validatedCards.Count - 1; i >= 0; i--) {
				battlePoints += p.cardsInHand [validatedCards [i] - 1].GetBattlePoints (); //because a player isn't going to see card #'s starting at 0, rather they start at 1
				Console.WriteLine (p.getName() + " played " + p.cardsInHand[validatedCards [i] - 1].GetName());
				p.cardsInHand.RemoveAt(validatedCards[i] - 1);
			}
			battlePoints += Player.rankDictionary[p.rank];
			Console.WriteLine ("Total battle points for player including rank " + battlePoints);
			return battlePoints;
		}


		/* INITIALIZATION FUNCTIONS */
		public void initializePlayers(){
			Console.WriteLine ("Please enter the number of players that wish to play:");
			string userInput = Console.ReadLine ();
			if (!(int.TryParse (userInput, out numPlayers) && numPlayers > 1 && numPlayers < 5)) {
				Console.WriteLine ("Invalid input, defaulting to four players");
				numPlayers = 4;
			}

			for (int i = 0; i < numPlayers; i++) {
				Console.WriteLine ("Player" + (i+1) + " please enter your name:");
				userInput = Console.ReadLine ();
				if (string.IsNullOrEmpty (userInput) || string.IsNullOrWhiteSpace (userInput)) {
					Console.WriteLine ("Player" + (i + 1) + " name set to 'Idiot who can't follow instructions #" + (i + 1) + "'");
					string name = "Idiot who can't follow instructions #" + i;
					playerList.Add (new Player (name));
				} else {
					Console.WriteLine ("Player" + (i + 1) +  " name set to " + userInput);
					playerList.Add (new Player (userInput));
				}
			}
		}
			
	    /* CARD OPERATION FUNCTIONS */
		public void deal(){
			for (int i = 0; i < 12; i++) {
				for (int y = 0; y < playerList.Count; y++) {
					playerList [y].cardsInHand.Add(adventureDeck[adventureDeck.Count-1]);
					adventureDeck.RemoveAt(adventureDeck.Count - 1);
				}
			}
		}

		public void shuffle(string whichDeck){
			Random rand = new Random ();
			switch (whichDeck) {
			case "storyDeck":
				for (int y = 0; y < 3; y++) {
					for (int i = 0; i < storyDeck.Count; i++) {
						//We should only ever shuffle a full story deck, for right now that's only at the start
						//Will have to deal with situation: All story cards are used, but no one has won
						int tempInt = rand.Next (0, storyDeck.Count); 
						Story swapCard = storyDeck [tempInt];
						storyDeck [tempInt] = storyDeck [i];
						storyDeck [i] = swapCard;
					}
				}
				break;
			default:
				for (int y = 0; y < 3; y++) {
					for (int i = 0; i < adventureDeck.Count; i++) {
						//Shuffling at the start is a full deck, but it could be a different number next time
						int tempInt = rand.Next(0, adventureDeck.Count);
						Adventure swapCard = adventureDeck [tempInt];
						adventureDeck [tempInt] = adventureDeck [i];
						adventureDeck [i] = swapCard;
					}
				}
				break;
			}
		}

		public Card draw(string whichDeck, int numCards = 1){
			switch (whichDeck) {
			case "Story":
				Console.WriteLine (storyDeck [storyDeck.Count - 1].GetName ());
				discardStoryDeck.Add (storyDeck [storyDeck.Count - 1]);
				storyDeck.RemoveAt (storyDeck.Count - 1);
				return discardStoryDeck [discardStoryDeck.Count - 1];
			default://For Adventure card drawing
				discardAdventureDeck.Add (adventureDeck [adventureDeck.Count - 1]);
				adventureDeck.RemoveAt (adventureDeck.Count - 1);
				return discardAdventureDeck [discardAdventureDeck.Count - 1];
			} 
		}
		/* CREATE DECK AND CARD FUNCTIONS */

		public void createDeck(){
			#region
			int cardCounter = 0;

			//ADVENTURE DECK

			/* AMOUR */

			for (int i = 0; i < 8; i++) {
				adventureDeck.Add(createAdventureCard ("Amour"));
				cardCounter++;
			}

			/* WEAPON */

			for (int i = cardCounter; i < 10; i++) {
				adventureDeck.Add(createAdventureCard("Weapon", "Excalibur", 30));
				cardCounter++;
			}

			for (int i = cardCounter; i < 16; i++) {
				adventureDeck.Add(createAdventureCard("Weapon", "Lance", 20));
				cardCounter++;
			}

			for (int i = cardCounter; i < 24; i++) { 
				adventureDeck.Add(createAdventureCard ("Weapon", "Battle-ax", 15));
				cardCounter++;
			}

			for (int i = cardCounter; i < 40; i++) { 
				adventureDeck.Add(createAdventureCard ("Weapon", "Sword", 10));
				cardCounter++;
			}

			for (int i = cardCounter; i < 51; i++) { 
				adventureDeck.Add(createAdventureCard ("Weapon", "Horse", 10));
				cardCounter++;
			}

			for (int i = cardCounter; i < 57; i++) { 
				adventureDeck.Add(createAdventureCard ("Weapon", "Dagger", 5));
				cardCounter++;
			}

			/* FOE */
			for(int i = cardCounter; i < 58; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Dragon", 50));
				cardCounter++;
			}

			for(int i = cardCounter; i < 60; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Giant", 40));
				cardCounter++;
			}

			for(int i = cardCounter; i < 64; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Mordred", 30));
				cardCounter++;
			}

			for(int i = cardCounter; i < 66; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Green Knight", 25));
				cardCounter++;
			}

			for(int i = cardCounter; i < 69; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Black Knight", 25));
				cardCounter++;
			}

			for(int i = cardCounter; i < 75; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Evil Knight", 20));
				cardCounter++;
			}

			for(int i = cardCounter; i < 83; i++){
						adventureDeck.Add(createAdventureCard ("Foe", "Saxon Knight", 15));
				cardCounter++;
			}

			for(int i = cardCounter; i < 90; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Robber Knight", 15));
				cardCounter++;
			}

			for(int i = cardCounter; i < 95; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Saxons", 10));
				cardCounter++;
			}

			for(int i = cardCounter; i < 99; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Boar", 5));
				cardCounter++;
			}

			for(int i = cardCounter; i < 107; i++){
				adventureDeck.Add(createAdventureCard ("Foe", "Thieves", 5));
				cardCounter++;
			}
			
			/*TEST*/
			adventureDeck.Add(createAdventureCard ("Test", "Test of Valor", 0));
			adventureDeck.Add(createAdventureCard ("Test", "Test of Valor", 0));

			adventureDeck.Add(createAdventureCard ("Test","Test of Temptation", 0));
			adventureDeck.Add(createAdventureCard ("Test","Test of Temptation", 0));

			adventureDeck.Add(createAdventureCard ("Test", "Test of Questing Best", 0)); 
			adventureDeck.Add(createAdventureCard ("Test", "Test of Questing Best", 0));

			adventureDeck.Add(createAdventureCard ("Test", "Test of Morgan Le Fey", 3));
			adventureDeck.Add(createAdventureCard ("Test", "Test of Morgan Le Fey", 3));


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
			/*storyDeck.Add(createEventCard("Chivalrous Deed", 3, 0, false, 0, 0));
			storyDeck.Add(createEventCard("Pox", -1, 0, false, 0, 0));
			storyDeck.Add(createEventCard("Plague", -2, 0, false, 0, 0));
			storyDeck.Add(createEventCard("King's Recognition", 2, 0, false, 0, 0));
			storyDeck.Add(createEventCard("King's Recognition", 2, 0, false, 0, 0));
			storyDeck.Add(createEventCard("Queen's Favor", 0, 2, false, 0, 0));
			storyDeck.Add(createEventCard("Queen's Favor", 0, 2, false, 0, 0));
			storyDeck.Add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0));
			storyDeck.Add(createEventCard("Court Called to Camelot", 0, 0, true, 0, 0));
			storyDeck.Add(createEventCard("King's Call To Arms", 0, 0, false, 1, 2));
			storyDeck.Add(createEventCard("Prosperity Throughout the Realm", 0, 2, false, 0, 0)); */

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

		public Story createEventCard(string name, int shieldModifier, int adventureCardModifier, bool eliminiateAllies, int weaponCardModifier, int foeCardModifier){
			Event eve = new Event(name, shieldModifier, adventureCardModifier, eliminiateAllies, weaponCardModifier, foeCardModifier);
			return eve;
		}

		public Story createTournamentCard(string name, int shieldModifier){
			Tournament tournament = new Tournament(name, shieldModifier);
			return tournament;
		}

		public Story createQuestCard(string name, int numStages, string linkedFoe, string linkedAlly = "none"){
			Quest quest = new Quest(name, numStages, linkedFoe, linkedAlly);
			return quest;
		}

		public Adventure createAdventureCard(string type, string name = "", int battlePoints = 0, int bids = 0){
			switch (type) {
			case "Foe":
				Foe foe = new Foe (name, battlePoints); 
				return foe;
			case "Weapon":
				Weapon weapon = new Weapon (name, battlePoints);
				return weapon;
			case "Ally":
				Ally ally = new Ally (name, battlePoints, bids);
				return ally;
			case "Amour":
				Amour amour = new Amour ();
				return amour;
			case "Test":
				Test test = new Test (name, bids);
				return test;
			default:
				Console.WriteLine ("Deck creation err, abort");
				Foe apocalypse = new Foe("Critical Err", 9001);
				return apocalypse;
			}
		}
    }
}
