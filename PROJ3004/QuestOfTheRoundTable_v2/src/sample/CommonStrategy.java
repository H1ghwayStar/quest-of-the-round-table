package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

abstract public class CommonStrategy implements AIStrategy{

    public void discardStrategy(AIPlayer player, int cardsTodiscard){
        //Allys and Amours valuable, not gonna discard those unless forced
        ArrayList<Foe> foesInHand = new ArrayList<>();
        ArrayList<Weapon> weaponsInHand = new ArrayList<>();
        ArrayList<Test> testsInHand = new ArrayList<>();

        for(int i = 0; i < player.cardsInHand.size(); i++){
            Adventure a = player.cardsInHand.get(i);
            if(a instanceof Foe){
                foesInHand.add((Foe)a);
            }else if(a instanceof  Weapon){
                weaponsInHand.add((Weapon)a);
            }else if(a instanceof Test){
                testsInHand.add((Test)a);
            }
        }

        Collections.sort(foesInHand, new Comparator<Foe>() {
            @Override
            public int compare(final Foe o1, final Foe o2) {
                return Integer.compare(o1.battlePoints, o2.battlePoints) ;
            }
        });

        Collections.sort(weaponsInHand, new Comparator<Weapon>() {
            @Override
            public int compare(final Weapon o1, final Weapon o2) {
                return Integer.compare(o1.battlePoints, o2.battlePoints) ;
            }
        });

        Collections.sort(testsInHand, new Comparator<Test>() {
            @Override
            public int compare(final Test o1, final Test o2) {
                return Integer.compare(o1.GetMinBids(), o2.GetMinBids()) ;
            }
        });

        Collections.reverse(foesInHand);
        Collections.reverse(weaponsInHand);
        Collections.reverse(testsInHand);

        int foeDiscardCounter = 0;
        int weaponDiscardCounter = 0;
        for(int i = 0; i < cardsTodiscard; i++){
            if(foesInHand.size() > 0 && foeDiscardCounter < 2){
                foesInHand.remove(foesInHand.size() - 1);
                foeDiscardCounter++;
            }else if(weaponsInHand.size() > 0 && weaponDiscardCounter < 1){
                weaponsInHand.remove(weaponsInHand.size() - 1);
                weaponDiscardCounter++;
            }else if(testsInHand.size() > 0){
                testsInHand.remove(testsInHand.size() - 1);
                foeDiscardCounter = 0;
                weaponDiscardCounter = 0;
            }else{
                foeDiscardCounter = 0;
                weaponDiscardCounter = 0;
            }
        }
    }
}
