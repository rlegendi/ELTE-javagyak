# Alapozás #

## Változók ##
* Deklaráció, inicializáció
* Kezdeti érték?
* Primitív típus, érték? Objektum, referencia?
* Kifejezések? Kiértékelési sorrend? Szigorúan balról jobbra (v.ö. C++)
* Prefix és postfix operátorok (`++i`, `i++`)

		int i = 0;
		System.out.println(i++); // kiir, megnovel: "0"
		System.out.println(++i); // megnovel, kiir: "2"

## Felhasználói interakció ##
Használjátok a `java.io.Console` osztályt (JDK 1.6 óta van csak, erre
figyeljetek!).

	java.io.Console c = System.console();
	String line = c.readLine();
	int lineAsInt = Integer.parseInt( line );

## Tömbök ##

* Minden `T` típushoz van `T[]`
* Referencia: null értéke lehet!
* Indexelés nullától
* Túl-, ill. alulindexelés: `ArrayIndexOutOfBoundsException` (futásidejű
  kivétel)
* Inicializáció:

		// barr1, barr2, barr3 ekvivalens definicio
		boolean barr1 = { true, false };
		boolean barr2 = new boolean[] { true, false }; // kifejezesben kotelezo!
		boolean barr3 = new boolean[2];
		barr3[0] = true; barr3[1] = false;

* Tömbök elemei default értéket kapnak (pl. objektum nullt, int 0-t, boolean false-t, stb.)
* Bejárás: tömb tudja a méretét

		int iarr[] = { 1, 2, 3, 4, 5 };
		
		for (int i=0; i<iarr.length; ++i) {
		   	System.out.println(iarr[i]);
		}

* Többdimenziós példa:

		public static void enumerate(int[][] arr) {
		    for (int i=0; i < arr.length; ++i) {		
		        for (int j=0; j < arr[i].length; ++j) {
		            System.out.print( arr[i][j] + " " );
		        }
		    }
		}
		...
		enumerate( new int[][] {
		    {0, 1},
		    {2, 3}
		});

* Inicializálásnál az 1. dimenzió megadása kötelező (pl.
`int[][] arr = new int[5][];` teljesen legális definíció!)

### Tömb értékű kifejezések ###
Inicializálásnál elég a { e_1, e_2, ... } forma. DE! Mindenhol máshol, ahol tömb
típusú kifejezést szeretnénk leírni (`new`), a fordítási hibák kiszűrése miatt a
típust is meg kell jelölnünk. Pl.:

	public static int sum(int[] arr) { ... }
	
	public static void main(String[] args) {
	    int result = sum( new int[] {1, 2, 3} );
	}

### Tömb segédosztály ###
`java.util.Arrays`, hasznos pl. a `toString()`, `binarySearch()`, `fill()`, etc.
Részletesen: <http://java.sun.com/javase/6/docs/api/java/util/Arrays.html>

### Összehasonlítás ###
Az `==` operátor **nem** használható (_referencia szerinti egyenlőség vizsgálat, nem érték szerinti_).
A megoldás a `java.util.Arrays` osztály: `equals()`, `deepEquals()` (vagy
megírod kézzel).

## Függvények ##

* Érték szerint
* Referenciák? - Azok is
* Tömbök, objektumok
* Vararg paraméterek: tetszőleges számú formális paraméter, kezelés mintha tömb
  lenne:

		public static void printMessages(String... messages) {
		    System.out.println("# of params: " + messages.length);
		    for (String act : messages) {
		        System.out.println(act);
		    }
		}
		...
		printMessages( "hello", "hi", "bye");

Ha minden függvényben szükséges egy érték:

	public class A {
	   public static int X = 1;
	
	   public static void inc() { X++; }
	   public static void dec() { X--; }
	}

## Stringek ##

* Létrehozás:

	String s = "hai!";

* Objektum, így lehet az értéke `null`! Ha `null` értéket próbáljátok feloldani
akkor az eredmény egy `NullPointerException` lesz.

		String nullString = null;
		
		if (nullString != null) { // Helyes ellenorzes
		   ...
		}
		
		nullString.isEmpty(); // NPE!

* Rengeteg hasznos szolgálgatás:

		int length = s.length;
		char firstChar = s.charAt(0);
		char[] carr = s.toCharArray();
		
		boolean isExecutable = s.endsWith(".exe");
		boolean isEmpty = s.isEmpty();
		boolean aidx = s.indexOf('a');
		boolean hasEM = s.contains('!');
		
		String upper = s.toUpperCase();
		String hai = s.substring(1, 3);
		String haiThere = hai + " there".
		String[] haiThereArr = haiThere.split(" ");
	
> **Részletesen** <http://java.sun.com/javase/6/docs/api/java/lang/String.html>

* Karakterenkénti bejárás:

		for (int i=0; i<s.length(); ++i) {
		    char act = s.charAt(i);
		    ...
		}
		
		// vagy:
		for (char act : s.toCharArray()) {
		   ...
		}

* `replaceAll()`, `split()` használható regexp (ld. Pattern osztály
dokumentációja <http://download.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html>)

* Immutable (megváltoztathatatlan) adatszerkezet

		String string = "AAAxAAA";
		string.replace('x', 'A');
		System.out.println(string); // "AAAxAAA"
		string = string.replace('x', 'A');
		System.out.println(string); // "AAAAAAA"

   Vagy `StringBuilder`, `StringBuffer` használható:

		StringBuffer sb = new StringBuffer();
		sb.append("Hello ").append("World");
		sb.reverse();
		System.out.println( sb.toString() ); // "dlroW olleH"
		sb.reverse();
		sb.setCharAt(6, '-');
		System.out.println( sb.toString() ); // "Hello-World"
		sb.deleteCharAt(6);
		System.out.println( sb.toString() ); // "HelloWorld"
		sb.delete(0, sb.length() );
		System.out.println( sb.toString() ); // ""

* Összehasonlítás: `equals()` metódussal (az `==` operátor referencia szerinti
összehasonlítást végez csak, nem tartalom szerintit).

		boolean b1 = "a" == "a";      // lehet hamis!
		boolean b2 = "a".equals("a"); // mindig megfeleloen mukodik

> **Részletesen** <http://java.sun.com/javase/6/docs/api/java/lang/StringBuilder.html>

## Feladatok ##
A feladat megoldásához használjatok függvényeket, és megfelelő hibakezelést (pl.
`IllegalArgumentException`, saját típust még nem kell definiálnotok)! Egyelőre
minden függvényt lássatok el a `public static` módosítószavakkal! Az összes
megoldást rakjátok egy `java.basics` csomagba! A feladatok nem feltétlen
nehézségi sorrendben követik egymást!

**+/- Feladat** Minden feladatcsoportból egy tetszőlegesen választott feladat.

### Tömbök ###
A következő függvények implementálásához használjátok nyugodtan a már
meglévőket. Például az átlagolás, normálás esetén szükség van az összegre, itt
használjátok azt a függvényt, amit az adott feladatban megírtatok!

1. Készíts egy függvényt, amely kiszámítja egy tömb elemeinek az összegét!
2. Készíts egy függvényt, amely kiszámítja egy tömb elemeinek az átlagát!
3. Készíts egy függvényt, amely normálja egy tömb összes elemeit (az elemek
   összege legyen kb. 1)!
4. Készíts egy függvényt, amely növekvő sorrendbe rendezi egy tömb elemeit!
5. Készíts egy függvényt, amely meghatározza egy tömb minimális és maximális
   elemét!
6. Készíts egy függvényt, amely összefűzi egyetlen Stringbe a paraméterként
   kapott Strinkgeket. Az első paraméter legyen az elválasztó karakter (minden
   konkatenáció közé ezt kell beszúrni), és a függvény fogadjon el tetszőleges
   számú paramétert!
7. Az előző pontban készített függvényhez készíts egy azonos nevű (túlterhelt)
   változatot, amelynek nincs szüksége az első paraméterre, és a ' ' karakterrel
   fűzi össze a karakterláncokat!
8. Készíts egy függvényt, amely képes meghatározni két paraméterként kapott tömb
   skaláris szorzatát!
9. (?) Készíts egy függvényt, amely képes meghatározni két paraméterként kapott
   tömb vektoriális szorzatát! % TODO: 2 dimenziora!
10. Készítsetek egy kódoló függvényt, amely egy `char[]` paraméterként
    megadott szöveget úgy kódol, hogy minden karaktert egy konstans értékkel
    XOR-ol (használjátok a `^` operátort!).
11. Készítsétek el a dekódoló változatát (a kódolt `char[]` paramétert
    XOR-oljátok végig még egyszer ugyanazzal az értékkel, így megkapjátok az
    eredeti értékeket!).

### Többdimenziós tömbök ###

1. Készíts egy függvényt, amely megfelelően formázva kiír a képernyőre egy 2
dimenziós tömböt!

		Példa (az `asMatrix` függvényt csak visszaadja Stringet!):
	
		doule[][] arr = new double[3][3];
		arr[1][1] = 1
		System.out.println( asMatrix( arr) );

	Eredmény:

		0.0 0.0 0.0
		0.0 1.0 0.0
		0.0 0.0 0.0

2. Készíts egy függvényt, amely képes megmondani, hogy egy kapott, 2 dimenziós
tömb soraiban, oszlopaiban, és főátlójában szereplő elemek összege mennyi!

   A program valami hasonló kimenetet generáljon:

		0 1 2 | 3
		1 2 3 | 6
		2 3 4 | 9
		------/
		3 6 9   6

3. Készíts egy függvényt, amely képes meghatározni egy paraméterként kapott, 2
   dimenziós tömbként reprezentált mátrix és egy valós érték szorzatát!
4. Készíts egy függvényt, amely képes meghatározni két paraméterként kapott, 2
   dimenziós tömb mátrix összegét!
5. Készíts egy függvényt, amely képes meghatározni két paraméterként kapott, 2
   dimenziós tömb mátrix szorzatát!

### Stringek ###

Igyekezzetek tanulmányozni a dokumentációt! A feladatok lényegi részei maximum 5
sorban megoldhatók a megfelelő API függvények használatával!

1. Készítsetek egy programot, amely egy parancssori argumentumként megadott
   Stringet átalakít a következőképpen:
	1. Minden numerikus karaktert változatlanul hagy
	2. Minden betűt kisbetűvé alakít
	3. Minden egyéb karaktert lecserél egy _ karakterre

2. Készítsetek egy programot, amely minden ékezetes karaktert lecserél a
megfelelő, ékezet nélküli változatára!

3. Készítsetek egy függvényt, amely egy  parancssori argumentumként megadott
Stringben lecseréli az első karaktert nagybetűre! A visszatérési értéke legyen
ez az új String!

4. Készítsétek el az előző függvény fordított változatát: az első karaktert
cserélje kisbetűre!

5. Készítseget egy függvényt, ami 2 String paramétert kap, és megmondja, hogy az
első a másodikkal kezdődik-e. Az összehasonlításnál a kisbetű, nagybetű eltérés
nem számít (azaz pl. `"Windows 7"`, `"win"`) paraméterekre adjon vissza igaz
értéket!).
