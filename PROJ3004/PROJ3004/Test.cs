using System;

namespace PROJ3004
{
	public class Test : Adventure
	{
		private int minBids;

		public Test(string name, int minBids)
		{
			this.name = name;
			this.minBids = minBids;
		}

		public int GetMinBids(){
			return minBids;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", minimum bids: " + minBids);
		}

	}
}

