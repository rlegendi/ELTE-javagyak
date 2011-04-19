# Threading - Kiegészítés #

* **StringBuilder** vs. **StringBuffer** `StringBuffer` szinkronizált, a
  `StringBuilder` nem, cserébe hatékonyabb (nincs szinkronizációs költség).
* **Szál indítása** Figyeljetek! **Nem** a `run()`, hanem a `start()` függvény
  használandó erre. Előbbi hatására ugyanúgy szekvenciális programunk lesz.
* **Deprecated függvények a Thread osztályban** Erősen deadlock-prone
  függvények. Pl. egy szál `synchronized` blokkban van, és suspendelik, akkor
  nem fogja elereszteni az erőforrásokat, így más nem juthat hozzá - így könnyen
  deadlock alakulhat ki. Részletes magyarázat, megoldások, workaroundok itt
  találhatók: <http://download.oracle.com/javase/6/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html>
* `synchronized(this)` **Ez rossz!** *Nem a `this` miatt, hanem mert egy közös használatú változót különböző objektumokkal védesz le!*
  A `this` minden esetben az aktuális példányt jelenti (ami példányonként
  nyilván különbözik), az `i` változó pedig mindnél ugyanaz.  

``` java
		static int i = 0;
		
		class T extends Thread {
		    public void run() { synchronized(this) { i++; }
		}
```


**Feladat** Adott `v_1`, `v_2`, ..., `v_n` vektorok, amelyen `n` szál dolgozik.
A program parancssori argumentumként kap egy `e` értéket. Keressük meg az első
olyan `j` indexet, ahol ez a szám megtalálható, vagyis `v_i[j] = e`,
`1 <= j <= n`. *Az egyszerűség kedvéért feltételezzük, hogy minden vektorelem*
*egyedi, valamelyik vektorban megtalálható a keresett `e` érték, és `n=2`.*

*'Nuff said, let's rock!*

## 1. kísérlet ##
Indítsunk két szálat. Közös változó a `found`, lokális változó a `v, i`.

``` java
	found = false; i = 0;    // A
	while (!found) {         // B
	  found = v.get(i) == e; // C
	  i++;                   // D
	}
```

### FAIL ###
Tegyük fel, hogy az egyes szál elindult, az `i.` elem épp `e`, `C` végrehajtása
után kapja meg a vezérlést a második szál. `A` inicializáló utasításával `found`
ismét hamis lesz **végtelen ciklus**.

## 2. kísérlet ##
*Ja, akkor nem a szálakban inicializálok.* Kerüljük el, hogy minden szál
külön-külön is inicializálja a közös változót, tegyük meg ezt a szálak indítása
előtt!

``` java
	found = false // Threadek inditasa elott
	
	i = 0;                   // A
	while (!found) {         // B
	  found = v.get(i) == e; // C
	  i++;                   // D
	}
```

### FAIL ###
Tegyük fel, hogy az egyes szál `C`-hez ér, végrehajtja, és épp megtalálja az
adott értéket! Így `found` értéke igaz lesz. De! Ha közben a másik szál is
`C`-nél volt, és ezután hajtódik végre, `found` értéke ismét hamis lesz, az
eredmény **végtelen ciklus**.

## 3. kísérlet ##
*Ouch, tényleg!* Csak akkor adjunk új értéket a `found` változónak, ha
megtaláltuk az elemet.

``` java
	i = 0;                      // A
	while (!found) {            // B
	  if (e == v[i]) b = true;  // C
	  i++;                      // D
	}
```

### FAIL ###
Tegyük fel hogy az első szál az első elemében rögtön fel is fedezi az `e`
keresett értéket, és terminál (sérül a feltétlen pártatlan ütemezés elve, a
szálak nem dolgoznak szinkronban). Ekkor a második szál soha nem terminál, az
eredmény **végtelen várakozás**.

## 4. kísérlet ##
*Jó, akkor ütemezek én!* Vezessünk be egy `next` flaget, amely jelölje, hogy
melyik szál futhat a `while` ciklusba való belépés után! A feltételhez kötött
várakozást `await` szimbólummal jelölve, az első szál definíciója:

``` java
	i = 0;                            // A
	while (!found) {                  // B
	  await (1 == next) { next = 2; } // C
	  if (e == v[i]) b = true;        // D
	  i++;                            // E
	}
```

valamint a második szál definíciója legyen a következő:

``` java
	j = 0;                            // A
	while (!found) {                  // B
	  await (2 == next) { next = 1; } // C
	  if (e == v[j]) b = true;        // D
	  j++;                            // E
	}
```

### FAIL ###
Tegyük fel, hogy az első szál eljut `D` végrehajtásáig, majd ezután a második
szál is eljut ugyaneddig (`next` értékét `2`-re állítva). Tegyük fel, hogy a
második szál következő eleme nem a keresett `e` elem, így a `C` ponton tovább
várakozik. Ha eközben az első szál megtalálja a keresett elemet, és terminál, az
eredmény **holtpont**.

## 5. kísérlet ##
*Ooooh! És ha terminálásnál is jelzek?!* A szálak terminálásánál is figyeljünk a
`next` változóra! Az első szál kódját módosítsuk a következőképp:

``` java
	i = 0;                            // A
	while (!found) {                  // B
	  await (1 == next) { next = 2; } // C
	  if (e == v[i]) b = true;        // D
	  i++;                            // E
	}
	next = 2;                         // F
```

a másodikét pedig az alábbi módon:

``` java
	j = 0;                            // A
	while (!found) {                  // B
	  await (2 == next) { next = 1; } // C
	  if (e == v[j]) b = true;        // D
	  j++;                            // E
	}
	next = 1;                         // F
```

* Na ez már menni fog.* :-)

## Lásd még ##
Peterson-féle algoritmus kölcsönös kizárás megoldására, vektorértékadás atomicitása nélkül.

## Irodalom ##
1. Brian Goetz et al.: Java Concurrency in Practice, Addison-Wesley
   Professional, May 19, 2006.
2. Kozma, L. és Varga, L.: A szoftvertechnológia elméleti kérdései, ELTE Eötvös
   Kiadó, első kiadás 2003, második kiadás 2006.

## Kliens-szerver architektúra ##
A szerveroldali kód:

``` java
	// Raakaszkodas a portra
	ServerSocket ss = new ServerSocket( port );
	// Fuss, amig...
	while (true) {
	    // Egy bejovo kapcsolat elkapasa
	    Socket newSocket = ss.accept();
	
	    // Kapcsolat kezelese
	    // ...
	}
```

## Feladat ##
Készítsetek egy többszálú chat szerveralkalmazást, valamint egy klienst hozzá!
Ha valaki küld egy üzenetet a szervernek, a szerveralkalmazás broadcastolja azt
mindenki másnak is. A szerver a `2442` számú porton figyeljen, és ide
csatlakozzanak a kliensek is!

A szerveralkalmazás minden egyes bejövő kapcsolatot külön szállal kezeljen, a
váza valahogy így nézzen ki:

``` java
	ServerSocket socket = new ServerSocket(PORT);
	while (true) {
	    new Handler(socket.accept()).start();
	}
```

A kliensek is legyenek többszálú alkalmazások: az egyik szál folyamatosan
figyelje, hogy nem jön-e új üzenet a csatornán, miközben a másik szál írjon a
csatornára, ha a felhasználó üzenetet írt a konzolra!

## Linkek ##
* **Socket példa** <http://www.oracle.com/technetwork/java/socket-140484.html>
* **Java Tutorial, All About Socket fejezete** <http://download.oracle.com/javase/tutorial/networking/sockets/index.html>
