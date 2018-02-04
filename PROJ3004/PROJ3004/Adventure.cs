using System;

namespace PROJ3004
{
	abstract public class Adventure : Card
	{
		protected string name;
		protected int battlePoints;

		public string GetName()
		{
			return name;
		}

		public int GetBattlePoints()
		{
			return battlePoints;
		}
			

	}
}

