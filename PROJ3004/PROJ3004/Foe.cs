using System;

namespace PROJ3004
{
	public class Foe : Adventure
	{
		private int boostedBattlePoints;

		public Foe(string name, int battlePoints, int boostedBattlePoints)
		{
			this.name = name;
			this.battlePoints = battlePoints;
			this.boostedBattlePoints = boostedBattlePoints;
		}

		public int GetBoostedBattlePoints(){
			return this.boostedBattlePoints;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints);
		}

	}
}

