package sample;

public class Ally extends Adventure
{
    private int bids;

    public Ally(String name, int battlePoints, int bids)
    {
        this.name = name;
        this.battlePoints = battlePoints;
        this.bids = bids;
    }
    public int GetBids()
    {
        return bids;
    }

    @Override
    public String toString()
    {
        return String.format("Name: " + name + ", Battle Points: " + battlePoints + ", Bids " + bids);
    }
}
