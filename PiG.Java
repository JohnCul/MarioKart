import java.util.ArrayList;

public class PiG{
    private int score;
    private int races;
    private String initials;
    private int place;
    private int numCompetitors;

    public PiG(int score, int races, String initials, int place, int numCompetitors){
        this.score = score;
        this.races = races;
        this.initials = initials;
        this.place = place;
        this.numCompetitors = numCompetitors;
    }

    public String getInitials(){
        return this.initials;
    }

    public int getScore(){
        return this.score;
    }

    public int getRaces(){
        return this.races;
    }

    public int getPlace(){
        return this.place;
    }

    public int getNumComp(){
        return this.numCompetitors;
    }
    
}