
import java.io.Serializable;
import java.util.function.Consumer;

public class ClientLogic extends NetworkConnectionClient {

    private String ip;
    private int port;

    public ClientLogic(Consumer<Serializable> callback) {
        super(callback);
        this.ip = "localhost";
        this.port = 5555;
    }

    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        // TODO Auto-generated method stub
        return this.ip;
    }

    @Override
    protected int getPort() {
        // TODO Auto-generated method stub
        return this.port;
    }

    @Override
    protected boolean isClient() {
        // TODO Auto-generated method stub
        return false;
    }
}
