
import java.io.Serializable;

public class Player {

    public Thread thread;
    public String name;
    public Serializable play;
    public int points;
    public boolean playAgain;

    Player(Thread t, String n) {
        this.thread = t;
        this.name = n;
    }

    public Serializable getPlay() {
        return play;
    }

    public void setPlay(Serializable val) {
        this.play = val;
        System.out.println(this.play);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int val) {
        this.points = val;
    }

    public boolean getPlayAgain() {
        return getPlayAgain();
    }

    public void setPlayAgain(boolean val) {
        this.playAgain = val;
    }
}
