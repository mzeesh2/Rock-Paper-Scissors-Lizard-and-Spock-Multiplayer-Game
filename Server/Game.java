import java.io.Serializable;
import java.util.ArrayList;

public class Game {
    public String winner = null;
    public ArrayList<Player> players = new ArrayList<Player>();
    public int numClients = players.size();
    public boolean ready;
    public String getStatus(){
        String status = "Clients Connected to Sever: " + this.numClients + "\n" +
                        "Player 1 play: " + this.players.get(0).getPlay() + "\n" +
                        "Player 2 play: " + this.players.get(1).getPlay() + "\n" +
                        "Player 1 points: " + this.players.get(0).getPoints() + "\n" +
                        "Player 2 points" + this.players.get(1).getPoints() + "\n" +
                        "Winner: " + this.winner + "\n" +
                        "Player 1 play again:" + "\n" +
                        "Player 2 play again:" + "\n";
        return status;
    }


    public void setWinner(String w){
        this.winner = w;
    }

    public boolean isReady(){return this.ready;}



    public int getNumClients() {
        return numClients;
    }

    public void setNumClients() {
        this.numClients = this.players.size();
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void removePlayer(Player p){
        players.remove(p);
    }

    public String getWinner(){
        return this.winner;
    }

    private void setWinner(){
        if (players.get(0).getPoints() == 1){
            this.winner = "Player 1";
        }
        else if (players.get(1).getPoints() == 1){
            this.winner = "Player 2";
        }
    }

    public String getRWinner(Serializable p1PlayO, Serializable p2PlayO){
        String p1Play = p1PlayO.toString().intern();
        String p2Play = p2PlayO.toString().intern();
        if (p1Play.toLowerCase().equals("rock") && p2Play.toLowerCase().equals("paper")){
             players.get(1).setPoints(players.get(1).getPoints() + 1);
             setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("rock") && p2Play.toLowerCase().equals("scissors")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("rock") && p2Play.toLowerCase().equals("lizard")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("rock") && p2Play.toLowerCase().equals("spock")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("paper") && p2Play.toLowerCase().equals("rock")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("paper") && p2Play.toLowerCase().equals("scissors")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("paper") && p2Play.toLowerCase().equals("lizard")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("paper") && p2Play.toLowerCase().equals("spock")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("scissors") && p2Play.toLowerCase().equals("rock")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("scissors") && p2Play.toLowerCase().equals("paper")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("scissors") && p2Play.toLowerCase().equals("lizard")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("scissors") && p2Play.toLowerCase().equals("spock")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("lizard") && p2Play.toLowerCase().equals("rock")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("lizard") && p2Play.toLowerCase().equals("paper")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("lizard") && p2Play.toLowerCase().equals("scissors")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("lizard") && p2Play.toLowerCase().equals("spock")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("spock") && p2Play.toLowerCase().equals("rock")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("spock") && p2Play.toLowerCase().equals("paper")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else if (p1Play.toLowerCase().equals("spock") && p2Play.toLowerCase().equals("scissors")){
            players.get(0).setPoints(players.get(0).getPoints() + 1);
            setWinner();
            return "Player 1";
        }
        else if (p1Play.toLowerCase().equals("spock") && p2Play.toLowerCase().equals("spock")){
            players.get(1).setPoints(players.get(1).getPoints() + 1);
            setWinner();
            return "Player 2";
        }
        else {
            return "Tie Game";
        }


    }
}
