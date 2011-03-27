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

A fordítás nehézkes, nincs rekurzív `javac -R *.java`. Leképezés a
fájlrendszerre: minden `.` karakterrel szeparált rész egy könyvtárat jelent,
fordítás a gyökérkönyvtárból történik. Static importot csak offtosan
(strukturáltság, enkapszuláció, egységbezárás ellen hat - használjatok helyette
dedikált osztályt vagy interfészt). Csomag definíciója a Java fájl legelején:

	package pkg;
	
	// Import utasitasok
	
	public class HelloWorldApp {
	    public static void main(String args[]) {
	        System.out.println("Hello World!");
	    }
	};

Fordítás teljes útvonal megadásával:

	C:\tmp>javac pkg/*.java
	
	C:\tmp>java pkg.HelloWorldApp
	Hello World!
	
	C:\tmp>

Ha esetleg névütközés van (2 azonos nevű osztály), akkor minősített névvel
érhetjük el az egyiket (pl. java.util.List, java.awt.List). Importokat
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
	* Lehet abstract: ekkor nincs implementáció (mint a C++ _pure virtual_
	  függvényei) leszármazottban kötelezően felüldefiniálandó
	* Lehet final: felüldefiniálhatóság letiltására
	* Lehet static: osztály szintű függvény (**Fontos:** static kontextusból
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

	eredmenyMeghatarozasa( double, int, int )

Overloading, overriding.

# Kivételek #
Általános forma:

	try {
	    ... // Kritikus utasitasok
	} catch (Exception1 e1) {
	    ...
	} catch (Exception2 e2) {
	    ...
	} finally {
	    
	}

A finally opcionális, de elképzelhető csak `try-catch`, `try-finally` blokk is:

	try {
	    ...
	} finally {
	    ...
	}
	
	try {
	    ....
	} catch (Throwable t) {
	    ....
	}

Az első ág, amelybe a kivétel osztályhierarchia szerint beleillik, lekezeli.
Újradobás lehetséges: `throw e1;`, stb.

Alapvetően három típusú kivétel:

1. Felügyelt kivételek: definiálni kell őket a függvényben, és ha definiáltak,
le is kell őket kezelni (ősosztály: `java.lang.Exception`}, pl.
`java.lang.ClassNotFoundException`, `java.io.IOException`)
2. Nem felügyelt kivételek: nem kötelező sem difiniálni, sem lekezelni őket
(ősosztály: `java.lang.RuntimeException`, pl. `ArrayIndexOutOfBoundsException`,
`NumberFormatException`, `DivisionByZeroException`)
3. Léteznek még `Error`-ok, ezek a `Throwable` leszármazottai. Kritikus esetben
fordulnak elő, a legtöbb esetben a lekezelésük is felesleges (pl.
`OutOfMemoryError`, `StackOverflowError`)

Mindkettő őse a `Throwable` osztály, ezt kell hát lekezelni, ha mindent
lehetőségre fel akarunk készülni. **Ökölszabály:** üres kivételkezelő blokkot
**soha** ne készítsünk! Legegyszerűbb megoldás: `e.getMessage()` vagy
`e.printStackTrace()`. További funkciók az `Exception` osztály leírásában
(`javadoc`). Kivételt dobni a `throw` utasítással lehet (_mindig_ van
paramétere!).

## Példa ##
### Egyszerű kivételkezelés ###

	public static void main(String[] args) {
	    try {
	        int res = Integer.parseInt(args[0]);
	        // ...
	    } catch (NumberFormatException nfe) {
	        System.err.println("Hibas input: " + args[0]);
	        nfe.printStackTrace();
	    }
	}
	
> **Megjegyzés** Kivételek neve általában `e`, de igazából ízlés kérdése. 

### Függvénydefiníció ###
	// Egyszerubb forma, ha nem akartok uzeneteket
	//class ZeroParameterException extends Exception {}
	
	class ZeroParameterException extends Exception {
	    public ZeroParameterException() {
	        super();
	    }
	
	    public ZeroParameterException(final String msg) {
	        super(msg);
	    }
	}
	
	static double divide(int a, int b) trhows ZeroParameterException {
	    if (0 == b) {
	        throw new ZeroParameterException("b erteke nem lehet 0!");
	    }
	    
	    return (double) a / b;
	}
	
	public static void main(String[] args) {
	    try {
	        double res = divide(1, 0);
	    } catch (ZeroParameterException e) {
	        System.err.println(e.getMessage());
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    } finally {
	        System.err.println("vege");
	    }
	}

> **Részletesen** <http://download.oracle.com/javase/tutorial/essential/exceptions/>

Saját kivétel is definiálható, csak származtatni kell (pl. a
`java.lang.RuntimeException`, `java.lang.Exception` osztályokból, ezekről
később).

> **Megjegyzés** Az alábbi *Explanations of Common Java Exceptions* egy humoros
> leírása a common Java kivételeknek. Mielőtt komolyan vennétek, hangsúlyozom,
> ez csak poén, de ha már megragad 1-2 Exception neve, akkor már megérte
> átröhögni :-) Thx to Björn Andersson!

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

	private static double[] sqroots(final double a, final double b,
	        final double c) {
	    // ...
	}

A függvény dobjon _nem ellenőrzött és ellenőrzött kivételeket is_ (pl.
`IllegalArgumentException` és egy saját), ha `a == 0`, vagy a diszkrimináns
negatív! A függvény által dobott kivételeket kezeld is le a `main()`
függvényben! A paramétereket a parancssori argumentumok határozzák meg, és az
`Integer.parseInt()` függvény által dobott `NumberFormatException` kivételt is
kezeljétek le ugyanabban a kivételkezelő ágban!

