# TODO #

## Jelölések ##
* `$`: command prompt

## Eldöntendő ##
* Mi legyen a README-ben, és mi az intróban? És mi a Wikiben?
* Mit tudok csinálni a képekkel? A Githubos Markdown megjelenítő baszik
  megmutatni a fájl **mellett** lévő képeket

## Anyagok rendezése ##
Függőségek:

* Generics: collections (vagy fordítva)
* JDBC: reflection, esetleg a szálak
* Socket: I/O, szálak
* Networking -> szálak (alap) -> networking (14 + 16 esetleg összegyúrható, ha
  15 után vannak?) 
* Névtelen osztályok: valahol a GUI akciókezelés környékén? És a threadeknél...
* Szerializáció: I/O
* RMI: szerializáció, socketek

## Wiki ##
* Használt eszközök:
	* Markdown: mert egyszerű, majd hogy nem plaintext (régen LaTeX volt, de nem
	  tudták rendesen használni, ezért áttértem erre :-)
* Usage: kell a `pandoc` vagy `Markdown.pl` (+ActivePerl)

## Buildelés ##
* Kell egy build script
* Kell egy script, ami gitből kiszedi a contributorokat (ld. progit script)

## Legal stuff ##
* Milyen CC licenc is kell nekem? Van vagy 5...

## Anyag bővítése ##
* Bevezetőnek pár megjegyzés az annotációkról: `@SuppressWarnings`, `@Override`
* Javadocról ser ártana pár szóban regélni
* Névtelen osztályok beleszövése: igazán az eseménykezeléshez, threadinghez
  kell, előbb nem nagyon van rá szükség
* Bevezető szöveg: Java vs. C++ (namespace + package, paraméterátadás,
  template-ek, stb.), bár nem kell sok, Tamás úgyis 3 EA-on keresztül ezzel
  foglalkozik :-)
* Ez nem egy teljes, önálló anyag: emlékeztetők, támpontok a gyakorlathoz, aktív
  órai munka és odafigyelés nélkül is lehet haszna, de így kevésbé lehet
  emészthető
* Kéne írni a tesztelésről (*unit testing*, *mock objects*)

## Frissítés ##
* Linkek átrágása (Sun --> Oracle ...) 
* Kiegészítések összefűzése az eredeti anyaggal

## Szószedet ##
* wrapper: csomagoló osztályok
* String: karakterlánc
* Garbage Collector (GC): szemétgyűjtő

## Gyakok ##
### 02-fuggvenyek-csomagok ###
* default konstruktor, main is dobhat kivételt, nem a szignatúra része
* csomagokat, láthatóságot, felsorolási típusokat, interfészeket,
kivételkezelést, konstruktorokat

### 05-oo-adt ###
* Kiegészítés: `toString()` (implicit) használata
* `instanceof` operátor
* `equals()`, `hashCode()` példa
* Beágyazott, statikus ill. példányszintű, lokális és anonymous osztályok

### 10-generics ###
* comparable gányolós függvény
* típusbiztonság a lényeg
* altípusosság megértése talán a legnehezebb, mert erősen az ember intuíciója
  ellen megy. Az intuícióval az a probléma, hogy nem veszi számításba, hogy a
  kollekciók megváltozhatnak
* `?` `Object`-ként kezelhető, de nem adható hozzá! Gondold végig! Szennyezed 
  kollekciót! (kivéve: a `null`, ami ugye minden típusba beleillik). Bounded
  típusnál persze más a helyzet.
* compiler infers most specific generic type
* generic függvények, ökölszabály: ha a típusok közötti polimorfikus
  függőségeket kell megfogalmazni
* 9. oldal lap alja - nem vagom...
* A `Class` `Type` paramétertől függetlenül ugyanaz (ezért nem lehet pl.
  `static` blokkokban használni őket) + ez is igaz lesz:
  
		List <String> l1 = new ArrayList<String>();
		List<Integer> l2 = new ArrayList<Integer>();
		
* `System.out.println(l1.getClass() == l2.getClass());`
* Köv.: `instanceof`-fal sem használható
* Array + generic használata necces
* Array létrehozása necces

### 11-reflection ###
* `Modifier.toString()`

### 12-gui-alapok ###
* Tic-Tac-Toe feladat (régen volt?)

### D-assert ###
* DbC szekciót kiegészíteni

## Final stuff ##
* Kéne csinálni egy nagyobb review-t, hogy nem maradtak-e ki véletlen értékesebb
  bekezdések az összefésülés, újraformázás során.
* Lehet lopni szerkezeti ötleteket a srácoktól: <https://github.com/mojombo/ernie>
  (Github alapító srác egyik projectje, LICENSE directory, stb.)
