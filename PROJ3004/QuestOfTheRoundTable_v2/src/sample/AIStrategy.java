package sample;

import java.util.ArrayList;

public interface AIStrategy {
    boolean tournamentStrategy(AIPlayer player, boolean isTie);
    void questStrategyFoe(AIPlayer player, int onStage);
    int questStrategyTest(AIPlayer player, int currentBid, int maxBid);
    public void discardStrategy(AIPlayer player, int cardsToDiscard);
    boolean willHost();
    boolean willPlayQuest();
    void allyStrategy(AIPlayer player);
    //void mordredStrategy(AIPlayer player, ArrayList<Player> playerList);

}