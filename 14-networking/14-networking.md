# Networking #
Elméletben *OSI* modell (*"All People Seem To Need Data Processing"* vagy
*"Please Do Not Throw Salami Pizza Away"*), gyakorlatban TCP/IP:

* Alkalmazás (SMTP, Telnet, FTP, etc.),
* Transzport (TCP vagy UDP),
* Hálózati (IPv4, IPv6),
* Adatkapcsolati, Fizikai rétegek (kommunikáció)

Szolgáltatási pont definiálásához három dolog kell:

* Internet cím (IPv4 vagy DNS által feloldva)
* Protokoll azonosító (TCP v. UDP)
* Port (lehet TCP, UDP is, a címtartományok függetlenek (!), 16 bites egész
  érték 0-65535)

Java esetén a `java.net.*` csomag használható.

## Összeköttetés-alapú kapcsolat ##
Kliens-szerver modell, a szerver általában:

* Lefoglal egy TCP portot
* Várakozik egy kliens kapcsolódására
* Ha kliens jelzi a kapcsolódási szándékát, felveszi vele a kapcsolatot, és
  kiszolgálja
* Folytatja a 2. ponttól

A kliens működése általában:

* Lefoglal egy TCP portot, ezen keresztül kommunikál a szerverrel
* Kapcsolódik a másik végponton a szerverhez, azon a porton, amelyet az
  közzétett
* Lezajlik a kommunikáció
* A kliens bontja a kapcsolatot a szerverrel

> **Megjegyzések**
> 
> * a kliens lokális portját nem kell ismernünk
> * a kapcsolat kiépítése után az full-duplex (kétirányú)
> * a szerver szinte mindig többszálú (ld. következő óra!)

## Összeköttetés-mentes kapcsolat ##
Majd előadáson ;]

## Címek kezelése ##
Hasznunkra válik az `InetAddress` osztály:

* `getByName(String)`: név &rarr; cím
* `getByAddress(byte[]):` IPv4/6 &rarr; cím
* `getAllByName()`: név &rarr; címek (attól függ, hogy van behegesztve a NS)
* `getLocalHost()`: &rarr; saját cím

> **Részletesen** <http://download.oracle.com/javase/6/docs/api/java/net/InetAddress.html>

## Példa ##

### Szerveralkalmazás ###
	package networking;
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.ServerSocket;
	import java.net.Socket;
	
	public class SimpleServer {
	    public static void main(final String[] args) throws IOException {
	        final int port = Integer.parseInt(args[0]);
	        final ServerSocket server = new ServerSocket(port);
	        
	        Socket client = null;
	        
	        while (true) {
	            client = server.accept();
	            final BufferedReader br = new BufferedReader(
	                new InputStreamReader(client.getInputStream()));
	            final PrintWriter pw = new PrintWriter(client.getOutputStream());
	            
	            final String line = br.readLine();
	            System.out.println("Got message: " + line);
	            
	            final String ret = new StringBuilder(line).reverse().toString();
	            System.out.println("Sending reply: " + ret);
	            
	            pw.println( ret );
	            pw.flush();
	            
	            client.close();
	            
	            if (line.equals("exit")) {
	                break;
	            }
	        }
	        
	        server.close();
	    }
	}

#### Használat ####
	$ java SimpleServer 5000

### Kliens alkalmazás ###
	package networking;
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.Socket;
	import java.net.UnknownHostException;
	
	public class SimpleClient {
	    public static void main(String[] args)
	            throws UnknownHostException, IOException {
	        String host = args[0];
	        int port = Integer.parseInt(args[1]);
	        String value = args[2];
	        
	        Socket socket = new Socket(host, port);
	        
	        BufferedReader br = new BufferedReader(
	            new InputStreamReader(socket.getInputStream()));
	        PrintWriter pw = new PrintWriter(socket.getOutputStream());
	        
	        System.out.println("Message: " + value);
	        pw.println(value);
	        pw.flush();
	        
	        System.out.println( "Response:" + br.readLine() );
	        socket.close();
	    }
	}

### Használat ###
	$ java SimpleClient localhost 5000 mentegetnem
	$ java SimpleClient localhost 5000 ideseggesedi
	$ java SimpleClient localhost 5000 exit

Palindromok, haha <http://hu.wikipedia.org/wiki/Magyar_nyelvű_palindromok_listája>

## Internet és a WWW-objektumok elérése ##
Socketek helyett `[Http]URLConnection` osztály használatával.

## Hasznos segédeszközök ##
`ping`, `netstat`, `netcat`, `telnet`, `tcpdump`, etc. 

## Feladatok ##

### Konzolos HTTP kliens ###
Készítsünk egy egyszerű alkalmazást, amely egy megadott HTML oldalt megjelenít a
konzolon. Ennek szabványos kommunikációja így zajlik:

	$ telnet index.hu 80
	Trying 217.20.130.97...
	Connected to index.hu.
	Escape character is '^]'.
	GET / HTTP/1.1
	Host: index.hu

	$

> **Megjegyzés** A végén két új sor karakter van!

### Chat alkalmazás ###
Készítsünk egy közös chat alkalmazást! Ti írjátok a kliens programot, amely
csatlakozik a `pandora.inf.elte.hu` szerver `5000`-es portjára. A program
parancssori argumentuma a felhasználó választott nickneve (ez csak alfanumerikus
karaktereket tartalmazhat).

* Indulás után építsen ki egy standard TCP kapcsolatot, majd küldje el a
szervernek a `"NICK nick"` üzenetet. 
* Ezután a felhasználó megkapja a jelenleg chatelő nickneveket (`;` karakterrel
elválasztva, egyetlen sorban).
* Ha új felhasználó lép a chatszobába, minden más felhasználó kap egy
`"JOIN nick"` üzenetet.
* Ha valamely felhasználó bontja a kapcsolatot, minden más felhasználó kap egy
`"QUIT nick"` üzenetet.
* Ha a szervert valamely okból leállítják, minden felhasználó kap egy
`"DISCONNECT message"` üzenetet.
* Ha valaki üzenetet küld, akkor minden más felhasználó kap egy `"MSG message"`
üzenetet.
* A szerver minden üzenetre visszajelez: `"OK uzenet"`, vagy `"ERR hibauzenet"`
formában (pl. az adott nicknév ár foglalt, etc.).
* A fenti programhoz készíts egy tetszőleges vezérlő felületet (lehet konzolos,
lehet grafikus).

### Port scanner ###
Készítsetek egy minimális port scanner alkalmazást! A programnak egyetlen
parancssori argumentuma legyen, a tesztelendő host címe. Ezután a program az
`[1..1024]` intervallumban (ezek az ún. *well known* portok) tesztelje végig,
hogy hol fogad kapcsolatot az adott gép (próbáljon meg egy TCP socket
kapcsolatot létrehozni). Amennyiben ez sikeres, jelezzük a felhasználónak, majd
írjuk ki, hogy milyen szolgáltatás fut valószínűleg a gépen. Localhoston
teszteljétek. A host azonosításához használjátok az `InetAddress` osztályt!

Egy lista, amely tartalmazza a szolgáltatások neveit: <http://www.iana.org/assignments/port-numbers>

### Tetszőleges protokoll megvalósítása ###
Válassz ki egy tetszőleges alkalmazási protokollt (HTTP, SMTP, FTP, IRC, stb.).
Készíts egy minimális kliens alkalmazást hozzá, amely egyszerű funkciók
ellátására képes. Nézz utána, hogy működik az adott protokoll, majd próbáld meg
`telnet` segítségével használni. Ha sikerül, írj a kisebb funkciókra egy
programot.

> **Tipp** Google &rarr; FTP over telnet
