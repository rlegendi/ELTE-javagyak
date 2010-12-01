# Gyűjtemény keretrendszer #

## Generic - Bevezetés #x
**Egyelőre csak a collectionökhöz, bevezető jelleggel.** Típussal
paraméterezhetőség. Type erasure: csak fordítási időben ismert a
típusinformáció, utána automatikusan törli a fordító, bájtkódból nem szerezhető vissza - nem C++ template-ek, nem generálódik fordítási időben új típus, nincs template metaprogramozás.

Nem kötelező velük foglalkozni (@SupressWarnings({"rawtypes", "unchecked")}), de rendkívül hasznosak, fordítási időben tudunk potenciális hibalehetőségeket kiszúrni - persze ez is a programozón múlik. Kényelmes, típusbiztos. Aki meg heterogén adatszerkezeteket használ, megérdemli.

Collectionöknél aktívan használjuk őket:

\begin{verbatim}
Vector<String> s = new Vector<String>();
\end{verbatim}

Az előnyük:

\begin{verbatim}
// Pre-1.5 era:
Vector v = new Vector();

v.add( new Integer(1) );
v.add( new Integer(2) );

for (int i=0, n=v.size(); i<n; ++i) {
    Integer act = (Integer) v.get(i);
    System.out.println(act);
}

// Uj:
Vector<Integer> v = new Vector<Integer>();
v.add(1);
v.add(2);

for (int i=0, n=v.size(); i<n; ++i) {
    Integer act = v.get(i);
    System.out.println(act);
}
\end{verbatim}

\subsection*{Autoboxing-unboxing}
Csak objektum referenciákat tárolhatnak, ezért \textit{primitív típusok helyett} ún. csomagoló (\textit{wrapper}) osztályokat (Integer, Character, Double, etc.) kell használnunk - azonban ezek ilyen esetekben automatikusan konvertálódnak (ld. fenti példa). \textbf{Amire figyelni kell:} teljesítmény, == operátor, null unboxing NPE-vel jár.

\bigskip
\noindent
Példa:
\begin{verbatim}
v.add(1);
// Implicit a kovetkezot jelenti:
v.add( new Integer(1) );

v.add(1);
boolean eq = v.get(0) == v.get(1); // LEHET hamis! (*)

v.add(null);
for (int act : v) { ... } // RECCS!
\end{verbatim}

\noindent
\textbf{Megjegyzés} A \textit{(*)}-gal jelölt rész speciel pont mindig igaz lesz, de ez mágia műve: a -127-126 intervallumon lévő számokat wrapper objektumait cache-eli a virtuális gép, azok mindig ugyanazok a példányok lesznek. Általában ezzel gond lehet.

\section*{Gyűjtemény keretrendszer}
Collections Framework, java.util.* csomag, objektumok memóriában tárolására, lekérdezése, manipulálása (v.ö. C++ STL). Általános célú adatszerkezetek:

\begin{itemize}
\item Collection
	\begin{itemize}
	\item List
    \item Deque
    \item Set
        \begin{itemize}
        \item SortedSet
        \end{itemize}
    \end{itemize}
\item Map
    \begin{itemize}
    \item SortedMap
	\end{itemize}
\end{itemize}

Nem megvalósított művelet UnsupportedOperationException-t dob. Copy konstruktorok vannak (egyik a másikra konvertálható). Műveletek 3 csoportja:

\begin{enumerate}
\item Alapvető műveletek: size(), isEmpty(), contains(), add(), remove()
\item Elemek együttes kezelése: addAll(), containsAll(), removeAll(), clear(), retainAll()
\item Tömbbé konvertálás - gány:
\begin{verbatim}
A[] arr = (A[]) list.toArray(new A[list.size()]);

// Kicsit egyszerubb, bar kevesbe hatekony, biztonsagos:
A[] arr = (A[]) list.toArray();
\end{verbatim}
\end{enumerate}

Iterátorokkal rendelkeznek, használhatók for-each-ben. Példa:

\begin{verbatim}
package collections;

import java.util.Vector;

public class VectorTest {
    public static void main(String[] args) {
        Vector<Double> vector = new Vector<Double>();
        
        for (int i=0; i<5; ++i) {
            vector.add( Math.random() );
        }
        
        System.out.println(vector);
    }
}
\end{verbatim}

\subsection*{Halmaz}
Duplikált elemeket nem tartalmazhat, kell hozzá az objektumon az equals() és hashCode() (hashelő implementációk, nem számít a sorrend, 2 halmaz egyenlő, ha ugyanazokat az elemeket tartalmazzák). HashSet, TreeSet: előbbi hatékonyabb, utóbbi rendezett.

\subsubsection*{Feladat}
Készíts egy programot, amely megszámolja a parancssori argumentumként megadott fájl szavaira, hogy azokban hány különböző betű van (kis-, és nagybetűk között ne tegyünk különbséget, és az egyéb karakterekkel ne foglalkozzunk)! A megvalósításhoz használj halmazt (Set<Character>, String\#to\-Char\-Ar\-ray())! Példa output:

\begin{verbatim}
> cat input.txt
Ia! Shub-Niggurath! The Black Goat of the Woods with a Thousand Young!
> java collections.CharCounter input.txt
Ia! -> 2
Shub-Niggurath! -> 11
The -> 3
Black -> 5
Goat -> 4
of -> 2
the -> 3
Woods -> 4
with -> 4
a -> 1
Thousand -> 8
Young! -> 5
\end{verbatim}

\subsection*{Lista}
Elemek pozíció szerinti elérése, iteráció, részlista kezelés. A remove() az elem 1. előfordulását távolítja el, az add(), addAll() a lista végéhez fűz hozzá. Két lista egyenlő, ha ugyanazokat az elemeket tartalmazzák, ugyanabban a sorrendben. A lista iterátora a ListIterator, 2 irányban is bejárható: hasNext(), next(), ill. hasPrevious(), previous(). Részlista: balról zárt, jobbról nyílt intervallumot kell megadni. Két implementáció: ArrayList, LinkedList, előbbi a pozicionális műveleteknek kedvez, utóbbi akkor, ha a lista elejére kell sokat beszúrni, és iteráció közben törölni (általában az ArrayList használata a célravezetőbb).

\subsubsection*{Feladat}
Készíts egy programot, amely a parancssori argumentumként megadott fájl szavait lexikografikusan rendezi, az eredményt kiírja a képernyőre, és el is menti egy (szintén parancssori argumentumként megadott) fájlba! A feldolgozás előtt minden nem alfanumerikus karaktert távolíts el a szavakból. A megvalósításhoz használj egy tetszőleges lista adatszerkezetet (ArrayList, LinkedList, Stack, Vector), valamint a java.util.Collections\#sort() függvényt! Példa output:

\begin{verbatim}
> cat input.txt
Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn
> java collections.StringSorter input.txt output.txt
[Cthulhu, Phnglui, Rlyeh, fhtagn, mglwnafh, wgahnagl]
> cat output.txt
Cthulhu
Phnglui
Rlyeh
fhtagn
mglwnafh
wgahnagl
\end{verbatim}

\subsection*{Leképezés}
Kulcs-érték párokhoz: HashMap, Hashtable (minimális különbség: utóbbi szinkronizált, megengedi a null értékeket is). Minden kulcshoz egy érték tartozhat. Nem iterálható, azonban lekérdezhető a keySet(), entrySet(), ami már igen.

\subsubsection*{Feladat}
Készíts egy programot, amely megszámolja egy fájlból az egyes szavak előfordulásainak számát! A program a fájl elérési útját argumentumként kapja. A megvalósításhoz használj egy String $\rightarrow$ Integer leképezést (HashMap<String, Integer>)! Példa output:

\begin{verbatim}
> cat input.txt
Ia! Ia! Cthulhu fhtagn!
> java collections.WordCounter input.txt
{Ia!=2, Cthulhu=1, fhtagn=1}
\end{verbatim}

Megoldási javaslat: minden szó esetén ellenőrizd le, hogy szerepel-e már az adatszerkezetben. Ha nem, rakd bele 1-es értékkel; ha igen, vedd ki az előző értékkel, és eggyel nagyobb értékkel tedd vissza!

\subsection*{Rendezés}
Beépített típusoknak értelemszerű a relációja - felhasználói típusokat a programozó dönti el. Comparable interfész $\rightarrow$ compareTo() metódusa, melynek eredménye int típusú:

\begin{itemize}
\item 0, ha a két objektum egyenlő
\item $<0$, ha az adott objektum kisebb a paraméternél
\item $>0$, ha fordítva
\end{itemize}

Implementálás:

\begin{verbatim}
class Foo implements Comparable<Foo> {
    ...
    public int compareTo(final Foo foo) {
        return ...;
    }
}
\end{verbatim}

Ha ennek használatára nincs lehetőség, marad egy saját Comparator készítése (pl. egyazon objektumot több szempont szerint kell rendezni).

\subsubsection*{Feladat}
Készítsetek egy Date osztályt, amely tartalmazza az év, hónap, nap adatokat (mind számok). Implementáljátok vele a Comparable<Date> interfészt, és ennek megfelelően valósítsátok meg a compareTo() függvényt! Hozzatok létre kódból 3 objektumot, és tároljátok el ezeket egy tetszőleges lista adatszerkezetben. Ezt aztán rendezzétek le kronológiai sorrend szerint a Collections\#sort() függvénnyel, és írjátok ki az eredményt!

\section*{Kényelmi lehetőségek}
\begin{enumerate}
\item java.util.Arrays\#asList(): tömbből listát csinál
\item java.util.Collections
\begin{itemize}
\item nCopies(int n, Object o): két paraméter, amely $n$-szer tartalmazza $o$-t.
\item Egyelemű, üres, módosíthatatlan, szinkronizált listák
\item Algoritmusok:
\begin{itemize}
\item Rendezés, összefésüléses módszerrel ($n log(n)$, rendezett listát már nem rendez, szemben a quick sorttal): sort()
\item Összekeveerés:  shuffle()
\item Megfordítás, feltöltés, másolás: reverse(), fill(), copy()
\item Bináris keresés: $(-i-1)$-et ad vissza, ahol $i$ az első olyan elem indexe, amely nagyobb az elemnél.
\item Minimum, maximum elem: min(), max()
\end{itemize}
\end{itemize} 
\end{enumerate}

\textbf{Megjegyzések:}
\begin{itemize}
\item $capacity() != size()$
\item Az interfész műveleteken kívül rengeteg egyéb hasznos funkcionalitás, érdemes a javadocot olvasgatni
\item Saját implementációk: hajrá! A Collections Framework absztrakt osztályokat biztosít (AbstractList, AbstractSet, etc.), lehet származtatni.
\item További adatszerkezetek: Dequeue, Stack, BitSet, Vector, etc.
\item Felhasználás: paraméterként, változódeklarációként célszerű minél általánosabb interfészt megadni (a collections framework előnye a rugalmassága):
\begin{verbatim}
Vector<Integer> v1 = new Vector<Integer>();  // vektorkent kezeles
List<Integer>   v2 = new Vector<Integer>();  // listakent kezeles
\end{verbatim}
\end{itemize}

\textbf{Részletesen:} http://java.sun.com/javase/6/docs/api/java/util/package-summary.html

\section*{Feladat}
Készítsünk egy sorozat rendező alkalmazást! A program inputja a következő formátumú fájl legyen:

\begin{verbatim}
# evad : epizod : cim
12:7:Super Fun Time
12:4:Canada on Strike
8:13:Cartman's Incredible Gift
10:8:Make Love, Not Warcraft
\end{verbatim}

A \# karakterrel kezdődő sorokat hagyjuk figyelmen kívül (String\#trim())! Készítsünk egy Sitcom osztályt a megfelelő adattagokkal (season, episode, title), és implementáljuk vele a Comparable<Sitcom> interfészt! A compareTo() működjön úgy, hogy elsődleges szempont szerint az évad, azon belül pedig az epizódszám alapján rendezzen! Az adatokat tároljuk egy Vector<Sitcom> adatszerkezetben. A saját osztály helyes működéséhez implementáljuk az equals(), toString(), hashCode() függvényeket is!

A program beolvasás után rendezze a használt vektort, és biztosítson egy interaktív konzolos felületet a felhasználónak, amely a következő funkcionalitásokkal rendelkezzen:

\begin{itemize}
\item Új epizód felvétele
\item Epizódlista mentése (kilépés után ezt töltse be)
\item Adott epizód adatainak módosítása
\item Epizódok listázása a képrenyőre
\item Kilépés
\end{itemize}

\end{document}
