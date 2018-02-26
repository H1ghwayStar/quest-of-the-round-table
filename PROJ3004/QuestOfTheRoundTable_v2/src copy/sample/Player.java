package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Player {

    //Rethink access modifiers for all this stuff later
    private String name;
    public int shields;
    public String rank;
    public boolean amourOnQuest = false;
    public boolean playerInStory = false;
    public static final int MAX_CARDS_HAND = 12;
    public ArrayList <Ally> alliesInPlay = new ArrayList <> ();
    public ArrayList <Adventure> cardsInHand = new ArrayList<>(); //reconsider visibility of this field
    public ArrayList<Adventure> discardPile = new ArrayList<>(); //reconsider visibility of this field
    ArrayList<Integer> cardsSelected = new ArrayList<>();
    public static final HashMap<String, Integer> rankDictionary;
    static {
        rankDictionary = new HashMap<>();
        rankDictionary.put("Squire", 5);
        rankDictionary.put("Knight", 10);
        rankDictionary.put("Champion Knight", 20);
        rankDictionary.put("Knight of the Round", 9001);
    }

    private static int numPlayers = 0;

    public Player (String name, String rank, int shields)//modified
    {
        this.name = name;
        this.rank = rank;
        this.shields = shields;

        //this.playerNum = numPlayers;
        numPlayers++;
        System.out.println("constructor called");
        System.out.println("numplayers is: " + numPlayers);
    }

    public String getName(){
        return name;
    }



    //discard method here
   /* public void discard(int discardedCards){
        if (cardsInHand.Count == 0)
            Console.WriteLine ("Can't discard from an empty hand!!!");

        for(int i = 0; i < discardedCards; i++){
            discardPile.Add(cardsInHand[cardsInHand.Count-1]);
            cardsInHand.RemoveAt (cardsInHand.Count - 1);
        }
    } */
    //rank-up method here

}
