# Threading #
Mottó: *"Concurrency is hard and boring. Unfortunately, my favoured technique of ignoring it and hoping it will go away doesn't look like it's going to bear fruit."*

Párhuzamosság: több részfeladat egyidejűleg történő végrehajtása.

Miért?

* A feladat logikai szerkezete
* A program több, fizikailag is független eszközön fut
* Hatékonyság (v.ö. Amdahl's law <http://en.wikipedia.org/wiki/Amdahl's_law>)

Elég régóta foglalkoztatja az embereket. *Látszat párhuzamosságról* is hallani
még (oprendszerek, multitasking: egyszerre egy folyamatot hajt végre, de adott
időtartam alatt akár többet is), de a *valódi párhuzamosság* is már mindennapos
(pl. többmagos, többprocesszoros gépekben).

## Párhuzamosság szintjei ##
* Utasítások
* Taskok
* Folyamatok (processes)
* **Szálak (threads)**

Viselkedésük alapján lehetnek:

* Függetlenek
* Versengők
* Együttműködők

## Alapproblémák ##

* Kommunikáció: kommunikációs közeg: socket, signal handler, fájl, osztott
  memória, etc.
* Szinkronizáció: folyamatok összehangolása, szinkron - aszinkron

## Alapdefiníciók ##

* **Szinkronizáció** Olyan folyamat, amellyel meghatározható a folyamatokban
  szereplő utasítások relatív sorrendje
* **Kölcsönös kizárás** osztott változók biztonságos használatához
* **Kritikus szakasz** program azon része, ahol egy időben csak egyetlen
  folyamat tartózkodhat
* **Atomi művelet** bármilyen közbeeső állapota nem látható a többi folyamat
  számára

Miért kell ez az egész? Pl. `x++`, 64 bites JVM , `long`-on ábrázolva 2
regiszterben van tárolva &rarr; 2 olvasás + 2 írás

\subsection*{Szálak létrehozása}
Két lehetőség:
\begin{itemize}
\item \texttt{Thread} osztályból származtatva: a run() metódust kell felüldefiniálni, majd a start() segítségével indítható az új szál. Megjegyzés: start() függvényt \textbf{nem bántod}, csak ha hívod a super.start() függvényt is! Példa:

\begin{verbatim}
package gyak10;

class TestThread extends Thread {
    @Override
    public void run() {
        System.out.println("TestThread");
    }
}

public class Create1 {
    public static void main(String[] args) {
        TestThread test = new TestThread();
        test.start();
    }
}
\end{verbatim}

Névtelen osztállyal ugyanez:
\begin{verbatim}
new Thread() {
    @Override
    public void run() {
        System.out.println("TestThread");
    }
}.start();
\end{verbatim}

\item \texttt{Runnable} interfész implementálása: ha a származtatás nem lehetséges (pl. a fő osztály egy JFrame, Applet, etc.). Egyetlen függvénye van: run(), melyet meg kell valósítani. Indítani úgy lehet, ha egy Thread objektumnak megadod paraméterként, és arra meghívjuk a start() eljárást:

\begin{verbatim}
package gyak10;

class TestRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("TestRunnable");
    }
}

public class Create2 {
    public static void main(String[] args) {
        Thread thread = new Thread( new TestRunnable() );
        thread.start();
    }
}
\end{verbatim}

Ugyanez névtelen osztállyal:

\begin{verbatim}
new Thread( new Runnable() {
    @Override
    public void run() {
        System.out.println("TestRunnable");
    }
}).start();
\end{verbatim}

\end{itemize}

\subsection*{Szálak függvényei}

\begin{itemize}
\item start(): indítás
\item stop(): megállítás (deprecated). Megjegyzés: utána érdemes a Thread referenciát null értékre állítani.
\item suspend(), resume(): felfüggesztés, majd újraindítás (deprecated)
\item join(): másik szál befejezésének megvárása
\item sleep( <ms> ): adott időnyi várakozás
\item yield(): well, ehh...
\item getName(): konstruktorban beállítható név lekérdezése (később már nem változtatható)
\item getThreadGroup(): konstruktorban beállítható csoport (később már nem változtatható). Egyszerre egyhez tartozhat, hierarchiába szervezhető (egy csoport más csoportokat is tartalmazhat).
\item setDaemon(): daemon szál készítése (akkor terminál, ha minden más, nem daemon szál is már terminált).
\item setPriority( <prior> ): prior lehet 1-10, fontosságot jelöl. OS függő, hogy pontosan milyen hatása van. Időosztásos (\emph{time slicing}) rendszereken nincs gond vele, egyébként egy "önző" szál teljesen befoglalhatja a CPU-t.
\end{itemize}

\noindent
\bigskip
\textbf{Részletesen}\\
\url{http://java.sun.com/javase/6/docs/api/java/lang/Thread.html}

\bigskip
\noindent
\textbf{Megjegyzés} Sok deprecated függvény, mert könnyen deadlockhoz vezethetnek (pl. erőforrás lefoglalásának megsznüntetése). Mindig van kerülőút, pl. szál leállítására:

\begin{verbatim}
private volatile isRunning = true;

public void stopRunning() {
    isRunning = false;
}

@Override
public void run() {
    while ( isRunning ) { ... }
}
\end{verbatim}

\subsection*{Felmerülő problémák}

\begin{description}
\item Azon túl, hogy megbízhatóság \dots
\item[Holtpont] kölcsönösen egymásra várakoznak a folyamatok, és egyik sem tud tovább haladni
\item[Kiéheztetés] több folyamat azonos erőforrást használ, és valamelyik ritkán fér csak hozzá
\item[Versenyhelyzetek] amikor egy számítás helyessége függ a végrehajtó folyamatok sorrendjétől (pl. \emph check-then-act blokkok)
\item[Nemdetirminisztikus végrehajtás] kétszer ugyanazt a viselkedést produkálni lehetetlen, debuggolás esélytelen
\end{description}

A szinkronizációt ezen problémák elkerülésével kell megoldani.

\subsection*{Kölcsönös kizárás}
Javaban ún. \emph{szinkronizációs burok} van:

\begin{verbatim}
synchronized ( resource ) {
    ...
}
\end{verbatim}

Ez garantálja, hogy az azonos lockhoz tartozó blokkokban egyszerre egy szál lehet csak (gond - kódblokkot védünk, nem erőforrást). A \texttt{synchronized} használható példány-, és osztályfüggvény  módosítószavaként, ekkor a jelentése:

\begin{verbatim}
public synchronized void f() {
    ...
}

// Ekvivalens:
public void f() {
    synchronized ( this )  {
        ...
    }
}
\end{verbatim}

Illetve osztályfüggvények esetén:

\begin{verbatim}
class MyClass {
    public static synchronized void s() {
        ....
    }
    
    // Ekvivalens:
    public static void s() {
        synchronized ( MyClass.class ) {
            ...
        }
    }
}
\end{verbatim}

\bigskip
\noindent
\textbf{Megjegyzés} ha csak egy szál változtathat egy változót, a többi csak olvassa, akkor jöhet jól a \texttt{volatile} kulcsszó, amely garantálja, hogy a szálak nem cache-elik az adott változó értékét, mindig a frissítik (ld. a stop() kiváltására írt példát feljebb!).

\bigskip
\noindent
\textbf{Megjegyzés} Immutable osztályokhoz nem kell szinkronizálni!


\subsection*{Szinkronizáció üzenetekkel}
Feltételes beváráshoz: \textit{Object} osztályban definiált \textit{wait()}, \textit{notify()} és \textit{notifyAll()} függvények. A wait hívásának hatására a szál elengedi a lockot és blokkolódik, amíg egy másik szál nem jelzi számára, hogy az adott feltétel teljesül (notify()).

Használatához \emph{mindig} egy monitor szükséges, különben futásidejű hibát kapunk!

\begin{verbatim}
synchronized (monitor) {
    monitor.wait();
}

synchronized (monitor) {
    monitor.notify();
}
\end{verbatim}

\subsubsection*{Deadlockra példa}
\begin{verbatim}
package gyak10;

public class Deadlock {
    public static void main(String[] args) {
        final Object res1 = new Object();
        final Object res2 = new Object();
        
        new Thread() {
            @Override
            public void run() {
                synchronized (res1) {
                    System.out.println("1 - Got res1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (res2) {
                        System.out.println("1 - Got res2");
                    }

                }
            }
        }.start();
        
        new Thread() {
            @Override
            public void run() {
                synchronized (res2) {
                    System.out.println("2 - Got res2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (res1) {
                        System.out.println("2 - Got res1");
                    }

                }
            }
        }.start();
    }
}
\end{verbatim}

\subsection*{Szállak állapotai}

Ld. \ref{fig:threadStates} ábra.

\begin{figure}[ht]
\centering
\includegraphics[width=\textwidth]{threads}
\caption{Szállak állapotai}
\label{fig:threadStates}
\end{figure}

\subsection*{Kollekciók}

Szinkronizált vs. nem szinkronizált adatszerkezetek (pl. Vector vs. ArrayList). Az iterátorok \emph{fail-fast} iterátorok: ha bejárás közben módosítják az adatszerkezetet, reccsen egy
\texttt{java.util.ConcurrentModificationException} kivétellel:

\begin{verbatim}
package gyak10;

import java.util.ArrayList;

public class FailFast {
    public static void main(final String[] args)
            throws InterruptedException {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i=0; i<100; ++i) list.add("" + i);
        
        final Thread reader = new Thread() {
            @Override
            public void run() {
                try {
                    for (final String act : list) {
                        System.out.println(act);
                            Thread.sleep(100);
                    }
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        
        reader.start();
        Thread.sleep(500);
        
        list.remove(50);
    }
}
\end{verbatim}

Szinkronizált adatszerkezetek készítése wrapperekkel, példa listára, másra hasonlóan:

\begin{verbatim}
final List<T> list = Collections.synchronizedList(new ArrayList<T>(...));
\end{verbatim}

\subsection*{Feladatok}
\begin{enumerate}
\item Készíts egy 2 szállal működő programot, amelyek neve térjen el! A szálak tízszer egymás után írják ki a képernyőre a nevüket, majd várjanak egy keveset (0-5 másodpercet, véletlenszerűen).
\item Készíts 5 szálat, amelyek a következő prioritás-szintekkel futnak: 3, 4, 5, 6, 7 (ez szerepeljen a szálak nevében is!). A szálak egy végtelen ciklusban írják ki a nevüket. Elemezd az eredményt!
\item Készíts egy 5 szállal dolgozó programot, amelyek ugyanazt a közös változót kiírják, majd csökkentik (100-ról 0-ra). Figyelj a szinkronizációra, és a végén ellenőrizd le, hogy valóban helyes outputot kaptál-e!
\item Készíts egy 3 szálú alkalmazást! Legyen egy termelő, és két fogyasztó szálunk. Az termelő szál induljon el, és töltsön fel egy kollekciót 10 db véletlen számmal (a másik két szál indulás után várjon)! Ezután jelezzen a másik két szálnak (\texttt{wait(), notify()}), hogy elkezdhetik a számok feldolgozását: adják össze őket. Az eredeti szál várja be a feldolgozást, majd írja ki a részösszegek összegét!
\item Kérdezd le egy új szálban az összes futó szálat, és írd ki azok neveit! Értékeld a látottakat egy grafikus alkalmazás indítása eseten (ehhez rekurzívan be kell járni a \texttt{getParent()}-eket)!
\item Készíts 2 szálat! Az első állítsa elő az első tíz hatványát a kettes számnak (majd várjon egy másodpercet), a másik legyen egy daemon szál, amely a nevét írja ki, majd vár egy másodpercet egy végtelen ciklusban.
\item Készíts 5 szálat, amelyek egy saját csoportban vannak! A szálak egy véletlen számot választanak az 1-100 intervallumból, és fél másodpercenként növelnek egy saját számlálót ezzel az értékkel, amíg az meg nem haladja az 1000-et.

A szálak elindítása után a fő szálban várjunk 10 másodpercet, majd listázzuk ki az aktív szálakat, a maximális prioritást a szálak között, és írjunk ki egy listát a szálakról és azok tulajdonságairól! Ezután függesszük fel az összes szálat, írjuk ki a szülő \texttt{ThreadGroup} nevét, majd újra indítsuk el az összes szálat a saját csoportunkban. A végén pedig várjuk be az összes szálat!
\item Készíts egy egyszerű grafikus alkalmazást, amely egyetlen panelt tartalmaz, az aktuális idővel. A panelen található információt másodpercenként frissítsd! Az osztály definíciója nézzen ki a következőképpen:

\begin{verbatim}
public class ... extends JFrame implements Runnable {
    ...
}
\end{verbatim}
\item Egészítsd ki az előző feladatot úgy, hogy ha ráklikkel a felhasználó a \texttt{Label}-re, akkor szüneteltesse a frissítést a program. Ha újra ráklikkel, folytassa a számlálást!
\end{enumerate}

\subsection*{Java Concurrency - 1.6}
A \texttt{java.util.concurrent.*}, \texttt{java.util.concurrent.atomic.*}, \texttt{java.\-util.concurrent.lock.*} csomagok változatos, hatékony eszközöket nyújtanak:

\begin{itemize}
\item Barrier, Semaphor, FutureTask, \dots
\item Adatszerkezetek: ConcurrentHashMap, BlockingQueue, \dots
\item Lockok, pl. ReentrantLock, \dots
\item Atomi változók: AtomicLong, AtomicReference, \dots
\end{itemize}

\newpage
\section*{+/- Feladat}
A feladatokat a \url{legendi@inf.elte.hu} címre küldjétek, \textbf{következő szombat éjfélig}! A subject a következőképp nézzen ki:

\begin{verbatim}
csop<csoportszám>_gyak<gyakorlat száma>_<EHA-kód>_<feladatok száma>
\end{verbatim}

Például:

\begin{verbatim}
csop1_gyak10_LERIAAT_1
csop1_gyak10_LERIAAT_12
\end{verbatim}

Mellékelni csak a Java forrásfájlokat mellékeljétek (semmiképp ne teljes Eclipse/NetBeans projecteket), esetleg rarolva, zipelve, és egy levelet küldjetek (több levél esetén az utolsó mellékleteit értékelem)! További fontos kritériumok:
\begin{itemize}
\item A konvenciókra figyeljetek plz!
\item Ékezetes karaktereket \textbf{ne} használjatok! Főleg azonosítók esetében ne! A Java ugyan ezt megengedi, ugyanakkor a különböző környezetekbe való konvertáláskor ($latin2 \leftrightarrow UTF-8 \leftrightarrow Cp1250$) összetörnek a karakterek! Az ilyen forrásokat fordítani, következésképp értékelni sem tudom.
\end{itemize}

\subsection*{1. Feladat}
Egészítsd ki a 8. gyakorlaton készített port scanner alkalmazást, hogy 2. parancssori argumentumként meg lehessen mondani hány szállal hajtsa végre párhuzamosan a vizsgálatot!

\subsection*{2. Feladat}
Készítsünk egy saját WWW keresőmotort, amely egy cím $\rightarrow$ URL kereső adatbázist képesz készíteni! A program 5 szál használatával keressen az interneten, minden szám max. 50 weboldalt járjon végig, és írják ki egy közös fájlba soronként a weboldal címét (a title meta-tag értékét), valamint az éppen vizsgált URL-t. Parancssori argumentumként kapjon egy URL címet, amelyet végigolvasva további URL címeket keressenek a szálak.
