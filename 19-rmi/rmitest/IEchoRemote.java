import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEchoRemote extends Remote {
    public abstract String hi() throws RemoteException;
}
