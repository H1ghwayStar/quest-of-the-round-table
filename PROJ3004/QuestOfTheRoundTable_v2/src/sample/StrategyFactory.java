package sample;

public class StrategyFactory {

    public static AIStrategy getStrategyMethod(String type){
        switch(type){
            case "passive":
                return passiveStrategy();
            case "aggressive":
                return aggressiveStrategy();
            //return createDebitCard();
            case "ultra":
                System.out.println("Not yet");
                break;
            //return createCash();
        }
        System.out.println("This should never happen in strategyFactory");
        return null;
    }

    public static PassiveStrategy passiveStrategy(){
        return new PassiveStrategy();
    }

    public static AggressiveStrategy aggressiveStrategy(){
        return new AggressiveStrategy();
    }

}