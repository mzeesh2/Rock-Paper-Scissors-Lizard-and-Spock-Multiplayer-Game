
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class ServerGUI extends Application {

    private NetworkConnectionServer conn;
    String message = null;
    private TextArea messages = new TextArea();
    Button serverOn, serverOff;
    Text clientNo, playerMove, playerPoints, gameWin, playAgain;
    Stage myStage;
    Scene scene;
    HashMap< String, Scene> sceneMap;

    private ServerLogic createServer(int portNo) {
        return new ServerLogic(data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
                message = data.toString();
                try {
                    String[] dataList = message.trim().split(" ");
                    System.out.println("Server UI : "+message);
                    if(message.trim().startsWith("MOV") ){
                        System.out.println("got a new move");
                        int opponentIndex = Integer.parseInt(dataList[2]);
                        conn.send(dataList[1]+" "+dataList[3], opponentIndex);
                    }else{
                        conn.sendAll(message);
                    }
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Rock Paper Scissor Lizard Spock Server");
        myStage = primaryStage;
        serverOn = new Button("Server On");
        serverOff = new Button("Server Off");
        messages.setPrefHeight(550);
        sceneMap = new HashMap< String, Scene>();

        serverOn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (conn == null) {
                    conn = createServer(5555);
                }
                try {
                    conn.startConn();
                } catch (Exception e) {
                    System.out.println("Connection not established");
                }

                if (!sceneMap.containsKey("server")) {
                    sceneMap.put("server", new Scene(new VBox(serverOff, messages), 600, 600));
                }
                myStage.setScene(sceneMap.get("server"));
            }
        });

        serverOff.setOnAction(new EventHandler< ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage) serverOff.getScene().getWindow();
                stage.close();
            }
        });

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));
        VBox paneCenter = new VBox(10, serverOn);
        pane.setCenter(paneCenter);
        scene = new Scene(pane, 400, 500);
        sceneMap.put("welcome", scene);
        primaryStage.setScene(sceneMap.get("welcome"));
        primaryStage.show();
    }
}
