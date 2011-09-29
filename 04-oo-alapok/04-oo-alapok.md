# Objektumorientált alapok #

## Hatókör, Élettartam ##
Alapvető fogalmak:

* Hatókör: Ahol a változó használható
* Élettartam: Ahol a változó még _"él"_

``` java
class A {
	static int x = 0;

	static void f(int x) {
		System.out.println( x );   // parameter
		System.out.println( A.x ); // osztaly valtozo
	}
}
```

  Hol deklarálhatok változót?

## Konzol használata ##

Tantárgyi ajánlás a `java.util.Scanner` használata - itt nem, de akit érdekel:
<http://download.oracle.com/javase/6/docs/api/java/util/Scanner.html>.
Helyette: `java.io.Console#readLine()` függvénye és `String#split()`.

## Osztályok, Objektumok ##

### Miért? ###
Egységbe zárás, adatelrejtés, absztrakt adattípus = adatabsztrakció, adattípus
_és_ a rajta értelmezett műveletek.

### Példa ###
``` java
class Koordinata {
	public int x, y;

	public void eltol(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
```

A tagok elérése:

``` java
Koordinata k = new Koordinata();
k.x = 10;
k.y = 20;
k.eltol(10, 0);
```

### Módosítószavak ###
* `static`: Osztályszintű tag: _minden_ példány rendelkezik vele, és ugyanazt a
változót, függvényt látják.

	> **Fontos** Statikus környezetből azonban csak statikus változókat,
	> függvényeket érhetünk el! _Miért?_ V.ö. eddigi függvénydefiníciókkal!
	
	Példa: adott osztály példányainak számolása:

``` java
public class Foo {
	private static int ctr = 0;
	private final int idx;
	...

	public Foo() {
		...
		idx = ctr++;
	}
}
```
	
* `final`: _Ha változó_, akkor az adott referencia nem állítható át (azonban ha
az egy objektum, tömb, akkor az elemei igen! V.ö. C++ `const`). _Ha függvény_,
akkor nem lehet felüldefiniálni (ld. később!).

``` java
public class Main {
	public static final int DEBUG = true;

	public sum(int[] arr) {
		if ( DEBUG ) {
			System.out.println( "Sum params: " + Arrays.toString( arr ) );
		}
		...
	}
}
```

* Láthatósági módosítószavak:
	* `public` Minden más osztályból elérhető (függvény, adattag, vagy osztály)
	* `protected` Adott csomagon belülről, valamint a származtatott osztályokból
	  elérhető
	* `private` Kizárólag az adott osztályon belülről használható
	* _(default)_ Ha nem deklarálsz semmit, az az alapértelmezett, ún.
	  _package-private_ láthatóság: az adott csomagon belül látszik csak.

### Definíció ###

Az osztálydefiníció tartalmazhat adattagokat, függvényeket, és egyéb osztályokat.
Függvénydefiníció tartalmazhat újabb, lokális osztályokat.

Osztályok definiálhatók azonos forrásállományban (de csak egy `public` lehet),
másik osztályban (belső osztályok), függvényen belül (lokális osztályok).
Például:

``` java
package oo.basics;
	
class A { ... }
	
public class B {
	...
	class C { ... }

	void f() {
		class D { ... }
		...
	}
}
```

Beágyazott osztályok lehetnek `static`-ok, ekkor nincs szükség a befoglaló
osztály egy példányára (így a példányváltozókhoz sem férnek hozzá). Ha egy
osztály `final`, nem származtatható belőle újabb osztály.

Mikor melyiket használjuk?

* Ha szükség van egy adott osztály tagjainak elérésére, akkor beágyazott
  osztályt
* Ha úgy logikusabb, akkor beágyazott statikus osztály
* Ha más osztályban nincs rá szükség, de közvetlen nem függ össze a
  reprezentációval, akkor a `public` osztállyal azonos forrásállományba
* Ha általános osztályt készítünk, akkor saját fordítási egységbe

Ha bizonytalan vagy, tégy minden minden osztályt külön fordítási egységbe!

## Származtatás ##

### Miért? ###
Kódmegosztás, kódkomplexitás csökkentése, karbantarthatóság, modularizáció, ...

### Példa ###

``` java
abstract class Sikidom {
	public static final double PI = 3.1415;

	public abstract double kerulet();
	public abstract double terulet();
}

class Kor extends Sikidom {
	private double r = 1.0;

	@Override
	public double kerulet() {
		return 2 * r * PI;
	}
		
	@Override
	public double terulet() {
		return Math.pow( r, 2 ) * PI;
	}
	
class Teglalap extends Sikidom {
	private double a = 1, b = 1;
		
	@Override
	public double kerulet() {
		return 2 * ( a + b );
	}
	
	@Override
	public double terulet() {
		return a * b;
	}
}
```

Javaban csak egyszeres öröklődés van, kivéve az interfészek esetében. Általános
forma:

``` java
class Foo extends Bar implements Baz1, Baz2, ... {
    ...
}
```

### Felüldefiniálás, túlterhelés ###

* Felüldefiniálás Ha a _szignatúra_ megegyezik
* Túlterhelés Minden más esetben

A felüldefiniált függvények elé írjuk oda az `@Override` annotációt, és segít a
compilernek kiszűrni az elgépelésből adódó problémákat fordítási időben.  Ha
hibát ad a fordító, valami nem stimmel (pl. nem egyezik a szignatúra).

Származtatásnál a szűkebb hatókör nem megengedett. Visszatérési értékre nem
lehet túlterhelni, mert az nem része a szignatúrának. Visszatérési értéket lehet
specializálni (hasznos pl. a `clone()` függvénynél - kovariáns kötés).

A kivétel lista nem lényeges ebből a szempontból.

``` java
abstract class A {
    public abstract int f(int a, int b);
    public abstract int g() throws Exception;
    public abstract A h();
}
	
class B extends A {
    @Override
    int f(int a, int b) { return 0; }	// Feluldefinialas
	
    void f(int a, int b) {};			// Hibas: visszateresi ertek nem kompatibilis
    int f(int a) { return f(a, 0); }    // Tulterheles (parameterek szama)
    int f(Integer a, Integer b) { ... } // Tulterheles (parameterek tipusa)
	
    @Override
    int g() { return 0; }     			// OK: Exception lista nem szamit
	
    @Override
    B   h() { return null; }; 			// OK: Visszateresi ertekre specializal
}
```

### A `this` pszeudováltozó. ###

### Adattagok elérése ###
Enkapszuláció elve: minden osztály rejtse el a reprezentációját (hogy könnyen le
lehessen cserélni), és csak a publikus metódusain (interfészén) keresztül
lehessen megváltoztatni.

Azaz az adattagok elérése getter és setter függvényeken keresztül történjen:

``` java
class Koordinata {
	private int x, y; // private lett, csak belul hasznalhato
	...
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
```

### Konstruktorok ###
Objektum létrehozásáért felelnek, _"speciális függvények"_. Általános forma:

	<módosítószó> <Osztály neve>( <paraméter lista> ) {
	  <inicializációs utasítások>
	}

Például:

``` java
class Koordinata {
	private int x, y;
	
	public Koordinata(int x, int y) {
		this.x = x;
		this.y = y;
	}
	...
}
```

Ha származtatás is játszik a történetben, az kicsit bonyolultabbá teheti.
Konstruktorban a szülőre a `super()`, az aktuális példány valamely
konstruktorára `this()` hívással hivatkozhatunk. Ha ezeknek a paramétere egy
függvény visszatérési értéke lehet, az csak statikus függvény lehet.

Példa:

``` java
class A {
	protected int size;
	
	public A(int size) {
		this.size = size;
	}
	...
}
	
class B extends A {
	public B()         { this(0); }     // B(int) hivasa
	public B(int size) { super(size); } // A(int) hivasa
	...
}
```

### Létrehozás, életciklus ###
Objektum létrehozása a `new` operátorral történik:

``` java
A a = new A(5); // Konstruktorhívás
```

Felszabadítással nem kell foglalkozni, azt megoldja a Garbage Collector
(`finalize`). Memória: dinamikus/statikus/stack (utóbbihoz nem fértek hozzá),
automatikusan felügyelt (_eden_, stb.), `System.gc()`, `finalize()`. Az aktuális
értékek lekérdezhetők a `Runtime` osztály metódusaival.

Statikus/dinamikus típus: statikus, amivel definiálva lett, dinamikus, amilyen
referenciára éppen mutat.

``` java
A a = new B(5);
```

A fenti példában az `a` változó statikus típusa `A`, dinamikus típusa `B`
(megengedett, altípusos polimorfizmus, ld. Liskov-féle szubsztitúciós elv). A
dinamikus típus leellenőrizhető az `instanceof` operátorral:

``` java
if (a instanceof B) { ... }
else if (a instanceof C) { ... }
```

### Object ###

Minden típus az `Object` leszármazottja, ha nincs közvetlen őse. Javaban nincs
többszörös öröklődés (kivéve interfészeknél). Fontosabb függvények:

* `equals(Object)` Azonosság vizsgálat, contract szerint reflexív, tranzitív,
  szimmetrikus reláció, valamint konzisztens, és `x.equals(null)` értéke mindig
  hamis legyen.
* `hashCode()` Hasheléshez (pl. egyes halmaz implementációk, vagy a `Hashtable`
  esetén). Contract szerint:
    * Ugyanarra az objektumra hívva konzisztens értéket ad (ha az objektum nem
      változik, ugyanazt az értéket adja).
    * Ha két objektum az `equals()` szerint megegyezik, a `hashCode()` is
      egyezzen meg.
    * Két különböző objektumra _nem kell_ különböző értéket adni
      (ld. *hash collision*).
      
	Példa:

``` java
class Sample {
	private int i = 0;
	private String str = "str";
	private boolean b = false;
			
	@Override
	public int hashCode() {
		return (i * 31 + str.hashCode() );
	}
}
```

* `toString()` Az objektum szöveges reprezentációját adja vissza (Stringként).
* Egyéb függvények: `clone()`, `finalize()`, `notify()`, `wait()`, `getClass()`

> **Részletek** <http://download.oracle.com/javase/6/docs/api/java/lang/Object.html>

## Feladatok ##
A feladatok megoldásához használjatok saját osztályokat, objektumokat.
Használjatok megfelelő módosítószavakat! Az összes osztályt tegyétek legalább
az `oo.basics` csomagba!

**+/- Feladat:** egy tetszőlegesen választott feladat.

### Objektumok használata ###
* Készíts egy interaktív programot, amely a felhasználó által megadott
  szövegeket fordítva kiírja a képernyőre. A megoldáshoz használj
  `StringBuilder` osztályt, a felhasználói interakcióhoz pedig a
  `java.io.Console` osztályt! A program addig kérjen újabb és újabb stringeket a
  felhasználótól, amíg üres stringet nem kap az inputról!
* Készítsetek egy könyvtár listázó alkalmazást! Ez parancssori paraméterként egy
  teljes elérési utat várjon, és hozzon létre egy `java.io.File` objektumot
  (`File dir = new File(args[0]);`)! Ezután listázzátok ki a könyvtárban
  található összes, `".java"`-ra végződő fájlt (`String#endsWith()`,
  `File#listFiles()`)!
* Készíts egy interaktív programot, amely a felhasználó által megadott
  &alpha; értékre visszaadja annak szinusz, koszinusz és tangens értékeit! A
  megvalósításhoz használd a `Math` osztály statikus függvényeit, a felhasználói
  interakcióhoz pedig a `java.io.Console` osztályt! A program addig kérjen újabb
  és újabb &alpha; értékeket a felhasználótól, amíg `"END"` értéket nem kap az
  inputról!
* Készíts egy interaktív konzolos kockadobó alkalmazást! A program kérdezze meg,
  hogy hány 6 oldalú kockával dobjon, majd írja az eredményt a képernyőre! A
  megoldáshoz használd a `java.util.Random` osztályt, a felhasználói
  interakcióhoz pedig a `java.io.Console` osztályt! A program addig kérjen újabb
  és újabb értékeket a felhasználótól, amíg üres stringet nem kap az inputról!

