import java.util.*;
import java.io.*;
// input looks like this: numraces,numplayers,player1,score1,2,2,3,3,4,4
// example: java Main 8,4,JC,90,JP,88,TE,80,LV,76
public class Main{

    private ArrayList<Player> players;


    public Main(){
        this.players = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public static void main(String[] args) throws IOException{
        Main controller = new Main();
        // read in from spreadsheets
        File directoryPath = new File("PlayerData");
        File filesList[] = directoryPath.listFiles();
        for(File fp : filesList) {
            System.out.println("File name: "+fp.getName());
            Scanner sc = new Scanner(fp);
            String readInits = "";
            String readName = "";
            int readPoints = 0;
            int readPlace = 0;
            int readGames = 0;
            int readRaces = 0;
            ArrayList<GrandPrix> games = new ArrayList<GrandPrix>();
            int i = 0;
            // order = name, initials, races, score, games, places      
            while (sc.hasNextLine()){
                if(i == 0){ // basic info
                    String line = sc.nextLine();
                    String[] splitLine = line.split(",");
                    readName = splitLine[0];
                    readInits = splitLine[1];
                    readRaces = Integer.parseInt(splitLine[2]);
                    readPoints = Integer.parseInt(splitLine[3]);
                    readGames = Integer.parseInt(splitLine[4]);
                    readPlace = Integer.parseInt(splitLine[5]);
                }else{ // games to be added to the player
                    String line = sc.nextLine();
                    if(!line.equals("")){
                    String[] splitted_input = line.split(",");
                    int numRaces = Integer.parseInt(splitted_input[0]);
                    int numPlayers = Integer.parseInt(splitted_input[1]);
                    int numCompetitors = (splitted_input.length - 2) / 2;
                    int placeHolder = 1;
                    ArrayList<PiG> playersInGame = new ArrayList<PiG>();
                    for(int k = 2; k < splitted_input.length; k+=2){
                        String initials = splitted_input[k];
                        int score = Integer.parseInt(splitted_input[k+1]);
                        int place = placeHolder;
                        PiG newPlayer = new PiG(score, numRaces, initials, place, numCompetitors);
                        playersInGame.add(newPlayer);
                        placeHolder++;
                    }
                    GrandPrix newGame = new GrandPrix(numRaces, numPlayers, playersInGame);
                    games.add(newGame);
                    // create games to be read in here
                }
                }
                i++;
            }
            Player newPlayer = new Player(readName, readInits, readPoints, readRaces, games, readPlace);
            controller.getPlayers().add(newPlayer);
            sc.close();
        }


        // load in the new game from the input OR skip to stats
        if(!args[0].equals("Stats")){
            int numRaces = 0;
            int numPlayers = 0;
            ArrayList<PiG> playersInGame = new ArrayList<PiG>();
            String input = args[0];
            String[] splitted_input = input.split(",");
            numRaces = Integer.parseInt(splitted_input[0]);
            numPlayers = Integer.parseInt(splitted_input[1]);
            int numCompetitors = (splitted_input.length - 2) / 2;
            int placeHolder = 1;
            for(int i = 2; i < splitted_input.length; i+=2){
                String initials = splitted_input[i];
                int score = Integer.parseInt(splitted_input[i+1]);
                int place = placeHolder;
                PiG newPlayer = new PiG(score, numRaces, initials, place, numCompetitors);
                playersInGame.add(newPlayer);
                placeHolder++;
            }
            GrandPrix newGame = new GrandPrix(numRaces, numPlayers, playersInGame);





            // now that the game is loaded in, we have to add it to the players that played in it
            // have to check to see if the player is in the system: list of Players
            for(int i = 0; i < newGame.getPlayers().size(); i++){
                String inits = newGame.getPlayers().get(i).getInitials();
                boolean found = false;
                for(int j = 0; j < controller.getPlayers().size(); j++){
                    if(inits.equals(controller.getPlayers().get(j).getInitials())){
                        found = true;
                    }
                }
                if(!found){
                    // Enter data using BufferReader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Player not found: what name do you want for " + inits);
                    // Reading data using readLine
                    String newName = reader.readLine();
                    Player newPlayer = new Player(newName, inits);
                    newPlayer.addScore(newGame.getPlayers().get(i).getScore());
                    newPlayer.addRaces(newGame.getPlayers().get(i).getRaces());
                    newPlayer.addGame(newGame);
                    newPlayer.addPlaces(newGame.getPlayers().get(i).getPlace());
                    controller.players.add(newPlayer);

                }else{
                    int index = 0;
                    for(int j = 0; j < controller.getPlayers().size(); j++){
                        if(inits.equals(controller.getPlayers().get(j).getInitials())){
                            index = j;
                        }
                    }
                    // add to players[j]'s stats
                    controller.getPlayers().get(index).addScore(newGame.getPlayers().get(i).getScore());
                    controller.getPlayers().get(index).addRaces(newGame.getPlayers().get(i).getRaces());
                    controller.getPlayers().get(index).addPlaces(newGame.getPlayers().get(i).getPlace());
                    controller.getPlayers().get(index).addGame(newGame);
                    
                }
            }



            // write all player data to the CSV files
            for(int p = 0; p < controller.getPlayers().size(); p++){
                String path = "PlayerData/" +  controller.getPlayers().get(p).getInitials() + ".csv";
                BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
                // write the first line
                writer.append(controller.getPlayers().get(p).getName() + ",");
                writer.append(controller.getPlayers().get(p).getInitials() + ",");
                writer.append(controller.getPlayers().get(p).getRaces() + ",");
                writer.append(controller.getPlayers().get(p).getScore() + ",");
                writer.append(controller.getPlayers().get(p).getGames().size() + ",");
                writer.append(controller.getPlayers().get(p).getPlaces() + "\n");
                // write the games
                for(int l = 0; l < controller.getPlayers().get(p).getGames().size(); l++){
                    int count = 0;
                    writer.append(controller.getPlayers().get(p).getGames().get(l).getNumRaces() + ",");
                    writer.append(controller.getPlayers().get(p).getGames().get(l).getNumPlayers() + ",");
                    for(int n = 0; n < controller.getPlayers().get(p).getGames().get(l).getNumPlayers() - 1; n++){
                        writer.append(controller.getPlayers().get(p).getGames().get(l).getPlayers().get(n).getInitials() + ",");
                        writer.append(controller.getPlayers().get(p).getGames().get(l).getPlayers().get(n).getScore() + ",");
                        count++;
                    }
                    writer.append(controller.getPlayers().get(p).getGames().get(l).getPlayers().get(count).getInitials() + ",");
                    writer.append(controller.getPlayers().get(p).getGames().get(l).getPlayers().get(count).getScore() + "\n");
                    
                }
                writer.close();
            }
        }



        // do the stats for each player: output a leaderboard

        // need to implement true PPR, SOS, win percent, h2h win percent
        BufferedWriter writer = new BufferedWriter(new FileWriter("stats.csv", false));
        writer.append("PlayerName,Initials,GrandPrixsPlayed,RacesPlayed,TotalPoints,PPR,TruePPR,AveragePlace,Wins,WinPercent,QualityWinPct,H2HWinPct\n");
        for(int p = 0; p < controller.getPlayers().size(); p++){
            writer.append(controller.getPlayers().get(p).getName() + ",");
            writer.append(controller.getPlayers().get(p).getInitials() + ",");
            writer.append(controller.getPlayers().get(p).getGames().size() + ",");
            writer.append(controller.getPlayers().get(p).getRaces() + ",");
            writer.append(controller.getPlayers().get(p).getScore() + ",");
            // PPR
            double avgScore = (double) controller.getPlayers().get(p).getScore() / (double) controller.getPlayers().get(p).getRaces();
            writer.append(String.format("%.2f", avgScore) + ",");
            // true PPR: PPR * (total racers faced + totalGP * 6) / (totalGP * 9)
            int racersFaced = 0;
            for(int h = 0; h < controller.getPlayers().get(p).getGames().size(); h++){
                racersFaced += (controller.getPlayers().get(p).getGames().get(h).getNumPlayers() - 1);
            }
            double truePPR = avgScore * (racersFaced + (6 * controller.getPlayers().get(p).getGames().size())) / (double) (controller.getPlayers().get(p).getGames().size() * 9);
            writer.append(String.format("%.2f", truePPR) + ",");
            // avg place
            double avgPlace = (double) controller.getPlayers().get(p).getPlaces() / (double) controller.getPlayers().get(p).getGames().size();
            writer.append(String.format("%.2f", avgPlace) + ",");

            // num of wins
            int firstCount = 0;
            for(int h = 0; h < controller.getPlayers().get(p).getGames().size(); h++){
                if(controller.getPlayers().get(p).getGames().get(h).getPlayers().get(0).getInitials().equals(controller.getPlayers().get(p).getInitials())){
                    firstCount++;
                }
            }
            writer.append(firstCount + ",");
            // win percent
            double winpct = 100 * (double) firstCount / (double) controller.getPlayers().get(p).getGames().size();
            writer.append(String.format("%.2f", winpct) + " %,");


            // quality win pct: (# first place * (total racers faced / total GP played) / 3) / total # GP
            int numGP = controller.getPlayers().get(p).getGames().size();
            int totalRacersFaced = 0;
            for(int h = 0; h < controller.getPlayers().get(p).getGames().size(); h++){
                totalRacersFaced += (controller.getPlayers().get(p).getGames().get(h).getPlayers().size() - 1);
            }
            double qualWinPct = 100 * (((double) firstCount * ((double) totalRacersFaced / (double) numGP) / 3) / (double) numGP);
            writer.append(String.format("%.2f", qualWinPct) + " %,");

            //h2h win percent
            int winCount = 0;
            int lossCount = 0;
            for(int h = 0; h < controller.getPlayers().get(p).getGames().size(); h++){
                for(int o = 0; o < controller.getPlayers().get(p).getGames().get(h).getPlayers().size(); o++){
                    if(controller.getPlayers().get(p).getGames().get(h).getPlayers().get(o).getInitials().equals(controller.getPlayers().get(p).getInitials())){
                        winCount = winCount + (controller.getPlayers().get(p).getGames().get(h).getPlayers().size() - (o + 1));
                        lossCount = lossCount + o;
                    }
                }
            }
            double H2Hpct = 100 * (double) winCount / ((double) (winCount + lossCount));
            writer.append(String.format("%.2f", H2Hpct) + " %\n");
        }
        writer.close();
        
    }
}