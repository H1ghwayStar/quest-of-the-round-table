using System;
using System.Collections.Generic;
using System.Linq;

namespace PROJ3004
{
	public class K_Combinations
	{
		public static List<List<T>> 
		GetKCombs<T>(List<T> list, int length) where T : IComparable
		{
			if (length == 1) return list.Select(t => new List<T> { t }).ToList();
			return GetKCombs(list, length - 1)
				.SelectMany(t => list.Where(o => o.CompareTo(t.Last()) > 0).ToList(), 
					(t1, t2) => t1.Concat(new List<T> { t2 }).ToList()).ToList();
		}

		public K_Combinations ()
		{
		}
	}
}

