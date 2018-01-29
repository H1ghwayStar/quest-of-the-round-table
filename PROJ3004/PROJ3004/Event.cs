using System;

namespace PROJ3004
{
	public class Event : Story
	{
		protected int shieldModifier;
		private int adventureCardModifier;
		private bool eliminateAllies;
		private int weaponCardModifier;
		private int foeCardModifier;

		public Event(string name, int shieldModifier, int adventureCardModifier = 2, bool eliminiateAllies = false, int weaponCardModifier = 0, int foeCardModifier = 0)
		{
			this.name = name;
			this.shieldModifier = shieldModifier;
			this.adventureCardModifier = adventureCardModifier;
			this.eliminateAllies = eliminiateAllies;
			this.weaponCardModifier = weaponCardModifier;
			this.foeCardModifier = foeCardModifier;
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

		public override string ToString ()
		{
			return string.Format("Name: " + name);
		}
	}
}

