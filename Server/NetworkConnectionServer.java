
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetworkConnectionServer {

    private ConnThread connthread = new ConnThread();
    private Consumer<Serializable> callback;
    ArrayList<ClientThread> ct;
    // ArrayList<String> players;
    Boolean c1, c2;
    int player1Points = 0, player2Points = 0;
    String client1Data, client2Data;
    private Game game = new Game();

    public NetworkConnectionServer(Consumer<Serializable> callback) {
        this.callback = callback;
        connthread.setDaemon(true);
        ct = new ArrayList<>();
        c1 = false;
        c2 = false;
    }

    public void startConn() throws Exception {
        connthread.start();
    }

    public void sendAll(Serializable data) throws Exception {
        for (int clientNum = 0; clientNum < ct.size(); clientNum++) {
            ct.get(clientNum).tout.writeObject(data);
            ct.get(clientNum).tout.flush();
        }
    }

    public void sendAllExceptMe(Serializable data, int me) throws Exception {
        for (int clientNum = 0; clientNum < ct.size(); clientNum++) {
            if (me != clientNum) {
                ct.get(clientNum).tout.writeObject(data);
                ct.get(clientNum).tout.flush();
            }

        }
    }

    public void send(Serializable data, int index) throws Exception {
        ct.get(index).tout.writeObject(data);
        ct.get(index).tout.flush();
    }

    public void closeConn() throws Exception {
        connthread.socket.close();
    }

    abstract protected boolean isServer();

    abstract protected String getIP();

    abstract protected int getPort();

    class ConnThread extends Thread {

        private Socket socket;
        private ObjectOutputStream out;

        @SuppressWarnings("resource")
        public void run() {

            if (isServer()) {
                try {
                    ServerSocket server = new ServerSocket(5555);
                    while (true) {
                        ClientThread t1 = new ClientThread(server.accept(), game.getNumClients(), getName());
                        ct.add(t1);
                        t1.start();
                        Player p = new Player(t1, "Player" + game.getNumClients() + 1);
                        game.addPlayer(p);
                        game.setNumClients();
                        Thread.sleep(1000);
                        int index = ct.indexOf(t1);
                        System.out.println(game.players.get(index).name + " - " + p.name);
                        send("sp: " + p.name, index);
                        send("pi: " + index, index);
                        if (ct != null && !ct.isEmpty()) {
                            sendAll("RESET");
                            for (int i = 0; i < ct.size(); i++) {
                                if (!p.name.equals(game.players.get(i).name)) {
//                                    sendAll("pi: " + i)
                                    sendAll("pn: " + game.players.get(i).name + " " + i);
                                } else {
                                    sendAllExceptMe("pn: " + game.players.get(i).name + " " + i, index);
                                }

                            }
                        }
                        //players.add("Player");//
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    callback.accept("Connection Closed");
                }
            }
        }
    }

    class ClientThread extends Thread {

        Socket s;
        int number;
        String name;
        ObjectOutputStream tout;
        ObjectInputStream tin;

        public ClientThread(Socket socket, int num, String player) {
            this.s = socket;
            this.number = num;
            this.name = "Player " + num;
        }

        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                s.setTcpNoDelay(true);
                this.tout = out;
                this.tin = in;

                while (true) {

                    Serializable data = (Serializable) in.readObject();
                    System.out.println("Num Clients: " + game.getNumClients());
                    System.out.println("Data " + data + "    Player " + number);
                    Thread.sleep(1000);

                    if (game.getNumClients() >= 2) {
                        try {
                            if (data.toString().startsWith("INVITE")) {
                                String stringData = data.toString();
                                send("INVITE " + game.players.get(number).name + " wants to play with you. Select him and get started.", Integer.parseInt(stringData.split(" ")[1]));
                            } else if (data.toString().startsWith("EXIT")) {
                                String stringData = data.toString();
                                game.players.remove(Integer.parseInt(stringData.split(" ")[1]));
                                for (int i = 0; i < ct.size(); i++) {

                                    sendAll("pn: " + game.players.get(i).name + " " + i);
                                }

                            } else if (data.toString().startsWith("MOV")) {
                                String move = data.toString().split(" ")[1];
                                int opponent = Integer.parseInt(data.toString().split(" ")[2]);
                                game.players.get(number).setPlay(move);
                                if (game.players.get(number).play != null && game.players.get(opponent).play != null) {
                                    send("Round Winner: " + game.getRWinner(game.players.get(number).play, game.players.get(opponent).play) + "\n", number);
                                    send("Round Winner: " + game.getRWinner(game.players.get(number).play, game.players.get(opponent).play) + "\n", opponent);
//                            sendAll("Round Winner: " + game.getRWinner(game.players.get(0).play, game.players.get(1).play) + "\n");
                                    game.players.get(number).setPlay(null);
                                    game.players.get(opponent).setPlay(null);
                                    send("ready", opponent);
                                    send("ready", number);
                                }
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    } else {
                        tout.writeObject("Waiting for another player to join");
                    }
                }
            } catch (Exception e) {
                callback.accept("Connection Closed");
                try {
                    sendAll("EXIT "+name);
                } catch (Exception ex) {
                    Logger.getLogger(NetworkConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
