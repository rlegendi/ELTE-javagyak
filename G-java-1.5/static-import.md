# Static Import #

> **Megjegyzés** Az alábbi fejezetet a Java 1.5 egy bevezetőjének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Márton Dávidnak! 
> 
> <http://download.oracle.com/javase/1.5.0/docs/guide/language/static-import.html>

---

Ahhoz, hogy statikus tagokhoz hozzáférjünk, az őket tartalmazó osztály nevével kell hivatkoznunk rájuk. Például:

``` java
double r = Math.cos(Math.PI * theta);		//Math (mindkétszer piros)
```

Gyakran előfordul, hogy ennek megkerülése érdekében valaki a statikus tagokat egy interface-ben helyezi el, majd ebből az interface-ből származtat. Ez csúnya dolog, olyan csúnya, hogy még külön nevet is kapott: Konstans Interface Antiminta (*Constant Interface Antipattern*) (lsd.: [Effective Java](http://java.sun.com/docs/books/effective/ "Effective Java") 17 .szakasz). A probléma az, hogy ha egy osztály egy másik osztály statikus tagját használja az mindössze implementációs kérdés. Ugyanakkor, ha egy osztály implementál egy interface-t, az részévé válik az oszály publikus API-jának. Implementációs részletek viszont nem kerülhetnek ki a publikus API-ba.

A statikus import konstrukció minősített név nélküli hozzáférést biztosít statikus tagokhoz, *anélkül*, hogy azokat egy másik típustól kellene örökölni. Helyette a program egyesével importálja a tagokat:

``` java
import static java.lang.Math.PI;
```

vagy az összeset együtt:

``` java
import static java.lang.Math.*;
```

Amint a statikus membereket importáltuk, minősített név nélkül használhatjuk őket:

``` java
double r = cos(PI * theta);
```

A statikus import deklaráció analóg az import deklarációjával. Míg a normál import-ok csomagokban lévő osztályokat importálnak, lehetővé téve, hogy azokat a csomagnév nélkül használjuk, a statikus import segítségével statikus metódusokat importálhatunk osztályokból, és használhatjuk őket az osztálynév nélkül.

Mikor használj statikus importot? **Minél ritkábban**. Csak akkor használd, ha másképp arra éreznél késztetést, hogy lokális másolatot készíts konstantokról, vagy hogy megsértsd az öröklődést. (Konstans Interface Antiminta). Más szóval, akkor használd, ha egy-egy osztály statikus tagját gyakran használod a kódodban. Ha túl sokszor használod, az könnyen olvashatatlanná és nehezen karbantarthatóvá teheti a programod, az által, hogy a program névtere "beszennyeződik" mindazokkal a statikus tagokkal, amelyeket importáltál. Akik a kódod olvassák (köztük te is, pár hónappal annak megírása után) nem fogják tudni, hogy melyik statikus tag melyik osztályhoz tartozik. Egy osztály *összes* statikus tagjának importálása különösen megnehezíti az olvashatóságot; ha csak egy-két tagra van szükséged, külön importáld őket! Megfelelően használva, a static import *javítja* a programod olvashatóságát, azáltal hogy megszünteti az osztálynevek ismétlődését.

---

