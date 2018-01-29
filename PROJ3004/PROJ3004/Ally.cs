using System;

namespace PROJ3004
{
	public class Ally : Adventure
	{
		private int bids;

		public Ally(string name, int battlePoints, int bids = 0)
		{
			this.name = name;
			this.battlePoints = battlePoints;
			this.bids = bids;
		}
		public int GetBids()
		{
			return bids;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints + ", Bids " + bids);
		}
	}
}

