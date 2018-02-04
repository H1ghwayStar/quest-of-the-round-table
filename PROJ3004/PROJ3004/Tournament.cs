using System;

namespace PROJ3004
{
	public class Tournament : Story
	{
		protected int shieldModifier;

		public Tournament (string name, int shieldModifier)
		{
			this.name = name;
			this.shieldModifier = shieldModifier;
		}

		public int GetShieldModifier(){
			return shieldModifier;
		}

		public override string ToString ()
		{
			return string.Format ("Tournament at " + name + ", bonus shields " + shieldModifier);
		}
	}
}

