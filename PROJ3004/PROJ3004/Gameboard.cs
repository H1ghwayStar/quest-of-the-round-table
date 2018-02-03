using System;

namespace PROJ3004
{
    public class Gameboard
    {
		public Adventure[] adventureDeck  = new Adventure[125];
		public Story[] storyDeck = new Story[28];

        public static void Main(string[] args)
        {
			Gameboard play = new Gameboard ();
			play.createDeck ();	
			for (int i = 0; i < 125; i++) {
				Console.WriteLine("[" + i + "]" + " | " + play.adventureDeck[i].GetName());
			}
			for (int i = 0; i < 28; i++) {
				Console.WriteLine("[" + i + "]" + " | " + play.storyDeck[i].GetName());
			}
        }

		public void createDeck(){
			#region
			int cardCounter = 0;

			//ADVENTURE DECK

			/* AMOUR */

			for (int i = 0; i < 8; i++) {
				adventureDeck[cardCounter] = createAdventureCard ("Amour");
				cardCounter++;
			}

			/* WEAPON */

			for (int i = cardCounter; i < 10; i++) {
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Excalibur", 30);
				cardCounter++;
			}

			for (int i = cardCounter; i < 16; i++) {
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Lance", 20);
				cardCounter++;
			}

			for (int i = cardCounter; i < 24; i++) { 
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Battle-ax", 15);
				cardCounter++;
			}

			for (int i = cardCounter; i < 40; i++) { 
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Sword", 10);
				cardCounter++;
			}

			for (int i = cardCounter; i < 51; i++) { 
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Horse", 10);
				cardCounter++;
			}

			for (int i = cardCounter; i < 57; i++) { 
				adventureDeck[cardCounter] = createAdventureCard ("Weapon", "Dagger", 5);
				cardCounter++;
			}

			/* FOE */
			for(int i = cardCounter; i < 58; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Dragon", 50);
				cardCounter++;
			}

			for(int i = cardCounter; i < 60; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Giant", 40);
				cardCounter++;
			}

			for(int i = cardCounter; i < 64; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Mordred", 30);
				cardCounter++;
			}

			for(int i = cardCounter; i < 66; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Green Knight", 25);
				cardCounter++;
			}

			for(int i = cardCounter; i < 69; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Black Knight", 25);
				cardCounter++;
			}

			for(int i = cardCounter; i < 75; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Evil Knight", 20);
				cardCounter++;
			}

			for(int i = cardCounter; i < 83; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Saxon Knight", 15);
				cardCounter++;
			}

			for(int i = cardCounter; i < 90; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Robber Knight", 15);
				cardCounter++;
			}

			for(int i = cardCounter; i < 95; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Saxons", 10);
				cardCounter++;
			}

			for(int i = cardCounter; i < 99; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Boar", 5);
				cardCounter++;
			}

			for(int i = cardCounter; i < 107; i++){
				adventureDeck[cardCounter] = createAdventureCard ("Foe", "Thieves", 5);
				cardCounter++;
			}
			
			/*TEST*/
			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Valor", 0);
			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Valor", 0);

			adventureDeck[cardCounter++] = createAdventureCard ("Test","Test of Temptation", 0);
			adventureDeck[cardCounter++] = createAdventureCard ("Test","Test of Temptation", 0);

			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Questing Best", 0); 
			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Questing Best", 0);

			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Morgan Le Fey", 3);
			adventureDeck[cardCounter++] = createAdventureCard ("Test", "Test of Morgan Le Fey", 3);


			/*Allies*/
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Sir Galahad", 15, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Sir Gawain", 10, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "King Pellinore", 10, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Sir Percival", 5, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Sir Tristan", 10, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "King Arthur", 10, 2);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Queen Guinevere", 0, 3);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Merlin", 0, 0);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Queen Iseult", 0, 2);
			adventureDeck[cardCounter++] = createAdventureCard("Ally", "Sir Lancelot", 15, 0);


			//STORY DECK
			cardCounter = 0;

			/*EVENT*/
			storyDeck[cardCounter++] = createEventCard("Chivalrous Deed", 3, 0, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Pox", -1, 0, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Plague", -2, 0, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("King's Recognition", 2, 0, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("King's Recognition", 2, 0, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Queen's Favor", 0, 2, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Queen's Favor", 0, 2, false, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Court Called to Camelot", 0, 0, true, 0, 0);
			storyDeck[cardCounter++] = createEventCard("Court Called to Camelot", 0, 0, true, 0, 0);
			storyDeck[cardCounter++] = createEventCard("King's Call To Arms", 0, 0, false, 1, 2);
			storyDeck[cardCounter++] = createEventCard("Prosperity Throughout the Realm", 0, 2, false, 0, 0);

			/*Tournament*/
			storyDeck[cardCounter++] = createTournamentCard("AT YORK", 0);
			storyDeck[cardCounter++] = createTournamentCard("AT TINTAGEL", 1);
			storyDeck[cardCounter++] = createTournamentCard("AT ORKNEY", 2);
			storyDeck[cardCounter++] = createTournamentCard("AT CAMELOT", 3);

			/*Quest*/
			storyDeck[cardCounter++] = createQuestCard("Journey through the Enchanted Forest", 3, "Evil Knight");
			storyDeck[cardCounter++] = createQuestCard("Vanquish King Arthur's Enemies", 3, "none");
			storyDeck[cardCounter++] = createQuestCard("Vanquish King Arthur's Enemies", 3, "none");
			storyDeck[cardCounter++] = createQuestCard("Repel the Saxon Raiders", 2, "All Saxons");
			storyDeck[cardCounter++] = createQuestCard("Repel the Saxon Raiders", 2, "All Saxons");
			storyDeck[cardCounter++] = createQuestCard("Boar Hunt", 2, "Boar");
			storyDeck[cardCounter++] = createQuestCard("Boar Hunt", 2, "Boar");
			storyDeck[cardCounter++] = createQuestCard("Search for the Questing Beast", 4, "none", "King Pellinore");
			storyDeck[cardCounter++] = createQuestCard("Defend the Queen's Honor", 4, "All", "Sir Lancelot");
			storyDeck[cardCounter++] = createQuestCard("Slay the Dragon", 3, "Dragon");
			storyDeck[cardCounter++] = createQuestCard("Rescue the Fair Maiden", 3, "Black Knight");
			storyDeck[cardCounter++] = createQuestCard("Search for the Holy Grail", 5, "All", "Sir Percival");
			storyDeck[cardCounter++] = createQuestCard("Search for the Holy Grail", 4, "Green Knight", "Sir Gawain");

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
