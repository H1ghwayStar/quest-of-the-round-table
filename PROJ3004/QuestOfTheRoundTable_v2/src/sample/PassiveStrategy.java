package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class PassiveStrategy extends CommonStrategy implements AIStrategy {

    public boolean tournamentStrategy(AIPlayer player, boolean isTie){
        Random random = new Random();
        player.cardsSelected.clear();//Will this cause problems?
        int numWeapons = 0;
        int numAllies = 0;
        int amourInHand = -1;

        if(!isTie) {
            int willPlay = random.nextInt(2);
            if (willPlay < 1)
                return false;
        }

        ArrayList<Pair<Weapon, Integer>> weaponsInHand = new ArrayList<>();
        ArrayList<Pair<Ally, Integer>> alliesInHand = new ArrayList<>();


        for(int i = 0; i < player.cardsInHand.size(); i++){
            Adventure a = player.cardsInHand.get(i);
            if(a instanceof  Weapon){
                numWeapons++;
                weaponsInHand.add(new Pair<>((Weapon) a, i));
            }else if(a instanceof  Ally){
                numAllies++;
                alliesInHand.add(new Pair<>((Ally) a, i));
            }else if(a instanceof Amour){
                amourInHand = i;
            }
        }

        int a = random.nextInt(2);
        if(amourInHand > -1 && a > 0){// 50% change to play amour
            player.cardsSelected.add(amourInHand);
        }

        if(numWeapons > 0){
            int ceiling = (int) Math.ceil(0.5 * numWeapons);
            int weaponsToPlay = random.nextInt(ceiling);
            System.out.println("WeaponsToPlay: " + weaponsToPlay);
            for(int i = 0;  i < weaponsToPlay; i++){
                int randomWeapon = random.nextInt(weaponsInHand.size());
                player.cardsSelected.add(weaponsInHand.get(randomWeapon).getValue());
                weaponsInHand.remove(randomWeapon);
            }
        }

        if(numAllies > 0){
            int ceiling = ((int) Math.ceil(0.5 * numAllies));
            int alliesToPlay = random.nextInt(ceiling) + 1;
            System.out.println("AlliesToPlay: " + alliesToPlay);
            for(int i = 0; i < alliesToPlay; i++){
                int randomAlly = random.nextInt((alliesInHand.size()));
                player.cardsSelected.add(alliesInHand.get(randomAlly).getValue());
                alliesInHand.remove(randomAlly);
            }
        }

        return true;
    }

    public void questStrategyFoe(AIPlayer player, int onStage){
        player.cardsSelected.clear();//Will this cause problems?
        int numWeapons = 0;
        int numAllies = 0;
        int amourInHand = -1;
        Random random = new Random();

        ArrayList<Pair<Weapon, Integer>> weaponsInHand = new ArrayList<>();
        ArrayList<Pair<Ally, Integer>> alliesInHand = new ArrayList<>();


        for(int i = 0; i < player.cardsInHand.size(); i++){
            Adventure a = player.cardsInHand.get(i);
            if(a instanceof  Weapon){
                numWeapons++;
                weaponsInHand.add(new Pair<>((Weapon) a, i));
            }else if(a instanceof  Ally){
                numAllies++;
                alliesInHand.add(new Pair<>((Ally) a, i));
            }else if(a instanceof Amour && !player.amourOnQuest){
                amourInHand = i;
            }
        }

        if(amourInHand > -1){//for quest always play amours, it just makes sense
            player.cardsSelected.add(amourInHand);
        }

        if(numWeapons > 0){
            double factor;
            //int minIfPossible = 1;

            switch(onStage){
                case 4:
                    factor = 0.5;
                    //minIfPossible = 3;
                    break;
                case 3:
                    factor = 0.4;
                    //minIfPossible = 2;
                    break;
                case 2:
                    factor = 0.25;
                    //minIfPossible = 1;
                    break;
                case 1:
                    factor = 0.2;
                    //minIfPossible = 1;
                    break;
                default:
                    System.out.println("Hit the default, should be 5. Actual: " + onStage);
                    factor = 0.65;
                    //minIfPossible = 4;
            }

            //int ceiling = (int) Math.ceil(factor * numWeapons);
            int weaponsToPlay = (int) Math.ceil(factor * numWeapons);
            //if(minIfPossible <= weaponsInHand.size() && minIfPossible > weaponsToPlay )
            //weaponsToPlay = minIfPossible;
            System.out.println("WeaponsToPlay: " + weaponsToPlay);
            for(int i = 0;  i < weaponsToPlay; i++){
                int randomWeapon = random.nextInt(weaponsInHand.size());
                player.cardsSelected.add(weaponsInHand.get(randomWeapon).getValue());
                weaponsInHand.remove(randomWeapon);
            }
        }

        if(numAllies > 0 && !player.alliesAddedInQuest){//only add Allies in the first stage of the quest, that makes most sense
            int ceiling = ((int) Math.ceil(0.5 * numAllies));
            int alliesToPlay = random.nextInt(ceiling) + 1;
            System.out.println("AlliesToPlay: " + alliesToPlay);
            for(int i = 0; i < alliesToPlay; i++){
                int randomAlly = random.nextInt((alliesInHand.size()));
                player.cardsSelected.add(alliesInHand.get(randomAlly).getValue());
                player.alliesAddedInQuest = true;
                alliesInHand.remove(randomAlly);
            }
        }


    }

    public int questStrategyTest(AIPlayer player, int currentBid, int maxBids){ //stagesLeft not including this stage
        if(currentBid < maxBids * 0.3)//P - A - UA : 0.3 - 0.45 - 1
            return currentBid++;
        else
            return -1;
    }


    public boolean willHost(){//P - A - UA : 50% - 80 % - 100%
        boolean gonnaHost = false;
        Random random = new Random();
        int x = random.nextInt(2);
        if(x > 0)//50% chance of this
            gonnaHost = true;
        return gonnaHost;
    }

    public boolean willPlayQuest(){
        boolean willPlay = false;
        Random random = new Random();
        int x = random.nextInt(101);
        if(x <= 75)
            willPlay = true;
        return willPlay;
    }

    public void allyStrategy(AIPlayer player){//P - A - UA: 40%, 65%, 100%
        double factor = 0.4;
        int allyCounter = 0;

        for(int i = 0; i < player.cardsInHand.size(); i++){
            Adventure a = player.cardsInHand.get(i);
            if(a instanceof  Ally)
                allyCounter++;
        }

        int alliesToPlay = (int) Math.ceil(factor * allyCounter);

        for(int i = player.cardsInHand.size() - 1; i > -1; i--){
            if(alliesToPlay == 0)
                break;
            Adventure a = player.cardsInHand.get(i);
            if(a instanceof  Ally){
                player.cardsInHand.remove(i);
                player.alliesInPlay.add((Ally)a);
                alliesToPlay--;
            }
        }
    }

//    public void mordredStrategy( AIPlayer player, ArrayList<Player> playerList){//P - A - UA : 25%, 50%, 100%
//        //get playerList from Gameboard, pass it in from controller
//
//        Random random = new Random();
//        int x = random.nextInt(4);
//        if(x < 1) {
//
//            Player leader = null;
//            Ally strongestAlly = null;
//
//            for (Player p : playerList) {
//                if (p == player)
//                    continue;
//                if (p.alliesInPlay.size() == 0)
//                    continue;
//                if (leader == null)
//                    leader = p;
//                else if (Player.rankDictionary.get(p.rank) > Player.rankDictionary.get(leader.rank)) {
//                    leader = p;
//                }
//            }
//
//            if (leader != null) {
//                for (int i = 0; i < leader.alliesInPlay.size(); i++) {
//                    if (strongestAlly == null)
//                        strongestAlly = leader.alliesInPlay.get(i);
//                    else if (leader.alliesInPlay.get(i).battlePoints > strongestAlly.battlePoints)
//                        strongestAlly = leader.alliesInPlay.get(i);
//                }
//
//                //strongestAlly should not be null at this point
//                leader.alliesInPlay.remove(strongestAlly);
//            }
//        }
//    }

}
