import java.util.ArrayList;

public class GrandPrix{
    private int races;
    private int numPlayers;
    private ArrayList<PiG> players;


    public GrandPrix(int races, int numPlayers, ArrayList<PiG> players){
        this.races = races;
        this.numPlayers = numPlayers;
        this.players = players;
    }

    public int getNumRaces(){
        return this.races;
    }

    public int getNumPlayers(){
        return this.numPlayers;
    }

    public ArrayList<PiG> getPlayers(){
        return players;
    }
}