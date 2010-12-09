# TODO #

## Eldöntendő ##
* Mi legyen a README-ben, és mi az intróban? És mi a Wikiben?

## Wiki ##
* Használt eszközök:
	* Markdown: mert egyszerű, majd hogy nem plaintext (régen LaTeX volt, de nem
	  tudták rendesen használni, ezért áttértem erre :-)
* Usage: kell a pandoc vagy Markdown.pl (+ActivePerl)

## Buildelés ##
* Kell egy build script
* Kell egy script, ami gitből kiszedi a contributorokat (ld. progit script)

## Legal stuff ##
* Milyen CC licenc is kell nekem? Van vagy 5...

## Anyag bővítése ##
* Bevezetőnek pár megjegyzés az annotációkról: @SuppressWarnings, @Override
* Javadocról pár szó
* Bevezető szöveg: Java vs. C++ (namespace + package, paraméterátadás,
template-ek, stb.)
* Ez nem egy teljes, önálló anyag: emlékeztetők, támpontok a gyakorlathoz, aktív
órai munka és odafigyelés nélkül is lehet haszna, de így kevésbé lehet
emészthető   

## Frissítés ##
* Linkek átrágása (Sun \cup Oracle \ldots) 
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
* Kieg: toString() (implicit) használata
* instanceof operator
* equals(), hashcode példa
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

### D-assert ###
* DbC szekciót kiegészíteni
