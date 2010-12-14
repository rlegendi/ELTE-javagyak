\section*{Networking}
Elméletben OSI modell ("All People Seem To Need Data Processing" v. "Please Do Not Throw Salami Pizza Away"), gyakorlatban TCP/IP:

\begin{itemize}
\item Alkalmazás (SMTP, Telnet, FTP, etc.),
\item Transzport (TCP vagy UDP),
\item Hálózati (IPv4, IPv6),
\item Adatkapcsolati, Fizikai rétegek (kommunikáció)
\end{itemize}

Szolgáltatási pont definiálásához három dolog kell:

\begin{itemize}
\item Internet cím (IPv4 vagy DNS által feloldva)
\item Protokoll azonosító (TCP v. UDP)
\item Port (lehet TCP, UDP is, a címtartományok függetlenek (!), 16 bites egész érték 0-65535)
\end{itemize}

Java esetén a \texttt{java.net.*} csomag használható.

\subsection*{Összeköttetés-alapú kapcsolat}
Kliens-szerver modell, a szerver általában:

\begin{enumerate}
\item Lefoglal egy TCP portot
\item Várakozik egy kliens kapcsolódására
\item Ha kliens jelzi a kapcsolódási szándékát, felveszi vele a kapcsolatot, és kiszolgálja
\item Folytatja a 2. ponttól
\end{enumerate}

A kliens működése általában:

\begin{enumerate}
\item Lefoglal egy TCP portot, ezen keresztül kommunikál a szerverrel
\item Kapcsolódik a másik végponton a szerverhez, azon a porton, amelyet az kozzétett
\item Lezajlik a kommunikáció
\item A kliens bontja a kapcsolatot a szerverrel
\end{enumerate}

\textbf{Megjegyzések}
\begin{itemize}
\item a kliens lokális portját nem kell ismernünk
\item a kapcsolat kiépítése után az full-duplex (kétirányú)
\item a szerver szinte mindig többszálú (ld. következő óra!)
\end{itemize}

\subsection*{Összeköttetés-mentes kapcsolat}
Majd előadáson ;]

\subsection*{Címek kezelése}
Hasznunkra válik az \texttt{InetAddress} osztály:

\begin{itemize}
\item getByName(String) név -> cím
\item getByAddress(byte[]) IPv4/6 -> cím
\item getAllByName() név -> címek (attól függ, hogy van beheggesztve a NS)
\item getLocalHost() -> saját cím
\end{itemize}

\textbf{Részletesen:} \url{http://java.sun.com/javase/6/docs/api/java/net/InetAddress.html}

\subsection*{Példa}

\subsubsection*{Szerveralkalmazás}

\begin{verbatim}
package gyak8;

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
\end{verbatim}

\subsubsection*{Használat}
\begin{verbatim}
> java SimpleServer 5000
\end{verbatim}

\subsubsection*{Kliens alkalmazás}
\begin{verbatim}
package gyak8;

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
\end{verbatim}

\subsubsection*{Használat:}
\begin{verbatim}
> java SimpleClient localhost 5000 mentegetnem
> java SimpleClient localhost 5000 ideseggesedi
> java SimpleClient localhost 5000 exit
\end{verbatim}

\textbf{Sauce:} \url{http://hu.wikipedia.org/wiki/Magyar_nyelvű_palindromok_listája}

\subsection*{Internet és a WWW-objektumok elérése}
Socket helyett [Http]URLConnection használatával.

\section*{Hasznos segédeszközök}
ping, netstat, netcat, telnet, tcpdump, etc. 

\subsection*{Feladat}
Készítsünk egy egyszerű alkalmazást, amely egy megadott HTML oldalt megjelenít a konzolon. Ennek szabványos kommunikációja így zajlik:

\begin{verbatim}
> telnet index.hu 80
Trying 217.20.130.97...
Connected to index.hu.
Escape character is '^]'.
GET / HTTP/1.1
Host: index.hu

\end{verbatim}

\textbf{Megjegyzés} A végén két újsor karakter van!

\subsection*{Feladat}
Készítsünk egy közös chat alkalmazást! Ti írjátok a kliens programot, amely csatlakozik a pandora.inf.elte.hu szerver 5000-es portjára.

A program parancssori argumentuma a felhasználó választott nickneve (ez csak alfanumerikus karaktereket tartalmazhat).

Indulás után építsen ki egy standard TCP kapcsolatot, majd küldje el a szervernek a "NICK nick" üzenetet. 

Ezután a felhasználó megkapja a jelenleg chatelő nickneveket (; karakterrel elválasztva, egyetlen sorban).

Ha új felhasználó lép a chatszobába, minden más felhasználó kap egy "JOIN nick" üzenetet.

Ha valamely felhasználó bontja a kapcsolatot, minden más felhasználó kap egy "QUIT nick" üzenetet.

Ha a szervert valamely okból leállítják, minden felhasználó kap egy "DISCONNECT uzenet" üzenetet.

Ha valaki üzenetet küld, akkor minden más felhasználó kap egy "MSG uzenet" üzenetet.

A szerver minden üzenetre visszajelez: "OK uzenet", vagy "ERR hibauzenet" formában (pl. az adott nicknév ár foglalt, etc.).

A fenti programhoz készíts egy tetszőleges vezérlő felületet (lehet konzolos, lehet grafikus).

\newpage
\section*{+/- Feladat}
A feladatokat a \url{legendi@inf.elte.hu} címre küldjétek, \textbf{következő szombat éjfélig} (2010. április 13., a szünetre való tekintettel)! A subject a következőképp nézzen ki:

\begin{verbatim}
csop<csoportszám>_gyak<gyakorlat száma>_<EHA-kód>_<a megoldott feladatok száma>
\end{verbatim}

Például:

\begin{verbatim}
csop1_gyak7_LERIAAT_1
csop1_gyak7_LERIAAT_12
\end{verbatim}

Mellékelni csak a Java forrásfájlokat mellékeljétek (semmiképp ne teljes Eclipse/NetBeans projecteket), esetleg rarolva, zipelve, és egy levelet küldjetek (több levél esetén az utolsó mellékleteit értékelem)! További fontos kritériumok:
\begin{itemize}
\item A konvenciókra figyeljetek plz!
\item Ékezetes karaktereket \textbf{ne} használjatok! Főleg azonosítók esetében ne! A Java ugyan ezt megengedi, ugyanakkor a különböző környezetekbe való konvertáláskor ($latin2 \leftrightarrow UTF-8 \leftrightarrow Cp1250$) összetörnek a karakterek! Az ilyen forrásokat fordítani, következésképp értékelni sem tudom.
\end{itemize}

\subsection*{1. Feladat}
Készítsetek egy minimális port scanner alkalmazást! A programnak 1 parancssori argumentuma legyen, a tesztelendő host címe. Ezután a program az [1..1024] intervallumban (ezek az ún. \emph{well known} portok) tesztelje végig, hogy hol fogad kapcsolatot az adott gép (próbáljon meg egy TCP socket kapcsolatot létrehozni). Amennyiben ez sikeres, jelezzük a felhasználónak, majd írjuk ki, hogy milyen szolgáltatás fut valószínűleg a gépen. Localhoston teszteljétek. A host azonosításához használjátok az \texttt{InetAddress} osztályt!

\bigskip
\noindent
Egy lista, amely tartalmazza a szolgáltatások neveit:\\
\url{http://www.iana.org/assignments/port-numbers}

\subsection*{2. Feladat}
Válassz ki egy tetszőleges alkalmazási protokollt (HTTP, SMTP, FTP, IRC, etc.). Készíts egy minimális kliens alkalmazást hozzá, amely egyszerű funkciók ellátására képes. Nézz utána, hogy működik az adott protokoll, majd próbáld meg telnet segítségével használni. Ha sikerül, írj a kisebb funkciókra egy programot. \textbf{Tipp:} Google $\rightarrow$ FTP over telnet

\end{document}
