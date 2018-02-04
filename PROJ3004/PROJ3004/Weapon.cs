using System;

namespace PROJ3004
{
	public class Weapon : Adventure
	{
		public Weapon(string name, int battlePoints)
		{
			this.name = name;
			this.battlePoints = battlePoints;
		}

		public override string ToString()
		{
			return string.Format("Name: " + name + ", Battle Points: " + battlePoints);
		}

		public override bool Equals(Object obj){
			return obj is Weapon && this == (Weapon)obj;
		}

		public override int GetHashCode()
		{
			return base.GetHashCode();
		}

		public static bool operator !=(Weapon x, Weapon y){
			return !(x == y);
		}

		public static bool operator ==(Weapon x, Weapon y)
		{
			return x.name == y.name;
		}
	}
}

