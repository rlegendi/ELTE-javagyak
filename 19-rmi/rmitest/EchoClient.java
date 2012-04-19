import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class EchoClient {
	public static void main(final String[] args)
			throws MalformedURLException, RemoteException, NotBoundException {
		final IEchoRemote remote = (IEchoRemote) Naming.lookup( EchoServer.ADDRESS );
		System.out.println( remote.hi() );
	}
}
