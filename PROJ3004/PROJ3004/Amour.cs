using System;

namespace PROJ3004
{
	public class Amour : Adventure
	{
		private int bids;

		public Amour()
		{
			this.name = "Amour"; //remove this later if needed
			this.battlePoints = 10;
			this.bids = 1;
		}
		public int GetBids()
		{
			return bids;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints + ", Bids" + bids);
		}
	}
}

