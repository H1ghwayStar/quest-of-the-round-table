using System;

namespace PROJ3004
{
    class Gameboard
    {
        public static void Main(string[] args)
        {
            //Test Cases

            Foe Dragon = new Foe("Dragon", 50);
            Console.WriteLine(Dragon);

            Foe Boar = new Foe("Boar", 5);             Console.WriteLine(Boar);

            Weapon Excalibur = new Weapon("Excalibur", 30);
            Console.WriteLine(Excalibur);

            Weapon Lance = new Weapon("Lance", 20);
            Console.WriteLine(Lance);

            Ally KingArthur = new Ally("King Arthur", 10, 2);
            Console.WriteLine(KingArthur);

            Amour amour = new Amour();
            Console.WriteLine(amour);

            Ally KingPellinore = new Ally("King Pellinore", 10);
            Console.WriteLine(KingPellinore);

            Test TestOfTheQuestingBeast = new Test("Test of the Questing Beast", 4);
            Console.WriteLine(TestOfTheQuestingBeast);

            Test TestOfTemptation = new Test("Test of Temptation", 0);
            Console.WriteLine(TestOfTemptation);

            Tournament AtOrkey = new Tournament("Orkey", 2);
            Console.WriteLine(AtOrkey);

            Tournament AtYork = new Tournament("York", 0);
            Console.WriteLine(AtYork);

            Event QueensFavour = new Event("Queens Favour", 0);
            Console.WriteLine(QueensFavour);

            Event CourtCalledToCamelot = new Event("Court Called To Camelot", 0, 0, true, 0, 0);
            Console.WriteLine(CourtCalledToCamelot);

            Quest SearchForTheQuestingBeast = new Quest("Search For The Questing Beast", 4, "none");
            Console.WriteLine(SearchForTheQuestingBeast);

            Quest SlayTheDragon = new Quest("Slay The Dragon", 3, "Dragon");
            Console.WriteLine(SlayTheDragon);
        }
    }
}
