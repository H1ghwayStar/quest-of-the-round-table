using System;

namespace PROJ3004
{
	public class Amour : Adventure
	{
		private int bids;

		public Amour(string name, int battlePoints, int bids)
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
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints + "Bids" + bids);
		}
	}
}

