# Emlékeztető #

## Környezet beállítása ##
Emlékeztető:

	Microsoft Windows XP [verziószám: 5.1.2600]
	(C) Copyright 1985-2001 Microsoft Corp.
	
	c:\tmp>set PATH=%PATH%;c:\Program Files\Java\jdk1.6.0_12\bin\
	
	c:\tmp>javac -version
	javac 1.6.0_12
	
	c:\tmp>javac HelloWorldApp.java
	
	c:\tmp>java HelloWorldApp
	Hello World!
	
	c:\tmp>

# Csomagok #
Modularizáció, névütközések feloldása, hozzáférés szabályozás, etc. (mint a
C++ namespace). Osztályok, interfészek gyűjteménye. Használható a `*` wildcard.
Alapértelmezetten látszik a `java.lang.*` csomag minden eleme, minden mást
importálni kell (anélkül ún. _fully qualified classname_ segítségével
hivatkozhatunk, pl. `java.util.Vector`):

``` java
import java.util.Vector;	// 1 tipushoz
import java.math.*;			// Minden package-beli tipus lathatova valik
	
import java.awt.*;			// GUI
import java.awt.event.*;	// GUI - esemenykezeles
import javax.swing.*;		// Advancedebb GUI
import java.util.*;			// Adatstrukturak
import java.io.*;			// IO
import java.util.regex.*;	// Regexp
	
// static import: minden static konstans lathato az adott osztalybol
// fenntartasokkal hasznalni
import static java.lang.Math.*;
```

A fordítás nehézkes, nincs rekurzív `javac -R *.java`. Leképezés a
fájlrendszerre: minden `.` karakterrel szeparált rész egy könyvtárat jelent,
fordítás a gyökérkönyvtárból történik. Static importot csak offtosan
(strukturáltság, enkapszuláció, egységbezárás ellen hat - használjatok helyette
dedikált osztályt vagy interfészt). Csomag definíciója a Java fájl legelején:

``` java
package pkg;
	
// Import utasitasok
	
public class HelloWorldApp {
    public static void main(String args[]) {
        System.out.println("Hello World!");
    }
};
```

Fordítás teljes útvonal megadásával:

	C:\tmp>javac pkg/*.java
	
	C:\tmp>java pkg.HelloWorldApp
	Hello World!
	
	C:\tmp>

Ha esetleg névütközés van (2 azonos nevű osztály), akkor minősített névvel
érhetjük el az egyiket (pl. `java.util.List`, `java.awt.List`). Importokat
használjatok nyugodtan, nem gáz, nem emészt erőforrást (nem C++, dinamikus
osztálybetöltés van).

# Függvények #
Általános prototípus:

	<módosítószavak> <visszatérési érték> <név>( <paraméterek listája> )
	        [ throws <kivétel lista> ] {
	    <utasítás1>;
	    <utasítás2>;
	    ...
	}

Paraméter átadás érték szerint történik (még a referenciák is!).

* Módosítószavak:
	* Láthatóság: `public`, `protected`, `private`. Ha nem definiált, akkor ún.
	  _package-private_ láthatóság.
	* Lehet `abstract`: ekkor nincs implementáció (mint a C++ _pure virtual_
	  függvényei) leszármazottban kötelezően felüldefiniálandó
	* Lehet `final`: felüldefiniálhatóság letiltására
	* Lehet `static`: osztály szintű függvény (**Fontos:** static kontextusból
	  csak static módosítóval ellátott hivatkozás szerepelhet)
	* Egyéb, pl. `strictfp`, `native`, `synchronized`, `transient`, `volatile`
	  (utóbbi kettő **csak** fieldekre). Ezekről később.
* Visszatérési érték szerinti csoportosítás:
	* `void`: eljárás
	* Minden egyéb: függvény
* Metódusnév: _lowerCamelCase_ formátumban
* Paraméter átadás: minden paraméter érték szerint adódik át
_még a referenciák is_.

**Szignatúra** a függvény neve és paramétereinek típusa -- más **nem**. Például:

``` java
eredmenyMeghatarozasa( double, int, int )
```

Overloading, overriding.

# +/- Feladatok #

A feladatokat a `gyak2.f1` ill. `gyak2.f2` csomagba rakjátok!

## Euclid ##
Készítsetek egy függvényt, amely az Euklideszi-algoritmus alapján meghatározza
két szám legnagyobb közös osztóját! Az algoritmus pszeudokódja:

	function gcd(a, b)
	    if a = 0
	       return b
	    while b != 0
	        if a > b
	           a := a - b
	        else
	           b := b - a
	    return a

Készítsd el a függvény rekurzív változatát is!

## Quadratic ##
Készítsetek egy függvényt, amely megadja egy másodfokú egyenlet gyökeit! A
függvény definíciója legyen a következő:

``` java
private static double[] sqroots(final double a, final double b,
	final double c) {
	// ...
}
```

A függvény dobjon _nem ellenőrzött és ellenőrzött kivételeket is_ (pl.
`IllegalArgumentException` és egy saját), ha `a == 0`, vagy a diszkrimináns
negatív! A függvény által dobott kivételeket kezeld is le a `main()`
függvényben! A paramétereket a parancssori argumentumok határozzák meg, és az
`Integer.parseInt()` függvény által dobott `NumberFormatException` kivételt is
kezeljétek le ugyanabban a kivételkezelő ágban!

