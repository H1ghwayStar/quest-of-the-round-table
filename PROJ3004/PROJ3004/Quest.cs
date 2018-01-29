using System;

namespace PROJ3004
{
	public class Quest : Story
	{
		private int numStages;
		private string linkedFoe;
		private string linkedAlly;

		public Quest (int numStages, string linkedFoe, string linkedAlly = "none")
		{
			this.numStages = numStages;
			this.linkedFoe = linkedFoe;
			this.linkedAlly = linkedAlly;
		}

		public int GetNumStages(){
			return numStages;
		}

		public string GetLinkedFoe(){
			return linkedFoe;
		}

		public string GetLinkedAlly(){
			return linkedAlly;
		}

		public override string ToString ()
		{
			return string.Format("Name: " + name);
		}

	}
}

