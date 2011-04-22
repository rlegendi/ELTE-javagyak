# Enumok #

> **Megjegyzés** Az alábbi fejezetet a Java 1.5 egy bevezetőjének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Molnár Szilvinek!
> 
> <http://download.oracle.com/javase/1.5.0/docs/guide/language/enums.html>

A régebbi verziókban a felsoroló típusoknak a szabványos reprezentációja az *int enum pattern*:

``` java
//int Enum minta - komoly hibákkal küzd!
public static final int SEASON_WINTER = 0;
public static final int SEASON_SPRING = 1;
public static final int SEASON_SUMMER = 2;
public static final int SEASON_FALL   = 3;
```

Ezzel a mintával sok probléma van, mint például:

* **Nem típusbiztos** - Mivel az évszak csak egy `int`, ezért bármilyen más `int` értéket megadhatunk neki, ahol egy évszakra van szükség, vagy összeadhatunk két évszakot (aminek nincs semmi értelme).
* **Nincs névtér** - Minden int enum-nak kell adnunk egy string előtagot, (ebben az esetben `SEASON_`) hogy elkerüljük a többi int enum típussal való ütközést.
* **Törékenység** - Mivel az int enum-ok fordítási idejű konstansok, kliensekbe fordulnak le, amik használják őket. Ha egy új konstansot hozunk létre két, már létező konstans közé vagy a sorrend megváltozik, a klienseket újra kell fordítani. Ha ezt nem tesszük meg attól még a program futni fog, viszont a program nem-determinisztikussá válhat.
* **A kiírt értékek nem informatívak** -  Mivel csak egészek, ezért ha kiíratunk egyet csak egy számot fogunk látni, ami nem mond semmit a reprezentációjáról, de még a típusát se tudjuk belőle kikövetkeztetni.	

Lehetőség van elkerülni ezeket a problémákat a *Typesafe Enum* minta használatával (ld. [*Effective Java*](http://java.sun.com/docs/books/effective/) Item 21), de ennek a mintának is megvannak a saját hibái: eléggé összetett, ezért potenciális hibaforrás lehet, valamint ezeket az enum konstansokat nem lehet használni `switch` kifejezésben.

Az 5.0-s verzióban a felsoroló típusok nyelvi támogatást kapnak. Legegyszerűbb alakjukban ezek a felsorolás típusok ugyanúgy néznek ki mint a C, C++ vagy C#-ban levő megfelelőjük:

		`enum Season { WINTER, SPRING, SUMMER, FALL }`

Ugyanakkor a látszat megtévesztő lehet. A Javaban a felsorolási típusok sokkal kifejezőbbek, mint a többi programozási nyelvben, ahol nem sokkal többek, mint az egyszerű egész értékek. Az új `enum` deklaráció egy teljesen önálló osztályt (egy *felsorolási típust*) definiál. Azon kívül, hogy megoldja a fentebb említett összes problémát, lehetővé teszi, hogy tetszőleges metódusokat, adat mezőket adjunk egy felsoroló típusnak, tetszőleges interfészeket implementáljunk, valamint számos más előnnyel is rendelkezik. A felsorolási típusok magas szintű implementációt biztosítanak az összes `Object` metódusnak. Összehasonlíthatók (`Comperable`) és szerializálhatók (`Serializable`). Ezeket úgy tervezték, hogy kiállják a tetszőleges változtatásokat (*hibatűrés*).

Itt van egy kártya játék osztály példa, felépítve néhány egyszerű felsoroló típussal az elején. A `Card` osztály megváltoztathatatlan (*immutable*), és minden `Card`-nak csak egy példánya jön létre, tehát nincs szükség az `equals()` vagy a `hashCode()` függvények túlterhelésére:

``` java
import java.util.*;

public class Card {
	public enum Rank { DEUCE, THREE, FOUR, FIVE, SIX,
		SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }

	public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

	private final Rank rank;
	private final Suit suit;
	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank rank() { return rank; }
	public Suit suit() { return suit; }
	public String toString() { return rank + " of " + suit; }

	private static final List<Card> protoDeck = new ArrayList<Card>();

	// Prototype deck inicializációja
	static {
		for (Suit suit : Suit.values())
		for (Rank rank : Rnk.values())
			protoDeck.add(new Card(rank, suit));
	}

	public static ArrayList<Card> newDeck() {
		return new ArrayList<Card> (protoDeck);   //a protoype deck másolatát adja vissza
	}
}
```

A `Card toString()` metódusa a `Rank` és a `Suit toString()` metódusát használja. Vegyük észre, hogy a `Card` osztály rövid (kb 25 soros kód). Ha a típusbiztos felsorolók (`Rank` és `Suit`) kézzel lettek volna felépítve, akkor jelentősen hosszabbak lenének mint az egész `Card` osztály.

A `Card` (privát) konstruktorának két paramétere van, egy `Rank` és egy `Suit`. Ha véletlenül felcseréljük a konstruktor paramétereit, a fordító udvariasan szólni fog miatta. Szemben az `int` enum mintával, amiben a program futási időben dobna hibát.

Továbbá azt is vegyük észre, hogy mindegyik felsoroló típusnak van egy statikus `values()` metódusa, amely egy olyan tömböt ad vissza, ami tartalmazza az összes felsoroló típus értékét a deklarálásuk sorrendjében. Gyakran használják ezt a metódust kombinálva a [for-each](http://download.oracle.com/javase/1.5.0/docs/guide/language/foreach.html) ciklussal, a felsoroló típusok értékeinek bejárására.

A következő példa egy egyszerű program (`Deal` osztály), ami a `Card` osztályt használja. Két számot olvas be a konzolból, az első a játékosok számát a második a kártyák számát jelenti játékosonként. Aztán létrehoz egy új kártyapaklit, megkeveri, kiosztja és kiírja a konzolba a kártyákat játékosonként.

``` java
import java.util.*;

public class Deal {
	public static void main(String args[]) {
		int numHands = Integer.parseInt(args[0]);
		int cardsPerHand = Integer.parseInt(args[1]);
		List<Card> deck  = Card.newDeck();
		Collections.shuffle(deck);
		for (int i=0; i < numHands; i++)
			System.out.println(deal(deck, cardsPerHand));
		}

	public static ArrayList<Card> deal(List<Card> deck, int n) {
		int deckSize = deck.size();
		List<Card> handView = deck.subList(deckSize-n, deckSize);
		ArrayList<Card> hand = new ArrayList<Card>(handView);
		handView.clear();
		return hand;
	}
}
```

		$ java Deal 4 5
		[FOUR of HEARTS, NINE of DIAMONDS, QUEEN of SPADES, ACE of SPADES, NINE of SPADES]
		[DEUCE of HEARTS, EIGHT of SPADES, JACK of DIAMONDS, TEN of CLUBS, SEVEN of SPADES]
		[FIVE of HEARTS, FOUR of DIAMONDS, SIX of DIAMONDS, NINE of CLUBS, JACK of CLUBS]
		[SEVEN of HEARTS, SIX of CLUBS, DEUCE of DIAMONDS, THREE of SPADES, EIGHT of CLUBS]

Tegyük fel, hogy vmilyen adatot és viselkedés formát akarunk adni egy felsorolónak. Például nézzük a Naprendszer bolygóit. Minden bolygónak van tömege és sugara, ki lehet számolni a felszíni gravitációt és a bolygón lévő tárgyak a tömegét. Így néz ki:

``` java
public enum Planet {
	MERCURY (3.303e+23, 2.4397e6),
	VENUS   (4.869e+24, 6.0518e6),
	EARTH   (5.976e+24, 6.37814e6),
	MARS    (6.421e+23, 3.3972e6),
	JUPITER (1.9e+27,   7.1492e7),
	SATURN  (5.688e+26, 6.0268e7),
	URANUS  (8.686e+25, 2.5559e7),
	NEPTUNE (1.024e+26, 2.4746e7),
	PLUTO   (1.27e+22,  1.137e6);

	private final double mass;   // in kilograms
	private final double radius; // in meters
	Planet(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
	}
	public double mass()   { return mass; }
	public double radius() { return radius; }

	// Universal gravitational constant  (m^3 kg^-1 s^-2)
	public static final double G = 6.67300E-11;

	public double surfaceGravity() {
		return G * mass / (radius * radius);
	}
	public double surfaceWeight(double otherMass) {
		return otherMass * surfaceGravity();
	}
}
```

A `Planet` enum típus tartalmaz egy konstruktort, és minden enum konstans deklarálva van a paramétereivel, amik át lesznek adva a konstruktornak annak meghívásakor.

Itt van egy hasonló program, ami veszi a súlyunkat a földön (bármilyen mértékegységben) és kiszámolja, majd kiírja a különböző bolygókon mért súlyunkat (ugyanabban a mértékegységben):

``` java
public static void main(String[] args) {
	double earthWeight = Double.parseDouble(args[0]);
	double mass = earthWeight/EARTH.surfaceGravity();
	for (Planet p : Planet.values())
		System.out.printf("Your weight on %s is %f%n",
			p, p.surfaceWeight(mass));
}
```

		$ java Planet 175
		Your weight on MERCURY is 66.107583
		Your weight on VENUS is 158.374842
		Your weight on EARTH is 175.000000
		Your weight on MARS is 66.279007
		Your weight on JUPITER is 442.847567
		Your weight on SATURN is 186.552719
		Your weight on URANUS is 158.397260
		Your weight on NEPTUNE is 199.207413
		Your weight on PLUTO is 11.703031


Az elképzelést, hogy viselkedés formát adjunk egy enumnak még tovább fokozhatjuk. A konstans enumoknak néhány metódusához is adhatunk *különböző* viselkedési formát. Az egyik módja ennek, hogy egy switch ágban végigvizsgáljuk az enum konstansokat. A következő enum példában a konstansok reprezentálják a négy aritmetikai műveletet, és azok `eval()` metódusa hajtja végre a műveletet:

``` java
public enum Operation {
	PLUS, MINUS, TIMES, DIVIDE;

	// Ezzel a konstanssal reprezentálva végzi el az aritmetikai műveletet
	double eval(double x, double y){
		switch(this) {
			case PLUS:   return x + y;
			case MINUS:  return x - y;
			case TIMES:  return x * y;
			case DIVIDE: return x / y;
		}
		throw new AssertionError("Unknown op: " + this);
	}
}
```

Ez jól működik, de nem fog lefordulni a `throw` kulcsszó nélkül, ami nem feltétlen előnyös. Ami még rosszabb, hogy nem felejthetjük el, hogy minden alkalommal mikor egy új konstansot adunk az `Operation` enumhoz, akkor hozzá kell adnunk egy új esetet a `switch` blokkhoz. Ha erről megfeledkezünk, akkor az `eval()` metódus megbukik és végrehajtja a fent említett `throw` részt.

Van egy másik megoldás, amivel elkerülhetjük ezeket a problémákat. Az enum típusban absztraktnak deklarálhatjuk a metódust és felüldefiniálhatjuk egy konkrét metódussal minden egyes konstansban. Néhány metódust csak úgy ismerünk, mint *konstans-specifikus* metódus. Itt van az előző példa átalakítva úgy, hogy ezt a technikát használja:

``` java
public enum Operation {
	PLUS   { double eval(double x, double y) { return x + y; } },
	MINUS  { double eval(double x, double y) { return x - y; } },
	TIMES  { double eval(double x, double y) { return x * y; } },
	DIVIDE { double eval(double x, double y) { return x / y; } };

	// Ezt a konstansot használva végez aritmetikai műveletet
	abstract double eval(double x, double y);
}
```

Itt van egy hasonló program ami az `Operation` osztályt használja. Két műveletet vár a konzolból, végigmegy az összes műveleten és mindegyik műveletet végrehajtja, majd kiíratja a kapott egyenleteket.

``` java
public static void main(String args[]) {
	double x = Double.parseDouble(args[0]);
	double y = Double.parseDouble(args[1]);
	for (Operation op : Operation.values())
		System.out.printf("%f %s %f = %f%n", x, op, y, op.eval(x, y));
}
```

		$ java Operation 4 2
		4.000000 PLUS 2.000000 = 6.000000
		4.000000 MINUS 2.000000 = 2.000000
		4.000000 TIMES 2.000000 = 8.000000
		4.000000 DIVIDE 2.000000 = 2.000000

A konstans-specifikus metódusok meglehetősen mesterkéltek, és a legtöbb programozónak soha nem is lesz rá szüksége, de azért jó tudni, hogy ilyen is van.

A felsorolási típusok támogatására két új osztály található a `java.util` csomagban: az [`EnumSet`](http://download.oracle.com/javase/1.5.0/docs/api/java/util/EnumSet.html) és az [`EnumMap`](http://download.oracle.com/javase/1.5.0/docs/api/java/util/EnumMap.html) egy speciális `Set` és `Map` implementációk. Az `EnumSet` egy hatékonyabb implementációja a `Set` interfésznek. Egy enum halmaz összes enum elemének a típusa meg kell, hogy egyezzen. Igazából ez egy bit-vektorként van reprezentálva, tipikusan egy egyszerű `long`-ként. Az enum halmazok enum típusain végig lehet iterálni egy adott tartományon. Például adott a következő enum deklaráció:

``` java
enum Day { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }
```

itt végig lehet iterálni a hétköznapokon. Az `EnumSet` osztály egy static factory-t generál, ami megkönnyíti  a helyzetet:

``` java
for (Day d : EnumSet.range(Day.MONDAY, Day.FRIDAY))
	System.out.println(d);
```

Az Enum halmazok a hagyományos bit-flag-ekre is adnak egy gazdag és típusbiztosabb helyettesítést:

``` java
EnumSet.of(Style.BOLD, Style.ITALIC)
```

Hasonlóan, az `EnumMap` is egy hatékonyabb implementációja a `Map`-nak, amit enum kulcsokkal tudunk használni. Az `EnumMap` igazából egy tömbként van implementálva. Az enum map-ek a `Map` interfésznek egy sokrétű és biztonságos kombinációja, megközelítőleg egy tömb gyorsaságával. Ha egy enumhoz egy értéket akarunk hozzárendelni, akkor mindig az `EnumMap`-ot kell használnunk és nem egy tömböt.

A fenti `Card` osztály egy static factory függvényt tartalmaz, ami egy paklival tér vissza, és semmiféleképpen nem fogunk egy önálló kártyát a típusával és színével együtt visszakapni. Már a konstruktor felfedése is tönkretenné a singleton tulajdonságot (egy kártyának csak egy példánya létezhet). Itt van egy static factory példa, amivel meg tudjuk őrizni a singleton tulajdonságot egy beágyazott `EnumMap`-et használva:

``` java
private static Map<Suit, Map<Rank, Card>> table =
	new EnumMap<Suit, Map<Rank, Card>>(Suit.class);
static {
	for (Suit suit : Suit.values()) {
		Map<Rank, Card> suitTable = new EnumMap<Rank, Card>(Rank.class);
		for (Rank rank : Rank.values())
			suitTable.put(rank, new Card(rank, suit));
		table.put(suit, suitTable);
	}
}

public static Card valueOf(Rank rank, Suit suit) {
	return table.get(suit).get(rank);
}
```

Az `EnumMap` (table) mindegyik suit-ot egy `EnumMap`-hoz társítja és ez az `EnumMap` meg az összes rank-hoz egy card-ot társít. A `valueOf()` metódus által végrehajtott keresés valójában két tömb elérésével van megvalósítva, de a kód sokkal tisztább és biztonságosabb. A singleton tulajdonság megőrzése érdekében elkerülhetetlen, hogy a `Card` osztályban a prototype deck inicializációjánál a konstruktor hívását egy uj static factory hívással helyettesítsük:

``` java
// Initialize prototype deck
static {
	for (Suit suit : Suit.values())
		for (Rank rank : Rank.values())
		protoDeck.add(Card.valueOf(rank, suit));
}
```

Szintén elkerülhetetlen az is, hogy a `table` inicializációja a `protoDeck` inicializációja elé kerüljön, mivel az utóbbi függ az előbbitől.

Tehát mikor kéne enumokat használnunk? Bármikor amikor egy fix konstans halmazra van szükségünk. Ez magában foglalja a természetes felsorolási típusokat (mint például a bolygók, a hét napjai és egy kártyapakli színei), valamint más halmazokat, amiknek futási időben ismerjük az értékeit, mint például egy menü választéka, kerekítési módszerek, konzol flag-ek stb. *Nem* feltétlenül szükséges, hogy az enum típusú konstansok halmaza mindig fix maradjon. Az megvalósítást úgy tervezték, hogy a bináris kompatibilitás megmaradjon, mégha a felsorolási típus időben változik is.

