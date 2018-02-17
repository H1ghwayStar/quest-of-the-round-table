using System;

namespace PROJ3004
{
	public class Weapon : Adventure, IComparable
	{
		public Weapon(string name, int battlePoints)
		{
			this.name = name;
			this.battlePoints = battlePoints;
		}

		public void setBattlePoints(int battlePoints){
			this.battlePoints = battlePoints;
		}

		public int CompareTo(Object obj){
			if (obj == null)
				return 1;
			Weapon otherWeapon = obj as Weapon;
			if (otherWeapon != null)
				return this.battlePoints.CompareTo (otherWeapon.battlePoints);
			else
				throw new ArgumentException ("Object is not a Weapon");
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
			//Console.WriteLine ("We get here");
			if (ReferenceEquals (x, null) && ReferenceEquals (y, null))
				return true;
			else if(ReferenceEquals(x,null) || ReferenceEquals(y,null))
				return false;
			else
				return x.name == y.name;
		}
	}
}

