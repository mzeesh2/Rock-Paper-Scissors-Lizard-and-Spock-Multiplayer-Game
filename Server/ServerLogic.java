
import java.io.Serializable;
import java.util.function.Consumer;

public class ServerLogic extends NetworkConnectionServer {

    private int port;

    public ServerLogic(Consumer<Serializable> callback) {
        super(callback);
        // TODO Auto-generated constructor stub
        this.port = 5555;
    }

    @Override
    protected boolean isServer() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected String getIP() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int getPort() {
        // TODO Auto-generated method stub
        return port;
    }
}
