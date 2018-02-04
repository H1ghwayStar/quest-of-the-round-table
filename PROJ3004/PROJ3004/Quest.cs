using System;

namespace PROJ3004
{
	public class Quest : Story
	{
		private int numStages;
		private string linkedFoe;
		private string linkedAlly;

		public Quest (string name, int numStages, string linkedFoe, string linkedAlly = "none")
		{
            this.name = name;
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
            return string.Format("Name: " + name + ", Number of Stages: " + numStages + ", Linked Foe: " + linkedFoe + ", Linked Ally " + linkedAlly);
		}

	}
}

