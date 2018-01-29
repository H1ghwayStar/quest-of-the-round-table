using System;
using System.Collections;
using System.Collections.Generic;

namespace PROJ3004
{
	public class Player
	{
		private string name;
		private int shields;
		private string rank;
		public const int MAX_CARDS_HAND = 12;
		private ArrayList cardsInHand = new ArrayList();
		public static readonly Dictionary<string, int> rankDictionary = new Dictionary<string, int>(){ //rank and associated battle points
			{"Squire",5},
			{"Knight",10},
			{"Champion Knight", 20},
			{"Knight of the Round", 9001}
		};

		private static int numPlayers = 1;
		private int playerNum;

		public Player (string name, string rank = "Squire", int shields = 0)
		{
			this.name = name;
			this.rank = rank;
			this.shields = shields;

			this.playerNum = numPlayers;
			numPlayers++;
		}

		//draw method here
		//discard method here
		//rank-up method here

	}
}

