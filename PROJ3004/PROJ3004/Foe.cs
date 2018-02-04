using System;

namespace PROJ3004
{
	public class Foe : Adventure
	{

		public Foe(string name, int battlePoints)
		{
			this.name = name;
			this.battlePoints = battlePoints;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints);
		}

	}
}

