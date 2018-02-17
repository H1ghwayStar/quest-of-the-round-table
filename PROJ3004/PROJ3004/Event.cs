using System;
using System.Collections.Generic;

namespace PROJ3004
{
	public class Event : Story
	{
		private int shieldModifier;
		private int adventureCardModifier;
		private bool eliminateAllies;
		private int weaponCardModifier;
		private int foeCardModifier;
		private string description;
		private string whosAffected;
		public static readonly List<string> playersTargeted = new List<String> {"LowestRankAndShield", "All", "AllExceptDrawer", "DrawerOnly", "HighestRanked", "LowestRanked", "Next" };

		public Event(string name, int shieldModifier, int adventureCardModifier, bool eliminiateAllies, int weaponCardModifier, 
			int foeCardModifier, string whosAffected, string description)
		{
			this.name = name;
			this.shieldModifier = shieldModifier;
			this.adventureCardModifier = adventureCardModifier;
			this.eliminateAllies = eliminiateAllies;
			this.weaponCardModifier = weaponCardModifier;
			this.foeCardModifier = foeCardModifier;
			this.description = description;
			this.whosAffected = whosAffected;
		}

		public int GetAdventureCardModifier(){
			return adventureCardModifier;
		}

		public bool GetEliminateAllies(){
			return eliminateAllies;
		}

		public int GetWeaponCardModifier(){
			return weaponCardModifier;
		}

		public int GetFoeCardModifier(){
			return foeCardModifier;
		}

		public int GetShieldModifier(){
			return shieldModifier;
		}

		public string GetDescription(){
			return description;
		}

		public string GetWhosAffected(){
			return whosAffected;
		}

		public override string ToString ()
		{
            return string.Format("Name: " + name + ", Shield Modifier: " + shieldModifier + ", Adventure Cards Modifier: " + adventureCardModifier 
				+ ", Allies eliminated? " + eliminateAllies + ", Weapon Cards Modifier: " + weaponCardModifier + ", Foe Cards Modifier: " + foeCardModifier
				+ "who's affected?: " + whosAffected + "\n" + description);
		}
	}
}

