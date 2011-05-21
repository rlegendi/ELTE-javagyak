# "Hello World!" Microsoft Windowson#

> **Megjegyzés** Az alábbi fejezetetek a Java Tutorial egy bevezető fejezetének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Szabó-Demény Balázsnak!
> <http://download.oracle.com/javase/tutorial/getStarted/cupojava/win32.html>

Itt az ideje, hogy megírd az első alkalmazásod! A most következő utasítások Windows XP Professional, Windows XP Home, Windows Server 2003, Windows 2000 Professional, és Windows Vista használóknak szól. Más platformokhoz leírást a ["Hello World!" for Solaris OS and Linux](http://download.oracle.com/javase/tutorial/getStarted/cupojava/unix.html) és ["Hello World!" for the NetBeans IDE](http://download.oracle.com/javase/tutorial/getStarted/cupojava/netbeans.html) cikkekben találsz.

Ha problémákba ütközöl, vess egy pillantást a következő fejezetre, ahol a gyakoribb hibákat és magyarázatukat találod.

## Tartalom ##

* Szükséges szoftverek 
* Első alkalmazás létrehozása 
	* Forrás fájl létrehozása 
	* Fordítsd le a forrásfájlt `.class` formátumra 
	* A program futtatása

## Szükséges szoftverek ##
Hogy megírd az első programod, szükséged lesz az alábbiakra:

1. Java SE Development Kit 6 (JDK 6) 
Innen letöltheted a Windowsos verziót (a JDK-t töltsd le, ne a JRE-t): <http://www.oracle.com/technetwork/java/javase/downloads/index.html>.
Kövesd a telepítési utasításokat: <http://www.oracle.com/technetwork/java/javase/index-137561.html>
2. Szövegszerkesztő
Ebben a leírásban a Notepad alkalmazást fogjuk használni, amely minden Windows platformon megtalálható, de bármilyen más szövegszerkesztő is használható. 
Mindössze ennyi szükséges ahhoz, hogy megírd az első programod.

## Első alkalmazás létrehozása ##
Első alkalmazásod `HelloWorldApp` névre hallgat, és annyit csinál, hogy kiírja a `"Hello world!"` üdvözlő szöveget a képernyőre. Ehhez az alábbiak szükségesek:

* Hozz létre egy forrásfájlt
A forrásfájl Java nyelven írott kódot tartalmaz, amit te és más programozók megértenek. Bármilyen szövegszerkesztőt használhatsz, hogy létrehozd, vagy szerkeszd. 
* A forrásfájl `.class` fordítása formátumra
A *fordító* (`javac`, mint *Java compiler*) a forrásfájlban lévő szöveget a gép számára érthető utasításokra alakítja. Az így előállított fájl tartalma az ún. bytecode.
* A program futtatása
A futtató alkalmazás (`java`) indítja el a virtuális gépet, ami értelmezi a bytecode-ot, és futtatja az alkalmazást.

### Forrásfájl létrehozása ###
Itt két lehetőséged van:

* Elmented a `HelloWorldApp.java` a gépedre, ezzel sok gépelést megspórolsz: <http://download.oracle.com/javase/tutorial/getStarted/application/examples/HelloWorldApp.java>. Utána folytathatod a következő fejezettel.
* Vagy követed az utasításokat :-)

Indítsd el a szövegszerkesztőt. A Notepad alkalmazást a *Start menüből* indíthatod el. Egy új dokumentumba írd be az alábbiakat:

``` java
/**
 * The HelloWorldApp class implements an application that
 * simply prints "Hello World!" to standard output.
 */
class HelloWorldApp {
    public static void main(String[] args) {
	System.out.println("Hello World!"); // Display the string.
    }
}
```

> **Megjegyzés** Minden kódot, utasítást és fájlnevet pontosan másolj le. Mind a futtató (`java`), mind a fordító alkalmazás (`javac`) megkülönbözteti a kis-, és nagybetűket. 
> `HelloWorldApp != helloworldapp`

Mentsd el a kódot `HelloWorldApp.java` néven. Ehhez válaszd a *Fájl &rarr; Mentés másként* menüelemet, majd:

1. A Fájlnév mezőbe írd be, hogy `"HelloWorldApp.java"`, az idézőjeleket is.
2. A Fájl típusának állítsd be az egyszerű szöveges formátumot: *Text Documents (\*.txt)*.
3. A Kódolás legördülő menüben legyen *ANSI* kiválasztva.

Miután végeztél így kell kinéznie:

![Mentés másként](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/saveas.png "Mentés másként")
**Ábra** A *Mentés másként* dialógusablak, közvetlenül a *Mentés* gomb megnyomása előtt
 
Ments, és lépj ki a Notepad alkalmazásból!

### A forrásfájl `.class` fordítása formátumra ###
Indítsd el a parancsértelmezőt (`cmd.exe`), ehhez a *Start menüben* kattints a futtatásra, és írd be `"cmd"`. Ennek az ablaknak így kell kinéznie.
 
![Parancsértelmező ablak](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/cmd.png "Parancsértelmező ablak")
**Ábra** Parancsértelmező ablak
 
A parancsértelmező mutatja az *aktuális könyvtárat*. Ez alapértelmezés szerint általában a felhasználó saját könyvtára (*home directory*), mint a képen is látható egy Windows XP esetén.

Hogy lefordítsd a forrásfájlt, lépj be abba a könyvtárba, ahova a fájlt mentetted. Például ha ez a könyvtár a `C:` meghajtón lévő `java` könyvtár, akkor a következő utasítással teheted ezt meg:

	$ cd C:\java

Az aktuális könyvtárad most a `C:\java`, amelyet a parancsértelmező *prompt* is jelez.

> **Megjegyzés** Ahhoz, hogy meghajtót válts, szükség van egy másik utasításra is: a meghajtó nevére. Például ha a `D:` meghajtón lévő `java` könyvtárba tudj váltani, írd be a megható nevét és egy `":"` karaktert. Például: 
 
![Másik meghajtóra váltás](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/extrastep.png "Másik meghajtóra váltás")
**Ábra** Másik meghajtóra váltás 
 
Ha beírod a `dir` parancsot, láthatod a forrás fájlt, ahogy azt az alábbi ábra is mutatja.
 
![Mappa tartalmának listázása](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/dir.png "Mappa tartalmának listázása")
**Ábra** Mappa tartalmának listázása
 
Most már készen állsz a fordításra. Ehhez a következőt kell begépelned:  

	$ javac HelloWorldApp.java

A fordító létrehozta a bytecode állományt, egy `HelloWorldApp.class` fájlt. A `dir` paranccsal hatására látható is a megjelenő listán az állományok között.
 
![Könyvtár lista, amely tartalmazza a generált class fájlt](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/class.png "Könyvtár lista, amely tartalmazza a generált class fájlt")
**Ábra** Könyvtár lista, amely tartalmazza a generált `class` fájlt
 
Most már futtathatod a programot. 
Ha problémákba ütköztél, vess egy pillantást a következő fejezetre, ahol a gyakoribb hibákat és magyarázatukat találod.

### A program futtatása ###
Gépeld be a következő parancsot abban a könyvtárban, ahol az alkalmazásod található:

	$ java HelloWorldApp

A lenti képen találhatod az eredményt.
 
![A program eredménye](https://github.com/rlegendi/ELTE-javagyak/raw/master/B-kornyezet-beallitasa/result.png "A program eredménye")
**Ábra** A program eredménye
 
A program kiírja a `"Hello World!"` üzenetet.

Gratulálunk! Működik a program!

Ha problémákba ütköztél, vess egy pillantást a következő fejezetre, ahol a gyakoribb hibákat és magyarázatukat találod.

# Gyakran előforduló hibák #
## Fordítási hibák ##
### Általános hiba üzenetek Microsoft Windows rendszeren ###

	'javac' is not recognized as an internal or external command, operable program or batch file

Ha ezt a hibát kapod, a Windows nem találja a fordítót (`javac`).

Itt egy megoldás a problémára. Tegyük fel, hogy a telepített JDK a `C:\jdk6` könyvtárban található. A konzolba írd be a következő parancsot: `C:\jdk6\bin\javac HelloWorldApp.java`. Így minden alkalommal be kell írni ezt a parancsot, ha fordítani vagy futtatni akarod az alkalmazást. A felesleges gépelés elkerülése végett, olvasd el a JDK telepítési útmutatót (*vagy nézd meg az első gyak anyagát :P*). 

	Class names, 'HelloWorldApp', are only accepted if annotation processing is explicitly requested 

Elfelejtetted a `.java` postfixet a fordítás során. Ne feledd, a használandó parancs `javac HelloWorldApp.java` nem pedig `javac HelloWorldApp`.

### Általános hibaüzenetek UNIX rendszeren ###

	javac: Command not found 

Ha ezt a hibát kapod, a UNIX nem találja a fordítót (`javac`).

Itt egy megoldás a problémára. Tegyük fel, hogy a telepített JDK a `/usr/local/jdk6` könyvtárban található. A konzolba írd be a következő parancsot: `/usr/local/jdk6/javac HelloWorldApp.java`. Így minden alkalommal be kell írni ezt a parancsot, ha fordítani vagy futtatni akarod az alkalmazást. A felesleges gépelés elkerülése végett, olvasd el a JDK telepítési útmutatót (*vagy nézd meg az első gyak anyagát :P*). 

	Class names, 'HelloWorldApp', are only accepted if annotation processing is explicitly requested

Elfelejtetted a `.java` postfixet a fordítás során. Ne feledd, a használandó parancs `javac HelloWorldApp.java` nem pedig `javac HelloWorldApp`.

### Szintaktikai hibák (minden platformon)  ###
Akkor jelentkezik, mikor hiba van a program kódjában. A hiba üzenet leírja, hogy milyen típusú hiba lépett fel, és hol található a kódban. Így néz ki, ha elhagyod a sort záró jelet (;):

	at the end of a statement: testing.java:14: `;` expected. 
	System.out.println("Input has " + count + " chars.") ^ 1 error 

Néha a fordító nem tudja kitalálni, hogy mi a szándékod, ezért megtévesztő hibaüzenetet adhat vissza. Például ebből a kód részletből hiányzik egy (;) a vastaggal szedett résznél:

``` java
while (System.in.read() != -1) {
	count++ System.out.println("Input has " + count + " chars.");
}
```

Ebben az esetben a fordító két hibával tér vissza: 

	testing.java:13: Invalid type expression. count++ ^
	testing.java:14: Invalid declaration. System.out.println("Input has " + count + " chars."); ^
	2 errors 

A fordító két hibaüzenetet ír ki, mert a `count++` elérése után, a fordító azt hiszi, hogy ez egy kifejezés közepén van. A pontosvessző nélkül a fordító nem tudhatja, hogy az állítás teljes. 

Ha compiler error-okat látsz, akkor a programodnak nem sikerült lefordulnia, és a fordító nem hozta létre a .class fájlt. Nézd át a kódot, javítsd ki a hibákat és próbáld újra.

### Szemantikai Hibák ###

Azon felül, hogy ellenőrzi, hogy a programod szintaktikailag helyes, más alapvető hibák is jelentkezhetnek. Például a fordító minden alkalommal figyelmeztet, mikor olyan változó használsz ami nincs inicializálva: 

	testing.java:13: Variable count may not have been initialized. count++ ^
	testing.java:14: Variable count may not have been initialized. System.out.println("Input has " + count + " chars."); ^
	2 errors

Még egyszer, a program nem fordult le, nem jött létre a `.class` fájl. Javítsd ki a hibákat és próbáld újra.

## Futási idejű hibák ##
### Hibaüzenetek Microsoft Windows rendszeren ###

	Exception in thread "main" java.lang.NoClassDefFoundError: HelloWorldApp

A `java` nem találja a `HelloWorldApp.class` bájtkódot.  

Az egyik hely, ahol a `java` keresi a `.class` fájlt, az a jelenlegi könyvtár. Tehát ha a `.class` a `C:\java` könyvtárban található, válts át a következő paranccsal:

	cd c:\java 

Ha most kiadod a `dir` parancsot, láthatod a `.java` és `.class` fájlokat. Most írd be újra a `java HelloWorldApp` parancsot.

Ha még mindig hibába ütközöl, lehet, hogy meg kell változtatnod a `CLASSPATH` változót. Hogy lásd, hogy ez valóban szükséges, üsd ki a `CLASSPATH` változót ezzel a paranccsal:

	set CLASSPATH= 

Most írd be újra a `java HelloWorldApp` parancsot. Ha a program most működik, meg kell változtatnod a `CLASSPATH` változót. Hogy átállítsd ezt a változót, olvasd el a JDK telepítési útmutatót. 

	Exception in thread "main" java.lang.NoClassDefFoundError: HelloWorldApp/class 

Általános hiba a kezdő programozók részéről, mikor a java futtató alkalmazást a fordító által létrehozott `.class` fájlon próbálják alkalmazni. Ezt a hibát akkor kapod, ha példálul `java HelloWorldApp.class` parancsot adsz ki be `java HelloWorldApp` helyett. Ne feledd, az argumentum az osztály neve, amit használni akarsz, és nem a fájl neve. 

	Exception in thread "main" java.lang.NoSuchMethodError: main 

A Java VM-nek szüksége egy main metódusra, ahol elkezdheti az alkalmazásod végrehajtását. Biztos megfelel a szignatúra, nem írtad el a függvénydefiníciót?

### Hibaüzenetek UNIX rendszeren ###

	Exception in thread "main" java.lang.NoClassDefFoundError: HelloWorldApp 

A `java` nem találja a `HelloWorldApp.class` bájtkódot.  

Az egyik hely, ahol a `java` keresi a `.class` fájlt, az a jelenlegi könyvtár. Tehát ha a `.class` a `/home/jdoe/java` könyvtárban található, válts át a következő paranccsal: 

	cd /home/jdoe/java 

Ha most kiadod a `pwd` parancsot, láthatod a `.java` és `.class` fájlokat. Most írd be újra `java HelloWorldApp` parancsot.

Ha még mindig hibába ütközöl, lehet, hogy meg kell változtatnod a `CLASSPATH` változót. Hogy lásd, hogy ez valóban szükséges, üsd ki a `CLASSPATH` változót ezzel a paranccsal:

	unset CLASSPATH 

Most írd be újra a `java HelloWorldApp` parancsot. Ha a program most működik, meg kell változtatnod a `CLASSPATH` változót. Hogy átállítsd ezt a változót, olvasd el a JDK telepítési útmutatót.

	Exception in thread "main" java.lang.NoClassDefFoundError: HelloWorldApp/class 

Általános hiba a kezdő programozók részéről, mikor a java futtató alkalmazást a fordító által létrehozott `.class` fájlon próbálják alkalmazni. Ezt a hibát akkor kapod, ha példálul `java HelloWorldApp.class` parancsot adsz ki be `java HelloWorldApp` helyett. Ne feledd, az argumentum az osztály neve, amit használni akarsz, és nem a fájl neve. 

	Exception in thread "main" java.lang.NoSuchMethodError: main 

A Java VM-nek szüksége egy main metódusra, ahol elkezdheti az alkalmazásod végrehajtását. Biztos megfelel a szignatúra, nem írtad el a függvénydefiníciót?

