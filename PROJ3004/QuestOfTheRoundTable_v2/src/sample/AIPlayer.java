package sample;

public class AIPlayer extends Player {

    public boolean alliesAddedInQuest = false; //RESET THIS TO FALSE IN CONTROL FLOW EVERY TIME - CURRENTLY NOT DONE

    public AIStrategy strategy;

    public AIPlayer(String name, String rank, AIStrategy strategy){
        this.name = name;
        this.strategy = strategy;
        this.rank = rank;
        System.out.println("AI Player" +this.name+"created");
    }



}
