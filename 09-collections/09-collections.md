# Gyűjtemény keretrendszer #

## Generic - Bevezetés ##
**Egyelőre csak a collectionökhöz, bevezető jelleggel.** Típussal
paraméterezhetőség. Type erasure: csak fordítási időben ismert a
típusinformáció, utána automatikusan törli a fordító, bájtkódból nem szerezhető
vissza - nem C++ template-ek, nem generálódik fordítási időben új típus, nincs
template metaprogramozás.

Nem kötelező velük foglalkozni (`@SupressWarnings({"rawtypes", "unchecked")}`),
de rendkívül hasznosak, fordítási időben tudunk potenciális hibalehetőségeket
kiszúrni - persze ez is a programozón múlik. Kényelmes, típusbiztos. Aki meg
heterogén adatszerkezeteket használ, megérdemli.

Collectionöknél aktívan használjuk őket:

``` java
Vector<String> s = new Vector<String>();
```

Az előnyük:

``` java
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
```

## Autoboxing-unboxing ##
Csak objektum referenciákat tárolhatnak, ezért _primitív típusok helyett_ ún.
csomagoló (_wrapper_) osztályokat (`Integer`, `Character`, `Double`, etc.) kell
használnunk - azonban ezek ilyen esetekben automatikusan konvertálódnak (ld.
fenti példa). **Amire figyelni kell:** teljesítmény, `==` operátor, `null`
unboxing NullPointerException-nel jár.

Példa:

``` java
v.add(1);
// Implicit a kovetkezot jelenti:
v.add( new Integer(1) );
	
v.add(1);
boolean eq = v.get(0) == v.get(1); // LEHET hamis! (*)
	
v.add(null);
for (int act : v) { ... } // RECCS!
```

**Megjegyzés** A `(*)`-gal jelölt rész speciel pont mindig igaz lesz, de ez
**mágia műve**: a -127-126 intervallumon lévő számok wrapper objektumait
cache-eli a virtuális gép, azok mindig ugyanazok a példányok lesznek. Így itt
tapasztalható probléma nem lép fel, de általában ezzel gond lehet.

## Gyűjtemény keretrendszer ##
Collections Framework, `java.util.*` csomag, objektumok memóriában tárolására,
lekérdezése, manipulálása (v.ö. C++ STL). Általános célú adatszerkezetek:

* Collection
	* List
    * Deque
    * Set
        * SortedSet
	* Map
    	* SortedMap

Nem megvalósított művelet `UnsupportedOperationException` kivételt dob. Copy
konstruktorok vannak (egyik a másikra konvertálható). Műveletek 3 csoportja:

1. Alapvető műveletek: `size()`, `isEmpty()`, `contains()`, `add()`, `remove()`
2. Elemek együttes kezelése: `addAll()`, `containsAll()`, `removeAll()`,
   `clear()`, `retainAll()`
3. Tömbbé konvertálás - gány:

``` java
A[] arr = (A[]) list.toArray(new A[list.size()]);
		
// Kicsit egyszerubb, bar kevesbe hatekony, biztonsagos:
A[] arr = (A[]) list.toArray();
```

Iterátorokkal rendelkeznek, használhatók for-each-ben. Példa:

``` java
package collections;
	
import java.util.Vector;
	
public class VectorTest {
	public static void main(String[] args) {
		Vector<Double> vector = new Vector<Double>();
	        
		for (int i=0; i<5; ++i) {
			vector.add( Math.random() );
		}
	        
		for (double act : vector) {
			System.out.println(act);
		}
		
		// Ellenorzeskeppen kiirhatjuk a teljes vektort az alabbi modon is: 
		System.out.println(vector);
	}
}
```

### Halmaz ###
Duplikált elemeket nem tartalmazhat, kell hozzá az objektumon az `equals()` és
`hashCode()` (hashelő implementációk, nem számít a sorrend, 2 halmaz egyenlő, ha
ugyanazokat az elemeket tartalmazzák). `HashSet`, `TreeSet`: előbbi hatékonyabb,
utóbbi rendezett.

#### Feladat ####
Készíts egy programot, amely megszámolja a parancssori argumentumként megadott
fájl szavaira, hogy azokban hány különböző betű van (kis-, és nagybetűk között
ne tegyünk különbséget, és az egyéb karakterekkel ne foglalkozzunk)! A
megvalósításhoz használj halmazt (`Set<Character>`, `String#toCharArray()`)!
Példa output:

	$ cat input.txt
	Ia! Shub-Niggurath! The Black Goat of the Woods with a Thousand Young!
	$ java collections.CharCounter input.txt
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

### Lista ###
Elemek pozíció szerinti elérése, iteráció, részlista kezelés. A `remove()` az
elem első előfordulását távolítja el, az `add()`, `addAll()` a lista végéhez fűz
hozzá. Két lista egyenlő, ha ugyanazokat az elemeket tartalmazzák, ugyanabban a
sorrendben. A lista iterátora a `ListIterator`, 2 irányban is bejárható:
`hasNext()`, `next()`, ill. `hasPrevious()`, `previous()`. Részlista: balról
zárt, jobbról nyílt intervallumot kell megadni. Két alapvető implementáció:
`ArrayList`, `LinkedList`, előbbi a pozicionáló műveleteknek kedvez, utóbbi
akkor, ha a lista elejére kell sokat beszúrni, és iteráció közben törölni
(általában az `ArrayList` használata a célravezetőbb).

#### Feladat ####
Készíts egy programot, amely a parancssori argumentumként megadott fájl szavait
lexikografikusan rendezi, az eredményt kiírja a képernyőre, és el is menti egy
(szintén parancssori argumentumként megadott) fájlba! A feldolgozás előtt minden
nem alfanumerikus karaktert távolíts el a szavakból. A megvalósításhoz használ
egy tetszőleges lista adatszerkezetet (`ArrayList`, `LinkedList`, `Stack`,
`Vector`), valamint a `java.util.Collections#sort()` függvényt! Példa output:

	$ cat input.txt
	Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn
	$ java collections.StringSorter input.txt output.txt
	[Cthulhu, Phnglui, Rlyeh, fhtagn, mglwnafh, wgahnagl]
	$ cat output.txt
	Cthulhu
	Phnglui
	Rlyeh
	fhtagn
	mglwnafh
	wgahnagl

### Leképezés ###
Kulcs-érték párokhoz: `HashMap`, `Hashtable` (minimális különbség: utóbbi
szinkronizált, megengedi a `null` értékeket is). Minden kulcshoz egy érték
tartozhat. Nem iterálható, azonban lekérdezhető a `keySet()`, `entrySet()`, ami
már igen.

#### Feladat ####
Készíts egy programot, amely megszámolja egy fájlból az egyes szavak
előfordulásainak számát! A program a fájl elérési útját argumentumként kapja. A
megvalósításhoz használj egy `String` &rarr; `Integer` leképezést
(`HashMap<String, Integer>`)! Példa output:

	$ cat input.txt
	Ia! Ia! Cthulhu fhtagn!
	$ java collections.WordCounter input.txt
	{Ia!=2, Cthulhu=1, fhtagn=1}

Megoldási javaslat: minden szó esetén ellenőrizd le, hogy szerepel-e már az
adatszerkezetben. Ha nem, rakd bele 1-es értékkel; ha igen, vedd ki az előző
értékkel, és eggyel nagyobb értékkel tedd vissza!

### Rendezés ###
Beépített típusoknak értelemszerű a relációja - felhasználói típusokat a
programozó dönti el. `Comparable` interfész `compareTo()` metódusa, melynek
eredménye `int` típusú:

* 0, ha a két objektum egyenlő
* < 0, ha az adott objektum kisebb a paraméternél
* > 0, ha fordítva

Implementáció:

``` java
class Foo implements Comparable<Foo> {
	...
	public int compareTo(final Foo foo) {
		return ...;
	}
}
```

Ha ennek használatára nincs lehetőség, marad egy saját `Comparator` készítése
(pl. egyazon objektumot több szempont szerint kell rendezni).

#### Feladat ####
Készítsetek egy `Date` osztályt, amely tartalmazza az év, hónap, nap adatokat
(mind számok). Implementáljátok vele a `Comparable<Date>` interfészt, és ennek
megfelelően valósítsátok meg a `compareTo()` függvényt! Hozzatok létre kódból 3
objektumot, és tároljátok el ezeket egy tetszőleges lista adatszerkezetben.
Ezt aztán rendezzétek le kronológiai sorrend szerint a `Collections#sort()`
függvénnyel, és írjátok ki az eredményt!

### Bejárás ###
Saját típus is *"iterálhatóvá"* tehető a megfelelő interfész megvalósításával:

``` java
public class Necronomicon implements Iterable<Account> { 
	public static final String author = "Abdul 'Mad Arab' Alhazred";
	
	private List<Account> accountsOfTheOldOnes;

	public Iterator<Account> iterator() {        
		Iterator<Account> itr = accountsOfTheOldOnes.iterator();
		return itr;
	}
	...
}
```

Bejárás:

``` java  
Necronomicon necronomicon = Necronomicon.getInstance();

Iterator<Account> itr = necronomicon.iterator();
while (itr.hasNext()) {  
	Account account = itr.next();
	account.summonOldOne();
}
```

vagy:

``` java
for (Iterator<Account> itr = necronomicon.iterator(); it.hasNext(); ) {
	account.decreaseReaderSanity();
}
```

vagy Java 1.5 óta:

``` java
for (Account account : necronomicon) {
	account.suppressAndBurnReader();
}
```

### Kényelmi lehetőségek ###
1. `java.util.Arrays#asList()`: tömbből listát csinál
2. `java.util.Collections`
	* `nCopies(int n, Object o)`: két paraméter, amely `n`-szer tartalmazza
	  `o`-t
	* Egyelemű, üres, módosíthatatlan, szinkronizált listák
	* Algoritmusok:
		* Rendezés, összefésüléses módszerrel (`n log(n)`, rendezett listát már
		  nem rendez, szemben a quick sorttal): `sort()`
		* Összekeveerés: `shuffle()`
		* Megfordítás, feltöltés, másolás: `reverse()`, `fill()`, `copy()`
		* Bináris keresés: a keresett elem első talált indexét adja vissza, vagy
                  `(-i-1)`-et ad vissza, ahol `i` az első olyan elem indexe, amely nagyobb
                  a keresett elemnél.
		* Minimum, maximum elem: `min()`, `max()`

#### Megjegyzések: ####
* `capacity()` != `size()`
* Az interfész műveleteken kívül rengeteg egyéb hasznos funkcionalitás, érdemes
  a javadocot olvasgatni
* Saját implementációk: hajrá! A Collections Framework absztrakt osztályokat
  biztosít (`AbstractList`, `AbstractSet`, etc.), lehet származtatni.
* További adatszerkezetek: `Dequeue`, `Stack`, `BitSet`, `Vector`, etc.
* Felhasználás: paraméterként, változódeklarációként célszerű minél általánosabb
  interfészt megadni (a collections framework előnye a rugalmassága):

``` java
Vector<Integer> v1 = new Vector<Integer>();  // vektorkent kezeles
List<Integer>   v2 = new Vector<Integer>();  // listakent kezeles
```

> **Részletesen:**
> <http://download.oracle.com/javase/tutorial/collections/index.html>
> <http://download.oracle.com/javase/6/docs/api/java/util/package-summary.html>

#### Feladat ####
Készítsünk egy sorozat rendező alkalmazást! A program inputja a következő
formátumú fájl legyen:

	# evad : epizod : cim
	12:7:Super Fun Time
	12:4:Canada on Strike
	8:13:Cartman's Incredible Gift
	10:8:Make Love, Not Warcraft

A # karakterrel kezdődő sorokat hagyjuk figyelmen kívül (használjuk a vizsgálat
előtt a `String#trim()`)! Készítsünk egy `Sitcom` osztályt a megfelelő
adattagokkal (`season`, `episode`, `title`), és implementáljuk vele a
`Comparable<Sitcom>` interfészt! A `compareTo()` működjön úgy, hogy elsődleges
szempont szerint az évad, azon belül pedig az epizódszám alapján rendezzen! Az
adatokat tároljuk egy `Vector<Sitcom>` adatszerkezetben. A saját osztály helyes
működéséhez implementáljuk az `equals()`, `toString()`, `hashCode()`
függvényeket is!

A program beolvasás után rendezze a használt vektort, és biztosítson egy
interaktív konzolos felületet a felhasználónak, amely a következő
funkcionalitásokkal rendelkezzen:

* Új epizód felvétele
* Epizódlista mentése (kilépés után ezt töltse be)
* Adott epizód adatainak módosítása
* Epizódok listázása a képernyőre
* Kilépés
