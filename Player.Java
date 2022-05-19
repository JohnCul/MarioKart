import java.util.ArrayList;

public class Player{
    private String name;
    private String initials;
    private int totalScore;
    private ArrayList<GrandPrix> games;
    private int totalRaces;
    private int totalPlaces;

    public Player(String name, String initials){
        this.name = name;
        this.initials = initials;
        this.totalScore = 0;
        this.totalRaces = 0;
        this.games = new ArrayList<GrandPrix>();
        this.totalPlaces = 0;
    }

    public Player(String name, String initials, int score, int races, ArrayList<GrandPrix> games, int places){
        this.name = name;
        this.initials = initials;
        this.totalScore = score;
        this.totalRaces = races;
        this.games = games;
        this.totalPlaces = places;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void addScore(int newGame){
        this.totalScore += newGame;
    }

    public void addRaces(int newGame){
        this.totalRaces += newGame;
    }

    public void setInitials(String newName){
        this.initials = newName;
    }

    public void addGame(GrandPrix newGame){
        this.games.add(newGame);
    }

    public void addPlaces(int newGame){
        this.totalPlaces += newGame;
    }

    public int getScore(){
        return this.totalScore;
    }

    public String getInitials(){
        return this.initials;
    }

    public int getRaces(){
        return this.totalRaces;
    }

    public int getPlaces(){
        return this.totalPlaces;
    }
    public ArrayList<GrandPrix> getGames(){
        return this.games;
    }

    public String getName(){
        return this.name;
    }

}