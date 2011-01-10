# Remote Method Invocation #
Hálózatkezelés: eddig TCP/UDP és kliens-szerver architektúra. Ezt viszont sajnos
sok tényező nehezíti:

* Ha már egy létező alkalmazást kell felkészíteni hálózati kommunikációra, ahhoz
  az egészet újra részekre kell bontani (refactor)
* Halom boilerplate kódot kell írni: a teljes hálózati protokollt újra kell
  implementálni az adatátvitelhez, ez elég error-prone feladat.

Megoldás: távoli eljáráshívás. Ez egy magasabb szintű absztrakciós eszköz,
lehetővé teszi a procedurális programtervezést: távoli számítógépen lévő
függvényeket tudunk hívni (ez persze mindenféle problémákkal járhat). A hívó
folyamat felveszi a kapcsolatot a kiszolgáló számítógéppel, becsomagolva elküldi
az adatokat, és felfüggesztődik, míg azokat vissza nem kapja.

Javaban ez Remote Method Invocation (RMI, v.ö. RPC) néven fut, hogy illeszkedjen
a nyelv OO szemléletéhez (vannak magasabb szintű frameworkok, v.ö. CORBA).
Segítségével egy távoli gépen lévő JVM objektumot tudok megszólítani.

## Modell ##
A távoli objektumoknak egy távoli interfészt kell megvalósítaniuk
(`java.rmi.Remote`). Ehhez minden konstruktorának, és minden függvényének
deklaráltan dobnia kell a `java.rmi.RemoteException` kivételt. Ami ebben az
interfészben nincs definiálva, az a kliensek felé *nem fog látszani*.

	public interface IEchoRemote extends Remote {
	    public abstract String hi() throws RemoteException;
	}

## Paraméter átadás ##
Szerializáció segítségével minden. Az elemi adattípusok JVM-ben történő
ábrázolása rögzített (a típuskonstrukciókkal együtt), így a konverziókkal nem
kell foglalkozni (v.ö. C). A szerializált objektum egy bájtsorozatként
socketeken átmegy, a visszatérési érték hasonlóan jön vissza.

## A kliens és szerver összekapcsolása ##
Alapvetően két módszer lehetséges:

* "Bedrótozzuk" a szerver címét
* Használunk egy telefonkönyv-szerű szerverprogramot (*registry*). Ha a szerver
  elindul, bejegyzi magát az elérhetőségével, a kliensek pedig innen kérdezhetik
  azt le. Ez hasznos lehet még, ha pl. terhelésmegosztást (*load balancing*)
  szeretnénk megvalósítani.

A `java.rmi.Naming` osztály műveletei használatának segítségével (`lookup()`,
`bind`, etc.).

## Felmerülő problémák ##
A hálózati fejlesztés így ugyanolyan elvek alapján történhet, mint egy sima
desktop alkalmazás fejlesztése, ugyanolyan szintaxissal. Mégis, felmerülhetnek
speciális problémák:

* A hálózati adatok elveszhetnek
* A hálózati végpontok leállhatnak
* Az adatátviteli vonal elromolhat

Probléma: nem tudhatjuk, hogy meddig jutott el a feldolgozásban a szerver, ha
nem kapunk tőle választ. Az ilyen esetekre külön fel kell készülni (pl.
nyugtázással). Elv: *idempotens műveletek* (ne legyen káros mellékhatása).

## A távoli objektum implementációja ##
Származtassunk a `java.rmi.server.UnicastRemoteObject` osztályt, ez elérhetővé
teszi az osztályunkat (megjegyzés: ez nem kötelező, de akkor kézzel kell mindent
csinálni, pl. `toString()`, `hashCode()`, `equals()` megfelelő implementálása). 

	import java.io.File;
	import java.net.MalformedURLException;
	import java.rmi.AlreadyBoundException;
	import java.rmi.Naming;
	import java.rmi.RMISecurityManager;
	import java.rmi.RemoteException;
	import java.rmi.server.UnicastRemoteObject;
	
	public class EchoServer
	        extends UnicastRemoteObject
	        implements IEchoRemote {
	    public static final String ADDRESS = "rmi://localhost:1099/echo";
	
	    protected EchoServer() throws RemoteException {
	        super();
	    }
	
	    @Override
	    public void hi() throws RemoteException {
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

## A kliens implementációja ##
	public class EchoClient {
	    public static void main(final String[] args)
	            throws MalformedURLException,
	                   RemoteException,
	                   NotBoundException {
	        final IEchoRemote remote = (IEchoRemote)
	                Naming.lookup(EchoServer.ADDRESS);
	        System.out.println( remote.hi() );
	    }
	}

## Futtatás ##
1. El kell indítani egy registry szolgáltatást a szerver gépen:
	
		::Windows
		start rmiregistry 
		
		# Unix
		rmiregistry &

2. Le kell fordítani a kliens és szerveralkalmazást
3. Hozzuk létre a következő policy fájlt:

		grant {
		    permission java.security.AllPermission;
		};

4. Futtatni kell a szervert, a következő paraméterekkel:

		-Djava.security.policy=server.policy
		-Djava.rmi.server.codebase=file://<path_a_classfile_okhoz>

5. Futtassuk a klienst!

## Feladatok ##
* Készítsetek egy egyszerű hálózati naplózó alkalmazást! A szerver tudjon
  lementeni sztringeket egy dedikált fájlba, és a tartalmát is le lehessen
  kérdezni a kliensekből! A szerver szolgáltatásait RMI segítségével érd el!

* Készíts egy egyszerű számológép szervert. Tudjon összeadni, kivonni, osztani,
  szorozni. A műveleteit RMI segítségével érd el!

	> **Részletesen** <http://java.sun.com/developer/onlineTraining/rmi/RMI.html>

* Készítsetek egy alkalmazást, amely kilistázza a megadott registry szerver
  összes szolgáltatását!

* A szerializációval kapcsolatos gyakorlaton létrehozott `Circle` osztályt
  adjátok át paraméterként egy RMI szervernek, amely legyen képes visszaadni
  annak területét!

* Készítsetek egy *remote shell* alkalmazást! A kliens egy interaktív konzolos
  alkalmazás legyen. Ha a felhasználó beír egy parancsot, azt küldje el a
  szervernek, az hajtsa végre, és az outputot küldje vissza a kliensnek (a
  végrehajtáshoz használd a `Runtime.exec(String cmd)` függvényt)!
