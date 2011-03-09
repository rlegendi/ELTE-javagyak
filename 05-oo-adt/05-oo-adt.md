# Absztrakt Adattípus #

Absztrakt adattípus = adatabsztrakció, absztrakt adattípus és a rajta
értelmezett műveletek.

## Absztrakt osztályok ##
Ősosztályok "közös nevezőre" hozzák leszármazottaikat: definiálnak egy
interfészt (külső felületet), vagyis egy adat-, és metóduskészletet, amelyen
keresztül a leszármazottak egységesen kezelhetők.

Megvalósítás nélküli metódusokat absztrakt osztályok tartalmazhatnak (`abstract`
módosítószó az osztályhoz, metódushoz). A fordító nem engedi meg, hogy
`abstract` módosítószó szerepeljen `private`, `final`, `static` mellett (ezeket
nem lehet felüldefiniálni). Absztrakt osztály nem példányosítható.

Absztrakt osztály kiterjesztésekor nem kell minden függvényt implementálni (de
ekkor a leszármazottnak is absztraktnak kell lennie).

> **Részletesen** <http://download.oracle.com/javase/tutorial/java/IandI/abstract.html>

Példa:
	abstract class Sikidom {
	  protected boolean tukorszimmetrikus;
	
	  public Sikidom(boolean tukorszimmetrikus) {
	    this.tukorszimmetrikus = tukorszimmetrikus;
	  }
	
	  public abstract double kerulet();
	  public abstract double terulet();
	
	  public void isTukorszimmetrikus() {
	    return tukorszimmetrikus;
	  }
	
	  public void kiir() {
	    System.out.println( "K: " + kerulet() );
	    System.out.println( "T: " + terulet() );
	  }
	}
	
	class Kor extends Sikidom {
	  private static final double PI = 3.1415;
	  private double r = 1.0;
	
	  public Kor() {
	    super( true );
	  }
	  
	  @Override
	  public double kerulet() {
	    return 2 * r * PI;
	  }
	  
	  @Override
	  public double terulet() {
	    return Math.pow( r, 2 ) * PI;
	  }
	}
	
	class Teglalap extends Sikidom {
	  private double a = 1, b = 1;
	  
	  public Teglalap() {
	    super( true );
	  }
	  
	  @Override
	  public double kerulet() {
	    return 2 * ( a + b );
	  }
	  
	  @Override
	  public double terulet() {
	    return a * b;
	  }
	}

Használatra példa:

	public class Main {
	  public static void keruletKiir(Sikidom sikidom) {
	    System.out.println( sikidom.kerulet() );
	  }
	
	  public static void main(String[] args) {
	    Sikidom s = new Kor(); // v.o. statikus-dinamikus tipus
	    keruletKiir( s );
	  }
	}

## Interfészek ##
Új referencia típus, absztrakt függvények és konstansok gyűjteménye
(gyakorlatilag teljesen absztrakt osztályok, amik kizárólag konstans adattagokat
tartalmazhatnak).

Absztrakciós szintet vezet be, felületet definiál. Osztály megvalósít egy
interfészt (`implements`), ha minden függvényét megvalósítja (`abstract`)
osztálynál nem kötelező, ugye).

Eltérés az osztályoktól:

* Többszörös öröklődés (névütközésre figyelni, függvényekre fordítási hiba lesz,
  nem C++, konstansok minősített névvel elérhetők). Szépen ezt úgy mondják, hogy
  a specifikáció többszörösen örökölhető, kód csak egyszeresen.
* Nincs közös ős (mint osztályoknál az `Object`)
* Nem tartalmazhat implementációt (csak absztrakt függvényeket és konstansokat)

> **Részletesen** <http://download.oracle.com/javase/tutorial/java/IandI/index.html>

### Deklaráció ###
Mint az osztályoké:

	interface A {}
	
	public interface B {}

Öröklődési reláció neve itt _kiterjesztés_, lehet többszörös:

	interface C extends A, B {}

Körkörös kiterjesztés fordítási hibát eredményez.

### Tagok ###

Mint az osztályoké, de:

* minden adattag `public`, `static` és `final` alapból  (ezeket nem kell kiírni
  sem, ugyanakkor az egyértelműség kedvéért ajánlott a feltüntetésük)
* minden függvény `public` és `abstract` alapból (ezeket nem kell kiírni sem).
  Más nem lehet.

Előbbiből következik, hogy minden adattagot inicializálni kell (különben
fordítási hibát kapunk), és ez csak már ismert érték lehet
(_forward referencing_ tilos):

	interface I {
	    int A = B; // Hibas definicio!
	    int B = 0;
	}

Nem szerepelhet `this`, `super` sem. Módosítószavak között nem szerepelhet
`synchronized`, `transient`, `volatile` -  ezek olyan dolgokat kötnek meg,
amiknek implementációs szinten kell eldőlniük, használatuk ésszerűtlen lenne
(legalábbis ez a hivatalos álláspont).

Főleg tulajdonságok, viselkedés hozzáadására. Például:

	interface Beolvashato() {
	  public abstract void beolvas();
	}
	
	class Kor implements Beolvashato {
	  ...
	
	  public void beolvas() {
	    String sor = Console.readLine("r = ?");
	    r = Integer.parseInt( sor );
	  }
	}

## Summarium ##
*It boils down to this.* Amit *mindenképp* meg kell jegyezni, az a következő:

* *Interfész* használatával megoldható a többszörös öröklődés
* *Absztrakt osztály* pedig megvalósítást is tartalmazhat

### Interfész ###
	public interface GroupedInterface
	    extends Interface1, Interface2, Interface3 { ... }

vagy például:

	class A extends B implements I1, I2 { ... }

> **Részletesen** <http://download.oracle.com/javase/tutorial/java/IandI/interfaceDef.html>

### Absztrakt osztályra példa ###
	public abstract class Point {
	    private int x = 1, y = 1;
	    
	    void move(final int dx, final int dy) {
	        x += dx;
	        y += dy;
	        alert();
	    }
	    
	    abstract void alert();
	}

> **Részletesen** <http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#34944>

## Feladatok ##
A feladatok megoldásához használjatok saját osztályokat, objektumokat.
Használjatok megfelelő módosítószavakat! Az összes osztályt tegyétek legalább
egy `oo.adt` csomagba!

> **+/- Feladat** egy tetszőlegesen választott feladat.

### Interfészek ###
* Készíts egy saját osztályt, amely implementálja a `java.lang.CharSequence`
  interfészt úgy, hogy a konstruktorában megadott Stringet fordítva tartalmazza!
* Készíts két saját tetszőleges `Kirajzolhato` és `Mozgo` interfészt. Adj
  hozzájuk szemléltető implementációkat, amelyek bemutatják az interfészek
  használatát külön-külön és mindkét esetben is!

### Osztályok készítése ###
Ügyelj a láthatóságok helyes használatára (`private`, `protected` adattagok, és
ezekhez megfelelő `public` lekérdező függvények). Az osztályok külön fordítási
egységekben legyenek! A `Main` osztály a `oo.adt.fx` csomagban legyen, az összes
többi a `oo.adt.fx.data` csomagban (ahol `x` a feladat sorszámát jelöli)! A
megadott példakódokat használjátok, azokat szűkíteni nem, de bővíteni
megengedett (sőt, ajánlott is). 

* Készítsd el a többdimenziós pontok absztrakt osztályát (`APont`). Pontokat
  lehessen eltolni, forgatni és tükrözni! Készítsd el ez alapján az ősosztály
  alapján az 1D, 2D, 3D pontok megvalósítását (tartalmazzák a szükséges `double`
  koordinátákat, valamint az absztrakt függvények implementációit).

  Közös adattag legyen az első, `x` koordináta (ezzel minden származtatott osztály
  rendelkezik). Az eltoláshoz használj változó számú paramétert (*vararg*), a
  következő minta alapján:

		public abstract void translate(double... coordinates);

  A forgatást vedd úgy, hogy az origó kürül kell elforgatni a megadott pontot,
  a paraméterként specifikált *alpha* szöggel (3D pont esetén elég valamelyik
  tengely körül forgatni)!

  Készíts egy `Kirajzolható` interfészt! Ezt az 1D pont implementálja úgy, hogy
  a koordinátáinak megfelelő számú szóköz után rajzoljon egy `#` karaktert a
  konzolra (negatív esetben az `#` után rakjon szóközöket).
  
  Készíts egy `Frissitheto` interfészt! Ez egyetlen metódust írjon elő:

		public abstract void frissit();

  Ezzel a képernyőről, felhasználói interakció révén lehessen frissíteni az
  adott objektum értékeit! Minden implementáció valósítsa meg ezt az interfészt!

	> **Tipp/Csel** Használhatjátok a későbbiekben leírt mátrix osztályt!

* Készítsetek egy kódoló alkalmazást! Hozzátok létre a kódolóalgoritmusok
  absztrakt osztályát (`AKodolo`), amely a következő definíciókat tartalmazza:

		public abstract class AKodolo {
		  public abstract String kodol(String eredeti);
		  public abstract String dekodol(String kodolt);
		}

  A különböző megvalósítások a kapott eredeti szöveget kódolják ill. fejtik
  vissza (kódolás előtt alakítsátok nagybetűssé a kapott szöveget).

  Készítsetek legalább két különböző kódoló algoritmus implementációt:

	* Caesar-kódolás: 3 betűvel legyenek eltolva a karakterek (A = D, B = E,
	  ..., X = A, Y = B, Z = C)
	* Monoalfabetikus kódolás: a karakterek megfeleltethetők egymásnak az alábbi
	  szabályok szerint:
	
			Eredeti: ABCDEFGHIJKLMNOPQRSTUVWXYZ
			Kodolt:  ZEBRASCDFGHIJKLMNOPQTUVWXY

	> **Tipp/Csel**  Nehogy switch-case szerkezetet használjatok! :-)

* Készítsd el a sorozatok absztrakt osztályát (`ASorozat`)! Sorozatoknak
  általánosan lehessen lekérdezni az első `n` tagját, az `n` tag
  részletösszegét, valamint hogy monoton növekvő-e, csökkenő-e a sorozat.
  Készítsd el ez alapján az ősosztály alapján a számtani és mértani sorozatok
  megvalósítását (tartalmazzák a specifikus adatokat, mint pl. az első szám, és
  kvóciens vagy differencia).

  Készíts egy `Util` osztályt, amely a következő definíciót tartalmazza:

		public final class Util {
		  public static ASorozat beolvas() {
		    //...
		  }
		
		  private Util() {}
		}

  Ez a felhasználótól kérdezze meg, hogy milyen sorozatot szeretne megadni,
  segítsen neki létrehozni egyet, és ezt a létrehozott sorozatot adja is vissza.

* Valósítsd meg a mátrixok típusát Javaban! Két konstruktor legyen: az egyik a
  méretet adja meg (négyzetes mátrixot hozzon létre, és minden elem 0 legyen), a
  másik pedig egy `double[][]` paramétert kapjon! Mátrixokat lehessen összeadni,
  és adott valós számmal beszorozni, valamint lekérdezni, hogy négyzetes-e.

* Egészítsd ki az előző feladatot az általános, `Object` osztályból örökölt
  függvényeket (`equals()`, `hashCode()`, `toString()`).

* Egészítsd ki az előző feladatot a következő funkciókkal:
	* transzponálás
	* nyom kiszámítása (átló elemeinek összege, ha négyzetes)
	* mátrixok szorzása mátrixokkal (amennyiben lehetséges)
