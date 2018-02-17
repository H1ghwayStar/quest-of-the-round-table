using System;
using System.Collections;
using System.Collections.Generic;

namespace PROJ3004
{
	public class Player
	{	//Rethink access modifiers for all this stuff later
		private string name;
		public int shields;
		public string rank;
		public static readonly int MAX_CARDS_HAND = 12;
		public List <Ally> alliesInPlay = new List <Ally> ();
		public List <Adventure> cardsInHand = new List <Adventure>(); //reconsider visibility of this field
		public List<Adventure> discardPile = new List<Adventure>(); //reconsider visibility of this field
		public static readonly Dictionary<string, int> rankDictionary = new Dictionary<string, int>(){ //rank and associated battle points
			{"Squire",5},
			{"Knight",10},
			{"Champion Knight", 20},
			{"Knight of the Round", 9001}
		};

		private static int numPlayers = 1;
		//private int playerNum;

		public Player (string name, string rank = "Squire", int shields = 0)
		{
			this.name = name;
			this.rank = rank;
			this.shields = shields;

			//this.playerNum = numPlayers;
			numPlayers++;
		}

		public string getName(){
			return name;
		}
			
		//discard method here
		public void discard(int discardedCards){
			if (cardsInHand.Count == 0)
				Console.WriteLine ("Can't discard from an empty hand!!!");

			for(int i = 0; i < discardedCards; i++){
				discardPile.Add(cardsInHand[cardsInHand.Count-1]);
				cardsInHand.RemoveAt (cardsInHand.Count - 1);
			}
		}
		//rank-up method here

	}
}

