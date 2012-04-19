import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EchoServer
    extends UnicastRemoteObject
    implements IEchoRemote {
	private static final long serialVersionUID = 1L;
	public static final String ADDRESS = "rmi://localhost:1099/echo";

    protected EchoServer() throws RemoteException {
        super();
    }

    @Override
    public String hi() throws RemoteException {
        return "Hoi!";
    }

    public static void main(final String[] args)
        throws RemoteException, MalformedURLException {
        if (null == System.getSecurityManager() ) {
            System.setSecurityManager(new RMISecurityManager());
        }

        final EchoServer server = new EchoServer();
        Naming.rebind(ADDRESS, server);
    }
}
